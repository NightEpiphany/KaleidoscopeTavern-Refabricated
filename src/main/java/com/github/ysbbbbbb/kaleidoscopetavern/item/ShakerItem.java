package com.github.ysbbbbbb.kaleidoscopetavern.item;

import com.github.ysbbbbbb.kaleidoscopetavern.blockentity.mixology.SignatureCocktailBlockEntity;
import com.github.ysbbbbbb.kaleidoscopetavern.crafting.container.SimpleInput;
import com.github.ysbbbbbb.kaleidoscopetavern.datamap.data.DrinkEffectData;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModDataComponents;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopetavern.init.ModSounds;
import com.github.ysbbbbbb.kaleidoscopetavern.util.CocktailEffectHelper;
import com.github.ysbbbbbb.kaleidoscopetavern.util.ColorUtils;
import com.github.ysbbbbbb.kaleidoscopetavern.util.neo.ItemStackHandler;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static com.github.ysbbbbbb.kaleidoscopetavern.block.mixology.GlasswareBlock.ROTATION;

public class ShakerItem extends BlockItem {
    private static final int STORAGE_SIZE = 3;

    public ShakerItem(Properties properties) {
        super(ModBlocks.SHAKER, properties.stacksTo(1).useBlockDescriptionPrefix());
    }

    @Deprecated
    public ShakerItem() {
        this(new Properties());
    }

    public static ItemStackHandler getStorage(ItemStack stack) {
        ItemStackHandler handler = new ItemStackHandler(STORAGE_SIZE);
        List<ItemStack> contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                .itemCopies()
                .toList();
        for (int i = 0; i < Math.min(handler.getSlots(), contents.size()); i++) {
            handler.setStackInSlot(i, contents.get(i));
        }
        return handler;
    }

    public static void setStorage(ItemStack stack, ItemStackHandler handler) {
        List<ItemStack> items = Lists.newArrayList();
        for (int i = 0; i < handler.getSlots(); i++) {
            items.add(handler.getStackInSlot(i));
        }
        stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
    }

    public static boolean hasStorage(ItemStack stack) {
        return stack.has(DataComponents.CONTAINER);
    }

