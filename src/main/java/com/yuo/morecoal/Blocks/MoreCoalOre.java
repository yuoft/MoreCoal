package com.yuo.morecoal.Blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

import java.util.List;
import java.util.Random;

public class MoreCoalOre extends OreBlock {
    public MoreCoalOre(Material material, int harvestLevel, ToolType toolType, float hardness, float resistancelln, int lightLevel) {
        super(Properties.create(material).harvestLevel(harvestLevel).harvestTool(toolType).sound(SoundType.STONE)
                .hardnessAndResistance(hardness, resistancelln).setLightLevel(e -> lightLevel));
    }

    @Override
    protected int getExperience(Random rand) {
        return MathHelper.nextInt(rand, 0, 4);
    }
}
