package com.yuo.morecoal.Items;

import com.yuo.morecoal.Arms.*;
import com.yuo.morecoal.Blocks.MoreCoalBlocks;
import com.yuo.morecoal.Items.Bow.CoalBow;
import com.yuo.morecoal.Items.Bow.TorchArrow;
import com.yuo.morecoal.MoreCoal;
import com.yuo.morecoal.tab.ModGroup;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//物品注册管理器
public class MoreCoalItems {
	//创建注册器。ForgeRegistries.ITEMS代表了我们要注册的是物品，第二个参数填入的应该是你的modId。
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreCoal.MODID);

	//注册物品。一个是「注册名」，还有一个就是你要注册对象的实例
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
	public static RegistryObject<ArmorItem> coalHead = ITEMS.register("coal_head",
			() -> new CoalArms( EquipmentSlotType.HEAD));
	public static RegistryObject<ArmorItem> coalChest = ITEMS.register("coal_chest",
			() -> new CoalArms(EquipmentSlotType.CHEST));
	public static RegistryObject<ArmorItem> coalLegs = ITEMS.register("coal_legs",
			() -> new CoalArms(EquipmentSlotType.LEGS));
	public static RegistryObject<ArmorItem> coalFeet = ITEMS.register("coal_feet",
			() -> new CoalArms(EquipmentSlotType.FEET));
	public static RegistryObject<ArmorItem> grassHead = ITEMS.register("grass_head",
			() -> new GrassArms(EquipmentSlotType.HEAD));
	public static RegistryObject<ArmorItem> grassChest = ITEMS.register("grass_chest",
			() -> new GrassArms(EquipmentSlotType.CHEST));
	public static RegistryObject<ArmorItem> grassLegs = ITEMS.register("grass_legs",
			() -> new GrassArms(EquipmentSlotType.LEGS));
	public static RegistryObject<ArmorItem> grassFeet = ITEMS.register("grass_feet",
			() -> new GrassArms(EquipmentSlotType.FEET));
	public static RegistryObject<ArmorItem> beaconHead = ITEMS.register("beacon_head",
			() -> new BeaconArms(EquipmentSlotType.HEAD));
	public static RegistryObject<ArmorItem> beaconChest = ITEMS.register("beacon_chest",
			() -> new BeaconArms(EquipmentSlotType.CHEST));
	public static RegistryObject<ArmorItem> beaconLegs = ITEMS.register("beacon_legs",
			() -> new BeaconArms(EquipmentSlotType.LEGS));
	public static RegistryObject<ArmorItem> beaconFeet = ITEMS.register("beacon_feet",
			() -> new BeaconArms(EquipmentSlotType.FEET));
	public static RegistryObject<ArmorItem> obsidianHead = ITEMS.register("obsidian_head",
			() -> new ObsidianArms(EquipmentSlotType.HEAD));
	public static RegistryObject<ArmorItem> obsidianChest = ITEMS.register("obsidian_chest",
			() -> new ObsidianArms(EquipmentSlotType.CHEST));
	public static RegistryObject<ArmorItem> obsidianLegs = ITEMS.register("obsidian_legs",
			() -> new ObsidianArms(EquipmentSlotType.LEGS));
	public static RegistryObject<ArmorItem> obsidianFeet = ITEMS.register("obsidian_feet",
			() -> new ObsidianArms(EquipmentSlotType.FEET));

	//注册方块物品
	public static RegistryObject<BlockItem> ironCoalBlock = ITEMS.register("iron_coal_block",
			() -> new BlockItem(MoreCoalBlocks.ironCoalBlock.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> goldCoalBlock = ITEMS.register("gold_coal_block",
			() -> new BlockItem(MoreCoalBlocks.goldCoalBlock.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> diamondCoalBlock = ITEMS.register("diamond_coal_block",
			() -> new BlockItem(MoreCoalBlocks.diamondCoalBlock.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> netheriteCoalBlock = ITEMS.register("netherite_coal_block",
			() -> new BlockItem(MoreCoalBlocks.netheriteCoalBlock.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> lapisCoalBlock = ITEMS.register("lapis_coal_block",
			() -> new BlockItem(MoreCoalBlocks.lapisCoalBlock.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> redstoneCoalBlock = ITEMS.register("redstone_coal_block",
			() -> new BlockItem(MoreCoalBlocks.redstoneCoalBlock.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> emeraldCoalBlock = ITEMS.register("emerald_coal_block",
			() -> new BlockItem(MoreCoalBlocks.emeraldCoalBlock.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> lavaCoalBlock = ITEMS.register("lava_coal_block",
			() -> new BlockItem(MoreCoalBlocks.lavaCoalBlock.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> fallBlock = ITEMS.register("fall_block",
			() -> new BlockItem(MoreCoalBlocks.fallBlock.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> moreCoalOre = ITEMS.register("more_coal_ore",
			() -> new BlockItem(MoreCoalBlocks.moreCoalOre.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> endMoreCoalOre = ITEMS.register("end_more_coal_ore",
			() -> new BlockItem(MoreCoalBlocks.endMoreCoalOre.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> netherMoreCoalOre = ITEMS.register("nether_more_coal_ore",
			() -> new BlockItem(MoreCoalBlocks.netherMoreCoalOre.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> energyBlock = ITEMS.register("energy_block",
			() -> new BlockItem(MoreCoalBlocks.energyBlock.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> superLamp = ITEMS.register("super_lamp",
			() -> new BlockItem(MoreCoalBlocks.superLamp.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> torchPlacer = ITEMS.register("torch_placer",
			() -> new BlockItem(MoreCoalBlocks.torchPlacer.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> bigTorch = ITEMS.register("big_torch",
			() -> new BlockItem(MoreCoalBlocks.bigTorch.get(), new Item.Properties().group(ModGroup.myGroup)));
	public static RegistryObject<BlockItem> bigTorch0 = ITEMS.register("big_torch0",
			() -> new BlockItem(MoreCoalBlocks.bigTorch0.get(), new Item.Properties().group(ModGroup.myGroup)));
}
