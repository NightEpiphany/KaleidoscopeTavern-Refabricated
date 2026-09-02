package com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.category;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.ReiUtil;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.ShakerRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReiShakerRecipeCategory implements DisplayCategory<ReiShakerRecipeCategory.ShakerRecipeDisplay> {
    public static final CategoryIdentifier<ShakerRecipeDisplay> ID = CategoryIdentifier.of(KaleidoscopeTavern.MOD_ID, "plugin/shaker");

    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_tavern.shaker");
    private static final Identifier BG = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "textures/gui/jei/shaker.png");

    public static final int WIDTH = 150;
    public static final int HEIGHT = 80;

    @Override
    public CategoryIdentifier<? extends ShakerRecipeDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public List<Widget> setupDisplay(ShakerRecipeDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        int startX = bounds.x;
        int startY = bounds.y;

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createTexturedWidget(BG, startX, startY, 0, 0, WIDTH, HEIGHT));

        var inputs = display.getInputEntries();
        int offsetY = 0;
        for (EntryIngredient input : inputs) {
            widgets.add(Widgets.createSlot(new Point(startX + 52, startY + 14 + offsetY))
                    .entries(input)
                    .markInput());
            offsetY += 18;
        }

        widgets.add(Widgets.createSlot(new Point(startX + 112, startY + 36))
                .entries(display.getOutputEntries().getFirst())
                .backgroundEnabled(false)
                .markOutput());

        // 绘制颜色指示块
        if (display.recipe != null) {
            display.recipe.value().ingredientColors().forEach((index, color) -> {
                int x = startX + 69;
                int y = startY + 14 + 18 * index;
                int rgba = 0xFF000000 | color.getColor();
                widgets.add(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, v) ->
                        guiGraphics.fill(x, y, x + 5, y + 16, rgba)));
            });
        }

        return widgets;
    }

    @Override
    public int getDisplayWidth(ShakerRecipeDisplay display) {
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
        return EntryStacks.of(ModItems.SHAKER);
    }

    public static void registerCategories(CategoryRegistry registry) {
        registry.add(new ReiShakerRecipeCategory());
        registry.addWorkstations(ReiShakerRecipeCategory.ID, ReiUtil.ofItem(ModItems.SHAKER));
    }

    public static class ShakerRecipeDisplay extends BasicDisplay {
        public final RecipeHolder<ShakerRecipe> recipe;

        public static final DisplaySerializer<ShakerRecipeDisplay> SERIALIZER = DisplaySerializer.of(
                RecordCodecBuilder.mapCodec(inst -> inst.group(
                        Identifier.CODEC.fieldOf("location").forGetter(r -> r.getDisplayLocation().orElse(Identifier.withDefaultNamespace("air"))),
                        EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(ShakerRecipeDisplay::getInputEntries),
                        EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(ShakerRecipeDisplay::getOutputEntries)
                ).apply(inst, (location, inputs, outputs) ->
                        new ShakerRecipeDisplay(null, inputs, outputs, Optional.of(location)))),
                StreamCodec.composite(
                        Identifier.STREAM_CODEC, r -> r.getDisplayLocation().orElse(Identifier.withDefaultNamespace("air")),
                        EntryIngredient.streamCodec().apply(ByteBufCodecs.list()), ShakerRecipeDisplay::getInputEntries,
                        EntryIngredient.streamCodec().apply(ByteBufCodecs.list()), ShakerRecipeDisplay::getOutputEntries,
                        (location, inputs, outputs) -> new ShakerRecipeDisplay(null, inputs, outputs, Optional.of(location))
                ));

        public ShakerRecipeDisplay(@Nullable RecipeHolder<ShakerRecipe> holder) {
            this(holder,
                    holder != null ? holder.value().getIngredients().stream()
                            .filter(i -> !i.isEmpty())
                            .map(ReiUtil::ofIngredient)
                            .toList() : List.of(),
                    holder != null ? List.of(ReiUtil.ofItemStack(holder.value().result())) : List.of(),
                    holder != null ? Optional.of(holder.id().identifier()) : Optional.empty());
        }

        public ShakerRecipeDisplay(@Nullable RecipeHolder<ShakerRecipe> holder, List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
            super(inputs, outputs, location);
            this.recipe = holder;
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