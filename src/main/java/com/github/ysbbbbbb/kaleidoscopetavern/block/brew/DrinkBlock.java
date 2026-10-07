package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.DrinkBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import com.github.ysbbbbbb.kaleidoscopetavern.item.DrinkBlockItem;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ItemUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class DrinkBlock extends BottleBlock implements EntityBlock {
    protected final IntegerProperty countProperty;
    protected final int maxCount;
    protected final EnumMap<Direction, VoxelShape>[] shapes;
    protected final String id;

    @SuppressWarnings("all")
    @Deprecated(forRemoval = true)
    public DrinkBlock(String id, boolean irregular, int maxCount, VoxelShape... shapes) {
        this(id, maxCount, shapes);
    }

    @SuppressWarnings("unchecked")
    public DrinkBlock(String id, int maxCount, VoxelShape... shapes) {
        super(id);
        this.id = id;
        this.maxCount = maxCount;
        this.countProperty = IntegerProperty.create("count", 1, maxCount);
        this.shapes = new EnumMap[shapes.length];
        for (int i = 0; i < shapes.length; i++) {
            this.shapes[i] = VoxelShapeUtils.horizontalShapes(shapes[i]);
        }

        // 重置一遍 BlockState，因为在父类 Block 中已经创建了一个默认的 BlockStateDefinition
        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createCountBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(countProperty, 1)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    public String getId() {
        return id;
    }

    public boolean tryIncreaseCount(Level level, BlockPos pos, BlockState state, ItemStack stack) {
        int count = state.getValue(this.countProperty);
        if (count < this.maxCount) {
            if (level.getBlockEntity(pos) instanceof DrinkBlockEntity be) {
                if (be.addItem(stack)) {
                    be.refresh();
                }
            }
            level.setBlockAndUpdate(pos, state.cycle(this.countProperty));
            return true;
        }
        return false;
    }

    @Override
    public @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state,
                                                @NonNull Level level, @NonNull BlockPos pos,
                                                @NonNull Player player, @NonNull InteractionHand hand,
                                                @NonNull BlockHitResult hitResult) {
        if (!stack.isEmpty() || !player.getItemInHand(hand).isEmpty()) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }

        // 只在服务端修改方块实体和方块状态，避免客户端预测造成物品丢失。
        if (!level.isClientSide()) {
            ItemStack removeItem = ItemStack.EMPTY;
            if (level.getBlockEntity(pos) instanceof DrinkBlockEntity be) {
                removeItem = be.removeItem();
                be.refresh();
            }
            // 兼容旧存档中未写入方块实体的酒瓶。
            if (removeItem.isEmpty()) {
                removeItem = new ItemStack(this.asItem());
            }

            ItemUtils.giveItemToPlayer(player, removeItem);
            level.playSound(null, pos, SoundEvents.GLASS_PLACE.value(), SoundSource.BLOCKS);

            int count = state.getValue(this.countProperty);
            if (count > 1) {
                level.setBlockAndUpdate(pos, state.setValue(this.countProperty, count - 1));
            } else {
                level.removeBlock(pos, false);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void setPlacedBy(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state,
                            @Nullable LivingEntity placer, @NonNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof DrinkBlockEntity be) {
            if (be.addItem(stack)) {
                be.refresh();
            }
        }
    }

    @Override
    public void onProjectileHit(Level level, @NonNull BlockState state, @NonNull BlockHitResult hit, @NonNull Projectile projectile) {
        // 获取其中所含的效果等级最高的酒
        if (level.isClientSide()) {
            super.onProjectileHit(level, state, hit, projectile);
            return;
        }

        BlockPos pos = hit.getBlockPos();
        if (!(level.getBlockEntity(pos) instanceof DrinkBlockEntity be)) {
            super.onProjectileHit(level, state, hit, projectile);
            return;
        }

        int maxBrewLevel = 0;
        for (int i = 0; i < be.getItems().size(); i++) {
            ItemStack stack = be.getItems().get(i);
            if (!stack.isEmpty()) {
                int brewLevel = BottleBlockItem.getBrewLevel(stack);
                if (brewLevel > maxBrewLevel) {
                    maxBrewLevel = brewLevel;
                }
            }
        }

        // 生成药水云
        if (maxBrewLevel > 0 && this.asItem() instanceof DrinkBlockItem item) {
            item.makeThrownPotion(level, pos.getX(), pos.getY(), pos.getZ(), maxBrewLevel, projectile.getOwner());
        }

        super.onProjectileHit(level, state, hit, projectile);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NonNull BlockState state, LootParams.@NonNull Builder params) {
        List<ItemStack> stacks = new ArrayList<>();
        BlockEntity blockEntity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof DrinkBlockEntity be) {
            for (ItemStack stack : be.getItems()) {
                if (!stack.isEmpty()) {
                    stacks.add(stack.copy());
                }
            }
        }
        // 酒类方块没有独立的方块战利品表；缺少实体内容时按状态数量兜底掉落。
        int count = state.getValue(this.countProperty);
        while (stacks.size() < count) {
            stacks.add(new ItemStack(this.asItem()));
        }
        return stacks;
    }

    protected void createCountBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, countProperty, WATERLOGGED);
    }

    @Override
    public @NotNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        if (this.shapes.length == 0) {
            return super.getShape(state, level, pos, context);
        }
        int count = state.getValue(this.countProperty);
        if (count > this.shapes.length) {
            count = this.shapes.length;
        }
        Direction direction = state.getValue(FACING);
        return this.shapes[count - 1].getOrDefault(direction, super.getShape(state, level, pos, context));
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new DrinkBlockEntity(pos, state);
    }

    public IntegerProperty getCountProperty() {
        return countProperty;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
        private int maxCount;
        private VoxelShape[] shapes;
        private String id = "";

        /**
         * @deprecated 现在通过 Item Tag 来决定了，不应当再使用此方法
         */
        @Deprecated(forRemoval = true)
        public Builder irregular() {
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder maxCount(int maxCount) {
            this.maxCount = maxCount;
            return this;
        }

        public Builder shapes(VoxelShape... shapes) {
            this.shapes = shapes;
            return this;
        }

        public Block build() {
            return new DrinkBlock(id, maxCount, shapes);
        }
    }
}
