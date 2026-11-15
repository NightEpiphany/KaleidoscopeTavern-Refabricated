package com.github.ysbbbbbb.kaleidoscopetavern.compat.jei;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.jei.category.BarrelRecipeCategory;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.jei.category.PressingTubCategory;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.jei.category.ShakerRecipeCategory;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    private static final Identifier UID = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "jei");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new BarrelRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new PressingTubCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ShakerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(BarrelRecipeCategory.TYPE, BarrelRecipeCategory.getRecipes());
        registration.addRecipes(PressingTubCategory.TYPE, PressingTubCategory.getRecipes());
        registration.addRecipes(ShakerRecipeCategory.TYPE, ShakerRecipeCategory.getRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(BarrelRecipeCategory.TYPE, ModItems.BARREL);
        registration.addCraftingStation(PressingTubCategory.TYPE, ModItems.PRESSING_TUB);
        registration.addCraftingStation(ShakerRecipeCategory.TYPE, ModItems.SHAKER);
    }

    @Override
    public @NotNull Identifier getPluginUid() {
        return UID;
    }
}
