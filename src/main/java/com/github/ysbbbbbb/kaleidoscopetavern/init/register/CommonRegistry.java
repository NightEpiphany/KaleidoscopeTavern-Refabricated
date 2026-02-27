package com.github.ysbbbbbb.kaleidoscopetavern.init.register;

import com.github.ysbbbbbb.kaleidoscopetavern.KaleidoscopeTavern;
import com.github.ysbbbbbb.kaleidoscopetavern.block.brew.BottleBlock;
import com.github.ysbbbbbb.kaleidoscopetavern.block.dispenser.BottleBlockDispenseBehavior;
import com.github.ysbbbbbb.kaleidoscopetavern.item.BottleBlockItem;
import com.github.ysbbbbbb.kaleidoscopetavern.network.NetworkHandler;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = KaleidoscopeTavern.MOD_ID)
public class CommonRegistry {
    @SubscribeEvent
    public static void onSetupEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::init);
        event.enqueueWork(CommonRegistry::addDispenserBehavior);
    }

    private static void addDispenserBehavior() {
        // 遍历物品列表，注册所有的 Bottle Block 子类方块
        ForgeRegistries.BLOCKS.getValues().forEach(block -> {
            if (block instanceof BottleBlock) {
                if (block.asItem() instanceof BottleBlockItem blockItem) {
                    DispenserBlock.registerBehavior(blockItem, new BottleBlockDispenseBehavior());
                }
            }
        });
    }
}
