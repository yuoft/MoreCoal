package com.yuo.morecoal.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CoalSword extends SwordItem {
    public CoalSword() {
        super(ModItemTiers.COAL_INGOT, 2, -2.4f, new Item.Properties());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setSecondsOnFire(1); //煤炭剑攻击生物，点燃1s
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("morecoal.text.itemInfo.coal_sword"));
        components.add(Component.translatable("morecoal.text.itemInfo.coal_tool"));
    }
}
