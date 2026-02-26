package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ItemUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.fluids.PressingTubFluidTank;
import com.github.ysbbbbbb.kaleidoscopetavern.util.forge.ItemStackHandler;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@SuppressWarnings("UnstableApiUsage")
public class PressingTubBlockEntity extends BaseBlockEntity implements IPressingTub {
    private final RecipeManager.CachedCheck<Container, PressingTubRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.PRESSING_TUB_RECIPE);

    /**
     * 压榨桶的物品槽，目前只有一个槽位，用于放置被压榨的物品，最大可放入一组件（64个）物品
     */
    private final ItemStackHandler items = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            // 物品槽内容改变时，需要强制刷新状态，以便客户端同步
            refresh();
        }
    };
    /**
     * 当前压榨桶内的液体量，最大为 1000 mb
     */
    private final PressingTubFluidTank fluid = new PressingTubFluidTank(FluidConstants.BUCKET, this::refresh);

    public PressingTubBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.PRESSING_TUB_BE, pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items.deserializeNBT(tag.getCompound("items"));
        this.fluid.readFromNBT(tag.getCompound("fluid"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("items", this.items.serializeNBT());
        tag.put("fluid", this.fluid.writeToNBT(new CompoundTag()));
    }

    @Override
    public boolean addIngredient(ItemStack stack) {
        int count = stack.getCount();
        ItemStack remaining = this.items.insertItem(0, stack.copy(), false);
        if (remaining.getCount() >= count) {
            return false;
        }
        stack.shrink(count - remaining.getCount());
        if (this.level instanceof ServerLevel) {
            this.level.playSound(null,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    SoundEvents.ITEM_FRAME_ADD_ITEM,
                    SoundSource.BLOCKS,
                    0.5F + this.level.random.nextFloat(),
                    this.level.random.nextFloat() * 0.7F + 0.6F);
        }
        return true;
    }

    @Override
    public boolean removeIngredient(LivingEntity target, int count) {
        ItemStack removed = this.items.extractItem(0, count, false);
        if (removed.isEmpty()) {
            return false;
        }
        ItemUtils.getItemToLivingEntity(target, removed);
        if (this.level instanceof ServerLevel) {
            this.level.playSound(null,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    SoundEvents.ITEM_FRAME_REMOVE_ITEM,
                    SoundSource.BLOCKS,
                    0.5F + this.level.random.nextFloat(),
                    this.level.random.nextFloat() * 0.7F + 0.6F);
        }
        return true;
    }

    @Override
    public boolean press(LivingEntity target, float fallDistance) {
        if (level == null) {
            return false;
        }
        if (fallDistance < MIN_FALL_DISTANCE) {
            return false;
        }

        ItemStack stack = items.getStackInSlot(0);
        if (stack.isEmpty()) {
            // 如果有流体，播放正常压榨效果
            if (this.getFluidAmount() > 0) {
                playSuccessPressEffect(null);
            } else {
                // 没有流体，播放失败压榨效果
                playFailPressEffect(null);
            }
            return false;
        }

        SimpleContainer container = new SimpleContainer(stack);
        return this.quickCheck.getRecipeFor(container, level).map(recipe -> {
            FluidVariant fluidVariant = FluidVariant.of(recipe.getFluid());
            FluidVariant fluidInTub = this.fluid.getFluidVariant();

            if (!fluidInTub.isBlank() && !fluidVariant.equals(fluidInTub)) {
                playFailPressEffect(stack);
                // 丢出内容物并刷新状态
                if (this.dropContents()) {
                    this.refresh();
                }
                return false;
            }

            // 液体已满，无法继续压榨
            if (this.fluid.getFluidAmountTransfer() >= IPressingTub.MAX_FLUID_AMOUNT_TRANSFER) {
                playFinishedPressEffect();
                return false;
            }
            ItemStack output = recipe.assemble(container, level.registryAccess());

            // 产物为空，无法继续压榨（一般不太可能发生）
            if (output.isEmpty()) {
                playFailPressEffect(stack);
                return false;
            }

            long insertAmount = toTransferAmount(recipe.getFluidAmount());
            boolean inserted = false;
            try (Transaction transaction = Transaction.openOuter()) {
                long actual = this.fluid.insert(fluidVariant, insertAmount, transaction);
                if (actual > 0) {
                    inserted = true;
                    transaction.commit();
                }
            }
            if (!inserted) {
                playFinishedPressEffect();
                return false;
            }
            this.items.extractItem(0, 1, false);

            playSuccessPressEffect(stack);
            return true;
        }).orElseGet(() -> {
            playFailPressEffect(stack);
            // 没有找到配方，丢出内容物并刷新状态
            if (this.dropContents()) {
                this.refresh();
            }
            return false;
        });
    }

    /**
     * 成功压榨时，显示对应物品破碎的粒子，粘液块的音效
     */
    private void playSuccessPressEffect(@Nullable ItemStack stack) {
        if (this.level instanceof ServerLevel serverLevel) {
            this.level.playSound(null,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    SoundEvents.SLIME_BLOCK_FALL,
                    SoundSource.BLOCKS,
                    0.5F + this.level.random.nextFloat(),
                    this.level.random.nextFloat() * 0.3F + 0.7F);

            if (stack == null) {
                serverLevel.sendParticles(ParticleTypes.RAIN,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 0.5,
                        worldPosition.getZ() + 0.5,
                        10, 0.25, 0.2, 0.25, 0.05);
            } else {
                ItemParticleOption option = new ItemParticleOption(ParticleTypes.ITEM, stack);
                serverLevel.sendParticles(option,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 0.5,
                        worldPosition.getZ() + 0.5,
                        10, 0.25, 0.2, 0.25, 0.05);
            }
        }
    }

    /**
     * 压榨失败时，显示对应物品破碎的粒子，木板的音效
     */
    private void playFailPressEffect(@Nullable ItemStack stack) {
        if (this.level instanceof ServerLevel serverLevel) {
            this.level.playSound(null,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    SoundEvents.WOOD_FALL,
                    SoundSource.BLOCKS,
                    0.5F + this.level.random.nextFloat(),
                    this.level.random.nextFloat() * 0.3F + 0.7F);

            if (stack == null) {
                BlockState state = ModBlocks.PRESSING_TUB.defaultBlockState();
                BlockParticleOption option = new BlockParticleOption(ParticleTypes.BLOCK, state);
                serverLevel.sendParticles(option,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 0.5,
                        worldPosition.getZ() + 0.5,
                        10, 0.25, 0.2, 0.25, 0.05);
            } else {
                ItemParticleOption option = new ItemParticleOption(ParticleTypes.ITEM, stack);
                serverLevel.sendParticles(option,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 0.5,
                        worldPosition.getZ() + 0.5,
                        10, 0.25, 0.2, 0.25, 0.05);
            }
        }
    }

    /**
     * 完成压榨后再踩踏，显示雨水粒子，蜂蜜块的音效
     */
    private void playFinishedPressEffect() {
        if (this.level instanceof ServerLevel serverLevel) {
            this.level.playSound(null,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    SoundEvents.HONEY_BLOCK_HIT,
                    SoundSource.BLOCKS,
                    0.5F + this.level.random.nextFloat(),
                    this.level.random.nextFloat() * 0.3F + 0.7F);

            serverLevel.sendParticles(ParticleTypes.RAIN,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.5,
                    worldPosition.getZ() + 0.5,
                    10, 0.25, 0.2, 0.25, 0.05);
        }
    }

    @Override
    public boolean getResult(LivingEntity target, ItemStack carriedStack) {
        if (level == null) {
            return false;
        }
        // 必须完全满液体才能取出产物
        if (this.fluid.getFluidAmountTransfer() < IPressingTub.MAX_FLUID_AMOUNT_TRANSFER) {
            return false;
        }

        FluidVariant fluidVariant = this.fluid.getFluidVariant();
        if (fluidVariant.isBlank()) {
            return false;
        }

        ItemStack copy = carriedStack.copyWithCount(1);
        ContainerItemContext context = ContainerItemContext.withConstant(copy);
        Storage<FluidVariant> itemStorage = context.find(FluidStorage.ITEM);
        if (itemStorage == null) {
            return false;
        }

        try (Transaction transaction = Transaction.openOuter()) {
            long extracted = this.fluid.extract(fluidVariant, IPressingTub.MAX_FLUID_AMOUNT_TRANSFER, transaction);
            if (extracted != IPressingTub.MAX_FLUID_AMOUNT_TRANSFER) {
                return false;
            }
            long inserted = itemStorage.insert(fluidVariant, extracted, transaction);
            if (inserted != extracted) {
                return false;
            }
            transaction.commit();
        }

        ItemVariant resultVariant = context.getItemVariant();
        ItemStack resultStack = resultVariant.toStack((int) Math.min(Integer.MAX_VALUE, context.getAmount()));

        if (!(target instanceof Player player) || !player.isCreative()) {
            carriedStack.shrink(1);
        }
        ItemUtils.getItemToLivingEntity(target, resultStack);

        SoundEvent sound = getBucketFillSound(fluidVariant);
        if (sound != null) {
            target.playSound(sound);
        }
        return true;
    }

    public boolean dropContents() {
        if (level == null) {
            return false;
        }
        ItemStack stack = items.extractItem(0, 64, false);
        if (stack.isEmpty()) {
            return false;
        }

        int totalCount = stack.getCount();
        int directionCount = Math.min(8, totalCount);

        // 8个水平弹射方向 (dx, dz)，从东方向顺时针排列，对角分量已归一化
        double s = 1.0 / Math.sqrt(2.0);
        double[][] dirs = {
                {1.0, 0.0},  // 东
                {s, s},  // 东南
                {0.0, 1.0},  // 南
                {-s, s},  // 西南
                {-1.0, 0.0},  // 西
                {-s, -s},  // 西北
                {0.0, -1.0},  // 北
                {s, -s},  // 东北
        };

        int base = totalCount / directionCount;
        int remainder = totalCount % directionCount;

        for (int i = 0; i < directionCount; i++) {
            int count = base + (i < remainder ? 1 : 0);
            ItemStack split = stack.copyWithCount(count);

            double dx = dirs[i][0];
            double dz = dirs[i][1];

            // 生成位置：方块中心偏向弹射方向，加少量随机扰动
            double spawnX = worldPosition.getX() + 0.5 + dx * 0.3 + Mth.nextDouble(level.random, -0.05, 0.05);
            double spawnY = worldPosition.getY() + 0.5 + Mth.nextDouble(level.random, 0, 0.1);
            double spawnZ = worldPosition.getZ() + 0.5 + dz * 0.3 + Mth.nextDouble(level.random, -0.05, 0.05);

            // 速度：沿弹射方向加随机扰动，带少量向上分量
            double velX = dx * 0.15 + Mth.nextDouble(level.random, -0.02, 0.02);
            double velY = 0.1 + Mth.nextDouble(level.random, -0.02, 0.02);
            double velZ = dz * 0.15 + Mth.nextDouble(level.random, -0.02, 0.02);

            this.popResource(level, () -> new ItemEntity(level, spawnX, spawnY, spawnZ, split, velX, velY, velZ), split);
        }

        return true;
    }

    private void popResource(Level level, Supplier<ItemEntity> supplier, ItemStack stack) {
        if (!level.isClientSide && !stack.isEmpty() && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            ItemEntity entity = supplier.get();
            entity.setDefaultPickUpDelay();
            level.addFreshEntity(entity);
        }
    }

    @Override
    public ItemStackHandler getItems() {
        return items;
    }

    @Override
    public PressingTubFluidTank getFluid() {
        return fluid;
    }

    @Override
    public int getFluidAmount() {
        return this.fluid.getFluidAmountMb();
    }

    private static long toTransferAmount(int milliBuckets) {
        if (milliBuckets <= 0) {
            return 0;
        }
        return (long) milliBuckets * FluidConstants.BUCKET / (long) PressingTubFluidTank.MB_PER_BUCKET;
    }

    private static SoundEvent getBucketFillSound(FluidVariant variant) {
        if (variant.isBlank()) {
            return null;
        }
        return FluidVariantAttributes.getFillSound(variant);
    }
}
