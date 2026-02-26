package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PressingTubBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.util.fluids.CustomFluidTank;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
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

import java.util.List;

@SuppressWarnings("deprecation")
public class PressingTubBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape SHAPE = Shapes.join(
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(2, 4, 2, 14, 8, 14),
            BooleanOp.ONLY_FIRST
    );

    public PressingTubBlock() {
        super(Properties.of()
                .mapColor(MapColor.WOOD)
                .instrument(NoteBlockInstrument.GUITAR)
                .strength(0.8F)
                .sound(SoundType.WOOD)
                .ignitedByLava());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(WATERLOGGED, false));
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                          InteractionHand hand, BlockHitResult hitResult) {
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
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity instanceof LivingEntity livingEntity) {
            if (!(level.getBlockEntity(pos) instanceof IPressingTub pressingTub)) {
                return;
            }
            if (pressingTub.press(livingEntity, fallDistance)) {
                return;
            }
        }
        // 如果压榨不成功，则正常掉落伤害
        super.fallOn(level, state, pos, entity, fallDistance);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
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
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                           LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PressingTubBlockEntity(pos, state);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof IPressingTub pressingTub) {
            return pressingTub.getFluidAmount();
        }
        return 0;
    }

    @Override
    public @NotNull ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        ItemStack stack = SimpleWaterloggedBlock.super.pickupBlock(level, pos, state);
        if (!stack.isEmpty()) {
            return stack;
        }
        if (level.isClientSide()) {
            return stack;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);
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
}
