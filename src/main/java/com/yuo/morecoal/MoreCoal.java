package com.yuo.morecoal;

import com.yuo.morecoal.Blocks.MoreCoalBlocks;
import com.yuo.morecoal.Entity.EntityTypeRegistry;
import com.yuo.morecoal.Gui.ContainerTypeRegistry;
import com.yuo.morecoal.Items.MoreCoalItems;
import com.yuo.morecoal.Proxy.ClientProxy;
import com.yuo.morecoal.Proxy.CommonProxy;
import com.yuo.morecoal.Proxy.IProxy;
import com.yuo.morecoal.Tiles.TileTypeRegistry;
import com.yuo.morecoal.world.OreGen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("morecoal")
public class MoreCoal {
	public static final String MODID = "morecoal";
    public static final IProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
	public MoreCoal() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		//注册物品至mod总线
        MoreCoalItems.ITEMS.register(modEventBus);
        MoreCoalBlocks.BLOCKS.register(modEventBus);
        EntityTypeRegistry.ENTITY_TYPES.register(modEventBus);
        TileTypeRegistry.TILE_ENTITIES.register(modEventBus);
        ContainerTypeRegistry.CONTAINERS.register(modEventBus);
        proxy.registerHandlers();

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGen::generateOres); //注册矿物生成
    }

}
