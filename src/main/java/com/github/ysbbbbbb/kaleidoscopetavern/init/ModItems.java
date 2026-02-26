package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.item.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;

import static com.github.ysbbbbbb.kaleidoscopetavern.init.ModFluids.*;

public class ModItems {
    // 葡萄
    public static final Item GRAPE = new Item(new Item.Properties().food(ModFoods.GRAPE));
    // 野生葡萄藤
    public static final Item GRAPEVINE = new ItemNameBlockItem(ModBlocks.WILD_GRAPEVINE, new Item.Properties());
    // 果汁桶
    public static final Item GRAPE_BUCKET = new JuiceBucketItem(GRAPE_JUICE);
    public static final Item SWEET_BERRIES_BUCKET = new JuiceBucketItem(SWEET_BERRIES_JUICE);
    public static final Item GLOW_BERRIES_BUCKET = new JuiceBucketItem(GLOW_BERRIES_JUICE);
    // 空瓶
    public static final Item EMPTY_BOTTLE = new BottleBlockItem(ModBlocks.EMPTY_BOTTLE);

    // 沙发
    public static final Item WHITE_SOFA = new SofaBlockItem(ModBlocks.WHITE_SOFA);
    public static final Item LIGHT_GRAY_SOFA = new SofaBlockItem(ModBlocks.LIGHT_GRAY_SOFA);
    public static final Item GRAY_SOFA = new SofaBlockItem(ModBlocks.GRAY_SOFA);
    public static final Item BLACK_SOFA = new SofaBlockItem(ModBlocks.BLACK_SOFA);
    public static final Item BROWN_SOFA = new SofaBlockItem(ModBlocks.BROWN_SOFA);
    public static final Item RED_SOFA = new SofaBlockItem(ModBlocks.RED_SOFA);
    public static final Item ORANGE_SOFA = new SofaBlockItem(ModBlocks.ORANGE_SOFA);
    public static final Item YELLOW_SOFA = new SofaBlockItem(ModBlocks.YELLOW_SOFA);
    public static final Item LIME_SOFA = new SofaBlockItem(ModBlocks.LIME_SOFA);
    public static final Item GREEN_SOFA = new SofaBlockItem(ModBlocks.GREEN_SOFA);
    public static final Item CYAN_SOFA = new SofaBlockItem(ModBlocks.CYAN_SOFA);
    public static final Item LIGHT_BLUE_SOFA = new SofaBlockItem(ModBlocks.LIGHT_BLUE_SOFA);
    public static final Item BLUE_SOFA = new SofaBlockItem(ModBlocks.BLUE_SOFA);
    public static final Item PURPLE_SOFA = new SofaBlockItem(ModBlocks.PURPLE_SOFA);
    public static final Item MAGENTA_SOFA = new SofaBlockItem(ModBlocks.MAGENTA_SOFA);
    public static final Item PINK_SOFA = new SofaBlockItem(ModBlocks.PINK_SOFA);

    // 高脚凳
    public static final Item WHITE_BAR_STOOL = new BarStoolBlockItem(ModBlocks.WHITE_BAR_STOOL);
    public static final Item LIGHT_GRAY_BAR_STOOL = new BarStoolBlockItem(ModBlocks.LIGHT_GRAY_BAR_STOOL);
    public static final Item GRAY_BAR_STOOL = new BarStoolBlockItem(ModBlocks.GRAY_BAR_STOOL);
    public static final Item BLACK_BAR_STOOL = new BarStoolBlockItem(ModBlocks.BLACK_BAR_STOOL);
    public static final Item BROWN_BAR_STOOL = new BarStoolBlockItem(ModBlocks.BROWN_BAR_STOOL);
    public static final Item RED_BAR_STOOL = new BarStoolBlockItem(ModBlocks.RED_BAR_STOOL);
    public static final Item ORANGE_BAR_STOOL = new BarStoolBlockItem(ModBlocks.ORANGE_BAR_STOOL);
    public static final Item YELLOW_BAR_STOOL = new BarStoolBlockItem(ModBlocks.YELLOW_BAR_STOOL);
    public static final Item LIME_BAR_STOOL = new BarStoolBlockItem(ModBlocks.LIME_BAR_STOOL);
    public static final Item GREEN_BAR_STOOL = new BarStoolBlockItem(ModBlocks.GREEN_BAR_STOOL);
    public static final Item CYAN_BAR_STOOL = new BarStoolBlockItem(ModBlocks.CYAN_BAR_STOOL);
    public static final Item LIGHT_BLUE_BAR_STOOL = new BarStoolBlockItem(ModBlocks.LIGHT_BLUE_BAR_STOOL);
    public static final Item BLUE_BAR_STOOL = new BarStoolBlockItem(ModBlocks.BLUE_BAR_STOOL);
    public static final Item PURPLE_BAR_STOOL = new BarStoolBlockItem(ModBlocks.PURPLE_BAR_STOOL);
    public static final Item MAGENTA_BAR_STOOL = new BarStoolBlockItem(ModBlocks.MAGENTA_BAR_STOOL);
    public static final Item PINK_BAR_STOOL = new BarStoolBlockItem(ModBlocks.PINK_BAR_STOOL);

