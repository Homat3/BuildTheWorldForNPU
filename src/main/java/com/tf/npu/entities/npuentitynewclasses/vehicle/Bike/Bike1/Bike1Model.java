package com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike1;

import com.tf.npu.util.Reference;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;


public class Bike1Model extends GeoModel<Bike1> {
    private final ResourceLocation model = ResourceLocation.fromNamespaceAndPath(Reference.MODID, "bike1");
    private final ResourceLocation animations = ResourceLocation.fromNamespaceAndPath(Reference.MODID, "bike1");
    private final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(Reference.MODID, "textures/entity/b/bike1.png");

    @Override
    public ResourceLocation getModelResource(GeoRenderState geoRenderState) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState geoRenderState) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(Bike1 bike1) {
        return this.animations;
    }
}