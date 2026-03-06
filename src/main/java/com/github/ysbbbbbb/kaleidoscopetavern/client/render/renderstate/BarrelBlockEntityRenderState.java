package com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate;

import com.github.ysbbbbbb.kaleidoscopetavern.util.fluids.CustomFluidTank;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.ItemStackHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class BarrelBlockEntityRenderState extends BlockEntityRenderState {
    public Direction facing;
    public boolean isOpen = true;
    public CustomFluidTank fluidTank;
    public float time;
    public long seed;
    public ItemStackHandler items;
    public List<ItemStackRenderState> itemStates = Collections.emptyList();
}
