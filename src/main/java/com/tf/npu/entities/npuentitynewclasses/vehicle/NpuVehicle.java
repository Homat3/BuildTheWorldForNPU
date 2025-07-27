package com.tf.npu.entities.npuentitynewclasses.vehicle;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

abstract public class NpuVehicle extends VehicleEntity {
    private final Supplier<Item> dropItem;

    public NpuVehicle(EntityType<? extends NpuVehicle> entityType, Level level, Supplier<Item> itemSupplier) {
        super(entityType, level);
        this.dropItem = itemSupplier;
    }

    public static boolean canVehicleCollide(Entity vehicle, Entity entity) {
        return (entity.canBeCollidedWith(vehicle) || entity.isPushable()) && !vehicle.isPassengerOfSameVehicle(entity);
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
    public boolean canBeCollidedWith(@Nullable Entity entity) { return true;}

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
            try (var world = this.level()) {
                if (!world.isClientSide) {
                    return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
                } else {
                    return InteractionResult.SUCCESS;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
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

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity passenger) {
        List<Double> xz = getPosition(3.5F, 0.0F);
        return new Vec3(xz.get(0), this.getY(), xz.get(1));
    }

    protected List<Double> getPosition(double relative_X, double relative_Z) {
        double x = this.getX() - relative_Z * Mth.sin(getYRot() * Mth.DEG_TO_RAD)
                + relative_X * Mth.cos(getYRot() * Mth.DEG_TO_RAD);
        double z = this.getZ() + relative_Z * Mth.cos(getYRot() * Mth.DEG_TO_RAD)
                + relative_X * Mth.sin(getYRot() * Mth.DEG_TO_RAD);
        return List.of(x, z);
    }
}
