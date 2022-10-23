package com.yuo.morecoal.Items.Bow;

import com.yuo.morecoal.Entity.EntityTypeRegistry;
import com.yuo.morecoal.Entity.TorchArrowEntity;
import com.yuo.morecoal.tab.ModGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

//箭物品
public class TorchArrow extends ArrowItem {
    public TorchArrow() {
        super(new Properties().group(ModGroup.myGroup));
    }

    //创建箭实体
    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        return new TorchArrowEntity(EntityTypeRegistry.TORCH_ARROW.get(), shooter, worldIn);
    }

    //是否无限
    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.entity.player.PlayerEntity player) {
        int enchant = net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(net.minecraft.enchantment.Enchantments.INFINITY, bow);
        return enchant > 0 && this.getClass() == TorchArrow.class;
    }
}
