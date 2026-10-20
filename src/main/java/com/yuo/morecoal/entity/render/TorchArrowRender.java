package com.yuo.morecoal.entity.render;

import com.yuo.morecoal.ModUtils;
import com.yuo.morecoal.entity.TorchArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

//箭实体渲染
public class TorchArrowRender extends ArrowRenderer<TorchArrowEntity> {

    private static final ResourceLocation TEXTURE = ModUtils.fa("textures/models/misc/torch_arrow.png");

    public TorchArrowRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(TorchArrowEntity entity) {
        return TEXTURE;
    }
}
