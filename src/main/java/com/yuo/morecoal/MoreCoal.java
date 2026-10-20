package com.yuo.morecoal;

import com.yuo.morecoal.blocks.ModBlocks;
import com.yuo.morecoal.entity.ModEntityTypes;
import com.yuo.morecoal.gui.ModMenus;
import com.yuo.morecoal.items.ModItems;
import com.yuo.morecoal.proxy.ClientProxy;
import com.yuo.morecoal.proxy.CommonProxy;
import com.yuo.morecoal.proxy.IProxy;
import com.yuo.morecoal.tiles.ModTiles;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("morecoal")
public class MoreCoal {
	public static final String MOD_ID = "morecoal";
    public static final IProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    @SuppressWarnings("removal")
    public MoreCoal() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		//注册至mod总线
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModTabs.TABS.register(modEventBus);
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);
        ModTiles.TILE_ENTITIES.register(modEventBus);

        proxy.registerHandlers(modEventBus);
    }

    /**
     * 检查mod
     * @param modId 模组id
     * @return 存在 true
     */
    private boolean checkMod(String modId){
        return ModList.get().isLoaded(modId);
    }
}
