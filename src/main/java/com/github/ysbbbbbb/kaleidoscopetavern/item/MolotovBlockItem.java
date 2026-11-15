package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.entity.ThrownMolotovEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ItemUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class MolotovBlockItem extends BottleBlockItem {
    public MolotovBlockItem(Block block) {
        super(block);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NonNull Level level, Player player, @NonNull InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();

        // 只有潜行时才放置
        if (player == null || player.isShiftKeyDown()) {
            return this.place(new BlockPlaceContext(context));
        }

        return InteractionResult.PASS;
    }

    @Override
    public void releaseUsing(@NonNull ItemStack stack, @NonNull Level level, @NonNull LivingEntity entity, int timeLeft) {
        int time = this.getUseDuration(stack) - timeLeft;
        if (time < 10) {
            return;
        }
        if (!level.isClientSide) {
            ThrownMolotovEntity molotov = new ThrownMolotovEntity(level, entity);
            molotov.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0, 0.8f, 1);
            level.addFreshEntity(molotov);

            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.5F,
                    0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

            ItemUtils.consume(stack, entity, 1);
        }
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NonNull ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(@NonNull ItemStack stack) {
        return 72000;
    }
}
