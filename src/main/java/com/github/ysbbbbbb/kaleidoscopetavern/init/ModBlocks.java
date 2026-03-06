package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.*;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.*;
import com.github.ysbbbbbb.kaleidoscopetavern.block.plant.*;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.*;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.ChalkboardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.SandwichBoardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.util.PortHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ModBlocks {
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
    public static final Block WHITE_BAR_STOOL = barStoolReg("white_bar_stool");
    public static final Block LIGHT_GRAY_BAR_STOOL = barStoolReg("light_gray_bar_stool");
    public static final Block GRAY_BAR_STOOL = barStoolReg("gray_bar_stool");
    public static final Block BLACK_BAR_STOOL = barStoolReg("black_bar_stool");
    public static final Block BROWN_BAR_STOOL = barStoolReg("brown_bar_stool");
    public static final Block RED_BAR_STOOL = barStoolReg("red_bar_stool");
    public static final Block ORANGE_BAR_STOOL = barStoolReg("orange_bar_stool");
    public static final Block YELLOW_BAR_STOOL = barStoolReg("yellow_bar_stool");
    public static final Block LIME_BAR_STOOL = barStoolReg("lime_bar_stool");
    public static final Block GREEN_BAR_STOOL = barStoolReg("green_bar_stool");
    public static final Block CYAN_BAR_STOOL = barStoolReg("cyan_bar_stool");
    public static final Block LIGHT_BLUE_BAR_STOOL = barStoolReg("light_blue_bar_stool");
    public static final Block BLUE_BAR_STOOL = barStoolReg("blue_bar_stool");
    public static final Block PURPLE_BAR_STOOL = barStoolReg("purple_bar_stool");
    public static final Block MAGENTA_BAR_STOOL = barStoolReg("magenta_bar_stool");
    public static final Block PINK_BAR_STOOL = barStoolReg("pink_bar_stool");
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
    // 彩灯
    public static final Block STRING_LIGHTS_COLORLESS = stringLightReg("string_lights_colorless", null);
    public static final Block STRING_LIGHTS_WHITE = stringLightReg("string_lights_white", Items.WHITE_DYE);
    public static final Block STRING_LIGHTS_LIGHT_GRAY = stringLightReg("string_lights_light_gray", Items.LIGHT_GRAY_DYE);
    public static final Block STRING_LIGHTS_GRAY = stringLightReg("string_lights_gray", Items.GRAY_DYE);
    public static final Block STRING_LIGHTS_BLACK = stringLightReg("string_lights_black", Items.BLACK_DYE);
    public static final Block STRING_LIGHTS_BROWN = stringLightReg("string_lights_brown", Items.BROWN_DYE);
    public static final Block STRING_LIGHTS_RED = stringLightReg("string_lights_red", Items.RED_DYE);
    public static final Block STRING_LIGHTS_ORANGE = stringLightReg("string_lights_orange", Items.ORANGE_DYE);
    public static final Block STRING_LIGHTS_YELLOW = stringLightReg("string_lights_yellow", Items.YELLOW_DYE);
    public static final Block STRING_LIGHTS_LIME = stringLightReg("string_lights_lime", Items.LIME_DYE);
    public static final Block STRING_LIGHTS_GREEN = stringLightReg("string_lights_green", Items.GREEN_DYE);
    public static final Block STRING_LIGHTS_CYAN = stringLightReg("string_lights_cyan", Items.CYAN_DYE);
    public static final Block STRING_LIGHTS_LIGHT_BLUE = stringLightReg("string_lights_light_blue", Items.LIGHT_BLUE_DYE);
    public static final Block STRING_LIGHTS_BLUE = stringLightReg("string_lights_blue", Items.BLUE_DYE);
    public static final Block STRING_LIGHTS_PURPLE = stringLightReg("string_lights_purple", Items.PURPLE_DYE);
    public static final Block STRING_LIGHTS_MAGENTA = stringLightReg("string_lights_magenta", Items.MAGENTA_DYE);
    public static final Block STRING_LIGHTS_PINK = stringLightReg("string_lights_pink", Items.PINK_DYE);
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
    public static final Block VAN_GOGH_SELF_PORTRAIT_PAINTING = paintingReg("vangogh_self_portrait_painting");
    public static final Block FATHER_PAINTING = paintingReg("father_painting");
    public static final Block GREAT_WAVE_PAINTING = paintingReg("great_wave_painting");
    public static final Block MONA_LISA_PAINTING = paintingReg("mona_lisa_painting");
    public static final Block MONDRIAN_PAINTING = paintingReg("mondrian_painting");
    public static final Block NIGHT_EPIPHANY_PAINTING = paintingReg("night_epiphany_painting");
    // 空瓶
    public static final Block EMPTY_BOTTLE = commonReg("empty_bottle", p -> new BottleBlock(p, false),
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .instabreak()
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.GLASS)
    );

    // 野生葡萄藤
    public static final Block WILD_GRAPEVINE = commonReg("wild_grapevine", WildGrapevineBlock::new, BlockBehaviour.Properties.of());
    public static final Block WILD_GRAPEVINE_PLANT = commonReg("wild_grapevine_plant", WildGrapevinePlantBlock::new, BlockBehaviour.Properties.of());
    // 藤架
    public static final Block TRELLIS = commonReg("trellis", TrellisBlock::new, BlockBehaviour.Properties.of());
    // 葡萄藤
    public static final Block GRAPEVINE_TRELLIS = commonReg("grapevine_trellis", GrapevineTrellisBlock::new, BlockBehaviour.Properties.of());
    // 葡萄
    public static final Block GRAPE_CROP = commonReg("grape_crop", GrapeCropBlock::new, BlockBehaviour.Properties.of());
    // 吧台
    public static final Block BAR_COUNTER = commonReg("bar_counter", BarCounterBlock::new, BlockBehaviour.Properties.of());
    // 人字梯
    public static final Block STEPLADDER = commonReg("stepladder", StepladderBlock::new, BlockBehaviour.Properties.of());
    // 黑板
    public static final Block CHALKBOARD = commonReg("chalkboard", ChalkboardBlock::new, BlockBehaviour.Properties.of());

    // 龙头
    public static final Block TAP = commonReg("tap", TapBlock::new, BlockBehaviour.Properties.of());

    // 燃烧瓶
    public static final Block MOLOTOV = commonReg("molotov", MolotovBlock::new, BlockBehaviour.Properties.of());

    // 酒柜
    public static final Block BAR_CABINET = commonReg("bar_cabinet", BarCabinetBlock::new, BlockBehaviour.Properties.of());
    public static final Block GLASS_BAR_CABINET = commonReg("glass_bar_cabinet", BarCabinetBlock::new, BlockBehaviour.Properties.of());

    // 酒桶
    public static final Block BARREL = commonReg("barrel", BarrelBlock::new, BlockBehaviour.Properties.of());

    // 果盆
    public static final Block PRESSING_TUB = commonReg("pressing_tub", PressingTubBlock::new, BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.GUITAR)
            .strength(0.8F)
            .sound(SoundType.WOOD)
            .ignitedByLava());

    // 酒
    public static final Block WINE = wineReg(ModFoods.ModWines.WINE);
    public static final Block CHAMPAGNE = wineReg(ModFoods.ModWines.CHAMPAGNE);
    public static final Block VODKA = wineReg(ModFoods.ModWines.VODKA);
    public static final Block BRANDY = wineReg(ModFoods.ModWines.BRANDY);
    public static final Block CARIGNAN = wineReg(ModFoods.ModWines.CARIGNAN);
    public static final Block SAKURA_WINE = wineReg(ModFoods.ModWines.SAKURA_WINE);
    public static final Block PLUM_WINE = wineReg(ModFoods.ModWines.PLUM_WINE);
    public static final Block WHISKEY = wineReg(ModFoods.ModWines.WHISKEY);
    public static final Block ICE_WINE = wineReg(ModFoods.ModWines.ICE_WINE);
    public static final Block VINEGAR = wineReg(ModFoods.ModWines.VINEGAR);


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
            WITHER_ROSE_SANDWICH_BOARD
    ).build();
    public static final BlockEntityType<ChalkboardBlockEntity> CHALKBOARD_BE = FabricBlockEntityTypeBuilder.create(ChalkboardBlockEntity::new, CHALKBOARD).build();
    public static final BlockEntityType<BarrelBlockEntity> BARREL_BE = FabricBlockEntityTypeBuilder.create(BarrelBlockEntity::new, BARREL).build();
    public static final BlockEntityType<PressingTubBlockEntity> PRESSING_TUB_BE = FabricBlockEntityTypeBuilder.create(PressingTubBlockEntity::new, PRESSING_TUB).build();
    public static final BlockEntityType<BarCabinetBlockEntity> BAR_CABINET_BE = FabricBlockEntityTypeBuilder.create(BarCabinetBlockEntity::new,
            BAR_CABINET,
            GLASS_BAR_CABINET
    ).build();
    public static final BlockEntityType<TapBlockEntity> TAP_BE = FabricBlockEntityTypeBuilder.create(TapBlockEntity::new, TAP).build();
    public static final BlockEntityType<DrinkBlockEntity> DRINK_BE = FabricBlockEntityTypeBuilder.create(DrinkBlockEntity::new,
            WINE,
            CHAMPAGNE,
            VODKA,
            BRANDY,
            CARIGNAN,
            SAKURA_WINE,
            PLUM_WINE,
            WHISKEY,
            ICE_WINE,
            VINEGAR
    ).build();


    public static void registerBlocks() {
        // Block entities
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "barrel"), BARREL_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "pressing_tub"), PRESSING_TUB_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "bar_cabinet"), BAR_CABINET_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "drink"), DRINK_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "tap"), TAP_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "chalkboard"), CHALKBOARD_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "sandwich_board"), SANDWICH_BOARD_BE);
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

    private static Block barStoolReg(String string) {
        return commonReg(string, BarStoolBlock::new, BlockBehaviour.Properties.of());
    }

    private static Block sandwichBoardReg(String string, Item... items) {
        return commonReg(string, p -> new SandwichBoardBlock(p, items), BlockBehaviour.Properties.of());
    }

    private static Block paintingReg(String string) {
        return commonReg(string, PaintingBlock::new, BlockBehaviour.Properties.of());
    }

    private static Block wineReg(Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, PortHelper.createBlockId(((DrinkBlock) block).getId()), block);
    }
}
