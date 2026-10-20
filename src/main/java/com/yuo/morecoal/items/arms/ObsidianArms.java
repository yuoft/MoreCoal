package com.yuo.morecoal.items.arms;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class ObsidianArms extends ArmorItem {

	public ObsidianArms(Type slot) {
		super(ModArmorMaterials.OBSIDIAN, slot, new Properties().stacksTo(1));
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
		components.add(Component.translatable("morecoal.text.itemInfo.obsidian_arms"));
	}

	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {
		Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
		boolean flag = true;
		while (iterator.hasNext()){
			if (!(iterator.next().getItem() instanceof ObsidianArms)){
				flag = false;
			}
		}
		if (flag){
			player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 1)); //抗性
			player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 600, 0)); //生命提升
		}
	}
}
