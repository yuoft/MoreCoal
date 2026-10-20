package com.yuo.morecoal.items.arms;

import com.yuo.morecoal.MoreCoal;
import com.yuo.morecoal.items.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

/**
 * 盔甲材料类
 */
public enum ModArmorMaterials implements ArmorMaterial {
	//---------材质---耐久值----------护甲值-------附魔能力--------音效----------------------盔甲韧性- 击退抗性-修复材料
	COAL_INGOT(MoreCoal.MOD_ID + ":" + "coal", 16, new int[] { 2, 5, 6, 2 }, 7, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.5F, 0,
			() -> Ingredient.of(ModItems.coalIngot.get())),
	BEACON(MoreCoal.MOD_ID + ":" + "beacon", 21, new int[] { 4, 7, 9, 5 }, 9, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.5F, 0,
			() -> Ingredient.of(Blocks.BEACON)),
	GRASS(MoreCoal.MOD_ID + ":" + "grass", 13, new int[] { 2, 5, 6, 2 }, 7, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.5F, 0,
			() -> Ingredient.of(Blocks.GRASS)),
	OBSIDIAN(MoreCoal.MOD_ID + ":" + "obsidian", 35, new int[] { 3, 6, 8, 4 }, 8, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.05f,
			() -> Ingredient.of(Blocks.OBSIDIAN));

	private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };
	private final String name;
	private final int maxDamageFactor;
	private final int[] damageReductionAmountArray;
	private final int enchantability;
	private final SoundEvent soundEvent;
	private final float toughness;
	private final LazyLoadedValue<Ingredient> repairMaterial;
	private final float knockbackesistance;

	ModArmorMaterials(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn, int enchantabilityIn, SoundEvent equipSoundIn, float toughnessIn, float knockbackesistanceIn, Supplier<Ingredient> repairMaterialSupplier) {
	      this.name = nameIn;
	      this.maxDamageFactor = maxDamageFactorIn;
	      this.damageReductionAmountArray = damageReductionAmountsIn;
	      this.enchantability = enchantabilityIn;
	      this.soundEvent = equipSoundIn;
	      this.toughness = toughnessIn;
	      this.repairMaterial = new LazyLoadedValue<>(repairMaterialSupplier);
	      this.knockbackesistance = knockbackesistanceIn;
	   }

	@Override
	public int getDurabilityForType(Type type) {
		return MAX_DAMAGE_ARRAY[type.getSlot().getIndex()] * this.maxDamageFactor;
	}

	@Override
	public int getDefenseForType(Type type) {
		return this.damageReductionAmountArray[type.getSlot().getIndex()];
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantability;
	}

	@Override
	public SoundEvent getEquipSound() {
		return this.soundEvent;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairMaterial.get();
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
