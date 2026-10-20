package com.yuo.morecoal.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class BigTorch0 extends BigTorch {

    public BigTorch0() {
        super();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("morecoal.text.blockInfo.big_torch0"));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 1.0D;
        double z = pos.getZ() + 0.5D;
        for (int i = 0; i < 3; i++) {
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, random.nextDouble() * 0.1D, random.nextDouble() * 0.1D, random.nextDouble() * 0.1D);
            level.addParticle(ParticleTypes.SMOKE, x, y, z, random.nextDouble() * 0.1D, random.nextDouble() * 0.1D, random.nextDouble() * 0.1D);
        }
    }
}