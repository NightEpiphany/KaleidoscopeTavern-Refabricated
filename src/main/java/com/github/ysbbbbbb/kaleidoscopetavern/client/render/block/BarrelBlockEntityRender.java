package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IBarrel;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BarrelBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.BarrelBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.brew.BarrelModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.BarrelBlockEntityRenderState;
import com.github.ysbbbbbb.kaleidoscopetavern.util.RenderUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.fluids.CustomFluidTank;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.ItemStackHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import static com.github.ysbbbbbb.kaleidoscopetavern.util.RenderUtils.stableRandom;

@Environment(EnvType.CLIENT)
public class BarrelBlockEntityRender implements BlockEntityRenderer<BarrelBlockEntity, BarrelBlockEntityRenderState> {
    private static final Identifier LARGE_TEXTURE = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "textures/entity/brew/barrel.png");
    private final Minecraft minecraft = Minecraft.getInstance();
    private final ItemModelResolver itemModelResolver;
    private final BarrelModel model;

    public BarrelBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
        this.model = new BarrelModel(context.bakeLayer(BarrelModel.LAYER_LOCATION));
    }

    @Override
    public void extractRenderState(BarrelBlockEntity blockEntity, BarrelBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.facing = blockEntity.getBlockState().getValue(BarrelBlock.FACING);
        blockEntityRenderState.isOpen = blockEntity.isOpen();
        blockEntityRenderState.fluidTank = blockEntity.getFluid();
        if (blockEntity.getLevel() != null) {
            blockEntityRenderState.time = blockEntity.getLevel().getGameTime() + f;
        }
        blockEntityRenderState.seed = blockEntity.getBlockPos().asLong();
        blockEntityRenderState.items = blockEntity.getIngredient();
        for (int index = 0; index < blockEntity.getIngredient().getSlots(); index++) {
            ItemStack stack = blockEntity.getIngredient().getStackInSlot(index);
            if (!stack.isEmpty()) {
                ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
                this.itemModelResolver
                        .updateForTopItem(itemStackRenderState, stack, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, (int) (blockEntityRenderState.seed + index));
                blockEntityRenderState.itemStates.add(itemStackRenderState);
            }
        }
    }

    private void renderBody(BarrelBlockEntityRenderState barrel, PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector) {

        poseStack.pushPose();
        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        poseStack.mulPose(Axis.YN.rotationDegrees(180 - barrel.facing.get2DDataValue() * 90));
        BarrelModel.State state = new BarrelModel.State(barrel.isOpen);
        this.model.setupAnim(state);
        RenderType renderType = RenderTypes.entityCutoutNoCull(LARGE_TEXTURE);
        submitNodeCollector.submitModel(
                model,
                state,
                poseStack,
                renderType,
                barrel.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0,
                null
        );
        poseStack.popPose();
    }

    private void renderFluid(BarrelBlockEntityRenderState barrel, PoseStack poseStack, MultiBufferSource buffer) {
        CustomFluidTank fluidTank = barrel.fluidTank;
        int fluidAmount = fluidTank.getFluidAmountMb();
        if (fluidAmount > 0) {
            poseStack.pushPose();
            poseStack.translate(0, 2, 0);

            float percent = fluidAmount / (float) IBarrel.MAX_FLUID_AMOUNT;
            float y = percent * 0.65f;
            Fluid fluid = fluidTank.getFluid();
            RenderUtils.renderFluid(fluid, poseStack, buffer, barrel.lightCoords, 16, y);

            poseStack.popPose();
        }
    }

    private void renderItems(BarrelBlockEntityRenderState barrel, PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector) {
        int globalIndex = 0;
        for (int index = 0; index < barrel.items.getSlots(); index++) {
            ItemStack stack = barrel.items.getStackInSlot(index);
            if (stack.isEmpty() || stack.is(Items.BARRIER)) continue;
            int count = stack.getCount() / 2 + 1;
            ItemStackRenderState state = barrel.itemStates.get(index);
            if (!state.isEmpty()) {
                for (int i = 0; i < count; i++) {
                    poseStack.pushPose();

                    float x = stableRandom(barrel.seed, globalIndex, index + 1) * 0.4f;
                    float z = stableRandom(barrel.seed, globalIndex, index + 2) * 0.4f;
                    float y = (float) (globalIndex / 4) * 0.025f + stableRandom(barrel.seed, globalIndex, index + 3) * 0.05f;

                    // 添加一些上下浮动效果
                    y += (float) (Math.sin(barrel.time / 10f + globalIndex) * 0.02f);

                    float yRot = stableRandom(barrel.seed, globalIndex, index + 4) * 5f;
                    float zRot = stableRandom(barrel.seed, globalIndex, index + 5) * 360f;

                    poseStack.translate(0.5f + x, 2.7f + y, 0.5f + z);
                    poseStack.scale(0.5f, 0.5f, 0.5f);
                    poseStack.mulPose(Axis.XN.rotationDegrees(90));
                    poseStack.mulPose(Axis.YN.rotationDegrees(yRot));
                    poseStack.mulPose(Axis.ZN.rotationDegrees(zRot));
                    state.submit(poseStack, submitNodeCollector, barrel.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                    poseStack.popPose();
                    globalIndex++;
                }
            }
        }
    }

    @Override
    public BarrelBlockEntityRenderState createRenderState() {
        return new BarrelBlockEntityRenderState();
    }

    @Override
    public void submit(BarrelBlockEntityRenderState barrel, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        // 本体渲染
        this.renderBody(barrel, poseStack, submitNodeCollector);
        // 开盖后才会渲染下面部分
        if (barrel.isOpen) {
            MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
            // 如果有流体，渲染流体
            this.renderFluid(barrel, poseStack, buffer);
            // 渲染物品
            this.renderItems(barrel, poseStack, submitNodeCollector);
        }
    }
}
