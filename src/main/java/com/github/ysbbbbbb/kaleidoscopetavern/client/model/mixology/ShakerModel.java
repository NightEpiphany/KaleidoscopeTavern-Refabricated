package com.github.ysbbbbbb.kaleidoscopetavern.client.model.mixology;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.client.animation.ShakerAnimation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class ShakerModel extends Model<ShakerModel.State> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "shaker"), "main");
    private final ModelPart root;
    private final KeyframeAnimation putAnimation;

    public ShakerModel(ModelPart modelPart) {
        super(modelPart, RenderTypes::entityCutoutNoCull);
        this.root = modelPart.getChild("root");
        this.putAnimation = ShakerAnimation.PUT.bake(this.root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(23, 28).addBox(-3.0F, 0.75F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(2, 2).addBox(-3.5F, -6.25F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.25F, 0.0F));

        PartDefinition bone2 = root.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(3, 36).addBox(-1.5F, -3.8333F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(2, 2).addBox(-3.5F, 0.1667F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 46).addBox(-3.0F, -1.8333F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.4167F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(State state) {
        // 手动 resetPose 后根据 State 应用加酒动画（不调用 super，由本方法全权控制姿势）
        this.root.getAllParts().forEach(part -> part.resetPose());
        if (state.lastPutTick > 0) {
            float elapsed = state.ageInTicks - state.lastPutTick;
            if (elapsed >= 0.0F && elapsed < 10.0F) {
                AnimationState putState = new AnimationState();
                putState.start(state.lastPutTick);
                this.putAnimation.apply(putState, state.ageInTicks, 1.0F);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public record State(float ageInTicks, int lastPutTick) {
        public State() {
            this(0.0F, 0);
        }
    }
}