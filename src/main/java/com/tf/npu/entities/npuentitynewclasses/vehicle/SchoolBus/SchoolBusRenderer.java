package com.tf.npu.entities.npuentitynewclasses.vehicle.SchoolBus;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

//SchoolBus的Renderer
@OnlyIn(Dist.CLIENT)
public class SchoolBusRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<SchoolBus, R> {
    public SchoolBusRenderer(EntityRendererProvider.Context context) {
        super(context, new SchoolBusModel());
    }
}
