package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.DrinkBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.init.registery.ids.ModBlockItemIds;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;

public interface ModFoods {
    FoodProperties GRAPE = (new FoodProperties.Builder())
            .nutrition(2).saturationModifier(0.5F)
            .alwaysEdible().build();

    interface ModWines {
        // 酒
        Block WINE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.WINE.block()).build();

        Block CHAMPAGNE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.CHAMPAGNE.block()).build();

        Block VODKA = DrinkBlock.create().maxCount(4).shapes(
                Block.box(4, 0, 4, 12, 15, 12),
                Block.box(0, 0, 4, 16, 15, 12),
                Shapes.or(
                        Block.box(0, 0, 8, 16, 15, 16),
                        Block.box(4, 0, 0, 12, 15, 16)
                ),
                Block.box(0, 0, 0, 16, 16, 16)
        ).setId(ModBlockItemIds.VODKA.block()).build();

        Block BRANDY = DrinkBlock.create().maxCount(3).shapes(
                Block.box(3, 0, 6, 13, 13, 10),
                Block.box(1, 0, 3, 15, 12, 12),
                Block.box(1, 0, 1, 16, 12, 13)
        ).setId(ModBlockItemIds.BRANDY.block()).build();

        Block CARIGNAN = DrinkBlock.create().maxCount(3).shapes(
                Block.box(3, 0, 6, 13, 13, 10),
                Block.box(1, 0, 3, 15, 12, 12),
                Block.box(1, 0, 1, 16, 12, 13)
        ).setId(ModBlockItemIds.CARIGNAN.block()).build();

        Block SAKURA_WINE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.SAKURA_WINE.block()).build();

        Block PLUM_WINE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 12, 10),
                Block.box(3, 0, 6, 13, 12, 10),
                Shapes.or(
                        Block.box(3, 0, 9, 13, 12, 13),
                        Block.box(6, 0, 3, 10, 12, 13)
                ),
                Block.box(3, 0, 3, 13, 12, 13)
        ).setId(ModBlockItemIds.PLUM_WINE.block()).build();

        Block WHISKEY = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.WHISKEY.block()).build();

        Block ICE_WINE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.ICE_WINE.block()).build();

        Block POLARIS_SWEET_WHITE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.POLARIS_SWEET_WHITE.block()).build();

        Block HONEY_WINE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.HONEY_WINE.block()).build();

        Block RED_QUEEN = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.RED_QUEEN.block()).build();

        Block MINERS_STAR = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.MINERS_STAR.block()).build();

        Block RUM = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.RUM.block()).build();

        Block RIESLING_DRY_WHITE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(4, 0, 4, 12, 15, 12),
                Block.box(0, 0, 4, 16, 15, 12),
                Shapes.or(
                        Block.box(0, 0, 8, 16, 15, 16),
                        Block.box(4, 0, 0, 12, 15, 16)
                ),
                Block.box(0, 0, 0, 16, 16, 16)
        ).setId(ModBlockItemIds.RIESLING_DRY_WHITE.block()).build();

        Block SUNSET_GLOW = DrinkBlock.create().maxCount(3).shapes(
                Block.box(3, 0, 6, 13, 13, 10),
                Block.box(1, 0, 3, 15, 12, 12),
                Block.box(1, 0, 1, 16, 12, 13)
        ).setId(ModBlockItemIds.SUNSET_GLOW.block()).build();

        Block MADAME_SHEXIANG = DrinkBlock.create().maxCount(4).shapes(
                Block.box(4, 0, 4, 12, 15, 12),
                Block.box(0, 0, 4, 16, 15, 12),
                Shapes.or(
                        Block.box(0, 0, 8, 16, 15, 16),
                        Block.box(4, 0, 0, 12, 15, 16)
                ),
                Block.box(0, 0, 0, 16, 16, 16)
        ).setId(ModBlockItemIds.MADAME_SHEXIANG.block()).build();

        Block SWEET_BERRY_WINE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(4, 0, 4, 12, 15, 12),
                Block.box(0, 0, 4, 16, 15, 12),
                Shapes.or(
                        Block.box(0, 0, 8, 16, 15, 16),
                        Block.box(4, 0, 0, 12, 15, 16)
                ),
                Block.box(0, 0, 0, 16, 16, 16)
        ).setId(ModBlockItemIds.SWEET_BERRY_WINE.block()).build();

        Block SHERRY = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.SHERRY.block()).build();

        Block MOTHER_SNOW = DrinkBlock.create().maxCount(4).shapes(
                Block.box(4, 0, 4, 12, 15, 12),
                Block.box(0, 0, 4, 16, 15, 12),
                Shapes.or(
                        Block.box(0, 0, 8, 16, 15, 16),
                        Block.box(4, 0, 0, 12, 15, 16)
                ),
                Block.box(0, 0, 0, 16, 16, 16)
        ).setId(ModBlockItemIds.MOTHER_SNOW.block()).build();

        Block LUMINOUS_BRIDE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.LUMINOUS_BRIDE.block()).build();

        Block GLOWFLOWER_BREW = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.GLOWFLOWER_BREW.block()).build();

        Block SAUVIGNON_BLANC_DRY_WHITE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.SAUVIGNON_BLANC_DRY_WHITE.block()).build();

        Block VINEGAR = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.VINEGAR.block()).build();

        Block WATERMELON_JUICE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId(ModBlockItemIds.WATERMELON_JUICE.block()).build();
    }
}
