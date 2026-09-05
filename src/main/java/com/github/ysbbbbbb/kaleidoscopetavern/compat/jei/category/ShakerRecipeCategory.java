package com.github.ysbbbbbb.kaleidoscopetavern.compat.jei.category;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.ShakerRecipe;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.google.common.collect.Lists;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeHolderType;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class ShakerRecipeCategory implements IRecipeCategory<RecipeHolder<ShakerRecipe>> {
    public static final IRecipeHolderType<ShakerRecipe> TYPE = IRecipeType.create(ModRecipes.SHAKER_RECIPE);

    private static final Identifier BG = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "textures/gui/jei/shaker.png");
    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_tavern.shaker");

    public static final int WIDTH = 150;
    public static final int HEIGHT = 80;

    private final IDrawable bgDraw;
    private final IDrawable iconDraw;

    public ShakerRecipeCategory(IGuiHelper guiHelper) {
        this.bgDraw = guiHelper.createDrawable(BG, 0, 0, WIDTH, HEIGHT);
        this.iconDraw = guiHelper.createDrawableItemLike(ModItems.SHAKER);
    }

    public static List<RecipeHolder<ShakerRecipe>> getRecipes() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return List.of();
        }
        List<RecipeHolder<ShakerRecipe>> recipes = Lists.newArrayList();
        recipes.addAll(level.recipeAccess().getSynchronizedRecipes().getAllOfType(ModRecipes.SHAKER_RECIPE));
        return recipes;
    }

    @Override
    public void draw(RecipeHolder<ShakerRecipe> holder, @NonNull IRecipeSlotsView recipeSlotsView, @NonNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.bgDraw.draw(guiGraphics);

        // 如果对应的配方是颜色配方，那么渲染一个色块（x=69 起、宽 5，与贴图方框内部完全重合）
        holder.value().ingredientColors().forEach((index, color) -> {
            int x = 69;
            int y = 14 + 18 * index;
            int rgba = 0xFF000000 | color.getColor();
            guiGraphics.fill(x, y, x + 5, y + 16, rgba);
        });
    }

    @Override
    public void setRecipe(@NonNull IRecipeLayoutBuilder builder, RecipeHolder<ShakerRecipe> holder, @NonNull IFocusGroup focuses) {
        ShakerRecipe recipe = holder.value();
        int offsetY = 0;
        for (Ingredient input : recipe.getIngredients()) {
            if (input.isEmpty()) {
                continue;
            }
            List<ItemStack> list = input.items().map(s -> s.value().getDefaultInstance()).toList();
            builder.addSlot(RecipeIngredientRole.INPUT, 52, 14 + offsetY)
                    .addIngredients(VanillaTypes.ITEM_STACK, list);
            offsetY += 18;
        }

        ItemStack outputStack = recipe.result();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 112, 36)
                .add(outputStack);
    }

    @Override
    public @NonNull IRecipeType<RecipeHolder<ShakerRecipe>> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return TITLE;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    @Nullable
    public IDrawable getIcon() {
        return iconDraw;
    }
}
