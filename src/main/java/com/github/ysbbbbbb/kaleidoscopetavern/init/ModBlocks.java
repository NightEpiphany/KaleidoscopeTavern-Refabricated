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
import com.github.ysbbbbbb.kaleidoscopetavern.init.registery.ids.ModBlockIds;
import com.github.ysbbbbbb.kaleidoscopetavern.init.registery.ids.ModBlockItemIds;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.sounds.BlockSoundSets;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@SuppressWarnings("all")
public final class ModBlocks {
    // 沙发
    public static final Block WHITE_SOFA = sofaReg(ModBlockItemIds.WHITE_SOFA.block());
    public static final Block LIGHT_GRAY_SOFA = sofaReg(ModBlockItemIds.LIGHT_GRAY_SOFA.block());
    public static final Block GRAY_SOFA = sofaReg(ModBlockItemIds.GRAY_SOFA.block());
    public static final Block BLACK_SOFA = sofaReg(ModBlockItemIds.BLACK_SOFA.block());
    public static final Block BROWN_SOFA = sofaReg(ModBlockItemIds.BROWN_SOFA.block());
    public static final Block RED_SOFA = sofaReg(ModBlockItemIds.RED_SOFA.block());
    public static final Block ORANGE_SOFA = sofaReg(ModBlockItemIds.ORANGE_SOFA.block());
    public static final Block YELLOW_SOFA = sofaReg(ModBlockItemIds.YELLOW_SOFA.block());
    public static final Block LIME_SOFA = sofaReg(ModBlockItemIds.LIME_SOFA.block());
    public static final Block GREEN_SOFA = sofaReg(ModBlockItemIds.GREEN_SOFA.block());
    public static final Block CYAN_SOFA = sofaReg(ModBlockItemIds.CYAN_SOFA.block());
    public static final Block LIGHT_BLUE_SOFA = sofaReg(ModBlockItemIds.LIGHT_BLUE_SOFA.block());
    public static final Block BLUE_SOFA = sofaReg(ModBlockItemIds.BLUE_SOFA.block());
    public static final Block PURPLE_SOFA = sofaReg(ModBlockItemIds.PURPLE_SOFA.block());
    public static final Block MAGENTA_SOFA = sofaReg(ModBlockItemIds.MAGENTA_SOFA.block());
    public static final Block PINK_SOFA = sofaReg(ModBlockItemIds.PINK_SOFA.block());
    // 高脚凳
    public static final Block WHITE_BAR_STOOL = barStoolReg(ModBlockItemIds.WHITE_BAR_STOOL.block(), DyeColor.WHITE);
    public static final Block LIGHT_GRAY_BAR_STOOL = barStoolReg(ModBlockItemIds.LIGHT_GRAY_BAR_STOOL.block(), DyeColor.LIGHT_GRAY);
    public static final Block GRAY_BAR_STOOL = barStoolReg(ModBlockItemIds.GRAY_BAR_STOOL.block(), DyeColor.GRAY);
    public static final Block BLACK_BAR_STOOL = barStoolReg(ModBlockItemIds.BLACK_BAR_STOOL.block(), DyeColor.BLACK);
    public static final Block BROWN_BAR_STOOL = barStoolReg(ModBlockItemIds.BROWN_BAR_STOOL.block(), DyeColor.BROWN);
    public static final Block RED_BAR_STOOL = barStoolReg(ModBlockItemIds.RED_BAR_STOOL.block(), DyeColor.RED);
    public static final Block ORANGE_BAR_STOOL = barStoolReg(ModBlockItemIds.ORANGE_BAR_STOOL.block(), DyeColor.ORANGE);
    public static final Block YELLOW_BAR_STOOL = barStoolReg(ModBlockItemIds.YELLOW_BAR_STOOL.block(), DyeColor.YELLOW);
    public static final Block LIME_BAR_STOOL = barStoolReg(ModBlockItemIds.LIME_BAR_STOOL.block(), DyeColor.LIME);
    public static final Block GREEN_BAR_STOOL = barStoolReg(ModBlockItemIds.GREEN_BAR_STOOL.block(), DyeColor.GREEN);
    public static final Block CYAN_BAR_STOOL = barStoolReg(ModBlockItemIds.CYAN_BAR_STOOL.block(), DyeColor.CYAN);
    public static final Block LIGHT_BLUE_BAR_STOOL = barStoolReg(ModBlockItemIds.LIGHT_BLUE_BAR_STOOL.block(), DyeColor.LIGHT_BLUE);
    public static final Block BLUE_BAR_STOOL = barStoolReg(ModBlockItemIds.BLUE_BAR_STOOL.block(), DyeColor.BLUE);
    public static final Block PURPLE_BAR_STOOL = barStoolReg(ModBlockItemIds.PURPLE_BAR_STOOL.block(), DyeColor.PURPLE);
    public static final Block MAGENTA_BAR_STOOL = barStoolReg(ModBlockItemIds.MAGENTA_BAR_STOOL.block(), DyeColor.MAGENTA);
    public static final Block PINK_BAR_STOOL = barStoolReg(ModBlockItemIds.PINK_BAR_STOOL.block(), DyeColor.PINK);
    // 展板
    public static final Block BASE_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.BASE_SANDWICH_BOARD.block());
    public static final Block GRASS_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.GRASS_SANDWICH_BOARD.block(), Items.SHORT_GRASS);
    public static final Block ALLIUM_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.ALLIUM_SANDWICH_BOARD.block(), Items.AZURE_BLUET, Items.OXEYE_DAISY, Items.LILY_OF_THE_VALLEY);
    public static final Block AZURE_BLUET_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.AZURE_BLUET_SANDWICH_BOARD.block(), Items.AZURE_BLUET, Items.OXEYE_DAISY, Items.LILY_OF_THE_VALLEY);
    public static final Block CORNFLOWER_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.CORNFLOWER_SANDWICH_BOARD.block(), Items.CORNFLOWER);
    public static final Block ORCHID_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.ORCHID_SANDWICH_BOARD.block(), Items.BLUE_ORCHID);
    public static final Block PEONY_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.PEONY_SANDWICH_BOARD.block(), Items.PEONY, Items.LILAC);
    public static final Block PINK_PETALS_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.PINK_PETALS_SANDWICH_BOARD.block(), Items.PINK_PETALS);
    public static final Block PITCHER_PLANT_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.PITCHER_PLANT_SANDWICH_BOARD.block(), Items.PITCHER_PLANT);
    public static final Block POPPY_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.POPPY_SANDWICH_BOARD.block(), Items.POPPY, Items.ROSE_BUSH);
    public static final Block SUNFLOWER_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.SUNFLOWER_SANDWICH_BOARD.block(), Items.SUNFLOWER, Items.DANDELION);
    public static final Block TORCHFLOWER_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.TORCHFLOWER_SANDWICH_BOARD.block(), Items.TORCHFLOWER);
    public static final Block TULIP_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.TULIP_SANDWICH_BOARD.block(), Items.RED_TULIP, Items.ORANGE_TULIP, Items.WHITE_TULIP, Items.PINK_TULIP);
    public static final Block WITHER_ROSE_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.WITHER_ROSE_SANDWICH_BOARD.block(), Items.WITHER_ROSE);
    public static final Block EYEBLOSSOM_SANDWICH_BOARD = sandwichBoardReg(ModBlockItemIds.EYEBLOSSOM_SANDWICH_BOARD.block(), Items.OPEN_EYEBLOSSOM, Items.CLOSED_EYEBLOSSOM);
    // 彩灯
    public static final Block STRING_LIGHTS_COLORLESS = stringLightReg(ModBlockItemIds.STRING_LIGHTS_COLORLESS.block(), null);
    public static final Block STRING_LIGHTS_WHITE = stringLightReg(ModBlockItemIds.STRING_LIGHTS_WHITE.block(), Items.DYE.white());
    public static final Block STRING_LIGHTS_LIGHT_GRAY = stringLightReg(ModBlockItemIds.STRING_LIGHTS_LIGHT_GRAY.block(), Items.DYE.lightGray());
    public static final Block STRING_LIGHTS_GRAY = stringLightReg(ModBlockItemIds.STRING_LIGHTS_GRAY.block(), Items.DYE.gray());
    public static final Block STRING_LIGHTS_BLACK = stringLightReg(ModBlockItemIds.STRING_LIGHTS_BLACK.block(), Items.DYE.black());
    public static final Block STRING_LIGHTS_BROWN = stringLightReg(ModBlockItemIds.STRING_LIGHTS_BROWN.block(), Items.DYE.brown());
    public static final Block STRING_LIGHTS_RED = stringLightReg(ModBlockItemIds.STRING_LIGHTS_RED.block(), Items.DYE.red());
    public static final Block STRING_LIGHTS_ORANGE = stringLightReg(ModBlockItemIds.STRING_LIGHTS_ORANGE.block(), Items.DYE.orange());
    public static final Block STRING_LIGHTS_YELLOW = stringLightReg(ModBlockItemIds.STRING_LIGHTS_YELLOW.block(), Items.DYE.yellow());
    public static final Block STRING_LIGHTS_LIME = stringLightReg(ModBlockItemIds.STRING_LIGHTS_LIME.block(), Items.DYE.lime());
    public static final Block STRING_LIGHTS_GREEN = stringLightReg(ModBlockItemIds.STRING_LIGHTS_GREEN.block(), Items.DYE.green());
    public static final Block STRING_LIGHTS_CYAN = stringLightReg(ModBlockItemIds.STRING_LIGHTS_CYAN.block(), Items.DYE.cyan());
    public static final Block STRING_LIGHTS_LIGHT_BLUE = stringLightReg(ModBlockItemIds.STRING_LIGHTS_LIGHT_BLUE.block(), Items.DYE.lightBlue());
    public static final Block STRING_LIGHTS_BLUE = stringLightReg(ModBlockItemIds.STRING_LIGHTS_BLUE.block(), Items.DYE.blue());
    public static final Block STRING_LIGHTS_PURPLE = stringLightReg(ModBlockItemIds.STRING_LIGHTS_PURPLE.block(), Items.DYE.purple());
    public static final Block STRING_LIGHTS_MAGENTA = stringLightReg(ModBlockItemIds.STRING_LIGHTS_MAGENTA.block(), Items.DYE.magenta());
    public static final Block STRING_LIGHTS_PINK = stringLightReg(ModBlockItemIds.STRING_LIGHTS_PINK.block(), Items.DYE.pink());
    // 挂画
    public static final Block YSBB_PAINTING = paintingReg(ModBlockItemIds.YSBB_PAINTING.block());
    public static final Block TARTARIC_ACID_PAINTING = paintingReg(ModBlockItemIds.TARTARIC_ACID_PAINTING.block());
    public static final Block CR019_PAINTING = paintingReg(ModBlockItemIds.CR019_PAINTING.block());
    public static final Block UNKNOWN_PAINTING = paintingReg(ModBlockItemIds.UNKNOWN_PAINTING.block());
    public static final Block MASTER_MARISA_PAINTING = paintingReg(ModBlockItemIds.MASTER_MARISA_PAINTING.block());
    public static final Block SON_OF_MAN_PAINTING = paintingReg(ModBlockItemIds.SON_OF_MAN_PAINTING.block());
    public static final Block DAVID_PAINTING = paintingReg(ModBlockItemIds.DAVID_PAINTING.block());
    public static final Block GIRL_WITH_PEARL_EARRING_PAINTING = paintingReg(ModBlockItemIds.GIRL_WITH_PEARL_EARRING_PAINTING.block());
    public static final Block STARRY_NIGHT_PAINTING = paintingReg(ModBlockItemIds.STARRY_NIGHT_PAINTING.block());
    public static final Block VAN_GOGH_SELF_PORTRAIT_PAINTING = paintingReg(ModBlockItemIds.VAN_GOGH_SELF_PORTRAIT_PAINTING.block());
    public static final Block FATHER_PAINTING = paintingReg(ModBlockItemIds.FATHER_PAINTING.block());
    public static final Block GREAT_WAVE_PAINTING = paintingReg(ModBlockItemIds.GREAT_WAVE_PAINTING.block());
    public static final Block MONA_LISA_PAINTING = paintingReg(ModBlockItemIds.MONA_LISA_PAINTING.block());
    public static final Block MONDRIAN_PAINTING = paintingReg(ModBlockItemIds.MONDRIAN_PAINTING.block());
    public static final Block NIGHT_EPIPHANY_PAINTING = paintingReg(ModBlockItemIds.NIGHT_EPIPHANY_PAINTING.block());
    public static final Block DOBELRING_PAINTING = paintingReg(ModBlockItemIds.DOBELRING_PAINTING.block());
    // 空瓶
    public static final Block EMPTY_BOTTLE = commonReg(ModBlockItemIds.EMPTY_BOTTLE.block(), BottleBlock::simpleBottle, BlockBehaviour.Properties.of());
    public static final Block EMPTY_GLASSWARE = commonReg(ModBlockItemIds.EMPTY_GLASSWARE.block(), GlasswareBlock::new, BlockBehaviour.Properties.of());
    // 酒杯架
    public static final Block GLASSWARE_HOLDER = commonReg(ModBlockItemIds.GLASSWARE_HOLDER.block(), GlasswareHolderBlock::new, BlockBehaviour.Properties.of());
    // 鸡尾酒
    public static final Block SIGNATURE_COCKTAIL = commonReg(ModBlockItemIds.SIGNATURE_COCKTAIL.block(), SignatureCocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block MYSTERY_COCKTAIL = commonReg(ModBlockItemIds.MYSTERY_COCKTAIL.block(), MysteryCocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block WHITE_LADY = commonReg(ModBlockItemIds.WHITE_LADY.block(), CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block EMERALD = commonReg(ModBlockItemIds.EMERALD.block(), CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block BRASS_HEART = commonReg(ModBlockItemIds.BRASS_HEART.block(), CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block GODFATHER = commonReg(ModBlockItemIds.GODFATHER.block(), CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block GRASSHOPPER = commonReg(ModBlockItemIds.GRASSHOPPER.block(), CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block SCREWDRIVER = commonReg(ModBlockItemIds.SCREWDRIVER.block(), CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block MOJITO = commonReg(ModBlockItemIds.MOJITO.block(), CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block ALLIUM_GARDEN = commonReg(ModBlockItemIds.ALLIUM_GARDEN.block(), CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block DEPTH_CHARGE = commonReg(ModBlockItemIds.DEPTH_CHARGE.block(), CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block NETHER_SPECIAL = commonReg(ModBlockItemIds.NETHER_SPECIAL.block(), CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block BLOODY_MARY = commonReg(ModBlockItemIds.BLOODY_MARY.block(), CocktailBlock::new, BlockBehaviour.Properties.of());
    public static final Block SCULK_SPECIAL = commonReg(ModBlockItemIds.SCULK_SPECIAL.block(), CocktailBlock::new, BlockBehaviour.Properties.of());
    // 杂项的瓶子
    public static final Block WATER_BOTTLE = commonReg(ModBlockIds.WATER_BOTTLE, BottleBlock::simpleBottle, BlockBehaviour.Properties.of());
    public static final Block HONEY_BOTTLE = commonReg(ModBlockIds.HONEY_BOTTLE, BottleBlock::simpleBottle, BlockBehaviour.Properties.of());
    public static final Block DRAGON_BREATH_BOTTLE = commonReg(ModBlockIds.DRAGON_BREATH_BOTTLE, BottleBlock::simpleBottle, BlockBehaviour.Properties.of());
    public static final Block POTION_BOTTLE = commonReg(ModBlockIds.POTION_BOTTLE, PotionBottleBlock::new, BlockBehaviour.Properties.of());
    public static final Block XP_BOTTLE = commonReg(ModBlockIds.XP_BOTTLE, BottleBlock::simpleBottle, BlockBehaviour.Properties.of());
    // 桌子
    public static final Block TABLE = commonReg(ModBlockItemIds.TABLE.block(), TableBlock::new, BlockBehaviour.Properties.of());
    // 野生葡萄藤
    public static final Block WILD_GRAPEVINE = commonReg(ModBlockItemIds.WILD_GRAPEVINE.block(), WildGrapevineBlock::new, BlockBehaviour.Properties.of());
    public static final Block WILD_GRAPEVINE_PLANT = commonReg(ModBlockIds.WILD_GRAPEVINE_PLANT, WildGrapevinePlantBlock::new, BlockBehaviour.Properties.of());
    // 藤架
    public static final Block TRELLIS = commonReg(ModBlockItemIds.TRELLIS.block(), TrellisBlock::new, BlockBehaviour.Properties.of());
    // 葡萄
    public static final Block GRAPE_CROP = commonReg(ModBlockItemIds.GRAPE_CROP.block(), p -> new GrapeCropBlock(
            p,  (_, _, _, _) -> 0.25F, () -> new ItemStack(ModItems.GRAPE, 3)
    ), BlockBehaviour.Properties.of().dynamicShape());
    public static final Block ICE_GRAPE_CROP = commonReg(ModBlockItemIds.ICE_GRAPE_CROP.block(), p -> new GrapeCropBlock(
            p,  (_, level, pos, _) -> level.getBiome(pos).value().getBaseTemperature() < 0.15F ? 0.8F : 0.25F, () -> new ItemStack(ModItems.ICE_GRAPE, 3)
    ), BlockBehaviour.Properties.of().dynamicShape());
    public static final Block GOLD_GRAPE_CROP = commonReg(ModBlockItemIds.GOLD_GRAPE_CROP.block(), p -> new GrapeCropBlock(
            p,  (_, level, pos, _) -> level.getBiome(pos).value().getBaseTemperature() > 1.0F ? 0.8F : 0.25F, () -> new ItemStack(ModItems.GOLD_GRAPE, 3)
    ), BlockBehaviour.Properties.of().dynamicShape());
    // 葡萄藤
    public static final Block GRAPEVINE_TRELLIS = commonReg(ModBlockIds.GRAPEVINE_TRELLIS, p -> new GrapevineTrellisBlock(
            p, (_, _, _, _) -> 0.25F, ModBlocks.GRAPE_CROP::defaultBlockState
    ), BlockBehaviour.Properties.of());
    public static final Block ICE_GRAPEVINE_TRELLIS = commonReg(ModBlockIds.ICE_GRAPEVINE_TRELLIS, p -> new GrapevineTrellisBlock(
            p, (_, level, pos, _) -> level.getBiome(pos).value().getBaseTemperature() < 0.15F ? 0.8F : 0.25F, ModBlocks.ICE_GRAPE_CROP::defaultBlockState
    ), BlockBehaviour.Properties.of());
    public static final Block GOLD_GRAPEVINE_TRELLIS = commonReg(ModBlockIds.GOLD_GRAPEVINE_TRELLIS, p -> new GrapevineTrellisBlock(
            p, (_, level, pos, _) -> level.getBiome(pos).value().getBaseTemperature() > 1.0F ? 0.8F : 0.25F, ModBlocks.GOLD_GRAPE_CROP::defaultBlockState
    ), BlockBehaviour.Properties.of());
    // 吧台
    public static final Block BAR_COUNTER = commonReg(ModBlockItemIds.BAR_COUNTER.block(), BarCounterBlock::new, BlockBehaviour.Properties.of());
    // 人字梯
    public static final Block STEPLADDER = commonReg(ModBlockItemIds.STEPLADDER.block(), StepladderBlock::new, BlockBehaviour.Properties.of());
    // 黑板
    public static final Block CHALKBOARD = commonReg(ModBlockItemIds.CHALKBOARD.block(), ChalkboardBlock::new, BlockBehaviour.Properties.of());
    public static final Block BELL_PENDANT_LAMP = commonReg(ModBlockItemIds.BELL_PENDANT_LAMP.block(), PendantLampBlock::new, BlockBehaviour.Properties.of());
    public static final Block YELLOW_PENDANT_LAMP = commonReg(ModBlockItemIds.YELLOW_PENDANT_LAMP.block(), PendantLampBlock::new, BlockBehaviour.Properties.of());
    public static final Block BLUE_PENDANT_LAMP = commonReg(ModBlockItemIds.BLUE_PENDANT_LAMP.block(), PendantLampBlock::new, BlockBehaviour.Properties.of());
    // 熏香
    public static final Block SAKURA_INCENSE = commonReg(ModBlockItemIds.SAKURA_INCENSE.block(),
            p -> new IncenseBlock(p, () -> ModParticles.SAKURA_INCENSE_PARTICLE, () -> ParticleTypes.CHERRY_LEAVES),
            BlockBehaviour.Properties.of());
    public static final Block PINE_INCENSE = commonReg(ModBlockItemIds.PINE_INCENSE.block(),
            p -> new IncenseBlock(p, () -> ModParticles.PINE_INCENSE_PARTICLE, () -> ModParticles.PINE_INCENSE_LARGE_PARTICLE),
            BlockBehaviour.Properties.of());
    public static final Block GINKGO_INCENSE = commonReg(ModBlockItemIds.GINKGO_INCENSE.block(),
            p -> new IncenseBlock(p, () -> ModParticles.GINKGO_INCENSE_PARTICLE, () -> ModParticles.GINKGO_INCENSE_LARGE_PARTICLE),
            BlockBehaviour.Properties.of());
    public static final Block SPORE_INCENSE = commonReg(ModBlockItemIds.SPORE_INCENSE.block(),
            p -> new IncenseBlock(p, () -> ModParticles.SPORE_INCENSE_PARTICLE, () -> ParticleTypes.SPORE_BLOSSOM_AIR),
            BlockBehaviour.Properties.of());
    public static final Block CATNIP_INCENSE = commonReg(ModBlockItemIds.CATNIP_INCENSE.block(),
            p -> new IncenseBlock(p, () -> ModParticles.CATNIP_INCENSE_PARTICLE, () -> ModParticles.CATNIP_INCENSE_LARGE_PARTICLE),
            BlockBehaviour.Properties.of());
    public static final Block SNOW_INCENSE = commonReg(ModBlockItemIds.SNOW_INCENSE.block(),
            p -> new IncenseBlock(p, () -> ModParticles.SNOW_INCENSE_PARTICLE, () -> ModParticles.SNOW_INCENSE_LARGE_PARTICLE),
            BlockBehaviour.Properties.of());
    public static final Block BUTTERFLY_INCENSE = commonReg(ModBlockItemIds.BUTTERFLY_INCENSE.block(),
            p -> new IncenseBlock(p, () -> ModParticles.BUTTERFLY_INCENSE_PARTICLE, () -> ModParticles.BUTTERFLY_INCENSE_LARGE_PARTICLE),
            BlockBehaviour.Properties.of());
    public static final Block FIREFLY_INCENSE = commonReg(ModBlockItemIds.FIREFLY_INCENSE.block(),
            p -> new IncenseBlock(p, () -> ModParticles.FIREFLY_INCENSE_PARTICLE, () -> ModParticles.FIREFLY_INCENSE_LARGE_PARTICLE, -0.67, 5.33),
            BlockBehaviour.Properties.of());

    // 龙头
    public static final Block TAP = commonReg(ModBlockItemIds.TAP.block(), TapBlock::new, BlockBehaviour.Properties.of());

    // 燃烧瓶
    public static final Block MOLOTOV = commonReg(ModBlockItemIds.MOLOTOV.block(), MolotovBlock::new, BlockBehaviour.Properties.of());

    // 酒柜
    public static final Block BAR_CABINET = commonReg(ModBlockItemIds.BAR_CABINET.block(), BarCabinetBlock::new, BlockBehaviour.Properties.of());
    public static final Block GLASS_BAR_CABINET = commonReg(ModBlockItemIds.GLASS_BAR_CABINET.block(), BarCabinetBlock::new, BlockBehaviour.Properties.of());
    public static final Block CELLAR_CABINET = commonReg(ModBlockItemIds.CELLAR_CABINET.block(), CellarCabinetBlock::new, BlockBehaviour.Properties.of());
    public static final Block TILTED_RACK = commonReg(ModBlockItemIds.TILTED_RACK.block(), TiltedRackBlock::new, BlockBehaviour.Properties.of());
    public static final Block CIRCULAR_RACK = commonReg(ModBlockItemIds.CIRCULAR_RACK.block(), CircularRackBlock::new, BlockBehaviour.Properties.of());
    public static final Block HOLDER = commonReg(ModBlockItemIds.HOLDER.block(), HolderBlock::new, BlockBehaviour.Properties.of());
    public static final Block SHAKER = commonReg(ModBlockItemIds.SHAKER.block(), ShakerBlock::new, BlockBehaviour.Properties.of());

    // 酒桶
    public static final Block BARREL = commonReg(ModBlockItemIds.BARREL.block(), BarrelBlock::new, BlockBehaviour.Properties.of());

    // 果盆
    public static final Block PRESSING_TUB = commonReg(ModBlockItemIds.PRESSING_TUB.block(), PressingTubBlock::new, BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .instrument(NoteBlockInstrument.GUITAR)
            .strength(0.8F)
            .sound(BlockSoundSets.WOOD)
            .ignitedByLava());

    // 酒
    public static final Block WINE = wineReg(ModFoods.ModWines.WINE, ModBlockItemIds.WINE.block());
    public static final Block CHAMPAGNE = wineReg(ModFoods.ModWines.CHAMPAGNE, ModBlockItemIds.CHAMPAGNE.block());
    public static final Block VODKA = wineReg(ModFoods.ModWines.VODKA, ModBlockItemIds.VODKA.block());
    public static final Block BRANDY = wineReg(ModFoods.ModWines.BRANDY, ModBlockItemIds.BRANDY.block());
    public static final Block CARIGNAN = wineReg(ModFoods.ModWines.CARIGNAN, ModBlockItemIds.CARIGNAN.block());
    public static final Block SAKURA_WINE = wineReg(ModFoods.ModWines.SAKURA_WINE, ModBlockItemIds.SAKURA_WINE.block());
    public static final Block PLUM_WINE = wineReg(ModFoods.ModWines.PLUM_WINE, ModBlockItemIds.PLUM_WINE.block());
    public static final Block WHISKEY = wineReg(ModFoods.ModWines.WHISKEY, ModBlockItemIds.WHISKEY.block());
    public static final Block ICE_WINE = wineReg(ModFoods.ModWines.ICE_WINE, ModBlockItemIds.ICE_WINE.block());
    public static final Block VINEGAR = wineReg(ModFoods.ModWines.VINEGAR, ModBlockItemIds.VINEGAR.block());
    public static final Block POLARIS_SWEET_WHITE = wineReg(ModFoods.ModWines.POLARIS_SWEET_WHITE, ModBlockItemIds.POLARIS_SWEET_WHITE.block());
    public static final Block HONEY_WINE = wineReg(ModFoods.ModWines.HONEY_WINE, ModBlockItemIds.HONEY_WINE.block());
    public static final Block RED_QUEEN = wineReg(ModFoods.ModWines.RED_QUEEN, ModBlockItemIds.RED_QUEEN.block());
    public static final Block MINERS_STAR = wineReg(ModFoods.ModWines.MINERS_STAR, ModBlockItemIds.MINERS_STAR.block());
    public static final Block RUM = wineReg(ModFoods.ModWines.RUM, ModBlockItemIds.RUM.block());
    public static final Block RIESLING_DRY_WHITE = wineReg(ModFoods.ModWines.RIESLING_DRY_WHITE, ModBlockItemIds.RIESLING_DRY_WHITE.block());
    public static final Block SUNSET_GLOW = wineReg(ModFoods.ModWines.SUNSET_GLOW, ModBlockItemIds.SUNSET_GLOW.block());
    public static final Block MADAME_SHEXIANG = wineReg(ModFoods.ModWines.MADAME_SHEXIANG, ModBlockItemIds.MADAME_SHEXIANG.block());
    public static final Block SWEET_BERRY_WINE = wineReg(ModFoods.ModWines.SWEET_BERRY_WINE, ModBlockItemIds.SWEET_BERRY_WINE.block());
    public static final Block SHERRY = wineReg(ModFoods.ModWines.SHERRY, ModBlockItemIds.SHERRY.block());
    public static final Block MOTHER_SNOW = wineReg(ModFoods.ModWines.MOTHER_SNOW, ModBlockItemIds.MOTHER_SNOW.block());
    public static final Block LUMINOUS_BRIDE = wineReg(ModFoods.ModWines.LUMINOUS_BRIDE, ModBlockItemIds.LUMINOUS_BRIDE.block());
    public static final Block GLOWFLOWER_BREW = wineReg(ModFoods.ModWines.GLOWFLOWER_BREW, ModBlockItemIds.GLOWFLOWER_BREW.block());
    public static final Block SAUVIGNON_BLANC_DRY_WHITE = wineReg(ModFoods.ModWines.SAUVIGNON_BLANC_DRY_WHITE, ModBlockItemIds.SAUVIGNON_BLANC_DRY_WHITE.block());
    public static final Block WATERMELON_JUICE = wineReg(ModFoods.ModWines.WATERMELON_JUICE, ModBlockItemIds.WATERMELON_JUICE.block());


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
    public static final BlockEntityType<BarrelBlockEntity> BARREL_BE = FabricBlockEntityTypeBuilder.create(BarrelBlockEntity::new, BARREL).build();
    public static final BlockEntityType<PressingTubBlockEntity> PRESSING_TUB_BE = FabricBlockEntityTypeBuilder.create(PressingTubBlockEntity::new, PRESSING_TUB).build();
    public static final BlockEntityType<BarCabinetBlockEntity> BAR_CABINET_BE = FabricBlockEntityTypeBuilder.create(BarCabinetBlockEntity::new,
            BAR_CABINET,
            GLASS_BAR_CABINET
    ).build();
    public static final BlockEntityType<CellarCabinetBlockEntity> CELLAR_CABINET_BE = FabricBlockEntityTypeBuilder.create(CellarCabinetBlockEntity::new, CELLAR_CABINET).build();
    public static final BlockEntityType<TiltedRackBlockEntity> TILTED_RACK_BE = FabricBlockEntityTypeBuilder.create(TiltedRackBlockEntity::new, TILTED_RACK).build();
    public static final BlockEntityType<GlasswareHolderBlockEntity> GLASSWARE_HOLDER_BE = FabricBlockEntityTypeBuilder.create(GlasswareHolderBlockEntity::new, GLASSWARE_HOLDER).build();
    public static final BlockEntityType<CircularRackBlockEntity> CIRCULAR_RACK_BE = FabricBlockEntityTypeBuilder.create(CircularRackBlockEntity::new, CIRCULAR_RACK).build();
    public static final BlockEntityType<HolderBlockEntity> HOLDER_BE = FabricBlockEntityTypeBuilder.create(HolderBlockEntity::new, HOLDER).build();
    public static final BlockEntityType<ShakerBlockEntity> SHAKER_BE = FabricBlockEntityTypeBuilder.create(ShakerBlockEntity::new, SHAKER).build();
    public static final BlockEntityType<PotionBottleBlockEntity> POTION_BOTTLE_BE = FabricBlockEntityTypeBuilder.create(PotionBottleBlockEntity::new, POTION_BOTTLE).build();
    public static final BlockEntityType<IncenseBlockEntity> INCENSE_BE = FabricBlockEntityTypeBuilder.create(IncenseBlockEntity::new,
            SAKURA_INCENSE,
            PINE_INCENSE,
            GINKGO_INCENSE,
            SPORE_INCENSE,
            CATNIP_INCENSE,
            SNOW_INCENSE,
            BUTTERFLY_INCENSE,
            FIREFLY_INCENSE
    ).build();
    public static final BlockEntityType<SignatureCocktailBlockEntity> SIGNATURE_COCKTAIL_BE = FabricBlockEntityTypeBuilder.create(SignatureCocktailBlockEntity::new, SIGNATURE_COCKTAIL).build();
    public static final BlockEntityType<TapBlockEntity> TAP_BE = FabricBlockEntityTypeBuilder.create(TapBlockEntity::new, TAP).build();
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
        // Block entities
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "barrel"), BARREL_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "pressing_tub"), PRESSING_TUB_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "bar_cabinet"), BAR_CABINET_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "cellar_cabinet"), CELLAR_CABINET_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "tilted_rack"), TILTED_RACK_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "glassware_holder"), GLASSWARE_HOLDER_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "circular_rack"), CIRCULAR_RACK_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "holder"), HOLDER_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "shaker"), SHAKER_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "potion_bottle"), POTION_BOTTLE_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "incense"), INCENSE_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "signature_cocktail"), SIGNATURE_COCKTAIL_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "drink"), DRINK_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "tap"), TAP_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "chalkboard"), CHALKBOARD_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "sandwich_board"), SANDWICH_BOARD_BE);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "bar_stool"), BAR_STOOL_BE);
    }
    public static Block register(ResourceKey<Block> resourceKey, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties) {
        Block block = function.apply(properties.setId(resourceKey));
        return Registry.register(BuiltInRegistries.BLOCK, resourceKey, block);
    }
    private static Block commonReg(ResourceKey<Block> resourceKey, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties) {
        return register(resourceKey, function, properties);
    }

    private static Block sofaReg(ResourceKey<Block> resourceKey) {
        return commonReg(resourceKey, SofaBlock::new, BlockBehaviour.Properties.of());
    }

    private static Block stringLightReg(ResourceKey<Block> resourceKey, @Nullable Item color) {
        return commonReg(resourceKey, properties -> new StringLightsBlock(properties, color), BlockBehaviour.Properties.of());
    }

    private static Block barStoolReg(ResourceKey<Block> resourceKey, DyeColor color) {
        return commonReg(resourceKey, p -> new BarStoolBlock(p, color), BlockBehaviour.Properties.of());
    }

    private static Block sandwichBoardReg(ResourceKey<Block> resourceKey, Item... items) {
        return commonReg(resourceKey, p -> new SandwichBoardBlock(p, items), BlockBehaviour.Properties.of());
    }

    private static Block paintingReg(ResourceKey<Block> resourceKey) {
        return commonReg(resourceKey, PaintingBlock::new, BlockBehaviour.Properties.of());
    }

    private static Block paintingRegSpecial(ResourceKey<Block> resourceKey) {
        return commonReg(resourceKey, PaintingBlock::new, BlockBehaviour.Properties.of().overrideDescription("block.kaleidoscope_tavern.painting"));
    }

    private static Block wineReg(Block block, ResourceKey<Block> resourceKey) {
        return Registry.register(BuiltInRegistries.BLOCK, resourceKey, block);
    }
}
