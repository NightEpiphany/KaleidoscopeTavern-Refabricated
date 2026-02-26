package com.github.ysbbbbbb.kaleidoscopetavern.compact.jade.components;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IBarrel;
import com.github.ysbbbbbb.kaleidoscopetavern.compact.jade.ModJadePlugin;
import com.github.ysbbbbbb.kaleidoscopetavern.util.fluids.CustomFluidTank;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.fluid.JadeFluidObject;
import snownee.jade.api.ui.IElementHelper;

@SuppressWarnings("UnstableApiUsage")
public class BarrelComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    public static final BarrelComponentProvider INSTANCE = new BarrelComponentProvider();
    private static final String KEY_FLUID = "kt_fluid";
    private static final String KEY_AMOUNT = "kt_amount";
    private static final String KEY_CAPACITY = "kt_capacity";
    private static final String KEY_NBT = "kt_nbt";

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        CompoundTag data = blockAccessor.getServerData();
        int capacity = data.getInt(KEY_CAPACITY);
        if (capacity <= 0) {
            capacity = IBarrel.MAX_FLUID_AMOUNT;
        }

        String fluidId = data.getString(KEY_FLUID);
        int amount = Math.max(0, data.getInt(KEY_AMOUNT));
        if (fluidId.isEmpty()) {
            iTooltip.add(Component.literal("Liquid: Empty " + amount + "/" + capacity + " mB"));
            return;
        }

        Fluid fluid = BuiltInRegistries.FLUID.get(new ResourceLocation(fluidId));
        CompoundTag nbt = data.contains(KEY_NBT, Tag.TAG_COMPOUND) ? data.getCompound(KEY_NBT) : null;
        FluidVariant variant = nbt == null ? FluidVariant.of(fluid) : FluidVariant.of(fluid, nbt);
        if (!variant.isBlank()) {
            IElementHelper helper = IElementHelper.get();
            iTooltip.add(helper.fluid(nbt == null ? JadeFluidObject.of(fluid, amount) : JadeFluidObject.of(fluid, amount, nbt)));
            Component name = FluidVariantAttributes.getName(variant);
            iTooltip.add(Component.literal("Liquid: ").append(name).append(Component.literal(" " + amount + "/" + capacity + " mB")));
        } else {
            iTooltip.add(Component.literal("Liquid: Empty " + amount + "/" + capacity + " mB"));
        }
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor blockAccessor) {
        if (!(blockAccessor.getBlockEntity() instanceof IBarrel barrel)) {
            return;
        }
        CustomFluidTank tank = barrel.getFluid();
        if (tank == null) {
            return;
        }
        data.putInt(KEY_CAPACITY, IBarrel.MAX_FLUID_AMOUNT);
        int amount = tank.getFluidAmountMb();
        if (amount <= 0 || tank.getFluidVariant().isBlank()) {
            data.putString(KEY_FLUID, "");
            data.putInt(KEY_AMOUNT, 0);
            data.remove(KEY_NBT);
            return;
        }
        FluidVariant variant = tank.getFluidVariant();
        ResourceLocation id = BuiltInRegistries.FLUID.getKey(variant.getFluid());
        data.putString(KEY_FLUID, id.toString());
        data.putInt(KEY_AMOUNT, amount);
        if (variant.getNbt() != null) {
            data.put(KEY_NBT, variant.getNbt().copy());
        } else {
            data.remove(KEY_NBT);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ModJadePlugin.BARREL;
    }
}
