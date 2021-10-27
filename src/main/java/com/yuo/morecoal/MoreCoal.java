package com.yuo.morecoal;

import com.yuo.morecoal.Blocks.BlockRegistry;
import com.yuo.morecoal.Entity.EntityTypeRegistry;
import com.yuo.morecoal.Entity.Render.TorchArrowRender;
import com.yuo.morecoal.Gui.ContainerTypeRegistry;
import com.yuo.morecoal.Gui.TorchPlacerScreen;
import com.yuo.morecoal.Tiles.TileTypeRegistry;
import com.yuo.morecoal.Items.ItemRegistry;
import com.yuo.morecoal.world.OreGen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Supplier;

@Mod("morecoal")
@Mod.EventBusSubscriber(modid = com.yuo.morecoal.MoreCoal.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoreCoal {
	public static final String MODID = "morecoal";
	public MoreCoal() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::clientSetup);
		//注册物品至mod总线
        ItemRegistry.ITEMS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        EntityTypeRegistry.ENTITY_TYPES.register(modEventBus);
        TileTypeRegistry.TILE_ENTITIES.register(modEventBus);
        ContainerTypeRegistry.CONTAINERS.register(modEventBus);

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGen::generateOres); //注册矿物生成
    }
    @SubscribeEvent
    public void clientSetup(final FMLClientSetupEvent event) {
        registerEntityRender(event.getMinecraftSupplier()); //注册客户端渲染
        //物品动态属性注册
        event.enqueueWork(() -> {
            ItemModelsProperties.registerProperty(ItemRegistry.coalBow.get(), new ResourceLocation(MoreCoal.MODID,
                    "pull"), (itemStack, clientWorld, livingEntity) -> {
                if (livingEntity == null) {
                    return 0.0F;
                } else {
                    return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
                }
            });
            ItemModelsProperties.registerProperty(ItemRegistry.coalBow.get(), new ResourceLocation(MoreCoal.MODID,
                    "pulling"), (itemStack, clientWorld, livingEntity) -> {
                return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
            });
        });
        //方块渲染方式
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(BlockRegistry.superLamp.get(), RenderType.getTranslucent());
            RenderTypeLookup.setRenderLayer(BlockRegistry.bigTorch.get(), RenderType.getTranslucent());
        });
        //绑定Container和ContainerScreen
        event.enqueueWork(() -> {
            ScreenManager.registerFactory(ContainerTypeRegistry.torchPlacerContainer.get(), TorchPlacerScreen::new);
        });
    }

    private void registerEntityRender(Supplier<Minecraft> minecraft){
        ItemRenderer renderer = minecraft.get().getItemRenderer();
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeRegistry.TORCH_ARROW.get(),
                (renderManager) -> new TorchArrowRender(renderManager));

    }
}
