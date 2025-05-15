package com.tf.npu.items.npuitemnewclasses;

import com.tf.npu.entities.npuentitynewclasses.vehicle.NpuVehicle;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class VehicleItem extends Item {
    private final EntityType<? extends NpuVehicle> type;

    public VehicleItem(EntityType<? extends NpuVehicle> entityType, Properties properties) {
        super(properties);
        this.type = entityType;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();

        ItemStack itemstack = context.getItemInHand();

        Vec3 vec3 = new Vec3(blockpos.getX() + 0.5, blockpos.getY() + 0.0625, blockpos.getZ() + 0.5);
        NpuVehicle vehicle = NpuVehicle.createNpuVehicle(
                level, vec3.x, vec3.y, vec3.z, this.type, EntitySpawnReason.DISPENSER, itemstack, context.getPlayer());
        if (vehicle == null) {
            return InteractionResult.FAIL;
        }

        if (AbstractMinecart.useExperimentalMovement(level)) {
            for (Entity entity : level.getEntities(null, vehicle.getBoundingBox())) {
                if (entity instanceof AbstractMinecart) {
                    return InteractionResult.FAIL;
                }
            }
        }

        if (level instanceof ServerLevel serverlevel) {
            serverlevel.addFreshEntity(vehicle);
            serverlevel.gameEvent(
                    GameEvent.ENTITY_PLACE, blockpos, GameEvent.Context.of(context.getPlayer(), serverlevel.getBlockState(blockpos.below()))
            );
        }

        itemstack.shrink(1);
        return InteractionResult.SUCCESS;
    }
}
