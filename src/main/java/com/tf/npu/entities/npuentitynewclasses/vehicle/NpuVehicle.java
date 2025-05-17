package com.tf.npu.entities.npuentitynewclasses.vehicle;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

abstract public class NpuVehicle extends VehicleEntity {
    private final Supplier<Item> dropItem;

    public NpuVehicle(EntityType<? extends NpuVehicle> entityType, Level level, Supplier<Item> itemSupplier) {
        super(entityType, level);
        this.dropItem = itemSupplier;
    }

    public static boolean canVehicleCollide(Entity vehicle, Entity entity) {
        return (entity.canBeCollidedWith() || entity.isPushable()) && !vehicle.isPassengerOfSameVehicle(entity);
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
        this.setPos(x, y + 1.0D, z);
        this.xo = x;
        this.yo = y + 1.0D;
        this.zo = z;
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return canVehicleCollide(this, entity);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected @NotNull Item getDropItem() {
        return dropItem.get();
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    public ItemStack getPickResult() {
        return new ItemStack(dropItem.get());
    }

    @Override
    public @NotNull InteractionResult interactAt(Player player, @NotNull Vec3 vec3, @NotNull InteractionHand hand) {
        if (!player.isSecondaryUseActive() && this.canAddPassenger(player)) {
            if (!this.level().isClientSide) {
                return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
            } else {
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    protected boolean canAddPassenger(@NotNull Entity entity) {
        return entity instanceof Player && getPassengers().size() < getMaxPassengers();
    }

    protected abstract int getMaxPassengers();

    @Override
    public abstract void positionRider(@NotNull Entity entity, @NotNull Entity.MoveFunction moveFunction);

    protected List<Double> getPosition(double relative_X, double relative_Z) {
        double x = this.getX() - relative_Z * Mth.sin(getYRot() * Mth.DEG_TO_RAD)
                + relative_X * Mth.cos(getYRot() * Mth.DEG_TO_RAD);
        double z = this.getZ() + relative_Z * Mth.cos(getYRot() * Mth.DEG_TO_RAD)
                + relative_X * Mth.sin(getYRot() * Mth.DEG_TO_RAD);
        return List.of(x, z);
    }

}
