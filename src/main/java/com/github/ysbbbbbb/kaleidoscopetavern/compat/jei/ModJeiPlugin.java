package com.github.ysbbbbbb.kaleidoscopetavern.compat.jei;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.jei.category.BarrelRecipeCategory;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.jei.category.PressingTubCategory;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    private static final Identifier UID = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "jei");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new BarrelRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new PressingTubCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(BarrelRecipeCategory.TYPE, BarrelRecipeCategory.getRecipes());
        registration.addRecipes(PressingTubCategory.TYPE, PressingTubCategory.getRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(BarrelRecipeCategory.TYPE, ModItems.BARREL);
        registration.addCraftingStation(PressingTubCategory.TYPE, ModItems.PRESSING_TUB);
    }

    public static void syncRecipes() {
        RecipeSynchronization.synchronizeRecipeSerializer(ModRecipes.BARREL_SERIALIZER);
        RecipeSynchronization.synchronizeRecipeSerializer(ModRecipes.PRESSING_TUB_SERIALIZER);
    }

    @Override
    public @NotNull Identifier getPluginUid() {
        return UID;
    }
}
