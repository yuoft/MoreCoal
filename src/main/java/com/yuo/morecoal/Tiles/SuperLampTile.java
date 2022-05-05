package com.yuo.morecoal.Tiles;

import com.mojang.authlib.GameProfile;
import com.yuo.morecoal.Blocks.AirLight;
import com.yuo.morecoal.Blocks.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.*;
import net.minecraft.world.LightType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//在周围？个区块范围放置火把或光源
public class SuperLampTile extends TileEntity implements ITickableTileEntity {

    private static List<BlockPos> childLights = new ArrayList<>(); //光源坐标列表
    private int radius = 32; //光源设置范围

    public SuperLampTile() {
        super(TileTypeRegistry.SUPER_LAMP_TILE.get());
    }

    @Override
    public void tick() {
        if (world == null || world.isRemote) return;
        if (world.isNightTime()) return;
        if (world.getGameTime() % 10 != 0) return; //每0.5秒运行一次
        if (childLights.size() > 64) return;  //限制光源数量

        int diameter = radius * 2;
        //随机生成一个坐标
        int x = (radius - world.rand.nextInt(diameter)) + pos.getX();
        int y = (radius - world.rand.nextInt(diameter)) + pos.getY();
        int z = (radius - world.rand.nextInt(diameter)) + pos.getZ();
        if (y < 3) y = 3; //最低高度

        BlockPos targetPos = new BlockPos(x, y, z);
        BlockPos surfaceHeight = world.getHeight(Heightmap.Type.WORLD_SURFACE, targetPos);
        if (targetPos.getY() > surfaceHeight.getY() + 4)
            targetPos = surfaceHeight.up(4); //最高高度 世界表面高度加4

        //不能超过世界高度限制（建筑高度）
        int worldHeightCap = world.getHeight();
        if(targetPos.getY() > worldHeightCap)
            targetPos = new BlockPos(targetPos.getX(), worldHeightCap - 1, targetPos.getZ());

        if(!world.chunkExists(targetPos.getX(), targetPos.getZ())) return; //区块是否加载

        if (world.isAirBlock(targetPos) && world.getLightFor(LightType.BLOCK, targetPos) < 5){ //亮度小于5
            if(world.setBlockState(targetPos, BlockRegistry.airLight.get().getDefaultState().with(AirLight.AGE, 0), 2)) {
                childLights.add(targetPos);
                markDirty();  //告知游戏保存数据
            }
        }

    }

    @Override
    public void read(BlockState blockState, CompoundNBT nbt) {
        childLights.clear();
        if(nbt.getTagId("lights") == Constants.NBT.TAG_INT_ARRAY) {
            BlockPos origin = new BlockPos(nbt.getInt("x"), nbt.getInt("y"),nbt.getInt("z"));
            int[] lightsEncoded = ((IntArrayNBT) nbt.get("lights")).getIntArray();
            for(int encodedLight : lightsEncoded)
                childLights.add(decodePosition(origin, encodedLight));
        }
        super.read(blockState, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        List<Integer> childLightsEncoded = new ArrayList<>(childLights.size());
        for(BlockPos child : childLights)
            childLightsEncoded.add(encodePosition(this.pos, child));
        nbt.put("lights", new IntArrayNBT(childLightsEncoded));
        return super.write(nbt);
    }

    //清除光源
    public void removeChildLights() {
        if(world.isRemote) return;
        for(BlockPos pos : childLights) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            if (world.getBlockState(pos).getBlock().equals(BlockRegistry.airLight.get()))
                world.removeBlock(pos, false);
        }
        childLights.clear();
    }

    //加密坐标
    public static int encodePosition(BlockPos origin, BlockPos target) {
        int x = target.getX() - origin.getX();
        int y = target.getY() - origin.getY();
        int z = target.getZ() - origin.getZ();
        return ((x & 0xFF) << 16) + ((y & 0xFF) << 8) + (z & 0xFF);
    }

    //解密坐标
    public static BlockPos decodePosition(BlockPos origin, int pos) {
        int x = (byte)((pos >> 16) & 0xFF);
        int y = (byte)((pos >> 8) & 0xFF);
        int z = (byte)(pos & 0xFF);
        return origin.add(x, y, z);
    }
}
