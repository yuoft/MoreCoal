package com.yuo.morecoal.blocks;

import com.yuo.morecoal.MoreCoal;
import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//方块注册
public class ModBlocks {
	//同物品
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MoreCoal.MOD_ID);

	//高级煤炭块
	public static RegistryObject<Block> ironCoalBlock = BLOCKS.register("iron_coal_block", () -> new MoreCoalBlock(5, 9, SoundType.STONE));
	public static RegistryObject<Block> goldCoalBlock = BLOCKS.register("gold_coal_block", () -> new MoreCoalBlock(6, 10, SoundType.STONE));
	public static RegistryObject<Block> diamondCoalBlock = BLOCKS.register("diamond_coal_block", () -> new MoreCoalBlock(10, 15, SoundType.STONE));
	public static RegistryObject<Block> netheriteCoalBlock = BLOCKS.register("netherite_coal_block", () -> new MoreCoalBlock(15, 20, SoundType.STONE));
	public static RegistryObject<Block> lapisCoalBlock = BLOCKS.register("lapis_coal_block", () -> new MoreCoalBlock(7, 12, SoundType.STONE));
	public static RegistryObject<Block> redstoneCoalBlock = BLOCKS.register("redstone_coal_block", () -> new MoreCoalBlock(7, 11, SoundType.STONE));
	public static RegistryObject<Block> emeraldCoalBlock = BLOCKS.register("emerald_coal_block", () -> new MoreCoalBlock(11, 15, SoundType.STONE));
	public static RegistryObject<Block> lavaCoalBlock = BLOCKS.register("lava_coal_block", () -> new MoreCoalBlock(3, 8, SoundType.STONE));

	//重力方块
	public static RegistryObject<Block> fallBlock = BLOCKS.register("fall_block", FallBlock::new);

	//混合煤炭矿石
	public static RegistryObject<Block> moreCoalOre = BLOCKS.register("more_coal_ore",
			() -> new MoreCoalOre(5, 10, 2, 0,4));
	public static RegistryObject<Block> moreCoalDeepOre = BLOCKS.register("more_coal_deep_ore",
			() -> new MoreCoalOre(6, 11, 2, 0,6));
	public static RegistryObject<Block> netherMoreCoalOre = BLOCKS.register("nether_more_coal_ore",
			() -> new MoreCoalOre(7, 12, 2, 1,5));
	public static RegistryObject<Block> endMoreCoalOre = BLOCKS.register("end_more_coal_ore",
			() -> new MoreCoalOre(6, 11, 2, 2,6));

	//能量核心
	public static RegistryObject<Block> energyBlock = BLOCKS.register("energy_block", EnergyBlock::new);
	public static RegistryObject<Block> superLamp = BLOCKS.register("super_lamp", SuperLamp::new);
	public static RegistryObject<Block> airLight = BLOCKS.register("air_light", AirLight::new);
	public static RegistryObject<Block> torchPlacer = BLOCKS.register("torch_placer", TorchPlacer::new);
	public static RegistryObject<Block> bigTorch = BLOCKS.register("big_torch", BigTorch::new);
	public static RegistryObject<Block> bigTorch0 = BLOCKS.register("big_torch0", BigTorch0::new);

}
