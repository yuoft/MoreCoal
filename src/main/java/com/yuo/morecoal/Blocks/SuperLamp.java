package com.yuo.morecoal.Blocks;

import com.yuo.morecoal.Tiles.SuperLampTile;
import com.yuo.morecoal.Tiles.TileTypeRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class SuperLamp extends Block {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 14.0D, 13.0D);
    protected final IParticleData particleData = ParticleTypes.FLAME;

    public SuperLamp() {
        super(Properties.create(Material.ROCK).harvestLevel(0).harvestTool(ToolType.PICKAXE)
                .hardnessAndResistance(3,3).sound(SoundType.GLASS));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("morecoal.text.blockInfo.super_lamp"));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    //能否放置
    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos down = pos.down();
        if (worldIn.isAirBlock(down)) return false;
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 1.0D;
        double d2 = (double)pos.getZ() + 0.5D;
        for (int i = 0; i < 3; i++){
            worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, worldIn.rand.nextDouble() * 0.1d, worldIn.rand.nextDouble() * 0.1d, worldIn.rand.nextDouble() * 0.1d);
            worldIn.addParticle(this.particleData, d0, d1, d2, worldIn.rand.nextDouble() * 0.1d, worldIn.rand.nextDouble() * 0.1d, worldIn.rand.nextDouble() * 0.1d);
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SuperLampTile();
    }

    //方块被破坏时触发
    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moving)
    {
        TileEntity te = world.getTileEntity(pos);
        if(te instanceof SuperLampTile){
            SuperLampTile superLampTile = (SuperLampTile) te;
            superLampTile.removeChildLights();
        }
    }
}
