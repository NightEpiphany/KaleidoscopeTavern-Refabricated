package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PressingTubBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.util.fluids.CustomFluidTank;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class PressingTubBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<PressingTubBlock> CODEC = simpleCodec(PressingTubBlock::new);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty TILT = BooleanProperty.create("tilt");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape SHAPE = Shapes.join(
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(2, 4, 2, 14, 8, 14),
            BooleanOp.ONLY_FIRST
    );

    public static final VoxelShape TILTED_SHAPE_NORTH = Shapes.or(
            Block.box(0, 0, 0, 16, 8, 8),
            Block.box(0, 4, 4, 16, 12, 12),
            Block.box(0, 8, 8, 16, 16, 16)
    );
    public static final VoxelShape TILTED_SHAPE_SOUTH = Shapes.or(
            Block.box(0, 0, 8, 16, 8, 16),
            Block.box(0, 4, 4, 16, 12, 12),
            Block.box(0, 8, 0, 16, 16, 8)
    );
    public static final VoxelShape TILTED_SHAPE_WEST = Shapes.or(
            Block.box(0, 0, 0, 8, 8, 16),
            Block.box(4, 4, 0, 12, 12, 16),
            Block.box(8, 8, 0, 16, 16, 16)
    );
    public static final VoxelShape TILTED_SHAPE_EAST = Shapes.or(
            Block.box(8, 0, 0, 16, 8, 16),
            Block.box(4, 4, 0, 12, 12, 16),
            Block.box(0, 8, 0, 8, 16, 16)
    );

    public PressingTubBlock(Properties p) {
        super(p);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TILT, false)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public @NotNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, Level level, @NonNull BlockPos pos,
                                                @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof IPressingTub pressingTub)) {
            return InteractionResult.PASS;
        }

        // 如果是空手，尝试取出
        ItemStack itemInHand = player.getItemInHand(hand);
        if (itemInHand.isEmpty()) {
            // 潜行时一次取出一组（64 个），否则一次取出一个
            int removeCount = player.isSecondaryUseActive() ? 64 : 1;
            if (pressingTub.removeIngredient(player, removeCount)) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }

        // 然后尝试能否取出
        if (pressingTub.getResult(player, itemInHand)) {
            return InteractionResult.SUCCESS;
        }

        // 最后尝试放入
        if (pressingTub.addIngredient(itemInHand)) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void fallOn(@NonNull Level level, @NonNull BlockState blockState, @NonNull BlockPos blockPos, @NonNull Entity entity, double d) {

        // 倾斜的果盘不能踩
        if (blockState.getValue(TILT)) {
            super.fallOn(level, blockState, blockPos, entity, d);
            return;
        }

        if (entity instanceof LivingEntity livingEntity) {
            if (!(level.getBlockEntity(blockPos) instanceof IPressingTub pressingTub)) {
                return;
            }
            if (pressingTub.press(livingEntity, (float) d)) {
                return;
            }
        }

        // 如果压榨不成功，则正常掉落伤害
        super.fallOn(level, blockState, blockPos, entity, d);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NonNull BlockState pState, LootParams.@NonNull Builder pParams) {
        List<ItemStack> stacks = super.getDrops(pState, pParams);
        BlockEntity blockEntity = pParams.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof IPressingTub pressingTub) {
            ItemStack drop = pressingTub.getItems().getStackInSlot(0).copy();
            if (!drop.isEmpty()) {
                stacks.add(drop);
            }
        }
        return stacks;
    }

    @Override
    protected @NonNull BlockState updateShape(@NonNull BlockState blockState, @NonNull LevelReader levelReader, @NonNull ScheduledTickAccess scheduledTickAccess, @NonNull BlockPos blockPos, @NonNull Direction direction, @NonNull BlockPos blockPos2, @NonNull BlockState blockState2, @NonNull RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TILT, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // 倾斜的朝向，默认为点击的面的朝向
        Direction clickedFace;
        // 依据点击的位置，决定是否倾斜
        boolean isTilting;

        // 如果点击的是上方或下方，那么依据玩家朝向，放置普通的
        if (context.getClickedFace().getAxis().isVertical()) {
            clickedFace = context.getHorizontalDirection().getOpposite();
            isTilting = false;
        } else {
            clickedFace = context.getClickedFace();
            isTilting = true;
        }

        return this.defaultBlockState()
                .setValue(FACING, clickedFace)
                .setValue(TILT, isTilting)
                .setValue(WATERLOGGED, context.getLevel().isWaterAt(context.getClickedPos()));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        if (state.getValue(TILT)) {
            return switch (state.getValue(FACING)) {
                case NORTH -> TILTED_SHAPE_NORTH;
                case SOUTH -> TILTED_SHAPE_SOUTH;
                case WEST -> TILTED_SHAPE_WEST;
                case EAST -> TILTED_SHAPE_EAST;
                default -> SHAPE;
            };
        }
        return SHAPE;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new PressingTubBlockEntity(pos, state);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean hasAnalogOutputSignal(@NonNull BlockState blockState) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(@NonNull BlockState blockState, @NonNull Level level, @NonNull BlockPos blockPos, @NonNull Direction direction) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof IPressingTub pressingTub) {
            return pressingTub.getFluidAmount();
        }
        return 0;
    }

    @Override
    public @NonNull ItemStack pickupBlock(@Nullable LivingEntity livingEntity, @NonNull LevelAccessor level, @NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        ItemStack stack = SimpleWaterloggedBlock.super.pickupBlock(livingEntity, level, blockPos, blockState);
        if (!stack.isEmpty()) {
            return stack;
        }
        if (level.isClientSide()) {
            return stack;
        }

        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (!(blockEntity instanceof PressingTubBlockEntity pressingTub)) {
            return stack;
        }
        if (pressingTub.getFluidAmount() < IPressingTub.MAX_FLUID_AMOUNT) {
            return stack;
        }
        // 判断产物是否是铁桶容器的
        CustomFluidTank fluidTank = pressingTub.getFluid();
        Fluid fluid = fluidTank.getFluid();
        Item bucket = fluid.getBucket();
        if (bucket instanceof BucketItem) {
            FluidVariant variant = fluidTank.getFluidVariant();
            try (Transaction transaction = Transaction.openOuter()) {
                long extracted = fluidTank.extract(variant, IPressingTub.MAX_FLUID_AMOUNT_TRANSFER, transaction);
                if (extracted == IPressingTub.MAX_FLUID_AMOUNT_TRANSFER) {
                    transaction.commit();
                    return bucket.getDefaultInstance();
                }
            }
        }
        return stack;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
