package com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike2;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

//Bike2的Renderer

public class Bike2Renderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<Bike2, R> {
    public Bike2Renderer(EntityRendererProvider.Context context) {
        super(context, new Bike2Model());
        this.shadowRadius = 0.5f;
    }
}
