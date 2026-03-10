package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IBarrel;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.container.BarrelRecipeContainer;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.BarrelRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.BarrelRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ItemUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.fluids.CustomFluidTank;
import com.github.ysbbbbbb.kaleidoscopetavern.util.fluids.FluidUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.IItemHandler;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.ItemStackHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class BarrelBlockEntity extends BaseBlockEntity implements IBarrel {
    /**
     * 酒桶检查时间，每 5s 检查一次，选取最接近的质数，避免与其他周期性事件同时发生，导致性能问题
     */
    private static final int CHECK_INTERVAL = 97;
    private static final String INGREDIENT_ITEMS_KEY = "ingredient_items";
    private static final String INGREDIENT_SIZE_KEY = "ingredient_size";
    private static final String OUTPUT_ITEMS_KEY = "output_items";
    private static final String OUTPUT_SIZE_KEY = "output_size";
    /**
     * 配方缓存
     */
    private final RecipeManager.CachedCheck<BarrelRecipeContainer, BarrelRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.BARREL_RECIPE);
    /**
     * 酒桶的物品槽，大小固定为 4 组
     */
    private final ItemStackHandler ingredient = new ItemStackHandler(MAX_ITEM_SLOTS) {
        @Override
        public int getSlotLimit(int slot) {
            // 最大只运行 16 个物品，防止玩家浪费
            return 16;
        }

        @Override
        protected void onContentsChanged(int slot) {
            refresh();
        }
    };
    /**
     * 输出的物品栏，大小只有 1 槽位，只有在发酵完成后才会有物品输出
     * <p>
     * 此物品仅用于计数和显示，不会直接输出
     */
    private final ItemStackHandler output = new ItemStackHandler(1);
    /**
     * 酒桶的液体槽，大小固定为 4 桶
     */
    private final CustomFluidTank fluid = new CustomFluidTank(toTransferAmount(), this::refresh);
    /**
     * 酒桶开盖状态，只有关闭盖子才会进行发酵判定
     */
    private boolean open = true;
    /**
     * 发酵等级，没有开始酿造时为 0，每过单位时间增加 1，达到 7 时为最高品质
     */
    private int brewLevel = BREWING_NOT_STARTED;
    /**
     * 到达下一个阶段的剩余时间，单位为 tick，每过一个 tick 减少 1，当达到 0 时进入下一个阶段
     */
    private int brewTime = -1;
    /**
     * 缓存的配方 ID，没有任何酿造配方时，为 null，主要通过它读取容器信息和酿造时间
     */
    private @Nullable Identifier recipeId = null;
    private @Nullable RecipeHolder<BarrelRecipe> recipeHolder = null;

    public BarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.BARREL_BE, pos, state);
    }

    public void tick(Level level) {
        // 盖子打开时，不进行任何 tick
        if (open) {
            return;
        }

        // 满级，不进任何 tick
        if (this.isMaxBrewLevel()) {
            return;
        }

        // 每 5s 检查一次
        // 不同酒桶错开检查时间，避免同时检查导致的性能问题
        int offset = this.hashCode() % CHECK_INTERVAL + CHECK_INTERVAL;
        long tick = level.getGameTime() + offset;
        if (tick % CHECK_INTERVAL != 0) {
            return;
        }

        // 如果已经开始酿造了
        if (this.isBrewing()) {
            // 自减发酵时间
            if (brewTime > 0) {
                brewTime -= CHECK_INTERVAL;
                this.refresh();
                return;
            }

            // 如果发酵时间小于等于 0，进入下一个阶段
            brewLevel = Math.min(brewLevel + 1, BREWING_FINISHED);
            // 下一次的发酵时间
            brewTime = this.getBrewTimeForLevel();
            this.refresh();
            return;
        }

        // 如果还没有开始酿造，检查是否满足开始条件，满足则进入第一个阶段
        // 先检查流体
        if (fluid.getFluidAmountMb() < MAX_FLUID_AMOUNT) {
            // 流体不符合，不发酵
            return;
        }
        // 检查配方
        BarrelRecipeContainer container = new BarrelRecipeContainer(ingredient, fluid);
        if (level instanceof ServerLevel serverLevel) {
            quickCheck.getRecipeFor(container, serverLevel).ifPresentOrElse(holder -> {
                BarrelRecipe recipe = holder.value();
                ItemStack assemble = recipe.assemble(container, level.registryAccess());
                output.setStackInSlot(0, assemble);
                recipeId = holder.id().identifier();
                recipeHolder = holder;
                brewLevel = BREWING_STARTED;
                brewTime = this.getBrewTimeForLevel();
                this.clearItemsAndFluid();
                this.refresh();
            }, () -> {
                // 没有找到配方，变成醋
                ItemStack assemble = new ItemStack(ModItems.VINEGAR, 16);
                output.setStackInSlot(0, assemble);
                recipeId = BarrelRecipeSerializer.EMPTY_RECIPE_ID;
                recipeHolder = null;
                brewLevel = BREWING_STARTED;
                brewTime = this.getBrewTimeForLevel();
                this.clearItemsAndFluid();
                this.refresh();
            });
        }
    }

    public int getBrewTimeForLevel() {
        if (this.level == null || this.recipeId == null || this.recipeId.equals(BarrelRecipeSerializer.EMPTY_RECIPE_ID)) {
            return BarrelRecipeSerializer.DEFAULT_UNIT_TIME * this.brewLevel;
        }
        // 如果已经达到最高品质了，就不需要再发酵了，返回 -1 代表不需要再发酵了
        if (this.isMaxBrewLevel()) {
            return -1;
        }
        if (this.level instanceof ServerLevel serverLevel) {
            RecipeHolder<BarrelRecipe> holder = this.resolveRecipeHolder(serverLevel);
            if (holder != null) {
                return holder.value().unitTime() * this.brewLevel;
            }
        }
        return BarrelRecipeSerializer.DEFAULT_UNIT_TIME * this.brewLevel;
    }

    public void clearItemsAndFluid() {
        // 清空物品槽和液体槽
        for (int i = 0; i < this.ingredient.getSlots(); i++) {
            if (this.ingredient.getStackInSlot(i).isEmpty()) {
                continue;
            }
            this.ingredient.setStackInSlot(i, ItemStack.EMPTY);
        }
        this.fluid.drain(this.fluid.getFluidAmountTransfer(), CustomFluidTank.FluidAction.EXECUTE);
    }

    @Override
    public boolean openLid(@Nullable LivingEntity user) {
        // 先判断当前是否处于发酵状态，发酵状态下无法打开盖子
        if (this.isBrewing()) {
            this.tip(user, "brewing_unable_to_open");
            return false;
        }
        // 切换开盖状态
        this.open = true;
        this.refresh();
        if (this.level != null) {
            // 因为盖子在酒桶上方两格，所以声音位置需要上移两格
            BlockPos pos = this.getBlockPos().above(2);
            this.level.playSound(null, pos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS);
        }
        return true;
    }

    @Override
    public boolean closeLid(@Nullable LivingEntity user) {
        // 关盖子没有限制
        this.open = false;
        this.refresh();
        if (this.level != null) {
            // 因为盖子在酒桶上方两格，所以声音位置需要上移两格
            BlockPos pos = this.getBlockPos().above(2);
            this.level.playSound(null, pos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS);
        }
        return true;
    }

    @Override
    public boolean addIngredient(@Nullable LivingEntity user, ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        // 盖子必须打开才能添加物品
        if (!open) {
            return false;
        }
        // 处于发酵状态时无法添加物品
        if (this.isBrewing()) {
            return false;
        }
        // 流体没有装满不允许添加物品
        if (fluid.getFluidAmountMb() < MAX_FLUID_AMOUNT) {
            this.tip(user, "add_ingredient_fluid_not_full");
            return false;
        }
        int count = Math.min(16, stack.getCount());
        ItemStack input = stack.copyWithCount(count);
        // 只尝试放入 16 个
        ItemStack remaining = this.addIngredientOnce(this.ingredient, input, false);
        // 如果数量发生了变化，代表成功添加了部分或全部物品
        if (remaining.getCount() < count) {
            // 不需要刷新，因为 items 内部会调用 onContentsChanged 来刷新状态
            if (user != null) {
                user.playSound(SoundEvents.ITEM_FRAME_ADD_ITEM);
            }
            stack.shrink(count - remaining.getCount());
            this.refresh();
            return true;
        }
        this.tip(user, "add_ingredient_no_space");
        return false;
    }

    /**
     * 单次投料逻辑，会优先放置到已有相同物品的槽位
     * 然后放置成功一次后，立即返回，而不是继续尝试放置，避免一次性放入过多物品
     */
    public ItemStack addIngredientOnce(IItemHandler inventory, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return stack;
        }

        // 不可堆叠的物品，直接尝试放入任意空槽
        if (!stack.isStackable()) {
            int slots = inventory.getSlots();
            for (int i = 0; i < slots; i++) {
                if (inventory.getStackInSlot(i).isEmpty()) {
                    return inventory.insertItem(i, stack, simulate);
                }
            }
            return stack;
        }

        int slots = inventory.getSlots();
        int beforeCount = stack.getCount();

        // 优先尝试合并到已有相同物品的槽位
        for (int i = 0; i < slots; i++) {
            ItemStack slot = inventory.getStackInSlot(i);
            if (ItemStack.isSameItemSameComponents(slot, stack)) {
                stack = inventory.insertItem(i, stack, simulate);
                // 放置成功一次后，立即返回，避免一次性放入过多物品
                if (stack.getCount() < beforeCount) {
                    return stack;
                }
            }
        }

        // 没有可合并的槽位，找第一个空槽放入
        if (!stack.isEmpty()) {
            for (int i = 0; i < slots; i++) {
                if (inventory.getStackInSlot(i).isEmpty()) {
                    // 放置到空槽后，立即返回，避免一次性放入过多物品
                    return inventory.insertItem(i, stack, simulate);
                }
            }
        }

        return stack;
    }

    @Override
    public boolean removeIngredient(@NotNull LivingEntity user) {
        // 盖子必须打开才能移除物品
        if (!open) {
            return false;
        }
        // 处于发酵状态时无法移除物品
        if (this.isBrewing()) {
            return false;
        }
        if (fluid.getFluidAmountMb() < MAX_FLUID_AMOUNT) {
            this.tip(user, "add_ingredient_fluid_not_full");
            return false;
        }
        // 倒序遍历物品槽，优先移除最后一个槽的物品
        for (int i = this.ingredient.getSlots() - 1; i >= 0; i--) {
            ItemStack stack = this.ingredient.getStackInSlot(i);
            // 找到一个非空的槽，移除其中的物品
            if (!stack.isEmpty()) {
                // 不需要刷新，因为 items 内部会调用 onContentsChanged 来刷新状态
                ItemStack removed = this.ingredient.extractItem(i, stack.getCount(), false);
                user.playSound(SoundEvents.ITEM_FRAME_REMOVE_ITEM);
                ItemUtils.getItemToLivingEntity(user, removed);
                this.refresh();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addFluid(@NotNull LivingEntity user, ItemStack stack) {
        // 盖子必须打开才能添加液体
        if (!open) {
            return false;
        }
        // 处于发酵状态时无法添加液体
        if (this.isBrewing()) {
            return false;
        }
        // 有物品时，必须先移除物品才能添加液体
        for (int i = 0; i < this.ingredient.getSlots(); i++) {
            if (!this.ingredient.getStackInSlot(i).isEmpty()) {
                this.tip(user, "add_fluid_ingredient_not_empty");
                return false;
            }
        }
        // 开始把容器中的流体转移到酒桶里
        boolean result = FluidUtils.emptyItem(user, stack, this.fluid, MAX_FLUID_AMOUNT);
        if (result) {
            this.refresh();
        }
        return result;
    }

    @Override
    public boolean removeFluid(@NotNull LivingEntity user, ItemStack stack) {
        // 盖子必须打开才能移除液体
        if (!open) {
            return false;
        }
        // 处于发酵状态时无法移除液体
        if (this.isBrewing()) {
            return false;
        }
        // 有物品时，必须先移除物品才能移除液体
        for (int i = 0; i < this.ingredient.getSlots(); i++) {
            if (!this.ingredient.getStackInSlot(i).isEmpty()) {
                this.tip(user, "remove_fluid_ingredient_not_empty");
                return false;
            }
        }
        // 开始把酒桶中的流体转移到容器里
        boolean result = FluidUtils.fillItem(user, stack, this.fluid, MAX_FLUID_AMOUNT);
        if (result) {
            this.refresh();
        }
        return result;
    }

    /**
     * 能否使用水龙头取出酒
     *
     * @param tapPos 水龙头所处的位置
     */
    @Override
    public boolean canTapExtract(Level level, BlockPos tapPos, LivingEntity user) {
        // 检查是否处于酿造状态
        if (!this.isBrewing()) {
            this.tip(user, "tap_extract_not_brewing");
            return false;
        }
        // 桶是不是已经空了
        if (output.getStackInSlot(0).isEmpty()) {
            this.tip(user, "tap_extract_empty");
            return false;
        }
        // 先检查水龙头下方是否是合法容器
        Block below = level.getBlockState(tapPos.below()).getBlock();
        if (below == Blocks.AIR) {
            this.tip(user, "tap_extract_empty_container");
            return false;
        }
        // 容器必须是 BlockItem
        if (!(below.asItem() instanceof BlockItem blockItem)) {
            this.tip(user, "tap_extract_invalid_container");
            return false;
        }
        if (this.recipeId == null || this.recipeId.equals(BarrelRecipeSerializer.EMPTY_RECIPE_ID)) {
            return below == ModBlocks.EMPTY_BOTTLE;
        }
        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }
        RecipeHolder<BarrelRecipe> holder = this.resolveRecipeHolder(serverLevel);
        if (holder == null) {
            return false;
        }
        return holder.value().carrier().test(blockItem.getDefaultInstance());
    }

    @Override
    public void doTapExtract(Level level, BlockPos tapPos) {
        // 检查是否处于酿造状态
        if (!this.isBrewing()) {
            return;
        }
        // 桶是不是已经空了
        if (output.getStackInSlot(0).isEmpty()) {
            return;
        }
        BlockPos below = tapPos.below();
        BlockState belowState = level.getBlockState(below);
        if (this.recipeId == null || this.recipeId.equals(BarrelRecipeSerializer.EMPTY_RECIPE_ID)) {
            if (belowState.is(ModBlocks.EMPTY_BOTTLE)) {
                this.transform(level, below, belowState, (BottleBlockItem) ModItems.VINEGAR);
            }
            return;
        }
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        RecipeHolder<BarrelRecipe> holder = this.resolveRecipeHolder(serverLevel);
        if (holder == null) {
            if (belowState.is(ModBlocks.EMPTY_BOTTLE)) {
                this.transform(level, below, belowState, (BottleBlockItem) ModItems.VINEGAR);
            }
            return;
        }
        BarrelRecipe barrelRecipe = holder.value();
        ItemStack belowStack = belowState.getBlock().asItem().getDefaultInstance();
        if (barrelRecipe.carrier().test(belowStack)) {
            this.transform(level, below, belowState, (BottleBlockItem) barrelRecipe.result().getItem());
        }
    }

    private void transform(Level level, BlockPos below, BlockState belowState, BottleBlockItem result) {
        // 取出一个酒瓶，仅用于计数
        output.extractItem(0, 1, false);

        // 将方块变成对应的酒瓶
        BlockState state = result.getBlock().defaultBlockState();
        if (state.hasProperty(HORIZONTAL_FACING) && belowState.hasProperty(HORIZONTAL_FACING)) {
            state = state.setValue(HORIZONTAL_FACING, belowState.getValue(HORIZONTAL_FACING));
        }
        level.setBlockAndUpdate(below, state);

        // 存入对应等级的酒类
        ItemStack filledStack = result.getFilledStack(this.brewLevel);
        if (level.getBlockEntity(below) instanceof DrinkBlockEntity drinkBlock) {
            drinkBlock.addItem(filledStack);
        }

        // 如果此时桶已经空了，那么就重置酒桶状态，准备下一轮酿造
        if (output.getStackInSlot(0).isEmpty()) {
            this.clearItemsAndFluid(); // 以防万一，再次清空物品槽和液体槽
            this.recipeId = null;
            this.recipeHolder = null;
            this.brewLevel = BREWING_NOT_STARTED;
            this.brewTime = -1;
            this.refresh();
        }
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput valueInput) {
        super.loadAdditional(valueInput);
        if (valueInput.contains(INGREDIENT_ITEMS_KEY) || valueInput.contains(INGREDIENT_SIZE_KEY)) {
            this.ingredient.deserializeNBT(valueInput, INGREDIENT_ITEMS_KEY, INGREDIENT_SIZE_KEY);
        } else {
            this.ingredient.deserializeNBT(valueInput);
        }
        this.normalizeIngredientInventory();
        if (valueInput.contains(OUTPUT_ITEMS_KEY) || valueInput.contains(OUTPUT_SIZE_KEY)) {
            this.output.deserializeNBT(valueInput, OUTPUT_ITEMS_KEY, OUTPUT_SIZE_KEY);
        } else {
            this.output.deserializeNBT(valueInput);
        }
        this.fluid.readFromNBT(valueInput);
        this.open = valueInput.getBooleanOr("open", true);
        this.brewLevel = valueInput.getIntOr("brew_level", BREWING_NOT_STARTED);
        this.brewTime = valueInput.getIntOr("brew_time", -1);
        this.recipeId = valueInput.getString("recipe_id").map(Identifier::parse).orElse(null);
        this.recipeHolder = null;
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        this.ingredient.serialize(valueOutput, INGREDIENT_ITEMS_KEY, INGREDIENT_SIZE_KEY);
        this.output.serialize(valueOutput, OUTPUT_ITEMS_KEY, OUTPUT_SIZE_KEY);
        this.fluid.writeToNBT(valueOutput);
        valueOutput.putBoolean("open", this.open);
        valueOutput.putInt("brew_level", this.brewLevel);
        valueOutput.putInt("brew_time", this.brewTime);
        valueOutput.putString("recipe_id", this.recipeId == null ? "" : this.recipeId.toString());
    }

    public void tip(@Nullable LivingEntity entity, String key) {
        if (entity instanceof ServerPlayer player) {
            Component message = Component.translatable("message.kaleidoscope_tavern.barrel.%s".formatted(key));
            player.connection.send(new ClientboundSetActionBarTextPacket(message));
        }
    }

    public void tipBrewInfo(@Nullable LivingEntity entity) {
        if (!(entity instanceof ServerPlayer player)) {
            return;
        }

        ItemStack result = output.getStackInSlot(0);
        Component resultText = result.getHoverName();
        Component levelText = Component.translatable("message.kaleidoscope_tavern.barrel.brew_level.%d".formatted(this.brewLevel));

        if (!this.isBrewing()) {
            Component message = Component.translatable("message.kaleidoscope_tavern.barrel.not_brewing");
            player.connection.send(new ClientboundSetActionBarTextPacket(message));
            return;
        }

        if (this.isMaxBrewLevel()) {
            Component message = Component.translatable("message.kaleidoscope_tavern.barrel.brew_info.full", resultText, result.getCount(), levelText);
            player.connection.send(new ClientboundSetActionBarTextPacket(message));
            return;
        }

        Component timeText = Component.literal(StringUtil.formatTickDuration(Math.max(this.brewTime, 0), 20.0f));
        Component message = Component.translatable("message.kaleidoscope_tavern.barrel.brew_info.next", resultText, result.getCount(), levelText, timeText);
        player.connection.send(new ClientboundSetActionBarTextPacket(message));
    }


    @Override
    public boolean isBrewing() {
        return brewLevel >= BREWING_STARTED;
    }

    @Override
    public boolean isMaxBrewLevel() {
        return brewLevel >= BREWING_FINISHED;
    }

    @Override
    public ItemStackHandler getIngredient() {
        return ingredient;
    }

    @Override
    public CustomFluidTank getFluid() {
        return fluid;
    }

    @Override
    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public int getBrewLevel() {
        return brewLevel;
    }

    @Override
    public int getBrewTime() {
        return brewTime;
    }

    @Override
    public ItemStackHandler getOutput() {
        return output;
    }

    @Override
    @Nullable
    public Identifier getRecipeId() {
        return this.recipeId;
    }

    private @Nullable RecipeHolder<BarrelRecipe> resolveRecipeHolder(ServerLevel serverLevel) {
        if (this.recipeId == null || this.recipeId.equals(BarrelRecipeSerializer.EMPTY_RECIPE_ID)) {
            this.recipeHolder = null;
            return null;
        }
        if (this.recipeHolder != null && this.recipeHolder.id().identifier().equals(this.recipeId)) {
            return this.recipeHolder;
        }
        this.recipeHolder = serverLevel.recipeAccess().getSynchronizedRecipes()
                .getAllOfType(ModRecipes.BARREL_RECIPE)
                .stream()
                .filter(holder -> holder.id().identifier().equals(this.recipeId))
                .findFirst()
                .orElse(null);
        return this.recipeHolder;
    }

    private void normalizeIngredientInventory() {
        int currentSlots = this.ingredient.getSlots();
        if (currentSlots == MAX_ITEM_SLOTS) {
            for (int i = 0; i < currentSlots; i++) {
                ItemStack slot = this.ingredient.getStackInSlot(i);
                int limit = this.ingredient.getSlotLimit(i);
                if (!slot.isEmpty() && slot.getCount() > limit) {
                    this.ingredient.setStackInSlot(i, slot.copyWithCount(limit));
                }
            }
            return;
        }

        ItemStack[] oldStacks = new ItemStack[currentSlots];
        for (int i = 0; i < currentSlots; i++) {
            oldStacks[i] = this.ingredient.getStackInSlot(i).copy();
        }

        this.ingredient.setSize(MAX_ITEM_SLOTS);
        for (ItemStack oldStack : oldStacks) {
            if (oldStack.isEmpty()) {
                continue;
            }
            ItemUtils.insertItem(this.ingredient, oldStack, false);
        }
    }

    // 换算到Fabric的液体计量单位，单位为桶，应该是4桶
    private static long toTransferAmount() {
        if (IBarrel.MAX_FLUID_AMOUNT <= 0) {
            return 0;
        }
        return (long) IBarrel.MAX_FLUID_AMOUNT * FluidConstants.BUCKET / (long) CustomFluidTank.MB_PER_BUCKET;
    }
}
