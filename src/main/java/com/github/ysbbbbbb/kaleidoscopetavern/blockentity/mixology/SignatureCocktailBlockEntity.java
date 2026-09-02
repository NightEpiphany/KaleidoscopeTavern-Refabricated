package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class SignatureCocktailBlockEntity extends BaseBlockEntity {
    private static final Codec<List<DrinkEffectData.Entry>> EFFECTS_CODEC = Codec.list(DrinkEffectData.Entry.ENTRY_CODEC);

    private List<DrinkEffectData.Entry> effects = Lists.newArrayList();
    private int color = 0x5555ff;

    public SignatureCocktailBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.SIGNATURE_COCKTAIL_BE, pos, state);
    }

    @Override
    public void loadAdditional(@NonNull ValueInput valueInput) {
        super.loadAdditional(valueInput);
        valueInput.read("effects", EFFECTS_CODEC).ifPresent(r -> this.effects = r);
        this.color = valueInput.getIntOr("color", 0x5555ff);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.store("effects", EFFECTS_CODEC, this.effects);
        valueOutput.putInt("color", this.color);
    }

    public List<DrinkEffectData.Entry> getEffects() {
        return effects;
    }

    public void setEffects(List<DrinkEffectData.Entry> effects) {
        this.effects = effects;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
