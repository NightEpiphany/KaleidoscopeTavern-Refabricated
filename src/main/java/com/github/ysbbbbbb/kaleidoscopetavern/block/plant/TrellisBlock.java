package com.github.ysbbbbbb.kaleidoscopetavern.block.plant;

import com.github.ysbbbbbb.kaleidoscopetavern.api.event.PlantGrapeEvent;
import com.github.ysbbbbbb.kaleidoscopetavern.block.properties.TrellisType;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.ysbbbbbb.kaleidoscopetavern.block.plant.ITrellis.*;

@SuppressWarnings("deprecation")
public class TrellisBlock extends Block implements SimpleWaterloggedBlock, ITrellis {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty WAXED = BooleanProperty.create("waxed");

    public TrellisBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOD)
                .noOcclusion()
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TYPE, TrellisType.SINGLE)
                .setValue(WAXED, false)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player,
                                          @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        // 打蜡与去除
        boolean waxed = state.getValue(WAXED);
        if (waxed && player.getItemInHand(hand).is(ItemTags.AXES)) {
            level.setBlockAndUpdate(pos, state.setValue(WAXED, false));
            level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, LevelEvent.PARTICLES_WAX_OFF, pos, 0);
            return InteractionResult.SUCCESS;
        } else if (!waxed && player.getItemInHand(hand).is(Items.HONEYCOMB)) {
            level.setBlockAndUpdate(pos, state.setValue(WAXED, true));
            level.playSound(player, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, LevelEvent.PARTICLES_AND_SOUND_WAX_ON, pos, 0);
            return InteractionResult.SUCCESS;
        }

        // 玩家手持的是葡萄藤
        ItemStack itemInHand = player.getItemInHand(hand);
        if (!itemInHand.is(ModItems.GRAPEVINE)) {
            return super.use(state, level, pos, player, hand, hitResult);
        }
        // 如果藤架是单个的
        TrellisType type = state.getValue(TYPE);
        if (type != TrellisType.SINGLE) {
            return super.use(state, level, pos, player, hand, hitResult);
        }
        // 通过事件来执行各种不同的种植逻辑
        PlantGrapeEvent plantGrapeEvent = new PlantGrapeEvent(state, level, pos, player, hand, hitResult);
        plantGrapeEvent.post();
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState,
                                           @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        boolean xHas = axisHasTrellis(level, pos, Direction.Axis.X);
        boolean yHas = axisHasTrellis(level, pos, Direction.Axis.Y);
        boolean zHas = axisHasTrellis(level, pos, Direction.Axis.Z);
        var trellisType = updateType(state.getValue(TYPE), xHas, yHas, zHas);

        state = state.setValue(TYPE, trellisType);
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean sameType(BlockState state) {
        return state.is(ModBlocks.TRELLIS) || state.is(ModBlocks.GRAPEVINE_TRELLIS);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction.Axis clickAxis = context.getClickedFace().getAxis();

        boolean xHas = axisHasTrellis(level, pos, Direction.Axis.X);
        boolean yHas = axisHasTrellis(level, pos, Direction.Axis.Y);
        boolean zHas = axisHasTrellis(level, pos, Direction.Axis.Z);
        var type = getTypeForPlacement(xHas, yHas, zHas, clickAxis);

        return this.defaultBlockState()
                .setValue(TYPE, type)
                .setValue(WATERLOGGED, level.getFluidState(pos).is(Fluids.WATER));
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return collisionShape(state.getValue(TYPE));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return selectShape(state.getValue(TYPE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WAXED, WATERLOGGED);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
