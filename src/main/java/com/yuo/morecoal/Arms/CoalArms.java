package com.yuo.morecoal.Arms;

import com.yuo.morecoal.Items.MoreCoalItems;
import com.yuo.morecoal.tab.ModGroup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CoalArms extends ArmorItem{

	public CoalArms(EquipmentSlotType slot) {
		super(MyArmorMaterial.COAL_INGOT, slot, new Properties().maxStackSize(1).group(ModGroup.myGroup));
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (stack.getItem().equals(MoreCoalItems.coalFeet.get())){
			tooltip.add(new TranslationTextComponent("morecoal.text.itemInfo.coal_feet"));
		}
	}
//	@Override
//	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
//		Iterator<ItemStack> iterator = player.getArmorInventoryList().iterator();
//		boolean flag = true;
//		while (iterator.hasNext()){
//			if (iterator.next().isEmpty()){
//				flag = false;
//			}
//		}
//		if (flag){
//			player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 0, 1));
//		}
//	}
}
