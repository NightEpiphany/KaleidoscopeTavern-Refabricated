package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.entity.ThrownMolotovEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class MolotovBlockItem extends BottleBlockItem implements ProjectileItem {
    public MolotovBlockItem(Block block, Properties properties) {
        super(block, properties.stacksTo(16).useBlockDescriptionPrefix());
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, Player player, @NonNull InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
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
    public boolean releaseUsing(@NonNull ItemStack stack, @NonNull Level level, @NonNull LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player) {
            int time = this.getUseDuration(stack, player) - timeLeft;
            if (time < 10) {
                return false;
            } else {
                player.awardStat(Stats.ITEM_USED.get(this));
                if (level instanceof ServerLevel serverLevel) {
                    ThrownMolotovEntity thrownMolotov = new ThrownMolotovEntity(serverLevel, player);
                    thrownMolotov.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.82F, 1.0F);
                    serverLevel.addFreshEntity(thrownMolotov);
                }

                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.5F,
                        0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                return true;
            }
        }else {
            return false;
        }
    }

    @Override
    public @NonNull ItemUseAnimation getUseAnimation(@NonNull ItemStack stack) {
        return ItemUseAnimation.TRIDENT;
    }

    @Override
    public int getUseDuration(@NonNull ItemStack stack, @NonNull LivingEntity entity) {
        return 72000;
    }

    @Override
    public @NonNull Projectile asProjectile(@NonNull Level level, @NonNull Position position, @NonNull ItemStack itemStack, @NonNull Direction direction) {
        return new ThrownMolotovEntity(level, position.x(), position.y(), position.z());
    }
}
