package com.yuo.morecoal.Items.Bow;

import com.yuo.morecoal.Entity.EntityTypeRegistry;
import com.yuo.morecoal.Entity.TorchArrowEntity;
import com.yuo.morecoal.Items.MoreCoalTags;
import com.yuo.morecoal.tab.ModGroup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class CoalBow extends BowItem {

    public static final Predicate<ItemStack> TORCH_ARROWS = (stack) -> {
        ITag<Item> tag = ItemTags.getCollection().get(MoreCoalTags.TORCH_ARROWS);
        return stack.getItem().isIn(tag);
    };

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("morecoal.text.itemInfo.coal_bow"));
    }

    public CoalBow() {
        super(new Properties().maxDamage(384).group(ModGroup.myGroup));
    }

    @Override
    public AbstractArrowEntity customArrow(AbstractArrowEntity arrow) {
        if (arrow.getEntity() instanceof LivingEntity){
            return new TorchArrowEntity(EntityTypeRegistry.TORCH_ARROW.get(), (LivingEntity) arrow.getEntity(), arrow.world);
        }
        return arrow;
    }

    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return TORCH_ARROWS;
    }
}
