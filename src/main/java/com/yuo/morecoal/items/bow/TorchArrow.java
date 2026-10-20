package com.yuo.morecoal.items.bow;

import com.yuo.morecoal.entity.ModEntityTypes;
import com.yuo.morecoal.entity.TorchArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

//箭物品
public class TorchArrow extends ArrowItem {
    public TorchArrow() {
        super(new Properties());
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity living) {
        return new TorchArrowEntity(ModEntityTypes.TORCH_ARROW.get(), living, level);
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
        int enchant = bow.getEnchantmentLevel(Enchantments.INFINITY_ARROWS);
        return enchant > 0 && this.getClass() == TorchArrow.class;
    }
}
