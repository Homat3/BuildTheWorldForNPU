package com.tf.npu.entities.npuentitynewclasses.vehicle;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

abstract public class NpuVehicle extends VehicleEntity {
    // 掉落物
    private final Supplier<Item> dropItem;

    // 数据保存
    @Override
    protected void readAdditionalSaveData(@NotNull ValueInput input) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull ValueOutput output) {
    }

    // 生成
    public NpuVehicle(EntityType<? extends NpuVehicle> entityType, Level level, Supplier<Item> itemSupplier) {
        super(entityType, level);
        this.dropItem = itemSupplier;
    }

    @Nullable
    public static <T extends NpuVehicle> T createNpuVehicle(
            Level level,
            double x,
            double y,
            double z,
            EntityType<T> entityType,
            EntitySpawnReason spawnReason,
            ItemStack itemStack,
            @Nullable Player player
    ) {
        T t = entityType.create(level, spawnReason);
        if (t != null) {
            t.setInitialPos(x, y, z);
            EntityType.createDefaultStackConfig(level, itemStack, player).accept(t);
        }
        return t;
    }

    public void setInitialPos(double x, double y, double z) {
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    // 相关物品
    @Override
    protected @NotNull Item getDropItem() {
        return dropItem.get();
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    public ItemStack getPickResult() {
        return new ItemStack(dropItem.get());
    }

    // 碰撞
    public static boolean canVehicleCollide(Entity entity1, Entity entity2) {
        return (entity2.canBeCollidedWith(entity1) || entity2.isPushable()) && !entity1.isPassengerOfSameVehicle(entity2);
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return canVehicleCollide(this, entity);
    }

    @Override
    public boolean canBeCollidedWith(@Nullable Entity entity) {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    // 乘坐
    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        InteractionResult interactionresult = super.interact(player, hand);
        if (interactionresult != InteractionResult.PASS) {
            return interactionresult;
        } else {
            if (player.isSecondaryUseActive() || !this.level().isClientSide && !player.startRiding(this))
                return InteractionResult.PASS;
            else {
                return InteractionResult.SUCCESS;
            }
        }
    }

    @Override
    protected boolean canAddPassenger(@NotNull Entity entity) {
        return entity instanceof Player && getPassengers().size() < getMaxPassengers();
    }

    protected abstract int getMaxPassengers();

    @Override
    public abstract void positionRider(@NotNull Entity entity, @NotNull Entity.MoveFunction moveFunction);

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity passenger) {
        List<Double> xz = getPosition(3.5F, 0.0F);
        return new Vec3(xz.get(0), this.getY(), xz.get(1));
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

    protected final List<Double> getPosition(double relative_X, double relative_Z) {
        double x = this.getX() - relative_Z * Mth.sin(getYRot() * Mth.DEG_TO_RAD)
                + relative_X * Mth.cos(getYRot() * Mth.DEG_TO_RAD);
        double z = this.getZ() + relative_Z * Mth.cos(getYRot() * Mth.DEG_TO_RAD)
                + relative_X * Mth.sin(getYRot() * Mth.DEG_TO_RAD);
        return List.of(x, z);
    }
}
