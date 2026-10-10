package com.github.ysbbbbbb.kaleidoscopetavern.compat.rrv;

import cc.cassian.rrv.api.ReliableRecipeViewerPlugin;
import cc.cassian.rrv.common.recipe.ServerRecipeManager;
import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;

public class ModRRVCommonPlugin implements ReliableRecipeViewerPlugin {
    @Override
    public void onIntegrationInitialize() {
        KaleidoscopeTavern.LOGGER.info("Registering RRV recipe synchronization");
        ServerRecipeManager.INSTANCE.synchronizeRecipeType(ModRecipes.BARREL_SERIALIZER, ModRecipes.BARREL_RECIPE);
        ServerRecipeManager.INSTANCE.synchronizeRecipeType(ModRecipes.PRESSING_TUB_SERIALIZER, ModRecipes.PRESSING_TUB_RECIPE);
        ServerRecipeManager.INSTANCE.synchronizeRecipeType(ModRecipes.SHAKER_SERIALIZER, ModRecipes.SHAKER_RECIPE);
    }
}