    // 黑板
    public static final Item CHALKBOARD = new BlockItem(ModBlocks.CHALKBOARD, new Item.Properties());

    // 展板
    public static final Item BASE_SANDWICH_BOARD = new SandwichBoardBlockItem(ModBlocks.BASE_SANDWICH_BOARD);
    public static final Item GRASS_SANDWICH_BOARD = new SandwichBoardBlockItem(ModBlocks.GRASS_SANDWICH_BOARD);
    public static final Item ALLIUM_SANDWICH_BOARD = new SandwichBoardBlockItem(ModBlocks.ALLIUM_SANDWICH_BOARD);
    public static final Item AZURE_BLUET_SANDWICH_BOARD = new SandwichBoardBlockItem(ModBlocks.AZURE_BLUET_SANDWICH_BOARD);
    public static final Item CORNFLOWER_SANDWICH_BOARD = new SandwichBoardBlockItem(ModBlocks.CORNFLOWER_SANDWICH_BOARD);
    public static final Item ORCHID_SANDWICH_BOARD = new SandwichBoardBlockItem(ModBlocks.ORCHID_SANDWICH_BOARD);
    public static final Item PEONY_SANDWICH_BOARD = new SandwichBoardBlockItem(ModBlocks.PEONY_SANDWICH_BOARD);
    public static final Item PINK_PETALS_SANDWICH_BOARD = new SandwichBoardBlockItem(ModBlocks.PINK_PETALS_SANDWICH_BOARD);
    public static final Item PITCHER_PLANT_SANDWICH_BOARD = new SandwichBoardBlockItem(ModBlocks.PITCHER_PLANT_SANDWICH_BOARD);
    public static final Item POPPY_SANDWICH_BOARD = new SandwichBoardBlockItem(ModBlocks.POPPY_SANDWICH_BOARD);
    public static final Item SUNFLOWER_SANDWICH_BOARD = new SandwichBoardBlockItem(ModBlocks.SUNFLOWER_SANDWICH_BOARD);
    public static final Item TORCHFLOWER_SANDWICH_BOARD = new SandwichBoardBlockItem(ModBlocks.TORCHFLOWER_SANDWICH_BOARD);
    public static final Item TULIP_SANDWICH_BOARD = new SandwichBoardBlockItem(ModBlocks.TULIP_SANDWICH_BOARD);
    public static final Item WITHER_ROSE_SANDWICH_BOARD = new SandwichBoardBlockItem(ModBlocks.WITHER_ROSE_SANDWICH_BOARD);

