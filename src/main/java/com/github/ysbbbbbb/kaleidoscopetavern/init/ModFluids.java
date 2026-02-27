package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.fluid.JuiceFluid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class ModFluids {

    public static final Map<Fluid, Item> SELECT_BUCKETS = new HashMap<>();

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
    public static final LiquidBlock GRAPE_JUICE_BLOCK = new LiquidBlock(GRAPE_JUICE, BlockBehaviour.Properties.copy(Blocks.WATER));

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
    public static final LiquidBlock SWEET_BERRIES_JUICE_BLOCK = new LiquidBlock(SWEET_BERRIES_JUICE, BlockBehaviour.Properties.copy(Blocks.WATER));

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
    public static final LiquidBlock GLOW_BERRIES_JUICE_BLOCK = new LiquidBlock(GLOW_BERRIES_JUICE, BlockBehaviour.Properties.copy(Blocks.WATER));

    public static void registerFluids() {
        register("grape_juice", GRAPE_JUICE, FLOWING_GRAPE_JUICE, GRAPE_JUICE_BLOCK, ModItems.GRAPE_BUCKET);
        register("sweet_berries_juice", SWEET_BERRIES_JUICE, FLOWING_SWEET_BERRIES_JUICE, SWEET_BERRIES_JUICE_BLOCK, ModItems.SWEET_BERRIES_BUCKET);
        register("glow_berries_juice", GLOW_BERRIES_JUICE, FLOWING_GLOW_BERRIES_JUICE, GLOW_BERRIES_JUICE_BLOCK, ModItems.GLOW_BERRIES_BUCKET);
    }

    @Environment(EnvType.CLIENT)
    public static void registerFluidRenderers() {
        registerRender(GRAPE_JUICE, FLOWING_GRAPE_JUICE, "block/grape_juice_still", "block/grape_juice_flow", 0xFFFFFFFF);
        registerRender(SWEET_BERRIES_JUICE, FLOWING_SWEET_BERRIES_JUICE, "block/sweet_berries_juice_still", "block/sweet_berries_juice_flow", 0xFFFFFFFF);
        registerRender(GLOW_BERRIES_JUICE, FLOWING_GLOW_BERRIES_JUICE, "block/glow_berries_juice_still", "block/glow_berries_juice_flow", 0xFFFFFFFF);
    }

    private static void register(String name,
                                 FlowingFluid still,
                                 FlowingFluid flowing,
                                 Block block,
                                 Item bucket) {
        Registry.register(BuiltInRegistries.FLUID, id(name), still);
        Registry.register(BuiltInRegistries.FLUID, id("flowing_" + name), flowing);
        Registry.register(BuiltInRegistries.BLOCK, id(name), block);
        SELECT_BUCKETS.put(still, bucket);
        SELECT_BUCKETS.put(flowing, bucket);
    }

    @Environment(EnvType.CLIENT)
    private static void registerRender(Fluid still, Fluid flowing, String stillTexture, String flowTexture, int color) {
        ResourceLocation stillId = id(stillTexture);
        ResourceLocation flowId = id(flowTexture);
        FluidRenderHandlerRegistry.INSTANCE.register(still, flowing, new SimpleFluidRenderHandler(stillId, flowId, color));
        registration(still, color, stillId, flowId);
        registration(flowing, color, stillId, flowId);
    }

    private static void registration(Fluid fluid, int color, ResourceLocation stillId, ResourceLocation flowId) {
        FluidVariantRendering.register(fluid, new FluidVariantRenderHandler() {
            @Override
            public TextureAtlasSprite[] getSprites(FluidVariant fluidVariant) {
                TextureAtlasSprite stillSprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillId);
                TextureAtlasSprite flowSprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(flowId);
                return new TextureAtlasSprite[]{stillSprite, flowSprite};
            }

            @Override
            public int getColor(FluidVariant fluidVariant, net.minecraft.world.level.BlockAndTintGetter view, net.minecraft.core.BlockPos pos) {
                return color;
            }
        });
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(KaleidoscopeTavern.MOD_ID, path);
    }
}
