package com.tf.npu.entities.npuentitynewclasses.GoldenChicken;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.Level;

public class GoldenChicken extends Chicken
{
    public GoldenChicken(EntityType<? extends Chicken> type, Level level)
    {
        super(type, level);
    }

    public static AttributeSupplier.Builder registerAttributes()
    {
        return Chicken.createAttributes()
                .add(Attributes.MAX_HEALTH, 25.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.1F);
    }
}