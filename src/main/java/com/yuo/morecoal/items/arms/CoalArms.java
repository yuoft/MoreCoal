package com.yuo.morecoal.items.arms;

import com.yuo.morecoal.items.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CoalArms extends ArmorItem {

	public CoalArms(Type type) {
		super(ModArmorMaterials.COAL_INGOT, type, new Properties().stacksTo(1));
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
		if (stack.getItem() == ModItems.coalFeet.get())
			components.add(Component.translatable("morecoal.text.itemInfo.coal_feet"));
	}
}
