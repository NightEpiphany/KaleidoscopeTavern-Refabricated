package com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.category;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.api.blockentity.IPressingTub;
import com.github.ysbbbbbb.kaleidoscopetavern.compat.rei.ReiUtil;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.PressingTubRecipe;
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
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReiPressingTubRecipeCategory implements DisplayCategory<ReiPressingTubRecipeCategory.PressingTubRecipeDisplay> {
    public static final CategoryIdentifier<PressingTubRecipeDisplay> ID = CategoryIdentifier.of(KaleidoscopeTavern.MOD_ID, "plugin/pressing_tub");

    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_tavern.pressing_tub");
    private static final Identifier BG = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "textures/gui/jei/pressing_tub.png");

    public static final int WIDTH = 155;
    public static final int HEIGHT = 54;

    @Override
    public CategoryIdentifier<? extends PressingTubRecipeDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public List<Widget> setupDisplay(PressingTubRecipeDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        int startX = bounds.x;
        int startY = bounds.y;

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createTexturedWidget(BG, startX, startY, 0, 0, WIDTH, HEIGHT));

        widgets.add(Widgets.createSlot(new Point(startX + 32, startY + 13))
                .entries(display.getInputEntries().getFirst())
                .disableBackground()
                .markInput());

        widgets.add(Widgets.createSlot(new Point(startX + 128, startY + 18))
                .entries(display.getOutputEntries().getFirst())
                .disableBackground()
                .markOutput());

        int needPressCount = display.needPressCount;
        Component tip = Component.translatable("jei.kaleidoscope_tavern.pressing_tub.need_press_count", needPressCount);
        widgets.add(Widgets.withTranslate(Widgets.createDrawableWidget((guiGraphics, mouseX, mouseY, v) ->
                drawCenteredString(guiGraphics, tip)), startX, startY));
        return widgets;
    }

    private void drawCenteredString(GuiGraphics guiGraphics, Component text) {
        Font font = Minecraft.getInstance().font;
        FormattedCharSequence sequence = text.getVisualOrderText();
        guiGraphics.drawString(font, sequence, (WIDTH - font.width(sequence) - 5), 42, 0x555555, false);
    }

    @Override
    public int getDisplayWidth(PressingTubRecipeDisplay display) {
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
        return EntryStacks.of(ModItems.PRESSING_TUB);
    }

    public static void registerCategories(CategoryRegistry registry) {
        registry.add(new ReiPressingTubRecipeCategory());
        registry.addWorkstations(ReiPressingTubRecipeCategory.ID, ReiUtil.ofItem(ModItems.PRESSING_TUB));
    }

    public static void registerDisplays(DisplayRegistry registry) {
        ReiUtil.INSTANCE.pressingTubRecipes().forEach(holder -> {
            PressingTubRecipe recipe = holder.value();
            int needPressCount = IPressingTub.MAX_FLUID_AMOUNT / recipe.getFluidAmount();
            if (needPressCount * recipe.getFluidAmount() < IPressingTub.MAX_FLUID_AMOUNT) {
                needPressCount++;
            }
            final int finalNeedPressCount = needPressCount;
            Ingredient inputs = recipe.getIngredient();
            EntryIngredient entryStacks = ReiUtil.ofIngredient(inputs, finalNeedPressCount);
            List<EntryIngredient> outputs = List.of(ReiUtil.ofItem(recipe.getFluid().getBucket()));
            registry.add(new PressingTubRecipeDisplay(holder.id().identifier(), Collections.singletonList(entryStacks), outputs, IPressingTub.MAX_FLUID_AMOUNT / holder.value().getFluidAmount()));
        });
    }

    public static class PressingTubRecipeDisplay extends BasicDisplay {

        public final int needPressCount;

        public static final DisplaySerializer<PressingTubRecipeDisplay> SERIALIZER = DisplaySerializer.of(
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Identifier.CODEC.fieldOf("location").forGetter(r -> r.getDisplayLocation().orElse(Identifier.withDefaultNamespace("air"))),
                        EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(PressingTubRecipeDisplay::getInputEntries),
                        EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(PressingTubRecipeDisplay::getOutputEntries),
                        Codec.INT.fieldOf("needPressCount").forGetter(PressingTubRecipeDisplay::getNeedPressCount)
                ).apply(instance, PressingTubRecipeDisplay::new)),
                StreamCodec.composite(
                        Identifier.STREAM_CODEC, r -> r.getDisplayLocation().orElse(Identifier.withDefaultNamespace("air")),
                        EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
                        PressingTubRecipeDisplay::getInputEntries,
                        EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
                        PressingTubRecipeDisplay::getOutputEntries,
                        ByteBufCodecs.INT,
                        i -> i.needPressCount,
                        PressingTubRecipeDisplay::new
                ));

        public PressingTubRecipeDisplay(Identifier location, List<EntryIngredient> inputs, List<EntryIngredient> outputs, int needPressCount) {
            super(inputs, outputs, Optional.of(location));
            this.needPressCount = needPressCount;
        }

        public int getNeedPressCount() {
            return needPressCount;
        }

        public PressingTubRecipeDisplay(RecipeHolder<PressingTubRecipe> holder) {
            this(holder.id().identifier(), List.of(ReiUtil.ofIngredient(holder.value().getIngredient(), (IPressingTub.MAX_FLUID_AMOUNT / holder.value().getFluidAmount()))), List.of(ReiUtil.ofItem(holder.value().getFluid().getBucket())), (IPressingTub.MAX_FLUID_AMOUNT / holder.value().getFluidAmount()));
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
