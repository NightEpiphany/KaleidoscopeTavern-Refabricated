package com.github.ysbbbbbb.kaleidoscopetavern.compat.rei;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.category.ReiBarrelRecipeCategory;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.category.ReiPressingTubRecipeCategory;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.BarrelRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomDisplay;
import net.minecraft.resources.Identifier;

import java.util.Collections;
import java.util.Optional;

public class ModREICommonPlugin implements REICommonPlugin {

    private static final Identifier BARREL_ID = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "plugin/barrel");
    private static final Identifier PRESSING_TUB_ID = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "plugin/pressing_tub");

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registerIfNeeded(registry);
    }

    private static void registerIfNeeded(DisplaySerializerRegistry registry) {
        if (!registry.isRegistered(ReiBarrelRecipeCategory.BarrelRecipeDisplay.SERIALIZER)) {
            registry.register(BARREL_ID, ReiBarrelRecipeCategory.BarrelRecipeDisplay.SERIALIZER);
        }
        if (!registry.isRegistered(ReiPressingTubRecipeCategory.PressingTubRecipeDisplay.SERIALIZER)) {
            registry.register(PRESSING_TUB_ID, ReiPressingTubRecipeCategory.PressingTubRecipeDisplay.SERIALIZER);
        }
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        registerIfNeeded(DisplaySerializerRegistry.getInstance());
        registry.beginRecipeFiller(PressingTubRecipe.class).filterType(ModRecipes.PRESSING_TUB_RECIPE).fill(ReiPressingTubRecipeCategory.PressingTubRecipeDisplay::new);
        registry.beginRecipeFiller(BarrelRecipe.class).filterType(ModRecipes.BARREL_RECIPE).fill(ReiBarrelRecipeCategory.BarrelRecipeDisplay::new);
    }
}
