package com.github.ysbbbbbb.kaleidoscopetavern.client.gui.overlay;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.ShakerBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.ItemStackHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ShakerOverlay {
    private static final Identifier IMG = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "textures/gui/shaker.png");
    private static final Identifier ICON = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "gui/rhombus");

    public static void register() {
        HudRenderCallback.EVENT.register(ShakerOverlay::render);
    }


    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.gameMode == null || minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR) {
            return;
        }
        LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }
        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();
        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(false);
        // 如果指向雪克杯，提示内容
        renderShakerBlockTips(guiGraphics, screenWidth, screenHeight, minecraft, player);
        renderShakerProgress(guiGraphics, screenWidth, screenHeight, player, partialTick);
    }

    private static void renderShakerProgress(GuiGraphics guiGraphics, int screenWidth, int screenHeight, LocalPlayer player, float partialTick) {
        // 如果手持雪克杯
        int remainingTicks = player.getUseItemRemainingTicks();
        if (remainingTicks > 0 && player.getUseItem().is(ModItems.SHAKER)) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMG, screenWidth / 2 - 91, screenHeight / 2 + 32, 0.0F, 0.0F, 181, 17, 256, 256);

            // 图标移动
            int offsetX = (int) Math.round((player.getTicksUsingItem() + partialTick) * 1.5);
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMG, screenWidth / 2 - 91 + offsetX, screenHeight / 2 + 26, 181.0F, 0.0F, 11, 13, 256, 256);
        }
    }

    private static void renderShakerBlockTips(GuiGraphics guiGraphics, int screenWidth, int screenHeight, Minecraft minecraft, LocalPlayer player) {
        HitResult hitResult = minecraft.hitResult;
        if (!(hitResult instanceof BlockHitResult result)) {
            return;
        }
        Level level = player.level();
        BlockPos blockPos = result.getBlockPos();
        BlockState blockState = player.level().getBlockState(blockPos);
        if (!blockState.is(ModBlocks.SHAKER)) {
            return;
        }
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (!(blockEntity instanceof ShakerBlockEntity shaker)) {
            return;
        }

        Font font = Minecraft.getInstance().font;
        int x = screenWidth / 2 - 28;
        int y = screenHeight / 2 + 26;

        ItemStackHandler storage = shaker.getStorage();
        for (int i = 0; i < storage.getSlots(); i++) {
            ItemStack stack = storage.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }
            ChatFormatting chatFormatting = ColorUtils.ITEM_COLOR_CACHE.apply(stack.getItem());
            // 如果没有颜色匹配，渲染物品
            if (chatFormatting == ChatFormatting.RESET) {
                guiGraphics.renderFakeItem(stack, x, y);
                guiGraphics.renderItemDecorations(font, stack, x, y);
            } else {
                int color = Objects.requireNonNull(chatFormatting.getColor()) | 0xFF000000;
                renderIcon(guiGraphics, x, y + 6, color);
            }
            x = x + 20;
        }
    }

    @SuppressWarnings("deprecation")
    private static void renderIcon(GuiGraphics pGuiGraphics, int x, int y, int color) {
        TextureAtlas atlas = (TextureAtlas) Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS);
        TextureAtlasSprite sprite = atlas.getSprite(ICON);
        pGuiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x, y, 16, 16, color);
    }
}
