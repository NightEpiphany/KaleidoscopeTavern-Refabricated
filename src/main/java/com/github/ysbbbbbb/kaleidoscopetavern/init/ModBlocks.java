package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.deco.SofaBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, KaleidoscopeTavern.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, KaleidoscopeTavern.MOD_ID);

    // 装饰部分
    // 沙发
    public static RegistryObject<Block> WHITE_SOFA = BLOCKS.register("white_sofa", SofaBlock::new);
    public static RegistryObject<Block> LIGHT_GRAY_SOFA = BLOCKS.register("light_gray_sofa", SofaBlock::new);
    public static RegistryObject<Block> GRAY_SOFA = BLOCKS.register("gray_sofa", SofaBlock::new);
    public static RegistryObject<Block> BLACK_SOFA = BLOCKS.register("black_sofa", SofaBlock::new);
    public static RegistryObject<Block> BROWN_SOFA = BLOCKS.register("brown_sofa", SofaBlock::new);
    public static RegistryObject<Block> RED_SOFA = BLOCKS.register("red_sofa", SofaBlock::new);
    public static RegistryObject<Block> ORANGE_SOFA = BLOCKS.register("orange_sofa", SofaBlock::new);
    public static RegistryObject<Block> YELLOW_SOFA = BLOCKS.register("yellow_sofa", SofaBlock::new);
    public static RegistryObject<Block> LIME_SOFA = BLOCKS.register("lime_sofa", SofaBlock::new);
    public static RegistryObject<Block> GREEN_SOFA = BLOCKS.register("green_sofa", SofaBlock::new);
    public static RegistryObject<Block> CYAN_SOFA = BLOCKS.register("cyan_sofa", SofaBlock::new);
    public static RegistryObject<Block> LIGHT_BLUE_SOFA = BLOCKS.register("light_blue_sofa", SofaBlock::new);
    public static RegistryObject<Block> BLUE_SOFA = BLOCKS.register("blue_sofa", SofaBlock::new);
    public static RegistryObject<Block> PURPLE_SOFA = BLOCKS.register("purple_sofa", SofaBlock::new);
    public static RegistryObject<Block> MAGENTA_SOFA = BLOCKS.register("magenta_sofa", SofaBlock::new);
    public static RegistryObject<Block> PINK_SOFA = BLOCKS.register("pink_sofa", SofaBlock::new);
}