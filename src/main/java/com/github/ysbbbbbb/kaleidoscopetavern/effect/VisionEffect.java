package com.github.ysbbbbbb.kaleidoscopetavern.effect;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.Predicate;

public class VisionEffect extends BaseEffect {
    public VisionEffect(int color) {
        super(color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 50 == 0;
    }

    @Override
    public boolean applyEffectTick(@NonNull ServerLevel serverLevel, LivingEntity living, int amplifier) {
        Level level = living.level();
        double radius = Math.min(amplifier + 1, 3) * 6;
        AABB range = living.getBoundingBox().inflate(radius);
        Predicate<LivingEntity> filter = entity -> entity != living && entity.isAlive();

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, range, filter);
        if (entities.isEmpty()) {
            return true;
        }
        // 只有新生物附加了效果才播放声音，避免反复播放
        boolean apply = false;
        for (LivingEntity entity : entities) {
            if (!entity.hasEffect(MobEffects.GLOWING)) {
                apply = true;
            }
            entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60));
        }
        if (apply) {
            // 1.21.11 效果 tick 仅在服务端运行，Player.playSound 会排除自身，
            // 单人游戏里玩家本人永远听不到；改为显式广播给所有玩家
            serverLevel.playSound(null, living.getX(), living.getY(), living.getZ(),
                    ModSounds.EFFECT_VISION, living.getSoundSource(), 1.0F, 1.0F);
        }
        return true;
    }
}
