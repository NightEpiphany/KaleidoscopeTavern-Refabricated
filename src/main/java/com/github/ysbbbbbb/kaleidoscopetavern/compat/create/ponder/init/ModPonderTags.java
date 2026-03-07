package com.github.ysbbbbbb.kaleidoscopetavern.compat.create.ponder.init;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.zurrtum.create.client.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import java.util.Objects;

public class ModPonderTags {
    public static final Identifier BREWING = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "brewing");

    public static void register(PonderTagRegistrationHelper<Identifier> helper) {
        helper.registerTag(BREWING).addToIndex()
                .item(ModItems.WINE, true, false)
                .title("")
                .description("")
                .register();

        helper.addToTag(BREWING)
                .add(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(ModItems.GRAPEVINE)))
                .add(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(ModItems.PRESSING_TUB)))
                .add(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(ModItems.BARREL)));
    }
}
