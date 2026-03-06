package com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class BarCabinetBlockEntityRenderState extends BlockEntityRenderState {
    public Direction facing;
    public ItemStack leftItem = ItemStack.EMPTY;
    public ItemStack rightItem = ItemStack.EMPTY;
    public boolean isSingle = true;
}
