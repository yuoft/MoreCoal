package com.yuo.morecoal.tiles;

import com.yuo.morecoal.blocks.TorchPlacer;
import com.yuo.morecoal.gui.TorchPlacerContainer;
import com.yuo.morecoal.gui.TorchPlacerIntArray;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

public class TorchPlacerTile extends BlockEntity implements WorldlyContainer, MenuProvider {

    private int burnTime; //燃烧时间
    private int burnCount; //可放置数量
    private int energy;
    private NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY);
    private final TorchPlacerIntArray data = new TorchPlacerIntArray();

    private static final int[] SLOTS_FUEL = {0};
    private static final int[] SLOTS_ITEMS = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    public TorchPlacerTile(BlockPos pos, BlockState state) {
        super(ModTiles.TORCH_PLACER_TILE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TorchPlacerTile tile) {
        if (level.isClientSide) return;

        boolean wasBurning = tile.isBurning();
        //机器运行逻辑
        if (tile.isBurning()) {
            --tile.burnTime;
            tile.data.set(0, tile.burnTime);
            tile.data.set(2, tile.energy);
        }

        ItemStack fuel = tile.items.get(0);
        if ((tile.isBurning() || !fuel.isEmpty()) && tile.energy < 10400) {
            if (!tile.isBurning()) {
                tile.burnTime = ForgeHooks.getBurnTime(fuel, null);
                tile.burnCount = tile.burnTime;
                tile.data.set(1, tile.burnCount);
                if (tile.isBurning()) {
                    tile.setChanged();
                    if (fuel.hasCraftingRemainingItem()) {
                        tile.items.set(0, fuel.getCraftingRemainingItem());
                    } else {
                        fuel.shrink(1);
                        if (fuel.isEmpty()) {
                            tile.items.set(0, fuel.getCraftingRemainingItem());
                        }
                    }
                }
            }
            if (tile.isBurning() && tile.energy <= 10400) {
                tile.energy += 2;
                tile.data.set(2, tile.energy);
            }
        }

        if (wasBurning != tile.isBurning()) {
            tile.setChanged();
            level.setBlock(pos, state.setValue(AbstractFurnaceBlock.LIT, tile.isBurning()), 3);
        }

        //火把放置逻辑
        if (pos.getY() <= -61 || pos.getY() >= level.getMaxBuildHeight() - 1) return;
        if (level.getGameTime() % 10 != 0) return;

        if (!state.getValue(TorchPlacer.WORK) || tile.energy < 200 || !tile.hasTorch()) return;

        int slot = tile.getTorchSlot();
        if (slot < 0) return;

        for (int ring = 1; ring <= 8; ring++) {
            boolean placed = false;
            for (BlockPos candidate : BlockPos.betweenClosed(
                    pos.offset(-8 * ring, 0, -8 * ring),
                    pos.offset(8 * ring, 0, 8 * ring))) {

                if (Math.abs(candidate.getX() - pos.getX()) % 8 != 0 || Math.abs(candidate.getZ() - pos.getZ()) % 8 != 0 || candidate.equals(pos)) {
                    continue;
                }

                int surfaceY = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, candidate).getY();
                int maxY = pos.getY() + 8;
                int minY = Math.max(-61, pos.getY() - 8);

                if (!tile.hasTorch() || tile.energy < 200 || surfaceY <= minY) {
                    return;
                }

                if (surfaceY > maxY) surfaceY = maxY;

                for (int y = surfaceY + 1; y > minY; y--) {
                    BlockPos placePos = new BlockPos(candidate.getX(), y, candidate.getZ());
                    BlockPos belowPos = placePos.below();
                    BlockState placeState = level.getBlockState(placePos);
                    BlockState belowState = level.getBlockState(belowPos);

                    boolean belowNotLiquid = belowState.getFluidState().isEmpty();
                    boolean placeReplaceable = placeState.canBeReplaced();
                    boolean belowSolid = belowState.isSolid();
                    boolean notTallFlower = !(placeState.getBlock() instanceof TallFlowerBlock);
                    boolean notPressurePlate = !(belowState.getBlock() instanceof PressurePlateBlock);

                    //下方不是流体,是实体方块,不是压力板 放置位置能够替换,不是高花
                    if (belowNotLiquid && placeReplaceable && belowSolid && notTallFlower && notPressurePlate) {
                        Block torchBlock = Block.byItem(tile.items.get(slot).getItem());
                        if (level.setBlock(placePos, torchBlock.defaultBlockState(), 2)) {
                            tile.items.get(slot).shrink(1);
                            tile.energy -= 200;
                            tile.data.set(2, tile.energy);
                            tile.setChanged();
                            placed = true;
                            break;
                        }
                    }
                }
                if (placed) break;
            }
            if (placed) break;
        }
    }

    private boolean hasTorch() {
        return items.stream().skip(1).anyMatch(TorchPlacerTile::isTorch);
    }

    /**
     * 获取火把槽位
     */
    private int getTorchSlot() {
        for (int i = 1; i < items.size(); i++) {
            if (isTorch(items.get(i))) return i;
        }
        return -1;
    }

    /**
     * 物品是火把？
     */
    public static boolean isTorch(ItemStack stack) {
        return !stack.isEmpty() && (stack.is(Items.TORCH)
                || stack.is(Items.SOUL_TORCH)
                || stack.is(Items.REDSTONE_TORCH));
    }

    // ---------- Container ----------

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(items, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return level != null
                && level.getBlockEntity(worldPosition) == this
                && player.distanceToSqr(worldPosition.getX() + 0.5,
                worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    // ---------- WorldlyContainer ----------

    @Override
    public int[] getSlotsForFace(Direction side) {
        return side == Direction.UP ? SLOTS_FUEL : SLOTS_ITEMS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        if (index == 0) {
            return direction == Direction.UP && ForgeHooks.getBurnTime(stack, null) > 0;
        }
        return index >= 1 && index < 10
                && direction != Direction.DOWN
                && direction != Direction.UP
                && isTorch(stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return false;
    }

    // ---------- MenuProvider ----------

    @Override
    public Component getDisplayName() {
        return Component.translatable("gui.morecoal.torch_placer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new TorchPlacerContainer(containerId, playerInventory, this, this.data);
    }

    // ---------- NBT ----------

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("BurnTime", burnTime);
        tag.putInt("BurnCount", burnCount);
        tag.putInt("Energy", energy);
        ContainerHelper.saveAllItems(tag, items);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items);
        burnTime = tag.getInt("BurnTime");
        burnCount = tag.getInt("BurnCount");
        energy = tag.getInt("Energy");
    }

    private boolean isBurning() {
        return burnTime > 0;
    }
}