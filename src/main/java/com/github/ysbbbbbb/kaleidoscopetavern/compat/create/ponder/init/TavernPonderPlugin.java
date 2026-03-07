package com.github.ysbbbbbb.kaleidoscopetavern.compat.create.ponder.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.zurrtum.create.client.ponder.api.registration.PonderPlugin;
import com.zurrtum.create.client.ponder.api.registration.PonderSceneRegistrationHelper;
import com.zurrtum.create.client.ponder.api.registration.PonderTagRegistrationHelper;
import com.zurrtum.create.client.ponder.foundation.PonderIndex;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class TavernPonderPlugin implements PonderPlugin {
    public static void init() {
        PonderIndex.addPlugin(new TavernPonderPlugin());
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<Identifier> helper) {
        ModPonderTags.register(helper);
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<Identifier> helper) {
        ModPonderScreen.register(helper);
    }

    @Override
    public String getModId() {
        return KaleidoscopeTavern.MOD_ID;
    }
}
