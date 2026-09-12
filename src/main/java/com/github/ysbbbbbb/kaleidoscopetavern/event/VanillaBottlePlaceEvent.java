package com.github.ysbbbbbb.kaleidoscopetavern.event;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PotionBottleBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.config.ConfigGetter;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import static com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks.*;

public class VanillaBottlePlaceEvent {

    public static void register() {
        UseBlockCallback.EVENT.register(VanillaBottlePlaceEvent::onRightClickBlock);
    }


    private static InteractionResult onRightClickBlock(Player player, Level level, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (!player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }

        ItemStack stack = player.getItemInHand(interactionHand);
        Placement placement = getPlacement(stack);
        if (placement == null || !placement.enabled()) {
            return InteractionResult.PASS;
        }

        BlockPlaceContext context = new BlockPlaceContext(player, interactionHand, stack, blockHitResult);
        if (!context.canPlace()) {
            return InteractionResult.PASS;
        }

        BlockState state = placement.block().getStateForPlacement(context);
        if (state == null) {
            return InteractionResult.PASS;
        }

        BlockPos placePos = context.getClickedPos();
        if (!state.canSurvive(level, placePos)) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!level.setBlock(placePos, state, Block.UPDATE_ALL)) {
            return InteractionResult.CONSUME;
        }

        if (placement.storePotionStack() && level.getBlockEntity(placePos) instanceof PotionBottleBlockEntity be) {
            be.setPotionStack(stack);
        }

        level.playSound(null, placePos, SoundEvents.GLASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return InteractionResult.CONSUME;
    }

    private static Placement getPlacement(ItemStack stack) {
        if (stack.is(Items.POTION)) {
            var potion = stack.get(DataComponents.POTION_CONTENTS);
            if (potion == null || potion.is(Potions.WATER)) {
                return new Placement(WATER_BOTTLE, ConfigGetter.getWaterBottlePlacement(), false);
            }
            return new Placement(POTION_BOTTLE, ConfigGetter.getPotionBottlePlacement(), true);
        }

        if (stack.is(Items.HONEY_BOTTLE)) {
            return new Placement(HONEY_BOTTLE, ConfigGetter.getHoneyBottlePlacement(), false);
        }

        if (stack.is(Items.DRAGON_BREATH)) {
            return new Placement(DRAGON_BREATH_BOTTLE, ConfigGetter.getDragonBreathBottlePlacement(), false);
        }

        if (stack.is(Items.EXPERIENCE_BOTTLE)) {
            return new Placement(XP_BOTTLE, ConfigGetter.getExperienceBottlePlacement(), false);
        }

        return null;
    }

    private record Placement(Block block, boolean enabled, boolean storePotionStack) {
    }
}
