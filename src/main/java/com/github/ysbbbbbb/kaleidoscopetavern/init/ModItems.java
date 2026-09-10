package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.item.*;
import com.github.ysbbbbbb.kaleidoscopetavern.util.PortHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("all")
public final class ModItems {
    // 桌子
    public static final Item TABLE = registerItem("table", p -> new BlockItem(ModBlocks.TABLE, p.useBlockDescriptionPrefix()));
    // 沙发
    public static final Item WHITE_SOFA = registerItem("white_sofa", p -> new SofaBlockItem(ModBlocks.WHITE_SOFA, p));
    public static final Item LIGHT_GRAY_SOFA = registerItem("light_gray_sofa", p -> new SofaBlockItem(ModBlocks.LIGHT_GRAY_SOFA, p));
    public static final Item GRAY_SOFA = registerItem("gray_sofa", p -> new SofaBlockItem(ModBlocks.GRAY_SOFA, p));
    public static final Item BLACK_SOFA = registerItem("black_sofa", p -> new SofaBlockItem(ModBlocks.BLACK_SOFA, p));
    public static final Item BROWN_SOFA = registerItem("brown_sofa", p -> new SofaBlockItem(ModBlocks.BROWN_SOFA, p));
    public static final Item RED_SOFA = registerItem("red_sofa", p -> new SofaBlockItem(ModBlocks.RED_SOFA, p));
    public static final Item ORANGE_SOFA = registerItem("orange_sofa", p -> new SofaBlockItem(ModBlocks.ORANGE_SOFA, p));
    public static final Item YELLOW_SOFA = registerItem("yellow_sofa", p -> new SofaBlockItem(ModBlocks.YELLOW_SOFA, p));
    public static final Item LIME_SOFA = registerItem("lime_sofa", p -> new SofaBlockItem(ModBlocks.LIME_SOFA, p));
    public static final Item GREEN_SOFA = registerItem("green_sofa", p -> new SofaBlockItem(ModBlocks.GREEN_SOFA, p));
    public static final Item CYAN_SOFA = registerItem("cyan_sofa", p -> new SofaBlockItem(ModBlocks.CYAN_SOFA, p));
    public static final Item LIGHT_BLUE_SOFA = registerItem("light_blue_sofa", p -> new SofaBlockItem(ModBlocks.LIGHT_BLUE_SOFA, p));
    public static final Item BLUE_SOFA = registerItem("blue_sofa", p -> new SofaBlockItem(ModBlocks.BLUE_SOFA, p));
    public static final Item PURPLE_SOFA = registerItem("purple_sofa", p -> new SofaBlockItem(ModBlocks.PURPLE_SOFA, p));
    public static final Item MAGENTA_SOFA = registerItem("magenta_sofa", p -> new SofaBlockItem(ModBlocks.MAGENTA_SOFA, p));
    public static final Item PINK_SOFA = registerItem("pink_sofa", p -> new SofaBlockItem(ModBlocks.PINK_SOFA, p));

