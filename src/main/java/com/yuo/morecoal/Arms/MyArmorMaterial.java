package com.yuo.morecoal.Arms;

import java.util.function.Supplier;

import com.yuo.morecoal.Items.ItemRegistry;
import com.yuo.morecoal.MoreCoal;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * 盔甲材料类
 */
public enum MyArmorMaterial implements IArmorMaterial {
	//---------材质---耐久值----------护甲值-------附魔能力--------音效----------------------盔甲韧性- 击退抗性-修复材料
	COAL_INGOT(MoreCoal.MODID + ":" + "coal", 14, new int[] { 2, 3, 4, 2 }, 7, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F, 0,() -> {
		return Ingredient.fromItems(ItemRegistry.coalIngot.get());
	}),
	BEACON(MoreCoal.MODID + ":" + "beacon", 20, new int[] { 3, 5, 7, 3 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F, 0,() -> {
		return Ingredient.fromItems(Blocks.BEACON);
	}),
	GRASS(MoreCoal.MODID + ":" + "grass", 13, new int[] { 2, 3, 4, 2 }, 7, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F, 0,() -> {
		return Ingredient.fromItems(Blocks.GRASS);
	}),
	OBSIDIAN(MoreCoal.MODID + ":" + "obsidian", 25, new int[] { 3, 6, 8, 3 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F, 0.05f,() -> {
		return Ingredient.fromItems(Blocks.OBSIDIAN);
	});

	private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final LazyValue<Ingredient> repairMaterial;
	private final float knockbackesistance;

	MyArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, SoundEvent equipSoundIn, float toughnessIn, float knockbackesistanceIn, Supplier<Ingredient> repairMaterialSupplier) {
	      this.name = nameIn;
	      this.maxDamageFactor = maxDamageFactorIn;
	      this.damageReductionAmountArray = damageReductionAmountsIn;
	      this.enchantability = enchantabilityIn;
	      this.soundEvent = equipSoundIn;
	      this.toughness = toughnessIn;
	      this.repairMaterial = new LazyValue<>(repairMaterialSupplier);
	      this.knockbackesistance = knockbackesistanceIn;
	   }

	public int getDurability(EquipmentSlotType slotIn) {
		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
	}

	public int getDamageReductionAmount(EquipmentSlotType slotIn) {
		return this.damageReductionAmountArray[slotIn.getIndex()];
	}

	public int getEnchantability() {
		return this.enchantability;
	}

	public SoundEvent getSoundEvent() {
		return this.soundEvent;
	}

	public Ingredient getRepairMaterial() {
		return this.repairMaterial.getValue();
	}

	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return this.name;
	}

	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackesistance;
	}

}
