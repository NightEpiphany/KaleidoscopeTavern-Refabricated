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
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.function.Function;

@SuppressWarnings("all")
public final class ModBlocks {
    // ========== 注册辅助方法 ==========
    private static Block registerBlock(String id, Function<BlockBehaviour.Properties, Block> factory) {
        ResourceKey<Block> key = PortHelper.createBlockId(id);
        return Registry.register(BuiltInRegistries.BLOCK, key, factory.apply(BlockBehaviour.Properties.of().setId(key)));
    }

    // ========== 方块定义 ==========
    // 沙发
    public static final Block WHITE_SOFA = registerBlock("white_sofa", SofaBlock::new);
    public static final Block LIGHT_GRAY_SOFA = registerBlock("light_gray_sofa", SofaBlock::new);
    public static final Block GRAY_SOFA = registerBlock("gray_sofa", SofaBlock::new);
    public static final Block BLACK_SOFA = registerBlock("black_sofa", SofaBlock::new);
    public static final Block BROWN_SOFA = registerBlock("brown_sofa", SofaBlock::new);
    public static final Block RED_SOFA = registerBlock("red_sofa", SofaBlock::new);
    public static final Block ORANGE_SOFA = registerBlock("orange_sofa", SofaBlock::new);
    public static final Block YELLOW_SOFA = registerBlock("yellow_sofa", SofaBlock::new);
    public static final Block LIME_SOFA = registerBlock("lime_sofa", SofaBlock::new);
    public static final Block GREEN_SOFA = registerBlock("green_sofa", SofaBlock::new);
    public static final Block CYAN_SOFA = registerBlock("cyan_sofa", SofaBlock::new);
    public static final Block LIGHT_BLUE_SOFA = registerBlock("light_blue_sofa", SofaBlock::new);
    public static final Block BLUE_SOFA = registerBlock("blue_sofa", SofaBlock::new);
    public static final Block PURPLE_SOFA = registerBlock("purple_sofa", SofaBlock::new);
    public static final Block MAGENTA_SOFA = registerBlock("magenta_sofa", SofaBlock::new);
    public static final Block PINK_SOFA = registerBlock("pink_sofa", SofaBlock::new);

    // 高脚凳
    public static final Block WHITE_BAR_STOOL = registerBlock("white_bar_stool", p -> new BarStoolBlock(p, DyeColor.WHITE));
    public static final Block LIGHT_GRAY_BAR_STOOL = registerBlock("light_gray_bar_stool", p -> new BarStoolBlock(p, DyeColor.LIGHT_GRAY));
    public static final Block GRAY_BAR_STOOL = registerBlock("gray_bar_stool", p -> new BarStoolBlock(p, DyeColor.GRAY));
    public static final Block BLACK_BAR_STOOL = registerBlock("black_bar_stool", p -> new BarStoolBlock(p, DyeColor.BLACK));
    public static final Block BROWN_BAR_STOOL = registerBlock("brown_bar_stool", p -> new BarStoolBlock(p, DyeColor.BROWN));
    public static final Block RED_BAR_STOOL = registerBlock("red_bar_stool", p -> new BarStoolBlock(p, DyeColor.RED));
    public static final Block ORANGE_BAR_STOOL = registerBlock("orange_bar_stool", p -> new BarStoolBlock(p, DyeColor.ORANGE));
    public static final Block YELLOW_BAR_STOOL = registerBlock("yellow_bar_stool", p -> new BarStoolBlock(p, DyeColor.YELLOW));
    public static final Block LIME_BAR_STOOL = registerBlock("lime_bar_stool", p -> new BarStoolBlock(p, DyeColor.LIME));
    public static final Block GREEN_BAR_STOOL = registerBlock("green_bar_stool", p -> new BarStoolBlock(p, DyeColor.GREEN));
    public static final Block CYAN_BAR_STOOL = registerBlock("cyan_bar_stool", p -> new BarStoolBlock(p, DyeColor.CYAN));
    public static final Block LIGHT_BLUE_BAR_STOOL = registerBlock("light_blue_bar_stool", p -> new BarStoolBlock(p, DyeColor.LIGHT_BLUE));
    public static final Block BLUE_BAR_STOOL = registerBlock("blue_bar_stool", p -> new BarStoolBlock(p, DyeColor.BLUE));
    public static final Block PURPLE_BAR_STOOL = registerBlock("purple_bar_stool", p -> new BarStoolBlock(p, DyeColor.PURPLE));
    public static final Block MAGENTA_BAR_STOOL = registerBlock("magenta_bar_stool", p -> new BarStoolBlock(p, DyeColor.MAGENTA));
    public static final Block PINK_BAR_STOOL = registerBlock("pink_bar_stool", p -> new BarStoolBlock(p, DyeColor.PINK));

