package com.tf.npu.util;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;

import static com.tf.npu.blocks.NpuBlocks.BLOCKS;
import static com.tf.npu.creativemodtab.NpuCreativeModeTabs.CREATIVE_MODE_TABS;
import static com.tf.npu.entities.NpuEntities.ENTITY_TYPES;
import static com.tf.npu.items.NpuItems.ITEMS;

public class RegisterObjects {
    public static final org.slf4j.Logger LOGGER = LogUtils.getLogger();
    public static void register(IEventBus modEventBus) {
        // 加入事件
        LOGGER.info("Register mod things to mod event bus");
        // Register the Deferred Register to the mod event bus so block get registered
        LOGGER.info("Starting dealing blocks");
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        LOGGER.info("Starting dealing items");
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        LOGGER.info("Starting dealing creative mode tabs");
        CREATIVE_MODE_TABS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so entities get registered
        ENTITY_TYPES.register(modEventBus);
    }

}
