package com.yuo.morecoal.blocks;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MoreCoalOre extends DropExperienceBlock {

    public MoreCoalOre(float hardness, float resistance, int lightLevel, int expMin, int expMax) {
        super(BlockBehaviour.Properties.of().sound(SoundType.STONE).lightLevel(state -> lightLevel)
                .strength(hardness, resistance).requiresCorrectToolForDrops(), UniformInt.of(expMin, expMax));
    }
}