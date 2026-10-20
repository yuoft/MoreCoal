package com.yuo.morecoal.tiles;

import com.yuo.morecoal.blocks.AirLight;
import com.yuo.morecoal.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SuperLampTile extends BlockEntity {

    private final List<BlockPos> childLights = new ArrayList<>();
    private final int radius = 32;

    public SuperLampTile(BlockPos pos, BlockState state) {
        super(ModTiles.SUPER_LAMP_TILE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SuperLampTile tile) {
        if (level.isClientSide) return;
        if (!level.isNight()) return;
        if (level.getGameTime() % 10 != 0) return;
        if (tile.childLights.size() > 64) return;

        int diameter = tile.radius * 2;
        int x = (tile.radius - level.random.nextInt(diameter)) + pos.getX();
        int y = (tile.radius - level.random.nextInt(diameter)) + pos.getY();
        int z = (tile.radius - level.random.nextInt(diameter)) + pos.getZ();
        if (y < 3) y = 3;

        BlockPos target = new BlockPos(x, y, z);
        BlockPos surface = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, target);
        if (target.getY() > surface.getY() + 4) {
            target = surface.above(4);
        }

        int maxY = level.getMaxBuildHeight() - 1;
        if (target.getY() > maxY) {
            target = new BlockPos(target.getX(), maxY, target.getZ());
        }

        if (!level.isLoaded(target)) return;

        if (level.isEmptyBlock(target) && level.getBrightness(LightLayer.BLOCK, target) < 5) {
            if (level.setBlock(target, ModBlocks.airLight.get().defaultBlockState()
                    .setValue(AirLight.AGE, 0), 2)) {
                tile.childLights.add(target);
                tile.setChanged();
            }
        }
    }

    public void removeChildLights() {
        if (level == null || level.isClientSide) return;
        for (BlockPos pos : childLights) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            if (level.getBlockState(pos).is(ModBlocks.airLight.get())) {
                level.removeBlock(pos, false);
            }
        }
        childLights.clear();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        List<Integer> encoded = new ArrayList<>(childLights.size());
        for (BlockPos child : childLights) {
            encoded.add(encodePosition(worldPosition, child));
        }
        tag.put("lights", new IntArrayTag(encoded));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        childLights.clear();
        if (tag.contains("lights", CompoundTag.TAG_INT_ARRAY)) {
            BlockPos origin = new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
            int[] encoded = tag.getIntArray("lights");
            for (int value : encoded) {
                childLights.add(decodePosition(origin, value));
            }
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    public static int encodePosition(BlockPos origin, BlockPos target) {
        int x = target.getX() - origin.getX();
        int y = target.getY() - origin.getY();
        int z = target.getZ() - origin.getZ();
        return ((x & 0xFF) << 16) + ((y & 0xFF) << 8) + (z & 0xFF);
    }

    public static BlockPos decodePosition(BlockPos origin, int pos) {
        int x = (byte) ((pos >> 16) & 0xFF);
        int y = (byte) ((pos >> 8) & 0xFF);
        int z = (byte) (pos & 0xFF);
        return origin.offset(x, y, z);
    }
}