package com.yuo.morecoal.Tiles;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.IIntArray;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.items.ItemHandlerHelper;

import java.lang.reflect.Field;

//为上方熔炉提供热值
public class EnergyBlockTile extends TileEntity implements ITickableTileEntity {
    private static final Field FURNACE_DATA_FIELD = ObfuscationReflectionHelper.findField(AbstractFurnaceTileEntity.class, "field_214013_b");

    public EnergyBlockTile() {
        super(TileTypeRegistry.ENERGY_BLOCK_TILE.get());
    }

    @Override
    public void tick() {
        if (world == null || world.isRemote) return;
        BlockState state = world.getBlockState(pos.up()); //获取上方方块
        TileEntity tileEntity = world.getTileEntity(pos.up());
        if (tileEntity instanceof AbstractFurnaceTileEntity){
            AbstractFurnaceTileEntity furnaceTile = (AbstractFurnaceTileEntity) tileEntity;
            if (!isRun(furnaceTile)) return;
            if (world.getGameTime() % 20 == 0 ) { //每秒触发一次
                IIntArray data = getFurnaceData(furnaceTile);
                if (data != null){
                    int burnTime = data.get(0);
                    if (burnTime <= 0)
                        world.setBlockState(pos.up(), state.with(AbstractFurnaceBlock.LIT, true));
                    data.set(0, 200); //0为燃烧时间 提供热值
                }
            }
            for (int i = 0; i < 3; i++){ //加速
                furnaceTile.tick();
            }
        }

    }

    //获取熔炉数据
    private static IIntArray getFurnaceData(AbstractFurnaceTileEntity tile) {
        try {
            return (IIntArray) FURNACE_DATA_FIELD.get(tile);
        } catch (IllegalAccessException e) {
            System.out.println("Couldn't reflect furnace field: " + e.getMessage());
            return null;
        }
    }

    //判断是否提供热值
    private boolean isRun(AbstractFurnaceTileEntity furnaceTile){
        ItemStack stack1 = furnaceTile.getStackInSlot(1);
        ItemStack stack = furnaceTile.getStackInSlot(0);
        if (!stack1.isEmpty()) return false; //燃料为空
        if (stack.isEmpty()) return false; //待烧物品不为空
        return !getRecipeOutput(furnaceTile, stack).equals(stack); //无烧炼产物
    }

    //根据不同熔炉类型返回产物
    private ItemStack getRecipeOutput(AbstractFurnaceTileEntity furnaceTile, ItemStack stack) {
        if (world == null) return ItemStack.EMPTY;
        if (furnaceTile instanceof BlastFurnaceTileEntity) {  //高炉
            return world.getRecipeManager().getRecipe(IRecipeType.BLASTING, new Inventory(stack), world)
                    .map(BlastingRecipe::getRecipeOutput).filter(e -> !e.isEmpty())
                    .map(e -> ItemHandlerHelper.copyStackWithSize(e, stack.getCount() * e.getCount()))
                    .orElse(stack);
        } else if (furnaceTile instanceof SmokerTileEntity) { //烟熏炉
            return world.getRecipeManager().getRecipe(IRecipeType.SMOKING, new Inventory(stack), world)
                    .map(SmokingRecipe::getRecipeOutput).filter(e -> !e.isEmpty())
                    .map(e -> ItemHandlerHelper.copyStackWithSize(e, stack.getCount() * e.getCount()))
                    .orElse(stack);
        } else {
            return world.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(stack), world)
                    .map(FurnaceRecipe::getRecipeOutput).filter(e -> !e.isEmpty())
                    .map(e -> ItemHandlerHelper.copyStackWithSize(e, stack.getCount() * e.getCount()))
                    .orElse(stack);
        }
    }

}
