package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.item.*;
import com.github.ysbbbbbb.kaleidoscopetavern.util.PortHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ModItems {
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
    public static final Item GRASS_SANDWICH_BOARD = registerItem("grass_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.GRASS_SANDWICH_BOARD, p));
    public static final Item ALLIUM_SANDWICH_BOARD = registerItem("allium_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.ALLIUM_SANDWICH_BOARD, p));
    public static final Item AZURE_BLUET_SANDWICH_BOARD = registerItem("azure_bluet_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.AZURE_BLUET_SANDWICH_BOARD, p));
    public static final Item CORNFLOWER_SANDWICH_BOARD = registerItem("cornflower_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.CORNFLOWER_SANDWICH_BOARD, p));
    public static final Item ORCHID_SANDWICH_BOARD = registerItem("orchid_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.ORCHID_SANDWICH_BOARD, p));
    public static final Item PEONY_SANDWICH_BOARD = registerItem("peony_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.PEONY_SANDWICH_BOARD, p));
    public static final Item PINK_PETALS_SANDWICH_BOARD = registerItem("pink_petals_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.PINK_PETALS_SANDWICH_BOARD, p));
    public static final Item PITCHER_PLANT_SANDWICH_BOARD = registerItem("pitcher_plant_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.PITCHER_PLANT_SANDWICH_BOARD, p));
    public static final Item POPPY_SANDWICH_BOARD = registerItem("poppy_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.POPPY_SANDWICH_BOARD, p));
    public static final Item SUNFLOWER_SANDWICH_BOARD = registerItem("sunflower_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.SUNFLOWER_SANDWICH_BOARD, p));
    public static final Item TORCHFLOWER_SANDWICH_BOARD = registerItem("torchflower_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.TORCHFLOWER_SANDWICH_BOARD, p));
    public static final Item TULIP_SANDWICH_BOARD = registerItem("tulip_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.TULIP_SANDWICH_BOARD, p));
    public static final Item WITHER_ROSE_SANDWICH_BOARD = registerItem("wither_rose_sandwich_board", p -> new SandwichBoardBlockItem(ModBlocks.WITHER_ROSE_SANDWICH_BOARD, p));

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
    // 空瓶
    public static final Item EMPTY_BOTTLE = registerItemViaBlock(ModBlocks.EMPTY_BOTTLE);
    // 葡萄
    public static final Item GRAPE = registerItem("grape", p -> new Item(p.food(ModFoods.GRAPE)));
    // 野生葡萄藤
    public static final Item GRAPEVINE = registerItem("grapevine", createBlockItemWithCustomItemName(ModBlocks.WILD_GRAPEVINE));
    // 黑板
    public static final Item CHALKBOARD = registerItem("chalkboard", p -> new BlockItem(ModBlocks.CHALKBOARD, p.useBlockDescriptionPrefix()));
    // 燃烧瓶
    public static final Item MOLOTOV = registerItem("molotov", p -> new MolotovBlockItem(ModBlocks.MOLOTOV, p.useBlockDescriptionPrefix()));
    // 吧台
    public static final Item BAR_COUNTER = registerItem("bar_counter", p -> new BlockItem(ModBlocks.BAR_COUNTER, p.useBlockDescriptionPrefix()));
    // 人字梯
    public static final Item STEPLADDER = registerItem("stepladder", p -> new BlockItem(ModBlocks.STEPLADDER, p.useBlockDescriptionPrefix()));
    // 藤架
    public static final Item TRELLIS = registerItem("trellis", p -> new BlockItem(ModBlocks.TRELLIS, p.useBlockDescriptionPrefix()));
    // 果盆
    public static final Item PRESSING_TUB = registerItem("pressing_tub", p -> new BlockItem(ModBlocks.PRESSING_TUB, p.useBlockDescriptionPrefix()));
    // 龙头
    public static final Item TAP = registerItem("tap", p -> new BlockItem(ModBlocks.TAP, p.useBlockDescriptionPrefix()));
    // 酒桶
    public static final Item BARREL = registerItem("barrel", p -> new BottleBlockItem(ModBlocks.BARREL, p.useBlockDescriptionPrefix()));
    // 酒柜
    public static final Item BAR_CABINET =registerItem("bar_cabinet", p -> new BlockItem(ModBlocks.BAR_CABINET, p.useBlockDescriptionPrefix()));
    public static final Item GLASS_BAR_CABINET = registerItem("glass_bar_cabinet", p -> new BlockItem(ModBlocks.GLASS_BAR_CABINET, p.useBlockDescriptionPrefix()));
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

    // 果汁桶
    public static final Item GRAPE_BUCKET = registerItem("grape_bucket", p -> new JuiceBucketItem(ModFluids.GRAPE_JUICE, p));
    public static final Item SWEET_BERRIES_BUCKET = registerItem("sweet_berries_bucket", p -> new JuiceBucketItem(ModFluids.SWEET_BERRIES_JUICE, p));
    public static final Item GLOW_BERRIES_BUCKET = registerItem("glow_berries_bucket", p -> new JuiceBucketItem(ModFluids.GLOW_BERRIES_JUICE, p));
    public static void init() {

    }

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
