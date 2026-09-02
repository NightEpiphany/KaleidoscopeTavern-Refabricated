package com.github.ysbbbbbb.kaleidoscopetavern.client.render.renderstate;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class StorageBlockEntityRenderState extends BlockEntityRenderState {
    public Direction facing = Direction.NORTH;
    public List<ItemStack> items = new ArrayList<>();
}