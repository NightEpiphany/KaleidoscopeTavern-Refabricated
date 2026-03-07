package com.github.ysbbbbbb.kaleidoscopetavern.datamap.resources;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.google.common.collect.Maps;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class DrinkEffectDataReloadListener extends SimpleJsonResourceReloadListener<DrinkEffectData> {
    public static final Map<Item, DrinkEffectData> INSTANCE = Maps.newHashMap();

    public DrinkEffectDataReloadListener() {
        super(DrinkEffectData.CODEC, FileToIdConverter.json("datamap/drink_effect"));
    }

    @Override
    protected void apply(Map<Identifier, DrinkEffectData> resources, @NonNull ResourceManager resourceManager, @NonNull ProfilerFiller profilerFiller) {
        INSTANCE.clear();
        for (var entry : resources.entrySet()) {
            var result = entry.getValue();
            if (result == null) continue;
            INSTANCE.put(result.item(), result);
        }
        KaleidoscopeTavern.LOGGER.info("Successfully loaded drink effect data with {} entries", INSTANCE.size());
    }
}
