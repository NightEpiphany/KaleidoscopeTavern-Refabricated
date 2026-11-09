package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.BarrelBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.util.fluids.FluidUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("deprecation")
public class BarrelBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    /**
     * 对应酒桶的上中下三层
     */
    public static final EnumProperty<AttachFace> LAYER = EnumProperty.create("layer", AttachFace.class);
    /**
     * 对应每层的序号，也就是从左往右，从上往下，0 - 8
     */
    public static final IntegerProperty INDEX = IntegerProperty.create("index", 0, 8);

    // 中间位置
    private static final VoxelShape SHAPE_FULL = Block.box(0, 0, 0, 16, 16, 16);
    // 南北朝向桶身左列
    private static final VoxelShape SHAPE_CUT_LEFT = Block.box(4, 0, 0, 16, 16, 16);
    // 南北朝向桶身右列
    private static final VoxelShape SHAPE_CUT_RIGHT = Block.box(0, 0, 0, 12, 16, 16);
    // 东西朝向桶身近行
    private static final VoxelShape SHAPE_CUT_NEAR = Block.box(0, 0, 4, 16, 16, 16);
    // 东西朝向桶身远行
    private static final VoxelShape SHAPE_CUT_FAR = Block.box(0, 0, 0, 16, 16, 12);

    private static final AttachFace[] LAYERS = {AttachFace.FLOOR, AttachFace.WALL, AttachFace.CEILING};

    public BarrelBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.5F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .pushReaction(PushReaction.BLOCK)
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LAYER, AttachFace.FLOOR)
                .setValue(WATERLOGGED, false)
                .setValue(INDEX, 4));
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState blockState, @NotNull Direction direction, @NotNull BlockState blockState2, @NotNull LevelAccessor levelAccessor, @NotNull BlockPos blockPos, @NotNull BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player,
                                          @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }
        BarrelBlockEntity barrelEntity = getBarrelEntity(level, pos, state);
        if (barrelEntity == null) {
            return InteractionResult.PASS;
        }
        // 点击桶身时优先放置方块，避免状态查询拦截水龙头等方块物品。
        if (!isCeiling(state) && player.getMainHandItem().getItem() instanceof BlockItem) {
            return InteractionResult.PASS;
        }
        // 只有顶层可以交互
        if (isCeiling(state)) {
            if (!barrelEntity.isOpen()) {
                // 如果是关着的，此时点击顶层会尝试开盖
                return barrelEntity.openLid(player) ? InteractionResult.SUCCESS : InteractionResult.PASS;
            }

            boolean clickedLid = isLid(state);
            ItemStack itemInHand = player.getItemInHand(hand);

            // 如果是空手，且没有点击中心，则尝试关盖
            if (itemInHand.isEmpty() && !clickedLid) {
                return barrelEntity.closeLid(player) ? InteractionResult.SUCCESS : InteractionResult.PASS;
            }

            // 只有点击中心，才能进行添加物品或流体的交互
            if (clickedLid) {
                // 如果拿的是流体容器
                if (FluidUtils.isFluidContainer(itemInHand)) {
                    if (barrelEntity.addFluid(player, itemInHand)) {
                        return InteractionResult.SUCCESS;
                    }
                    // 如果倒出失败了，再尝试从酒桶里装流体到容器里
                    if (barrelEntity.removeFluid(player, itemInHand)) {
                        return InteractionResult.SUCCESS;
                    }
                    // 流体容器不可作为原料输入
                    return InteractionResult.PASS;
                }

                // 其他情况，有物品，则尝试添加
                if (!itemInHand.isEmpty() && barrelEntity.addIngredient(player, itemInHand)) {
                    return InteractionResult.SUCCESS;
                }

                // 没有物品，则尝试取出
                if (itemInHand.isEmpty() && barrelEntity.removeIngredient(player)) {
                    return InteractionResult.SUCCESS;
                }
            }
        } else {
            barrelEntity.tipBrewInfo(player);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    /**
     * 判断是否为顶盖，只有顶盖可以添加物品和流体交互
     */
    private boolean isLid(BlockState state) {
        return state.getValue(LAYER) == AttachFace.CEILING && state.getValue(INDEX) == 4;
    }

    /**
     * 判断是否为顶部，用于开盖和关盖判断
     */
    private boolean isCeiling(BlockState state) {
        return state.getValue(LAYER) == AttachFace.CEILING;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlocks.BARREL_BE,
                (levelIn, pos, stateIn, barrel) -> barrel.tick(levelIn));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        boolean hasWater = context.getLevel().getFluidState(pos).getType() == Fluids.WATER;
        // 需要 3 格高度
        if (pos.getY() > level.getMaxBuildHeight() - 3) {
            return null;
        }

        // 检查 3x3x3 范围内是否有足够空间
        for (int y = 0; y < 3; y++) {
            for (int z = -1; z <= 1; z++) {
                for (int x = -1; x <= 1; x++) {
                    if (x == 0 && y == 0 && z == 0) {
                        continue;
                    }
                    BlockPos checkPos = pos.offset(x, y, z);
                    if (!level.getBlockState(checkPos).canBeReplaced(context)) {
                        return null;
                    }
                }
            }
        }

        Direction facing = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(WATERLOGGED, hasWater)
                .setValue(LAYER, AttachFace.FLOOR)
                .setValue(INDEX, 4);
    }

    @Override
    public void setPlacedBy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity entity, @NotNull ItemStack stack) {
        if (level.isClientSide) {
            return;
        }

        Direction facing = state.getValue(FACING);

        for (int y = 0; y < 3; y++) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    // 跳过原点（已由放置逻辑处理）
                    if (y == 0 && row == 1 && col == 1) {
                        continue;
                    }
                    int index = row * 3 + col;
                    BlockPos targetPos = pos.offset(col - 1, y, row - 1);
                    boolean hasWater = level.getFluidState(targetPos).getType() == Fluids.WATER;
                    level.setBlockAndUpdate(targetPos, this.defaultBlockState()
                            .setValue(FACING, facing)
                            .setValue(WATERLOGGED, hasWater)
                            .setValue(LAYER, LAYERS[y])
                            .setValue(INDEX, index));
                }
            }
        }
    }

    @Override
    public void playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        handleRemove(level, pos, state, player);
        super.playerWillDestroy(level, pos, state, player);
    }

    private static boolean isBarrelPart(BlockState state) {
        return state.getBlock() instanceof BarrelBlock && state.hasProperty(LAYER) && state.hasProperty(INDEX);
    }

    @Override
    public void wasExploded(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull Explosion explosion) {
        BlockState state = level.getBlockState(blockPos);
        if (isBarrelPart(state)) {
            handleRemove(level, blockPos, state, null);
        } else {
            cleanupNearbyBarrelParts(level, blockPos);
        }
        super.wasExploded(level, blockPos, explosion);
    }

    public static BlockPos getOriginPos(BlockPos pos, BlockState state) {
        if (!isBarrelPart(state)) {
            return pos;
        }
        AttachFace layer = state.getValue(LAYER);
        int index = state.getValue(INDEX);

        // 根据当前方块的 LAYER 和 INDEX 反推原点位置（FLOOR 层中心, INDEX 4）
        int yOffset = layer == AttachFace.FLOOR ? 0 : layer == AttachFace.WALL ? 1 : 2;
        int col = index % 3;
        int row = index / 3;

        // 返回原点位置
        return pos.offset(-(col - 1), -yOffset, -(row - 1));
    }

    @Nullable
    public static BarrelBlockEntity getBarrelEntity(Level level, BlockPos clickPos, BlockState clickState) {
        if (isBarrelPart(clickState)) {
            BlockPos origin = getOriginPos(clickPos, clickState);
            // 获取原点位置的 BlockEntity，并检查是否为 BarrelBlockEntity
            if (level.getBlockEntity(origin) instanceof BarrelBlockEntity barrelEntity) {
                return barrelEntity;
            }
        }
        return null;
    }

    private static void handleRemove(Level level, BlockPos pos, BlockState state, @Nullable Player player) {
        if (level.isClientSide || !isBarrelPart(state)) {
            return;
        }

        boolean drop = player == null || !player.isCreative();
        BlockPos origin = getOriginPos(pos, state);

        // 破坏 3x3x3 范围
        destroyByOrigin(level, origin, drop);
    }

    private static void cleanupNearbyBarrelParts(Level level, BlockPos centerPos) {
        Set<BlockPos> origins = new HashSet<>();
        for (BlockPos checkPos : BlockPos.betweenClosed(centerPos.offset(-1, -1, -1), centerPos.offset(1, 1, 1))) {
            BlockState checkState = level.getBlockState(checkPos);
            if (isBarrelPart(checkState)) {
                origins.add(getOriginPos(checkPos, checkState));
            }
        }
        for (BlockPos origin : origins) {
            destroyByOrigin(level, origin, true);
        }
    }

    private static void destroyByOrigin(Level level, BlockPos origin, boolean drop) {
        for (int y = 0; y < 3; y++) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    BlockPos targetPos = origin.offset(c - 1, y, r - 1);
                    BlockState targetState = level.getBlockState(targetPos);
                    if (!isBarrelPart(targetState)) {
                        continue;
                    }
                    if (!getOriginPos(targetPos, targetState).equals(origin)) {
                        continue;
                    }
                    level.destroyBlock(targetPos, drop);
                }
            }
        }
    }

    @Override
    public @NotNull List<ItemStack> getDrops(BlockState state, LootParams.@NotNull Builder builder) {
        // 仅原点方块（FLOOR 层中心）掉落物品，避免重复掉落
        if (state.getValue(LAYER) == AttachFace.FLOOR && state.getValue(INDEX) == 4) {
            return super.getDrops(state, builder);
        }
        return Collections.emptyList();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LAYER, INDEX, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, BlockState state) {
        // 仅底层中间方块附加 BlockEntity
        AttachFace layer = state.getValue(LAYER);
        int index = state.getValue(INDEX);
        if (layer == AttachFace.FLOOR && index == 4) {
            return new BarrelBlockEntity(pos, state);
        }
        return null;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Direction facing = state.getValue(FACING);
        int index = state.getValue(INDEX);
        int col = index % 3;
        int row = index / 3;
        return switch (facing) {
            case NORTH, SOUTH -> col == 0 ? SHAPE_CUT_LEFT : col == 2 ? SHAPE_CUT_RIGHT : SHAPE_FULL;
            case WEST, EAST -> row == 0 ? SHAPE_CUT_NEAR : row == 2 ? SHAPE_CUT_FAR : SHAPE_FULL;
            default -> SHAPE_FULL;
        };
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        // 仅底层中间方块能显示
        AttachFace layer = state.getValue(LAYER);
        int index = state.getValue(INDEX);
        if (layer == AttachFace.FLOOR && index == 4) {
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }
        return RenderShape.INVISIBLE;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
