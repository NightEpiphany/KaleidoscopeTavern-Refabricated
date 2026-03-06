package com.github.ysbbbbbb.kaleidoscopetavern.init;

import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.DrinkBlock;
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
        ).setId("wine").build();
        Block CHAMPAGNE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId("champagne").build();
        Block VODKA = DrinkBlock.create().maxCount(4).shapes(
                Block.box(4, 0, 4, 12, 15, 12),
                Block.box(0, 0, 4, 16, 15, 12),
                Shapes.or(
                        Block.box(0, 0, 8, 16, 15, 16),
                        Block.box(4, 0, 0, 12, 15, 16)
                ),
                Block.box(0, 0, 0, 16, 16, 16)
        ).setId("vodka").build();
        Block BRANDY = DrinkBlock.create().maxCount(3).irregular().shapes(
                Block.box(3, 0, 6, 13, 13, 10),
                Block.box(1, 0, 3, 15, 12, 12),
                Block.box(1, 0, 1, 16, 12, 13)
        ).setId("brandy").build();
        Block CARIGNAN = DrinkBlock.create().maxCount(3).irregular().shapes(
                Block.box(3, 0, 6, 13, 13, 10),
                Block.box(1, 0, 3, 15, 12, 12),
                Block.box(1, 0, 1, 16, 12, 13)
        ).build();
        Block SAKURA_WINE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId("sakura_wine").build();
        Block PLUM_WINE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 12, 10),
                Block.box(3, 0, 6, 13, 12, 10),
                Shapes.or(
                        Block.box(3, 0, 9, 13, 12, 13),
                        Block.box(6, 0, 3, 10, 12, 13)
                ),
                Block.box(3, 0, 3, 13, 12, 13)
        ).setId("plum_wine").build();
        Block WHISKEY = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId("whiskey").build();
        Block ICE_WINE = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId("ice_wine").build();
        Block VINEGAR = DrinkBlock.create().maxCount(4).shapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setId("vinegar").build();
    }
}
