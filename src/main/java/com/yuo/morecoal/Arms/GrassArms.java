package com.yuo.morecoal.Arms;

import com.yuo.morecoal.Items.ItemRegistry;
import com.yuo.morecoal.tab.ModGroup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class GrassArms extends ArmorItem{

	public GrassArms(EquipmentSlotType slot) {
		super(MyArmorMaterial.GRASS, slot, new Properties().maxStackSize(1).group(ModGroup.myGroup));
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("morecoal.text.itemInfo.grass_arms"));
	}
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		Iterator<ItemStack> iterator = player.getArmorInventoryList().iterator();
		boolean flag = true;
		while (iterator.hasNext()){
			if (!(iterator.next().getItem() instanceof GrassArms)){
				flag = false;
			}
		}
		if (flag){
			player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 0, 1)); //再生
		}
	}
}
