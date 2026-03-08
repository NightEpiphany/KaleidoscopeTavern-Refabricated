package com.github.ysbbbbbb.kaleidoscopetavern.compat.create.ponder.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.create.ponder.scene.BarrelScenes;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.create.ponder.scene.GrapevineScenes;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.create.ponder.scene.PressingTubScenes;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class TavernPonderScreen {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(new ResourceLocation(KaleidoscopeTavern.MOD_ID, "pressing_tub"))
                .addStoryBoard("pressing_tub/introduction", PressingTubScenes::introduction)
                .addStoryBoard("pressing_tub/introduction", PressingTubScenes::pressing)
                .addStoryBoard("pressing_tub/introduction", PressingTubScenes::variant);

        helper.forComponents(new ResourceLocation(KaleidoscopeTavern.MOD_ID, "barrel"))
                .addStoryBoard("barrel/introduction", BarrelScenes::introduction);

        helper.forComponents(new ResourceLocation(KaleidoscopeTavern.MOD_ID, "grapevine"))
                .addStoryBoard("grapevine/wild_generation", GrapevineScenes::wildGeneration)
                .addStoryBoard("grapevine/planting", GrapevineScenes::planting);
    }
}
