package com.github.ysbbbbbb.kaleidoscopetavern.client.render.block;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.ChalkboardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.LargeChalkboardModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco.SmallChalkboardModel;
import com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate.ChalkboardBlockEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ChalkboardBlockEntityRender extends TextBlockEntityRender<ChalkboardBlockEntity, ChalkboardBlockEntityRenderState> {
    private static final Identifier SMALL_TEXTURE = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "textures/entity/deco/small_chalkboard.png");
    private static final Identifier LARGE_TEXTURE = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "textures/entity/deco/large_chalkboard.png");

    private static final float TEXT_SCALE = 0.012f;
    private static final int LINE_HEIGHT = 12;
    private static final int MAX_LINES = 11;

    private final SmallChalkboardModel small;
    private final SmallChalkboardModel large;

    public ChalkboardBlockEntityRender(BlockEntityRendererProvider.Context context) {
        super(context);
        this.small = new SmallChalkboardModel(context.bakeLayer(SmallChalkboardModel.LAYER_LOCATION));
        this.large = new SmallChalkboardModel(context.bakeLayer(LargeChalkboardModel.LAYER_LOCATION));
    }

    @Override
    public void extractRenderState(ChalkboardBlockEntity blockEntity, ChalkboardBlockEntityRenderState blockEntityRenderState, float f, @NonNull Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        blockEntityRenderState.large = blockEntity.isLarge();
    }


    @Override
    public ChalkboardBlockEntityRenderState createRenderState() {
        return new ChalkboardBlockEntityRenderState();
    }

    @Override
    protected void renderModel(ChalkboardBlockEntityRenderState textBlockRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, Direction facing) {
        poseStack.pushPose();

        poseStack.translate(0.5, 1.5, 0.5);
        poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        poseStack.mulPose(Axis.YN.rotationDegrees(180 - facing.get2DDataValue() * 90));

        if (textBlockRenderState.large) {
            submitNodeCollector.submitModel(large, new SmallChalkboardModel.State(), poseStack, RenderTypes.entitySolid(LARGE_TEXTURE), textBlockRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0, null);
        }else {
            submitNodeCollector.submitModel(small, new SmallChalkboardModel.State(), poseStack, RenderTypes.entitySolid(SMALL_TEXTURE), textBlockRenderState.lightCoords, OverlayTexture.NO_OVERLAY, 0, null);
        }
        poseStack.popPose();
    }

    @Override
    protected void renderText(ChalkboardBlockEntityRenderState textBlockRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, Direction facing) {
        poseStack.pushPose();
        if (facing == Direction.EAST) {
            poseStack.translate(0.08, 1.535, 0.5);
        } else if (facing == Direction.WEST) {
            poseStack.translate(0.92, 1.535, 0.5);
        } else if (facing == Direction.SOUTH) {
            poseStack.translate(0.5, 1.535, 0.08);
        } else {
            poseStack.translate(0.5, 1.535, 0.92);
        }

        poseStack.mulPose(Axis.YN.rotationDegrees(facing.get2DDataValue() * 90));

        int maxWidth = textBlockRenderState.large ? 232 : 63;
        doTextRender(textBlockRenderState, poseStack, textBlockRenderState.text, maxWidth, TEXT_SCALE, MAX_LINES, LINE_HEIGHT);

        poseStack.popPose();
    }
}
