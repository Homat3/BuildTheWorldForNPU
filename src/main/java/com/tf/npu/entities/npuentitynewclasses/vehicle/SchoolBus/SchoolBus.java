package com.tf.npu.entities.npuentitynewclasses.vehicle.SchoolBus;

import com.tf.npu.entities.npuentitynewclasses.vehicle.NpuVehicle;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class SchoolBus extends NpuVehicle implements GeoEntity {
    // 动画文件
    protected static final RawAnimation MOVE_ANIM = RawAnimation.begin().thenLoop("animation.ModelSchoolBus.move");
    // 碰撞箱
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    // 最大乘客数
    private int maxPassenger = 15;

    // 实体工厂
    private SchoolBus(EntityType<? extends SchoolBus> type, Level level, Supplier<Item> itemSupplier) {
        super(type, level, itemSupplier);
    }

    public static EntityType.EntityFactory<SchoolBus> schoolBusFactory(Supplier<Item> itemSupplier) {
        return (type, level) -> new SchoolBus(type, level, itemSupplier);
    }

    // 动画控制器
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("Moving", 5,
                (animTest) -> animTest.isMoving() ? animTest.setAndContinue(MOVE_ANIM) : PlayState.STOP)
        );
    }

    // 动画实例
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    // 数据保存
    @Override
    protected void readAdditionalSaveData(@NotNull ValueInput input) {
        this.maxPassenger = input.getInt("MaxPassengers").isPresent() ? input.getInt("MaxPassengers").get() : 0;
    }

    @Override
    protected void addAdditionalSaveData(@NotNull ValueOutput output) {
        output.putInt("MaxPassengers", this.maxPassenger);
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

    @Override
    protected int getMaxPassengers() {
        return maxPassenger;
    }

    @Override
    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity var2 = this.getFirstPassenger();
        LivingEntity res;
        if (var2 instanceof LivingEntity entity) {
            res = entity;
        } else {
            res = null;
        }
        return res;
    }

    // 移动
    @Override
    public void tick() {
        if (this.isAlive()) {
            LivingEntity passenger = this.getControllingPassenger();
            if (this.isVehicle() && passenger instanceof Player) {
                float zInput = 0.0F;
                float xInput = 0.0F;
                float theta = this.getYRot() * (float) (Math.PI / 180.0);
                float currentParallelSpeed = (float) (this.getDeltaMovement().z * Mth.cos(theta) - this.getDeltaMovement().x * Mth.sin(theta));
                float currentVerticalSpeed = (float) (this.getDeltaMovement().z * Mth.sin(theta) + this.getDeltaMovement().x * Mth.cos(theta));

                zInput = passenger.zza;
                xInput = -0.04F <= currentParallelSpeed && currentParallelSpeed <= 0.04F ? 0.0F : passenger.xxa;

                if (zInput != 0.0F) zInput = zInput > 0.0F ? 1.0F : -1.0F;
                float temp = 0.0F;
                if (currentParallelSpeed != 0.0F) temp = currentParallelSpeed > 0.0F ? 1.0F : -1.0F;
                if (xInput != 0.0F) xInput = xInput > 0.0F ? -temp : temp;

                this.setYRot(this.getYRot() + xInput * 0.8F);
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
    }

    @Override
    public void positionRider(@NotNull Entity entity, @NotNull Entity.MoveFunction moveFunction) {
        if (entity instanceof LivingEntity passenger) {
            List<Double> xz = getPosition(getPassengers().indexOf(passenger) % 2 == 0 ? 1 : -1, 6.0 - ((double) (getPassengers().indexOf(passenger) + 1) / 2) * 25 / 16.0);
            moveFunction.accept(entity, xz.get(0), this.getY() + 1.2, xz.get(1));
        }
    }

}