package com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.crafting.container.BarrelRecipeContainer;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopetavern.util.RecipeMatcher;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

/**
 * 酒桶的配方类
 *
 * @param id          配方 ID
 * @param ingredients 酒桶输入的原料，没有数量参数，最大只允许 4 种物品输入。可以有 0 种物品输入，此时表示只使用流体酿造
 * @param fluid       酒桶输入的流体，只能有一个流体输入
 * @param carrier     酒桶的容器，必须是 ItemBlock 及其子类，且只能有一个输入
 * @param result      酒桶的输出物品，其必须是 BottleBlockItem 及其子类，只能有一个输出
 * @param unitTime    酿造单位时间，单位为 tick。<br>
 *                    酿造会分多个阶段，每个阶段会持续 n * unitTime tick。<br>
 *                    n 为当前阶段数（从 1 开始）。例如，unitTime 为 100，则第一阶段持续 100 tick，第二阶段持续 200 tick，以此类推
 */
public record BarrelRecipe(
        ResourceLocation id,
        NonNullList<Ingredient> ingredients,
        Fluid fluid,
        Ingredient carrier,
        ItemStack result,
        int unitTime
) implements Recipe<BarrelRecipeContainer> {
    @Override
    public boolean matches(BarrelRecipeContainer container, Level level) {
        boolean emptyIngredients = this.ingredients.stream().allMatch(Ingredient::isEmpty);
        // 如果全为空
        if (emptyIngredients) {
            return container.getFluid().isSame(this.fluid) && container.itemsIsEmpty();
        }
        // 否则匹配输入的流体和物品
        return container.getFluid().isSame(this.fluid)
               && RecipeMatcher.findMatches(container.items, ingredients) != null;
    }

    @Override
    public @NotNull ItemStack assemble(BarrelRecipeContainer container, RegistryAccess registryAccess) {
        return getResultItem(registryAccess).copy();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.BARREL_SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.BARREL_RECIPE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }
}
