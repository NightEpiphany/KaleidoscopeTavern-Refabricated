package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;

public final class ModCreativeTabs {
    private static final Identifier MAIN_ICON_ID = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "wine");

    private static final Identifier DECO_ICON_ID = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "stepladder");

    private static final ResourceKey<CreativeModeTab> TAVERN_MAIN_TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "tavern_main"));

    private static final ResourceKey<CreativeModeTab> TAVERN_DECO_TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "tavern_deco"));


    public static void registerTabs() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAVERN_MAIN_TAB, FabricItemGroup.builder()
                .title(Component.translatable("item_group.kaleidoscope_tavern.tavern_main.name"))
                .icon(() -> BuiltInRegistries.ITEM.getValue(MAIN_ICON_ID).getDefaultInstance())
                .displayItems((par, output) -> {
                    output.accept(ModItems.GRAPEVINE);
                    output.accept(ModItems.GRAPE);
                    output.accept(ModItems.ICE_GRAPE);
                    output.accept(ModItems.GOLD_GRAPE);
                    output.accept(ModItems.GREEN_GRAPE);
                    output.accept(ModItems.TRELLIS);

                    output.accept(ModItems.PRESSING_TUB);
                    output.accept(ModItems.BARREL);
                    output.accept(ModItems.TAP);
                    output.accept(ModItems.SHAKER);

                    output.accept(ModItems.GRAPE_BUCKET);
                    output.accept(ModItems.ICE_GRAPE_BUCKET);
                    output.accept(ModItems.GOLD_GRAPE_BUCKET);
                    output.accept(ModItems.GREEN_GRAPE_BUCKET);
                    output.accept(ModItems.SWEET_BERRIES_BUCKET);
                    output.accept(ModItems.GLOW_BERRIES_BUCKET);

                    output.accept(ModItems.EMPTY_BOTTLE);
                    output.accept(ModItems.EMPTY_GLASSWARE);
                    output.accept(ModItems.MOLOTOV);
                    output.accept(ModItems.WATERMELON_JUICE);

                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.VINEGAR));

                    // 葡萄桶
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.WINE));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.SAKURA_WINE));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.CHAMPAGNE));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.BRANDY));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.CARIGNAN));

                    // 冰葡萄桶
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.ICE_WINE));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.POLARIS_SWEET_WHITE));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.SHERRY));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.MOTHER_SNOW));

                    // 黄金葡萄桶
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.MINERS_STAR));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.HONEY_WINE));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.MADAME_SHEXIANG));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.SUNSET_GLOW));

                    // 青提桶
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.SAUVIGNON_BLANC_DRY_WHITE));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.RIESLING_DRY_WHITE));

                    // 发光浆果桶
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.LUMINOUS_BRIDE));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.GLOWFLOWER_BREW));

                    // 甜浆果桶
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.PLUM_WINE));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.SWEET_BERRY_WINE));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.RED_QUEEN));

                    // 水桶
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.VODKA));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.WHISKEY));
                    output.accept(BottleBlockItem.getMaxLevelDrink(ModItems.RUM));

                    // 鸡尾酒
                    output.accept(ModItems.SIGNATURE_COCKTAIL);
                    output.accept(ModItems.MYSTERY_COCKTAIL);
                    output.accept(ModItems.WHITE_LADY);
                    output.accept(ModItems.EMERALD);
                    output.accept(ModItems.BRASS_HEART);
                    output.accept(ModItems.GODFATHER);
                    output.accept(ModItems.GRASSHOPPER);
                    output.accept(ModItems.SCREWDRIVER);
                    output.accept(ModItems.MOJITO);
                    output.accept(ModItems.ALLIUM_GARDEN);
                    output.accept(ModItems.DEPTH_CHARGE);
                    output.accept(ModItems.NETHER_SPECIAL);
                    output.accept(ModItems.BLOODY_MARY);
                    output.accept(ModItems.SCULK_SPECIAL);
                }).build());

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAVERN_DECO_TAB, FabricItemGroup.builder()
                .title(Component.translatable("item_group.kaleidoscope_tavern.tavern_deco.name"))
                .icon(() -> BuiltInRegistries.ITEM.getValue(DECO_ICON_ID).getDefaultInstance())
                .displayItems((par, output) -> {
                    output.accept(ModItems.BAR_CABINET);
                    output.accept(ModItems.GLASS_BAR_CABINET);
                    output.accept(ModItems.CELLAR_CABINET);
                    output.accept(ModItems.BAR_COUNTER);
                    output.accept(ModItems.TABLE);
                    output.accept(ModItems.TILTED_RACK);
                    output.accept(ModItems.CIRCULAR_RACK);
                    output.accept(ModItems.HOLDER);
                    output.accept(ModItems.GLASSWARE_HOLDER);
                    output.accept(ModItems.STEPLADDER);

                    output.accept(ModItems.STRING_LIGHTS_COLORLESS);
                    output.accept(ModItems.STRING_LIGHTS_WHITE);
                    output.accept(ModItems.STRING_LIGHTS_LIGHT_GRAY);
                    output.accept(ModItems.STRING_LIGHTS_GRAY);
                    output.accept(ModItems.STRING_LIGHTS_BLACK);
                    output.accept(ModItems.STRING_LIGHTS_BROWN);
                    output.accept(ModItems.STRING_LIGHTS_RED);
                    output.accept(ModItems.STRING_LIGHTS_ORANGE);
                    output.accept(ModItems.STRING_LIGHTS_YELLOW);
                    output.accept(ModItems.STRING_LIGHTS_LIME);
                    output.accept(ModItems.STRING_LIGHTS_GREEN);
                    output.accept(ModItems.STRING_LIGHTS_CYAN);
                    output.accept(ModItems.STRING_LIGHTS_LIGHT_BLUE);
                    output.accept(ModItems.STRING_LIGHTS_BLUE);
                    output.accept(ModItems.STRING_LIGHTS_PURPLE);
                    output.accept(ModItems.STRING_LIGHTS_MAGENTA);
                    output.accept(ModItems.STRING_LIGHTS_PINK);

                    output.accept(ModItems.BELL_PENDANT_LAMP);
                    output.accept(ModItems.YELLOW_PENDANT_LAMP);
                    output.accept(ModItems.BLUE_PENDANT_LAMP);

                    output.accept(ModItems.WHITE_SOFA);
                    output.accept(ModItems.LIGHT_GRAY_SOFA);
                    output.accept(ModItems.GRAY_SOFA);
                    output.accept(ModItems.BLACK_SOFA);
                    output.accept(ModItems.BROWN_SOFA);
                    output.accept(ModItems.RED_SOFA);
                    output.accept(ModItems.ORANGE_SOFA);
                    output.accept(ModItems.YELLOW_SOFA);
                    output.accept(ModItems.LIME_SOFA);
                    output.accept(ModItems.GREEN_SOFA);
                    output.accept(ModItems.CYAN_SOFA);
                    output.accept(ModItems.LIGHT_BLUE_SOFA);
                    output.accept(ModItems.BLUE_SOFA);
                    output.accept(ModItems.PURPLE_SOFA);
                    output.accept(ModItems.MAGENTA_SOFA);
                    output.accept(ModItems.PINK_SOFA);

                    output.accept(ModItems.WHITE_BAR_STOOL);
                    output.accept(ModItems.LIGHT_GRAY_BAR_STOOL);
                    output.accept(ModItems.GRAY_BAR_STOOL);
                    output.accept(ModItems.BLACK_BAR_STOOL);
                    output.accept(ModItems.BROWN_BAR_STOOL);
                    output.accept(ModItems.RED_BAR_STOOL);
                    output.accept(ModItems.ORANGE_BAR_STOOL);
                    output.accept(ModItems.YELLOW_BAR_STOOL);
                    output.accept(ModItems.LIME_BAR_STOOL);
                    output.accept(ModItems.GREEN_BAR_STOOL);
                    output.accept(ModItems.CYAN_BAR_STOOL);
                    output.accept(ModItems.LIGHT_BLUE_BAR_STOOL);
                    output.accept(ModItems.BLUE_BAR_STOOL);
                    output.accept(ModItems.PURPLE_BAR_STOOL);
                    output.accept(ModItems.MAGENTA_BAR_STOOL);
                    output.accept(ModItems.PINK_BAR_STOOL);

                    output.accept(ModItems.BASE_SANDWICH_BOARD);
                    output.accept(ModItems.GRASS_SANDWICH_BOARD);
                    output.accept(ModItems.PINK_PETALS_SANDWICH_BOARD);
                    output.accept(ModItems.SUNFLOWER_SANDWICH_BOARD);
                    output.accept(ModItems.POPPY_SANDWICH_BOARD);
                    output.accept(ModItems.CORNFLOWER_SANDWICH_BOARD);
                    output.accept(ModItems.ORCHID_SANDWICH_BOARD);
                    output.accept(ModItems.ALLIUM_SANDWICH_BOARD);
                    output.accept(ModItems.AZURE_BLUET_SANDWICH_BOARD);
                    output.accept(ModItems.TULIP_SANDWICH_BOARD);
                    output.accept(ModItems.TORCHFLOWER_SANDWICH_BOARD);
                    output.accept(ModItems.WITHER_ROSE_SANDWICH_BOARD);
                    output.accept(ModItems.PEONY_SANDWICH_BOARD);
                    output.accept(ModItems.PITCHER_PLANT_SANDWICH_BOARD);

                    output.accept(ModItems.YSBB_PAINTING);
                    output.accept(ModItems.TARTARIC_ACID_PAINTING);
                    output.accept(ModItems.CR019_PAINTING);
                    output.accept(ModItems.UNKNOWN_PAINTING);
                    output.accept(ModItems.MASTER_MARISA_PAINTING);
                    output.accept(ModItems.NIGHT_EPIPHANY_PAINTING);
                    output.accept(ModItems.SON_OF_MAN_PAINTING);
                    output.accept(ModItems.DAVID_PAINTING);
                    output.accept(ModItems.GIRL_WITH_PEARL_EARRING_PAINTING);
                    output.accept(ModItems.STARRY_NIGHT_PAINTING);
                    output.accept(ModItems.VAN_GOGH_SELF_PORTRAIT_PAINTING);
                    output.accept(ModItems.FATHER_PAINTING);
                    output.accept(ModItems.GREAT_WAVE_PAINTING);
                    output.accept(ModItems.MONA_LISA_PAINTING);
                    output.accept(ModItems.MONDRIAN_PAINTING);

                    output.accept(ModItems.CHALKBOARD);

                    // 香薰
                    output.accept(ModItems.SAKURA_INCENSE);
                    output.accept(ModItems.PINE_INCENSE);
                    output.accept(ModItems.GINKGO_INCENSE);
                    output.accept(ModItems.SPORE_INCENSE);
                    output.accept(ModItems.CATNIP_INCENSE);
                    output.accept(ModItems.SNOW_INCENSE);
                    output.accept(ModItems.BUTTERFLY_INCENSE);
                    output.accept(ModItems.FIREFLY_INCENSE);
                }).build());
    }
}