    // 彩灯
    public static final Item STRING_LIGHTS_COLORLESS = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_COLORLESS);
    public static final Item STRING_LIGHTS_WHITE = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_WHITE);
    public static final Item STRING_LIGHTS_LIGHT_GRAY = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIGHT_GRAY);
    public static final Item STRING_LIGHTS_GRAY = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_GRAY);
    public static final Item STRING_LIGHTS_BLACK = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BLACK);
    public static final Item STRING_LIGHTS_BROWN = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BROWN);
    public static final Item STRING_LIGHTS_RED = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_RED);
    public static final Item STRING_LIGHTS_ORANGE = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_ORANGE);
    public static final Item STRING_LIGHTS_YELLOW = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_YELLOW);
    public static final Item STRING_LIGHTS_LIME = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIME);
    public static final Item STRING_LIGHTS_GREEN = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_GREEN);
    public static final Item STRING_LIGHTS_CYAN = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_CYAN);
    public static final Item STRING_LIGHTS_LIGHT_BLUE = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_LIGHT_BLUE);
    public static final Item STRING_LIGHTS_BLUE = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_BLUE);
    public static final Item STRING_LIGHTS_PURPLE = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_PURPLE);
    public static final Item STRING_LIGHTS_MAGENTA = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_MAGENTA);
    public static final Item STRING_LIGHTS_PINK = new StringLightsBlockItem(ModBlocks.STRING_LIGHTS_PINK);

    // 挂画
    public static final Item YSBB_PAINTING = new PaintingBlockItem(ModBlocks.YSBB_PAINTING);
    public static final Item TARTARIC_ACID_PAINTING = new PaintingBlockItem(ModBlocks.TARTARIC_ACID_PAINTING);
    public static final Item CR019_PAINTING = new PaintingBlockItem(ModBlocks.CR019_PAINTING);
    public static final Item UNKNOWN_PAINTING = new PaintingBlockItem(ModBlocks.UNKNOWN_PAINTING);
    public static final Item MASTER_MARISA_PAINTING = new PaintingBlockItem(ModBlocks.MASTER_MARISA_PAINTING);
    public static final Item SON_OF_MAN_PAINTING = new PaintingBlockItem(ModBlocks.SON_OF_MAN_PAINTING);
    public static final Item DAVID_PAINTING = new PaintingBlockItem(ModBlocks.DAVID_PAINTING);
    public static final Item GIRL_WITH_PEARL_EARRING_PAINTING = new PaintingBlockItem(ModBlocks.GIRL_WITH_PEARL_EARRING_PAINTING);
    public static final Item STARRY_NIGHT_PAINTING = new PaintingBlockItem(ModBlocks.STARRY_NIGHT_PAINTING);
    public static final Item VAN_GOGH_SELF_PORTRAIT_PAINTING = new PaintingBlockItem(ModBlocks.VAN_GOGH_SELF_PORTRAIT_PAINTING);
    public static final Item FATHER_PAINTING = new PaintingBlockItem(ModBlocks.FATHER_PAINTING);
    public static final Item GREAT_WAVE_PAINTING = new PaintingBlockItem(ModBlocks.GREAT_WAVE_PAINTING);
    public static final Item MONA_LISA_PAINTING = new PaintingBlockItem(ModBlocks.MONA_LISA_PAINTING);
    public static final Item MONDRIAN_PAINTING = new PaintingBlockItem(ModBlocks.MONDRIAN_PAINTING);

    // 吧台
    public static final Item BAR_COUNTER = new BlockItem(ModBlocks.BAR_COUNTER, new Item.Properties());
    // 人字梯
    public static final Item STEPLADDER = new BlockItem(ModBlocks.STEPLADDER, new Item.Properties());
    // 藤架
    public static final Item TRELLIS = new BlockItem(ModBlocks.TRELLIS, new Item.Properties());
    // 果盆
    public static final Item PRESSING_TUB = new BlockItem(ModBlocks.PRESSING_TUB, new Item.Properties());
    // 龙头
    public static final Item TAP = new BlockItem(ModBlocks.TAP, new Item.Properties());
    // 酒桶
    public static final Item BARREL = new BlockItem(ModBlocks.BARREL, new Item.Properties());
    // 燃烧瓶
    public static final Item MOLOTOV = new MolotovBlockItem(ModBlocks.MOLOTOV);
    // 酒
    public static final Item WINE = new DrinkBlockItem(ModBlocks.WINE);
    public static final Item CHAMPAGNE = new DrinkBlockItem(ModBlocks.CHAMPAGNE);
    public static final Item VODKA = new DrinkBlockItem(ModBlocks.VODKA);
    public static final Item BRANDY = new DrinkBlockItem(ModBlocks.BRANDY);
    public static final Item CARIGNAN = new DrinkBlockItem(ModBlocks.CARIGNAN);
    public static final Item SAKURA_WINE = new DrinkBlockItem(ModBlocks.SAKURA_WINE);
    public static final Item PLUM_WINE = new DrinkBlockItem(ModBlocks.PLUM_WINE);
    public static final Item WHISKEY = new DrinkBlockItem(ModBlocks.WHISKEY);
    public static final Item ICE_WINE = new DrinkBlockItem(ModBlocks.ICE_WINE);
    public static final Item VINEGAR = new DrinkBlockItem(ModBlocks.VINEGAR);

    public static void registerItems() {
        // 已存在的物品（确保全部注册）
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "grape"), GRAPE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "grapevine"), GRAPEVINE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "grape_bucket"), GRAPE_BUCKET);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "sweet_berries_bucket"), SWEET_BERRIES_BUCKET);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "glow_berries_bucket"), GLOW_BERRIES_BUCKET);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "empty_bottle"), EMPTY_BOTTLE);

        // 沙发
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "white_sofa"), WHITE_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "light_gray_sofa"), LIGHT_GRAY_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "gray_sofa"), GRAY_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "black_sofa"), BLACK_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "brown_sofa"), BROWN_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "red_sofa"), RED_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "orange_sofa"), ORANGE_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "yellow_sofa"), YELLOW_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "lime_sofa"), LIME_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "green_sofa"), GREEN_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "cyan_sofa"), CYAN_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "light_blue_sofa"), LIGHT_BLUE_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "blue_sofa"), BLUE_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "purple_sofa"), PURPLE_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "magenta_sofa"), MAGENTA_SOFA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pink_sofa"), PINK_SOFA);

        // 高脚凳
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "white_bar_stool"), WHITE_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "light_gray_bar_stool"), LIGHT_GRAY_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "gray_bar_stool"), GRAY_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "black_bar_stool"), BLACK_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "brown_bar_stool"), BROWN_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "red_bar_stool"), RED_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "orange_bar_stool"), ORANGE_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "yellow_bar_stool"), YELLOW_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "lime_bar_stool"), LIME_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "green_bar_stool"), GREEN_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "cyan_bar_stool"), CYAN_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "light_blue_bar_stool"), LIGHT_BLUE_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "blue_bar_stool"), BLUE_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "purple_bar_stool"), PURPLE_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "magenta_bar_stool"), MAGENTA_BAR_STOOL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pink_bar_stool"), PINK_BAR_STOOL);

        // 黑板
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "chalkboard"), CHALKBOARD);

        // 展板
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "base_sandwich_board"), BASE_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "grass_sandwich_board"), GRASS_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "allium_sandwich_board"), ALLIUM_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "azure_bluet_sandwich_board"), AZURE_BLUET_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "cornflower_sandwich_board"), CORNFLOWER_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "orchid_sandwich_board"), ORCHID_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "peony_sandwich_board"), PEONY_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pink_petals_sandwich_board"), PINK_PETALS_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pitcher_plant_sandwich_board"), PITCHER_PLANT_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "poppy_sandwich_board"), POPPY_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "sunflower_sandwich_board"), SUNFLOWER_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "torchflower_sandwich_board"), TORCHFLOWER_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "tulip_sandwich_board"), TULIP_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "wither_rose_sandwich_board"), WITHER_ROSE_SANDWICH_BOARD);

        // 彩灯
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_colorless"), STRING_LIGHTS_COLORLESS);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_white"), STRING_LIGHTS_WHITE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_light_gray"), STRING_LIGHTS_LIGHT_GRAY);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_gray"), STRING_LIGHTS_GRAY);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_black"), STRING_LIGHTS_BLACK);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_brown"), STRING_LIGHTS_BROWN);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_red"), STRING_LIGHTS_RED);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_orange"), STRING_LIGHTS_ORANGE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_yellow"), STRING_LIGHTS_YELLOW);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_lime"), STRING_LIGHTS_LIME);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_green"), STRING_LIGHTS_GREEN);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_cyan"), STRING_LIGHTS_CYAN);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_light_blue"), STRING_LIGHTS_LIGHT_BLUE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_blue"), STRING_LIGHTS_BLUE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_purple"), STRING_LIGHTS_PURPLE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_magenta"), STRING_LIGHTS_MAGENTA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_pink"), STRING_LIGHTS_PINK);

        // 挂画
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "ysbb_painting"), YSBB_PAINTING);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "tartaric_acid_painting"), TARTARIC_ACID_PAINTING);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "cr019_painting"), CR019_PAINTING);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "unknown_painting"), UNKNOWN_PAINTING);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "master_marisa_painting"), MASTER_MARISA_PAINTING);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "son_of_man_painting"), SON_OF_MAN_PAINTING);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "david_painting"), DAVID_PAINTING);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "girl_with_pearl_earring_painting"), GIRL_WITH_PEARL_EARRING_PAINTING);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "starry_night_painting"), STARRY_NIGHT_PAINTING);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "van_gogh_self_portrait_painting"), VAN_GOGH_SELF_PORTRAIT_PAINTING);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "father_painting"), FATHER_PAINTING);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "great_wave_painting"), GREAT_WAVE_PAINTING);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "mona_lisa_painting"), MONA_LISA_PAINTING);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "mondrian_painting"), MONDRIAN_PAINTING);

        // 其他方块物品
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "bar_counter"), BAR_COUNTER);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "stepladder"), STEPLADDER);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "trellis"), TRELLIS);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pressing_tub"), PRESSING_TUB);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "tap"), TAP);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "barrel"), BARREL);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "molotov"), MOLOTOV);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "wine"), WINE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "champagne"), CHAMPAGNE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "vodka"), VODKA);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "brandy"), BRANDY);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "carignan"), CARIGNAN);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "sakura_wine"), SAKURA_WINE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "plum_wine"), PLUM_WINE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "whiskey"), WHISKEY);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "ice_wine"), ICE_WINE);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "vinegar"), VINEGAR);
    }
}
