package com.yuo.morecoal.Items;

import com.yuo.morecoal.tab.ModGroup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CoalPickaxe extends PickaxeItem {
    public CoalPickaxe() {
        super(MyItemTier.COAL_INGOT, 1, -2.8f, new Item.Properties().group(ModGroup.myGroup));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("morecoal.text.itemInfo.coal_pickaxe"));
        tooltip.add(new TranslationTextComponent("morecoal.text.itemInfo.coal_tool"));
    }
}
