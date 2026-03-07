package com.github.ysbbbbbb.kaleidoscopetavern.compat.jade.block;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.jade.ModJadePlugin;
import com.github.ysbbbbbb.kaleidoscopetavern.util.fluids.CustomFluidTank;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public enum PressingTubDataProvider implements IServerDataProvider<BlockAccessor> {
    INSTANCE;
    private static final String KEY_FLUID = "kt_fluid";
    private static final String KEY_AMOUNT = "kt_amount";
    private static final String KEY_CAPACITY = "kt_capacity";
    private static final String KEY_NBT = "kt_nbt";

    @Override
    public void appendServerData(@NonNull CompoundTag data, BlockAccessor blockAccessor) {
        if (!(blockAccessor.getBlockEntity() instanceof IPressingTub pressingTub)) {
            return;
        }
        CustomFluidTank tank = pressingTub.getFluid();
        if (tank == null) {
            return;
        }
        data.putInt(KEY_CAPACITY, IPressingTub.MAX_FLUID_AMOUNT);
        int amount = tank.getFluidAmountMb();
        if (amount <= 0 || tank.getFluidVariant().isBlank()) {
            data.putString(KEY_FLUID, "");
            data.putInt(KEY_AMOUNT, 0);
            data.remove(KEY_NBT);
            return;
        }
        FluidVariant variant = tank.getFluidVariant();
        Identifier id = BuiltInRegistries.FLUID.getKey(variant.getFluid());
        RegistryOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, blockAccessor.getLevel().registryAccess());
        data.putString(KEY_FLUID, id.toString());
        data.putInt(KEY_AMOUNT, amount);
        Tag encoded = FluidVariant.CODEC.encodeStart(ops, variant).result().orElse(new CompoundTag());
        data.put(KEY_NBT, encoded);
    }

    @Override
    public @NonNull Identifier getUid() {
        return ModJadePlugin.PRESSING_TUB;
    }
}
