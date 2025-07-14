package com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike3;

import com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animation.RawAnimation;

import java.util.function.Supplier;

public class Bike3 extends Bike {
    // 动画文件
    protected static final RawAnimation FRONT_ANIM = RawAnimation.begin().thenLoop("animation.ModelBike3.front");
    protected static final RawAnimation BACK_ANIM = RawAnimation.begin().thenLoop("animation.ModelBike3.back");

    @Override
    protected RawAnimation getFrontAnim() {
        return FRONT_ANIM;
    }

    @Override
    protected RawAnimation getBackAnim() {
        return BACK_ANIM;
    }

    // 实体工厂
    private Bike3(EntityType<? extends Bike> type, Level level, Supplier<Item> itemSupplier) {
        super(type, level, itemSupplier);
    }
    public static EntityType.EntityFactory<Bike3> bike3Factory(Supplier<Item> itemSupplier) {
        return (type, level) -> new Bike3(type, level, itemSupplier);
    }
}
