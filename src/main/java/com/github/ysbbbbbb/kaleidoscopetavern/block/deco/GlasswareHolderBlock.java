package com.github.ysbbbbbb.kaleidoscopetavern.block.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.GlasswareHolderBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.ItemStackHandler;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class GlasswareHolderBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock {
    public static final VoxelShape NORTH_SOUTH_SHAPE = Block.box(0, 11, 1, 16, 16, 15);
    public static final VoxelShape EAST_WEST_SHAPE = Block.box(1, 11, 0, 15, 16, 16);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public GlasswareHolderBlock(Properties properties) {
        super(properties
                .mapColor(MapColor.METAL)
                .strength(0.8F)
                .sound(SoundType.METAL)
                .lightLevel(_ -> 8)
                .noOcclusion());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(WATERLOGGED, false)
                .setValue(FACING, Direction.NORTH));
    }

    @Deprecated
    public GlasswareHolderBlock() {
        this(Properties.of());
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected @NonNull BlockState updateShape(
            @NonNull BlockState state,
            @NonNull LevelReader level,
            @NonNull ScheduledTickAccess ticks,
            @NonNull BlockPos pos,
            @NonNull Direction directionToNeighbour,
            @NonNull BlockPos neighbourPos,
            @NonNull BlockState neighbourState,
            @NonNull RandomSource random
    ) {
        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected @NotNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos,
                                                   @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof GlasswareHolderBlockEntity be)) {
            return InteractionResult.PASS;
        }

        int slot = this.getSlotFromHit(hitResult, pos);
        ItemStack itemInHand = player.getItemInHand(hand);

        // 鎵嬫寔绌洪厭鏉細灏濊瘯鏀惧叆鎸囧畾妲戒綅
        if (itemInHand.is(ModItems.EMPTY_GLASSWARE)) {
            return putOn(level, pos, player, be, slot);
        }

        // 绌烘墜锛氬皾璇曚粠鎸囧畾妲戒綅鍙栧嚭
        if (itemInHand.isEmpty()) {
            return takeOut(level, pos, player, be, slot);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult takeOut(Level level, BlockPos pos, Player player, GlasswareHolderBlockEntity be, int slot) {
        ItemStackHandler items = be.getItems();
        if (items.getStackInSlot(slot).isEmpty()) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            ItemStack extracted = items.extractItem(slot, 1, false);
            player.setItemInHand(InteractionHand.MAIN_HAND, extracted);
            be.refresh();
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.BLOCKS);
        }

        return InteractionResult.SUCCESS;
    }

    private InteractionResult putOn(Level level, BlockPos pos, Player player, GlasswareHolderBlockEntity be,
                                        int slot
    ) {
        ItemStackHandler items = be.getItems();
        if (!items.getStackInSlot(slot).isEmpty()) {
            return InteractionResult.CONSUME;
        }

        ItemStack itemInHand = player.getMainHandItem();
        if (!level.isClientSide()) {
            items.setStackInSlot(slot, itemInHand.copyWithCount(1));
            if (!player.getAbilities().instabuild) {
                itemInHand.shrink(1);
            }
            be.refresh();
            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.BLOCKS);
        }

        return InteractionResult.SUCCESS;
    }

    private int getSlotFromHit(BlockHitResult hitResult, BlockPos pos) {
        Vec3 hit = hitResult.getLocation();
        double localX = hit.x - pos.getX();
        double localZ = hit.z - pos.getZ();

        if (localX > 0.5) {
            return localZ > 0.5 ? 3 : 1;
        } else {
            return localZ > 0.5 ? 2 : 0;
        }
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return new GlasswareHolderBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean hasWater = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        Direction opposite = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState().setValue(FACING, opposite).setValue(WATERLOGGED, hasWater);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NonNull BlockState state, LootParams.@NonNull Builder builder) {
        List<ItemStack> stacks = super.getDrops(state, builder);
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (!(blockEntity instanceof GlasswareHolderBlockEntity be)) {
            return stacks;
        }
        ItemStackHandler items = be.getItems();
        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack stack = items.getStackInSlot(i);
            if (!stack.isEmpty()) {
                stacks.add(stack);
            }
        }
        return stacks;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NonNull BlockGetter pLevel, @NonNull BlockPos pPos, @NonNull CollisionContext pContext) {
        return switch (state.getValue(FACING)) {
            case NORTH, SOUTH -> NORTH_SOUTH_SHAPE;
            default -> EAST_WEST_SHAPE;
        };
    }
}