    // 展板
    public static final Block BASE_SANDWICH_BOARD = registerBlock("base_sandwich_board", p -> new SandwichBoardBlock(p));
    public static final Block GRASS_SANDWICH_BOARD = registerBlock("grass_sandwich_board", p -> new SandwichBoardBlock(p, Items.SHORT_GRASS));
    public static final Block ALLIUM_SANDWICH_BOARD = registerBlock("allium_sandwich_board", p -> new SandwichBoardBlock(p, Items.ALLIUM, Items.AZURE_BLUET, Items.OXEYE_DAISY, Items.LILY_OF_THE_VALLEY));
    public static final Block AZURE_BLUET_SANDWICH_BOARD = registerBlock("azure_bluet_sandwich_board", p -> new SandwichBoardBlock(p, Items.AZURE_BLUET, Items.OXEYE_DAISY, Items.LILY_OF_THE_VALLEY));
    public static final Block CORNFLOWER_SANDWICH_BOARD = registerBlock("cornflower_sandwich_board", p -> new SandwichBoardBlock(p, Items.CORNFLOWER));
    public static final Block ORCHID_SANDWICH_BOARD = registerBlock("orchid_sandwich_board", p -> new SandwichBoardBlock(p, Items.BLUE_ORCHID));
    public static final Block PEONY_SANDWICH_BOARD = registerBlock("peony_sandwich_board", p -> new SandwichBoardBlock(p, Items.PEONY, Items.LILAC));
    public static final Block PINK_PETALS_SANDWICH_BOARD = registerBlock("pink_petals_sandwich_board", p -> new SandwichBoardBlock(p, Items.PINK_PETALS));
    public static final Block PITCHER_PLANT_SANDWICH_BOARD = registerBlock("pitcher_plant_sandwich_board", p -> new SandwichBoardBlock(p, Items.PITCHER_PLANT));
    public static final Block POPPY_SANDWICH_BOARD = registerBlock("poppy_sandwich_board", p -> new SandwichBoardBlock(p, Items.POPPY, Items.ROSE_BUSH));
    public static final Block SUNFLOWER_SANDWICH_BOARD = registerBlock("sunflower_sandwich_board", p -> new SandwichBoardBlock(p, Items.SUNFLOWER, Items.DANDELION));
    public static final Block TORCHFLOWER_SANDWICH_BOARD = registerBlock("torchflower_sandwich_board", p -> new SandwichBoardBlock(p, Items.TORCHFLOWER));
    public static final Block TULIP_SANDWICH_BOARD = registerBlock("tulip_sandwich_board", p -> new SandwichBoardBlock(p, Items.RED_TULIP, Items.ORANGE_TULIP, Items.WHITE_TULIP, Items.PINK_TULIP));
    public static final Block WITHER_ROSE_SANDWICH_BOARD = registerBlock("wither_rose_sandwich_board", p -> new SandwichBoardBlock(p, Items.WITHER_ROSE));
    public static final Block EYEBLOSSOM_SANDWICH_BOARD = registerBlock("eyeblossom_sandwich_board", p -> new SandwichBoardBlock(p, Items.OPEN_EYEBLOSSOM, Items.CLOSED_EYEBLOSSOM));

