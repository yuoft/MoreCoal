package com.yuo.morecoal.Items;

import com.yuo.morecoal.tab.ModGroup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CoalAxe extends AxeItem {
    public CoalAxe() {
        super(MyItemTier.COAL_INGOT, 5, -3.1f, new Item.Properties().group(ModGroup.myGroup));
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setFire(1);
        return true;
    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("morecoal.text.itemInfo.coal_axe"));
        tooltip.add(new TranslationTextComponent("morecoal.text.itemInfo.coal_tool"));
    }
//    @Override
//    public ActionResultType onItemUse(ItemUseContext context) {
//        World world = context.getWorld();
//        BlockPos pos = context.getPos();
//        BlockState blockState = world.getBlockState(pos);
//        PlayerEntity player = context.getPlayer();
//        boolean solid = blockState.getMaterial().isSolid();
//        player.sendStatusMessage(new StringTextComponent("实体：" + Boolean.toString(solid)), true);
//        return ActionResultType.SUCCESS;
//    }
}
