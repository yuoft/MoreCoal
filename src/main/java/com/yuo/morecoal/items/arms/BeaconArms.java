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

public class BeaconArms extends ArmorItem {

	public BeaconArms(Type slot) {
		super(ModArmorMaterials.BEACON, slot, new Properties().stacksTo(1));
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
		components.add(Component.translatable("morecoal.text.itemInfo.beacon_arms"));
	}

	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {
		Iterator<ItemStack> iterator = player.getArmorSlots().iterator();
		boolean flag = true;
		while (iterator.hasNext()){
			if (!(iterator.next().getItem() instanceof BeaconArms)){
				flag = false;
			}
		}
		if (flag){
			player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 0)); //速度
			player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20, 0)); //急迫
			player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 0)); //抗性提升
			player.addEffect(new MobEffectInstance(MobEffects.JUMP, 20, 0)); //跳跃提升
			player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20, 0)); //力量
			player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20, 0)); //再生
		}
	}
}
