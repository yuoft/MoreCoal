package com.yuo.morecoal.Blocks;

import com.yuo.morecoal.Tiles.TorchPlacerTile;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class TorchPlacer extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT; //能量提供状态
    public static final BooleanProperty WORK = BlockStateProperties.POWERED; //工作状态

    public TorchPlacer() {
        super(Properties.create(Material.ROCK).sound(SoundType.STONE).setRequiresTool().hardnessAndResistance(3,3));
        this.setDefaultState(this.stateContainer.getBaseState().with(LIT, false).with(WORK, false));
    }

    //能否提供红石信号
    @Override
    public boolean canProvidePower(BlockState state) {
        return true;
    }

    //旁边方块更新时
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        boolean flag = worldIn.isBlockPowered(pos);
        if (blockIn != this && flag != state.get(WORK)) {
            toggleWork(pos, state, worldIn);
        }
    }

    //切换工作状态
    private void toggleWork(BlockPos pos, BlockState state, World world){
        if (state.get(WORK)){
            world.setBlockState(pos, state.with(WORK, false), 2);
        }else world.setBlockState(pos, state.with(WORK, true), 2);
    }

    //提供红石信号值
//    @Override
//    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
//        return blockState.get(LIT) && Direction.UP != side ? 15 : 0;
//    }
//
//    @Override
//    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
//        return side == Direction.DOWN ? blockState.getWeakPower(blockAccess, pos, side) : 0;
//    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("morecoal.text.blockInfo.torch_placer"));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT, WORK);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 1D;
        double d2 = (double)pos.getZ() + 0.5D;
        if (stateIn.get(LIT)) worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.1D, 0.0D);
        if (stateIn.get(WORK)) worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, d0, d1, d2, 0.0D, 0.1D, 0.0D);
    }
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TorchPlacerTile();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TorchPlacerTile){ //打开gui
                player.openContainer((INamedContainerProvider) tileEntity);
                player.addStat(Stats.INTERACT_WITH_FURNACE);
            }
        }
        return ActionResultType.SUCCESS;
    }

    //被破坏时 里面所有物品掉落
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.isIn(newState.getBlock())) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TorchPlacerTile) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TorchPlacerTile)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
        if (!isMoving) {
            for(Direction direction : Direction.values()) {
                worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
            }
        }
    }
}
