package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IBarrel;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.container.BarrelRecipeContainer;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.BarrelRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer.BarrelRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopetavern.util.FluidUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class BarrelBlockEntity extends BaseBlockEntity implements IBarrel {
    /**
     * 酒桶检查时间，每 5s 检查一次，选取最接近的质数，避免与其他周期性事件同时发生，导致性能问题
     */
    private static final int CHECK_INTERVAL = 97;

    /**
     * 配方缓存
     */
    private final RecipeManager.CachedCheck<BarrelRecipeContainer, BarrelRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.BARREL_RECIPE);

    /**
     * 酒桶的物品槽，大小固定为 4 组
     */
    private final ItemStackHandler items = new ItemStackHandler(MAX_ITEM_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            // 物品槽内容改变时，需要强制刷新状态，以便客户端同步
            refresh();
        }
    };
    /**
     * 酒桶的液体槽，大小固定为 4 桶
     */
    private final FluidTank fluid = new FluidTank(MAX_FLUID_AMOUNT) {
        @Override
        protected void onContentsChanged() {
            // 液体槽内容改变时，需要强制刷新状态，以便客户端同步
            refresh();
        }
    };
    /**
     * 酒桶开盖状态，只有关闭盖子才会进行发酵判定
     */
    private boolean open = true;
    /**
     * 发酵等级，没有开始酿造时为 0，每过单位时间增加 1，达到 7 时为最高品质
     */
    private int brewLevel = BREWING_NOT_STARTED;
    /**
     * 当前正在进行的酿造配方 ID，只有在发酵过程中才会有值，用于快捷访问配方信息
     */
    private @Nullable ResourceLocation recipeId;
    /**
     * 到达下一个阶段的剩余时间，单位为 tick，每过一个 tick 减少 1，当达到 0 时进入下一个阶段
     */
    private int brewTime = -1;

    public BarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.BARREL_BE.get(), pos, state);
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
        if (fluid.getFluidAmount() < MAX_FLUID_AMOUNT) {
            // 流体不符合，不发酵
            return;
        }
        // 检查配方
        BarrelRecipeContainer container = new BarrelRecipeContainer(items, fluid);
        var recipeOpt = quickCheck.getRecipeFor(container, level);
        // 配方不符，发酵，但是 recipe id 为 EMPTY_RECIPE_ID
        recipeId = recipeOpt.map(BarrelRecipe::id).orElse(BarrelRecipeSerializer.EMPTY_RECIPE_ID);
        brewLevel = BREWING_STARTED;
        brewTime = this.getBrewTimeForLevel();
        this.clearItemsAndFluid();
        this.refresh();
    }

    public int getBrewTimeForLevel() {
        if (this.recipeId == null) {
            return BarrelRecipeSerializer.DEFAULT_UNIT_TIME * this.brewLevel;
        }
        // 如果已经达到最高品质了，就不需要再发酵了，返回 -1 代表不需要再发酵了
        if (this.isMaxBrewLevel()) {
            return -1;
        }
        if (this.level instanceof ServerLevel serverLevel) {
            var recipeOpt = serverLevel.getRecipeManager().byKey(this.recipeId);
            if (recipeOpt.isPresent() && recipeOpt.get() instanceof BarrelRecipe barrelRecipe) {
                return barrelRecipe.unitTime() * this.brewLevel;
            }
        }
        return BarrelRecipeSerializer.DEFAULT_UNIT_TIME * this.brewLevel;
    }

    public void clearItemsAndFluid() {
        // 清空物品槽和液体槽
        for (int i = 0; i < this.items.getSlots(); i++) {
            if (this.items.getStackInSlot(i).isEmpty()) {
                continue;
            }
            this.items.setStackInSlot(i, ItemStack.EMPTY);
        }
        this.fluid.drain(this.fluid.getFluidAmount(), FluidTank.FluidAction.EXECUTE);
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
        // 盖子必须打开才能添加物品
        if (!open) {
            return false;
        }
        // 处于发酵状态时无法添加物品
        if (this.isBrewing()) {
            return false;
        }
        // 流体没有装满不允许添加物品
        if (fluid.getFluidAmount() < MAX_FLUID_AMOUNT) {
            this.tip(user, "add_ingredient_fluid_not_full");
            return false;
        }
        int count = stack.getCount();
        ItemStack remaining = ItemHandlerHelper.insertItemStacked(this.items, stack.copy(), false);
        // 如果数量发生了变化，代表成功添加了部分或全部物品
        if (remaining.getCount() < count) {
            // 不需要刷新，因为 items 内部会调用 onContentsChanged 来刷新状态
            if (user != null) {
                user.playSound(SoundEvents.ITEM_FRAME_ADD_ITEM);
            }
            stack.shrink(count - remaining.getCount());
            return true;
        }
        this.tip(user, "add_ingredient_no_space");
        return false;
    }

    @Override
    public boolean removeIngredient(LivingEntity user) {
        // 盖子必须打开才能移除物品
        if (!open) {
            return false;
        }
        // 处于发酵状态时无法移除物品
        if (this.isBrewing()) {
            return false;
        }
        // 倒序遍历物品槽，优先移除最后一个槽的物品
        for (int i = this.items.getSlots() - 1; i >= 0; i--) {
            ItemStack stack = this.items.getStackInSlot(i);
            // 找到一个非空的槽，移除其中的物品
            if (!stack.isEmpty()) {
                // 不需要刷新，因为 items 内部会调用 onContentsChanged 来刷新状态
                ItemStack removed = this.items.extractItem(i, stack.getCount(), false);
                user.playSound(SoundEvents.ITEM_FRAME_REMOVE_ITEM);
                ItemUtils.getItemToLivingEntity(user, removed);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addFluid(LivingEntity user, ItemStack stack) {
        // 盖子必须打开才能添加液体
        if (!open) {
            return false;
        }
        // 处于发酵状态时无法添加液体
        if (this.isBrewing()) {
            return false;
        }
        // 有物品时，必须先移除物品才能添加液体
        for (int i = 0; i < this.items.getSlots(); i++) {
            if (!this.items.getStackInSlot(i).isEmpty()) {
                this.tip(user, "add_fluid_ingredient_not_empty");
                return false;
            }
        }
        // 开始把容器中的流体转移到酒桶里
        return FluidUtils.emptyItem(user, stack, this.fluid, MAX_FLUID_AMOUNT);
    }

    @Override
    public boolean removeFluid(LivingEntity user, ItemStack stack) {
        // 盖子必须打开才能移除液体
        if (!open) {
            return false;
        }
        // 处于发酵状态时无法移除液体
        if (this.isBrewing()) {
            return false;
        }
        // 有物品时，必须先移除物品才能移除液体
        for (int i = 0; i < this.items.getSlots(); i++) {
            if (!this.items.getStackInSlot(i).isEmpty()) {
                this.tip(user, "remove_fluid_ingredient_not_empty");
                return false;
            }
        }
        // 开始把酒桶中的流体转移到容器里
        return FluidUtils.fillItem(user, stack, this.fluid, MAX_FLUID_AMOUNT);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items.deserializeNBT(tag.getCompound("items"));
        this.fluid.readFromNBT(tag.getCompound("fluid"));
        this.open = tag.getBoolean("open");
        this.brewLevel = tag.getInt("brew_level");

        if (tag.contains("recipe_id")) {
            this.recipeId = new ResourceLocation(tag.getString("recipe_id"));
        } else {
            this.recipeId = null;
        }

        this.brewTime = tag.getInt("brew_time");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("items", this.items.serializeNBT());
        tag.put("fluid", this.fluid.writeToNBT(new CompoundTag()));
        tag.putBoolean("open", this.open);
        tag.putInt("brew_level", this.brewLevel);

        if (this.recipeId != null) {
            tag.putString("recipe_id", this.recipeId.toString());
        }

        tag.putInt("brew_time", this.brewTime);
    }

    public void tip(@Nullable LivingEntity entity, String key) {
        if (entity instanceof ServerPlayer player) {
            Component message = Component.translatable("message.kaleidoscope_tavern.barrel.%s".formatted(key));
            player.sendSystemMessage(message);
        }
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition.offset(-2, 0, -2), worldPosition.offset(2, 3, 2));
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
    public ItemStackHandler getItems() {
        return items;
    }

    @Override
    public FluidTank getFluid() {
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
    public void setBrewLevel(int brewLevel) {
        this.brewLevel = brewLevel;
    }

    @Nullable
    @Override
    public ResourceLocation getRecipeId() {
        return recipeId;
    }

    @Override
    public void setRecipeId(@Nullable ResourceLocation recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public int getBrewTime() {
        return brewTime;
    }

    @Override
    public void setBrewTime(int brewTime) {
        this.brewTime = brewTime;
    }
}
