package com.tf.npu.entities;

import com.tf.npu.entities.npuentitynewclasses.GoldenChicken.GoldenChicken;
import com.tf.npu.util.Reference;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


//这里用于新动物实体生成时的事件
//载具实体无需
//每个新动物实体都应该在这里有生成事件注册

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NpuEntitySubscriber {
    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        //event.put(NpuEntities.EXAMPLE.get(), EXAMPLE.registerAttributes().build());
        event.put(NpuEntities.GOLDEN_CHICKEN.get(), GoldenChicken.registerAttributes().build());
    }
}
