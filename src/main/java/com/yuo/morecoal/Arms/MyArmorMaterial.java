package com.yuo.morecoal.Arms;

import java.util.function.Supplier;

import com.yuo.morecoal.Items.MoreCoalItems;
import com.yuo.morecoal.MoreCoal;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
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
	COAL_INGOT(MoreCoal.MODID + ":" + "coal", 16, new int[] { 2, 5, 6, 2 }, 7, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F, 0,
			() -> Ingredient.fromItems(MoreCoalItems.coalIngot.get())),
	BEACON(MoreCoal.MODID + ":" + "beacon", 21, new int[] { 4, 7, 9, 5 }, 9, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F, 0,
			() -> Ingredient.fromItems(Blocks.BEACON)),
	GRASS(MoreCoal.MODID + ":" + "grass", 13, new int[] { 2, 5, 6, 2 }, 7, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F, 0,
			() -> Ingredient.fromItems(Blocks.GRASS)),
	OBSIDIAN(MoreCoal.MODID + ":" + "obsidian", 35, new int[] { 3, 6, 8, 4 }, 8, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 0.05f,
			() -> Ingredient.fromItems(Blocks.OBSIDIAN));

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
