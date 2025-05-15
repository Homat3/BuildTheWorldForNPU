package com.tf.npu.util;

import net.minecraftforge.eventbus.api.IEventBus;

import static com.tf.npu.blocks.NpuBlocks.BLOCKS;
import static com.tf.npu.items.NpuItems.ITEMS;
import static com.tf.npu.creativemodtab.NpuCreativeModeTabs.CREATIVE_MODE_TABS;
import static com.tf.npu.entities.NpuEntities.ENTITY_TYPES;

public class RegisterObjects {
    public static void register(IEventBus modEventBus) {
        // 加入事件
        Logger.LOGGER.info("Register mod things to mod event bus");
        // Register the Deferred Register to the mod event bus so block get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so entities get registered
        ENTITY_TYPES.register(modEventBus);
    }

}
