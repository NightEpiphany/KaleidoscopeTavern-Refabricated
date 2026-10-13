package com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.container.BarrelRecipeContainer;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.RecipeMatcher;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

/**
 * 酒桶的配方类
 *
 * @param ingredients 酒桶输入的原料，没有数量参数，最大只允许 4 种物品输入。可以有 0 种物品输入，此时表示只使用流体酿造
 * @param fluid       酒桶输入的流体，只能有一个流体输入
 * @param carrier     酒桶的容器，可以是放在龙头下方的方块，也可以是龙头下方的掉落物
 * @param result      酒桶的输出物品，只能有一个输出
 * @param resultCount 单次酿造的输出基数
 * @param unitTime    酿造单位时间，单位为 tick。<br>
 *                    酿造会分多个阶段，每个阶段会持续 n * unitTime tick。<br>
 *                    n 为当前阶段数（从 1 开始）。例如，unitTime 为 100，则第一阶段持续 100 tick，第二阶段持续 200 tick，以此类推
 */
public record BarrelRecipe(
        NonNullList<Ingredient> ingredients,
        Fluid fluid,
        Ingredient carrier,
        Item result,
        int resultCount,
        int unitTime
) implements Recipe<BarrelRecipeContainer> {
    public static final int INGREDIENT_UNIT_COUNT = 16;

    @Override
    public boolean matches(BarrelRecipeContainer container, @NonNull Level level) {
        NonNullList<Ingredient> effectiveIngredients = this.effectiveIngredients();
        List<ItemStack> nonEmptyInputs = container.getNonEmptyItems();
        boolean emptyIngredients = effectiveIngredients.isEmpty();
        // 如果全为空
        if (emptyIngredients) {
            return container.getFluid() == this.fluid && container.itemsIsEmpty();
        }
        // 否则匹配输入的流体和物品
        return container.getFluid() == this.fluid
               && container.hasUnitCount(INGREDIENT_UNIT_COUNT)
               && RecipeMatcher.findMatches(nonEmptyInputs, effectiveIngredients) != null;
    }

    @Override
    public @NotNull ItemStack assemble(BarrelRecipeContainer container) {
        // 遍历容器，找出数量最少的输入物品数量，作为酿造结果的数量
        // 默认数量为 16，超过这个数量的输入物品不会增加输出物品的数量
        int count = 16;
        for (ItemStack itemStack : container.getItems()) {
            if (!itemStack.isEmpty()) {
                count = Math.min(count, itemStack.getCount());
            }
        }
        int baseCount = Math.max(1, this.resultCount);
        int outputCount = Math.min(this.result.getDefaultMaxStackSize(), count * baseCount);
        return new ItemStack(this.result, outputCount);
    }



    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public @NonNull String group() {
        return "wine_barrel";
    }

    @Override
    public @NonNull RecipeSerializer<? extends Recipe<BarrelRecipeContainer>> getSerializer() {
        return ModRecipes.BARREL_SERIALIZER;
    }

    @Override
    public @NonNull RecipeType<? extends Recipe<BarrelRecipeContainer>> getType() {
        return ModRecipes.BARREL_RECIPE;
    }

    @Override
    public @NonNull PlacementInfo placementInfo() {
        return PlacementInfo.create(this.effectiveIngredients());
    }

    @Override
    public @NonNull RecipeBookCategory recipeBookCategory() {
        return Registry.register(BuiltInRegistries.RECIPE_BOOK_CATEGORY, Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "barrel"), new RecipeBookCategory());
    }

    private NonNullList<Ingredient> effectiveIngredients() {
        NonNullList<Ingredient> filtered = NonNullList.create();
        for (Ingredient ingredient : this.ingredients) {
            if (!ingredient.isEmpty()) {
                filtered.add(ingredient);
            }
        }
        return filtered;
    }

    public NonNullList<Ingredient> requiredIngredients() {
        return this.effectiveIngredients();
    }

}
