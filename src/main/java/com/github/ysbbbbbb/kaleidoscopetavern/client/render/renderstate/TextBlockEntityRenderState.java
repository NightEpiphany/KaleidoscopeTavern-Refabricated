package com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate;

import com.github.ysbbbbbb.kaleidoscopetavern.util.TextAlignment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;

@Environment(EnvType.CLIENT)
public class TextBlockEntityRenderState extends BlockEntityRenderState {
    public Direction facing = Direction.NORTH;
    public String text = "";
    public DyeColor color = DyeColor.WHITE;
    public boolean glowing = false;
    public TextAlignment textAlignment;
}
