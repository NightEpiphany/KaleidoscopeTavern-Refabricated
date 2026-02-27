package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.*;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.*;
import com.github.ysbbbbbb.kaleidoscopetavern.block.plant.*;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.BarrelBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.DrinkBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.TapBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.brew.PressingTubBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.ChalkboardBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco.SandwichBoardBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.shapes.Shapes;

public class ModBlocks {
    // 沙发
    public static final Block WHITE_SOFA = new SofaBlock();
    public static final Block LIGHT_GRAY_SOFA = new SofaBlock();
    public static final Block GRAY_SOFA = new SofaBlock();
    public static final Block BLACK_SOFA = new SofaBlock();
    public static final Block BROWN_SOFA = new SofaBlock();
    public static final Block RED_SOFA = new SofaBlock();
    public static final Block ORANGE_SOFA = new SofaBlock();
    public static final Block YELLOW_SOFA = new SofaBlock();
    public static final Block LIME_SOFA = new SofaBlock();
    public static final Block GREEN_SOFA = new SofaBlock();
    public static final Block CYAN_SOFA = new SofaBlock();
    public static final Block LIGHT_BLUE_SOFA = new SofaBlock();
    public static final Block BLUE_SOFA = new SofaBlock();
    public static final Block PURPLE_SOFA = new SofaBlock();
    public static final Block MAGENTA_SOFA = new SofaBlock();
    public static final Block PINK_SOFA = new SofaBlock();
    // 高脚凳
    public static final Block WHITE_BAR_STOOL = new BarStoolBlock();
    public static final Block LIGHT_GRAY_BAR_STOOL = new BarStoolBlock();
    public static final Block GRAY_BAR_STOOL = new BarStoolBlock();
    public static final Block BLACK_BAR_STOOL = new BarStoolBlock();
    public static final Block BROWN_BAR_STOOL = new BarStoolBlock();
    public static final Block RED_BAR_STOOL = new BarStoolBlock();
    public static final Block ORANGE_BAR_STOOL = new BarStoolBlock();
    public static final Block YELLOW_BAR_STOOL = new BarStoolBlock();
    public static final Block LIME_BAR_STOOL = new BarStoolBlock();
    public static final Block GREEN_BAR_STOOL = new BarStoolBlock();
    public static final Block CYAN_BAR_STOOL = new BarStoolBlock();
    public static final Block LIGHT_BLUE_BAR_STOOL = new BarStoolBlock();
    public static final Block BLUE_BAR_STOOL = new BarStoolBlock();
    public static final Block PURPLE_BAR_STOOL = new BarStoolBlock();
    public static final Block MAGENTA_BAR_STOOL = new BarStoolBlock();
    public static final Block PINK_BAR_STOOL = new BarStoolBlock();
    // 展板
    public static final Block BASE_SANDWICH_BOARD = new SandwichBoardBlock();
    public static final Block GRASS_SANDWICH_BOARD = new SandwichBoardBlock(Items.GRASS);
    public static final Block ALLIUM_SANDWICH_BOARD = new SandwichBoardBlock(Items.AZURE_BLUET, Items.OXEYE_DAISY, Items.LILY_OF_THE_VALLEY);
    public static final Block AZURE_BLUET_SANDWICH_BOARD = new SandwichBoardBlock(Items.AZURE_BLUET, Items.OXEYE_DAISY, Items.LILY_OF_THE_VALLEY);
    public static final Block CORNFLOWER_SANDWICH_BOARD = new SandwichBoardBlock(Items.CORNFLOWER);
    public static final Block ORCHID_SANDWICH_BOARD = new SandwichBoardBlock(Items.BLUE_ORCHID);
    public static final Block PEONY_SANDWICH_BOARD = new SandwichBoardBlock(Items.PEONY, Items.LILAC);
    public static final Block PINK_PETALS_SANDWICH_BOARD = new SandwichBoardBlock(Items.PINK_PETALS);
    public static final Block PITCHER_PLANT_SANDWICH_BOARD = new SandwichBoardBlock(Items.PITCHER_PLANT);
    public static final Block POPPY_SANDWICH_BOARD = new SandwichBoardBlock(Items.POPPY, Items.ROSE_BUSH);
    public static final Block SUNFLOWER_SANDWICH_BOARD = new SandwichBoardBlock(Items.SUNFLOWER, Items.DANDELION);
    public static final Block TORCHFLOWER_SANDWICH_BOARD = new SandwichBoardBlock(Items.TORCHFLOWER);
    public static final Block TULIP_SANDWICH_BOARD = new SandwichBoardBlock(Items.RED_TULIP, Items.ORANGE_TULIP, Items.WHITE_TULIP, Items.PINK_TULIP);
    public static final Block WITHER_ROSE_SANDWICH_BOARD = new SandwichBoardBlock(Items.WITHER_ROSE);
    // 彩灯
    public static final Block STRING_LIGHTS_COLORLESS = new StringLightsBlock(null);
    public static final Block STRING_LIGHTS_WHITE = new StringLightsBlock(Items.WHITE_DYE);
    public static final Block STRING_LIGHTS_LIGHT_GRAY = new StringLightsBlock(Items.LIGHT_GRAY_DYE);
    public static final Block STRING_LIGHTS_GRAY = new StringLightsBlock(Items.GRAY_DYE);
    public static final Block STRING_LIGHTS_BLACK = new StringLightsBlock(Items.BLACK_DYE);
    public static final Block STRING_LIGHTS_BROWN = new StringLightsBlock(Items.BROWN_DYE);
    public static final Block STRING_LIGHTS_RED = new StringLightsBlock(Items.RED_DYE);
    public static final Block STRING_LIGHTS_ORANGE = new StringLightsBlock(Items.ORANGE_DYE);
    public static final Block STRING_LIGHTS_YELLOW = new StringLightsBlock(Items.YELLOW_DYE);
    public static final Block STRING_LIGHTS_LIME = new StringLightsBlock(Items.LIME_DYE);
    public static final Block STRING_LIGHTS_GREEN = new StringLightsBlock(Items.GREEN_DYE);
    public static final Block STRING_LIGHTS_CYAN = new StringLightsBlock(Items.CYAN_DYE);
    public static final Block STRING_LIGHTS_LIGHT_BLUE = new StringLightsBlock(Items.LIGHT_BLUE_DYE);
    public static final Block STRING_LIGHTS_BLUE = new StringLightsBlock(Items.BLUE_DYE);
    public static final Block STRING_LIGHTS_PURPLE = new StringLightsBlock(Items.PURPLE_DYE);
    public static final Block STRING_LIGHTS_MAGENTA = new StringLightsBlock(Items.MAGENTA_DYE);
    public static final Block STRING_LIGHTS_PINK = new StringLightsBlock(Items.PINK_DYE);
    // 挂画
    public static final Block YSBB_PAINTING = new PaintingBlock();
    public static final Block TARTARIC_ACID_PAINTING = new PaintingBlock();
    public static final Block CR019_PAINTING = new PaintingBlock();
    public static final Block UNKNOWN_PAINTING = new PaintingBlock();
    public static final Block MASTER_MARISA_PAINTING = new PaintingBlock();
    public static final Block SON_OF_MAN_PAINTING = new PaintingBlock();
    public static final Block DAVID_PAINTING = new PaintingBlock();
    public static final Block GIRL_WITH_PEARL_EARRING_PAINTING = new PaintingBlock();
    public static final Block STARRY_NIGHT_PAINTING = new PaintingBlock();
    public static final Block VAN_GOGH_SELF_PORTRAIT_PAINTING = new PaintingBlock();
    public static final Block FATHER_PAINTING = new PaintingBlock();
    public static final Block GREAT_WAVE_PAINTING = new PaintingBlock();
    public static final Block MONA_LISA_PAINTING = new PaintingBlock();
    public static final Block MONDRIAN_PAINTING = new PaintingBlock();
    // 果盆
    public static final Block PRESSING_TUB = new PressingTubBlock();
    // 空瓶
    public static final Block EMPTY_BOTTLE = new BottleBlock();
    // 酒桶
    public static final Block BARREL = new BarrelBlock();
    // 野生葡萄藤
    public static final Block WILD_GRAPEVINE = new WildGrapevineBlock();
    public static final Block WILD_GRAPEVINE_PLANT = new WildGrapevinePlantBlock();
    // 藤架
    public static final Block TRELLIS = new TrellisBlock();
    // 葡萄藤
    public static final Block GRAPEVINE_TRELLIS = new GrapevineTrellisBlock();
    // 葡萄
    public static final Block GRAPE_CROP = new GrapeCropBlock();
    // 燃烧瓶
    public static final Block MOLOTOV =new BottleBlock();
    // 龙头
    public static final Block TAP = new TapBlock();
    // 吧台
    public static final Block BAR_COUNTER = new BarCounterBlock();
    // 人字梯
    public static final Block STEPLADDER = new StepladderBlock();
    // 黑板
    public static final Block CHALKBOARD = new ChalkboardBlock();
    // 酒
    public static final Block WINE = DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build();
    public static final Block CHAMPAGNE = DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build();
    public static final Block VODKA = DrinkBlock.create().maxCount(4).shapes(
            Block.box(4, 0, 4, 12, 15, 12),
            Block.box(0, 0, 4, 16, 15, 12),
            Shapes.or(
                    Block.box(0, 0, 8, 16, 15, 16),
                    Block.box(4, 0, 0, 12, 15, 16)
            ),
            Block.box(0, 0, 0, 16, 16, 16)
    ).build();
    public static final Block BRANDY = DrinkBlock.create().maxCount(3).shapes(
            Block.box(3, 0, 6, 13, 13, 10),
            Block.box(1, 0, 3, 15, 12, 12),
            Block.box(1, 0, 1, 16, 12, 13)
    ).build();
    public static final Block CARIGNAN = DrinkBlock.create().maxCount(3).shapes(
            Block.box(3, 0, 6, 13, 13, 10),
            Block.box(1, 0, 3, 15, 12, 12),
            Block.box(1, 0, 1, 16, 12, 13)
    ).build();
    public static final Block SAKURA_WINE = DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build();
    public static final Block PLUM_WINE = DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 12, 10),
            Block.box(3, 0, 6, 13, 12, 10),
            Shapes.or(
                    Block.box(3, 0, 9, 13, 12, 13),
                    Block.box(6, 0, 3, 10, 12, 13)
            ),
            Block.box(3, 0, 3, 13, 12, 13)
    ).build();
    public static final Block WHISKEY = DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build();
    public static final Block ICE_WINE = DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build();
    public static final Block VINEGAR = DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build();

    // Block entity
    public static final BlockEntityType<SandwichBoardBlockEntity> SANDWICH_BOARD_BE = BlockEntityType.Builder.of(SandwichBoardBlockEntity::new,
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
    ).build(null);
    public static final BlockEntityType<ChalkboardBlockEntity> CHALKBOARD_BE = BlockEntityType.Builder.of(ChalkboardBlockEntity::new, CHALKBOARD).build(null);
    public static final BlockEntityType<PressingTubBlockEntity> PRESSING_TUB_BE = BlockEntityType.Builder.of(PressingTubBlockEntity::new, PRESSING_TUB).build(null);
    public static final BlockEntityType<BarrelBlockEntity> BARREL_BE = BlockEntityType.Builder.of(BarrelBlockEntity::new, BARREL).build(null);
    public static final BlockEntityType<TapBlockEntity> TAP_BE = BlockEntityType.Builder.of(TapBlockEntity::new, TAP).build(null);
    public static final BlockEntityType<DrinkBlockEntity> DRINK_BE = BlockEntityType.Builder.of(DrinkBlockEntity::new,
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
    ).build(null);

    public static void registerBlocks() {
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "white_sofa"), WHITE_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "black_sofa"), BLACK_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "yellow_sofa"), YELLOW_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "red_sofa"), RED_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "blue_sofa"), BLUE_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "green_sofa"), GREEN_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "purple_sofa"), PURPLE_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "orange_sofa"), ORANGE_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pink_sofa"), PINK_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "brown_sofa"), BROWN_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "gray_sofa"), GRAY_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "light_gray_sofa"), LIGHT_GRAY_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "cyan_sofa"), CYAN_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "magenta_sofa"), MAGENTA_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "lime_sofa"), LIME_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "light_blue_sofa"), LIGHT_BLUE_SOFA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "white_bar_stool"), WHITE_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "light_gray_bar_stool"), LIGHT_GRAY_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "black_bar_stool"), BLACK_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "brown_bar_stool"), BROWN_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "gray_bar_stool"), GRAY_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "cyan_bar_stool"), CYAN_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "magenta_bar_stool"), MAGENTA_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "lime_bar_stool"), LIME_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "light_blue_bar_stool"), LIGHT_BLUE_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "red_bar_stool"), RED_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "yellow_bar_stool"), YELLOW_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pink_bar_stool"), PINK_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "purple_bar_stool"), PURPLE_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "orange_bar_stool"), ORANGE_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "blue_bar_stool"), BLUE_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "green_bar_stool"), GREEN_BAR_STOOL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "base_sandwich_board"), BASE_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "grass_sandwich_board"), GRASS_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "allium_sandwich_board"), ALLIUM_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "azure_bluet_sandwich_board"), AZURE_BLUET_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "cornflower_sandwich_board"), CORNFLOWER_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "orchid_sandwich_board"), ORCHID_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "peony_sandwich_board"), PEONY_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pink_petals_sandwich_board"), PINK_PETALS_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pitcher_plant_sandwich_board"), PITCHER_PLANT_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "poppy_sandwich_board"), POPPY_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "sunflower_sandwich_board"), SUNFLOWER_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "torchflower_sandwich_board"), TORCHFLOWER_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "tulip_sandwich_board"), TULIP_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "wither_rose_sandwich_board"), WITHER_ROSE_SANDWICH_BOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_colorless"), STRING_LIGHTS_COLORLESS);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_white"), STRING_LIGHTS_WHITE);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_light_gray"), STRING_LIGHTS_LIGHT_GRAY);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_gray"), STRING_LIGHTS_GRAY);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_black"), STRING_LIGHTS_BLACK);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_brown"), STRING_LIGHTS_BROWN);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_red"), STRING_LIGHTS_RED);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_orange"), STRING_LIGHTS_ORANGE);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_yellow"), STRING_LIGHTS_YELLOW);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_lime"), STRING_LIGHTS_LIME);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_green"), STRING_LIGHTS_GREEN);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_cyan"), STRING_LIGHTS_CYAN);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_light_blue"), STRING_LIGHTS_LIGHT_BLUE);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_blue"), STRING_LIGHTS_BLUE);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_purple"), STRING_LIGHTS_PURPLE);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_magenta"), STRING_LIGHTS_MAGENTA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "string_lights_pink"), STRING_LIGHTS_PINK);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "ysbb_painting"), YSBB_PAINTING);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "tartaric_acid_painting"), TARTARIC_ACID_PAINTING);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "cr019_painting"), CR019_PAINTING);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "unknown_painting"), UNKNOWN_PAINTING);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "master_marisa_painting"), MASTER_MARISA_PAINTING);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "son_of_man_painting"), SON_OF_MAN_PAINTING);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "david_painting"), DAVID_PAINTING);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "girl_with_pearl_earring_painting"), GIRL_WITH_PEARL_EARRING_PAINTING);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "starry_night_painting"), STARRY_NIGHT_PAINTING);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "van_gogh_self_portrait_painting"), VAN_GOGH_SELF_PORTRAIT_PAINTING);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "father_painting"), FATHER_PAINTING);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "great_wave_painting"), GREAT_WAVE_PAINTING);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "mona_lisa_painting"), MONA_LISA_PAINTING);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "mondrian_painting"), MONDRIAN_PAINTING);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pressing_tub"), PRESSING_TUB);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "empty_bottle"), EMPTY_BOTTLE);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "barrel"), BARREL);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "wild_grapevine"), WILD_GRAPEVINE);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "wild_grapevine_plant"), WILD_GRAPEVINE_PLANT);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "trellis"), TRELLIS);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "grapevine_trellis"), GRAPEVINE_TRELLIS);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "grape_crop"), GRAPE_CROP);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "molotov"), MOLOTOV);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "tap"), TAP);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "bar_counter"), BAR_COUNTER);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "stepladder"), STEPLADDER);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "chalkboard"), CHALKBOARD);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "wine"), WINE);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "champagne"), CHAMPAGNE);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "vodka"), VODKA);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "brandy"), BRANDY);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "carignan"), CARIGNAN);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "sakura_wine"), SAKURA_WINE);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "plum_wine"), PLUM_WINE);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "whiskey"), WHISKEY);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "ice_wine"), ICE_WINE);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "vinegar"), VINEGAR);

        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "sandwich_board"), SANDWICH_BOARD_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "chalkboard"), CHALKBOARD_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pressing_tub"), PRESSING_TUB_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "barrel"), BARREL_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "tap"), TAP_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(KaleidoscopeTavern.MOD_ID, "drink"), DRINK_BE);
    }

}
