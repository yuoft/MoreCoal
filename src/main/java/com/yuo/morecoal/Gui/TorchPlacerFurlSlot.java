package com.yuo.morecoal.Gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.ForgeHooks;

public class TorchPlacerFurlSlot extends Slot {
    public TorchPlacerFurlSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    //能否放入物品
    @Override
    public boolean isItemValid(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack) > 0 || isBucket(stack);
    }

    //放入数量
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return isBucket(stack) ? 1 : 64;
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.getItem() == Items.BUCKET;
    }

}
