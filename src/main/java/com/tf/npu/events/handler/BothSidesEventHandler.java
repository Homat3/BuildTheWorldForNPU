package com.tf.npu.events.handler;

import com.tf.npu.entities.NpuEntities;
import com.tf.npu.util.Reference;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;


@EventBusSubscriber(modid = Reference.MODID)
public class BothSidesEventHandler {
    @SubscribeEvent
    public static void registerEntityAttributes(net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent event) {
        NpuEntities.registerAttributes(event);
    }
}
