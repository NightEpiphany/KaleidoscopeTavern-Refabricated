package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.init.registery.ids.ModBlockItemIds;
import com.github.ysbbbbbb.kaleidoscopetavern.init.registery.ids.ModItemIds;
import com.github.ysbbbbbb.kaleidoscopetavern.item.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
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
    public static final Item TABLE = registerItem(ModBlockItemIds.TABLE, p -> new BlockItem(ModBlocks.TABLE, p.useBlockDescriptionPrefix()));
    // 沙发
    public static final Item WHITE_SOFA = registerItem(ModBlockItemIds.WHITE_SOFA, p -> new SofaBlockItem(ModBlocks.WHITE_SOFA, p));
    public static final Item LIGHT_GRAY_SOFA = registerItem(ModBlockItemIds.LIGHT_GRAY_SOFA, p -> new SofaBlockItem(ModBlocks.LIGHT_GRAY_SOFA, p));
    public static final Item GRAY_SOFA = registerItem(ModBlockItemIds.GRAY_SOFA, p -> new SofaBlockItem(ModBlocks.GRAY_SOFA, p));
    public static final Item BLACK_SOFA = registerItem(ModBlockItemIds.BLACK_SOFA, p -> new SofaBlockItem(ModBlocks.BLACK_SOFA, p));
    public static final Item BROWN_SOFA = registerItem(ModBlockItemIds.BROWN_SOFA, p -> new SofaBlockItem(ModBlocks.BROWN_SOFA, p));
    public static final Item RED_SOFA = registerItem(ModBlockItemIds.RED_SOFA, p -> new SofaBlockItem(ModBlocks.RED_SOFA, p));
    public static final Item ORANGE_SOFA = registerItem(ModBlockItemIds.ORANGE_SOFA, p -> new SofaBlockItem(ModBlocks.ORANGE_SOFA, p));
    public static final Item YELLOW_SOFA = registerItem(ModBlockItemIds.YELLOW_SOFA, p -> new SofaBlockItem(ModBlocks.YELLOW_SOFA, p));
    public static final Item LIME_SOFA = registerItem(ModBlockItemIds.LIME_SOFA, p -> new SofaBlockItem(ModBlocks.LIME_SOFA, p));
    public static final Item GREEN_SOFA = registerItem(ModBlockItemIds.GREEN_SOFA, p -> new SofaBlockItem(ModBlocks.GREEN_SOFA, p));
    public static final Item CYAN_SOFA = registerItem(ModBlockItemIds.CYAN_SOFA, p -> new SofaBlockItem(ModBlocks.CYAN_SOFA, p));
    public static final Item LIGHT_BLUE_SOFA = registerItem(ModBlockItemIds.LIGHT_BLUE_SOFA, p -> new SofaBlockItem(ModBlocks.LIGHT_BLUE_SOFA, p));
    public static final Item BLUE_SOFA = registerItem(ModBlockItemIds.BLUE_SOFA, p -> new SofaBlockItem(ModBlocks.BLUE_SOFA, p));
    public static final Item PURPLE_SOFA = registerItem(ModBlockItemIds.PURPLE_SOFA, p -> new SofaBlockItem(ModBlocks.PURPLE_SOFA, p));
    public static final Item MAGENTA_SOFA = registerItem(ModBlockItemIds.MAGENTA_SOFA, p -> new SofaBlockItem(ModBlocks.MAGENTA_SOFA, p));
    public static final Item PINK_SOFA = registerItem(ModBlockItemIds.PINK_SOFA, p -> new SofaBlockItem(ModBlocks.PINK_SOFA, p));

    // 高脚凳
    public static final Item WHITE_BAR_STOOL = registerItem(ModBlockItemIds.WHITE_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.WHITE_BAR_STOOL, p));
    public static final Item LIGHT_GRAY_BAR_STOOL = registerItem(ModBlockItemIds.LIGHT_GRAY_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.LIGHT_GRAY_BAR_STOOL, p));
    public static final Item GRAY_BAR_STOOL = registerItem(ModBlockItemIds.GRAY_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.GRAY_BAR_STOOL, p));
    public static final Item BLACK_BAR_STOOL = registerItem(ModBlockItemIds.BLACK_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.BLACK_BAR_STOOL, p));
    public static final Item BROWN_BAR_STOOL = registerItem(ModBlockItemIds.BROWN_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.BROWN_BAR_STOOL, p));
    public static final Item RED_BAR_STOOL = registerItem(ModBlockItemIds.RED_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.RED_BAR_STOOL, p));
    public static final Item ORANGE_BAR_STOOL = registerItem(ModBlockItemIds.ORANGE_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.ORANGE_BAR_STOOL, p));
    public static final Item YELLOW_BAR_STOOL = registerItem(ModBlockItemIds.YELLOW_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.YELLOW_BAR_STOOL, p));
    public static final Item LIME_BAR_STOOL = registerItem(ModBlockItemIds.LIME_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.LIME_BAR_STOOL, p));
    public static final Item GREEN_BAR_STOOL = registerItem(ModBlockItemIds.GREEN_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.GREEN_BAR_STOOL, p));
    public static final Item CYAN_BAR_STOOL = registerItem(ModBlockItemIds.CYAN_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.CYAN_BAR_STOOL, p));
    public static final Item LIGHT_BLUE_BAR_STOOL = registerItem(ModBlockItemIds.LIGHT_BLUE_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.LIGHT_BLUE_BAR_STOOL, p));
    public static final Item BLUE_BAR_STOOL = registerItem(ModBlockItemIds.BLUE_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.BLUE_BAR_STOOL, p));
    public static final Item PURPLE_BAR_STOOL = registerItem(ModBlockItemIds.PURPLE_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.PURPLE_BAR_STOOL, p));
    public static final Item MAGENTA_BAR_STOOL = registerItem(ModBlockItemIds.MAGENTA_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.MAGENTA_BAR_STOOL, p));
    public static final Item PINK_BAR_STOOL = registerItem(ModBlockItemIds.PINK_BAR_STOOL, p -> new BarStoolBlockItem(ModBlocks.PINK_BAR_STOOL, p));
    // 展板
    public static final Item BASE_SANDWICH_BOARD = registerItem(ModBlockItemIds.BASE_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.BASE_SANDWICH_BOARD, p));
    public static final Item GRASS_SANDWICH_BOARD = registerItem(ModBlockItemIds.GRASS_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.GRASS_SANDWICH_BOARD, p, Items.SHORT_GRASS));
    public static final Item ALLIUM_SANDWICH_BOARD = registerItem(ModBlockItemIds.ALLIUM_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.ALLIUM_SANDWICH_BOARD, p, Items.ALLIUM));
    public static final Item AZURE_BLUET_SANDWICH_BOARD = registerItem(ModBlockItemIds.AZURE_BLUET_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.AZURE_BLUET_SANDWICH_BOARD, p, Items.AZURE_BLUET));
    public static final Item CORNFLOWER_SANDWICH_BOARD = registerItem(ModBlockItemIds.CORNFLOWER_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.CORNFLOWER_SANDWICH_BOARD, p, Items.CORNFLOWER));
    public static final Item ORCHID_SANDWICH_BOARD = registerItem(ModBlockItemIds.ORCHID_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.ORCHID_SANDWICH_BOARD, p, Items.BLUE_ORCHID));
    public static final Item PEONY_SANDWICH_BOARD = registerItem(ModBlockItemIds.PEONY_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.PEONY_SANDWICH_BOARD, p, Items.PEONY));
    public static final Item PINK_PETALS_SANDWICH_BOARD = registerItem(ModBlockItemIds.PINK_PETALS_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.PINK_PETALS_SANDWICH_BOARD, p, Items.PINK_PETALS));
    public static final Item PITCHER_PLANT_SANDWICH_BOARD = registerItem(ModBlockItemIds.PITCHER_PLANT_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.PITCHER_PLANT_SANDWICH_BOARD, p, Items.PITCHER_PLANT));
    public static final Item POPPY_SANDWICH_BOARD = registerItem(ModBlockItemIds.POPPY_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.POPPY_SANDWICH_BOARD, p, Items.POPPY));
    public static final Item SUNFLOWER_SANDWICH_BOARD = registerItem(ModBlockItemIds.SUNFLOWER_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.SUNFLOWER_SANDWICH_BOARD, p, Items.SUNFLOWER));
    public static final Item TORCHFLOWER_SANDWICH_BOARD = registerItem(ModBlockItemIds.TORCHFLOWER_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.TORCHFLOWER_SANDWICH_BOARD, p, Items.TORCHFLOWER));
    public static final Item TULIP_SANDWICH_BOARD = registerItem(ModBlockItemIds.TULIP_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.TULIP_SANDWICH_BOARD, p, Items.WHITE_TULIP));
    public static final Item WITHER_ROSE_SANDWICH_BOARD = registerItem(ModBlockItemIds.WITHER_ROSE_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.WITHER_ROSE_SANDWICH_BOARD, p, Items.WITHER_ROSE));
    public static final Item EYEBLOSSOM_SANDWICH_BOARD = registerItem(ModBlockItemIds.EYEBLOSSOM_SANDWICH_BOARD, p -> new SandwichBoardBlockItem(ModBlocks.EYEBLOSSOM_SANDWICH_BOARD, p, Items.OPEN_EYEBLOSSOM));

    // 彩灯
    public static final Item STRING_LIGHTS_COLORLESS = registerItem(ModBlockItemIds.STRING_LIGHTS_COLORLESS, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_COLORLESS, p));
    public static final Item STRING_LIGHTS_WHITE = registerItem(ModBlockItemIds.STRING_LIGHTS_WHITE, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_WHITE, p));
    public static final Item STRING_LIGHTS_LIGHT_GRAY = registerItem(ModBlockItemIds.STRING_LIGHTS_LIGHT_GRAY, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIGHT_GRAY, p));
    public static final Item STRING_LIGHTS_GRAY = registerItem(ModBlockItemIds.STRING_LIGHTS_GRAY, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_GRAY, p));
    public static final Item STRING_LIGHTS_BLACK = registerItem(ModBlockItemIds.STRING_LIGHTS_BLACK, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BLACK, p));
    public static final Item STRING_LIGHTS_BROWN = registerItem(ModBlockItemIds.STRING_LIGHTS_BROWN, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BROWN, p));
    public static final Item STRING_LIGHTS_RED = registerItem(ModBlockItemIds.STRING_LIGHTS_RED, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_RED, p));
    public static final Item STRING_LIGHTS_ORANGE = registerItem(ModBlockItemIds.STRING_LIGHTS_ORANGE, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_ORANGE, p));
    public static final Item STRING_LIGHTS_YELLOW = registerItem(ModBlockItemIds.STRING_LIGHTS_YELLOW, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_YELLOW, p));
    public static final Item STRING_LIGHTS_LIME = registerItem(ModBlockItemIds.STRING_LIGHTS_LIME, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIME, p));
    public static final Item STRING_LIGHTS_GREEN = registerItem(ModBlockItemIds.STRING_LIGHTS_GREEN, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_GREEN, p));
    public static final Item STRING_LIGHTS_CYAN = registerItem(ModBlockItemIds.STRING_LIGHTS_CYAN, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_CYAN, p));
    public static final Item STRING_LIGHTS_LIGHT_BLUE = registerItem(ModBlockItemIds.STRING_LIGHTS_LIGHT_BLUE, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIGHT_BLUE, p));
    public static final Item STRING_LIGHTS_BLUE = registerItem(ModBlockItemIds.STRING_LIGHTS_BLUE, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BLUE, p));
    public static final Item STRING_LIGHTS_PURPLE = registerItem(ModBlockItemIds.STRING_LIGHTS_PURPLE, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_PURPLE, p));
    public static final Item STRING_LIGHTS_MAGENTA = registerItem(ModBlockItemIds.STRING_LIGHTS_MAGENTA, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_MAGENTA, p));
    public static final Item STRING_LIGHTS_PINK = registerItem(ModBlockItemIds.STRING_LIGHTS_PINK, p -> new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_PINK, p));

    // 挂画
    public static final Item YSBB_PAINTING = registerItem(ModBlockItemIds.YSBB_PAINTING, p -> new PaintingBlockItem(ModBlocks.YSBB_PAINTING, p));
    public static final Item TARTARIC_ACID_PAINTING = registerItem(ModBlockItemIds.TARTARIC_ACID_PAINTING, p -> new PaintingBlockItem(ModBlocks.TARTARIC_ACID_PAINTING, p));
    public static final Item CR019_PAINTING = registerItem(ModBlockItemIds.CR019_PAINTING, p -> new PaintingBlockItem(ModBlocks.CR019_PAINTING, p));
    public static final Item UNKNOWN_PAINTING = registerItem(ModBlockItemIds.UNKNOWN_PAINTING, p -> new PaintingBlockItem(ModBlocks.UNKNOWN_PAINTING, p));
    public static final Item MASTER_MARISA_PAINTING = registerItem(ModBlockItemIds.MASTER_MARISA_PAINTING, p -> new PaintingBlockItem(ModBlocks.MASTER_MARISA_PAINTING, p));
    public static final Item SON_OF_MAN_PAINTING = registerItem(ModBlockItemIds.SON_OF_MAN_PAINTING, p -> new PaintingBlockItem(ModBlocks.SON_OF_MAN_PAINTING, p));
    public static final Item DAVID_PAINTING = registerItem(ModBlockItemIds.DAVID_PAINTING, p -> new PaintingBlockItem(ModBlocks.DAVID_PAINTING, p));
    public static final Item GIRL_WITH_PEARL_EARRING_PAINTING = registerItem(ModBlockItemIds.GIRL_WITH_PEARL_EARRING_PAINTING, p -> new PaintingBlockItem(ModBlocks.GIRL_WITH_PEARL_EARRING_PAINTING, p));
    public static final Item STARRY_NIGHT_PAINTING = registerItem(ModBlockItemIds.STARRY_NIGHT_PAINTING, p -> new PaintingBlockItem(ModBlocks.STARRY_NIGHT_PAINTING, p));
    public static final Item VAN_GOGH_SELF_PORTRAIT_PAINTING = registerItem(ModBlockItemIds.VAN_GOGH_SELF_PORTRAIT_PAINTING, p -> new PaintingBlockItem(ModBlocks.VAN_GOGH_SELF_PORTRAIT_PAINTING, p));
    public static final Item FATHER_PAINTING = registerItem(ModBlockItemIds.FATHER_PAINTING, p -> new PaintingBlockItem(ModBlocks.FATHER_PAINTING, p));
    public static final Item GREAT_WAVE_PAINTING = registerItem(ModBlockItemIds.GREAT_WAVE_PAINTING, p -> new PaintingBlockItem(ModBlocks.GREAT_WAVE_PAINTING, p));
    public static final Item MONA_LISA_PAINTING = registerItem(ModBlockItemIds.MONA_LISA_PAINTING, p -> new PaintingBlockItem(ModBlocks.MONA_LISA_PAINTING, p));
    public static final Item MONDRIAN_PAINTING = registerItem(ModBlockItemIds.MONDRIAN_PAINTING, p -> new PaintingBlockItem(ModBlocks.MONDRIAN_PAINTING, p));
    public static final Item NIGHT_EPIPHANY_PAINTING = registerItem(ModBlockItemIds.NIGHT_EPIPHANY_PAINTING, p -> new PaintingBlockItem(ModBlocks.NIGHT_EPIPHANY_PAINTING, p));
    public static final Item DOBELRING_PAINTING = registerItem(ModBlockItemIds.DOBELRING_PAINTING, p -> new PaintingBlockItem(ModBlocks.DOBELRING_PAINTING, p));
    // 空瓶
    public static final Item EMPTY_BOTTLE = registerItemViaBlock(ModBlockItemIds.EMPTY_BOTTLE, ModBlocks.EMPTY_BOTTLE, BottleBlockItem::new);
    public static final Item EMPTY_GLASSWARE = registerItemViaBlock(ModBlockItemIds.EMPTY_GLASSWARE, ModBlocks.EMPTY_GLASSWARE, GlasswareBlockItem::new);
    public static final Item SIGNATURE_COCKTAIL = registerItemViaBlock(ModBlockItemIds.SIGNATURE_COCKTAIL, ModBlocks.SIGNATURE_COCKTAIL, SignatureCocktailBlockItem::new);
    public static final Item MYSTERY_COCKTAIL = registerItemViaBlock(ModBlockItemIds.MYSTERY_COCKTAIL, ModBlocks.MYSTERY_COCKTAIL, CocktailBlockItem::new);
    public static final Item WHITE_LADY = registerItemViaBlock(ModBlockItemIds.WHITE_LADY, ModBlocks.WHITE_LADY, CocktailBlockItem::new);
    public static final Item EMERALD = registerItemViaBlock(ModBlockItemIds.EMERALD, ModBlocks.EMERALD, CocktailBlockItem::new);
    public static final Item BRASS_HEART = registerItemViaBlock(ModBlockItemIds.BRASS_HEART, ModBlocks.BRASS_HEART, CocktailBlockItem::new);
    public static final Item GODFATHER = registerItemViaBlock(ModBlockItemIds.GODFATHER, ModBlocks.GODFATHER, CocktailBlockItem::new);
    public static final Item GRASSHOPPER = registerItemViaBlock(ModBlockItemIds.GRASSHOPPER, ModBlocks.GRASSHOPPER, CocktailBlockItem::new);
    public static final Item SCREWDRIVER = registerItemViaBlock(ModBlockItemIds.SCREWDRIVER, ModBlocks.SCREWDRIVER, CocktailBlockItem::new);
    public static final Item MOJITO = registerItemViaBlock(ModBlockItemIds.MOJITO, ModBlocks.MOJITO, CocktailBlockItem::new);
    public static final Item ALLIUM_GARDEN = registerItemViaBlock(ModBlockItemIds.ALLIUM_GARDEN, ModBlocks.ALLIUM_GARDEN, CocktailBlockItem::new);
    public static final Item DEPTH_CHARGE = registerItemViaBlock(ModBlockItemIds.DEPTH_CHARGE, ModBlocks.DEPTH_CHARGE, CocktailBlockItem::new);
    public static final Item NETHER_SPECIAL = registerItemViaBlock(ModBlockItemIds.NETHER_SPECIAL, ModBlocks.NETHER_SPECIAL, CocktailBlockItem::new);
    public static final Item BLOODY_MARY = registerItemViaBlock(ModBlockItemIds.BLOODY_MARY, ModBlocks.BLOODY_MARY, CocktailBlockItem::new);
    public static final Item SCULK_SPECIAL = registerItemViaBlock(ModBlockItemIds.SCULK_SPECIAL, ModBlocks.SCULK_SPECIAL, CocktailBlockItem::new);
    // 葡萄
    public static final Item GRAPE = registerItem(ModBlockItemIds.GRAPE_CROP, p -> new TooltipItem(p.food(ModFoods.GRAPE), "tooltip.kaleidoscope_tavern.grape"));
    public static final Item ICE_GRAPE = registerItem(ModBlockItemIds.ICE_GRAPE_CROP, p -> new TooltipItem(p.food(ModFoods.GRAPE), "tooltip.kaleidoscope_tavern.ice_grape"));
    public static final Item GOLD_GRAPE = registerItem(ModBlockItemIds.GOLD_GRAPE_CROP, p -> new TooltipItem(p.food(ModFoods.GRAPE), "tooltip.kaleidoscope_tavern.gold_grape"));
    public static final Item GREEN_GRAPE = registerItem(ModItemIds.GREEN_GRAPE, p -> new TooltipItem(p.food(ModFoods.GRAPE), "tooltip.kaleidoscope_tavern.green_grape"));
    // 野生葡萄藤
    public static final Item GRAPEVINE = registerItem(ModBlockItemIds.WILD_GRAPEVINE, s -> new GrapevineItem(s.cookingFuel(ContextIntProviders.COOKING_TIME_BAMBOO)));
    // 黑板
    public static final Item CHALKBOARD = registerItem(ModBlockItemIds.CHALKBOARD, p -> new BlockItem(ModBlocks.CHALKBOARD, p.useBlockDescriptionPrefix()));
    public static final Item BELL_PENDANT_LAMP = registerItem(ModBlockItemIds.BELL_PENDANT_LAMP, p -> new BlockItem(ModBlocks.BELL_PENDANT_LAMP, p.useBlockDescriptionPrefix()));
    public static final Item YELLOW_PENDANT_LAMP = registerItem(ModBlockItemIds.YELLOW_PENDANT_LAMP, p -> new BlockItem(ModBlocks.YELLOW_PENDANT_LAMP, p.useBlockDescriptionPrefix()));
    public static final Item BLUE_PENDANT_LAMP = registerItem(ModBlockItemIds.BLUE_PENDANT_LAMP, p -> new BlockItem(ModBlocks.BLUE_PENDANT_LAMP, p.useBlockDescriptionPrefix()));
    public static final Item SAKURA_INCENSE = registerItem(ModBlockItemIds.SAKURA_INCENSE, p -> new BlockItem(ModBlocks.SAKURA_INCENSE, p.useBlockDescriptionPrefix()));
    public static final Item PINE_INCENSE = registerItem(ModBlockItemIds.PINE_INCENSE, p -> new BlockItem(ModBlocks.PINE_INCENSE, p.useBlockDescriptionPrefix()));
    public static final Item GINKGO_INCENSE = registerItem(ModBlockItemIds.GINKGO_INCENSE, p -> new BlockItem(ModBlocks.GINKGO_INCENSE, p.useBlockDescriptionPrefix()));
    public static final Item SPORE_INCENSE = registerItem(ModBlockItemIds.SPORE_INCENSE, p -> new BlockItem(ModBlocks.SPORE_INCENSE, p.useBlockDescriptionPrefix()));
    public static final Item CATNIP_INCENSE = registerItem(ModBlockItemIds.CATNIP_INCENSE, p -> new BlockItem(ModBlocks.CATNIP_INCENSE, p.useBlockDescriptionPrefix()));
    public static final Item SNOW_INCENSE = registerItem(ModBlockItemIds.SNOW_INCENSE, p -> new BlockItem(ModBlocks.SNOW_INCENSE, p.useBlockDescriptionPrefix()));
    public static final Item BUTTERFLY_INCENSE = registerItem(ModBlockItemIds.BUTTERFLY_INCENSE, p -> new BlockItem(ModBlocks.BUTTERFLY_INCENSE, p.useBlockDescriptionPrefix()));
    public static final Item FIREFLY_INCENSE = registerItem(ModBlockItemIds.FIREFLY_INCENSE, p -> new BlockItem(ModBlocks.FIREFLY_INCENSE, p.useBlockDescriptionPrefix()));
    // 燃烧瓶
    public static final Item MOLOTOV = registerItem(ModBlockItemIds.MOLOTOV, p -> new MolotovBlockItem(ModBlocks.MOLOTOV, p.useBlockDescriptionPrefix()));
    // 吧台
    public static final Item BAR_COUNTER = registerItem(ModBlockItemIds.BAR_COUNTER, p -> new BlockItem(ModBlocks.BAR_COUNTER, p.useBlockDescriptionPrefix()));
    // 人字梯
    public static final Item STEPLADDER = registerItem(ModBlockItemIds.STEPLADDER, p -> new BlockItem(ModBlocks.STEPLADDER, p.useBlockDescriptionPrefix()));
    // 藤架
    public static final Item TRELLIS = registerItem(ModBlockItemIds.TRELLIS, p -> new TooltipBlockItem(
            ModBlocks.TRELLIS,
            p.useBlockDescriptionPrefix(),
            "tooltip.kaleidoscope_tavern.trellis.1",
            "tooltip.kaleidoscope_tavern.trellis.2"
    ));
    // 果盆
    public static final Item PRESSING_TUB = registerItem(ModBlockItemIds.PRESSING_TUB, p -> new BlockItem(ModBlocks.PRESSING_TUB, p.useBlockDescriptionPrefix()));
    // 龙头
    public static final Item TAP = registerItem(ModBlockItemIds.TAP, p -> new BlockItem(ModBlocks.TAP, p.useBlockDescriptionPrefix()));
    // 酒桶
    public static final Item BARREL = registerItem(ModBlockItemIds.BARREL, p -> new BlockItem(ModBlocks.BARREL, p.useBlockDescriptionPrefix()));
    // 酒柜
    public static final Item BAR_CABINET = registerItem(ModBlockItemIds.BAR_CABINET, p -> new BlockItem(ModBlocks.BAR_CABINET, p.useBlockDescriptionPrefix()));
    public static final Item GLASS_BAR_CABINET = registerItem(ModBlockItemIds.GLASS_BAR_CABINET, p -> new BlockItem(ModBlocks.GLASS_BAR_CABINET, p.useBlockDescriptionPrefix()));
    public static final Item CELLAR_CABINET = registerItem(ModBlockItemIds.CELLAR_CABINET, p -> new BlockItem(ModBlocks.CELLAR_CABINET, p.useBlockDescriptionPrefix()));
    public static final Item TILTED_RACK = registerItem(ModBlockItemIds.TILTED_RACK, p -> new BlockItem(ModBlocks.TILTED_RACK, p.useBlockDescriptionPrefix()));
    public static final Item CIRCULAR_RACK = registerItem(ModBlockItemIds.CIRCULAR_RACK, p -> new BlockItem(ModBlocks.CIRCULAR_RACK, p.useBlockDescriptionPrefix()));
    public static final Item HOLDER = registerItem(ModBlockItemIds.HOLDER, p -> new BlockItem(ModBlocks.HOLDER, p.useBlockDescriptionPrefix()));
    public static final Item SHAKER = registerItem(ModBlockItemIds.SHAKER, ShakerItem::new);
    public static final Item GLASSWARE_HOLDER = registerItem(ModBlockItemIds.GLASSWARE_HOLDER, p -> new BlockItem(ModBlocks.GLASSWARE_HOLDER, p.useBlockDescriptionPrefix()));
    // 酒
    public static final Item WINE = registerItem(ModBlockItemIds.WINE, p -> new DrinkBlockItem(ModBlocks.WINE, p));
    public static final Item CHAMPAGNE = registerItem(ModBlockItemIds.CHAMPAGNE, p -> new DrinkBlockItem(ModBlocks.CHAMPAGNE, p));
    public static final Item VODKA = registerItem(ModBlockItemIds.VODKA, p -> new DrinkBlockItem(ModBlocks.VODKA, p));
    public static final Item BRANDY = registerItem(ModBlockItemIds.BRANDY, p -> new DrinkBlockItem(ModBlocks.BRANDY, p));
    public static final Item CARIGNAN = registerItem(ModBlockItemIds.CARIGNAN, p -> new DrinkBlockItem(ModBlocks.CARIGNAN, p));
    public static final Item SAKURA_WINE = registerItem(ModBlockItemIds.SAKURA_WINE, p -> new DrinkBlockItem(ModBlocks.SAKURA_WINE, p));
    public static final Item PLUM_WINE = registerItem(ModBlockItemIds.PLUM_WINE, p -> new DrinkBlockItem(ModBlocks.PLUM_WINE, p));
    public static final Item WHISKEY = registerItem(ModBlockItemIds.WHISKEY, p -> new DrinkBlockItem(ModBlocks.WHISKEY, p));
    public static final Item ICE_WINE = registerItem(ModBlockItemIds.ICE_WINE, p -> new DrinkBlockItem(ModBlocks.ICE_WINE, p));
    public static final Item VINEGAR = registerItem(ModBlockItemIds.VINEGAR, p -> new DrinkBlockItem(ModBlocks.VINEGAR, p));
    public static final Item POLARIS_SWEET_WHITE = registerItem(ModBlockItemIds.POLARIS_SWEET_WHITE, p -> new DrinkBlockItem(ModBlocks.POLARIS_SWEET_WHITE, p));
    public static final Item HONEY_WINE = registerItem(ModBlockItemIds.HONEY_WINE, p -> new DrinkBlockItem(ModBlocks.HONEY_WINE, p));
    public static final Item RED_QUEEN = registerItem(ModBlockItemIds.RED_QUEEN, p -> new DrinkBlockItem(ModBlocks.RED_QUEEN, p));
    public static final Item MINERS_STAR = registerItem(ModBlockItemIds.MINERS_STAR, p -> new DrinkBlockItem(ModBlocks.MINERS_STAR, p));
    public static final Item RUM = registerItem(ModBlockItemIds.RUM, p -> new DrinkBlockItem(ModBlocks.RUM, p));
    public static final Item RIESLING_DRY_WHITE = registerItem(ModBlockItemIds.RIESLING_DRY_WHITE, p -> new DrinkBlockItem(ModBlocks.RIESLING_DRY_WHITE, p));
    public static final Item SUNSET_GLOW = registerItem(ModBlockItemIds.SUNSET_GLOW, p -> new DrinkBlockItem(ModBlocks.SUNSET_GLOW, p));
    public static final Item MADAME_SHEXIANG = registerItem(ModBlockItemIds.MADAME_SHEXIANG, p -> new DrinkBlockItem(ModBlocks.MADAME_SHEXIANG, p));
    public static final Item SWEET_BERRY_WINE = registerItem(ModBlockItemIds.SWEET_BERRY_WINE, p -> new DrinkBlockItem(ModBlocks.SWEET_BERRY_WINE, p));
    public static final Item SHERRY = registerItem(ModBlockItemIds.SHERRY, p -> new DrinkBlockItem(ModBlocks.SHERRY, p));
    public static final Item MOTHER_SNOW = registerItem(ModBlockItemIds.MOTHER_SNOW, p -> new DrinkBlockItem(ModBlocks.MOTHER_SNOW, p));
    public static final Item LUMINOUS_BRIDE = registerItem(ModBlockItemIds.LUMINOUS_BRIDE, p -> new DrinkBlockItem(ModBlocks.LUMINOUS_BRIDE, p));
    public static final Item GLOWFLOWER_BREW = registerItem(ModBlockItemIds.GLOWFLOWER_BREW, p -> new DrinkBlockItem(ModBlocks.GLOWFLOWER_BREW, p));
    public static final Item SAUVIGNON_BLANC_DRY_WHITE = registerItem(ModBlockItemIds.SAUVIGNON_BLANC_DRY_WHITE, p -> new DrinkBlockItem(ModBlocks.SAUVIGNON_BLANC_DRY_WHITE, p));
    public static final Item WATERMELON_JUICE = registerItem(ModBlockItemIds.WATERMELON_JUICE, p -> new DrinkBlockItem(ModBlocks.WATERMELON_JUICE, p));

    // 果汁桶
    public static final Item GRAPE_BUCKET = registerItem(ModItemIds.GRAPE_BUCKET, p -> new JuiceBucketItem(ModFluids.GRAPE_JUICE, p));

    public static final Item ICE_GRAPE_BUCKET = registerItem(ModItemIds.ICE_GRAPE_BUCKET, p -> new JuiceBucketItem(ModFluids.ICE_GRAPE_JUICE, p));

    public static final Item GOLD_GRAPE_BUCKET = registerItem(ModItemIds.GOLD_GRAPE_BUCKET, p -> new JuiceBucketItem(ModFluids.GOLD_GRAPE_JUICE, p));

    public static final Item GREEN_GRAPE_BUCKET = registerItem(ModItemIds.GREEN_GRAPE_BUCKET, p -> new JuiceBucketItem(ModFluids.GREEN_GRAPE_JUICE, p));
    public static final Item SWEET_BERRIES_BUCKET = registerItem(ModItemIds.SWEET_BERRIES_BUCKET, p -> new JuiceBucketItem(ModFluids.SWEET_BERRIES_JUICE, p));
    public static final Item GLOW_BERRIES_BUCKET = registerItem(ModItemIds.GLOW_BERRIES_BUCKET, p -> new JuiceBucketItem(ModFluids.GLOW_BERRIES_JUICE, p));
    public static void registerItems() {

    }

    public static Item registerItemViaBlock(
            BlockItemId blockItemId,
            Block block,
            BiFunction<Block, Item.Properties, Item> biFunction,
            Item.Properties properties
    ) {
        return registerItem(
                blockItemId.item(), properties2 -> biFunction.apply(block, properties2), properties.useBlockDescriptionPrefix()
        );
    }

    public static Item registerItemViaBlock(
            BlockItemId blockItemId,
            Block block,
            BiFunction<Block, Item.Properties, Item> biFunction
    ) {
        return registerItemViaBlock(blockItemId, block, biFunction, new Item.Properties());
    }

    public static Item registerItemViaBlock(BlockItemId blockItemId, Block block) {
        return registerItemViaBlock(blockItemId, block, BlockItem::new);
    }

    public static Item registerItem(BlockItemId blockItemId, Function<Item.Properties, Item> function, Item.Properties properties) {
        return registerItem(blockItemId.item(), function, properties);
    }

    public static Item registerItem(BlockItemId blockItemId, Function<Item.Properties, Item> function) {
        return registerItem(blockItemId.item(), function, new Item.Properties());
    }

    public static Item registerItem(ResourceKey<Item> resourceKey, Function<Item.Properties, Item> function) {
        return registerItem(resourceKey, function, new Item.Properties());
    }

    public static Item registerItem(ResourceKey<Item> resourceKey, Function<Item.Properties, Item> function, Item.Properties properties) {
        Item item = function.apply(properties.setId(resourceKey));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }

        return Registry.register(BuiltInRegistries.ITEM, resourceKey, item);
    }
}
