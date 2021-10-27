package com.yuo.morecoal.Blocks;

import com.yuo.morecoal.Event.EventHandler;
import com.yuo.morecoal.Items.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;

public class FallBlock extends FallingBlock {

    public FallBlock() {
        super(Properties.create(Material.ANVIL).harvestTool(ToolType.PICKAXE).harvestLevel(-1)
                .hardnessAndResistance(5, 6).sound(SoundType.ANVIL));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("morecoal.text.blockInfo.fall_block"));
    }

    @Override
    public void onEndFalling(World worldIn, BlockPos pos, BlockState fallingState, BlockState hitState, FallingBlockEntity fallingBlock) {
        List<ItemEntity> itemEntities = worldIn.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos));
        for (ItemEntity itemEntity : itemEntities){
            boolean willDead = false;
            for (int i = 0; i < itemEntity.getItem().getCount(); i++) {
                Item item = itemEntity.getItem().getItem();
                if (item.equals(Items.COAL_BLOCK)) {
                    worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ItemRegistry.coalIngot.get())));
                    willDead = true;
                }
                if (item.equals(ItemRegistry.moreCoalOre.get()) || item.equals(ItemRegistry.netherMoreCoalOre.get())
                        || item.equals(ItemRegistry.endMoreCoalOre.get())){
                    EventHandler.dropsSuperCoal(worldIn, pos, false);
                    willDead = true;
                }
            }
            if (willDead)
                itemEntity.remove();
        }
    }
}
