package com.github.ysbbbbbb.kaleidoscopetavern.block.dispenser;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class BottleBlockDispenseBehavior implements DispenseBehavior {
    public static final MapCodec<BottleBlockDispenseBehavior> CODEC = MapCodec.unit(new BottleBlockDispenseBehavior());

    @Override
    public @NotNull DispenseResult execute(BlockSource source, @NonNull ItemStack stack) {
        ServerLevel level = source.level();
        if (stack.getItem() instanceof BottleBlockItem bottleBlockItem) {
            Direction facing = source.state().getValue(DispenserBlock.FACING);
            BlockPos placePos = source.pos().relative(facing);
            Direction placeDirection = facing.getAxis() == Direction.Axis.Y ? Direction.NORTH : facing;
            try {
                DirectionalPlaceContext context = new DirectionalPlaceContext(level, placePos, facing, stack, placeDirection);
                boolean success = bottleBlockItem.placeForDispenser(context, source.blockEntity()).consumesAction();
                return success ? DispenseResult.success(stack) : DispenseResult.stoppingFailure();
            } catch (Exception exception) {
                KaleidoscopeTavern.LOGGER.error("Error trying to place bottle block item from dispenser", exception);
            }
        } else if (stack.is(ModItems.EMPTY_BOTTLE) && stack.getItem() instanceof BlockItem blockItem) {
            Direction facing = source.state().getValue(DispenserBlock.FACING);
            BlockPos placePos = source.pos().relative(facing);
            Direction placeDirection = facing.getAxis() == Direction.Axis.Y ? Direction.NORTH : facing;
            try {
                DirectionalPlaceContext context = new DirectionalPlaceContext(level, placePos, facing, stack, placeDirection);
                boolean success = blockItem.place(context).consumesAction();
                return success ? DispenseResult.success(stack) : DispenseResult.stoppingFailure();
            } catch (Exception exception) {
                KaleidoscopeTavern.LOGGER.error("Error trying to place bottle block item from dispenser", exception);
            }
        }
        return DispenseResult.stoppingFailure();
    }

    @Override
    public @NonNull MapCodec<? extends DispenseBehavior> codec() {
        return CODEC;
    }
}
