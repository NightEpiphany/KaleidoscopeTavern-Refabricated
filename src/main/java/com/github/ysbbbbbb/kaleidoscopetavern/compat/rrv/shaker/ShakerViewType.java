package com.github.ysbbbbbb.kaleidoscopetavern.compat.rrv.shaker;

import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class ShakerViewType implements ReliableClientRecipeType {
    public static final ShakerViewType INSTANCE = new ShakerViewType();

    private ShakerViewType() {
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("emi.category.kaleidoscope_tavern.shaker");
    }

    @Override
    public int getDisplayWidth() {
        return 170;
    }

    @Override
    public int getDisplayHeight() {
        return 138;
    }

    @Override
    public @Nullable Identifier getGuiTexture() {
        return Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "textures/gui/eiv/shaker.png");
    }

    @Override
    public int getSlotCount() {
        return 4;
    }

    @Override
    public void placeSlots(RecipeViewMenu.SlotDefinition slotDefinition) {
        for (int i = 0; i < 3; i++) {
            slotDefinition.addItemSlot(i, 64, 43 + i * 18); // wine ingredients
        }
        slotDefinition.addItemSlot(3, 112, 64); // cocktail
    }

    @Override
    public Identifier getId() {
        return Identifier.withDefaultNamespace("shaker");
    }

    @Override
    public ItemStack getIcon() {
        return ModItems.SHAKER.getDefaultInstance();
    }
}
