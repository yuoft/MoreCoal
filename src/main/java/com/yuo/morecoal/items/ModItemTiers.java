package com.yuo.morecoal.items;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ModItemTiers implements Tier {
    COAL_INGOT(356, 5.0f, 3.5f, 2, 7, () -> Ingredient.of(ModItems.coalIngot.get()));

    private final int maxUses;//耐久
    private final float efficiency;//使用效率
    private final float attackDamage;//工具伤害
    private final int harvestLevel;//工具等级
    private final int enchantability;//附魔等级
    private final LazyLoadedValue<Ingredient> repairMaterial;//修复材料

    ModItemTiers(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
