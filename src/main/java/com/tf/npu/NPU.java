package com.tf.npu;

import com.tf.npu.creativemodtab.CreativeModeTab;
import com.tf.npu.entities.NpuEntities;
import com.tf.npu.entities.npuentitynewclasses.GoldenChicken.GoldenChickenRenderer;
import com.tf.npu.entities.npuentitynewclasses.vehicle.SchoolBus.SchoolBusRenderer;
import com.tf.npu.util.Logger;
import com.tf.npu.util.Reference;
import com.tf.npu.util.RegisterObjects;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Reference.MODID)
public class NPU {
    public NPU(FMLJavaModLoadingContext context) {
        final IEventBus modEventBus = context.getModEventBus();
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so new things of the mod get registered
        RegisterObjects.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // 将物品注册到创造模式物品栏
        modEventBus.addListener(this::addCreative);
        // 将模组渲染方式注册到模组实体
        modEventBus.addListener(this::registerRenderers);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        Logger.LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            Logger.LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        Logger.LOGGER.info("{}{}", Config.magicNumberIntroduction, Config.magicNumber);

        Config.items.forEach((item) -> Logger.LOGGER.info("ITEM >> {}", item.toString()));
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
        Logger.LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            Logger.LOGGER.info("HELLO FROM CLIENT SETUP");
            Logger.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
