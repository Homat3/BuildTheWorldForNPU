package com.tf.npu.entities.npuentitynewclasses.GoldenChicken;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tf.npu.util.Reference;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ChickenRenderState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


//GoldenChicken的Renderer
public class GoldenChickenRenderer extends ChickenRenderer {
    private static final ResourceLocation GOLDEN_CHICKEN_TEXTURES =
            ResourceLocation.fromNamespaceAndPath(Reference.MODID, "textures/entity/g/golden_chicken.png");


    public GoldenChickenRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius *= 0.6F;
    }

    //放缩方式
    @Override
    protected void scale(@NotNull ChickenRenderState renderState, PoseStack stack) {
        stack.scale(1.5F, 1.5F, 1.5F);
        super.scale(renderState, stack);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ChickenRenderState renderState) {
        return GOLDEN_CHICKEN_TEXTURES;
    }
}
