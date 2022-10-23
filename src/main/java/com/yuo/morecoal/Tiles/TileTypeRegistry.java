package com.yuo.morecoal.Tiles;

import com.yuo.morecoal.Blocks.MoreCoalBlocks;
import com.yuo.morecoal.MoreCoal;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//方块实体类型注册
public class TileTypeRegistry {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MoreCoal.MODID);

    public static final RegistryObject<TileEntityType<EnergyBlockTile>> ENERGY_BLOCK_TILE = TILE_ENTITIES.register("energy_block_tile",
            () -> TileEntityType.Builder.create(EnergyBlockTile::new, MoreCoalBlocks.energyBlock.get()).build(null));
    public static final RegistryObject<TileEntityType<SuperLampTile>> SUPER_LAMP_TILE = TILE_ENTITIES.register("super_lamp_tile",
            () -> TileEntityType.Builder.create(SuperLampTile::new, MoreCoalBlocks.superLamp.get()).build(null));
    public static final RegistryObject<TileEntityType<TorchPlacerTile>> TORCH_PLACER_TILE = TILE_ENTITIES.register("torch_placer_tile",
            () -> TileEntityType.Builder.create(TorchPlacerTile::new, MoreCoalBlocks.torchPlacer.get()).build(null));

}
