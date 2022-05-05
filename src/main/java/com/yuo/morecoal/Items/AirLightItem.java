package com.yuo.morecoal.Items;

import com.yuo.morecoal.Blocks.BlockRegistry;
import com.yuo.morecoal.tab.ModGroup;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class AirLightItem extends Item {
    public AirLightItem() {
        super(new Properties().group(ModGroup.myGroup));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("morecoal.text.itemInfo.air_light"));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        Direction face = context.getFace();
        if (face == Direction.UP){
            BlockState state = world.getBlockState(pos);
            BlockState blockState = world.getBlockState(pos.up());
            if (!state.getMaterial().isLiquid() && blockState.getMaterial().isReplaceable() && state.getMaterial().isSolid()){
                if (world.setBlockState(pos.up(), BlockRegistry.airLight.get().getDefaultState(), 2))
                    return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
}
