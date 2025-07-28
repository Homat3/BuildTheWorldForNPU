package com.tf.npu.items.npuitemnewclasses;

import com.tf.npu.entities.npuentitynewclasses.vehicle.NpuVehicle;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VehicleItem extends Item {
    private final EntityType<? extends NpuVehicle> type;

    public VehicleItem(EntityType<? extends NpuVehicle> entityType, Properties properties) {
        super(properties);
        this.type = entityType;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();
        if (player == null) return InteractionResult.FAIL;

        HitResult hitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);

        if (hitresult.getType() == HitResult.Type.MISS) {
            return InteractionResult.PASS;
        }
        else {
            Vec3 vec3 = player.getViewVector(1.0F);
            List<Entity> list = level.getEntities(
                    player, player.getBoundingBox().expandTowards(vec3.scale(5.0)).inflate(1.0), EntitySelector.CAN_BE_PICKED
            );
            if (!list.isEmpty()) {
                Vec3 vec31 = player.getEyePosition();

                for (Entity entity : list) {
                    AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (aabb.contains(vec31)) {
                        return InteractionResult.PASS;
                    }
                }
            }

            if (hitresult.getType() == HitResult.Type.BLOCK) {
                NpuVehicle vehicle = NpuVehicle.createNpuVehicle(level,
                        hitresult.getLocation().x, hitresult.getLocation().y, hitresult.getLocation().z,
                        this.type, EntitySpawnReason.SPAWN_ITEM_USE, itemstack, player);
                if (vehicle == null) {
                    return InteractionResult.FAIL;
                } else {
                    vehicle.setYRot(player.getYRot());
                    if (!level.noCollision(vehicle, vehicle.getBoundingBox())) {
                        return InteractionResult.FAIL;
                    } else {
                        if (!level.isClientSide) {
                            level.addFreshEntity(vehicle);
                            level.gameEvent(player, GameEvent.ENTITY_PLACE, hitresult.getLocation());
                            itemstack.consume(1, player);
                        }

                        player.awardStat(Stats.ITEM_USED.get(this));
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            else {
                return InteractionResult.PASS;
            }
        }
    }
}
