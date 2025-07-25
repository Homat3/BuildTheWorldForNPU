package com.tf.npu.entities.npuentitynewclasses.vehicle.SchoolBus;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

//SchoolBus的Renderer

public class SchoolBusRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<SchoolBus, R> {
    public SchoolBusRenderer(EntityRendererProvider.Context context) {
        super(context, new SchoolBusModel());
        this.shadowRadius = 4.5f;
    }
}
