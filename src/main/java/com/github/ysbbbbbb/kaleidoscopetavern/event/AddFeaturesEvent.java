package com.github.ysbbbbbb.kaleidoscopetavern.event;

import com.github.ysbbbbbb.kaleidoscopetavern.worldgen.WildGrapevineDecorator;
import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;

public class AddFeaturesEvent {
    public static void addFeatures() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> applyWildGrapevineDecorators(server.registryAccess()
                .lookupOrThrow(Registries.CONFIGURED_FEATURE)));
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, manager, success) -> {
            if (!success) {
                return;
            }
            applyWildGrapevineDecorators(server.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE));
        });
    }

    private static void applyWildGrapevineDecorators(HolderLookup.RegistryLookup<ConfiguredFeature<?, ?>> registryLookup) {
        addWildGrapevineTreeDeco(registryLookup, TreeFeatures.SUPER_BIRCH_BEES_0002, 0.002f, 3, 3);
        addWildGrapevineTreeDeco(registryLookup, TreeFeatures.SUPER_BIRCH_BEES, 1f, 1, 3);

        addWildGrapevineTreeDeco(registryLookup, TreeFeatures.OAK_BEES_0002_LEAF_LITTER, 0.002f, 3, 3);
        addWildGrapevineTreeDeco(registryLookup, TreeFeatures.OAK_BEES_002, 0.02f, 2, 3);
        addWildGrapevineTreeDeco(registryLookup, TreeFeatures.OAK_BEES_005, 0.05f, 1, 3);

        addWildGrapevineTreeDeco(registryLookup, TreeFeatures.BIRCH_BEES_0002, 0.002f, 3, 3);
        addWildGrapevineTreeDeco(registryLookup, TreeFeatures.BIRCH_BEES_002, 0.02f, 2, 3);
        addWildGrapevineTreeDeco(registryLookup, TreeFeatures.BIRCH_BEES_005, 0.05f, 1, 3);

        // 高大橡树会生成较多的葡萄藤
        addWildGrapevineTreeDeco(registryLookup, TreeFeatures.FANCY_OAK_BEES_0002_LEAF_LITTER, 0.02f, 5, 7);
        addWildGrapevineTreeDeco(registryLookup, TreeFeatures.FANCY_OAK_BEES_002, 0.2f, 5, 7);
        addWildGrapevineTreeDeco(registryLookup, TreeFeatures.FANCY_OAK_BEES_005, 0.5f, 5, 7);
        addWildGrapevineTreeDeco(registryLookup, TreeFeatures.FANCY_OAK_BEES, 1f, 5, 7);

        // 樱花树下不生成葡萄藤了，毕竟它们的树叶颜色不太搭
    }

    private static void addWildGrapevineTreeDeco(
            HolderLookup.RegistryLookup<ConfiguredFeature<?, ?>> registryLookup,
            ResourceKey<ConfiguredFeature<?, ?>> id,
            float probability,
            int maxVineCount,
            int vineChainLength
    ) {
        var holder = registryLookup.get(id).orElse(null);
        if (holder == null) {
            return;
        }
        ConfiguredFeature<?, ?> configuredFeature = holder.value();
        FeatureConfiguration config = configuredFeature.config();
        if (config instanceof TreeConfiguration treeConfiguration) {
            if (treeConfiguration.decorators.stream().anyMatch(decorator -> decorator instanceof WildGrapevineDecorator)) {
                return;
            }
            // 因为原 list 是 ImmutableList，所以只能复制一份新的 list 出来添加装饰器
            treeConfiguration.decorators = ImmutableList.<TreeDecorator>builder()
                    .addAll(treeConfiguration.decorators)
                    .add(new WildGrapevineDecorator(probability, maxVineCount, vineChainLength))
                    .build();
        }
    }
}
