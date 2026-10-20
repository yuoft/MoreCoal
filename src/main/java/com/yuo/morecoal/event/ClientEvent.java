package com.yuo.morecoal.event;

import com.yuo.morecoal.MoreCoal;
import com.yuo.morecoal.entity.ModEntityTypes;
import com.yuo.morecoal.entity.render.TorchArrowRender;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreCoal.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvent {

    //实体渲染注册
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.TORCH_ARROW.get(), TorchArrowRender::new);
    }
}
