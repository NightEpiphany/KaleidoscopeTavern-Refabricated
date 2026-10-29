package com.github.ysbbbbbb.kaleidoscopetavern.api.event;

import com.github.ysbbbbbb.kaleidoscopetavern.util.event.CancellableKTEvent;
import net.minecraft.world.entity.LivingEntity;

public class LivingChangeTargetEvent extends CancellableKTEvent {
    private final LivingEntity entity;
    private final ILivingTargetType targetType;
    private final LivingEntity originalTarget;
    private LivingEntity newTarget;


    public LivingChangeTargetEvent(LivingEntity entity, LivingEntity originalTarget, ILivingTargetType targetType)
    {
        this.entity = entity;
        this.originalTarget = originalTarget;
        this.newTarget = originalTarget;
        this.targetType = targetType;
    }

    public LivingEntity getNewTarget()
    {
        return newTarget;
    }

    public void setNewTarget(LivingEntity newTarget)
    {
        this.newTarget = newTarget;
    }

    public ILivingTargetType getTargetType()
    {
        return targetType;
    }

    public LivingEntity getOriginalTarget()
    {
        return originalTarget;
    }

    public interface ILivingTargetType { }

    public enum LivingTargetType implements ILivingTargetType
    {

        MOB_TARGET,

        BEHAVIOR_TARGET;
    }
}
