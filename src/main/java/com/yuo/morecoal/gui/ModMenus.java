package com.yuo.morecoal.gui;

import com.yuo.morecoal.MoreCoal;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MoreCoal.MOD_ID);

    public static final RegistryObject<MenuType<TorchPlacerContainer>> TORCH_PLACER_CONTAINER = MENUS.register("torch_placer_container",
            () -> IForgeMenuType.create(TorchPlacerContainer::new));
}