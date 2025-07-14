package com.tf.npu.entities;

import com.tf.npu.entities.npuentitynewclasses.GoldenChicken.GoldenChicken;
import com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike1.Bike1;
import com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike2.Bike2;
import com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike3.Bike3;
import com.tf.npu.entities.npuentitynewclasses.vehicle.NpuVehicle;
import com.tf.npu.entities.npuentitynewclasses.vehicle.SchoolBus.SchoolBus;
import com.tf.npu.util.Reference;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NpuEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Reference.MODID);
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
    private static final ResourceLocation GOLDEN_CHICKEN_LOCATION = ResourceLocation.fromNamespaceAndPath(Reference.MODID, GOLDEN_CHICKEN_ID);
    public static final RegistryObject<EntityType<GoldenChicken>> GOLDEN_CHICKEN =
            ENTITY_TYPES.register(GOLDEN_CHICKEN_ID, () ->
                    EntityType.Builder.of(GoldenChicken::new, MobCategory.CREATURE)
                            .sized(1.0F, 1.0F)
                            .build(ResourceKey.create(ResourceKey.createRegistryKey(GOLDEN_CHICKEN_LOCATION), GOLDEN_CHICKEN_LOCATION)));

    private static final ResourceLocation SCHOOL_BUS_LOCATION = ResourceLocation.fromNamespaceAndPath(Reference.MODID, SCHOOL_BUS_ID);
    public static final RegistryObject<EntityType<SchoolBus>> SCHOOL_BUS =
            ENTITY_TYPES.register(SCHOOL_BUS_ID, () ->
                    EntityType.Builder.of(SchoolBus.schoolBusFactory(() -> Items.IRON_NUGGET), MobCategory.MISC)
                            .sized(6.0F, 5.0F)
                            .fireImmune()
                            .canSpawnFarFromPlayer()
                            .build(ResourceKey.create(ResourceKey.createRegistryKey(SCHOOL_BUS_LOCATION), SCHOOL_BUS_LOCATION)));

    private static final ResourceLocation BIKE1_LOCATION = ResourceLocation.fromNamespaceAndPath(Reference.MODID, BIKE1_ID);
    public static final RegistryObject<EntityType<Bike1>> BIKE1 =
            ENTITY_TYPES.register(BIKE1_ID, () ->
                    EntityType.Builder.of(Bike1.bike1Factory(() -> Items.IRON_NUGGET), MobCategory.MISC)
                            .sized(1.0F, 1.2F)
                            .fireImmune()
                            .canSpawnFarFromPlayer()
                            .build(ResourceKey.create(ResourceKey.createRegistryKey(BIKE1_LOCATION), BIKE1_LOCATION)));

    private static final ResourceLocation BIKE2_LOCATION = ResourceLocation.fromNamespaceAndPath(Reference.MODID, BIKE2_ID);
    public static final RegistryObject<EntityType<Bike2>> BIKE2 =
            ENTITY_TYPES.register(BIKE2_ID, () ->
                    EntityType.Builder.of(Bike2.bike2Factory(() -> Items.IRON_NUGGET), MobCategory.MISC)
                            .sized(1.0F, 1.2F)
                            .fireImmune()
                            .canSpawnFarFromPlayer()
                            .build(ResourceKey.create(ResourceKey.createRegistryKey(BIKE2_LOCATION), BIKE2_LOCATION)));

    private static final ResourceLocation BIKE3_LOCATION = ResourceLocation.fromNamespaceAndPath(Reference.MODID, BIKE3_ID);
    public static final RegistryObject<EntityType<Bike3>> BIKE3 =
            ENTITY_TYPES.register(BIKE3_ID, () ->
                    EntityType.Builder.of(Bike3.bike3Factory(() -> Items.IRON_NUGGET), MobCategory.MISC)
                            .sized(1.0F, 1.2F)
                            .fireImmune()
                            .canSpawnFarFromPlayer()
                            .build(ResourceKey.create(ResourceKey.createRegistryKey(BIKE3_LOCATION), BIKE3_LOCATION)));

    static {
        MOB_ID_MAP.put(GOLDEN_CHICKEN_ID, GOLDEN_CHICKEN);
        VEHICLE_ID_MAP.put(SCHOOL_BUS_ID, SCHOOL_BUS);
        VEHICLE_ID_MAP.put(BIKE1_ID, BIKE1);
        VEHICLE_ID_MAP.put(BIKE2_ID, BIKE2);
        VEHICLE_ID_MAP.put(BIKE3_ID, BIKE3);
    }
}
