package com.yuo.morecoal;

import com.yuo.morecoal.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

//创造模式物品栏 实例化
public class ModTabs {
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreCoal.MOD_ID);
	public static final RegistryObject<CreativeModeTab> MOD_TAB = TABS.register(MoreCoal.MOD_ID + "_tab", () -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup.tab.morecoal"))
			.icon(() -> ModItems.diamondCoal.get().getDefaultInstance())
			.displayItems((parameters, output) -> {
				for (RegistryObject<Item> entry : ModItems.ITEMS.getEntries()) {
					output.accept(new ItemStack(entry.get()));
				}

			}).build());
}
