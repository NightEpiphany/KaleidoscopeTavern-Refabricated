package com.github.ysbbbbbb.kaleidoscopetavern.blockentity.deco;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.network.message.TextOpenS2CMessage;
import com.github.ysbbbbbb.kaleidoscopetavern.util.TextAlignment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public abstract class TextBlockEntity extends BaseBlockEntity {
    /**
     * 正在编辑这个写字板的玩家的 UUID
     */
    protected @Nullable UUID playerWhoMayEdit = null;
    /**
     * 这个写字板上的文本内容，默认为空字符串
     */
    protected String text = StringUtils.EMPTY;
    /**
     * 这个写字板上的文本颜色，默认为白色
     */
    protected DyeColor color = DyeColor.WHITE;
    /**
     * 文本对齐方式，默认为居中
     */
    protected TextAlignment textAlignment = TextAlignment.CENTER;
    /**
     * 这个写字板是否被打蜡了（无法被再次修改），默认为 false
     */
    protected boolean isWaxed = false;
    /**
     * 这个写字板是否在发光（文本会发光），默认为 false
     */
    protected boolean isGlowing = false;

    public TextBlockEntity(BlockEntityType<?> entityType, BlockPos pos, BlockState state) {
        super(entityType, pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TextBlockEntity be) {
        UUID uuid = be.getPlayerWhoMayEdit();
        if (uuid != null && be.playerIsTooFarAwayToEdit(uuid)) {
            be.setPlayerWhoMayEdit(null);
        }
    }

    public static InteractionResult onItemUse(Level level, TextBlockEntity textBlock, Player player, InteractionHand hand) {
        BlockPos pos = textBlock.getBlockPos();

        // 如果玩家距离太远了，就不允许编辑
        if (textBlock.playerIsTooFarAwayToEdit(player.getUUID())) {
            return InteractionResult.PASS;
        }

        // 如果打蜡了，就不允许编辑
        if (textBlock.isWaxed()) {
            level.playSound(null, pos, SoundEvents.WAXED_SIGN_INTERACT_FAIL, SoundSource.BLOCKS);
            return InteractionResult.PASS;
        }

        ItemStack itemInHand = player.getItemInHand(hand);

        // 染料
        if (itemInHand.getItem() instanceof DyeItem dyeItem) {
            DyeColor newColor = dyeItem.getDyeColor();
            if (newColor != textBlock.getColor()) {
                textBlock.setColor(newColor);
                textBlock.refresh();
                level.playSound(null, pos, SoundEvents.DYE_USE, SoundSource.BLOCKS);
                if (!player.isCreative()) {
                    itemInHand.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }

        // 荧光墨囊
        if (itemInHand.getItem() instanceof GlowInkSacItem) {
            if (!textBlock.isGlowing()) {
                textBlock.setGlowing(true);
                textBlock.refresh();
                level.playSound(null, pos, SoundEvents.GLOW_INK_SAC_USE, SoundSource.BLOCKS);
                if (!player.isCreative()) {
                    itemInHand.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }

        // 普通墨囊
        if (itemInHand.getItem() instanceof InkSacItem) {
            if (textBlock.isGlowing()) {
                textBlock.setGlowing(false);
                textBlock.refresh();
                level.playSound(null, pos, SoundEvents.INK_SAC_USE, SoundSource.BLOCKS);
                if (!player.isCreative()) {
                    itemInHand.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }

        // 打蜡
        if (itemInHand.getItem() instanceof HoneycombItem) {
            textBlock.setWaxed(true);
            textBlock.refresh();
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.WAX_ON,
                        pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                        10, 0.5, 0.2, 0.5, 0.1
                );
                serverLevel.playSound(null, pos, SoundEvents.HONEYCOMB_WAX_ON,
                        SoundSource.BLOCKS, 1.0F, 1.0F
                );
            }
            if (!player.isCreative()) {
                itemInHand.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }

        if (player instanceof ServerPlayer serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, new TextOpenS2CMessage(pos));
        }
        return InteractionResult.SUCCESS;
    }

    /**
     * 当前这个写字板允许的最大文本长度（字符数）
     *
     * @return 最大文本长度
     */
    public abstract int getMaxTextLength();

    @Override
    public void loadAdditional(@NonNull ValueInput valueInput) {
        super.loadAdditional(valueInput);
        this.text = valueInput.getStringOr("text", "");
        this.color = DyeColor.byId(valueInput.getIntOr("color", DyeColor.WHITE.getId()));
        this.textAlignment = TextAlignment.byId(valueInput.getIntOr("text_alignment", TextAlignment.CENTER.getId()));
        this.isWaxed = valueInput.getBooleanOr("is_waxed", false);
        this.isGlowing = valueInput.getBooleanOr("is_glowing", false);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.putString("text", this.text);
        valueOutput.putInt("color", this.color.getId());
        valueOutput.putInt("text_alignment", this.textAlignment.getId());
        valueOutput.putBoolean("is_waxed", this.isWaxed);
        valueOutput.putBoolean("is_glowing", this.isGlowing);
    }

    public boolean playerIsTooFarAwayToEdit(UUID id) {
        if (this.level == null) {
            return true;
        }
        Player player = this.level.getPlayerByUUID(id);
        if (player == null) {
            return true;
        }
        BlockPos pos = this.getBlockPos();
        return player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) > 8 * 8;
    }

    @Nullable
    public UUID getPlayerWhoMayEdit() {
        return playerWhoMayEdit;
    }

    public void setPlayerWhoMayEdit(@Nullable UUID playerWhoMayEdit) {
        this.playerWhoMayEdit = playerWhoMayEdit;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DyeColor getColor() {
        return color;
    }

    public void setColor(DyeColor color) {
        this.color = color;
    }

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(TextAlignment textAlignment) {
        this.textAlignment = textAlignment;
    }

    public boolean isWaxed() {
        return isWaxed;
    }

    public void setWaxed(boolean waxed) {
        isWaxed = waxed;
    }

    public boolean isGlowing() {
        return isGlowing;
    }

    public void setGlowing(boolean glowing) {
        isGlowing = glowing;
    }
}
