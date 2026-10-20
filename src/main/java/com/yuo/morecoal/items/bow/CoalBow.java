package com.yuo.morecoal.items.bow;

import com.yuo.morecoal.entity.ModEntityTypes;
import com.yuo.morecoal.entity.TorchArrowEntity;
import com.yuo.morecoal.items.MoreCoalTags;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class CoalBow extends BowItem {

    public static final Predicate<ItemStack> TORCH_ARROWS = (stack) -> stack.is(MoreCoalTags.TORCH_ARROWS);

    public CoalBow() {
        super(new Properties().durability(384));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("morecoal.text.itemInfo.coal_bow"));
    }

    @Override
    public AbstractArrow customArrow(AbstractArrow arrow) {
        if (arrow.getOwner() instanceof LivingEntity){
            return new TorchArrowEntity(ModEntityTypes.TORCH_ARROW.get(), (LivingEntity) arrow.getOwner(), arrow.level());
        }
        return arrow;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return TORCH_ARROWS;
    }
}
