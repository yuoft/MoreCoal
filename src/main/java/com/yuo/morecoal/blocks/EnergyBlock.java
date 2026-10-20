package com.yuo.morecoal.blocks;

import com.yuo.morecoal.tiles.EnergyBlockTile;
import com.yuo.morecoal.tiles.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class EnergyBlock extends Block implements EntityBlock {

    public EnergyBlock() {
        super(Properties.of().sound(SoundType.STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("morecoal.text.blockInfo.energy_block"));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnergyBlockTile(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModTiles.ENERGY_BLOCK_TILE.get() ? (lvl, pos, st, be) -> EnergyBlockTile.tick(lvl, pos, st, (EnergyBlockTile) be) : null;
    }
}