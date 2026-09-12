package com.github.ysbbbbbb.kaleidoscopetavern.datagen.loottable;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.jspecify.annotations.NonNull;

import java.util.HashSet;
import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {
    public final Set<Block> knownBlocks = new HashSet<>();

    public BlockLootTables(LootTableSubProvider.Context registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        // 沙发
        dropSelf(ModBlocks.WHITE_SOFA);
        dropSelf(ModBlocks.LIGHT_GRAY_SOFA);
        dropSelf(ModBlocks.GRAY_SOFA);
        dropSelf(ModBlocks.BLACK_SOFA);
        dropSelf(ModBlocks.BROWN_SOFA);
        dropSelf(ModBlocks.RED_SOFA);
        dropSelf(ModBlocks.ORANGE_SOFA);
        dropSelf(ModBlocks.YELLOW_SOFA);
        dropSelf(ModBlocks.LIME_SOFA);
        dropSelf(ModBlocks.GREEN_SOFA);
        dropSelf(ModBlocks.CYAN_SOFA);
        dropSelf(ModBlocks.LIGHT_BLUE_SOFA);
        dropSelf(ModBlocks.BLUE_SOFA);
        dropSelf(ModBlocks.PURPLE_SOFA);
        dropSelf(ModBlocks.MAGENTA_SOFA);
        dropSelf(ModBlocks.PINK_SOFA);

        // 高脚凳
        dropSelf(ModBlocks.WHITE_BAR_STOOL);
        dropSelf(ModBlocks.LIGHT_GRAY_BAR_STOOL);
        dropSelf(ModBlocks.GRAY_BAR_STOOL);
        dropSelf(ModBlocks.BLACK_BAR_STOOL);
        dropSelf(ModBlocks.BROWN_BAR_STOOL);
        dropSelf(ModBlocks.RED_BAR_STOOL);
        dropSelf(ModBlocks.ORANGE_BAR_STOOL);
        dropSelf(ModBlocks.YELLOW_BAR_STOOL);
        dropSelf(ModBlocks.LIME_BAR_STOOL);
        dropSelf(ModBlocks.GREEN_BAR_STOOL);
        dropSelf(ModBlocks.CYAN_BAR_STOOL);
        dropSelf(ModBlocks.LIGHT_BLUE_BAR_STOOL);
        dropSelf(ModBlocks.BLUE_BAR_STOOL);
        dropSelf(ModBlocks.PURPLE_BAR_STOOL);
        dropSelf(ModBlocks.MAGENTA_BAR_STOOL);
        dropSelf(ModBlocks.PINK_BAR_STOOL);

        // 黑板
        dropSelf(ModBlocks.CHALKBOARD);
        // 桌子
        dropSelf(ModBlocks.TABLE);

        // 展板
        dropSelf(ModBlocks.BASE_SANDWICH_BOARD);
        dropSelf(ModBlocks.ALLIUM_SANDWICH_BOARD);
        dropSelf(ModBlocks.AZURE_BLUET_SANDWICH_BOARD);
        dropSelf(ModBlocks.CORNFLOWER_SANDWICH_BOARD);
        dropSelf(ModBlocks.ORCHID_SANDWICH_BOARD);
        dropSelf(ModBlocks.PEONY_SANDWICH_BOARD);
        dropSelf(ModBlocks.PINK_PETALS_SANDWICH_BOARD);
        dropSelf(ModBlocks.PITCHER_PLANT_SANDWICH_BOARD);
        dropSelf(ModBlocks.POPPY_SANDWICH_BOARD);
        dropSelf(ModBlocks.SUNFLOWER_SANDWICH_BOARD);
        dropSelf(ModBlocks.TORCHFLOWER_SANDWICH_BOARD);
        dropSelf(ModBlocks.TULIP_SANDWICH_BOARD);
        dropSelf(ModBlocks.WITHER_ROSE_SANDWICH_BOARD);

        // 彩灯
        dropSelf(ModBlocks.STRING_LIGHTS_COLORLESS);
        dropSelf(ModBlocks.STRING_LIGHTS_WHITE);
        dropSelf(ModBlocks.STRING_LIGHTS_LIGHT_GRAY);
        dropSelf(ModBlocks.STRING_LIGHTS_GRAY);
        dropSelf(ModBlocks.STRING_LIGHTS_BLACK);
        dropSelf(ModBlocks.STRING_LIGHTS_BROWN);
        dropSelf(ModBlocks.STRING_LIGHTS_RED);
        dropSelf(ModBlocks.STRING_LIGHTS_ORANGE);
        dropSelf(ModBlocks.STRING_LIGHTS_YELLOW);
        dropSelf(ModBlocks.STRING_LIGHTS_LIME);
        dropSelf(ModBlocks.STRING_LIGHTS_GREEN);
        dropSelf(ModBlocks.STRING_LIGHTS_CYAN);
        dropSelf(ModBlocks.STRING_LIGHTS_LIGHT_BLUE);
        dropSelf(ModBlocks.STRING_LIGHTS_BLUE);
        dropSelf(ModBlocks.STRING_LIGHTS_PURPLE);
        dropSelf(ModBlocks.STRING_LIGHTS_MAGENTA);
        dropSelf(ModBlocks.STRING_LIGHTS_PINK);

        // 挂画
        dropSelf(ModBlocks.YSBB_PAINTING);
        dropSelf(ModBlocks.TARTARIC_ACID_PAINTING);
        dropSelf(ModBlocks.CR019_PAINTING);
        dropSelf(ModBlocks.UNKNOWN_PAINTING);
        dropSelf(ModBlocks.MASTER_MARISA_PAINTING);
        dropSelf(ModBlocks.SON_OF_MAN_PAINTING);
        dropSelf(ModBlocks.DAVID_PAINTING);
        dropSelf(ModBlocks.GIRL_WITH_PEARL_EARRING_PAINTING);
        dropSelf(ModBlocks.STARRY_NIGHT_PAINTING);
        dropSelf(ModBlocks.VAN_GOGH_SELF_PORTRAIT_PAINTING);
        dropSelf(ModBlocks.FATHER_PAINTING);
        dropSelf(ModBlocks.GREAT_WAVE_PAINTING);
        dropSelf(ModBlocks.MONA_LISA_PAINTING);
        dropSelf(ModBlocks.MONDRIAN_PAINTING);
        dropSelf(ModBlocks.NIGHT_EPIPHANY_PAINTING);

        // 垂灯
        dropSelf(ModBlocks.BELL_PENDANT_LAMP);
        dropSelf(ModBlocks.YELLOW_PENDANT_LAMP);
        dropSelf(ModBlocks.BLUE_PENDANT_LAMP);

        // 吧台
        dropSelf(ModBlocks.BAR_COUNTER);
        // 人字梯
        dropSelf(ModBlocks.STEPLADDER);
        // 野生葡萄藤
        dropOther(ModBlocks.WILD_GRAPEVINE, ModItems.GRAPEVINE);
        dropOther(ModBlocks.WILD_GRAPEVINE_PLANT, ModItems.GRAPEVINE);
        // 藤架
        dropSelf(ModBlocks.TRELLIS);
        // 葡萄藤
        add(ModBlocks.GRAPEVINE_TRELLIS, this.createMultiItemTable(ModItems.TRELLIS, ModItems.GRAPEVINE));
        add(ModBlocks.ICE_GRAPEVINE_TRELLIS, this.createMultiItemTable(ModItems.TRELLIS, ModItems.GRAPEVINE));
        add(ModBlocks.GOLD_GRAPEVINE_TRELLIS, this.createMultiItemTable(ModItems.TRELLIS, ModItems.GRAPEVINE));
        // 葡萄
        add(ModBlocks.GRAPE_CROP, this.createGrapeItemTable(ModItems.GRAPE));
        add(ModBlocks.ICE_GRAPE_CROP, this.createGrapeItemTable(ModItems.ICE_GRAPE));
        add(ModBlocks.GOLD_GRAPE_CROP, this.createGrapeItemTable(ModItems.GOLD_GRAPE));

        // 果盆
        dropSelf(ModBlocks.PRESSING_TUB);

        // 酒杯架
        dropSelf(ModBlocks.GLASSWARE_HOLDER);

        // 空瓶子
        dropSelf(ModBlocks.EMPTY_BOTTLE);
        dropSelf(ModBlocks.EMPTY_GLASSWARE);

        // 鸡尾酒
        dropSelf(ModBlocks.MYSTERY_COCKTAIL);
        dropSelf(ModBlocks.WHITE_LADY);
        dropSelf(ModBlocks.EMERALD);
        dropSelf(ModBlocks.BRASS_HEART);
        dropSelf(ModBlocks.GODFATHER);
        dropSelf(ModBlocks.GRASSHOPPER);
        dropSelf(ModBlocks.SCREWDRIVER);
        dropSelf(ModBlocks.MOJITO);
        dropSelf(ModBlocks.ALLIUM_GARDEN);
        dropSelf(ModBlocks.DEPTH_CHARGE);
        dropSelf(ModBlocks.NETHER_SPECIAL);
        dropSelf(ModBlocks.BLOODY_MARY);
        dropSelf(ModBlocks.SCULK_SPECIAL);
        dropSelf(ModBlocks.MOLOTOV);

        // 龙头
        dropSelf(ModBlocks.TAP);
        // 酒桶
        dropSelf(ModBlocks.BARREL);
        // 酒柜
        dropSelf(ModBlocks.BAR_CABINET);
        dropSelf(ModBlocks.GLASS_BAR_CABINET);
        dropSelf(ModBlocks.CELLAR_CABINET);

        // 酒架
        dropSelf(ModBlocks.TILTED_RACK);
        dropSelf(ModBlocks.CIRCULAR_RACK);
        dropSelf(ModBlocks.HOLDER);

        // 香薰
        dropSelf(ModBlocks.SAKURA_INCENSE);
        dropSelf(ModBlocks.PINE_INCENSE);
        dropSelf(ModBlocks.GINKGO_INCENSE);
        dropSelf(ModBlocks.SPORE_INCENSE);
        dropSelf(ModBlocks.CATNIP_INCENSE);
        dropSelf(ModBlocks.SNOW_INCENSE);
        dropSelf(ModBlocks.BUTTERFLY_INCENSE);
        dropSelf(ModBlocks.FIREFLY_INCENSE);

        // 杂项瓶子
        add(ModBlocks.WATER_BOTTLE, this.createWaterBottle());
        dropOther(ModBlocks.HONEY_BOTTLE, Items.HONEY_BOTTLE);
        dropOther(ModBlocks.DRAGON_BREATH_BOTTLE, Items.DRAGON_BREATH);
        dropOther(ModBlocks.XP_BOTTLE, Items.EXPERIENCE_BOTTLE);
    }

    @Override
    public void add(@NonNull Block block, LootTable.@NonNull Builder builder) {
        this.knownBlocks.add(block);
        super.add(block, builder);
    }


    public Iterable<Block> getKnownBlocks() {
        return this.knownBlocks;
    }

    public Identifier modLoc(String name) {
        return Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, name);
    }

    public LootTable.Builder createMultiItemTable(ItemLike... items) {
        LootTable.Builder builder = LootTable.lootTable();
        for (ItemLike item : items) {
            LootPool.Builder pool = LootPool.lootPool()
                    .setRolls(ContextIntProviders.exactly(1))
                    .add(LootItem.lootTableItem(item));
            builder.withPool(this.applyExplosionCondition(item, pool));
        }
        return builder;
    }

    protected LootTable.Builder createItemWithCountTable(ItemLike item, Holder<ContextIntProvider> countProvider) {
        LootTable.Builder builder = LootTable.lootTable();
        LootPool.Builder pool = LootPool.lootPool()
                .setRolls(ContextIntProviders.exactly(1))
                .apply(SetItemCountFunction.setCount(countProvider))
                .add(LootItem.lootTableItem(item));
        builder.withPool(this.applyExplosionCondition(item, pool));
        return builder;
    }

    protected LootTable.Builder createWaterBottle() {
        LootTable.Builder builder = LootTable.lootTable();
        LootPool.Builder pool = LootPool.lootPool()
                .setRolls(ContextIntProviders.exactly(1))
                .add(LootItem.lootTableItem(Items.POTION)
                        .apply(SetPotionFunction.setPotion(Potions.WATER)));
        builder.withPool(this.applyExplosionCondition(Items.POTION, pool));
        return builder;
    }

    public LootTable.Builder createGrapeItemTable(ItemLike item) {
        LootTable.Builder builder = LootTable.lootTable();
        var countProvider = ContextIntProviders.between(1, 2);

        // 主葡萄
        LootPool.Builder pool = LootPool.lootPool()
                .apply(SetItemCountFunction.setCount(countProvider))
                .add(LootItem.lootTableItem(item));
        builder.withPool(this.applyExplosionCondition(item, pool));

        // 30% 概率额外掉落 1 个
        LootPool.Builder extraPool = LootPool.lootPool()
                .setRolls(ContextIntProviders.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(0.3f))
                .add(LootItem.lootTableItem(ModItems.GREEN_GRAPE));
        builder.withPool(this.applyExplosionCondition(item, extraPool));

        return builder;
    }
}
