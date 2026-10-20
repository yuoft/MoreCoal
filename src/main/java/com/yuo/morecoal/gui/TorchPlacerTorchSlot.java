package com.yuo.morecoal.gui;

import com.yuo.morecoal.tiles.TorchPlacerTile;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class TorchPlacerTorchSlot extends Slot {

    public TorchPlacerTorchSlot(Container container, int index, int x, int y) {
        super(container, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return TorchPlacerTile.isTorch(stack);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 64;
    }
}