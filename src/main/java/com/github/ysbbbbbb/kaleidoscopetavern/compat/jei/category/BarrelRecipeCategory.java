package com.github.ysbbbbbb.kaleidoscopetavern.compat.jei.category;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.BarrelRecipe;
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

public class BarrelRecipeCategory implements IRecipeCategory<RecipeHolder<BarrelRecipe>> {
    public static final IRecipeHolderType<BarrelRecipe> TYPE = IRecipeType.create(ModRecipes.BARREL_RECIPE);

    private static final Identifier BG = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "textures/gui/jei/barrel.png");
    private static final MutableComponent TITLE = Component.translatable("block.kaleidoscope_tavern.barrel");

    public static final int WIDTH = 180;
    public static final int HEIGHT = 150;

    private final IDrawable bgDraw;
    private final IDrawable iconDraw;

    public BarrelRecipeCategory(IGuiHelper guiHelper) {
        this.bgDraw = guiHelper.createDrawable(BG, 0, 0, WIDTH, HEIGHT);
        this.iconDraw = guiHelper.createDrawableItemLike(ModItems.BARREL);
    }

    public static List<RecipeHolder<BarrelRecipe>> getRecipes() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return List.of();
        }
        List<RecipeHolder<BarrelRecipe>> recipes = Lists.newArrayList();
        recipes.addAll(level.recipeAccess().getSynchronizedRecipes().getAllOfType(ModRecipes.BARREL_RECIPE));
        return recipes;
    }

    @Override
    public void draw(RecipeHolder<BarrelRecipe> holder, @NonNull IRecipeSlotsView recipeSlotsView, @NonNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.bgDraw.draw(guiGraphics);
    }

    @Override
    public void setRecipe(@NonNull IRecipeLayoutBuilder builder, RecipeHolder<BarrelRecipe> holder, @NonNull IFocusGroup focuses) {
        BarrelRecipe recipe = holder.value();

        int offsetX = 0;
        for (Ingredient input : recipe.requiredIngredients()) {
            List<ItemStack> list = input.items()
                    .map(s -> s.value().getDefaultInstance().copyWithCount(16))
                    .toList();
            builder.addSlot(RecipeIngredientRole.INPUT, 30 + offsetX, 9)
                    .addIngredients(VanillaTypes.ITEM_STACK, list);
            offsetX += 18;
        }

        ItemStack fluidStack = new ItemStack(recipe.fluid().getBucket(), 4);
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 9)
                .add(fluidStack);

        builder.addSlot(RecipeIngredientRole.INPUT, 84, 117)
                .add(recipe.carrier());

        ItemStack outputStack = recipe.result().copyWithCount(16);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 152, 86)
                .add(outputStack);
    }


    @Override
    public @NonNull IRecipeType<RecipeHolder<BarrelRecipe>> getRecipeType() {
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
