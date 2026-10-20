package com.yuo.morecoal.blocks;

import com.yuo.morecoal.event.EventHandler;
import com.yuo.morecoal.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class FallBlock extends FallingBlock {

    public FallBlock() {
        super(Properties.of().mapColor(MapColor.COLOR_GRAY).strength(5, 6).sound(SoundType.ANVIL));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter getter, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("morecoal.text.blockInfo.fall_block"));
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        // 使用略微膨胀的碰撞箱，确保能捕捉到掉在方块上表面的物品
        AABB area = new AABB(pos).inflate(0.5, 0.5, 0.5);

        List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, area);
        for (ItemEntity itemEntity : itemEntities) {
            ItemStack stack = itemEntity.getItem();
            if (stack.isEmpty()) continue;

            Item item = stack.getItem();
            int count = stack.getCount(); // 获取整个堆叠的数量

            boolean consumed = false;

            if (item == Items.COAL_BLOCK) {
                // 假设转换比例是 1 煤炭块 -> 9 煤炭锭，请根据模组设定调整
                int ingotCount = count * 9;
                ItemStack result = new ItemStack(ModItems.coalIngot.get(), ingotCount);
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, result));
                consumed = true;
            } else if (item == ModItems.moreCoalOre.get() || item == ModItems.moreCoalDeepOre.get()
                    || item == ModItems.netherMoreCoalOre.get() || item == ModItems.endMoreCoalOre.get()) {
                // 根据堆叠数量调用相应次数的 superCoal 掉落逻辑
                for (int i = 0; i < count; i++) {
                    EventHandler.dropsSuperCoal(level, pos, false);
                }
                consumed = true;
            }

            if (consumed) {
                itemEntity.discard();
            }
        }

        for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, area)) {
            living.hurt(living.damageSources().anvil(entity), 2);
        }
    }
}
