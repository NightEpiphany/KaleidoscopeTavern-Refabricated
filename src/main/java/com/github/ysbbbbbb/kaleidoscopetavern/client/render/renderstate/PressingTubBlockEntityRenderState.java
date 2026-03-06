package com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate;

import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.ItemStackHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.material.Fluid;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class PressingTubBlockEntityRenderState extends BlockEntityRenderState {
    public ItemStackHandler items;
    public Direction facing;
    public boolean tilt;
    public long seed;
    public ItemStackRenderState itemState = new ItemStackRenderState();
    public Fluid fluid;
    public int fluidAmount;
}
