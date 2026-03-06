package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.SandwichBoardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.TextBlockEntity;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SandwichBoardBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<SandwichBoardBlock> CODEC = simpleCodec(SandwichBoardBlock::new);
    public static final Map<Item, SandwichBoardBlock> TRANSFORM_MAP = Maps.newHashMap();

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape TOP_SHAPE = Block.box(2, -16, 2, 14, 6, 14);
    public static final VoxelShape BOTTOM_SHAPE = Block.box(2, 0, 2, 14, 22, 14);

    /**
     * 右键交互，可以变成此变种展板的物品。
     */
    private final List<Item> transformItems;
    private @Nullable List<String> transformItemNames;

    public SandwichBoardBlock(Properties properties, Item... transformItems) {
        super(properties
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, Half.BOTTOM)
                .setValue(WATERLOGGED, false));
        this.transformItems = List.of(transformItems);
        this.transformItems.forEach(item -> TRANSFORM_MAP.put(item, this));
    }

    public @Nullable List<String> getTransformItemNames() {
        return transformItemNames;
    }

    @Override
    public @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, BlockState state, Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        Half half = state.getValue(HALF);
        BlockPos clickedPos = half == Half.BOTTOM ? pos : pos.below();
        if (level.getBlockEntity(clickedPos) instanceof SandwichBoardBlockEntity sandwichBlock) {
            // 优先判断是否可变样式
            Item item = player.getItemInHand(hand).getItem();
            // 如果在其他变种物品列表中，则变化种类
            if (TRANSFORM_MAP.containsKey(item) && !transformItems.contains(item)) {
                BlockState transform = TRANSFORM_MAP.get(item)
                        .defaultBlockState()
                        .setValue(FACING, state.getValue(FACING))
                        .setValue(HALF, Half.BOTTOM)
                        .setValue(WATERLOGGED, state.getValue(WATERLOGGED));

                // 复制 BlockEntity 数据
                CompoundTag tag = sandwichBlock.saveWithoutMetadata(level.registryAccess());

                // 设置上下两个部分
                level.setBlockAndUpdate(clickedPos, transform);
                level.setBlockAndUpdate(clickedPos.above(), transform.setValue(HALF, Half.TOP));

                // 粘贴 BlockEntity 数据
                BlockEntity blockEntity = level.getBlockEntity(clickedPos);
                if (blockEntity instanceof SandwichBoardBlockEntity be) {
                    be.loadAdditional(TagValueInput.create(ProblemReporter.DISCARDING, level.registryAccess(), tag));
                    be.refresh();
                }

                level.playSound(null, pos, SoundType.GRASS.getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }

                return InteractionResult.SUCCESS;
            } else {
                // 否则打开界面
                return TextBlockEntity.onItemUse(level, sandwichBlock, player, hand);
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected @NonNull BlockState updateShape(@NonNull BlockState blockState, @NonNull LevelReader levelReader, @NonNull ScheduledTickAccess scheduledTickAccess, @NonNull BlockPos blockPos, @NonNull Direction direction, @NonNull BlockPos blockPos2, @NonNull BlockState blockState2, @NonNull RandomSource randomSource) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }
        Half half = blockState.getValue(HALF);
        boolean isBottom = half == Half.BOTTOM && direction == Direction.UP;
        boolean isTop = half == Half.TOP && direction == Direction.DOWN;
        if (direction.getAxis() == Direction.Axis.Y && (isBottom || isTop)) {
            // 这里要用 instanceof，因为拿花切换种类时会触发此段
            if (blockState2.getBlock() instanceof SandwichBoardBlock && blockState2.getValue(HALF) != half) {
                return blockState.setValue(FACING, blockState2.getValue(FACING));
            }
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull Player player) {
        if (!level.isClientSide() && player.isCreative() && state.getValue(HALF) == Half.TOP) {
            BlockPos below = pos.below();
            BlockState belowState = level.getBlockState(below);
            if (belowState.is(state.getBlock()) && belowState.getValue(HALF) == Half.BOTTOM) {
                BlockState airBlockState = belowState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(below, airBlockState, Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, below, Block.getId(belowState));
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(@NonNull BlockPos pos, BlockState state) {
        if (state.getValue(HALF) == Half.BOTTOM) {
            return new SandwichBoardBlockEntity(pos, state);
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        if (pos.getY() < level.getMaxY() - 1 && level.getBlockState(pos.above()).canBeReplaced(context)) {
            return this.defaultBlockState()
                    .setValue(FACING, context.getHorizontalDirection().getOpposite())
                    .setValue(HALF, Half.BOTTOM)
                    .setValue(WATERLOGGED, level.isWaterAt(pos));
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, @NonNull ItemStack stack) {
        BlockPos above = pos.above();
        BlockState blockState = state.setValue(HALF, Half.TOP)
                .setValue(WATERLOGGED, level.isWaterAt(above));
        level.setBlockAndUpdate(above, blockState);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(BlockState state, LootParams.@NonNull Builder lootParamsBuilder) {
        if (state.getValue(HALF) == Half.BOTTOM) {
            return super.getDrops(state, lootParamsBuilder);
        }
        return Collections.emptyList();
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return state.getValue(HALF) == Half.TOP ? TOP_SHAPE : BOTTOM_SHAPE;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }


    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        if (this.transformItemNames == null && !this.transformItems.isEmpty()) {
            this.transformItemNames = this.transformItems.stream()
                    .map(Item::getDescriptionId)
                    .toList();
        }
        if (this.transformItemNames != null) {
            this.transformItemNames.forEach(name -> tooltip.add(Component.translatable(name).withStyle(ChatFormatting.GRAY)));
        }
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
