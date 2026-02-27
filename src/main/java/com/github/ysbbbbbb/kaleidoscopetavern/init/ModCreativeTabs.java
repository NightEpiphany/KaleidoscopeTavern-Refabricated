package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class ModCreativeTabs {
    private static final ResourceLocation MAIN_ICON_ID = new ResourceLocation(KaleidoscopeTavern.MOD_ID, "wine");

    private static final ResourceLocation DECO_ICON_ID = new ResourceLocation(KaleidoscopeTavern.MOD_ID, "stepladder");

    private static final ResourceKey<CreativeModeTab> TAVERN_MAIN_TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
            new ResourceLocation(KaleidoscopeTavern.MOD_ID, "tavern_main"));

    private static final ResourceKey<CreativeModeTab> TAVERN_DECO_TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
            new ResourceLocation(KaleidoscopeTavern.MOD_ID, "tavern_deco"));


    public static void registerTabs() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAVERN_MAIN_TAB, FabricItemGroup.builder()
                .title(Component.translatable("item_group.kaleidoscope_tavern.tavern_main.name"))
                .icon(() -> BuiltInRegistries.ITEM.get(MAIN_ICON_ID).getDefaultInstance())
                .displayItems((par, output) -> {
                    output.accept(ModItems.GRAPEVINE);
                    output.accept(ModItems.GRAPE);
                    output.accept(ModItems.TRELLIS);

                    output.accept(ModItems.PRESSING_TUB);
                    output.accept(ModItems.BARREL);
                    output.accept(ModItems.TAP);

                    output.accept(ModItems.GRAPE_BUCKET);
                    output.accept(ModItems.SWEET_BERRIES_BUCKET);
                    output.accept(ModItems.GLOW_BERRIES_BUCKET);

                    output.accept(ModItems.EMPTY_BOTTLE);
                    output.accept(ModItems.MOLOTOV);

                    output.accept(ModItems.VINEGAR);
                    output.accept(ModItems.WINE);
                    output.accept(ModItems.ICE_WINE);
                    output.accept(ModItems.SAKURA_WINE);
                    output.accept(ModItems.PLUM_WINE);
                    output.accept(ModItems.CHAMPAGNE);
                    output.accept(ModItems.VODKA);
                    output.accept(ModItems.BRANDY);
                    output.accept(ModItems.CARIGNAN);
                    output.accept(ModItems.WHISKEY);
                }).build());

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAVERN_DECO_TAB, FabricItemGroup.builder()
                .title(Component.translatable("item_group.kaleidoscope_tavern.tavern_deco.name"))
                .icon(() -> BuiltInRegistries.ITEM.get(DECO_ICON_ID).getDefaultInstance())
                .displayItems((par, output) -> {
                    output.accept(ModItems.BAR_COUNTER);
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
                }).build());
    }
}
