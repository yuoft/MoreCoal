package com.yuo.morecoal.Entity.Render;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

//箭实体渲染
public class TorchArrowRender extends ArrowRenderer {

    private static final ResourceLocation TEXTURE = new ResourceLocation("morecoal:textures/models/misc/torch_arrow.png");

    public TorchArrowRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        return TEXTURE;
    }
}
