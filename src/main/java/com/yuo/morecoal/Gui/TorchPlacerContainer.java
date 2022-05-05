package com.yuo.morecoal.Gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

public class TorchPlacerContainer extends Container {

    private final IInventory torchPlacerInventory;
    private final TorchPlacerIntArray torchPlacerData;
    private final World world;



    public TorchPlacerContainer(int id, PlayerInventory playerInventory){
        this(id, playerInventory, new Inventory(10), new TorchPlacerIntArray());
    }

    public TorchPlacerContainer(int id, PlayerInventory playerInventory, IInventory inventory, TorchPlacerIntArray intArray) {
        super(ContainerTypeRegistry.torchPlacerContainer.get(), id);
        this.torchPlacerInventory = inventory;
        this.torchPlacerData = intArray;
        this.trackIntArray(torchPlacerData); //同步torchPlacerData
        this.world = playerInventory.player.world;
        //燃料槽
        this.addSlot(new TorchPlacerFurlSlot(inventory, 0, 49, 35));
        //火把槽
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                this.addSlot(new TorchPlacerTorchSlot(inventory, j + i * 3 + 1, 73 + j * 18, 17 + i * 18));
            }}

        //添加玩家物品栏
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        //添加玩家快捷栏
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

    }

    //玩家能否打开gui
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.torchPlacerInventory.isUsableByPlayer(playerIn);
    }

    //玩家shift行为
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemstack = itemStack1.copy();
            if (index >= 10){
                if (ForgeHooks.getBurnTime(itemStack1) > 0) { //是燃料
                    if (!this.mergeItemStack(itemStack1, 0, 1, false)) return ItemStack.EMPTY;
                } //是火把
                else if (itemStack1.getItem().equals(Items.TORCH) || itemStack1.getItem().equals(Items.SOUL_TORCH) || itemStack1.getItem().equals(Items.REDSTONE_TORCH)) {
                    if (!this.mergeItemStack(itemStack1, 1, 10, false)) return ItemStack.EMPTY;
                }else if (index >= 10 && index < 37) { //从物品栏到快捷栏
                    if (!this.mergeItemStack(itemStack1, 37, 46, false)) return ItemStack.EMPTY;
                } else if (index >= 37 && index < 46 ) {
                    if (!this.mergeItemStack(itemStack1, 10, 37, false)) return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemStack1, 10, 46, false)) return ItemStack.EMPTY; //取出来

            if (itemStack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();

            if (itemStack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;
            slot.onTake(playerIn, itemStack1);
        }

        return itemstack;
    }

    public int getEnergy() {
        return (int) Math.ceil(this.torchPlacerData.get(2) / 200);
    }

    public int getBurnTime() {
        return (int) Math.ceil(this.torchPlacerData.get(0) / (this.torchPlacerData.get(1) * 1.0) * 14);
    }

    public boolean isRunning() {
        return this.torchPlacerData.get(0) > 0;
    }

}
