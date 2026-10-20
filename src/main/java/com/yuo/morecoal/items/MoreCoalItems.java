package com.yuo.morecoal.items;

import com.yuo.morecoal.Arms.*;
import com.yuo.morecoal.Blocks.MoreCoalBlocks;
import com.yuo.morecoal.Items.Bow.CoalBow;
import com.yuo.morecoal.Items.Bow.TorchArrow;
import com.yuo.morecoal.MoreCoal;
import com.yuo.morecoal.tab.ModGroup;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//物品注册管理器
public class MoreCoalItems {
	//创建注册器。ForgeRegistries.ITEMS代表了我们要注册的是物品，第二个参数填入的应该是你的modId。
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreCoal.MODID);

	//注册物品。一个是「注册名」，还有一个就是你要注册对象的实例

}
