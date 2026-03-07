package com.github.ysbbbbbb.kaleidoscopetavern.compat.jade.block;

import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.jade.ModJadePlugin;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.JadeUI;

public class PressingTubComponentProvider implements IBlockComponentProvider {
    public static final PressingTubComponentProvider INSTANCE = new PressingTubComponentProvider();
    private static final String KEY_FLUID = "kt_fluid";
    private static final String KEY_AMOUNT = "kt_amount";
    private static final String KEY_CAPACITY = "kt_capacity";
    private static final Identifier PROGRESS_BASE = Identifier.fromNamespaceAndPath("jade", "navbar_background");
    private static final Identifier PROGRESS_FILL = Identifier.fromNamespaceAndPath("jade", "progressbar");

    @Override
    public void appendTooltip(@NonNull ITooltip iTooltip, BlockAccessor blockAccessor, @NonNull IPluginConfig iPluginConfig) {
        CompoundTag data = blockAccessor.getServerData();
        int capacity = data.getInt(KEY_CAPACITY).orElse(0);
        if (capacity <= 0) {
            capacity = IPressingTub.MAX_FLUID_AMOUNT;
        }

        String fluidId = data.getString(KEY_FLUID).orElse("");
        int amount = Math.max(0, data.getInt(KEY_AMOUNT).orElse(0));
        float ratio = capacity > 0 ? Math.min(1F, amount / (float) capacity) : 0F;
        if (fluidId.isEmpty() || amount == 0) {
            iTooltip.add(
                    JadeUI.progress(
                            ratio,
                            PROGRESS_BASE,
                            PROGRESS_FILL,
                            185,
                            15,
                            Component.literal("Liquid: Empty " + amount + "/" + capacity + " mB").withStyle(ChatFormatting.WHITE),
                            JadeUI.progressStyle()
                    )
            );
            return;
        }

        Identifier fluidRegistryId = Identifier.parse(fluidId);
        Fluid fluid = BuiltInRegistries.FLUID.getValue(fluidRegistryId);
        var variant = net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant.of(fluid);
        if (!variant.isBlank()) {
            Component name = FluidVariantAttributes.getName(variant);
            iTooltip.add(
                    JadeUI.progress(
                            ratio,
                            PROGRESS_BASE,
                            PROGRESS_FILL,
                            185,
                            15,
                            Component.literal("Liquid: ").append(name).append(Component.literal(" " + amount + "/" + capacity + " mB")),
                            JadeUI.progressStyle()
                    )
            );
        } else {
            iTooltip.add(
                    JadeUI.progress(
                            ratio,
                            PROGRESS_BASE,
                            PROGRESS_FILL,
                            185,
                            15,
                            Component.literal("Liquid: Empty " + amount + "/" + capacity + " mB").withStyle(ChatFormatting.WHITE),
                            JadeUI.progressStyle()
                    )
            );
        }
    }

    @Override
    public @NonNull Identifier getUid() {
        return ModJadePlugin.PRESSING_TUB;
    }
}
