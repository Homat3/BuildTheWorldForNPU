package com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike3;

import com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike;
import com.tf.npu.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

@OnlyIn(Dist.CLIENT)
public class Bike3Model extends GeoModel<Bike3> {
    private final ResourceLocation model = ResourceLocation.fromNamespaceAndPath(Reference.MODID, "bike3");
    private final ResourceLocation animations = ResourceLocation.fromNamespaceAndPath(Reference.MODID, "bike3");
    private final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(Reference.MODID, "textures/entity/b/bike3.png");

    @Override
    public ResourceLocation getModelResource(GeoRenderState geoRenderState) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState geoRenderState) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(Bike3 bike3) {
        return this.animations;
    }
}