    public static ItemStack getResult(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.SHAKER_RESULT, Result.EMPTY).stack();
    }

    public static void setResult(ItemStack stack, ItemStack result) {
        stack.set(ModDataComponents.SHAKER_RESULT, new Result(result.copy()));
    }

    public static boolean hasResult(ItemStack stack) {
        return stack.has(ModDataComponents.SHAKER_RESULT) && !getResult(stack).isEmpty();
    }

    public static void removeAll(ItemStack stack) {
        stack.remove(ModDataComponents.SHAKER_RESULT);
        stack.remove(DataComponents.CONTAINER);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();

        // 如果有产物，并且点击的是空鸡尾酒杯，尝试倒出
        BlockState rawState = level.getBlockState(pos);
        if (hasResult(stack) && rawState.is(ModBlocks.EMPTY_GLASSWARE)) {
            pourResult(level, stack, pos, rawState);
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    /**
     * 将雪克杯中的调酒产物倒入空酒杯方块。
     * 放置对应鸡尾酒方块，若为特调鸡尾酒则计算合成效果和混合颜色。
     */
    private void pourResult(Level level, ItemStack stack, BlockPos pos, BlockState rawState) {
        ItemStack result = getResult(stack);
        int particleColor = 0xFFFFFF;

        // 替换成鸡尾酒方块
        if (result.getItem() instanceof BlockItem blockItem) {
            BlockState blockState = blockItem.getBlock().defaultBlockState();
            if (blockState.hasProperty(ROTATION)) {
                blockState = blockState.setValue(ROTATION, rawState.getValue(ROTATION));
            }
            level.setBlockAndUpdate(pos, blockState);
        }
        // 如果是特调鸡尾酒，需要计算效果和颜色
        if (level.getBlockEntity(pos) instanceof SignatureCocktailBlockEntity ordinary) {
            ItemStackHandler storage = getStorage(stack);
            CocktailEffectHelper.CollectedData data = CocktailEffectHelper.collectFromStorage(storage);
            List<DrinkEffectData.Entry> mergedEffects = CocktailEffectHelper.mergeEffects(data.effects());
            int finalColor = ColorUtils.mixColors(data.colors());
            particleColor = finalColor;

            ordinary.setColor(finalColor);
            ordinary.setEffects(mergedEffects);
            ordinary.refresh();
        }
        // 清除雪克杯数据
        removeAll(stack);

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, particleColor),
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    20, 0.1, 0.1, 0.1, 0.5
            );
            serverLevel.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public @NotNull InteractionResult use(@NonNull Level level, Player player, @NonNull InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        // 如果已经有结果了，不能再摇了
        // 物品数量检查
        if (hasResult(itemInHand) || !hasStorageItem(itemInHand)) {
            return InteractionResult.PASS;
        }

        // 检查是否三瓶全满
        ItemStackHandler storage = getStorage(itemInHand);
        for (int i = 0; i < storage.getSlots(); i++) {
            ItemStack stack = storage.getStackInSlot(i);
            if (!stack.isEmpty()) {
                continue;
            }
            if (player instanceof ServerPlayer serverPlayer) {
                Component message = Component.translatable("message.kaleidoscope_tavern.shaker.amount_too_low");
                serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(message));
            }
            return InteractionResult.PASS;
        }

        // 然后才能摇
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    private boolean hasStorageItem(ItemStack itemInHand) {
        if (!hasStorage(itemInHand)) {
            return false;
        }
        ItemStackHandler storage = getStorage(itemInHand);
        for (int i = 0; i < storage.getSlots(); i++) {
            if (!storage.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onUseTick(@NonNull Level level, @NonNull LivingEntity entity, @NonNull ItemStack stack, int remainingUseDuration) {
        if (remainingUseDuration % 10 == 0) {
            entity.playSound(ModSounds.SHAKER_SHAKING,
                    level.getRandom().nextFloat() * 0.2F + 0.75F,
                    level.getRandom().nextFloat() * 0.2F + 0.8F
            );
        }
        // 超出时长，强制中断
        if (entity.getTicksUsingItem() > 110) {
            entity.releaseUsingItem();
        }
    }

    /**
     * < 19 未开始
     * < 69 迷之鸡尾酒
     * < 89 普通鸡尾酒
     * < 99 特调鸡尾酒
     * <= 110 迷之鸡尾酒
     */
    @Override
    public boolean releaseUsing(@NonNull ItemStack stack, @NonNull Level level, @NonNull LivingEntity entity, int timeLeft) {
        int time = this.getUseDuration(stack, entity) - timeLeft;
        if (time < 19) {
            return false;
        }
        // 调酒
        if (!level.isClientSide()) {
            if (time < 69) {
                setResult(stack, new ItemStack(ModItems.MYSTERY_COCKTAIL));
            } else if (time < 89) {
                setResult(stack, new ItemStack(ModItems.SIGNATURE_COCKTAIL));
            } else if (time < 99) {
                handRecipe(level, stack);
            } else {
                setResult(stack, new ItemStack(ModItems.MYSTERY_COCKTAIL));
            }
        }
        entity.playSound(ModSounds.SHAKER_END);
        return true;
    }

    private void handRecipe(Level level, ItemStack stack) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        ItemStackHandler handler = getStorage(stack);
        List<ItemStack> stacks = Lists.newArrayList();
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack ingredient = handler.getStackInSlot(i);
            if (!ingredient.isEmpty()) {
                stacks.add(ingredient);
            }
        }

        SimpleInput container = new SimpleInput(stacks);
        var manager = serverLevel.recipeAccess();
        manager.getRecipeFor(ModRecipes.SHAKER_RECIPE, container, level).ifPresentOrElse(
                recipe -> setResult(stack, recipe.value().assemble(container)),
                () -> setResult(stack, new ItemStack(ModItems.SIGNATURE_COCKTAIL))
        );
    }

    @Override
    public int getUseDuration(@NonNull ItemStack stack, @NonNull LivingEntity entity) {
        return 72000;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay display,
                                @NotNull Consumer<Component> tooltip, @NotNull TooltipFlag flag) {
        if (hasResult(stack)) {
            ItemStack result = getResult(stack);
            if (result.getHoverName() instanceof MutableComponent component) {
                tooltip.accept(Component.literal("▶ ")
                        .withStyle(ChatFormatting.GRAY)
                        .append(component.withStyle(ChatFormatting.GRAY)));
            }
        } else if (hasStorage(stack)) {
            ItemStackHandler storage = getStorage(stack);
            for (int i = 0; i < storage.getSlots(); i++) {
                ItemStack ingredient = storage.getStackInSlot(i);
                if (ingredient.isEmpty() || !(ingredient.getHoverName() instanceof MutableComponent component)) {
                    continue;
                }
                TextColor textColor = ColorUtils.ITEM_COLOR_CACHE.apply(ingredient.getItem());
                tooltip.accept(Component.literal("▶ ")
                        .withStyle(ChatFormatting.GRAY)
                        .append(component.withColor(textColor)));
            }
        }
    }

    public record Result(ItemStack stack) {
        public static final Codec<Result> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ItemStack.OPTIONAL_CODEC.fieldOf("stack").forGetter(Result::stack)
        ).apply(instance, Result::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Result> STREAM_CODEC = StreamCodec.composite(
                ItemStack.OPTIONAL_STREAM_CODEC, Result::stack, Result::new
        );

        public static final Result EMPTY = new Result(ItemStack.EMPTY);
    }
}
