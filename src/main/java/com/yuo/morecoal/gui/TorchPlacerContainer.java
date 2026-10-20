package com.yuo.morecoal.gui;

import com.yuo.morecoal.tiles.TorchPlacerTile;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

public class TorchPlacerContainer extends AbstractContainerMenu {

    private final Container torchPlacerInventory;
    private final TorchPlacerIntArray torchPlacerData;

    // 客户端构造器：接收 FriendlyByteBuf，用于 IForgeMenuType 工厂
    public TorchPlacerContainer(int id, Inventory playerInventory, FriendlyByteBuf data) {
        this(id, playerInventory, new SimpleContainer(10), new TorchPlacerIntArray());
    }

    public TorchPlacerContainer(int id, Inventory playerInventory, Container inventory, TorchPlacerIntArray intArray) {
        super(ModMenus.TORCH_PLACER_CONTAINER.get(), id);
        this.torchPlacerInventory = inventory;
        this.torchPlacerData = intArray;
        this.addDataSlots(torchPlacerData); // 同步数据：trackIntArray → addDataSlots

        // 燃料槽
        this.addSlot(new TorchPlacerFuelSlot(inventory, 0, 49, 35));
        // 火把槽
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new TorchPlacerTorchSlot(inventory, j + i * 3 + 1, 73 + j * 18, 17 + i * 18));
            }
        }

        // 玩家物品栏
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        // 玩家快捷栏
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.torchPlacerInventory.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemstack = itemStack1.copy();
            if (index >= 10) {
                if (ForgeHooks.getBurnTime(itemStack1, null) > 0) {
                    if (!this.moveItemStackTo(itemStack1, 0, 1, false)) return ItemStack.EMPTY;
                } else if (TorchPlacerTile.isTorch(itemStack1)) {
                    if (!this.moveItemStackTo(itemStack1, 1, 10, false)) return ItemStack.EMPTY;
                } else if (index < 37) {
                    if (!this.moveItemStackTo(itemStack1, 37, 46, false)) return ItemStack.EMPTY;
                } else if (index < 46) {
                    if (!this.moveItemStackTo(itemStack1, 10, 37, false)) return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.set(ItemStack.EMPTY); // putStack → set
            } else {
                slot.setChanged(); // onSlotChanged → setChanged
            }

            if (itemStack1.getCount() == itemstack.getCount()) return ItemStack.EMPTY;
            slot.onTake(player, itemStack1);
        }
        return itemstack;
    }

    public int getEnergy() {
        return (int) Math.ceil(this.torchPlacerData.get(2) / 200f);
    }

    public int getBurnTime() {
        return (int) Math.ceil(this.torchPlacerData.get(0) / (this.torchPlacerData.get(1) * 1.0) * 14);
    }

    public boolean isRunning() {
        return this.torchPlacerData.get(0) > 0;
    }
}