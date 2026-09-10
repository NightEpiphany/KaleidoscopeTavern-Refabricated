package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IBarrel;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.DrinkBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.DrinkBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources.DrinkEffectDataReloadListener;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.google.common.collect.Lists;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownSplashPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

public class DrinkBlockItem extends BottleBlockItem implements IHasContainer {
    public DrinkBlockItem(Block block, Properties properties) {
        super(block, properties
                .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
                .stacksTo(16)
                .useBlockDescriptionPrefix()
                .craftRemainder(ModItems.EMPTY_BOTTLE));
    }

    @Override
    public int getUseDuration(@NonNull ItemStack stack, @NonNull LivingEntity entity) {
        return 32;
    }

    @Override
    public @NonNull ItemUseAnimation getUseAnimation(@NonNull ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();

        BlockState state = level.getBlockState(pos);
        Block self = this.getBlock();

        if (player != null && tryIncreaseCount(self, state, level, pos, stack, player)) {
            return InteractionResult.SUCCESS;
        }

        if (player == null || player.isShiftKeyDown()) {
            return this.place(new BlockPlaceContext(context));
        }

        InteractionResult result = this.use(level, player, context.getHand());
        return result == InteractionResult.CONSUME ? InteractionResult.TRY_WITH_EMPTY_HAND : result;
    }

    private boolean tryIncreaseCount(Block self, BlockState state, Level level, BlockPos pos, ItemStack stack, Player player) {
        if (self instanceof DrinkBlock drink && state.is(self) && drink.tryIncreaseCount(level, pos, state, stack)) {
            SoundType soundType = state.getSoundType();
            SoundEvent sound = this.getPlaceSound(state);
            level.playSound(
                    player, pos, sound, SoundSource.BLOCKS,
                    (soundType.getVolume() + 1) / 2f,
                    soundType.getPitch() * 0.8f
            );
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            return true;
        }
        return false;
    }

    @Override
    public @org.jspecify.annotations.Nullable BlockPlaceContext updatePlacementContext(@NonNull BlockPlaceContext context) {
        if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof DrinkBlockEntity be && be.addItem(context.getItemInHand())) {
            be.refresh();
        }
        return super.updatePlacementContext(context);
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, @NonNull Player player, @NonNull InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NonNull ItemStack stack, @NonNull Level level, @NonNull LivingEntity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        this.addDrinkEffect(stack, level, entity);
        if (entity instanceof Player player && !player.isCreative()) {
            stack.shrink(1);
        }
        return returnContainerToEntity(stack, level, entity);
    }

    protected void addDrinkEffect(ItemStack drink, Level level, LivingEntity entity) {
        DrinkEffectData effectData = DrinkEffectDataReloadListener.INSTANCE.get(drink.getItem());
        if (effectData == null) {
            return;
        }
        var effects = effectData.effects();
        if (effects.isEmpty()) {
            return;
        }
        int brewLevel = BottleBlockItem.getBrewLevel(drink);
        if (brewLevel < IBarrel.BREWING_STARTED) {
            return;
        }
        brewLevel = Math.min(brewLevel, effects.size());
        for (DrinkEffectData.Entry entry : effects.get(brewLevel - 1)) {
            if (!level.isClientSide() && level.getRandom().nextFloat() < entry.probability()) {
                MobEffect effect = entry.effect().value();
                int amplifier = entry.amplifier();
                if (effect.isInstantaneous() && level instanceof ServerLevel serverLevel) {
                    effect.applyInstantaneousEffect(serverLevel, entity, entity, entity, amplifier, 1.0);
                } else {
                    int duration = entry.duration() * 20;
                    entity.addEffect(new MobEffectInstance(entry.effect(), duration, amplifier));
                }
            }
        }
    }

    public void makeThrownPotion(Level level, double x, double y, double z, int brewLevel, @Nullable Entity owner) {
        this.makeThrownPotion(level, x, y, z, brewLevel, owner, null);
    }

    public void makeThrownPotion(Level level, double x, double y, double z, int brewLevel,
                                 @Nullable Entity owner, @Nullable Vec3 movement) {
        List<MobEffectInstance> instances = this.getEffectInstances(level, brewLevel);
        if (instances.isEmpty()) {
            return;
        }

        ItemStack stack = new ItemStack(this);
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), instances, Optional.empty()));

        ThrownSplashPotion potion = new ThrownSplashPotion(level, x, y, z, stack);
        if (owner instanceof LivingEntity livingEntity) {
            potion.setOwner(livingEntity);
        }
        if (movement != null) {
            potion.setDeltaMovement(movement);
        }
        potion.setItem(stack);

        level.addFreshEntity(potion);
    }

    protected List<MobEffectInstance> getEffectInstances(Level level, int brewLevel) {
        DrinkEffectData effectData = DrinkEffectDataReloadListener.INSTANCE.get(this);
        if (effectData == null) {
            return List.of();
        }
        var effects = effectData.effects();
        if (effects.isEmpty()) {
            return List.of();
        }
        brewLevel = BottleBlockItem.clampBrewLevel(brewLevel);
        if (brewLevel < IBarrel.BREWING_STARTED) {
            return List.of();
        }
        brewLevel = Math.min(brewLevel, effects.size());

        List<MobEffectInstance> instances = Lists.newArrayList();
        for (DrinkEffectData.Entry entry : effects.get(brewLevel - 1)) {
            if (level.getRandom().nextFloat() < entry.probability()) {
                int duration = entry.duration() * 20;
                int amplifier = entry.amplifier();
                instances.add(new MobEffectInstance(entry.effect(), duration, amplifier));
            }
        }
        return instances;
    }

    @Override
    public Item getContainerItem() {
        return ModItems.EMPTY_BOTTLE;
    }
}
