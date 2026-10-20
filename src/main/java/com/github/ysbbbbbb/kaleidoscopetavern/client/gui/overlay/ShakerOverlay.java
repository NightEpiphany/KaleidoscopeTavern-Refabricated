package com.github.ysbbbbbb.kaleidoscopetavern.client.gui.overlay;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.ShakerBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.ItemStackHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.pipeline.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ShakerOverlay implements HudElement {
    private static final Identifier IMG = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "textures/gui/shaker.png");
    private static final Identifier ICON = Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "gui/rhombus");
    private static final int IMG_WIDTH = 256;
    private static final int IMG_HEIGHT = 256;

    public static void register() {
        HudElementRegistry.addFirst(Identifier.fromNamespaceAndPath(KaleidoscopeTavern.MOD_ID, "shaker_overlay"), new ShakerOverlay());
    }

    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor guiGraphics) {
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
        float partialTick = minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        renderShakerBlockTips(guiGraphics, screenWidth, screenHeight, minecraft, player);
        renderShakerProgress(guiGraphics, screenWidth, screenHeight, player, partialTick);
    }

    private static void renderShakerProgress(GuiGraphicsExtractor guiGraphics, int screenWidth, int screenHeight, LocalPlayer player, float partialTick) {
        int remainingTicks = player.getUseItemRemainingTicks();
        if (remainingTicks > 0 && player.getUseItem().is(ModItems.SHAKER)) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMG, screenWidth / 2 - 91, screenHeight / 2 + 32,
                    0, 0, 181, 17, IMG_WIDTH, IMG_HEIGHT);

            int offsetX = (int) Math.round((player.getTicksUsingItem() + partialTick) * 1.5);
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, IMG, screenWidth / 2 - 91 + offsetX, screenHeight / 2 + 26,
                    181, 0, 11, 13, IMG_WIDTH, IMG_HEIGHT);
        }
    }

    private static void renderShakerBlockTips(GuiGraphicsExtractor guiGraphics, int screenWidth, int screenHeight, Minecraft minecraft, LocalPlayer player) {
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

            TextColor textColor = ColorUtils.ITEM_COLOR_CACHE.apply(stack.getItem());
            if (textColor == null) {
                guiGraphics.fakeItem(stack, x, y);
                guiGraphics.itemDecorations(font, stack, x, y);
            } else {
                int color = textColor.getValue() | 0xFF000000;
                renderIcon(guiGraphics, x, y + 6, color);
            }
            x += 20;
        }
    }

    private static void renderIcon(GuiGraphicsExtractor guiGraphics, int x, int y, int color) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ICON, x, y, 16, 16, color);
    }
}
