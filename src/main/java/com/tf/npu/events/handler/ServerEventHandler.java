package com.tf.npu.events.handler;

import com.tf.npu.util.Reference;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Reference.MODID, value = Dist.DEDICATED_SERVER)
public class ServerEventHandler {
}