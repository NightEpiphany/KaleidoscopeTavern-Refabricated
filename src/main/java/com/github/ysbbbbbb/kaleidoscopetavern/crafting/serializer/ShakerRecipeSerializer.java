package com.github.ysbbbbbb.kaleidoscopetavern.crafting.serializer;

import com.github.ysbbbbbb.kaleidoscopetavern.crafting.recipe.ShakerRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderSet;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class ShakerRecipeSerializer implements RecipeSerializer<ShakerRecipe> {
    public static final int MAX_INGREDIENTS = 3;

    private static final MapCodec<ShakerRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.NON_AIR_HOLDER_SET_CODEC.listOf().optionalFieldOf("ingredients", List.of()).forGetter(
                    recipe -> recipe.ingredients().stream()
                            .filter(i -> !i.isEmpty())
                            .map(i -> (HolderSet<Item>) HolderSet.direct(i.items().toList()))
                            .toList()
            ),
            ItemStack.CODEC.fieldOf("result").forGetter(ShakerRecipe::result)
    ).apply(instance, (ingredients, result) -> new ShakerRecipe(
            ingredients.stream().filter(s -> !s.isBound() || s.size() > 0).limit(MAX_INGREDIENTS).map(Ingredient::of).collect(Collectors.toCollection(NonNullList::create)),
            result,
            Int2ObjectMaps.emptyMap()
    )));

    private static final StreamCodec<RegistryFriendlyByteBuf, ShakerRecipe> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull ShakerRecipe decode(RegistryFriendlyByteBuf buf) {
            int size = Math.min(MAX_INGREDIENTS, buf.readVarInt());
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (int i = 0; i < size; i++) {
                ingredients.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            }

            ItemStack result = ItemStack.STREAM_CODEC.decode(buf);

            Int2ObjectMap<ChatFormatting> ingredientColors = new Int2ObjectOpenHashMap<>();
            int colorSize = buf.readVarInt();
            for (int i = 0; i < colorSize; i++) {
                int index = buf.readVarInt();
                ChatFormatting formatting = ChatFormatting.getById(buf.readVarInt());
                if (formatting != null) {
                    ingredientColors.put(index, formatting);
                }
            }
            return new ShakerRecipe(ingredients, result, ingredientColors);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, ShakerRecipe recipe) {
            var nonEmpty = recipe.ingredients().stream().filter(i -> !i.isEmpty()).toList();
            buf.writeVarInt(nonEmpty.size());
            for (Ingredient ingredient : nonEmpty) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buf, recipe.result());

            Int2ObjectMap<ChatFormatting> ingredientColors = recipe.ingredientColors();
            buf.writeVarInt(ingredientColors.size());
            ingredientColors.forEach((index, formatting) -> {
                buf.writeVarInt(index);
                buf.writeVarInt(formatting.getId());
            });
        }
    };

    @Override
    public @NotNull MapCodec<ShakerRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, ShakerRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}