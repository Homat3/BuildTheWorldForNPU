package com.tf.npu.entities.npuentitynewclasses.vehicle.Bike.Bike3;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

//Bike2的Renderer

public class Bike3Renderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<Bike3, R> {
    public Bike3Renderer(EntityRendererProvider.Context context) {
        super(context, new Bike3Model());
        this.shadowRadius = 0.5f;
    }
}