    // 彩灯
    public static final Block STRING_LIGHTS_COLORLESS = registerBlock("string_lights_colorless", p -> new StringLightsBlock(p, null));
    public static final Block STRING_LIGHTS_WHITE = registerBlock("string_lights_white", p -> new StringLightsBlock(p, Items.WHITE_DYE));
    public static final Block STRING_LIGHTS_LIGHT_GRAY = registerBlock("string_lights_light_gray", p -> new StringLightsBlock(p, Items.LIGHT_GRAY_DYE));
    public static final Block STRING_LIGHTS_GRAY = registerBlock("string_lights_gray", p -> new StringLightsBlock(p, Items.GRAY_DYE));
    public static final Block STRING_LIGHTS_BLACK = registerBlock("string_lights_black", p -> new StringLightsBlock(p, Items.BLACK_DYE));
    public static final Block STRING_LIGHTS_BROWN = registerBlock("string_lights_brown", p -> new StringLightsBlock(p, Items.BROWN_DYE));
    public static final Block STRING_LIGHTS_RED = registerBlock("string_lights_red", p -> new StringLightsBlock(p, Items.RED_DYE));
    public static final Block STRING_LIGHTS_ORANGE = registerBlock("string_lights_orange", p -> new StringLightsBlock(p, Items.ORANGE_DYE));
    public static final Block STRING_LIGHTS_YELLOW = registerBlock("string_lights_yellow", p -> new StringLightsBlock(p, Items.YELLOW_DYE));
    public static final Block STRING_LIGHTS_LIME = registerBlock("string_lights_lime", p -> new StringLightsBlock(p, Items.LIME_DYE));
    public static final Block STRING_LIGHTS_GREEN = registerBlock("string_lights_green", p -> new StringLightsBlock(p, Items.GREEN_DYE));
    public static final Block STRING_LIGHTS_CYAN = registerBlock("string_lights_cyan", p -> new StringLightsBlock(p, Items.CYAN_DYE));
    public static final Block STRING_LIGHTS_LIGHT_BLUE = registerBlock("string_lights_light_blue", p -> new StringLightsBlock(p, Items.LIGHT_BLUE_DYE));
    public static final Block STRING_LIGHTS_BLUE = registerBlock("string_lights_blue", p -> new StringLightsBlock(p, Items.BLUE_DYE));
    public static final Block STRING_LIGHTS_PURPLE = registerBlock("string_lights_purple", p -> new StringLightsBlock(p, Items.PURPLE_DYE));
    public static final Block STRING_LIGHTS_MAGENTA = registerBlock("string_lights_magenta", p -> new StringLightsBlock(p, Items.MAGENTA_DYE));
    public static final Block STRING_LIGHTS_PINK = registerBlock("string_lights_pink", p -> new StringLightsBlock(p, Items.PINK_DYE));

    // 挂画
    public static final Block YSBB_PAINTING = registerBlock("ysbb_painting", PaintingBlock::new);
    public static final Block TARTARIC_ACID_PAINTING = registerBlock("tartaric_acid_painting", PaintingBlock::new);
    public static final Block CR019_PAINTING = registerBlock("cr019_painting", PaintingBlock::new);
    public static final Block UNKNOWN_PAINTING = registerBlock("unknown_painting", PaintingBlock::new);
    public static final Block MASTER_MARISA_PAINTING = registerBlock("master_marisa_painting", PaintingBlock::new);
    public static final Block SON_OF_MAN_PAINTING = registerBlock("son_of_man_painting", PaintingBlock::new);
    public static final Block DAVID_PAINTING = registerBlock("david_painting", PaintingBlock::new);
    public static final Block GIRL_WITH_PEARL_EARRING_PAINTING = registerBlock("girl_with_pearl_earring_painting", PaintingBlock::new);
    public static final Block STARRY_NIGHT_PAINTING = registerBlock("starry_night_painting", PaintingBlock::new);
    public static final Block VAN_GOGH_SELF_PORTRAIT_PAINTING = registerBlock("van_gogh_self_portrait_painting", PaintingBlock::new);
    public static final Block FATHER_PAINTING = registerBlock("father_painting", PaintingBlock::new);
    public static final Block GREAT_WAVE_PAINTING = registerBlock("great_wave_painting", PaintingBlock::new);
    public static final Block MONA_LISA_PAINTING = registerBlock("mona_lisa_painting", PaintingBlock::new);
    public static final Block MONDRIAN_PAINTING = registerBlock("mondrian_painting", PaintingBlock::new);
    public static final Block NIGHT_EPIPHANY_PAINTING = registerBlock("night_epiphany_painting", PaintingBlock::new);

