package com.yuo.morecoal.items;

import com.yuo.morecoal.MoreCoal;
import com.yuo.morecoal.blocks.ModBlocks;
import com.yuo.morecoal.items.arms.BeaconArms;
import com.yuo.morecoal.items.arms.CoalArms;
import com.yuo.morecoal.items.arms.GrassArms;
import com.yuo.morecoal.items.arms.ObsidianArms;
import com.yuo.morecoal.items.bow.CoalBow;
import com.yuo.morecoal.items.bow.TorchArrow;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//物品注册管理器
public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreCoal.MOD_ID);

	public static RegistryObject<Item> ironCoal = ITEMS.register("iron_coal", MoreCoalItem::new);
	public static RegistryObject<Item> goldCoal = ITEMS.register("gold_coal", MoreCoalItem::new);
	public static RegistryObject<Item> diamondCoal = ITEMS.register("diamond_coal", MoreCoalItem::new);
	public static RegistryObject<Item> netheriteCoal = ITEMS.register("netherite_coal", MoreCoalItem::new);
	public static RegistryObject<Item> lapisCoal = ITEMS.register("lapis_coal", MoreCoalItem::new);
	public static RegistryObject<Item> redstoneCoal = ITEMS.register("redstone_coal", MoreCoalItem::new);
	public static RegistryObject<Item> emeraldCoal = ITEMS.register("emerald_coal", MoreCoalItem::new);
	public static RegistryObject<Item> lavaCoal = ITEMS.register("lava_coal", MoreCoalItem::new);
	public static RegistryObject<Item> coalIngot = ITEMS.register("coal_ingot", MoreCoalItem::new);
	public static RegistryObject<Item> airLight = ITEMS.register("air_light", AirLightItem::new);

	//工具
	public static RegistryObject<Item> coalPickaxe = ITEMS.register("coal_pickaxe", CoalPickaxe::new);
	public static RegistryObject<Item> coalAxe = ITEMS.register("coal_axe", CoalAxe::new);
	public static RegistryObject<Item> coalSword = ITEMS.register("coal_sword", CoalSword::new);

	//弓和箭
	public static RegistryObject<BowItem> coalBow = ITEMS.register("coal_bow", CoalBow::new);
	public static RegistryObject<Item> torchArrow = ITEMS.register("torch_arrow", TorchArrow::new);

	//装备
	public static RegistryObject<ArmorItem> coalHead = ITEMS.register("coal_head", () -> new CoalArms(Type.HELMET));
	public static RegistryObject<ArmorItem> coalChest = ITEMS.register("coal_chest", () -> new CoalArms(Type.CHESTPLATE));
	public static RegistryObject<ArmorItem> coalLegs = ITEMS.register("coal_legs", () -> new CoalArms(Type.LEGGINGS));
	public static RegistryObject<ArmorItem> coalFeet = ITEMS.register("coal_feet", () -> new CoalArms(Type.BOOTS));
	public static RegistryObject<ArmorItem> grassHead = ITEMS.register("grass_head", () -> new GrassArms(Type.HELMET));
	public static RegistryObject<ArmorItem> grassChest = ITEMS.register("grass_chest", () -> new GrassArms(Type.CHESTPLATE));
	public static RegistryObject<ArmorItem> grassLegs = ITEMS.register("grass_legs", () -> new GrassArms(Type.LEGGINGS));
	public static RegistryObject<ArmorItem> grassFeet = ITEMS.register("grass_feet", () -> new GrassArms(Type.BOOTS));
	public static RegistryObject<ArmorItem> beaconHead = ITEMS.register("beacon_head", () -> new BeaconArms(Type.HELMET));
	public static RegistryObject<ArmorItem> beaconChest = ITEMS.register("beacon_chest", () -> new BeaconArms(Type.CHESTPLATE));
	public static RegistryObject<ArmorItem> beaconLegs = ITEMS.register("beacon_legs", () -> new BeaconArms(Type.LEGGINGS));
	public static RegistryObject<ArmorItem> beaconFeet = ITEMS.register("beacon_feet", () -> new BeaconArms(Type.BOOTS));
	public static RegistryObject<ArmorItem> obsidianHead = ITEMS.register("obsidian_head", () -> new ObsidianArms(Type.HELMET));
	public static RegistryObject<ArmorItem> obsidianChest = ITEMS.register("obsidian_chest", () -> new ObsidianArms(Type.CHESTPLATE));
	public static RegistryObject<ArmorItem> obsidianLegs = ITEMS.register("obsidian_legs", () -> new ObsidianArms(Type.LEGGINGS));
	public static RegistryObject<ArmorItem> obsidianFeet = ITEMS.register("obsidian_feet", () -> new ObsidianArms(Type.BOOTS));

	//注册方块物品
	public static final Item.Properties BLOCK_TAB = new Item.Properties();
	public static RegistryObject<BlockItem> ironCoalBlock = ITEMS.register("iron_coal_block", () -> new BlockItem(ModBlocks.ironCoalBlock.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> goldCoalBlock = ITEMS.register("gold_coal_block", () -> new BlockItem(ModBlocks.goldCoalBlock.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> diamondCoalBlock = ITEMS.register("diamond_coal_block", () -> new BlockItem(ModBlocks.diamondCoalBlock.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> netheriteCoalBlock = ITEMS.register("netherite_coal_block", () -> new BlockItem(ModBlocks.netheriteCoalBlock.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> lapisCoalBlock = ITEMS.register("lapis_coal_block", () -> new BlockItem(ModBlocks.lapisCoalBlock.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> redstoneCoalBlock = ITEMS.register("redstone_coal_block", () -> new BlockItem(ModBlocks.redstoneCoalBlock.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> emeraldCoalBlock = ITEMS.register("emerald_coal_block", () -> new BlockItem(ModBlocks.emeraldCoalBlock.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> lavaCoalBlock = ITEMS.register("lava_coal_block", () -> new BlockItem(ModBlocks.lavaCoalBlock.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> fallBlock = ITEMS.register("fall_block", () -> new BlockItem(ModBlocks.fallBlock.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> moreCoalOre = ITEMS.register("more_coal_ore", () -> new BlockItem(ModBlocks.moreCoalOre.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> moreCoalDeepOre = ITEMS.register("more_coal_deep_ore", () -> new BlockItem(ModBlocks.moreCoalDeepOre.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> endMoreCoalOre = ITEMS.register("end_more_coal_ore", () -> new BlockItem(ModBlocks.endMoreCoalOre.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> netherMoreCoalOre = ITEMS.register("nether_more_coal_ore", () -> new BlockItem(ModBlocks.netherMoreCoalOre.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> energyBlock = ITEMS.register("energy_block", () -> new BlockItem(ModBlocks.energyBlock.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> superLamp = ITEMS.register("super_lamp", () -> new BlockItem(ModBlocks.superLamp.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> torchPlacer = ITEMS.register("torch_placer", () -> new BlockItem(ModBlocks.torchPlacer.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> bigTorch = ITEMS.register("big_torch", () -> new BlockItem(ModBlocks.bigTorch.get(), BLOCK_TAB));
	public static RegistryObject<BlockItem> bigTorch0 = ITEMS.register("big_torch0", () -> new BlockItem(ModBlocks.bigTorch0.get(), BLOCK_TAB));
}
