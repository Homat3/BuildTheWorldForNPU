package com.tf.npu.util;

import com.tf.npu.blocks.NpuBlocks;
import com.tf.npu.creativemodtab.NpuCreativeModeTabs;
import com.tf.npu.entities.NpuEntities;
import com.tf.npu.items.NpuItems;
import net.minecraftforge.eventbus.api.IEventBus;

public class Register
{
    //注册到主事件
    public static void register(IEventBus modEventBus)
    {
        Logger.LOGGER.info("Register mod things to mod event bus");
        // Register the Deferred Register to the mod event bus so block get registered
        NpuBlocks.BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        NpuItems.ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        NpuCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so entities get registered
        NpuEntities.ENTITY_TYPES.register(modEventBus);
    }

}