    // 高脚凳
    public static final Item WHITE_BAR_STOOL = registerItem("white_bar_stool", p -> new BarStoolBlockItem(ModBlocks.WHITE_BAR_STOOL, p));
    public static final Item LIGHT_GRAY_BAR_STOOL = registerItem("light_gray_bar_stool", p -> new BarStoolBlockItem(ModBlocks.LIGHT_GRAY_BAR_STOOL, p));
    public static final Item GRAY_BAR_STOOL = registerItem("gray_bar_stool", p -> new BarStoolBlockItem(ModBlocks.GRAY_BAR_STOOL, p));
    public static final Item BLACK_BAR_STOOL = registerItem("black_bar_stool", p -> new BarStoolBlockItem(ModBlocks.BLACK_BAR_STOOL, p));
    public static final Item BROWN_BAR_STOOL = registerItem("brown_bar_stool", p -> new BarStoolBlockItem(ModBlocks.BROWN_BAR_STOOL, p));
    public static final Item RED_BAR_STOOL = registerItem("red_bar_stool", p -> new BarStoolBlockItem(ModBlocks.RED_BAR_STOOL, p));
    public static final Item ORANGE_BAR_STOOL = registerItem("orange_bar_stool", p -> new BarStoolBlockItem(ModBlocks.ORANGE_BAR_STOOL, p));
    public static final Item YELLOW_BAR_STOOL = registerItem("yellow_bar_stool", p -> new BarStoolBlockItem(ModBlocks.YELLOW_BAR_STOOL, p));
    public static final Item LIME_BAR_STOOL = registerItem("lime_bar_stool", p -> new BarStoolBlockItem(ModBlocks.LIME_BAR_STOOL, p));
    public static final Item GREEN_BAR_STOOL = registerItem("green_bar_stool", p -> new BarStoolBlockItem(ModBlocks.GREEN_BAR_STOOL, p));
    public static final Item CYAN_BAR_STOOL = registerItem("cyan_bar_stool", p -> new BarStoolBlockItem(ModBlocks.CYAN_BAR_STOOL, p));
    public static final Item LIGHT_BLUE_BAR_STOOL = registerItem("light_blue_bar_stool", p -> new BarStoolBlockItem(ModBlocks.LIGHT_BLUE_BAR_STOOL, p));
    public static final Item BLUE_BAR_STOOL = registerItem("blue_bar_stool", p -> new BarStoolBlockItem(ModBlocks.BLUE_BAR_STOOL, p));
    public static final Item PURPLE_BAR_STOOL = registerItem("purple_bar_stool", p -> new BarStoolBlockItem(ModBlocks.PURPLE_BAR_STOOL, p));
    public static final Item MAGENTA_BAR_STOOL = registerItem("magenta_bar_stool", p -> new BarStoolBlockItem(ModBlocks.MAGENTA_BAR_STOOL, p));
    public static final Item PINK_BAR_STOOL = registerItem("pink_bar_stool", p -> new BarStoolBlockItem(ModBlocks.PINK_BAR_STOOL, p));
    // 展板
    public static final Item BASE_SANDWICH_BOARD = registerItem("base_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.BASE_SANDWICH_BOARD, p));
    public static final Item GRASS_SANDWICH_BOARD = registerItem("grass_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.GRASS_SANDWICH_BOARD, p, Items.SHORT_GRASS));
    public static final Item ALLIUM_SANDWICH_BOARD = registerItem("allium_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.ALLIUM_SANDWICH_BOARD, p, Items.ALLIUM));
    public static final Item AZURE_BLUET_SANDWICH_BOARD = registerItem("azure_bluet_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.AZURE_BLUET_SANDWICH_BOARD, p, Items.AZURE_BLUET));
    public static final Item CORNFLOWER_SANDWICH_BOARD = registerItem("cornflower_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.CORNFLOWER_SANDWICH_BOARD, p, Items.CORNFLOWER));
    public static final Item ORCHID_SANDWICH_BOARD = registerItem("orchid_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.ORCHID_SANDWICH_BOARD, p, Items.BLUE_ORCHID));
    public static final Item PEONY_SANDWICH_BOARD = registerItem("peony_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.PEONY_SANDWICH_BOARD, p, Items.PEONY));
    public static final Item PINK_PETALS_SANDWICH_BOARD = registerItem("pink_petals_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.PINK_PETALS_SANDWICH_BOARD, p, Items.PINK_PETALS));
    public static final Item PITCHER_PLANT_SANDWICH_BOARD = registerItem("pitcher_plant_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.PITCHER_PLANT_SANDWICH_BOARD, p, Items.PITCHER_PLANT));
    public static final Item POPPY_SANDWICH_BOARD = registerItem("poppy_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.POPPY_SANDWICH_BOARD, p, Items.POPPY));
    public static final Item SUNFLOWER_SANDWICH_BOARD = registerItem("sunflower_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.SUNFLOWER_SANDWICH_BOARD, p, Items.SUNFLOWER));
    public static final Item TORCHFLOWER_SANDWICH_BOARD = registerItem("torchflower_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.TORCHFLOWER_SANDWICH_BOARD, p, Items.TORCHFLOWER));
    public static final Item TULIP_SANDWICH_BOARD = registerItem("tulip_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.TULIP_SANDWICH_BOARD, p, Items.WHITE_TULIP));
    public static final Item WITHER_ROSE_SANDWICH_BOARD = registerItem("wither_rose_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.WITHER_ROSE_SANDWICH_BOARD, p, Items.WITHER_ROSE));
    public static final Item EYEBLOSSOM_SANDWICH_BOARD = registerItem("eyeblossom_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.EYEBLOSSOM_SANDWICH_BOARD, p, Items.OPEN_EYEBLOSSOM));

    // 彩灯
    public static final Item STRING_LIGHTS_COLORLESS = registerItem("string_lights_colorless", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_COLORLESS, p));
    public static final Item STRING_LIGHTS_WHITE = registerItem("string_lights_white", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_WHITE, p));
    public static final Item STRING_LIGHTS_LIGHT_GRAY = registerItem("string_lights_light_gray", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIGHT_GRAY, p));
    public static final Item STRING_LIGHTS_GRAY = registerItem("string_lights_gray", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_GRAY, p));
    public static final Item STRING_LIGHTS_BLACK = registerItem("string_lights_black", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BLACK, p));
    public static final Item STRING_LIGHTS_BROWN = registerItem("string_lights_brown", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BROWN, p));
    public static final Item STRING_LIGHTS_RED = registerItem("string_lights_red", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_RED, p));
    public static final Item STRING_LIGHTS_ORANGE = registerItem("string_lights_orange", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_ORANGE, p));
    public static final Item STRING_LIGHTS_YELLOW = registerItem("string_lights_yellow", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_YELLOW, p));
    public static final Item STRING_LIGHTS_LIME = registerItem("string_lights_lime", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIME, p));
    public static final Item STRING_LIGHTS_GREEN = registerItem("string_lights_green", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_GREEN, p));
    public static final Item STRING_LIGHTS_CYAN = registerItem("string_lights_cyan", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_CYAN, p));
    public static final Item STRING_LIGHTS_LIGHT_BLUE = registerItem("string_lights_light_blue", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIGHT_BLUE, p));
    public static final Item STRING_LIGHTS_BLUE = registerItem("string_lights_blue", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BLUE, p));
    public static final Item STRING_LIGHTS_PURPLE = registerItem("string_lights_purple", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_PURPLE, p));
    public static final Item STRING_LIGHTS_MAGENTA = registerItem("string_lights_magenta", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_MAGENTA, p));
    public static final Item STRING_LIGHTS_PINK = registerItem("string_lights_pink", p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_PINK, p));

    // 挂画
    public static final Item YSBB_PAINTING = registerItem("ysbb_painting", p -> new PaintingBlockItem(ModBlocks.YSBB_PAINTING, p));
    public static final Item TARTARIC_ACID_PAINTING = registerItem("tartaric_acid_painting", p -> new PaintingBlockItem(ModBlocks.TARTARIC_ACID_PAINTING, p));
    public static final Item CR019_PAINTING = registerItem("cr019_painting", p -> new PaintingBlockItem(ModBlocks.CR019_PAINTING, p));
    public static final Item UNKNOWN_PAINTING = registerItem("unknown_painting", p -> new PaintingBlockItem(ModBlocks.UNKNOWN_PAINTING, p));
    public static final Item MASTER_MARISA_PAINTING = registerItem("master_marisa_painting", p -> new PaintingBlockItem(ModBlocks.MASTER_MARISA_PAINTING, p));
    public static final Item SON_OF_MAN_PAINTING = registerItem("son_of_man_painting", p -> new PaintingBlockItem(ModBlocks.SON_OF_MAN_PAINTING, p));
    public static final Item DAVID_PAINTING = registerItem("david_painting", p -> new PaintingBlockItem(ModBlocks.DAVID_PAINTING, p));
    public static final Item GIRL_WITH_PEARL_EARRING_PAINTING = registerItem("girl_with_pearl_earring_painting", p -> new PaintingBlockItem(ModBlocks.GIRL_WITH_PEARL_EARRING_PAINTING, p));
    public static final Item STARRY_NIGHT_PAINTING = registerItem("starry_night_painting", p -> new PaintingBlockItem(ModBlocks.STARRY_NIGHT_PAINTING, p));
    public static final Item VAN_GOGH_SELF_PORTRAIT_PAINTING = registerItem("van_gogh_self_portrait_painting", p -> new PaintingBlockItem(ModBlocks.VAN_GOGH_SELF_PORTRAIT_PAINTING, p));
    public static final Item FATHER_PAINTING = registerItem("father_painting", p -> new PaintingBlockItem(ModBlocks.FATHER_PAINTING, p));
    public static final Item GREAT_WAVE_PAINTING = registerItem("great_wave_painting", p -> new PaintingBlockItem(ModBlocks.GREAT_WAVE_PAINTING, p));
    public static final Item MONA_LISA_PAINTING = registerItem("mona_lisa_painting", p -> new PaintingBlockItem(ModBlocks.MONA_LISA_PAINTING, p));
    public static final Item MONDRIAN_PAINTING = registerItem("mondrian_painting", p -> new PaintingBlockItem(ModBlocks.MONDRIAN_PAINTING, p));
    public static final Item NIGHT_EPIPHANY_PAINTING = registerItem("night_epiphany_painting", p -> new PaintingBlockItem(ModBlocks.NIGHT_EPIPHANY_PAINTING, p));
    // 空瓶
    public static final Item EMPTY_BOTTLE = registerItemViaBlock(ModBlocks.EMPTY_BOTTLE, BottleBlockItem::new);
    public static final Item EMPTY_GLASSWARE = registerItemViaBlock(ModBlocks.EMPTY_GLASSWARE, GlasswareBlockItem::new);
    public static final Item SIGNATURE_COCKTAIL = registerItemViaBlock(ModBlocks.SIGNATURE_COCKTAIL, SignatureCocktailBlockItem::new);
    public static final Item MYSTERY_COCKTAIL = registerItemViaBlock(ModBlocks.MYSTERY_COCKTAIL, CocktailBlockItem::new);
    public static final Item WHITE_LADY = registerItemViaBlock(ModBlocks.WHITE_LADY, CocktailBlockItem::new);
    public static final Item EMERALD = registerItemViaBlock(ModBlocks.EMERALD, CocktailBlockItem::new);
    public static final Item BRASS_HEART = registerItemViaBlock(ModBlocks.BRASS_HEART, CocktailBlockItem::new);
    public static final Item GODFATHER = registerItemViaBlock(ModBlocks.GODFATHER, CocktailBlockItem::new);
    public static final Item GRASSHOPPER = registerItemViaBlock(ModBlocks.GRASSHOPPER, CocktailBlockItem::new);
    public static final Item SCREWDRIVER = registerItemViaBlock(ModBlocks.SCREWDRIVER, CocktailBlockItem::new);
    public static final Item MOJITO = registerItemViaBlock(ModBlocks.MOJITO, CocktailBlockItem::new);
    public static final Item ALLIUM_GARDEN = registerItemViaBlock(ModBlocks.ALLIUM_GARDEN, CocktailBlockItem::new);
    public static final Item DEPTH_CHARGE = registerItemViaBlock(ModBlocks.DEPTH_CHARGE, CocktailBlockItem::new);
    public static final Item NETHER_SPECIAL = registerItemViaBlock(ModBlocks.NETHER_SPECIAL, CocktailBlockItem::new);
    public static final Item BLOODY_MARY = registerItemViaBlock(ModBlocks.BLOODY_MARY, CocktailBlockItem::new);
    public static final Item SCULK_SPECIAL = registerItemViaBlock(ModBlocks.SCULK_SPECIAL, CocktailBlockItem::new);
    // 葡萄
    public static final Item GRAPE = registerItem("grape", p -> new TooltipItem(p.food(ModFoods.GRAPE), "tooltip.kaleidoscope_tavern.grape"));
    public static final Item ICE_GRAPE = registerItem("ice_grape", p -> new TooltipItem(p.food(ModFoods.GRAPE), "tooltip.kaleidoscope_tavern.ice_grape"));
    public static final Item GOLD_GRAPE = registerItem("gold_grape", p -> new TooltipItem(p.food(ModFoods.GRAPE), "tooltip.kaleidoscope_tavern.gold_grape"));
    public static final Item GREEN_GRAPE = registerItem("green_grape", p -> new TooltipItem(p.food(ModFoods.GRAPE), "tooltip.kaleidoscope_tavern.green_grape"));
    // 野生葡萄藤
    public static final Item GRAPEVINE = registerItem("grapevine", s -> new GrapevineItem(s.cookingFuel(ContextIntProviders.COOKING_TIME_BAMBOO)));
    // 黑板
    public static final Item CHALKBOARD = registerItem("chalkboard", p -> new BlockItem(ModBlocks.CHALKBOARD, p.useBlockDescriptionPrefix()));
    public static final Item BELL_PENDANT_LAMP = registerItem("bell_pendant_lamp", p -> new BlockItem(ModBlocks.BELL_PENDANT_LAMP, p.useBlockDescriptionPrefix()));
    public static final Item YELLOW_PENDANT_LAMP = registerItem("yellow_pendant_lamp", p -> new BlockItem(ModBlocks.YELLOW_PENDANT_LAMP, p.useBlockDescriptionPrefix()));
    public static final Item BLUE_PENDANT_LAMP = registerItem("blue_pendant_lamp", p -> new BlockItem(ModBlocks.BLUE_PENDANT_LAMP, p.useBlockDescriptionPrefix()));
    public static final Item SAKURA_INCENSE = registerItem("sakura_incense", p -> new BlockItem(ModBlocks.SAKURA_INCENSE, p.useBlockDescriptionPrefix()));
    public static final Item PINE_INCENSE = registerItem("pine_incense", p -> new BlockItem(ModBlocks.PINE_INCENSE, p.useBlockDescriptionPrefix()));
    public static final Item GINKGO_INCENSE = registerItem("ginkgo_incense", p -> new BlockItem(ModBlocks.GINKGO_INCENSE, p.useBlockDescriptionPrefix()));
    public static final Item SPORE_INCENSE = registerItem("spore_incense", p -> new BlockItem(ModBlocks.SPORE_INCENSE, p.useBlockDescriptionPrefix()));
    public static final Item CATNIP_INCENSE = registerItem("catnip_incense", p -> new BlockItem(ModBlocks.CATNIP_INCENSE, p.useBlockDescriptionPrefix()));
    public static final Item SNOW_INCENSE = registerItem("snow_incense", p -> new BlockItem(ModBlocks.SNOW_INCENSE, p.useBlockDescriptionPrefix()));
    public static final Item BUTTERFLY_INCENSE = registerItem("butterfly_incense", p -> new BlockItem(ModBlocks.BUTTERFLY_INCENSE, p.useBlockDescriptionPrefix()));
    public static final Item FIREFLY_INCENSE = registerItem("firefly_incense", p -> new BlockItem(ModBlocks.FIREFLY_INCENSE, p.useBlockDescriptionPrefix()));
    // 燃烧瓶
    public static final Item MOLOTOV = registerItem("molotov", p -> new MolotovBlockItem(ModBlocks.MOLOTOV, p.useBlockDescriptionPrefix()));
    // 吧台
    public static final Item BAR_COUNTER = registerItem("bar_counter", p -> new BlockItem(ModBlocks.BAR_COUNTER, p.useBlockDescriptionPrefix()));
    // 人字梯
    public static final Item STEPLADDER = registerItem("stepladder", p -> new BlockItem(ModBlocks.STEPLADDER, p.useBlockDescriptionPrefix()));
    // 藤架
    public static final Item TRELLIS = registerItem("trellis", p -> new TooltipBlockItem(
            ModBlocks.TRELLIS,
            p.useBlockDescriptionPrefix(),
            "tooltip.kaleidoscope_tavern.trellis.1",
            "tooltip.kaleidoscope_tavern.trellis.2"
    ));
    // 果盆
    public static final Item PRESSING_TUB = registerItem("pressing_tub", p -> new BlockItem(ModBlocks.PRESSING_TUB, p.useBlockDescriptionPrefix()));
    // 龙头
    public static final Item TAP = registerItem("tap", p -> new BlockItem(ModBlocks.TAP, p.useBlockDescriptionPrefix()));
    // 酒桶
    public static final Item BARREL = registerItem("barrel", p -> new BlockItem(ModBlocks.BARREL, p.useBlockDescriptionPrefix()));
    // 酒柜
    public static final Item BAR_CABINET =registerItem("bar_cabinet", p -> new BlockItem(ModBlocks.BAR_CABINET, p.useBlockDescriptionPrefix()));
    public static final Item GLASS_BAR_CABINET = registerItem("glass_bar_cabinet", p -> new BlockItem(ModBlocks.GLASS_BAR_CABINET, p.useBlockDescriptionPrefix()));
    public static final Item CELLAR_CABINET = registerItem("cellar_cabinet", p -> new BlockItem(ModBlocks.CELLAR_CABINET, p.useBlockDescriptionPrefix()));
    public static final Item TILTED_RACK = registerItem("tilted_rack", p -> new BlockItem(ModBlocks.TILTED_RACK, p.useBlockDescriptionPrefix()));
    public static final Item CIRCULAR_RACK = registerItem("circular_rack", p -> new BlockItem(ModBlocks.CIRCULAR_RACK, p.useBlockDescriptionPrefix()));
    public static final Item HOLDER = registerItem("holder", p -> new BlockItem(ModBlocks.HOLDER, p.useBlockDescriptionPrefix()));
    public static final Item SHAKER = registerItem("shaker", ShakerItem::new);
    public static final Item GLASSWARE_HOLDER = registerItem("glassware_holder", p -> new BlockItem(ModBlocks.GLASSWARE_HOLDER, p.useBlockDescriptionPrefix()));
    // 酒
    public static final Item WINE = registerItem("wine", p -> new DrinkBlockItem(ModBlocks.WINE, p));
    public static final Item CHAMPAGNE = registerItem("champagne", p -> new DrinkBlockItem(ModBlocks.CHAMPAGNE, p));
    public static final Item VODKA = registerItem("vodka", p -> new DrinkBlockItem(ModBlocks.VODKA, p));
    public static final Item BRANDY = registerItem("brandy", p -> new DrinkBlockItem(ModBlocks.BRANDY, p));
    public static final Item CARIGNAN = registerItem("carignan", p -> new DrinkBlockItem(ModBlocks.CARIGNAN, p));
    public static final Item SAKURA_WINE = registerItem("sakura_wine", p -> new DrinkBlockItem(ModBlocks.SAKURA_WINE, p));
    public static final Item PLUM_WINE = registerItem("plum_wine", p -> new DrinkBlockItem(ModBlocks.PLUM_WINE, p));
    public static final Item WHISKEY = registerItem("whiskey", p -> new DrinkBlockItem(ModBlocks.WHISKEY, p));
    public static final Item ICE_WINE = registerItem("ice_wine", p -> new DrinkBlockItem(ModBlocks.ICE_WINE, p));
    public static final Item VINEGAR = registerItem("vinegar", p -> new DrinkBlockItem(ModBlocks.VINEGAR, p));
    public static final Item POLARIS_SWEET_WHITE = registerItem("polaris_sweet_white", p -> new DrinkBlockItem(ModBlocks.POLARIS_SWEET_WHITE, p));
    public static final Item HONEY_WINE = registerItem("honey_wine", p -> new DrinkBlockItem(ModBlocks.HONEY_WINE, p));
    public static final Item RED_QUEEN = registerItem("red_queen", p -> new DrinkBlockItem(ModBlocks.RED_QUEEN, p));
    public static final Item MINERS_STAR = registerItem("miners_star", p -> new DrinkBlockItem(ModBlocks.MINERS_STAR, p));
    public static final Item RUM = registerItem("rum", p -> new DrinkBlockItem(ModBlocks.RUM, p));
    public static final Item RIESLING_DRY_WHITE = registerItem("riesling_dry_white", p -> new DrinkBlockItem(ModBlocks.RIESLING_DRY_WHITE, p));
    public static final Item SUNSET_GLOW = registerItem("sunset_glow", p -> new DrinkBlockItem(ModBlocks.SUNSET_GLOW, p));
    public static final Item MADAME_SHEXIANG = registerItem("madame_shexiang", p -> new DrinkBlockItem(ModBlocks.MADAME_SHEXIANG, p));
    public static final Item SWEET_BERRY_WINE = registerItem("sweet_berry_wine", p -> new DrinkBlockItem(ModBlocks.SWEET_BERRY_WINE, p));
    public static final Item SHERRY = registerItem("sherry", p -> new DrinkBlockItem(ModBlocks.SHERRY, p));
    public static final Item MOTHER_SNOW = registerItem("mother_snow", p -> new DrinkBlockItem(ModBlocks.MOTHER_SNOW, p));
    public static final Item LUMINOUS_BRIDE = registerItem("luminous_bride", p -> new DrinkBlockItem(ModBlocks.LUMINOUS_BRIDE, p));
    public static final Item GLOWFLOWER_BREW = registerItem("glowflower_brew", p -> new DrinkBlockItem(ModBlocks.GLOWFLOWER_BREW, p));
    public static final Item SAUVIGNON_BLANC_DRY_WHITE = registerItem("sauvignon_blanc_dry_white", p -> new DrinkBlockItem(ModBlocks.SAUVIGNON_BLANC_DRY_WHITE, p));
    public static final Item WATERMELON_JUICE = registerItem("watermelon_juice", p -> new DrinkBlockItem(ModBlocks.WATERMELON_JUICE, p));

    // 果汁桶
    public static final Item GRAPE_BUCKET = registerItem("grape_bucket", p -> new JuiceBucketItem(ModFluids.GRAPE_JUICE, p));

    public static final Item ICE_GRAPE_BUCKET = registerItem("ice_grape_bucket", p -> new JuiceBucketItem(ModFluids.ICE_GRAPE_JUICE, p));

    public static final Item GOLD_GRAPE_BUCKET = registerItem("gold_grape_bucket", p -> new JuiceBucketItem(ModFluids.GOLD_GRAPE_JUICE, p));

    public static final Item GREEN_GRAPE_BUCKET = registerItem("green_grape_bucket", p -> new JuiceBucketItem(ModFluids.GREEN_GRAPE_JUICE, p));
    public static final Item SWEET_BERRIES_BUCKET = registerItem("sweet_berries_bucket", p -> new JuiceBucketItem(ModFluids.SWEET_BERRIES_JUICE, p));
    public static final Item GLOW_BERRIES_BUCKET = registerItem("glow_berries_bucket", p -> new JuiceBucketItem(ModFluids.GLOW_BERRIES_JUICE, p));
    public static void registerItems() {

    }

    @SuppressWarnings("deprecation")
    public static Item registerItemViaBlock(Block block, BiFunction<Block, Item.Properties, Item> biFunction, Item.Properties properties) {
        return registerItem(
                blockIdToItemId(block.builtInRegistryHolder().key()), properties2 -> biFunction.apply(block, properties2), properties.useBlockDescriptionPrefix()
        );
    }

    private static ResourceKey<Item> blockIdToItemId(ResourceKey<Block> resourceKey) {
        return ResourceKey.create(Registries.ITEM, resourceKey.identifier());
    }

    public static Item registerItem(ResourceKey<Item> resourceKey, Function<Item.Properties, Item> function, Item.Properties properties) {
        Item item = function.apply(properties.setId(resourceKey));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }

        return Registry.register(BuiltInRegistries.ITEM, resourceKey, item);
    }

    private static Function<Item.Properties, Item> createBlockItemWithCustomItemName(Block block) {
        return properties -> new BlockItem(block, properties.useItemDescriptionPrefix());
    }

    public static Item registerItem(String string) {
        return registerItem(PortHelper.createItemId(string), Item::new, new Item.Properties());
    }

    public static Item registerItem(String string, Function<Item.Properties, Item> function) {
        return registerItem(PortHelper.createItemId(string), function, new Item.Properties());
    }

    public static Item registerItem(String string, Function<Item.Properties, Item> function, Item.Properties properties) {
        return registerItem(PortHelper.createItemId(string), function, properties);
    }


    public static Item registerItemViaBlock(Block block, BiFunction<Block, Item.Properties, Item> biFunction) {
        return registerItemViaBlock(block, biFunction, new Item.Properties());
    }

    public static Item registerItemViaBlock(Block block) {
        return registerItemViaBlock(block, BlockItem::new);
    }
}
