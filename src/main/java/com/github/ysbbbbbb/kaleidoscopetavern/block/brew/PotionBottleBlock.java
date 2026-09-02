package com.github.ysbbbbbb.kaleidoscopetavern.block.brew;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PotionBottleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PotionBottleBlock extends BottleBlock implements EntityBlock {
    public PotionBottleBlock(Properties properties) {
        super(properties, false);
    }

    @Override
    public @NonNull List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        List<ItemStack> drops = super.getDrops(state, params);
        BlockEntity blockEntity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof PotionBottleBlockEntity be && !be.getPotionStack().isEmpty()) {
            drops.add(be.getPotionStack().copyWithCount(1));
        }
        return drops;
    }

    @Override
    public @NonNull ItemStack getCloneItemStack(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state, boolean bl) {
        if (level.getBlockEntity(pos) instanceof PotionBottleBlockEntity be && !be.getPotionStack().isEmpty()) {
            return be.getPotionStack().copyWithCount(1);
        }
        return super.getCloneItemStack(level, pos, state, bl);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PotionBottleBlockEntity(pos, state);
    }

    @Override
    public @NonNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BottleBlock.SHAPE;
    }
}
