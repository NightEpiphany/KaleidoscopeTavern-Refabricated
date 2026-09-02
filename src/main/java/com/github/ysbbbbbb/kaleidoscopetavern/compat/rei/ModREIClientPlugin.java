package com.github.ysbbbbbb.kaleidoscopetavern.compat.rei;

import com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.category.ReiBarrelRecipeCategory;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.category.ReiPressingTubRecipeCategory;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.category.ReiShakerRecipeCategory;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;

public class ModREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        ReiBarrelRecipeCategory.registerCategories(registry);
        ReiPressingTubRecipeCategory.registerCategories(registry);
        ReiShakerRecipeCategory.registerCategories(registry);
    }
}