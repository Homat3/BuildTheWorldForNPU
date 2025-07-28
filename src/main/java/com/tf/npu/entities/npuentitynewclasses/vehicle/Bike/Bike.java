package com.tf.npu.entities.npuentitynewclasses.vehicle.Bike;

import com.tf.npu.entities.npuentitynewclasses.vehicle.NpuVehicle;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Supplier;

abstract public class Bike extends NpuVehicle implements GeoEntity {
    // 动画数据
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    // 构造函数
    protected Bike(EntityType<? extends Bike> type, Level level, Supplier<Item> itemSupplier) {
        super(type, level, itemSupplier);
    }
    // 动画控制器
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("Move", 5,
                (animTest) -> {
                    float theta = (this.getYRot() - 90.0F) * (float) (Math.PI / 180.0);
                    float currentParallelSpeed = (float) (this.getDeltaMovement().z * Mth.cos(theta) - this.getDeltaMovement().x * Mth.sin(theta));
                    return animTest.isMoving() ? (
                            currentParallelSpeed < 0.0F ? animTest.setAndContinue(getFrontAnim()) : animTest.setAndContinue(getBackAnim())
                    ) : PlayState.STOP;
                })
        );
    }
    abstract protected RawAnimation getFrontAnim();
    abstract protected RawAnimation getBackAnim();
    // 动画实例
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
    // 属性
    @Override
    public boolean isVehicle() {
        return !getPassengers().isEmpty();
    }
    @Override
    public boolean canRiderInteract() {
        return true;
    }
    // 移动
    @Override
    public void tick() {
        LivingEntity passenger = this.getControllingPassenger();
        super.tick();

        if (this.isVehicle() && passenger instanceof Player) {
            float zInput = passenger.zza;

            if (zInput != 0.0F) zInput = zInput > 0.0F ? -1.0F : 1.0F;
            this.setYRot(passenger.getYRot() - 90.0F);
            float theta = (this.getYRot() - 90.0F) * (float) (Math.PI / 180.0);
            float currentVerticalSpeed = (float) (this.getDeltaMovement().z * Mth.sin(theta) + this.getDeltaMovement().x * Mth.cos(theta));

            this.setDeltaMovement(this.getDeltaMovement().add(
                    -Mth.sin(theta) * zInput * 0.01F - currentVerticalSpeed * 0.5F * Mth.cos(theta),
                    0.0,
                    Mth.cos(theta) * zInput * 0.01F - currentVerticalSpeed * 0.5F * Mth.sin(theta))
            );
        } else {
            this.setDeltaMovement(this.getDeltaMovement().add(
                    this.getDeltaMovement().x * (-0.05F),
                    0.0,
                    this.getDeltaMovement().z * (-0.05F))
            );
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
    }
    @Override
    public void positionRider(@NotNull Entity entity, @NotNull Entity.MoveFunction moveFunction) {
        if (entity instanceof LivingEntity) {
            List<Double> xz = getPosition(0, 0);
            moveFunction.accept(entity, xz.get(0), this.getY() + 0.5, xz.get(1));
        }
    }

    @Override
    protected int getMaxPassengers() {
        return 1;
    }
}
