package com.yuo.morecoal.Gui;

import com.yuo.morecoal.Tiles.TorchPlacerTile;
import net.minecraft.util.IIntArray;

public class TorchPlacerIntArray implements IIntArray {
    private int burnTime; //剩余燃烧时间
    private int burnCount = 1600; //总燃烧时间 默认值1600 防止燃烧时间计算出错
    private int energy; //能量值
    @Override
    public int get(int index) {
        switch(index) {
            case 0:
                return burnTime;
            case 1:
                return burnCount;
            case 2:
                return energy;
            default:
                return 0;
        }
    }

    @Override
    public void set(int index, int value) {
        switch(index) {
            case 0:
                burnTime = value;
                break;
            case 1:
                burnCount = value;
                break;
            case 2:
                energy = value;
        }
    }

    @Override
    public int size() {
        return 3;
    }
}
