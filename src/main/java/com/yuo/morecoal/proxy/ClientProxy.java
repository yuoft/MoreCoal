package com.yuo.morecoal.proxy;

import com.yuo.morecoal.gui.ModMenus;
import com.yuo.morecoal.gui.TorchPlacerScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * 客户端属性注册
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void registerHandlers(IEventBus modBus) {
        super.registerHandlers(modBus);
        modBus.addListener(this::clientSetup);
    }

    @SubscribeEvent
    public void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.TORCH_PLACER_CONTAINER.get(), TorchPlacerScreen::new);
        });
//        ItemBlockRenderTypes.setRenderLayer(OreCropBlocks.customSapling.get(), RenderType.cutout());
    }
}
