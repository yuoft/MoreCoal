package com.yuo.morecoal.blocks;

import com.yuo.morecoal.tiles.ModTiles;
import com.yuo.morecoal.tiles.SuperLampTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class SuperLamp extends Block implements EntityBlock {
    protected static final VoxelShape SHAPE = Shapes.box(0.1875, 0.0, 0.1875, 0.8125, 0.875, 0.8125);

    public SuperLamp() {
        super(Properties.of().sound(SoundType.GLASS).strength(3.0F, 3.0F).lightLevel(state -> 15));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("morecoal.text.blockInfo.super_lamp"));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return !level.isEmptyBlock(pos.below());
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 1.0D;
        double z = pos.getZ() + 0.5D;
        for (int i = 0; i < 3; i++) {
            level.addParticle(ParticleTypes.SMOKE, x, y, z, random.nextDouble() * 0.1D, random.nextDouble() * 0.1D, random.nextDouble() * 0.1D);
            level.addParticle(ParticleTypes.FLAME, x, y, z, random.nextDouble() * 0.1D, random.nextDouble() * 0.1D, random.nextDouble() * 0.1D);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SuperLampTile(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof SuperLampTile lamp) {
                lamp.removeChildLights();
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModTiles.SUPER_LAMP_TILE.get() ? (lvl, pos, st, be) -> SuperLampTile.tick(lvl, pos, st, (SuperLampTile) be) : null;
    }
}