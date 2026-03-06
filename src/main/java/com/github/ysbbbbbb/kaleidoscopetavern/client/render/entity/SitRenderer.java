package com.github.ysbbbbbb.kaleidoscopetavern.client.render.entity;


import com.github.ysbbbbbb.kaleidoscopetavern.entity.SitEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

@Deprecated
@Environment(EnvType.CLIENT)
public class SitRenderer extends EntityRenderer<SitEntity, EntityRenderState> {

    public SitRenderer(EntityRendererProvider.Context context) {
        super(context);
    }


    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }


}
