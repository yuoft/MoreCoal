package com.yuo.morecoal.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class MoreCoalBlock extends Block {

    /**
     * @param destroyTime 挖掘时间
     * @param explosionResistance 爆炸硬度
     * @param soundType 音效
     */
    public MoreCoalBlock(float destroyTime, float explosionResistance, SoundType soundType) {
        super(Properties.of().strength(destroyTime, explosionResistance).sound(soundType));
    }
}