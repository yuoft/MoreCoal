package com.yuo.morecoal.Blocks;

import com.yuo.morecoal.MoreCoal;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//方块注册
public class BlockRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MoreCoal.MODID);

    //高级煤炭块
	public static RegistryObject<Block> ironCoalBlock = BLOCKS.register("iron_coal_block", () -> {
        return new MoreCoalBlock(Material.ROCK, 1, ToolType.PICKAXE, 5, 5);
    });
	public static RegistryObject<Block> goldCoalBlock = BLOCKS.register("gold_coal_block", () -> {
        return new MoreCoalBlock(Material.ROCK, 2, ToolType.PICKAXE, 6, 6);
    });
	public static RegistryObject<Block> diamondCoalBlock = BLOCKS.register("diamond_coal_block", () -> {
        return new MoreCoalBlock(Material.ROCK, 3, ToolType.PICKAXE, 10, 10);
    });
	public static RegistryObject<Block> netheriteCoalBlock = BLOCKS.register("netherite_coal_block", () -> {
        return new MoreCoalBlock(Material.ROCK, 4, ToolType.PICKAXE, 15, 15);
    });
	public static RegistryObject<Block> lapisCoalBlock = BLOCKS.register("lapis_coal_block", () -> {
        return new MoreCoalBlock(Material.ROCK, 2, ToolType.PICKAXE, 7, 7);
    });
	public static RegistryObject<Block> redstoneCoalBlock = BLOCKS.register("redstone_coal_block", () -> {
        return new MoreCoalBlock(Material.ROCK, 2, ToolType.PICKAXE, 7, 7);
    });
	public static RegistryObject<Block> emeraldCoalBlock = BLOCKS.register("emerald_coal_block", () -> {
        return new MoreCoalBlock(Material.ROCK, 3, ToolType.PICKAXE, 11, 11);
    });
	public static RegistryObject<Block> lavaCoalBlock = BLOCKS.register("lava_coal_block", () -> {
        return new MoreCoalBlock(Material.ROCK, 1, ToolType.PICKAXE, 3, 3);
    });

    //重力方块
    public static RegistryObject<Block> fallBlock = BLOCKS.register("fall_block", FallBlock::new);

    //混合煤炭矿石
    public static RegistryObject<Block> moreCoalOre = BLOCKS.register("more_coal_ore", () ->{
        return new MoreCoalOre(Material.ROCK, 2, ToolType.PICKAXE, 5, 5, 5);
    });
    public static RegistryObject<Block> endMoreCoalOre = BLOCKS.register("end_more_coal_ore", () ->{
        return new MoreCoalOre(Material.ROCK, 2, ToolType.PICKAXE, 6, 6, 5);
    });
    public static RegistryObject<Block> netherMoreCoalOre = BLOCKS.register("nether_more_coal_ore", () ->{
        return new MoreCoalOre(Material.ROCK, 2, ToolType.PICKAXE, 7, 7, 5);
    });

    //能量核心
    public static RegistryObject<Block> energyBlock = BLOCKS.register("energy_block", EnergyBlock::new);
    public static RegistryObject<Block> superLamp = BLOCKS.register("super_lamp", SuperLamp::new);
    public static RegistryObject<Block> airLight = BLOCKS.register("air_light", AirLight::new);
    public static RegistryObject<Block> torchPlacer = BLOCKS.register("torch_placer", TorchPlacer::new);
    public static RegistryObject<Block> bigTorch = BLOCKS.register("big_torch", BigTorch::new);
    public static RegistryObject<Block> bigTorch0 = BLOCKS.register("big_torch0", BigTorch0::new);

}
