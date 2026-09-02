package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.item.*;
import com.github.ysbbbbbb.kaleidoscopetavern.util.PortHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.function.Function;

import static com.github.ysbbbbbb.kaleidoscopetavern.init.ModFluids.*;

public final class ModItems {
    // ========== 注册辅助方法 ==========
    private static Item registerItem(String id, Function<Item.Properties, Item> factory) {
        return registerItem(id, factory, new Item.Properties());
    }

    private static Item registerItem(String id, Function<Item.Properties, Item> factory, Item.Properties properties) {
        ResourceKey<Item> key = PortHelper.createItemId(id);
        Item item = factory.apply(properties.setId(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    private static Item registerBlockItem(String id, Function<Item.Properties, Item> factory) {
        return registerItem(id, factory, new Item.Properties().useBlockDescriptionPrefix());
    }

    // ========== 物品定义 ==========
    // 葡萄
    public static final Item GRAPE = registerItem("grape", p -> new TooltipItem(p.food(ModFoods.GRAPE), "tooltip.kaleidoscope_tavern.grape"));
    public static final Item ICE_GRAPE = registerItem("ice_grape", p -> new TooltipItem(p.food(ModFoods.GRAPE), "tooltip.kaleidoscope_tavern.ice_grape"));
    public static final Item GOLD_GRAPE = registerItem("gold_grape", p -> new TooltipItem(p.food(ModFoods.GRAPE), "tooltip.kaleidoscope_tavern.gold_grape"));
    public static final Item GREEN_GRAPE = registerItem("green_grape", p -> new TooltipItem(p.food(ModFoods.GRAPE), "tooltip.kaleidoscope_tavern.green_grape"));
    // 野生葡萄藤
    public static final Item GRAPEVINE = registerItem("grapevine", GrapevineItem::new);
    // 果汁桶
    public static final Item GRAPE_BUCKET = registerItem("grape_bucket", p -> new JuiceBucketItem(GRAPE_JUICE, p));
    public static final Item SWEET_BERRIES_BUCKET = registerItem("sweet_berries_bucket", p -> new JuiceBucketItem(SWEET_BERRIES_JUICE, p));
    public static final Item GLOW_BERRIES_BUCKET = registerItem("glow_berries_bucket", p -> new JuiceBucketItem(GLOW_BERRIES_JUICE, p));
    public static final Item ICE_GRAPE_BUCKET = registerItem("ice_grape_bucket", p -> new JuiceBucketItem(ICE_GRAPE_JUICE, p));
    public static final Item GOLD_GRAPE_BUCKET = registerItem("gold_grape_bucket", p -> new JuiceBucketItem(GOLD_GRAPE_JUICE, p));
    public static final Item GREEN_GRAPE_BUCKET = registerItem("green_grape_bucket", p -> new JuiceBucketItem(GREEN_GRAPE_JUICE, p));
    // 空瓶
    public static final Item EMPTY_BOTTLE = registerBlockItem("empty_bottle", p -> new BottleBlockItem(ModBlocks.EMPTY_BOTTLE, p));
    public static final Item EMPTY_GLASSWARE = registerBlockItem("empty_glassware", p -> new GlasswareBlockItem(ModBlocks.EMPTY_GLASSWARE, p));

    // 鸡尾酒
    public static final Item SIGNATURE_COCKTAIL = registerBlockItem("signature_cocktail", p -> new SignatureCocktailBlockItem(ModBlocks.SIGNATURE_COCKTAIL, p));
    public static final Item MYSTERY_COCKTAIL = registerBlockItem("mystery_cocktail", p -> new CocktailBlockItem(ModBlocks.MYSTERY_COCKTAIL, p));
    public static final Item WHITE_LADY = registerBlockItem("white_lady", p -> new CocktailBlockItem(ModBlocks.WHITE_LADY, p));
    public static final Item EMERALD = registerBlockItem("emerald", p -> new CocktailBlockItem(ModBlocks.EMERALD, p));
    public static final Item BRASS_HEART = registerBlockItem("brass_heart", p -> new CocktailBlockItem(ModBlocks.BRASS_HEART, p));
    public static final Item GODFATHER = registerBlockItem("godfather", p -> new CocktailBlockItem(ModBlocks.GODFATHER, p));
    public static final Item GRASSHOPPER = registerBlockItem("grasshopper", p -> new CocktailBlockItem(ModBlocks.GRASSHOPPER, p));
    public static final Item SCREWDRIVER = registerBlockItem("screwdriver", p -> new CocktailBlockItem(ModBlocks.SCREWDRIVER, p));
    public static final Item MOJITO = registerBlockItem("mojito", p -> new CocktailBlockItem(ModBlocks.MOJITO, p));
    public static final Item ALLIUM_GARDEN = registerBlockItem("allium_garden", p -> new CocktailBlockItem(ModBlocks.ALLIUM_GARDEN, p));
    public static final Item DEPTH_CHARGE = registerBlockItem("depth_charge", p -> new CocktailBlockItem(ModBlocks.DEPTH_CHARGE, p));
    public static final Item NETHER_SPECIAL = registerBlockItem("nether_special", p -> new CocktailBlockItem(ModBlocks.NETHER_SPECIAL, p));
    public static final Item BLOODY_MARY = registerBlockItem("bloody_mary", p -> new CocktailBlockItem(ModBlocks.BLOODY_MARY, p));
    public static final Item SCULK_SPECIAL = registerBlockItem("sculk_special", p -> new CocktailBlockItem(ModBlocks.SCULK_SPECIAL, p));

    // 沙发
    public static final Item WHITE_SOFA = registerBlockItem("white_sofa", p -> new SofaBlockItem(ModBlocks.WHITE_SOFA, p));
    public static final Item LIGHT_GRAY_SOFA = registerBlockItem("light_gray_sofa", p -> new SofaBlockItem(ModBlocks.LIGHT_GRAY_SOFA, p));
    public static final Item GRAY_SOFA = registerBlockItem("gray_sofa", p -> new SofaBlockItem(ModBlocks.GRAY_SOFA, p));
    public static final Item BLACK_SOFA = registerBlockItem("black_sofa", p -> new SofaBlockItem(ModBlocks.BLACK_SOFA, p));
    public static final Item BROWN_SOFA = registerBlockItem("brown_sofa", p -> new SofaBlockItem(ModBlocks.BROWN_SOFA, p));
    public static final Item RED_SOFA = registerBlockItem("red_sofa", p -> new SofaBlockItem(ModBlocks.RED_SOFA, p));
    public static final Item ORANGE_SOFA = registerBlockItem("orange_sofa", p -> new SofaBlockItem(ModBlocks.ORANGE_SOFA, p));
    public static final Item YELLOW_SOFA = registerBlockItem("yellow_sofa", p -> new SofaBlockItem(ModBlocks.YELLOW_SOFA, p));
    public static final Item LIME_SOFA = registerBlockItem("lime_sofa", p -> new SofaBlockItem(ModBlocks.LIME_SOFA, p));
    public static final Item GREEN_SOFA = registerBlockItem("green_sofa", p -> new SofaBlockItem(ModBlocks.GREEN_SOFA, p));
    public static final Item CYAN_SOFA = registerBlockItem("cyan_sofa", p -> new SofaBlockItem(ModBlocks.CYAN_SOFA, p));
    public static final Item LIGHT_BLUE_SOFA = registerBlockItem("light_blue_sofa", p -> new SofaBlockItem(ModBlocks.LIGHT_BLUE_SOFA, p));
    public static final Item BLUE_SOFA = registerBlockItem("blue_sofa", p -> new SofaBlockItem(ModBlocks.BLUE_SOFA, p));
    public static final Item PURPLE_SOFA = registerBlockItem("purple_sofa", p -> new SofaBlockItem(ModBlocks.PURPLE_SOFA, p));
    public static final Item MAGENTA_SOFA = registerBlockItem("magenta_sofa", p -> new SofaBlockItem(ModBlocks.MAGENTA_SOFA, p));
    public static final Item PINK_SOFA = registerBlockItem("pink_sofa", p -> new SofaBlockItem(ModBlocks.PINK_SOFA, p));

    // 高脚凳
    public static final Item WHITE_BAR_STOOL = registerBlockItem("white_bar_stool", p -> new BarStoolBlockItem(ModBlocks.WHITE_BAR_STOOL, p));
    public static final Item LIGHT_GRAY_BAR_STOOL = registerBlockItem("light_gray_bar_stool", p -> new BarStoolBlockItem(ModBlocks.LIGHT_GRAY_BAR_STOOL, p));
    public static final Item GRAY_BAR_STOOL = registerBlockItem("gray_bar_stool", p -> new BarStoolBlockItem(ModBlocks.GRAY_BAR_STOOL, p));
    public static final Item BLACK_BAR_STOOL = registerBlockItem("black_bar_stool", p -> new BarStoolBlockItem(ModBlocks.BLACK_BAR_STOOL, p));
    public static final Item BROWN_BAR_STOOL = registerBlockItem("brown_bar_stool", p -> new BarStoolBlockItem(ModBlocks.BROWN_BAR_STOOL, p));
    public static final Item RED_BAR_STOOL = registerBlockItem("red_bar_stool", p -> new BarStoolBlockItem(ModBlocks.RED_BAR_STOOL, p));
    public static final Item ORANGE_BAR_STOOL = registerBlockItem("orange_bar_stool", p -> new BarStoolBlockItem(ModBlocks.ORANGE_BAR_STOOL, p));
    public static final Item YELLOW_BAR_STOOL = registerBlockItem("yellow_bar_stool", p -> new BarStoolBlockItem(ModBlocks.YELLOW_BAR_STOOL, p));
    public static final Item LIME_BAR_STOOL = registerBlockItem("lime_bar_stool", p -> new BarStoolBlockItem(ModBlocks.LIME_BAR_STOOL, p));
    public static final Item GREEN_BAR_STOOL = registerBlockItem("green_bar_stool", p -> new BarStoolBlockItem(ModBlocks.GREEN_BAR_STOOL, p));
    public static final Item CYAN_BAR_STOOL = registerBlockItem("cyan_bar_stool", p -> new BarStoolBlockItem(ModBlocks.CYAN_BAR_STOOL, p));
    public static final Item LIGHT_BLUE_BAR_STOOL = registerBlockItem("light_blue_bar_stool", p -> new BarStoolBlockItem(ModBlocks.LIGHT_BLUE_BAR_STOOL, p));
    public static final Item BLUE_BAR_STOOL = registerBlockItem("blue_bar_stool", p -> new BarStoolBlockItem(ModBlocks.BLUE_BAR_STOOL, p));
    public static final Item PURPLE_BAR_STOOL = registerBlockItem("purple_bar_stool", p -> new BarStoolBlockItem(ModBlocks.PURPLE_BAR_STOOL, p));
    public static final Item MAGENTA_BAR_STOOL = registerBlockItem("magenta_bar_stool", p -> new BarStoolBlockItem(ModBlocks.MAGENTA_BAR_STOOL, p));
    public static final Item PINK_BAR_STOOL = registerBlockItem("pink_bar_stool", p -> new BarStoolBlockItem(ModBlocks.PINK_BAR_STOOL, p));
    // 垂灯
    public static final Item BELL_PENDANT_LAMP = registerBlockItem("bell_pendant_lamp", p -> new BlockItem(ModBlocks.BELL_PENDANT_LAMP, p));
    public static final Item YELLOW_PENDANT_LAMP = registerBlockItem("yellow_pendant_lamp", p -> new BlockItem(ModBlocks.YELLOW_PENDANT_LAMP, p));
    public static final Item BLUE_PENDANT_LAMP = registerBlockItem("blue_pendant_lamp", p -> new BlockItem(ModBlocks.BLUE_PENDANT_LAMP, p));
    // 黑板
    public static final Item CHALKBOARD = registerBlockItem("chalkboard", p -> new BlockItem(ModBlocks.CHALKBOARD, p));
    // 香薰
    public static final Item SAKURA_INCENSE = registerBlockItem("sakura_incense", p -> new BlockItem(ModBlocks.SAKURA_INCENSE, p));
    public static final Item PINE_INCENSE = registerBlockItem("pine_incense", p -> new BlockItem(ModBlocks.PINE_INCENSE, p));
    public static final Item GINKGO_INCENSE = registerBlockItem("ginkgo_incense", p -> new BlockItem(ModBlocks.GINKGO_INCENSE, p));
    public static final Item SPORE_INCENSE = registerBlockItem("spore_incense", p -> new BlockItem(ModBlocks.SPORE_INCENSE, p));
    public static final Item CATNIP_INCENSE = registerBlockItem("catnip_incense", p -> new BlockItem(ModBlocks.CATNIP_INCENSE, p));
    public static final Item SNOW_INCENSE = registerBlockItem("snow_incense", p -> new BlockItem(ModBlocks.SNOW_INCENSE, p));
    public static final Item BUTTERFLY_INCENSE = registerBlockItem("butterfly_incense", p -> new BlockItem(ModBlocks.BUTTERFLY_INCENSE, p));
    public static final Item FIREFLY_INCENSE = registerBlockItem("firefly_incense", p -> new BlockItem(ModBlocks.FIREFLY_INCENSE, p));
    // 桌子
    public static final Item TABLE = registerBlockItem("table", p -> new BlockItem(ModBlocks.TABLE, p));

    // 展板
    public static final Item BASE_SANDWICH_BOARD = registerBlockItem("base_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.BASE_SANDWICH_BOARD, p));
    public static final Item GRASS_SANDWICH_BOARD = registerBlockItem("grass_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.GRASS_SANDWICH_BOARD, p));
    public static final Item ALLIUM_SANDWICH_BOARD = registerBlockItem("allium_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.ALLIUM_SANDWICH_BOARD, p));
    public static final Item AZURE_BLUET_SANDWICH_BOARD = registerBlockItem("azure_bluet_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.AZURE_BLUET_SANDWICH_BOARD, p));
    public static final Item CORNFLOWER_SANDWICH_BOARD = registerBlockItem("cornflower_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.CORNFLOWER_SANDWICH_BOARD, p));
    public static final Item ORCHID_SANDWICH_BOARD = registerBlockItem("orchid_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.ORCHID_SANDWICH_BOARD, p));
    public static final Item PEONY_SANDWICH_BOARD = registerBlockItem("peony_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.PEONY_SANDWICH_BOARD, p));
    public static final Item PINK_PETALS_SANDWICH_BOARD = registerBlockItem("pink_petals_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.PINK_PETALS_SANDWICH_BOARD, p));
    public static final Item PITCHER_PLANT_SANDWICH_BOARD = registerBlockItem("pitcher_plant_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.PITCHER_PLANT_SANDWICH_BOARD, p));
    public static final Item POPPY_SANDWICH_BOARD = registerBlockItem("poppy_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.POPPY_SANDWICH_BOARD, p));
    public static final Item SUNFLOWER_SANDWICH_BOARD = registerBlockItem("sunflower_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.SUNFLOWER_SANDWICH_BOARD, p));
    public static final Item TORCHFLOWER_SANDWICH_BOARD = registerBlockItem("torchflower_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.TORCHFLOWER_SANDWICH_BOARD, p));
    public static final Item TULIP_SANDWICH_BOARD = registerBlockItem("tulip_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.TULIP_SANDWICH_BOARD, p));
    public static final Item WITHER_ROSE_SANDWICH_BOARD = registerBlockItem("wither_rose_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.WITHER_ROSE_SANDWICH_BOARD, p));
    public static final Item EYEBLOSSOM_SANDWICH_BOARD = registerBlockItem("eyeblossom_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.EYEBLOSSOM_SANDWICH_BOARD, p));

    // 彩灯
    public static final Item STRING_LIGHTS_COLORLESS = registerBlockItem("string_lights_colorless", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_COLORLESS, p));
    public static final Item STRING_LIGHTS_WHITE = registerBlockItem("string_lights_white", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_WHITE, p));
    public static final Item STRING_LIGHTS_LIGHT_GRAY = registerBlockItem("string_lights_light_gray", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIGHT_GRAY, p));
    public static final Item STRING_LIGHTS_GRAY = registerBlockItem("string_lights_gray", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_GRAY, p));
    public static final Item STRING_LIGHTS_BLACK = registerBlockItem("string_lights_black", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BLACK, p));
    public static final Item STRING_LIGHTS_BROWN = registerBlockItem("string_lights_brown", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BROWN, p));
    public static final Item STRING_LIGHTS_RED = registerBlockItem("string_lights_red", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_RED, p));
    public static final Item STRING_LIGHTS_ORANGE = registerBlockItem("string_lights_orange", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_ORANGE, p));
    public static final Item STRING_LIGHTS_YELLOW = registerBlockItem("string_lights_yellow", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_YELLOW, p));
    public static final Item STRING_LIGHTS_LIME = registerBlockItem("string_lights_lime", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIME, p));
    public static final Item STRING_LIGHTS_GREEN = registerBlockItem("string_lights_green", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_GREEN, p));
    public static final Item STRING_LIGHTS_CYAN = registerBlockItem("string_lights_cyan", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_CYAN, p));
    public static final Item STRING_LIGHTS_LIGHT_BLUE = registerBlockItem("string_lights_light_blue", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIGHT_BLUE, p));
    public static final Item STRING_LIGHTS_BLUE = registerBlockItem("string_lights_blue", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BLUE, p));
    public static final Item STRING_LIGHTS_PURPLE = registerBlockItem("string_lights_purple", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_PURPLE, p));
    public static final Item STRING_LIGHTS_MAGENTA = registerBlockItem("string_lights_magenta", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_MAGENTA, p));
    public static final Item STRING_LIGHTS_PINK = registerBlockItem("string_lights_pink", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_PINK, p));

    // 挂画
    public static final Item YSBB_PAINTING = registerBlockItem("ysbb_painting", p -> new PaintingBlockItem(ModBlocks.YSBB_PAINTING, p));
    public static final Item TARTARIC_ACID_PAINTING = registerBlockItem("tartaric_acid_painting", p -> new PaintingBlockItem(ModBlocks.TARTARIC_ACID_PAINTING, p));
    public static final Item CR019_PAINTING = registerBlockItem("cr019_painting", p -> new PaintingBlockItem(ModBlocks.CR019_PAINTING, p));
    public static final Item UNKNOWN_PAINTING = registerBlockItem("unknown_painting", p -> new PaintingBlockItem(ModBlocks.UNKNOWN_PAINTING, p));
    public static final Item MASTER_MARISA_PAINTING = registerBlockItem("master_marisa_painting", p -> new PaintingBlockItem(ModBlocks.MASTER_MARISA_PAINTING, p));
    public static final Item SON_OF_MAN_PAINTING = registerBlockItem("son_of_man_painting", p -> new PaintingBlockItem(ModBlocks.SON_OF_MAN_PAINTING, p));
    public static final Item DAVID_PAINTING = registerBlockItem("david_painting", p -> new PaintingBlockItem(ModBlocks.DAVID_PAINTING, p));
    public static final Item GIRL_WITH_PEARL_EARRING_PAINTING = registerBlockItem("girl_with_pearl_earring_painting", p -> new PaintingBlockItem(ModBlocks.GIRL_WITH_PEARL_EARRING_PAINTING, p));
    public static final Item STARRY_NIGHT_PAINTING = registerBlockItem("starry_night_painting", p -> new PaintingBlockItem(ModBlocks.STARRY_NIGHT_PAINTING, p));
    public static final Item VAN_GOGH_SELF_PORTRAIT_PAINTING = registerBlockItem("van_gogh_self_portrait_painting", p -> new PaintingBlockItem(ModBlocks.VAN_GOGH_SELF_PORTRAIT_PAINTING, p));
    public static final Item FATHER_PAINTING = registerBlockItem("father_painting", p -> new PaintingBlockItem(ModBlocks.FATHER_PAINTING, p));
    public static final Item GREAT_WAVE_PAINTING = registerBlockItem("great_wave_painting", p -> new PaintingBlockItem(ModBlocks.GREAT_WAVE_PAINTING, p));
    public static final Item MONA_LISA_PAINTING = registerBlockItem("mona_lisa_painting", p -> new PaintingBlockItem(ModBlocks.MONA_LISA_PAINTING, p));
    public static final Item MONDRIAN_PAINTING = registerBlockItem("mondrian_painting", p -> new PaintingBlockItem(ModBlocks.MONDRIAN_PAINTING, p));
    public static final Item NIGHT_EPIPHANY_PAINTING = registerBlockItem("night_epiphany_painting", p -> new PaintingBlockItem(ModBlocks.NIGHT_EPIPHANY_PAINTING, p));

    // 吧台
    public static final Item BAR_COUNTER = registerBlockItem("bar_counter", p -> new BlockItem(ModBlocks.BAR_COUNTER, p));
    // 人字梯
    public static final Item STEPLADDER = registerBlockItem("stepladder", p -> new BlockItem(ModBlocks.STEPLADDER, p));
    // 藤架
    public static final Item TRELLIS = registerBlockItem("trellis", p -> new TooltipBlockItem(ModBlocks.TRELLIS, p,
            "tooltip.kaleidoscope_tavern.trellis.1",
            "tooltip.kaleidoscope_tavern.trellis.2"
    ));
    // 果盆
    public static final Item PRESSING_TUB = registerBlockItem("pressing_tub", p -> new BlockItem(ModBlocks.PRESSING_TUB, p));
    // 龙头
    public static final Item TAP = registerBlockItem("tap", p -> new BlockItem(ModBlocks.TAP, p));
    // 酒桶
    public static final Item BARREL = registerBlockItem("barrel", p -> new BlockItem(ModBlocks.BARREL, p));
    // 燃烧瓶
    public static final Item MOLOTOV = registerBlockItem("molotov", p -> new MolotovBlockItem(ModBlocks.MOLOTOV, p));
    // 酒柜
    public static final Item BAR_CABINET = registerBlockItem("bar_cabinet", p -> new BlockItem(ModBlocks.BAR_CABINET, p));
    public static final Item GLASS_BAR_CABINET = registerBlockItem("glass_bar_cabinet", p -> new BlockItem(ModBlocks.GLASS_BAR_CABINET, p));
    public static final Item CELLAR_CABINET = registerBlockItem("cellar_cabinet", p -> new BlockItem(ModBlocks.CELLAR_CABINET, p));
    // 酒架
    public static final Item TILTED_RACK = registerBlockItem("tilted_rack", p -> new BlockItem(ModBlocks.TILTED_RACK, p));
    public static final Item CIRCULAR_RACK = registerBlockItem("circular_rack", p -> new BlockItem(ModBlocks.CIRCULAR_RACK, p));
    public static final Item HOLDER = registerBlockItem("holder", p -> new BlockItem(ModBlocks.HOLDER, p));
    // 雪克杯
    public static final Item SHAKER = registerBlockItem("shaker", ShakerItem::new);
    // 酒杯架
    public static final Item GLASSWARE_HOLDER = registerBlockItem("glassware_holder", p -> new BlockItem(ModBlocks.GLASSWARE_HOLDER, p));
    // 酒
    public static final Item WINE = registerBlockItem("wine", p -> new DrinkBlockItem(ModBlocks.WINE, p));
    public static final Item CHAMPAGNE = registerBlockItem("champagne", p -> new DrinkBlockItem(ModBlocks.CHAMPAGNE, p));
    public static final Item VODKA = registerBlockItem("vodka", p -> new DrinkBlockItem(ModBlocks.VODKA, p));
    public static final Item BRANDY = registerBlockItem("brandy", p -> new DrinkBlockItem(ModBlocks.BRANDY, p));
    public static final Item CARIGNAN = registerBlockItem("carignan", p -> new DrinkBlockItem(ModBlocks.CARIGNAN, p));
    public static final Item SAKURA_WINE = registerBlockItem("sakura_wine", p -> new DrinkBlockItem(ModBlocks.SAKURA_WINE, p));
    public static final Item PLUM_WINE = registerBlockItem("plum_wine", p -> new DrinkBlockItem(ModBlocks.PLUM_WINE, p));
    public static final Item WHISKEY = registerBlockItem("whiskey", p -> new DrinkBlockItem(ModBlocks.WHISKEY, p));
    public static final Item ICE_WINE = registerBlockItem("ice_wine", p -> new DrinkBlockItem(ModBlocks.ICE_WINE, p));
    public static final Item VINEGAR = registerBlockItem("vinegar", p -> new DrinkBlockItem(ModBlocks.VINEGAR, p));
    public static final Item POLARIS_SWEET_WHITE = registerBlockItem("polaris_sweet_white", p -> new DrinkBlockItem(ModBlocks.POLARIS_SWEET_WHITE, p));
    public static final Item MOTHER_SNOW = registerBlockItem("mother_snow", p -> new DrinkBlockItem(ModBlocks.MOTHER_SNOW, p));
    public static final Item SHERRY = registerBlockItem("sherry", p -> new DrinkBlockItem(ModBlocks.SHERRY, p));
    public static final Item SWEET_BERRY_WINE = registerBlockItem("sweet_berry_wine", p -> new DrinkBlockItem(ModBlocks.SWEET_BERRY_WINE, p));
    public static final Item RED_QUEEN = registerBlockItem("red_queen", p -> new DrinkBlockItem(ModBlocks.RED_QUEEN, p));
    public static final Item RUM = registerBlockItem("rum", p -> new DrinkBlockItem(ModBlocks.RUM, p));
    public static final Item MINERS_STAR = registerBlockItem("miners_star", p -> new DrinkBlockItem(ModBlocks.MINERS_STAR, p));
    public static final Item HONEY_WINE = registerBlockItem("honey_wine", p -> new DrinkBlockItem(ModBlocks.HONEY_WINE, p));
    public static final Item MADAME_SHEXIANG = registerBlockItem("madame_shexiang", p -> new DrinkBlockItem(ModBlocks.MADAME_SHEXIANG, p));
    public static final Item SUNSET_GLOW = registerBlockItem("sunset_glow", p -> new DrinkBlockItem(ModBlocks.SUNSET_GLOW, p));
    public static final Item SAUVIGNON_BLANC_DRY_WHITE = registerBlockItem("sauvignon_blanc_dry_white", p -> new DrinkBlockItem(ModBlocks.SAUVIGNON_BLANC_DRY_WHITE, p));
    public static final Item RIESLING_DRY_WHITE = registerBlockItem("riesling_dry_white", p -> new DrinkBlockItem(ModBlocks.RIESLING_DRY_WHITE, p));
    public static final Item LUMINOUS_BRIDE = registerBlockItem("luminous_bride", p -> new DrinkBlockItem(ModBlocks.LUMINOUS_BRIDE, p));
    public static final Item GLOWFLOWER_BREW = registerBlockItem("glowflower_brew", p -> new DrinkBlockItem(ModBlocks.GLOWFLOWER_BREW, p));
    public static final Item WATERMELON_JUICE = registerBlockItem("watermelon_juice", p -> new DrinkBlockItem(ModBlocks.WATERMELON_JUICE, p));

    /**
     * 保留供外部调用，现为空方法（注册已在字段初始化时完成）
     */
    public static void registerItems() {
        // 注册已在静态字段初始化时完成
    }
}
