package com.yuo.morecoal.items;

import com.yuo.morecoal.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class AirLightItem extends Item {
    public AirLightItem() {
        super(new Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("morecoal.text.itemInfo.air_light"));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction face = context.getClickedFace();
        if (face == Direction.UP){
            BlockState state = world.getBlockState(pos);
            BlockState blockState = world.getBlockState(pos.above());
            if (!state.liquid() && blockState.canBeReplaced() && state.isSolid()){
                if (world.setBlockAndUpdate(pos.above(), ModBlocks.airLight.get().defaultBlockState()))
                    return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(context);
    }
}
