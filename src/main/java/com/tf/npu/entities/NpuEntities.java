package com.tf.npu.entities;

import com.tf.npu.entities.npuentitynewclasses.GoldenChicken.GoldenChicken;
import com.tf.npu.entities.npuentitynewclasses.GoldenChicken.GoldenChickenRenderer;
import com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike1.Bike1;
import com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike1.Bike1Renderer;
import com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike2.Bike2;
import com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike2.Bike2Renderer;
import com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike3.Bike3;
import com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike3.Bike3Renderer;
import com.tf.npu.entities.npuentitynewclasses.vehicle.NpuVehicle;
import com.tf.npu.entities.npuentitynewclasses.vehicle.SchoolBus.SchoolBus;
import com.tf.npu.entities.npuentitynewclasses.vehicle.SchoolBus.SchoolBusRenderer;
import com.tf.npu.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NpuEntities {
    public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(Reference.MODID);
    public static final Map<String, Supplier<? extends EntityType<? extends Mob>>> MOB_ID_MAP = new HashMap<>(0);
    public static final Map<String, Supplier<? extends EntityType<? extends NpuVehicle>>> VEHICLE_ID_MAP = new HashMap<>(0);

    //新实体ID表
    public static final String GOLDEN_CHICKEN_ID = "golden_chicken";
    public static final String SCHOOL_BUS_ID = "school_bus";
    public static final String BIKE1_ID = "bike1";
    public static final String BIKE2_ID = "bike2";
    public static final String BIKE3_ID = "bike3";
    //注册新实体示例
    /*
    public static final RegistryObject<EntityType<YOURTYPE>> EXAMPLE_ENTITY =
            ENTITYTYPES.register(EXAMPLE_ENTITY_ID, () ->
                    EntityType.Builder.of(YOURTYPE::new, MobCategory.YOU_KNOW)                              //设置生物大类
                            .sized(Width, Height)                                                           //设置大小
                            .build(ResourceKey.create(ResourceKey.createRegistryKey(EXAMPLE_LOCATION), EXAMPLE_LOCATION)));
     */
    private static final Supplier<EntityType<GoldenChicken>> GOLDEN_CHICKEN =
            ENTITY_TYPES.register(GOLDEN_CHICKEN_ID, () ->
                    EntityType.Builder.of(GoldenChicken::new, MobCategory.CREATURE)
                            .sized(1.0F, 1.0F)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Reference.MODID, GOLDEN_CHICKEN_ID))));

    public static final Supplier<EntityType<SchoolBus>> SCHOOL_BUS =
            ENTITY_TYPES.register(SCHOOL_BUS_ID, () ->
                    EntityType.Builder.of(SchoolBus.schoolBusFactory(() -> Items.IRON_NUGGET), MobCategory.MISC)
                            .sized(6.0F, 5.0F)
                            .fireImmune()
                            .canSpawnFarFromPlayer()
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Reference.MODID, SCHOOL_BUS_ID))));

    public static final Supplier<EntityType<Bike1>> BIKE1 =
            ENTITY_TYPES.register(BIKE1_ID, () ->
                    EntityType.Builder.of(Bike1.bike1Factory(() -> Items.IRON_NUGGET), MobCategory.MISC)
                            .sized(1.0F, 1.2F)
                            .fireImmune()
                            .canSpawnFarFromPlayer()
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Reference.MODID, BIKE1_ID))));

    public static final Supplier<EntityType<Bike2>> BIKE2 =
            ENTITY_TYPES.register(BIKE2_ID, () ->
                    EntityType.Builder.of(Bike2.bike2Factory(() -> Items.IRON_NUGGET), MobCategory.MISC)
                            .sized(1.0F, 1.2F)
                            .fireImmune()
                            .canSpawnFarFromPlayer()
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Reference.MODID, BIKE2_ID))));

    public static final Supplier<EntityType<Bike3>> BIKE3 =
            ENTITY_TYPES.register(BIKE3_ID, () ->
                    EntityType.Builder.of(Bike3.bike3Factory(() -> Items.IRON_NUGGET), MobCategory.MISC)
                            .sized(1.0F, 1.2F)
                            .fireImmune()
                            .canSpawnFarFromPlayer()
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Reference.MODID, BIKE3_ID))));

    static {
        MOB_ID_MAP.put(GOLDEN_CHICKEN_ID, GOLDEN_CHICKEN);
        VEHICLE_ID_MAP.put(SCHOOL_BUS_ID, SCHOOL_BUS);
        VEHICLE_ID_MAP.put(BIKE1_ID, BIKE1);
        VEHICLE_ID_MAP.put(BIKE2_ID, BIKE2);
        VEHICLE_ID_MAP.put(BIKE3_ID, BIKE3);
    }

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(GOLDEN_CHICKEN.get(), GoldenChickenRenderer::new);
        event.registerEntityRenderer(SCHOOL_BUS.get(), SchoolBusRenderer::new);
        event.registerEntityRenderer(BIKE1.get(), Bike1Renderer::new);
        event.registerEntityRenderer(BIKE2.get(), Bike2Renderer::new);
        event.registerEntityRenderer(BIKE3.get(), Bike3Renderer::new);
    }


    //这里用于新动物实体生成时的事件
    //载具实体无需
    //每个新动物实体都应该在这里有生成事件注册
    public static void registerAttributes(EntityAttributeCreationEvent event){
        //event.put(NpuEntities.EXAMPLE.get(), EXAMPLE.registerAttributes().build());
        event.put(NpuEntities.GOLDEN_CHICKEN.get(), GoldenChicken.registerAttributes().build());
    }

}
