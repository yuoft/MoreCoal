package com.yuo.morecoal.tiles;

import com.yuo.morecoal.MoreCoal;
import com.yuo.morecoal.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//方块实体类型注册
public class ModTiles {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MoreCoal.MOD_ID);

    public static final RegistryObject<BlockEntityType<EnergyBlockTile>> ENERGY_BLOCK_TILE = TILE_ENTITIES.register("energy_block_tile",
            () -> BlockEntityType.Builder.of(EnergyBlockTile::new, ModBlocks.energyBlock.get()).build(null));
    public static final RegistryObject<BlockEntityType<SuperLampTile>> SUPER_LAMP_TILE = TILE_ENTITIES.register("super_lamp_tile",
            () -> BlockEntityType.Builder.of(SuperLampTile::new, ModBlocks.superLamp.get()).build(null));
    public static final RegistryObject<BlockEntityType<TorchPlacerTile>> TORCH_PLACER_TILE = TILE_ENTITIES.register("torch_placer_tile",
            () -> BlockEntityType.Builder.of(TorchPlacerTile::new, ModBlocks.torchPlacer.get()).build(null));

}
