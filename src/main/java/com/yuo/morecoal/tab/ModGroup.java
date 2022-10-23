package com.yuo.morecoal.tab;

import com.yuo.morecoal.Items.MoreCoalItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

//创造模式物品栏 实例化
public class ModGroup extends ItemGroup{
	public static ItemGroup myGroup = new ModGroup();

	public ModGroup() {
		super(ItemGroup.GROUPS.length, "MoreCoal"); //页码11开始，名称
	}
	//图标
	@Override
	public ItemStack createIcon() {
		return new ItemStack(MoreCoalItems.diamondCoal.get());
	}
}
