package com.yuo.morecoal.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SmokerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;

import java.lang.reflect.Field;

public class EnergyBlockTile extends BlockEntity {

    private static final Field FURNACE_DATA_FIELD;

    static {
        try {
            FURNACE_DATA_FIELD = AbstractFurnaceBlockEntity.class.getDeclaredField("dataAccess");
            FURNACE_DATA_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Failed to locate furnace dataAccess field", e);
        }
    }

    public EnergyBlockTile(BlockPos pos, BlockState state) {
        super(ModTiles.ENERGY_BLOCK_TILE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, EnergyBlockTile tile) {
        if (level.isClientSide) return;

        BlockPos above = pos.above();
        BlockEntity be = level.getBlockEntity(above);
        if (!(be instanceof AbstractFurnaceBlockEntity furnace)) return;

        if (!tile.isRun(furnace)) return;

        if (level.getGameTime() % 20 == 0) {
            ContainerData data = tile.getFurnaceData(furnace);
            if (data != null) {
                int burnTime = data.get(0);
                if (burnTime <= 0) {
                    BlockState aboveState = level.getBlockState(above);
                    level.setBlock(above, aboveState.setValue(AbstractFurnaceBlock.LIT, true), 2);
                }
                data.set(0, 200);
            }
        }

        // 加速：直接调用方块实体的 tick 逻辑
        for (int i = 0; i < 3; i++) {
            AbstractFurnaceBlockEntity.serverTick(level, above, level.getBlockState(above), furnace);
        }
    }

    private ContainerData getFurnaceData(AbstractFurnaceBlockEntity furnace) {
        try {
            return (ContainerData) FURNACE_DATA_FIELD.get(furnace);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private boolean isRun(AbstractFurnaceBlockEntity furnace) {
        ItemStack fuel = furnace.getItem(1);
        ItemStack input = furnace.getItem(0);
        if (!fuel.isEmpty()) return false;
        if (input.isEmpty()) return false;
        return !getRecipeOutput(furnace, input).equals(input);
    }

    private ItemStack getRecipeOutput(AbstractFurnaceBlockEntity furnace, ItemStack stack) {
        if (level == null) return ItemStack.EMPTY;

        RecipeType<? extends AbstractCookingRecipe> type;
        if (furnace instanceof BlastFurnaceBlockEntity) {
            type = RecipeType.BLASTING;
        } else if (furnace instanceof SmokerBlockEntity) {
            type = RecipeType.SMOKING;
        } else {
            type = RecipeType.SMELTING;
        }

        return level.getRecipeManager()
                .getRecipeFor((RecipeType<AbstractCookingRecipe>) type, new SimpleContainer(stack), level)
                .map(holder -> holder.getResultItem(level.registryAccess()))
                .filter(result -> !result.isEmpty())
                .map(result -> ItemHandlerHelper.copyStackWithSize(result, stack.getCount() * result.getCount()))
                .orElse(stack);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
    }
}