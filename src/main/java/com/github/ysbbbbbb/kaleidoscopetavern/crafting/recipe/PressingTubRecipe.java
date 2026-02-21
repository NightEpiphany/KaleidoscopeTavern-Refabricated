package com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.StringUtils;

public class PressingTubRecipe extends SingleItemRecipe {
    /**
     * 取出物品时的容器，默认是铁桶
     */
    private final Ingredient carrier;
    /**
     * 压榨时渲染的液体贴图，默认是水的贴图
     */
    private final ResourceLocation liquidTexture;

    public PressingTubRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result,
                             Ingredient carrier, ResourceLocation liquidTexture) {
        super(ModRecipes.PRESSING_TUB_RECIPE, ModRecipes.PRESSING_TUB_SERIALIZER.get(),
                id, StringUtils.EMPTY, ingredient, result);
        this.carrier = carrier;
        this.liquidTexture = liquidTexture;
    }

    @Override
    public boolean matches(Container inv, Level level) {
        return this.ingredient.test(inv.getItem(0));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public ItemStack getResult() {
        return this.result;
    }

    public Ingredient getCarrier() {
        return carrier;
    }

    public ResourceLocation getLiquidTexture() {
        return liquidTexture;
    }
}
