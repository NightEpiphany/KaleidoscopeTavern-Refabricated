package com.github.ysbbbbbb.kaleidoscopetavern.entity;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModEntities;
import com.github.ysbbbbbb.kaleidoscopetavern.init.tag.TagMod;
import com.github.ysbbbbbb.kaleidoscopetavern.util.SitUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class SitEntity extends Entity {
    private int passengerTick = 0;

    public SitEntity(EntityType<? extends SitEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public SitEntity(Level level, BlockPos pos) {
        this(ModEntities.SIT, level);
        this.setPos(pos.getX() + 0.5, pos.getY() + 0.4375, pos.getZ() + 0.5);
    }

    public SitEntity(Level level, BlockPos pos, double y) {
        this(ModEntities.SIT, level);
        this.setPos(pos.getX() + 0.5, pos.getY() + y, pos.getZ() + 0.5);
    }

    @Override
    public @NonNull Vec3 getPassengerRidingPosition(Entity entity) {
        return super.getPassengerRidingPosition(entity).add(0, -0.0625, 0);
    }

    @Override
    protected void defineSynchedData(@NonNull Builder builder) {}

    @Override
    public void readAdditionalSaveData(@NonNull ValueInput nbt) {
        // 坐姿实体不应持久化：如果被意外保存进存档，加载时立即丢弃，避免残留占位
        // （不能用 EntityType.noSave()，1.21.11 的 startRiding 要求 vehicle 的 EntityType 可序列化）
        this.discard();
    }

    @Override
    public void addAdditionalSaveData(@NonNull ValueOutput nbt) {}

    @Override
    public @NonNull Packet<ClientGamePacketListener> getAddEntityPacket(@NonNull ServerEntity serverEntity) {
        return new ClientboundAddEntityPacket(this, serverEntity);
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide()) {
            this.checkBelowWorld();
            this.checkPassengers();

            // 每隔一段时间检查下方方块是否仍是可坐方块，否则移除实体
            if (this.tickCount % 20 == 0) {
                BlockState blockState = this.level().getBlockState(this.blockPosition());
                if (!blockState.is(TagMod.SITTABLE)) {
                    this.discard();
                }
            }
        }
    }

    private void checkPassengers() {
        if (this.getPassengers().isEmpty()) {
            passengerTick++;
        } else {
            passengerTick = 0;
        }
        if (passengerTick > 10) {
            this.discard();
        }
    }

    @Override
    public void remove(@NonNull RemovalReason reason) {
        super.remove(reason);
        SitUtil.removeSitEntity(level(), blockPosition());
    }

    @Override
    public boolean skipAttackInteraction(Entity targetEntity) {
        return true;
    }

    @Override
    public boolean hurtServer(@NonNull ServerLevel level, @NonNull DamageSource source, float amount) {
        return false;
    }

    @Override
    public void move(@NonNull MoverType moverType, @NonNull Vec3 movement) {}

    @Override
    public void push(@NonNull Entity pushedEntity) {}

    @Override
    public void push(double x, double y, double z) {}

    @Override
    protected boolean repositionEntityAfterLoad() {
        return false;
    }

    @Override
    public void thunderHit(@NonNull ServerLevel serverLevel, @NonNull LightningBolt lightningBolt) {}

    @Override
    public void refreshDimensions() {}

    @Override
    public boolean canCollideWith(@NonNull Entity entity) {
        return false;
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return false;
    }
}
