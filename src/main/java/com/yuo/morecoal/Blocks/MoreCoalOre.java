package com.yuo.morecoal.Blocks;

import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class MoreCoalOre extends OreBlock {
    public MoreCoalOre(Material material, int harvestLevel, ToolType toolType, float hardness, float resistancelln, int lightLevel) {
        super(Properties.create(material).harvestLevel(harvestLevel).harvestTool(toolType).sound(SoundType.STONE)
                .hardnessAndResistance(hardness, resistancelln).setLightLevel(e -> lightLevel));
    }
}
