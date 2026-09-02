package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.fluid.JuiceFluid;
import com.github.ysbbbbbb.kaleidoscopetavern.util.PortHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public final class ModFluids {

    public static final FlowingFluid GRAPE_JUICE = new JuiceFluid.Still(
            () -> ModFluids.FLOWING_GRAPE_JUICE,
            () -> ModFluids.GRAPE_JUICE,
            () -> ModItems.GRAPE_BUCKET,
            () -> ModFluids.GRAPE_JUICE_BLOCK
    );
    public static final FlowingFluid FLOWING_GRAPE_JUICE = new JuiceFluid.Flowing(
            () -> ModFluids.FLOWING_GRAPE_JUICE,
            () -> ModFluids.GRAPE_JUICE,
            () -> ModItems.GRAPE_BUCKET,
            () -> ModFluids.GRAPE_JUICE_BLOCK
    );
    public static final LiquidBlock GRAPE_JUICE_BLOCK = new LiquidBlock(GRAPE_JUICE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).setId(PortHelper.createBlockId("grape_juice")));

    public static final FlowingFluid SWEET_BERRIES_JUICE = new JuiceFluid.Still(
            () -> ModFluids.FLOWING_SWEET_BERRIES_JUICE,
            () -> ModFluids.SWEET_BERRIES_JUICE,
            () -> ModItems.SWEET_BERRIES_BUCKET,
            () -> ModFluids.SWEET_BERRIES_JUICE_BLOCK
    );
    public static final FlowingFluid FLOWING_SWEET_BERRIES_JUICE = new JuiceFluid.Flowing(
            () -> ModFluids.FLOWING_SWEET_BERRIES_JUICE,
            () -> ModFluids.SWEET_BERRIES_JUICE,
            () -> ModItems.SWEET_BERRIES_BUCKET,
            () -> ModFluids.SWEET_BERRIES_JUICE_BLOCK
    );
    public static final LiquidBlock SWEET_BERRIES_JUICE_BLOCK = new LiquidBlock(SWEET_BERRIES_JUICE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).setId(PortHelper.createBlockId("sweet_berries_juice")));

    public static final FlowingFluid GLOW_BERRIES_JUICE = new JuiceFluid.Still(
            () -> ModFluids.FLOWING_GLOW_BERRIES_JUICE,
            () -> ModFluids.GLOW_BERRIES_JUICE,
            () -> ModItems.GLOW_BERRIES_BUCKET,
            () -> ModFluids.GLOW_BERRIES_JUICE_BLOCK
    );
    public static final FlowingFluid FLOWING_GLOW_BERRIES_JUICE = new JuiceFluid.Flowing(
            () -> ModFluids.FLOWING_GLOW_BERRIES_JUICE,
            () -> ModFluids.GLOW_BERRIES_JUICE,
            () -> ModItems.GLOW_BERRIES_BUCKET,
            () -> ModFluids.GLOW_BERRIES_JUICE_BLOCK
    );
    public static final LiquidBlock GLOW_BERRIES_JUICE_BLOCK = new LiquidBlock(GLOW_BERRIES_JUICE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).setId(PortHelper.createBlockId("glow_berries_juice")));

    public static final FlowingFluid ICE_GRAPE_JUICE = new JuiceFluid.Still(
            () -> ModFluids.FLOWING_ICE_GRAPE_JUICE,
            () -> ModFluids.ICE_GRAPE_JUICE,
            () -> ModItems.ICE_GRAPE_BUCKET,
            () -> ModFluids.ICE_GRAPE_JUICE_BLOCK
    );
    public static final FlowingFluid FLOWING_ICE_GRAPE_JUICE = new JuiceFluid.Flowing(
            () -> ModFluids.FLOWING_ICE_GRAPE_JUICE,
            () -> ModFluids.ICE_GRAPE_JUICE,
            () -> ModItems.ICE_GRAPE_BUCKET,
            () -> ModFluids.ICE_GRAPE_JUICE_BLOCK
    );
    public static final LiquidBlock ICE_GRAPE_JUICE_BLOCK = new LiquidBlock(ICE_GRAPE_JUICE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).setId(PortHelper.createBlockId("ice_grape_juice")));

    public static final FlowingFluid GOLD_GRAPE_JUICE = new JuiceFluid.Still(
            () -> ModFluids.FLOWING_GOLD_GRAPE_JUICE,
            () -> ModFluids.GOLD_GRAPE_JUICE,
            () -> ModItems.GOLD_GRAPE_BUCKET,
            () -> ModFluids.GOLD_GRAPE_JUICE_BLOCK
    );
    public static final FlowingFluid FLOWING_GOLD_GRAPE_JUICE = new JuiceFluid.Flowing(
            () -> ModFluids.FLOWING_GOLD_GRAPE_JUICE,
            () -> ModFluids.GOLD_GRAPE_JUICE,
            () -> ModItems.GOLD_GRAPE_BUCKET,
            () -> ModFluids.GOLD_GRAPE_JUICE_BLOCK
    );
    public static final LiquidBlock GOLD_GRAPE_JUICE_BLOCK = new LiquidBlock(GOLD_GRAPE_JUICE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).setId(PortHelper.createBlockId("gold_grape_juice")));

    public static final FlowingFluid GREEN_GRAPE_JUICE = new JuiceFluid.Still(
            () -> ModFluids.FLOWING_GREEN_GRAPE_JUICE,
            () -> ModFluids.GREEN_GRAPE_JUICE,
            () -> ModItems.GREEN_GRAPE_BUCKET,
            () -> ModFluids.GREEN_GRAPE_JUICE_BLOCK
    );
    public static final FlowingFluid FLOWING_GREEN_GRAPE_JUICE = new JuiceFluid.Flowing(
            () -> ModFluids.FLOWING_GREEN_GRAPE_JUICE,
            () -> ModFluids.GREEN_GRAPE_JUICE,
            () -> ModItems.GREEN_GRAPE_BUCKET,
            () -> ModFluids.GREEN_GRAPE_JUICE_BLOCK
    );
    public static final LiquidBlock GREEN_GRAPE_JUICE_BLOCK = new LiquidBlock(GREEN_GRAPE_JUICE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).setId(PortHelper.createBlockId("green_grape_juice")));

    public static void registerFluids() {
        register("grape_juice", GRAPE_JUICE, FLOWING_GRAPE_JUICE, GRAPE_JUICE_BLOCK, ModItems.GRAPE_BUCKET);
        register("sweet_berries_juice", SWEET_BERRIES_JUICE, FLOWING_SWEET_BERRIES_JUICE, SWEET_BERRIES_JUICE_BLOCK, ModItems.SWEET_BERRIES_BUCKET);
        register("glow_berries_juice", GLOW_BERRIES_JUICE, FLOWING_GLOW_BERRIES_JUICE, GLOW_BERRIES_JUICE_BLOCK, ModItems.GLOW_BERRIES_BUCKET);
        register("ice_grape_juice", ICE_GRAPE_JUICE, FLOWING_ICE_GRAPE_JUICE, ICE_GRAPE_JUICE_BLOCK, ModItems.ICE_GRAPE_BUCKET);
        register("gold_grape_juice", GOLD_GRAPE_JUICE, FLOWING_GOLD_GRAPE_JUICE, GOLD_GRAPE_JUICE_BLOCK, ModItems.GOLD_GRAPE_BUCKET);
        register("green_grape_juice", GREEN_GRAPE_JUICE, FLOWING_GREEN_GRAPE_JUICE, GREEN_GRAPE_JUICE_BLOCK, ModItems.GREEN_GRAPE_BUCKET);
    }

    @Environment(EnvType.CLIENT)
    public static void registerFluidRenderers() {
        registerRender(GRAPE_JUICE, FLOWING_GRAPE_JUICE, "block/grape_juice_still", "block/grape_juice_flow", 0xFFFFFFFF);
        registerRender(SWEET_BERRIES_JUICE, FLOWING_SWEET_BERRIES_JUICE, "block/sweet_berries_juice_still", "block/sweet_berries_juice_flow", 0xFFFFFFFF);
        registerRender(GLOW_BERRIES_JUICE, FLOWING_GLOW_BERRIES_JUICE, "block/glow_berries_juice_still", "block/glow_berries_juice_flow", 0xFFFFFFFF);
        registerRender(ICE_GRAPE_JUICE, FLOWING_ICE_GRAPE_JUICE, "block/ice_grape_juice_still", "block/ice_grape_juice_flow", 0xFFFFFFFF);
        registerRender(GOLD_GRAPE_JUICE, FLOWING_GOLD_GRAPE_JUICE, "block/gold_grape_juice_still", "block/gold_grape_juice_flow", 0xFFFFFFFF);
        registerRender(GREEN_GRAPE_JUICE, FLOWING_GREEN_GRAPE_JUICE, "block/green_grape_juice_still", "block/green_grape_juice_flow", 0xFFFFFFFF);
    }

    private static void register(String name,
                                 FlowingFluid still,
                                 FlowingFluid flowing,
                                 Block block,
                                 Item bucket) {
        Registry.register(BuiltInRegistries.FLUID, id(name), still);
        Registry.register(BuiltInRegistries.FLUID, id("flowing_" + name), flowing);
        Registry.register(BuiltInRegistries.BLOCK, id(name), block);
    }

    @Environment(EnvType.CLIENT)
    private static void registerRender(Fluid still, Fluid flowing, String stillTexture, String flowTexture, int color) {
        Identifier stillId = id(stillTexture);
        Identifier flowId = id(flowTexture);
        FluidRenderHandlerRegistry.INSTANCE.register(still, flowing, new SimpleFluidRenderHandler(stillId, flowId, stillId, color));
        registration(still, color, stillId, flowId);
        registration(flowing, color, stillId, flowId);
    }

    @Environment(EnvType.CLIENT)
    private static void registration(Fluid fluid, int color, Identifier stillId, Identifier flowId) {
        FluidVariantRendering.register(fluid, new FluidVariantRenderHandler() {
            @Override
            public TextureAtlasSprite[] getSprites(FluidVariant fluidVariant) {
                TextureAtlas stillAtlas = (TextureAtlas) Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS);
                TextureAtlasSprite stillSprite = stillAtlas.getSprite(stillId);
                TextureAtlasSprite flowSprite = stillAtlas.getSprite(flowId);
                return new TextureAtlasSprite[]{stillSprite, flowSprite};
            }

            @Override
            public int getColor(FluidVariant fluidVariant, net.minecraft.world.level.BlockAndTintGetter view, net.minecraft.core.BlockPos pos) {
                return color;
            }
        });
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, path);
    }
}
