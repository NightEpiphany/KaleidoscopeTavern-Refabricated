package com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.category;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.ReiUtil;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.BarrelRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomDisplay;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReiBarrelRecipeCategory implements DisplayCategory<ReiBarrelRecipeCategory.BarrelRecipeDisplay> {
    public static final CategoryIdentifier<BarrelRecipeDisplay> ID = CategoryIdentifier.of(KaleidoscopeTavern.MOD_ID, "plugin/barrel");

    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_tavern.barrel");
    private static final Identifier BG = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "textures/gui/jei/barrel.png");

    public static final int WIDTH = 180;
    public static final int HEIGHT = 150;

    @Override
    public CategoryIdentifier<? extends BarrelRecipeDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public List<Widget> setupDisplay(BarrelRecipeDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        int startX = bounds.x;
        int startY = bounds.y;

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createTexturedWidget(BG, startX, startY, 0, 0, WIDTH, HEIGHT));

        var inputs = display.getInputEntries();
        for (int i = 0; i < inputs.size(); i++) {
            EntryIngredient ingredient = inputs.get(i);
            if (i == 0) {
                widgets.add(Widgets.createSlot(new Point(startX + 10, startY + 9))
                        .entries(ingredient)
                        .markInput());
            } else {
                int offsetX = (i - 1) * 18;
                widgets.add(Widgets.createSlot(new Point(startX + 30 + offsetX, startY + 9))
                        .entries(ingredient)
                        .markInput());
            }
        }


        if (!display.carrier.isEmpty()) {
            widgets.add(Widgets.createSlot(new Point(startX + 84, startY + 117))
                    .entries(display.carrier)
                    .disableBackground()
                    .markInput());
        }

        widgets.add(Widgets.createSlot(new Point(startX + 152, startY + 86))
                .entries(display.getOutputEntries().getFirst())
                .disableBackground()
                .markOutput());

        return widgets;
    }

    @Override
    public int getDisplayWidth(BarrelRecipeDisplay display) {
        return WIDTH;
    }

    @Override
    public int getDisplayHeight() {
        return HEIGHT;
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModItems.BARREL);
    }

    public static void registerCategories(CategoryRegistry registry) {
        registry.add(new ReiBarrelRecipeCategory());
        registry.addWorkstations(ReiBarrelRecipeCategory.ID, ReiUtil.ofItem(ModItems.BARREL));
    }

    public static void registerDisplays(DisplayRegistry registry) {
        ReiUtil.INSTANCE.barrelRecipes().forEach(holder -> {
            var recipe = holder.value();
            List<EntryIngredient> inputs = Lists.newArrayList();
            inputs.addFirst(ReiUtil.ofItemStack(new ItemStack(recipe.fluid().getBucket(), 4)));
            recipe.ingredients().stream().map(i -> ReiUtil.ofIngredient(i, 16)).forEach(inputs::add);
            EntryIngredient carrier = holder.value().carrier().isEmpty() ? EntryIngredient.empty() : ReiUtil.ofIngredient(holder.value().carrier());
            List<EntryIngredient> outputs = List.of(ReiUtil.ofItemStack(recipe.result().copyWithCount(16)));

            registry.add(new BarrelRecipeDisplay(holder.id().identifier(), inputs, outputs, carrier, holder.value().unitTime()));
        });
    }

    public static class BarrelRecipeDisplay extends BasicDisplay {

        public final EntryIngredient carrier;
        public final int unitTime;

        public static final DisplaySerializer<BarrelRecipeDisplay> SERIALIZER = DisplaySerializer.of(
                RecordCodecBuilder.mapCodec(inst -> inst.group(
                        Identifier.CODEC.fieldOf("location").forGetter(r -> r.getDisplayLocation().orElse(Identifier.withDefaultNamespace("air"))),
                        EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(BarrelRecipeDisplay::getInputEntries),
                        EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(BarrelRecipeDisplay::getOutputEntries),
                        EntryIngredient.codec().fieldOf("carrier").forGetter(BarrelRecipeDisplay::getCarrier),
                        Codec.INT.fieldOf("unitTime").forGetter(BarrelRecipeDisplay::getUnitTime)
                ).apply(inst, BarrelRecipeDisplay::new)),
                StreamCodec.composite(
                        Identifier.STREAM_CODEC, r -> r.getDisplayLocation().orElse(Identifier.withDefaultNamespace("air")),
                        EntryIngredient.streamCodec().apply(ByteBufCodecs.list()), BarrelRecipeDisplay::getInputEntries,
                        EntryIngredient.streamCodec().apply(ByteBufCodecs.list()), BarrelRecipeDisplay::getOutputEntries,
                        EntryIngredient.streamCodec(), BarrelRecipeDisplay::getCarrier,
                        ByteBufCodecs.INT,
                        i -> i.unitTime,
                        BarrelRecipeDisplay::new
                ));

        public BarrelRecipeDisplay(Identifier location, List<EntryIngredient> inputs, List<EntryIngredient> outputs, EntryIngredient carrier, int unitTime) {
            super(inputs, outputs, Optional.of(location));
            this.carrier = carrier;
            this.unitTime = unitTime;
        }

        public BarrelRecipeDisplay(RecipeHolder<BarrelRecipe> holder) {
            this(holder.id().identifier(), ReiUtil.ofIngredients(holder.value().ingredients(), holder.value().fluid()), ReiUtil.ofItemStacks(holder.value().result()), ReiUtil.ofIngredient(holder.value().carrier()), holder.value().unitTime());
        }

        public EntryIngredient getCarrier() {
            return carrier;
        }

        public int getUnitTime() {
            return unitTime;
        }

        @Override
        public CategoryIdentifier<?> getCategoryIdentifier() {
            return ID;
        }

        @Override
        public @Nullable DisplaySerializer<? extends Display> getSerializer() {
            return SERIALIZER;
        }
    }
}
