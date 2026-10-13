package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.fluid.JuiceFluid;
import com.github.ysbbbbbb.kaleidoscopetavern.util.PortHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import org.jspecify.annotations.NonNull;

public final class ModFluids {

    public static final FlowingFluid GRAPE_JUICE = new JuiceFluid(
            () -> ModItems.GRAPE_BUCKET,
            () -> ModFluids.GRAPE_JUICE_BLOCK
    );
    public static final LiquidBlock GRAPE_JUICE_BLOCK = new LiquidBlock(GRAPE_JUICE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).setId(PortHelper.createBlockId("grape_juice")));

    public static final FlowingFluid ICE_GRAPE_JUICE = new JuiceFluid(
            () -> ModItems.ICE_GRAPE_BUCKET,
            () -> ModFluids.ICE_GRAPE_JUICE_BLOCK
    );
    public static final LiquidBlock ICE_GRAPE_JUICE_BLOCK = new LiquidBlock(ICE_GRAPE_JUICE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).setId(PortHelper.createBlockId("ice_grape_juice")));

    public static final FlowingFluid GOLD_GRAPE_JUICE = new JuiceFluid(
            () -> ModItems.GOLD_GRAPE_BUCKET,
            () -> ModFluids.GOLD_GRAPE_JUICE_BLOCK
    );
    public static final LiquidBlock GOLD_GRAPE_JUICE_BLOCK = new LiquidBlock(GOLD_GRAPE_JUICE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).setId(PortHelper.createBlockId("gold_grape_juice")));

    public static final FlowingFluid GREEN_GRAPE_JUICE = new JuiceFluid(
            () -> ModItems.GREEN_GRAPE_BUCKET,
            () -> ModFluids.GREEN_GRAPE_JUICE_BLOCK
    );
    public static final LiquidBlock GREEN_GRAPE_JUICE_BLOCK = new LiquidBlock(GREEN_GRAPE_JUICE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).setId(PortHelper.createBlockId("green_grape_juice")));

    public static final FlowingFluid SWEET_BERRIES_JUICE = new JuiceFluid(
            () -> ModItems.SWEET_BERRIES_BUCKET,
            () -> ModFluids.SWEET_BERRIES_JUICE_BLOCK
    );
    public static final LiquidBlock SWEET_BERRIES_JUICE_BLOCK = new LiquidBlock(SWEET_BERRIES_JUICE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).setId(PortHelper.createBlockId("sweet_berries_juice")));

    public static final FlowingFluid GLOW_BERRIES_JUICE = new JuiceFluid(
            () -> ModItems.GLOW_BERRIES_BUCKET,
            () -> ModFluids.GLOW_BERRIES_JUICE_BLOCK
    );
    public static final LiquidBlock GLOW_BERRIES_JUICE_BLOCK = new LiquidBlock(GLOW_BERRIES_JUICE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).setId(PortHelper.createBlockId("glow_berries_juice")));

    public static void registerFluids() {
        register("grape_juice", GRAPE_JUICE, GRAPE_JUICE_BLOCK);
        register("ice_grape_juice", ICE_GRAPE_JUICE, ICE_GRAPE_JUICE_BLOCK);
        register("gold_grape_juice", GOLD_GRAPE_JUICE, GOLD_GRAPE_JUICE_BLOCK);
        register("green_grape_juice", GREEN_GRAPE_JUICE, GREEN_GRAPE_JUICE_BLOCK);
        register("sweet_berries_juice", SWEET_BERRIES_JUICE, SWEET_BERRIES_JUICE_BLOCK);
        register("glow_berries_juice", GLOW_BERRIES_JUICE, GLOW_BERRIES_JUICE_BLOCK);
    }

    @Environment(EnvType.CLIENT)
    public static void registerFluidRenderers() {
        registerRender(GRAPE_JUICE, "block/grape_juice_still", "block/grape_juice_flow", 0xFFFFFFFF);
        registerRender(ICE_GRAPE_JUICE, "block/ice_grape_juice_still", "block/ice_grape_juice_flow", 0xFFFFFFFF);
        registerRender(GOLD_GRAPE_JUICE, "block/gold_grape_juice_still", "block/gold_grape_juice_flow", 0xFFFFFFFF);
        registerRender(GREEN_GRAPE_JUICE, "block/green_grape_juice_still", "block/green_grape_juice_flow", 0xFFFFFFFF);
        registerRender(SWEET_BERRIES_JUICE, "block/sweet_berries_juice_still", "block/sweet_berries_juice_flow", 0xFFFFFFFF);
        registerRender(GLOW_BERRIES_JUICE, "block/glow_berries_juice_still", "block/glow_berries_juice_flow", 0xFFFFFFFF);
    }

    public static void init() {

    }

    private static void register(String name, FlowingFluid fluid, Block block) {
        Registry.register(BuiltInRegistries.FLUID, id(name), fluid);
        Registry.register(BuiltInRegistries.BLOCK, id(name), block);
    }

    @Environment(EnvType.CLIENT)
    private static void registerRender(Fluid fluid, String stillTexture, String flowTexture, int color) {
        Identifier stillId = id(stillTexture);
        Identifier flowId = id(flowTexture);
        Material stillMaterial = new Material(stillId);
        Material flowMaterial = new Material(flowId);
        FluidRenderingRegistry.register(fluid, new FluidModel.Unbaked(stillMaterial, flowMaterial, null, null), FluidRenderingRegistry.get(fluid));
        registration(fluid, color);
    }

    @Environment(EnvType.CLIENT)
    private static void registration(Fluid fluid, int color) {
        FluidVariantRendering.register(fluid, new FluidVariantRenderHandler() {
            @Override
            public int getColor(@NonNull FluidVariant fluidVariant, BlockAndTintGetter view, BlockPos pos) {
                return color;
            }
        });
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, path);
    }
}
