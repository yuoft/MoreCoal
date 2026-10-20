package com.yuo.morecoal.gui;

import net.minecraft.world.inventory.ContainerData;

public class TorchPlacerIntArray implements ContainerData {
    private int burnTime;
    private int burnCount = 1600;
    private int energy;

    @Override
    public int get(int index) {
        return switch (index) {
            case 0 -> burnTime;
            case 1 -> burnCount;
            case 2 -> energy;
            default -> 0;
        };
    }

    @Override
    public void set(int index, int value) {
        switch (index) {
            case 0 -> burnTime = value;
            case 1 -> burnCount = value;
            case 2 -> energy = value;
        }
    }

    @Override
    public int getCount() { // size() → getCount()
        return 3;
    }
}