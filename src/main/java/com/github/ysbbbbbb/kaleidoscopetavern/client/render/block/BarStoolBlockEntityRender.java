package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.BarStoolBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.BarStoolBodyModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.BarStoolBlockEntityRenderState;
import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class BarStoolBlockEntityRender implements BlockEntityRenderer<BarStoolBlockEntity, BarStoolBlockEntityRenderState> {
    private final Function<DyeColor, Identifier> TEXTURE_CACHE = Util.memoize(color -> {
        String path = "textures/entity/deco/bar_stool/%s.png".formatted(color.getName());
        return Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, path);
    });
    // 一些会用到的常量声明
    private static final int MAX_ROT_CACHE_SIZE = 4096;
    private static final float MIN_SMOOTH_FACTOR = 0.20F;
    private static final float MAX_SMOOTH_FACTOR = 0.58F;
    private static final float SENSITIVITY_SCALE = 0.007F;
    private static final float PASSENGER_VELOCITY_BLEND = 0.22F;
    private static final float PASSENGER_PREDICT_TICKS = 0.18F;
    private static final float PASSENGER_MIN_STEP = 2.0F;
    private static final float PASSENGER_STEP_SCALE = 0.26F;
    private static final float RESTORE_SMOOTH_FACTOR = 0.35F;
    private final BarStoolBodyModel model;
    // 保险起见，还是做一个并发缓存
    private final Map<Long, Float> renderRotCache = new ConcurrentHashMap<>();

    public BarStoolBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.model = new BarStoolBodyModel(context.bakeLayer(BarStoolBodyModel.LAYER_LOCATION));
    }

    private static float getSampleTime(float partialTick) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return partialTick;
        }
        return level.getGameTime() + partialTick;
    }

    private static float smoothPassengerRotation(float currentRot, float targetRot, float angularVelocity) {
        float velocityAbs = Math.abs(angularVelocity);
        float smoothFactor = Mth.clamp(MIN_SMOOTH_FACTOR + velocityAbs * SENSITIVITY_SCALE, MIN_SMOOTH_FACTOR, MAX_SMOOTH_FACTOR);
        float lerped = Mth.rotLerp(smoothFactor, currentRot, targetRot);
        float rawStep = Mth.wrapDegrees(lerped - currentRot);
        float maxStep = PASSENGER_MIN_STEP + velocityAbs * PASSENGER_STEP_SCALE;
        float clampedStep = Mth.clamp(rawStep, -maxStep, maxStep);
        return Mth.wrapDegrees(currentRot + clampedStep);
    }

    private static @Nullable LivingEntity resolvePassenger(BarStoolBlockEntityRenderState state) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return null;
        }
        for (SitEntity sitEntity : level.getEntitiesOfClass(SitEntity.class, new AABB(state.blockPos))) {
            if (!sitEntity.isAlive() || sitEntity.getPassengers().isEmpty()) {
                continue;
            }
            Entity entity = sitEntity.getFirstPassenger();
            if (entity instanceof LivingEntity livingEntity) {
                return livingEntity;
            }
        }
        return null;
    }

    private float getRenderRot(BarStoolBlockEntityRenderState state) {
        if (this.renderRotCache.size() > MAX_ROT_CACHE_SIZE) {
            this.renderRotCache.clear();
        }
        long key = state.blockPos.asLong();
        float cachedRot = state.cachedRot;
        float currentRot = this.renderRotCache.getOrDefault(key, cachedRot);
        LivingEntity passenger = resolvePassenger(state);
        if (passenger == null) {
            float restoredRot = Mth.rotLerp(RESTORE_SMOOTH_FACTOR, currentRot, cachedRot);
            if (Mth.degreesDifferenceAbs(restoredRot, cachedRot) < 0.5F) {
                this.renderRotCache.remove(key);
                return cachedRot;
            }
            this.renderRotCache.put(key, restoredRot);
            return restoredRot;
        }
        float bodyRot = Mth.rotLerp(state.partialTicks, passenger.yBodyRotO, passenger.yBodyRot);
        float angularVelocity = Mth.wrapDegrees(passenger.yBodyRot - passenger.yBodyRotO);
        float predictedRot = bodyRot + angularVelocity * PASSENGER_PREDICT_TICKS;
        float wobble = Mth.sin(getSampleTime(state.partialTicks) * SENSITIVITY_SCALE) * angularVelocity * PASSENGER_VELOCITY_BLEND;
        float targetRot = Mth.wrapDegrees(Mth.rotLerp(PASSENGER_VELOCITY_BLEND, bodyRot, predictedRot) + wobble);
        float smoothedRot = smoothPassengerRotation(currentRot, targetRot, angularVelocity);
        this.renderRotCache.put(key, smoothedRot);
        return smoothedRot;
    }

    @Override
    public @NonNull BarStoolBlockEntityRenderState createRenderState() {
        return new BarStoolBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(@NonNull BarStoolBlockEntity blockEntity, @NonNull BarStoolBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.color = blockEntity.getColor();
        blockEntityRenderState.cachedRot = blockEntity.getCachedRot();
        blockEntityRenderState.partialTicks = f;
    }

    @Override
    public void submit(@NonNull BarStoolBlockEntityRenderState blockEntityRenderState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        float renderRot = this.getRenderRot(blockEntityRenderState);
        Identifier texture = TEXTURE_CACHE.apply(blockEntityRenderState.color);
        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.rotateDegrees(Axis.ZN, 180.0F);
        poseStack.rotateDegrees(Axis.YN, 180.0F - renderRot);
        BarStoolBodyModel.State state = new BarStoolBodyModel.State();
        submitNodeCollector.submitModel(
                this.model,
                state,
                poseStack,
                RenderTypes.entityCutout(texture),
                blockEntityRenderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0
        );
        poseStack.popPose();
    }


}
