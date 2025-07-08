package com.tf.npu;

import com.mojang.logging.LogUtils;
import com.tf.npu.creativemodtab.CreativeModeTab;
import com.tf.npu.entities.NpuEntities;
import com.tf.npu.entities.npuentitynewclasses.GoldenChicken.GoldenChickenRenderer;
import com.tf.npu.entities.npuentitynewclasses.vehicle.SchoolBus.SchoolBusRenderer;
import com.tf.npu.util.Reference;
import com.tf.npu.util.RegisterObjects;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Reference.MODID)
public class NPU {
    public static final org.slf4j.Logger LOGGER = LogUtils.getLogger();

    public NPU(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        // Register the Deferred Register to the mod event bus so new things of the mod get registered
        RegisterObjects.register(modBusGroup);

        // 将物品注册到创造模式物品栏
        BuildCreativeModeTabContentsEvent.getBus(modBusGroup).addListener(NPU::addCreative);
        // 将模组渲染方式注册到模组实体
        EntityRenderersEvent.RegisterRenderers.getBus(modBusGroup).addListener(NPU::registerRenderers);
    }


    //将物品注册到创造模式物品栏
    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTab.addCreative(event);
    }
    //将模组渲染方式注册到模组实体
    //新的实体方块和新的实体都需要自己的 Renderer 并把它们加到这里
    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers register) {
        register.registerEntityRenderer(NpuEntities.GOLDEN_CHICKEN.get(), GoldenChickenRenderer::new);
        register.registerEntityRenderer(NpuEntities.SCHOOL_BUS.get(), SchoolBusRenderer::new);
    }
}
