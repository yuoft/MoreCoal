package com.yuo.morecoal.Blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class AirLight extends Block {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_25;

    public AirLight() {
        super(AbstractBlock.Properties.create(Material.AIR).setAir().setLightLevel(e -> 15).doesNotBlockMovement().noDrops().tickRandomly());
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, Integer.valueOf(0)));
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        this.tick(state, worldIn, pos, random);
    }

    //一段时间后消失
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (worldIn.isRemote) return;
        int i = state.get(AGE);
        if (rand.nextInt(5) == 0 && i < 25){
            BlockState state1 = state.with(AGE, i + 1);
            worldIn.setBlockState(pos, state1, 2);
        }
        if (i == 25){
            worldIn.removeBlock(pos, false);
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
