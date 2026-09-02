package com.github.ysbbbbbb.kaleidoscopetavern.client.render.misc;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.SignatureCocktailBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.item.SignatureCocktailBlockItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SignatureCocktailColor {
    private static final int OPAQUE_MASK = 0xFE000000;

    private static int opaque(int color) {
        return OPAQUE_MASK | (color & 0xFFFFFF);
    }

    public static class Block implements BlockColor {
        @Override
        public int getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int tintIndex) {
            if (tintIndex != 0 || level == null || pos == null) {
                return opaque(0xFFFFFF);
            }
            if (level.getBlockEntity(pos) instanceof SignatureCocktailBlockEntity be) {
                return opaque(be.getColor());
            }
            return opaque(0xFFFFFF);
        }
    }

    public static class Item implements ItemTintSource {
        @Override
        public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
            return opaque(SignatureCocktailBlockItem.getColor(stack));
        }

        @Override
        public MapCodec<? extends ItemTintSource> type() {
            return MapCodec.unit(this);
        }
    }
}
