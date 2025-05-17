package com.tf.npu;

import com.mojang.logging.LogUtils;
import com.tf.npu.creativemodtab.CreativeModeTab;
import com.tf.npu.entities.NpuEntities;
import com.tf.npu.entities.npuentitynewclasses.GoldenChicken.GoldenChickenRenderer;
import com.tf.npu.entities.npuentitynewclasses.vehicle.SchoolBus.SchoolBusRenderer;
import com.tf.npu.util.Reference;
import com.tf.npu.util.RegisterObjects;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Reference.MODID)
public class NPU {
    public static final org.slf4j.Logger LOGGER = LogUtils.getLogger();

    public NPU(FMLJavaModLoadingContext context) {
        final IEventBus modEventBus = context.getModEventBus();

        // Register the Deferred Register to the mod event bus so new things of the mod get registered
        RegisterObjects.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // 将物品注册到创造模式物品栏
        modEventBus.addListener(this::addCreative);
        // 将模组渲染方式注册到模组实体
        modEventBus.addListener(this::registerRenderers);
    }

    //将物品注册到创造模式物品栏
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTab.addCreative(event);
    }

    //将模组渲染方式注册到模组实体
    //新的实体方块和新的实体都需要自己的 Renderer 并把它们加到这里
    private void registerRenderers(EntityRenderersEvent.RegisterRenderers register) {
        register.registerEntityRenderer(NpuEntities.GOLDEN_CHICKEN.get(), GoldenChickenRenderer::new);
        register.registerEntityRenderer(NpuEntities.SCHOOL_BUS.get(), SchoolBusRenderer::new);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
