package com.github.ysbbbbbb.kaleidoscopetavern.compat.create.ponder.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.create.ponder.scenes.BarrelScenes;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.create.ponder.scenes.GrapevineScenes;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.create.ponder.scenes.PressingTubScenes;
import com.zurrtum.create.client.ponder.api.registration.PonderSceneRegistrationHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class ModPonderScreen {
    public static void register(PonderSceneRegistrationHelper<Identifier> helper) {
        helper.forComponents(Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "pressing_tub"))
                .addStoryBoard("pressing_tub/introduction", PressingTubScenes::introduction)
                .addStoryBoard("pressing_tub/introduction", PressingTubScenes::pressing)
                .addStoryBoard("pressing_tub/introduction", PressingTubScenes::variant);

        helper.forComponents(Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "barrel"))
                .addStoryBoard("barrel/introduction", BarrelScenes::introduction);

        helper.forComponents(Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "grapevine"))
                .addStoryBoard("grapevine/wild_generation", GrapevineScenes::wildGeneration)
                .addStoryBoard("grapevine/planting", GrapevineScenes::planting);
    }
}
