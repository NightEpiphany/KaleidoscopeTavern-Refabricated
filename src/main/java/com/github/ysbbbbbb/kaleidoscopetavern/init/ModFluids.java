package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.fluid.JuiceFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid.Flowing;
import net.minecraftforge.fluids.ForgeFlowingFluid.Properties;
import net.minecraftforge.fluids.ForgeFlowingFluid.Source;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import static com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern.MOD_ID;
import static com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern.modLoc;
import static net.minecraftforge.registries.ForgeRegistries.FLUIDS;
import static net.minecraftforge.registries.ForgeRegistries.Keys.FLUID_TYPES;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFluids {
    // ID
    public static final ResourceLocation GRAPE_JUICE_ID = modLoc("grape_juice");
    public static final ResourceLocation FLOWING_GRAPE_JUICE_ID = modLoc("flowing_grape_juice");

    public static final ResourceLocation SWEET_BERRIES_JUICE_ID = modLoc("sweet_berries_juice");
    public static final ResourceLocation FLOWING_SWEET_BERRIES_JUICE_ID = modLoc("flowing_sweet_berries_juice");

    public static final ResourceLocation GLOW_BERRIES_JUICE_ID = modLoc("glow_berries_juice");
    public static final ResourceLocation FLOWING_GLOW_BERRIES_JUICE_ID = modLoc("flowing_glow_berries_juice");

    // FluidType
    public static final RegistryObject<FluidType> GRAPE_JUICE_TYPE = RegistryObject.create(GRAPE_JUICE_ID, FLUID_TYPES.location(), MOD_ID);
    public static final RegistryObject<FluidType> SWEET_BERRIES_JUICE_TYPE = RegistryObject.create(SWEET_BERRIES_JUICE_ID, FLUID_TYPES.location(), MOD_ID);
    public static final RegistryObject<FluidType> GLOW_BERRIES_JUICE_TYPE = RegistryObject.create(GLOW_BERRIES_JUICE_ID, FLUID_TYPES.location(), MOD_ID);

    // Fluid
    public static final RegistryObject<Fluid> GRAPE_JUICE = RegistryObject.create(GRAPE_JUICE_ID, FLUIDS);
    public static final RegistryObject<Fluid> FLOWING_GRAPE_JUICE = RegistryObject.create(FLOWING_GRAPE_JUICE_ID, FLUIDS);

    public static final RegistryObject<Fluid> SWEET_BERRIES_JUICE = RegistryObject.create(SWEET_BERRIES_JUICE_ID, FLUIDS);
    public static final RegistryObject<Fluid> FLOWING_SWEET_BERRIES_JUICE = RegistryObject.create(FLOWING_SWEET_BERRIES_JUICE_ID, FLUIDS);

    public static final RegistryObject<Fluid> GLOW_BERRIES_JUICE = RegistryObject.create(GLOW_BERRIES_JUICE_ID, FLUIDS);
    public static final RegistryObject<Fluid> FLOWING_GLOW_BERRIES_JUICE = RegistryObject.create(FLOWING_GLOW_BERRIES_JUICE_ID, FLUIDS);

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        event.register(Keys.FLUID_TYPES, helper -> {
            helper.register(GRAPE_JUICE_ID, new JuiceFluidType(GRAPE_JUICE_ID, 0));
            helper.register(SWEET_BERRIES_JUICE_ID, new JuiceFluidType(SWEET_BERRIES_JUICE_ID, 0));
            helper.register(GLOW_BERRIES_JUICE_ID, new JuiceFluidType(GLOW_BERRIES_JUICE_ID, 14));
        });

        event.register(Keys.FLUIDS, helper -> {
            Properties grapeJuice = new Properties(GRAPE_JUICE_TYPE, GRAPE_JUICE, FLOWING_GRAPE_JUICE).bucket(ModItems.GRAPE_BUCKET);
            Properties sweetBerriesJuice = new Properties(SWEET_BERRIES_JUICE_TYPE, SWEET_BERRIES_JUICE, FLOWING_SWEET_BERRIES_JUICE).bucket(ModItems.SWEET_BERRIES_BUCKET);
            Properties glowBerriesJuice = new Properties(GLOW_BERRIES_JUICE_TYPE, GLOW_BERRIES_JUICE, FLOWING_GLOW_BERRIES_JUICE).bucket(ModItems.GLOW_BERRIES_BUCKET);

            helper.register(GRAPE_JUICE_ID, new Source(grapeJuice));
            helper.register(FLOWING_GRAPE_JUICE_ID, new Flowing(grapeJuice));

            helper.register(SWEET_BERRIES_JUICE_ID, new Source(sweetBerriesJuice));
            helper.register(FLOWING_SWEET_BERRIES_JUICE_ID, new Flowing(sweetBerriesJuice));

            helper.register(GLOW_BERRIES_JUICE_ID, new Source(glowBerriesJuice));
            helper.register(FLOWING_GLOW_BERRIES_JUICE_ID, new Flowing(glowBerriesJuice));
        });
    }
}
