package com.tf.npu.entities;

import com.tf.npu.util.Reference;
import net.neoforged.fml.common.EventBusSubscriber;


@EventBusSubscriber(modid = Reference.MODID)
public class NpuEntitySubscriber {
    @net.neoforged.bus.api.SubscribeEvent
    public static void registerEntityAttributes(net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent event) {
        NpuEntities.registerAttributes(event);
    }
}
