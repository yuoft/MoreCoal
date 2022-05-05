package com.yuo.morecoal.Blocks;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BigTorch0 extends BigTorch {

    public BigTorch0() {
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("morecoal.text.blockInfo.big_torch0"));
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 1.0D;
        double d2 = (double)pos.getZ() + 0.5D;
        for (int i = 0; i < 3; i++){
            worldIn.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d0, d1, d2, worldIn.rand.nextDouble() * 0.1d, worldIn.rand.nextDouble() * 0.1d, worldIn.rand.nextDouble() * 0.1d);
            worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, worldIn.rand.nextDouble() * 0.1d, worldIn.rand.nextDouble() * 0.1d, worldIn.rand.nextDouble() * 0.1d);
        }
    }
}
