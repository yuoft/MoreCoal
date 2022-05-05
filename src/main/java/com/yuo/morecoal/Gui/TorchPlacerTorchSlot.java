package com.yuo.morecoal.Gui;

import com.yuo.morecoal.Tiles.TorchPlacerTile;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class TorchPlacerTorchSlot extends Slot {
    public TorchPlacerTorchSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return TorchPlacerTile.isTorch(stack);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 64;
    }
}
