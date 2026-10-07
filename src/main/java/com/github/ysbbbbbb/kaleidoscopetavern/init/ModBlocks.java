package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.*;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.*;
import com.github.ysbbbbbb.kaleidoscopetavern.block.mixology.*;
import com.github.ysbbbbbb.kaleidoscopetavern.block.plant.*;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.*;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.*;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.ShakerBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.SignatureCocktailBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.util.PortHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.sounds.BlockSoundSets;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public final class ModBlocks {
    // 沙发
    public static final Block WHITE_SOFA = sofaReg("white_sofa");
    public static final Block LIGHT_GRAY_SOFA = sofaReg("light_gray_sofa");
    public static final Block GRAY_SOFA = sofaReg("gray_sofa");
    public static final Block BLACK_SOFA = sofaReg("black_sofa");
    public static final Block BROWN_SOFA = sofaReg("brown_sofa");
    public static final Block RED_SOFA = sofaReg("red_sofa");
    public static final Block ORANGE_SOFA = sofaReg("orange_sofa");
    public static final Block YELLOW_SOFA = sofaReg("yellow_sofa");
    public static final Block LIME_SOFA = sofaReg("lime_sofa");
    public static final Block GREEN_SOFA = sofaReg("green_sofa");
    public static final Block CYAN_SOFA = sofaReg("cyan_sofa");
    public static final Block LIGHT_BLUE_SOFA = sofaReg("light_blue_sofa");
    public static final Block BLUE_SOFA = sofaReg("blue_sofa");
    public static final Block PURPLE_SOFA = sofaReg("purple_sofa");
    public static final Block MAGENTA_SOFA = sofaReg("magenta_sofa");
    public static final Block PINK_SOFA = sofaReg("pink_sofa");
    // 高脚凳
    public static final Block WHITE_BAR_STOOL = barStoolReg("white_bar_stool", DyeColor.WHITE);
    public static final Block LIGHT_GRAY_BAR_STOOL = barStoolReg("light_gray_bar_stool", DyeColor.LIGHT_GRAY);
    public static final Block GRAY_BAR_STOOL = barStoolReg("gray_bar_stool", DyeColor.GRAY);
    public static final Block BLACK_BAR_STOOL = barStoolReg("black_bar_stool", DyeColor.BLACK);
    public static final Block BROWN_BAR_STOOL = barStoolReg("brown_bar_stool", DyeColor.BROWN);
    public static final Block RED_BAR_STOOL = barStoolReg("red_bar_stool", DyeColor.RED);
    public static final Block ORANGE_BAR_STOOL = barStoolReg("orange_bar_stool", DyeColor.ORANGE);
    public static final Block YELLOW_BAR_STOOL = barStoolReg("yellow_bar_stool", DyeColor.YELLOW);
    public static final Block LIME_BAR_STOOL = barStoolReg("lime_bar_stool", DyeColor.LIME);
    public static final Block GREEN_BAR_STOOL = barStoolReg("green_bar_stool", DyeColor.GREEN);
    public static final Block CYAN_BAR_STOOL = barStoolReg("cyan_bar_stool", DyeColor.CYAN);
    public static final Block LIGHT_BLUE_BAR_STOOL = barStoolReg("light_blue_bar_stool", DyeColor.LIGHT_BLUE);
    public static final Block BLUE_BAR_STOOL = barStoolReg("blue_bar_stool", DyeColor.BLUE);
    public static final Block PURPLE_BAR_STOOL = barStoolReg("purple_bar_stool", DyeColor.PURPLE);
    public static final Block MAGENTA_BAR_STOOL = barStoolReg("magenta_bar_stool", DyeColor.MAGENTA);
    public static final Block PINK_BAR_STOOL = barStoolReg("pink_bar_stool", DyeColor.PINK);
    // 展板
    public static final Block BASE_SANDWICH_BOARD = sandwichBoardReg("base_sandwich_board");
    public static final Block GRASS_SANDWICH_BOARD = sandwichBoardReg("grass_sandwich_board", Items.SHORT_GRASS);
    public static final Block ALLIUM_SANDWICH_BOARD = sandwichBoardReg("allium_sandwich_board", Items.AZURE_BLUET, Items.OXEYE_DAISY, Items.LILY_OF_THE_VALLEY);
    public static final Block AZURE_BLUET_SANDWICH_BOARD = sandwichBoardReg("azure_bluet_sandwich_board", Items.AZURE_BLUET, Items.OXEYE_DAISY, Items.LILY_OF_THE_VALLEY);
    public static final Block CORNFLOWER_SANDWICH_BOARD = sandwichBoardReg("cornflower_sandwich_board", Items.CORNFLOWER);
    public static final Block ORCHID_SANDWICH_BOARD = sandwichBoardReg("orchid_sandwich_board", Items.BLUE_ORCHID);
    public static final Block PEONY_SANDWICH_BOARD = sandwichBoardReg("peony_sandwich_board", Items.PEONY, Items.LILAC);
    public static final Block PINK_PETALS_SANDWICH_BOARD = sandwichBoardReg("pink_petals_sandwich_board", Items.PINK_PETALS);
    public static final Block PITCHER_PLANT_SANDWICH_BOARD = sandwichBoardReg("pitcher_plant_sandwich_board", Items.PITCHER_PLANT);
    public static final Block POPPY_SANDWICH_BOARD = sandwichBoardReg("poppy_sandwich_board", Items.POPPY, Items.ROSE_BUSH);
    public static final Block SUNFLOWER_SANDWICH_BOARD = sandwichBoardReg("sunflower_sandwich_board", Items.SUNFLOWER, Items.DANDELION);
    public static final Block TORCHFLOWER_SANDWICH_BOARD = sandwichBoardReg("torchflower_sandwich_board", Items.TORCHFLOWER);
    public static final Block TULIP_SANDWICH_BOARD = sandwichBoardReg("tulip_sandwich_board", Items.RED_TULIP, Items.ORANGE_TULIP, Items.WHITE_TULIP, Items.PINK_TULIP);
    public static final Block WITHER_ROSE_SANDWICH_BOARD = sandwichBoardReg("wither_rose_sandwich_board", Items.WITHER_ROSE);
    public static final Block EYEBLOSSOM_SANDWICH_BOARD = sandwichBoardReg("eyeblossom_sandwich_board", Items.OPEN_EYEBLOSSOM, Items.CLOSED_EYEBLOSSOM);
    // 彩灯
    public static final Block STRING_LIGHTS_COLORLESS = stringLightReg("string_lights_colorless", null);
    public static final Block STRING_LIGHTS_WHITE = stringLightReg("string_lights_white", Items.DYE.white());
    public static final Block STRING_LIGHTS_LIGHT_GRAY = stringLightReg("string_lights_light_gray", Items.DYE.lightGray());
    public static final Block STRING_LIGHTS_GRAY = stringLightReg("string_lights_gray", Items.DYE.gray());
    public static final Block STRING_LIGHTS_BLACK = stringLightReg("string_lights_black", Items.DYE.black());
    public static final Block STRING_LIGHTS_BROWN = stringLightReg("string_lights_brown", Items.DYE.brown());
    public static final Block STRING_LIGHTS_RED = stringLightReg("string_lights_red", Items.DYE.red());
    public static final Block STRING_LIGHTS_ORANGE = stringLightReg("string_lights_orange", Items.DYE.orange());
    public static final Block STRING_LIGHTS_YELLOW = stringLightReg("string_lights_yellow", Items.DYE.yellow());
    public static final Block STRING_LIGHTS_LIME = stringLightReg("string_lights_lime", Items.DYE.lime());
    public static final Block STRING_LIGHTS_GREEN = stringLightReg("string_lights_green", Items.DYE.green());
    public static final Block STRING_LIGHTS_CYAN = stringLightReg("string_lights_cyan", Items.DYE.cyan());
    public static final Block STRING_LIGHTS_LIGHT_BLUE = stringLightReg("string_lights_light_blue", Items.DYE.lightBlue());
    public static final Block STRING_LIGHTS_BLUE = stringLightReg("string_lights_blue", Items.DYE.blue());
    public static final Block STRING_LIGHTS_PURPLE = stringLightReg("string_lights_purple", Items.DYE.purple());
    public static final Block STRING_LIGHTS_MAGENTA = stringLightReg("string_lights_magenta", Items.DYE.magenta());
    public static final Block STRING_LIGHTS_PINK = stringLightReg("string_lights_pink", Items.DYE.pink());
    // 挂画
    public static final Block YSBB_PAINTING = paintingReg("ysbb_painting");
    public static final Block TARTARIC_ACID_PAINTING = paintingReg("tartaric_acid_painting");
    public static final Block CR019_PAINTING = paintingReg("cr019_painting");
    public static final Block UNKNOWN_PAINTING = paintingReg("unknown_painting");
    public static final Block MASTER_MARISA_PAINTING = paintingReg("master_marisa_painting");
    public static final Block SON_OF_MAN_PAINTING = paintingReg("son_of_man_painting");
    public static final Block DAVID_PAINTING = paintingReg("david_painting");
    public static final Block GIRL_WITH_PEARL_EARRING_PAINTING = paintingReg("girl_with_pearl_earring_painting");
    public static final Block STARRY_NIGHT_PAINTING = paintingReg("starry_night_painting");
    public static final Block VAN_GOGH_SELF_PORTRAIT_PAINTING = paintingReg("van_gogh_self_portrait_painting");
    public static final Block FATHER_PAINTING = paintingReg("father_painting");
    public static final Block GREAT_WAVE_PAINTING = paintingReg("great_wave_painting");
    public static final Block MONA_LISA_PAINTING = paintingReg("mona_lisa_painting");
    public static final Block MONDRIAN_PAINTING = paintingReg("mondrian_painting");
    public static final Block NIGHT_EPIPHANY_PAINTING = paintingReg("night_epiphany_painting");
    public static final Block DOBELRING_PAINTING = paintingReg("dobelring_painting");
    // 空瓶
    public static final Block EMPTY_BOTTLE = commonReg("empty_bottle", BottleBlock::simpleBottle, BlockBehaviour.Properties.of());
    public static final Block EMPTY_GLASSWARE = commonReg("empty_glassware", GlasswareBlock::new, BlockBehaviour.Properties.of());
    // 酒杯架
    public static final Block GLASSWARE_HOLDER = commonReg("glassware_holder", GlasswareHolderBlock::new, BlockBehaviour.Properties.of());
    // 鸡尾酒
    public static final Block SIGNATURE_COCKTAIL = commonReg("signature_cocktail", SignatureCocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block MYSTERY_COCKTAIL = commonReg("mystery_cocktail", MysteryCocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block WHITE_LADY = commonReg("white_lady", CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block EMERALD = commonReg("emerald", CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block BRASS_HEART = commonReg("brass_heart", CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block GODFATHER = commonReg("godfather", CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block GRASSHOPPER = commonReg("grasshopper", CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block SCREWDRIVER = commonReg("screwdriver", CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block MOJITO = commonReg("mojito", CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block ALLIUM_GARDEN = commonReg("allium_garden", CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block DEPTH_CHARGE = commonReg("depth_charge", CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block NETHER_SPECIAL = commonReg("nether_special", CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block BLOODY_MARY = commonReg("bloody_mary", CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block SCULK_SPECIAL = commonReg("sculk_special", CocktailBlock::new, BlockBehaviour.Properties.of());
    // 杂项的瓶子
    public static final Block WATER_BOTTLE = commonReg("water_bottle", BottleBlock::simpleBottle, BlockBehaviour.Properties.of());
    public static final Block HONEY_BOTTLE = commonReg("honey_bottle", BottleBlock::simpleBottle, BlockBehaviour.Properties.of());
    public static final Block DRAGON_BREATH_BOTTLE = commonReg("dragon_breath_bottle", BottleBlock::simpleBottle, BlockBehaviour.Properties.of());
    public static final Block POTION_BOTTLE = commonReg("potion_bottle", PotionBottleBlock::new, BlockBehaviour.Properties.of());
    public static final Block XP_BOTTLE = commonReg("xp_bottle", BottleBlock::simpleBottle, BlockBehaviour.Properties.of());
    // 桌子
    public static final Block TABLE = commonReg("table", TableBlock::new, BlockBehaviour.Properties.of());
    // 野生葡萄藤
    public static final Block WILD_GRAPEVINE = commonReg("wild_grapevine", WildGrapevineBlock::new, BlockBehaviour.Properties.of());
    public static final Block WILD_GRAPEVINE_PLANT = commonReg("wild_grapevine_plant", WildGrapevinePlantBlock::new, BlockBehaviour.Properties.of());
    // 藤架
    public static final Block TRELLIS = commonReg("trellis", TrellisBlock::new, BlockBehaviour.Properties.of());
    // 葡萄
    public static final Block GRAPE_CROP = commonReg("grape_crop", p -> new GrapeCropBlock(
            p,  (_, _, _, _) -> 0.25F, () -> new ItemStack(ModItems.GRAPE, 3)
    ), BlockBehaviour.Properties.of().dynamicShape());
    public static final Block ICE_GRAPE_CROP = commonReg("ice_grape_crop", p -> new GrapeCropBlock(
            p,  (_, level, pos, _) -> level.getBiome(pos).value().getBaseTemperature() < 0.15F ? 0.8F : 0.25F, () -> new ItemStack(ModItems.ICE_GRAPE, 3)
    ), BlockBehaviour.Properties.of().dynamicShape());
    public static final Block GOLD_GRAPE_CROP = commonReg("gold_grape_crop", p -> new GrapeCropBlock(
            p,  (_, level, pos, _) -> level.getBiome(pos).value().getBaseTemperature() > 1.0F ? 0.8F : 0.25F, () -> new ItemStack(ModItems.GOLD_GRAPE, 3)
    ), BlockBehaviour.Properties.of().dynamicShape());
    // 葡萄藤
    public static final Block GRAPEVINE_TRELLIS = commonReg("grapevine_trellis", p -> new GrapevineTrellisBlock(
            p, (_, _, _, _) -> 0.25F, ModBlocks.GRAPE_CROP::defaultBlockState
    ), BlockBehaviour.Properties.of());
    public static final Block ICE_GRAPEVINE_TRELLIS = commonReg("ice_grapevine_trellis", p -> new GrapevineTrellisBlock(
            p, (_, level, pos, _) -> level.getBiome(pos).value().getBaseTemperature() < 0.15F ? 0.8F : 0.25F, ModBlocks.ICE_GRAPE_CROP::defaultBlockState
    ), BlockBehaviour.Properties.of());
    public static final Block GOLD_GRAPEVINE_TRELLIS = commonReg("gold_grapevine_trellis", p -> new GrapevineTrellisBlock(
            p, (_, level, pos, _) -> level.getBiome(pos).value().getBaseTemperature() > 1.0F ? 0.8F : 0.25F, ModBlocks.GOLD_GRAPE_CROP::defaultBlockState
    ), BlockBehaviour.Properties.of());
    // 吧台
    public static final Block BAR_COUNTER = commonReg("bar_counter", BarCounterBlock::new, BlockBehaviour.Properties.of());
    // 人字梯
    public static final Block STEPLADDER = commonReg("stepladder", StepladderBlock::new, BlockBehaviour.Properties.of());
    // 黑板
    public static final Block CHALKBOARD = commonReg("chalkboard", ChalkboardBlock::new, BlockBehaviour.Properties.of());
    public static final Block BELL_PENDANT_LAMP = commonReg("bell_pendant_lamp", PendantLampBlock::new, BlockBehaviour.Properties.of());
    public static final Block YELLOW_PENDANT_LAMP = commonReg("yellow_pendant_lamp", PendantLampBlock::new, BlockBehaviour.Properties.of());
    public static final Block BLUE_PENDANT_LAMP = commonReg("blue_pendant_lamp", PendantLampBlock::new, BlockBehaviour.Properties.of());
    // 熏香
    public static final Block SAKURA_INCENSE = commonReg("sakura_incense",
            p -> new IncenseBlock(p, () -> ModParticles.SAKURA_INCENSE_PARTICLE, () -> ParticleTypes.CHERRY_LEAVES),
            BlockBehaviour.Properties.of());
    public static final Block PINE_INCENSE = commonReg("pine_incense",
            p -> new IncenseBlock(p, () -> ModParticles.PINE_INCENSE_PARTICLE, () -> ModParticles.PINE_INCENSE_LARGE_PARTICLE),
            BlockBehaviour.Properties.of());
    public static final Block GINKGO_INCENSE = commonReg("ginkgo_incense",
            p -> new IncenseBlock(p, () -> ModParticles.GINKGO_INCENSE_PARTICLE, () -> ModParticles.GINKGO_INCENSE_LARGE_PARTICLE),
            BlockBehaviour.Properties.of());
    public static final Block SPORE_INCENSE = commonReg("spore_incense",
            p -> new IncenseBlock(p, () -> ModParticles.SPORE_INCENSE_PARTICLE, () -> ParticleTypes.SPORE_BLOSSOM_AIR),
            BlockBehaviour.Properties.of());
    public static final Block CATNIP_INCENSE = commonReg("catnip_incense",
            p -> new IncenseBlock(p, () -> ModParticles.CATNIP_INCENSE_PARTICLE, () -> ModParticles.CATNIP_INCENSE_LARGE_PARTICLE),
            BlockBehaviour.Properties.of());
    public static final Block SNOW_INCENSE = commonReg("snow_incense",
            p -> new IncenseBlock(p, () -> ModParticles.SNOW_INCENSE_PARTICLE, () -> ModParticles.SNOW_INCENSE_LARGE_PARTICLE),
            BlockBehaviour.Properties.of());
    public static final Block BUTTERFLY_INCENSE = commonReg("butterfly_incense",
            p -> new IncenseBlock(p, () -> ModParticles.BUTTERFLY_INCENSE_PARTICLE, () -> ModParticles.BUTTERFLY_INCENSE_LARGE_PARTICLE),
            BlockBehaviour.Properties.of());
    public static final Block FIREFLY_INCENSE = commonReg("firefly_incense",
            p -> new IncenseBlock(p, () -> ModParticles.FIREFLY_INCENSE_PARTICLE, () -> ModParticles.FIREFLY_INCENSE_LARGE_PARTICLE, -0.67, 5.33),
            BlockBehaviour.Properties.of());

    // 龙头
    public static final Block TAP = commonReg("tap", TapBlock::new, BlockBehaviour.Properties.of());

    // 燃烧瓶
    public static final Block MOLOTOV = commonReg("molotov", MolotovBlock::new, BlockBehaviour.Properties.of());

    // 酒柜
    public static final Block BAR_CABINET = commonReg("bar_cabinet", BarCabinetBlock::new, BlockBehaviour.Properties.of());
    public static final Block GLASS_BAR_CABINET = commonReg("glass_bar_cabinet", BarCabinetBlock::new, BlockBehaviour.Properties.of());
    public static final Block CELLAR_CABINET = commonReg("cellar_cabinet", CellarCabinetBlock::new, BlockBehaviour.Properties.of());
    public static final Block TILTED_RACK = commonReg("tilted_rack", TiltedRackBlock::new, BlockBehaviour.Properties.of());
    public static final Block CIRCULAR_RACK = commonReg("circular_rack", CircularRackBlock::new, BlockBehaviour.Properties.of());
    public static final Block HOLDER = commonReg("holder", HolderBlock::new, BlockBehaviour.Properties.of());
    public static final Block SHAKER = commonReg("shaker", ShakerBlock::new, BlockBehaviour.Properties.of());

    // 酒桶
    public static final Block BARREL = commonReg("barrel", BarrelBlock::new, BlockBehaviour.Properties.of());

    // 果盆
    public static final Block PRESSING_TUB = commonReg("pressing_tub", PressingTubBlock::new, BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.GUITAR)
            .strength(0.8F)
            .sound(BlockSoundSets.WOOD)
            .ignitedByLava());

    // 酒
    public static final Block WINE = wineReg(ModFoods.ModWines.WINE, "wine");
    public static final Block CHAMPAGNE = wineReg(ModFoods.ModWines.CHAMPAGNE, "champagne");
    public static final Block VODKA = wineReg(ModFoods.ModWines.VODKA, "vodka");
    public static final Block BRANDY = wineReg(ModFoods.ModWines.BRANDY, "brandy");
    public static final Block CARIGNAN = wineReg(ModFoods.ModWines.CARIGNAN, "carignan");
    public static final Block SAKURA_WINE = wineReg(ModFoods.ModWines.SAKURA_WINE, "sakura_wine");
    public static final Block PLUM_WINE = wineReg(ModFoods.ModWines.PLUM_WINE, "plum_wine");
    public static final Block WHISKEY = wineReg(ModFoods.ModWines.WHISKEY, "whiskey");
    public static final Block ICE_WINE = wineReg(ModFoods.ModWines.ICE_WINE, "ice_wine");
    public static final Block VINEGAR = wineReg(ModFoods.ModWines.VINEGAR, "vinegar");
    public static final Block POLARIS_SWEET_WHITE = wineReg(ModFoods.ModWines.POLARIS_SWEET_WHITE, "polaris_sweet_white");
    public static final Block HONEY_WINE = wineReg(ModFoods.ModWines.HONEY_WINE, "honey_wine");
    public static final Block RED_QUEEN = wineReg(ModFoods.ModWines.RED_QUEEN, "red_queen");
    public static final Block MINERS_STAR = wineReg(ModFoods.ModWines.MINERS_STAR, "miners_star");
    public static final Block RUM = wineReg(ModFoods.ModWines.RUM, "rum");
    public static final Block RIESLING_DRY_WHITE = wineReg(ModFoods.ModWines.RIESLING_DRY_WHITE, "riesling_dry_white");
    public static final Block SUNSET_GLOW = wineReg(ModFoods.ModWines.SUNSET_GLOW, "sunset_glow");
    public static final Block MADAME_SHEXIANG = wineReg(ModFoods.ModWines.MADAME_SHEXIANG, "madame_shexiang");
    public static final Block SWEET_BERRY_WINE = wineReg(ModFoods.ModWines.SWEET_BERRY_WINE, "sweet_berry_wine");
    public static final Block SHERRY = wineReg(ModFoods.ModWines.SHERRY, "sherry");
    public static final Block MOTHER_SNOW = wineReg(ModFoods.ModWines.MOTHER_SNOW, "mother_snow");
    public static final Block LUMINOUS_BRIDE = wineReg(ModFoods.ModWines.LUMINOUS_BRIDE, "luminous_bride");
    public static final Block GLOWFLOWER_BREW = wineReg(ModFoods.ModWines.GLOWFLOWER_BREW, "glowflower_brew");
    public static final Block SAUVIGNON_BLANC_DRY_WHITE = wineReg(ModFoods.ModWines.SAUVIGNON_BLANC_DRY_WHITE, "sauvignon_blanc_dry_white");
    public static final Block WATERMELON_JUICE = wineReg(ModFoods.ModWines.WATERMELON_JUICE, "watermelon_juice");


    public static final BlockEntityType<SandwichBoardBlockEntity> SANDWICH_BOARD_BE = FabricBlockEntityTypeBuilder.create(SandwichBoardBlockEntity::new,
            BASE_SANDWICH_BOARD,
            GRASS_SANDWICH_BOARD,
            ALLIUM_SANDWICH_BOARD,
            AZURE_BLUET_SANDWICH_BOARD,
            CORNFLOWER_SANDWICH_BOARD,
            ORCHID_SANDWICH_BOARD,
            PEONY_SANDWICH_BOARD,
            PINK_PETALS_SANDWICH_BOARD,
            PITCHER_PLANT_SANDWICH_BOARD,
            POPPY_SANDWICH_BOARD,
            SUNFLOWER_SANDWICH_BOARD,
            TORCHFLOWER_SANDWICH_BOARD,
            TULIP_SANDWICH_BOARD,
            WITHER_ROSE_SANDWICH_BOARD,
            EYEBLOSSOM_SANDWICH_BOARD
    ).build();
    public static final BlockEntityType<BarStoolBlockEntity> BAR_STOOL_BE = FabricBlockEntityTypeBuilder.create(BarStoolBlockEntity::new,
            BLUE_BAR_STOOL,
            GREEN_BAR_STOOL,
            ORANGE_BAR_STOOL,
            PURPLE_BAR_STOOL,
            YELLOW_BAR_STOOL,
            BLACK_BAR_STOOL,
            WHITE_BAR_STOOL,
            GRAY_BAR_STOOL,
            BROWN_BAR_STOOL,
            LIME_BAR_STOOL,
            MAGENTA_BAR_STOOL,
            CYAN_BAR_STOOL,
            LIGHT_BLUE_BAR_STOOL,
            PINK_BAR_STOOL,
            LIGHT_GRAY_BAR_STOOL,
            RED_BAR_STOOL
    ).build();
    public static final BlockEntityType<ChalkboardBlockEntity> CHALKBOARD_BE = FabricBlockEntityTypeBuilder.create(ChalkboardBlockEntity::new, CHALKBOARD).build();
    public static final BlockEntityType<BarrelBlockEntity> BARREL_BE = FabricBlockEntityTypeBuilder.create(BarrelBlockEntity::new, BARREL).build();
    public static final BlockEntityType<PressingTubBlockEntity> PRESSING_TUB_BE = FabricBlockEntityTypeBuilder.create(PressingTubBlockEntity::new, PRESSING_TUB).build();
    public static final BlockEntityType<BarCabinetBlockEntity> BAR_CABINET_BE = FabricBlockEntityTypeBuilder.create(BarCabinetBlockEntity::new,
            BAR_CABINET,
            GLASS_BAR_CABINET
    ).build();
    public static final BlockEntityType<CellarCabinetBlockEntity> CELLAR_CABINET_BE = FabricBlockEntityTypeBuilder.create(CellarCabinetBlockEntity::new, CELLAR_CABINET).build();
    public static final BlockEntityType<TiltedRackBlockEntity> TILTED_RACK_BE = FabricBlockEntityTypeBuilder.create(TiltedRackBlockEntity::new, TILTED_RACK).build();
    public static final BlockEntityType<GlasswareHolderBlockEntity> GLASSWARE_HOLDER_BE = FabricBlockEntityTypeBuilder.create(GlasswareHolderBlockEntity::new, GLASSWARE_HOLDER).build();
    public static final BlockEntityType<CircularRackBlockEntity> CIRCULAR_RACK_BE = FabricBlockEntityTypeBuilder.create(CircularRackBlockEntity::new, CIRCULAR_RACK).build();
    public static final BlockEntityType<HolderBlockEntity> HOLDER_BE = FabricBlockEntityTypeBuilder.create(HolderBlockEntity::new, HOLDER).build();
    public static final BlockEntityType<ShakerBlockEntity> SHAKER_BE = FabricBlockEntityTypeBuilder.create(ShakerBlockEntity::new, SHAKER).build();
    public static final BlockEntityType<PotionBottleBlockEntity> POTION_BOTTLE_BE = FabricBlockEntityTypeBuilder.create(PotionBottleBlockEntity::new, POTION_BOTTLE).build();
    public static final BlockEntityType<IncenseBlockEntity> INCENSE_BE = FabricBlockEntityTypeBuilder.create(IncenseBlockEntity::new,
            SAKURA_INCENSE,
            PINE_INCENSE,
            GINKGO_INCENSE,
            SPORE_INCENSE,
            CATNIP_INCENSE,
            SNOW_INCENSE,
            BUTTERFLY_INCENSE,
            FIREFLY_INCENSE
    ).build();
    public static final BlockEntityType<SignatureCocktailBlockEntity> SIGNATURE_COCKTAIL_BE = FabricBlockEntityTypeBuilder.create(SignatureCocktailBlockEntity::new, SIGNATURE_COCKTAIL).build();
    public static final BlockEntityType<TapBlockEntity> TAP_BE = FabricBlockEntityTypeBuilder.create(TapBlockEntity::new, TAP).build();
    public static final BlockEntityType<DrinkBlockEntity> DRINK_BE = FabricBlockEntityTypeBuilder.create(DrinkBlockEntity::new,
            WINE, CHAMPAGNE, VODKA, BRANDY, CARIGNAN,
            SAKURA_WINE, PLUM_WINE, WHISKEY, ICE_WINE,
            POLARIS_SWEET_WHITE, HONEY_WINE, RED_QUEEN, MINERS_STAR,
            RUM, RIESLING_DRY_WHITE, SUNSET_GLOW, MADAME_SHEXIANG,
            SWEET_BERRY_WINE, SHERRY, MOTHER_SNOW, LUMINOUS_BRIDE,
            GLOWFLOWER_BREW, SAUVIGNON_BLANC_DRY_WHITE, VINEGAR,
            WATERMELON_JUICE
    ).build();


    public static void registerBlocks() {
        // Block entities
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "barrel"), BARREL_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "pressing_tub"), PRESSING_TUB_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "bar_cabinet"), BAR_CABINET_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "cellar_cabinet"), CELLAR_CABINET_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "tilted_rack"), TILTED_RACK_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "glassware_holder"), GLASSWARE_HOLDER_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "circular_rack"), CIRCULAR_RACK_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "holder"), HOLDER_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "shaker"), SHAKER_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "potion_bottle"), POTION_BOTTLE_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "incense"), INCENSE_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "signature_cocktail"), SIGNATURE_COCKTAIL_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "drink"), DRINK_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "tap"), TAP_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "chalkboard"), CHALKBOARD_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "sandwich_board"), SANDWICH_BOARD_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "bar_stool"), BAR_STOOL_BE);
    }
    public static Block register(ResourceKey<Block> resourceKey, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties) {
        Block block = function.apply(properties.setId(resourceKey));
        return Registry.register(BuiltInRegistries.BLOCK, resourceKey, block);
    }
    private static Block commonReg(String string, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties) {
        return register(PortHelper.createBlockId(string), function, properties);
    }

    private static Block sofaReg(String string) {
        return commonReg(string, SofaBlock::new, BlockBehaviour.Properties.of());
    }

    private static Block stringLightReg(String string,@Nullable Item color) {
        return commonReg(string, properties -> new StringLightsBlock(properties, color), BlockBehaviour.Properties.of());
    }

    private static Block barStoolReg(String string, DyeColor color) {
        return commonReg(string, p -> new BarStoolBlock(p, color), BlockBehaviour.Properties.of());
    }

    private static Block sandwichBoardReg(String string, Item... items) {
        return commonReg(string, p -> new SandwichBoardBlock(p, items), BlockBehaviour.Properties.of());
    }

    private static Block paintingReg(String string) {
        return commonReg(string, PaintingBlock::new, BlockBehaviour.Properties.of());
    }

    private static Block paintingRegSpecial(String string) {
        return commonReg(string, PaintingBlock::new, BlockBehaviour.Properties.of().overrideDescription("block.kaleidoscope_tavern.painting"));
    }

    private static Block wineReg(Block block, String s) {
        return Registry.register(BuiltInRegistries.BLOCK, PortHelper.createBlockId(s), block);
    }
}