    // 桌子
    public static final Block TABLE = registerBlock("table", TableBlock::new);
    // 果盆
    public static final Block PRESSING_TUB = registerBlock("pressing_tub", p -> new PressingTubBlock(p
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.GUITAR)
            .strength(0.8F)
            .sound(SoundType.WOOD)
            .ignitedByLava()));
    // 空瓶
    public static final Block EMPTY_BOTTLE = registerBlock("empty_bottle", p -> new BottleBlock(p, false));
    public static final Block EMPTY_GLASSWARE = registerBlock("empty_glassware", GlasswareBlock::new);
    // 酒杯架
    public static final Block GLASSWARE_HOLDER = registerBlock("glassware_holder", GlasswareHolderBlock::new);
    // 鸡尾酒
    public static final Block SIGNATURE_COCKTAIL = registerBlock("signature_cocktail", p -> new SignatureCocktailBlock(p));
    public static final Block MYSTERY_COCKTAIL = registerBlock("mystery_cocktail", p -> new MysteryCocktailBlock(p));
    public static final Block WHITE_LADY = registerBlock("white_lady", p -> new CocktailBlock(p));
    public static final Block EMERALD = registerBlock("emerald", p -> new CocktailBlock(p));
    public static final Block BRASS_HEART = registerBlock("brass_heart", p -> new CocktailBlock(p));
    public static final Block GODFATHER = registerBlock("godfather", p -> new CocktailBlock(p));
    public static final Block GRASSHOPPER = registerBlock("grasshopper", p -> new CocktailBlock(p));
    public static final Block SCREWDRIVER = registerBlock("screwdriver", p -> new CocktailBlock(p));
    public static final Block MOJITO = registerBlock("mojito", p -> new CocktailBlock(p));
    public static final Block ALLIUM_GARDEN = registerBlock("allium_garden", p -> new CocktailBlock(p));
    public static final Block DEPTH_CHARGE = registerBlock("depth_charge", p -> new CocktailBlock(p));
    public static final Block NETHER_SPECIAL = registerBlock("nether_special", p -> new CocktailBlock(p));
    public static final Block BLOODY_MARY = registerBlock("bloody_mary", p -> new CocktailBlock(p));
    public static final Block SCULK_SPECIAL = registerBlock("sculk_special", p -> new CocktailBlock(p));
    // 杂项的瓶子
    public static final Block WATER_BOTTLE = registerBlock("water_bottle", p -> new BottleBlock(p, false));
    public static final Block HONEY_BOTTLE = registerBlock("honey_bottle", p -> new BottleBlock(p, false));
    public static final Block DRAGON_BREATH_BOTTLE = registerBlock("dragon_breath_bottle", p -> new BottleBlock(p, false));
    public static final Block POTION_BOTTLE = registerBlock("potion_bottle", p -> new PotionBottleBlock(p));
    public static final Block XP_BOTTLE = registerBlock("xp_bottle", p -> new BottleBlock(p, false));

    // 酒桶
    public static final Block BARREL = registerBlock("barrel", BarrelBlock::new);
    // 酒柜
    public static final Block BAR_CABINET = registerBlock("bar_cabinet", BarCabinetBlock::new);
    public static final Block GLASS_BAR_CABINET = registerBlock("glass_bar_cabinet", BarCabinetBlock::new);
    public static final Block CELLAR_CABINET = registerBlock("cellar_cabinet", CellarCabinetBlock::new);
    // 酒架
    public static final Block TILTED_RACK = registerBlock("tilted_rack", TiltedRackBlock::new);
    public static final Block CIRCULAR_RACK = registerBlock("circular_rack", CircularRackBlock::new);
    public static final Block HOLDER = registerBlock("holder", HolderBlock::new);

    public static final Block SHAKER = registerBlock("shaker", ShakerBlock::new);
    // 野生葡萄藤
    public static final Block WILD_GRAPEVINE = registerBlock("wild_grapevine", WildGrapevineBlock::new);
    public static final Block WILD_GRAPEVINE_PLANT = registerBlock("wild_grapevine_plant", WildGrapevinePlantBlock::new);
    // 藤架
    public static final Block TRELLIS = registerBlock("trellis", TrellisBlock::new);
    // 葡萄
    public static final Block GRAPE_CROP = registerBlock("grape_crop", p -> new GrapeCropBlock(p,
            (state, level, pos, random) -> 0.25F,
            () -> new ItemStack(ModItems.GRAPE, 3)
    ));
    public static final Block ICE_GRAPE_CROP = registerBlock("ice_grape_crop", p -> new GrapeCropBlock(p,
            (state, level, pos, random) -> level.getBiome(pos).value().getBaseTemperature() < 0.15F ? 0.8F : 0.25F,
            () -> new ItemStack(ModItems.ICE_GRAPE, 3)
    ));
    public static final Block GOLD_GRAPE_CROP = registerBlock("gold_grape_crop", p -> new GrapeCropBlock(p,
            (state, level, pos, random) -> level.getBiome(pos).value().getBaseTemperature() > 1.0F ? 0.8F : 0.25F,
            () -> new ItemStack(ModItems.GOLD_GRAPE, 3)
    ));

    // 葡萄藤
    public static final Block GRAPEVINE_TRELLIS = registerBlock("grapevine_trellis", p -> new GrapevineTrellisBlock(p,
            (state, level, pos, random) -> 0.25F,
            ModBlocks.GRAPE_CROP::defaultBlockState
    ));
    public static final Block ICE_GRAPEVINE_TRELLIS = registerBlock("ice_grapevine_trellis", p -> new GrapevineTrellisBlock(p,
            (state, level, pos, random) ->
                    level.getBiome(pos).value().getBaseTemperature() < 0.15F ? 0.8F : 0.25F,
            ModBlocks.ICE_GRAPE_CROP::defaultBlockState
    ));
    public static final Block GOLD_GRAPEVINE_TRELLIS = registerBlock("gold_grapevine_trellis", p -> new GrapevineTrellisBlock(p,
            (state, level, pos, random) ->
                    level.getBiome(pos).value().getBaseTemperature() > 1.0F ? 0.8F : 0.25F,
            ModBlocks.GOLD_GRAPE_CROP::defaultBlockState
    ));
    // 燃烧瓶
    public static final Block MOLOTOV = registerBlock("molotov", MolotovBlock::new);
    // 龙头
    public static final Block TAP = registerBlock("tap", TapBlock::new);
    // 吧台
    public static final Block BAR_COUNTER = registerBlock("bar_counter", BarCounterBlock::new);
    // 人字梯
    public static final Block STEPLADDER = registerBlock("stepladder", StepladderBlock::new);
    // 黑板
    public static final Block CHALKBOARD = registerBlock("chalkboard", ChalkboardBlock::new);
    // 垂灯
    public static final Block BELL_PENDANT_LAMP = registerBlock("bell_pendant_lamp", PendantLampBlock::new);
    public static final Block YELLOW_PENDANT_LAMP = registerBlock("yellow_pendant_lamp", PendantLampBlock::new);
    public static final Block BLUE_PENDANT_LAMP = registerBlock("blue_pendant_lamp", PendantLampBlock::new);
    // 香薰
    public static final Block SAKURA_INCENSE = registerBlock("sakura_incense", p -> new IncenseBlock(
            p, () -> ModParticles.SAKURA_INCENSE_PARTICLE, () -> ParticleTypes.CHERRY_LEAVES
    ));

    public static final Block PINE_INCENSE = registerBlock("pine_incense", p -> new IncenseBlock(
            p, () -> ModParticles.PINE_INCENSE_PARTICLE, () -> ModParticles.PINE_INCENSE_LARGE_PARTICLE
    ));

    public static final Block GINKGO_INCENSE = registerBlock("ginkgo_incense", p -> new IncenseBlock(
            p, () -> ModParticles.GINKGO_INCENSE_PARTICLE, () -> ModParticles.GINKGO_INCENSE_LARGE_PARTICLE
    ));

    public static final Block SPORE_INCENSE = registerBlock("spore_incense", p -> new IncenseBlock(
            p, () -> ModParticles.SPORE_INCENSE_PARTICLE, () -> ParticleTypes.SPORE_BLOSSOM_AIR
    ));

    public static final Block CATNIP_INCENSE = registerBlock("catnip_incense", p -> new IncenseBlock(
            p, () -> ModParticles.CATNIP_INCENSE_PARTICLE, () -> ModParticles.CATNIP_INCENSE_LARGE_PARTICLE
    ));

    public static final Block SNOW_INCENSE = registerBlock("snow_incense", p -> new IncenseBlock(
            p, () -> ModParticles.SNOW_INCENSE_PARTICLE, () -> ModParticles.SNOW_INCENSE_LARGE_PARTICLE
    ));

    public static final Block BUTTERFLY_INCENSE = registerBlock("butterfly_incense", p -> new IncenseBlock(
            p, () -> ModParticles.BUTTERFLY_INCENSE_PARTICLE, () -> ModParticles.BUTTERFLY_INCENSE_LARGE_PARTICLE
    ));

    public static final Block FIREFLY_INCENSE = registerBlock("firefly_incense", p -> new IncenseBlock(
            p, () -> ModParticles.FIREFLY_INCENSE_PARTICLE, () -> ModParticles.FIREFLY_INCENSE_LARGE_PARTICLE,
            -0.67, 5.33
    ));
    // 酒
    public static final Block WINE = registerBlock("wine", p -> DrinkBlock.create().setId("wine").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block CHAMPAGNE = registerBlock("champagne", p -> DrinkBlock.create().setId("champagne").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block VODKA = registerBlock("vodka", p -> DrinkBlock.create().setId("vodka").maxCount(4).shapes(
            Block.box(4, 0, 4, 12, 15, 12),
            Block.box(0, 0, 4, 16, 15, 12),
            Shapes.or(
                    Block.box(0, 0, 8, 16, 15, 16),
                    Block.box(4, 0, 0, 12, 15, 16)
            ),
            Block.box(0, 0, 0, 16, 16, 16)
    ).build());

    public static final Block BRANDY = registerBlock("brandy", p -> DrinkBlock.create().setId("brandy").maxCount(3).shapes(
            Block.box(3, 0, 6, 13, 13, 10),
            Block.box(1, 0, 3, 15, 12, 12),
            Block.box(1, 0, 1, 16, 12, 13)
    ).build());

    public static final Block CARIGNAN = registerBlock("carignan", p -> DrinkBlock.create().setId("carignan").maxCount(3).shapes(
            Block.box(3, 0, 6, 13, 13, 10),
            Block.box(1, 0, 3, 15, 12, 12),
            Block.box(1, 0, 1, 16, 12, 13)
    ).build());

    public static final Block SAKURA_WINE = registerBlock("sakura_wine", p -> DrinkBlock.create().setId("sakura_wine").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block PLUM_WINE = registerBlock("plum_wine", p -> DrinkBlock.create().setId("plum_wine").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 12, 10),
            Block.box(3, 0, 6, 13, 12, 10),
            Shapes.or(
                    Block.box(3, 0, 9, 13, 12, 13),
                    Block.box(6, 0, 3, 10, 12, 13)
            ),
            Block.box(3, 0, 3, 13, 12, 13)
    ).build());

    public static final Block WHISKEY = registerBlock("whiskey", p -> DrinkBlock.create().setId("whiskey").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block ICE_WINE = registerBlock("ice_wine", p -> DrinkBlock.create().setId("ice_wine").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block POLARIS_SWEET_WHITE = registerBlock("polaris_sweet_white", p -> DrinkBlock.create().setId("polaris_sweet_white").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block HONEY_WINE = registerBlock("honey_wine", p -> DrinkBlock.create().setId("honey_wine").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block RED_QUEEN = registerBlock("red_queen", p -> DrinkBlock.create().setId("red_queen").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block MINERS_STAR = registerBlock("miners_star", p -> DrinkBlock.create().setId("miners_star").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block RUM = registerBlock("rum", p -> DrinkBlock.create().setId("rum").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block RIESLING_DRY_WHITE = registerBlock("riesling_dry_white", p -> DrinkBlock.create().setId("riesling_dry_white").maxCount(4).shapes(
            Block.box(4, 0, 4, 12, 15, 12),
            Block.box(0, 0, 4, 16, 15, 12),
            Shapes.or(
                    Block.box(0, 0, 8, 16, 15, 16),
                    Block.box(4, 0, 0, 12, 15, 16)
            ),
            Block.box(0, 0, 0, 16, 16, 16)
    ).build());

    public static final Block SUNSET_GLOW = registerBlock("sunset_glow", p -> DrinkBlock.create().setId("sunset_glow").maxCount(3).shapes(
            Block.box(3, 0, 6, 13, 13, 10),
            Block.box(1, 0, 3, 15, 12, 12),
            Block.box(1, 0, 1, 16, 12, 13)
    ).build());

    public static final Block MADAME_SHEXIANG = registerBlock("madame_shexiang", p -> DrinkBlock.create().setId("madame_shexiang").maxCount(4).shapes(
            Block.box(4, 0, 4, 12, 15, 12),
            Block.box(0, 0, 4, 16, 15, 12),
            Shapes.or(
                    Block.box(0, 0, 8, 16, 15, 16),
                    Block.box(4, 0, 0, 12, 15, 16)
            ),
            Block.box(0, 0, 0, 16, 16, 16)
    ).build());

    public static final Block SWEET_BERRY_WINE = registerBlock("sweet_berry_wine", p -> DrinkBlock.create().setId("sweet_berry_wine").maxCount(4).shapes(
            Block.box(4, 0, 4, 12, 15, 12),
            Block.box(0, 0, 4, 16, 15, 12),
            Shapes.or(
                    Block.box(0, 0, 8, 16, 15, 16),
                    Block.box(4, 0, 0, 12, 15, 16)
            ),
            Block.box(0, 0, 0, 16, 16, 16)
    ).build());

    public static final Block SHERRY = registerBlock("sherry", p -> DrinkBlock.create().setId("sherry").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block MOTHER_SNOW = registerBlock("mother_snow", p -> DrinkBlock.create().setId("mother_snow").maxCount(4).shapes(
            Block.box(4, 0, 4, 12, 15, 12),
            Block.box(0, 0, 4, 16, 15, 12),
            Shapes.or(
                    Block.box(0, 0, 8, 16, 15, 16),
                    Block.box(4, 0, 0, 12, 15, 16)
            ),
            Block.box(0, 0, 0, 16, 16, 16)
    ).build());

    public static final Block LUMINOUS_BRIDE = registerBlock("luminous_bride", p -> DrinkBlock.create().setId("luminous_bride").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block GLOWFLOWER_BREW = registerBlock("glowflower_brew", p -> DrinkBlock.create().setId("glowflower_brew").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block SAUVIGNON_BLANC_DRY_WHITE = registerBlock("sauvignon_blanc_dry_white", p -> DrinkBlock.create().setId("sauvignon_blanc_dry_white").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block VINEGAR = registerBlock("vinegar", p -> DrinkBlock.create().setId("vinegar").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    public static final Block WATERMELON_JUICE = registerBlock("watermelon_juice", p -> DrinkBlock.create().setId("watermelon_juice").maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    // ========== 方块实体 ==========
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
    public static final BlockEntityType<PressingTubBlockEntity> PRESSING_TUB_BE = FabricBlockEntityTypeBuilder.create(PressingTubBlockEntity::new, PRESSING_TUB).build();
    public static final BlockEntityType<BarrelBlockEntity> BARREL_BE = FabricBlockEntityTypeBuilder.create(BarrelBlockEntity::new, BARREL).build();
    public static final BlockEntityType<TapBlockEntity> TAP_BE = FabricBlockEntityTypeBuilder.create(TapBlockEntity::new, TAP).build();
    public static final BlockEntityType<ShakerBlockEntity> SHAKER_BE = FabricBlockEntityTypeBuilder.create(ShakerBlockEntity::new, SHAKER).build();
    public static final BlockEntityType<PotionBottleBlockEntity> POTION_BOTTLE_BE = FabricBlockEntityTypeBuilder.create(PotionBottleBlockEntity::new, POTION_BOTTLE).build();
    public static final BlockEntityType<IncenseBlockEntity> INCENSE_BE = FabricBlockEntityTypeBuilder.create(IncenseBlockEntity::new,
            SAKURA_INCENSE, PINE_INCENSE, GINKGO_INCENSE, SPORE_INCENSE,
            CATNIP_INCENSE, SNOW_INCENSE, BUTTERFLY_INCENSE, FIREFLY_INCENSE
            ).build();
    public static final BlockEntityType<BarCabinetBlockEntity> BAR_CABINET_BE = FabricBlockEntityTypeBuilder.create(BarCabinetBlockEntity::new,
            BAR_CABINET,
            GLASS_BAR_CABINET
    ).build();
    public static final BlockEntityType<SignatureCocktailBlockEntity> SIGNATURE_COCKTAIL_BE = FabricBlockEntityTypeBuilder.create(SignatureCocktailBlockEntity::new,
            SIGNATURE_COCKTAIL
    ).build();
    public static final BlockEntityType<CellarCabinetBlockEntity> CELLAR_CABINET_BE = FabricBlockEntityTypeBuilder.create(CellarCabinetBlockEntity::new,
            CELLAR_CABINET
    ).build();
    public static final BlockEntityType<TiltedRackBlockEntity> TILTED_RACK_BE = FabricBlockEntityTypeBuilder.create(TiltedRackBlockEntity::new,
            TILTED_RACK
    ).build();
    public static final BlockEntityType<GlasswareHolderBlockEntity> GLASSWARE_HOLDER_BE = FabricBlockEntityTypeBuilder.create(GlasswareHolderBlockEntity::new,
            GLASSWARE_HOLDER
    ).build();
    public static final BlockEntityType<CircularRackBlockEntity> CIRCULAR_RACK_BE = FabricBlockEntityTypeBuilder.create(CircularRackBlockEntity::new,
            CIRCULAR_RACK
    ).build();
    public static final BlockEntityType<HolderBlockEntity> HOLDER_BE = FabricBlockEntityTypeBuilder.create(HolderBlockEntity::new,
            HOLDER
    ).build();
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
        // 方块与方块实体已在字段初始化时注册，此处仅保留 BlockEntityType 注册
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "sandwich_board"), SANDWICH_BOARD_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "bar_stool"), BAR_STOOL_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "chalkboard"), CHALKBOARD_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "pressing_tub"), PRESSING_TUB_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "barrel"), BARREL_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "tap"), TAP_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "shaker"), SHAKER_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "potion_bottle"), POTION_BOTTLE_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "incense"), INCENSE_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "bar_cabinet"), BAR_CABINET_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "signature_cocktail"), SIGNATURE_COCKTAIL_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "cellar_cabinet"), CELLAR_CABINET_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "tilted_rack"), TILTED_RACK_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "glassware_holder"), GLASSWARE_HOLDER_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "circular_rack"), CIRCULAR_RACK_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "holder"), HOLDER_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "drink"), DRINK_BE);
    }

}