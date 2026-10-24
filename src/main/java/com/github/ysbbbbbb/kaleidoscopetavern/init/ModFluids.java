package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.rrv.ModRRVFluidItems;
import com.github.ysbbbbbb.kaleidoscopetavern.fluid.JuiceFluid;
import com.github.ysbbbbbb.kaleidoscopetavern.init.registery.ids.ModBlockIds;
import com.github.ysbbbbbb.kaleidoscopetavern.init.registery.ids.ModFluidIds;
import com.github.ysbbbbbb.kaleidoscopetavern.init.registery.ids.ModItemIds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.sounds.SoundEvents;
import org.jspecify.annotations.NonNull;

public final class ModFluids {

    public static final FlowingFluid GRAPE_JUICE = new JuiceFluid(
            Fluid.Properties.of()
                    .drag(0.7F)
                    .bucketItem(ModItemIds.GRAPE_BUCKET)
                    .dripParticle(ParticleTypes.BUBBLE)
                    .soundEvent(SoundEvents.BUCKET_FILL),
            ModBlockIds.GRAPE_JUICE
    );
    public static final LiquidBlock GRAPE_JUICE_BLOCK = liquidBlock(GRAPE_JUICE, ModBlockIds.GRAPE_JUICE);

    public static final FlowingFluid ICE_GRAPE_JUICE = new JuiceFluid(
            Fluid.Properties.of()
                    .drag(0.7F)
                    .bucketItem(ModItemIds.ICE_GRAPE_BUCKET)
                    .dripParticle(ParticleTypes.BUBBLE)
                    .soundEvent(SoundEvents.BUCKET_FILL),
            ModBlockIds.ICE_GRAPE_JUICE
    );
    public static final LiquidBlock ICE_GRAPE_JUICE_BLOCK = liquidBlock(ICE_GRAPE_JUICE, ModBlockIds.ICE_GRAPE_JUICE);

    public static final FlowingFluid GOLD_GRAPE_JUICE = new JuiceFluid(
            Fluid.Properties.of()
                    .drag(0.7F)
                    .bucketItem(ModItemIds.GOLD_GRAPE_BUCKET)
                    .dripParticle(ParticleTypes.BUBBLE)
                    .soundEvent(SoundEvents.BUCKET_FILL),
            ModBlockIds.GOLD_GRAPE_JUICE
    );
    public static final LiquidBlock GOLD_GRAPE_JUICE_BLOCK = liquidBlock(GOLD_GRAPE_JUICE, ModBlockIds.GOLD_GRAPE_JUICE);

    public static final FlowingFluid GREEN_GRAPE_JUICE = new JuiceFluid(
            Fluid.Properties.of()
                    .drag(0.7F)
                    .bucketItem(ModItemIds.GREEN_GRAPE_BUCKET)
                    .dripParticle(ParticleTypes.BUBBLE)
                    .soundEvent(SoundEvents.BUCKET_FILL),
            ModBlockIds.GREEN_GRAPE_JUICE
    );
    public static final LiquidBlock GREEN_GRAPE_JUICE_BLOCK = liquidBlock(GREEN_GRAPE_JUICE, ModBlockIds.GREEN_GRAPE_JUICE);

    public static final FlowingFluid SWEET_BERRIES_JUICE = new JuiceFluid(
            Fluid.Properties.of()
                    .drag(0.7F)
                    .bucketItem(ModItemIds.SWEET_BERRIES_BUCKET)
                    .dripParticle(ParticleTypes.BUBBLE)
                    .soundEvent(SoundEvents.BUCKET_FILL),
            ModBlockIds.SWEET_BERRIES_JUICE
    );
    public static final LiquidBlock SWEET_BERRIES_JUICE_BLOCK = liquidBlock(SWEET_BERRIES_JUICE, ModBlockIds.SWEET_BERRIES_JUICE);

    public static final FlowingFluid GLOW_BERRIES_JUICE = new JuiceFluid(
            Fluid.Properties.of()
                    .drag(0.7F)
                    .bucketItem(ModItemIds.GLOW_BERRIES_BUCKET)
                    .dripParticle(ParticleTypes.BUBBLE)
                    .soundEvent(SoundEvents.BUCKET_FILL),
            ModBlockIds.GLOW_BERRIES_JUICE
    );
    public static final LiquidBlock GLOW_BERRIES_JUICE_BLOCK = liquidBlock(GLOW_BERRIES_JUICE, ModBlockIds.GLOW_BERRIES_JUICE);

    public static void registerFluids() {
        register(ModFluidIds.GRAPE_JUICE, GRAPE_JUICE, ModBlockIds.GRAPE_JUICE, GRAPE_JUICE_BLOCK);
        register(ModFluidIds.ICE_GRAPE_JUICE, ICE_GRAPE_JUICE, ModBlockIds.ICE_GRAPE_JUICE, ICE_GRAPE_JUICE_BLOCK);
        register(ModFluidIds.GOLD_GRAPE_JUICE, GOLD_GRAPE_JUICE, ModBlockIds.GOLD_GRAPE_JUICE, GOLD_GRAPE_JUICE_BLOCK);
        register(ModFluidIds.GREEN_GRAPE_JUICE, GREEN_GRAPE_JUICE, ModBlockIds.GREEN_GRAPE_JUICE, GREEN_GRAPE_JUICE_BLOCK);
        register(ModFluidIds.SWEET_BERRIES_JUICE, SWEET_BERRIES_JUICE, ModBlockIds.SWEET_BERRIES_JUICE, SWEET_BERRIES_JUICE_BLOCK);
        register(ModFluidIds.GLOW_BERRIES_JUICE, GLOW_BERRIES_JUICE, ModBlockIds.GLOW_BERRIES_JUICE, GLOW_BERRIES_JUICE_BLOCK);
        if (FabricLoader.getInstance().isModLoaded("rrv"))
            ModRRVFluidItems.register();
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

    private static LiquidBlock liquidBlock(FlowingFluid fluid, ResourceKey<Block> blockKey) {
        return new LiquidBlock(fluid, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).setId(blockKey));
    }

    private static void register(
            ResourceKey<Fluid> fluidKey,
            FlowingFluid fluid,
            ResourceKey<Block> blockKey,
            Block block
    ) {
        Registry.register(BuiltInRegistries.FLUID, fluidKey, fluid);
        Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
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
