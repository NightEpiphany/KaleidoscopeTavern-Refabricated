package com.github.ysbbbbbb.kaleidoscopetavern.client.model.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class LargeChalkboardModel extends Model<LargeChalkboardModel.State> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "large_chalk"), "main");
    private final ModelPart bone;

    public LargeChalkboardModel(ModelPart root) {
        super(root, RenderTypes::entitySolid);
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-32.0F, -30.0F, 15.0F, 48.0F, 28.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Environment(EnvType.CLIENT)
    public record State() {
    }
}
