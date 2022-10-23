package com.yuo.morecoal.Proxy;

import com.yuo.morecoal.Blocks.MoreCoalBlocks;
import com.yuo.morecoal.Entity.EntityTypeRegistry;
import com.yuo.morecoal.Entity.Render.TorchArrowRender;
import com.yuo.morecoal.Gui.ContainerTypeRegistry;
import com.yuo.morecoal.Gui.TorchPlacerScreen;
import com.yuo.morecoal.Items.MoreCoalItems;
import com.yuo.morecoal.MoreCoal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Supplier;

public class ClientProxy implements IProxy {

    @Override
    public void registerHandlers() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::clientSetup);
    }

    @SubscribeEvent
    public void clientSetup(final FMLClientSetupEvent event) {
        registerEntityRender(event.getMinecraftSupplier()); //注册客户端渲染
        //物品动态属性注册
        event.enqueueWork(() -> {
            ItemModelsProperties.registerProperty(MoreCoalItems.coalBow.get(), new ResourceLocation(MoreCoal.MODID,
                    "pull"), (itemStack, clientWorld, livingEntity) -> {
                if (livingEntity == null) {
                    return 0.0F;
                } else {
                    return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
                }
            });
            ItemModelsProperties.registerProperty(MoreCoalItems.coalBow.get(), new ResourceLocation(MoreCoal.MODID,
                    "pulling"), (itemStack, clientWorld, livingEntity) ->
                    livingEntity != null && livingEntity.isHandActive()
                            && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F);
        });
        //方块渲染方式
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(MoreCoalBlocks.superLamp.get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(MoreCoalBlocks.bigTorch.get(), RenderType.getTranslucent());
        });
        //绑定Container和ContainerScreen
        event.enqueueWork(() -> {
            ScreenManager.registerFactory(ContainerTypeRegistry.torchPlacerContainer.get(), TorchPlacerScreen::new);
        });
    }

    private void registerEntityRender(Supplier<Minecraft> minecraft){
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.TORCH_ARROW.get(), TorchArrowRender::new);

    }
}
