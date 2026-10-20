package com.yuo.morecoal.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CoalPickaxe extends PickaxeItem {
    public CoalPickaxe() {
        super(ModItemTiers.COAL_INGOT, 1, -2.8f, new Item.Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("morecoal.text.itemInfo.coal_pickaxe"));
        components.add(Component.translatable("morecoal.text.itemInfo.coal_tool"));
    }
}
