package com.yuo.morecoal.Tiles;

import com.yuo.morecoal.Blocks.TorchPlacer;
import com.yuo.morecoal.Gui.TorchPlacerContainer;
import com.yuo.morecoal.Gui.TorchPlacerIntArray;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.INameable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;

public class TorchPlacerTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IInventory, INameable, ISidedInventory {
    private int burnTime; //燃烧时间
    private int burnCount; //燃烧时间
    private int energy; //能量值 上限52 * 200
    private NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY); //存储物品
    private final TorchPlacerIntArray torchPlacerData = new TorchPlacerIntArray();
    private static final int[] SLOTS_FUEL = new int[]{0};
    private static final int[] SLOTS_ITEMS = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    public TorchPlacerTile() {
        super(TileTypeRegistry.TORCH_PLACER_TILE.get());
    }

    @Override
    public void tick() {
        if (world == null || world.isRemote) return;
        boolean flag = this.isBurning();
        //机器运行逻辑
        if (this.isBurning()) {
            --this.burnTime;  //运行时 热值减少
            torchPlacerData.set(0, burnTime);
            torchPlacerData.set(2, energy);
        }

        ItemStack fuel = this.items.get(0);
        if ((this.isBurning() || !fuel.isEmpty()) && energy < 10400) { //正在运行或燃料不为空 能量未达上限
            if (!this.isBurning()) {
                this.burnTime = ForgeHooks.getBurnTime(fuel);
                this.burnCount = burnTime;
                torchPlacerData.set(1, burnCount);
                if (this.isBurning()) {
                    this.markDirty(); //数据变动保存
                    if (fuel.hasContainerItem()) //燃料有容器 返回容器
                        this.items.set(0, fuel.getContainerItem());
                    else if (!fuel.isEmpty()) {
                        fuel.shrink(1); //消耗燃料
                        if (fuel.isEmpty()) {
                            this.items.set(0, fuel.getContainerItem());
                        }
                    }
                }
            }
            if (this.isBurning() && this.energy <= 10400) {
                this.energy += 2;  //增加能量2
                torchPlacerData.set(2, energy);
            }
        }
        if (flag != this.isBurning()) { //状态变化
            markDirty();
            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, this.isBurning()), 3);
        }

        //火把放置逻辑
        if (pos.getY() <= 3 || pos.getY() >= 255) return;
        if (world.getGameTime() % 10 != 0 ) return;
        if (world.getBlockState(pos).get(TorchPlacer.WORK) && energy >= 200 && getTorchEmpty()){ //方块处于工作状态 能量有 有火把
            int slot = getTorchStackSlot();
            for (int i = 1; i < 9; i++){ //控制距离
                boolean bool = false;
                BlockPos blockPos1 = this.pos;
                for (BlockPos blockPos : BlockPos.getAllInBoxMutable(blockPos1.add(-8 * i, 0, -8 * i), blockPos1.add(8 * i, 0, 8 * i))) {
                    int x = this.pos.getX();
                    int z = this.pos.getZ();
                    if (Math.abs(blockPos.getX() - x) % 8 == 0 && Math.abs(blockPos.getZ() - z) % 8 == 0
                            && !(blockPos.getX() == x && blockPos.getZ() == z)){ //坐标筛选 间隔为8 不能在机器位置放置
                        int height = world.getHeight(Heightmap.Type.WORLD_SURFACE, blockPos).getY(); //世界表面高度
                        int maxY = pos.getY() + 8; //放置高度为机器上下8格
                        int minY = pos.getY() - 8;
                        if(minY < 0) minY = 0;
                        if (!getTorchEmpty() || energy < 200 || height <= minY){//火把消耗完时 能量不足  高度过低
                            bool = true;  //结束此次放置
                            break;
                        }
                        if (height > maxY) height = maxY;
                        for (int y = height + 1; y > minY; y--){
                            BlockPos pos1 = new BlockPos(blockPos.getX(), y, blockPos.getZ());
                            BlockPos down = pos1.down();
                            BlockState state = world.getBlockState(pos1);
                            BlockState state1 = world.getBlockState(down);
                            //下方不是流体,是实体方块,不是压力板 放置位置能够替换,不是高花
                            if (!state1.getMaterial().isLiquid() && state.getMaterial().isReplaceable() && state1.getMaterial().isSolid() &&
                                    !(state.getBlock() instanceof TallFlowerBlock) && !(state1.getBlock() instanceof PressurePlateBlock)){
                                if (world.setBlockState(pos1, Block.getBlockFromItem(items.get(slot).getItem()).getDefaultState(), 2)){
                                    items.get(slot).shrink(1); //物品消耗
                                    energy -= 200; //能量消耗
                                    torchPlacerData.set(2, energy);
                                    markDirty();
                                    bool = true; //实现每0.5秒放置一次
                                    break;
                                }
                            }
                        }
                    }
                }
                if (bool) break;
            }
        }
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        handleUpdateTag(world.getBlockState(pkt.getPos()), pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT compound = super.getUpdateTag();
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("BurnCount", this.burnCount);
        compound.putInt("Energy", this.energy);
        ItemStackHelper.saveAllItems(compound, this.items);
        return compound;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(tag, this.items);
        this.burnTime = tag.getInt("BurnTime");
        this.burnCount = tag.getInt("BurnCount");
        this.energy = tag.getInt("Energy");
    }

    //机器内是否有火把
    private boolean getTorchEmpty(){
        for (ItemStack itemStack : items.subList(1, 10)){
            if ( isTorch(itemStack)) return true; //至少有一格类有火把
        }
        return false; //没有火把
    }

    //获取第一个有火把的槽位
    private int getTorchStackSlot(){
        for(int i = 0; i < 10; i++){
            ItemStack itemStack = items.get(i);
            if (isTorch(itemStack)) return i;
        }
        return 0;
    }

    //判断是否时火把
    public static boolean isTorch(ItemStack stack){
        return !stack.isEmpty() && (stack.getItem().equals(Items.TORCH) || stack.getItem().equals(Items.SOUL_TORCH) || stack.getItem().equals(Items.REDSTONE_TORCH));
    }

    @Override
    public ITextComponent getName() {
        return this.getDisplayName();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("gui.morecoal.torch_placer");
    }

    //创建容器
    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new TorchPlacerContainer(p_createMenu_1_, p_createMenu_2_, this, this.torchPlacerData);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.items);
        this.burnTime = nbt.getInt("BurnTime");
        this.burnCount = nbt.getInt("BurnCount");
        this.energy = nbt.getInt("Energy");
    }

    @Override
    public int getSizeInventory() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.items, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.items, index);
    }

    //将给定项目设置为容器中的制定槽位
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack); //相同物品
        this.items.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (!flag) {
            this.markDirty();
        }
    }

    //可由玩家使用吗
    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("BurnTime", this.burnTime);
        compound.putInt("BurnCount", this.burnCount);
        compound.putInt("Energy", this.energy);
        ItemStackHelper.saveAllItems(compound, this.items);
        return compound;
    }

    //是否燃烧
    private boolean isBurning() {
        return this.burnTime > 0;
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    //通过面获取slot
    @Override
    public int[] getSlotsForFace(Direction side) {
        return side == Direction.UP ? SLOTS_FUEL : SLOTS_ITEMS;
    }

    //自动输入物品
    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        if (index == 0){
            if (direction == Direction.UP) return ForgeHooks.getBurnTime(itemStackIn) > 0; //上方输入燃料
        } else if (index >= 1 && index < 10){
            if (direction != Direction.DOWN && direction!= Direction.UP) return isTorch(itemStackIn); //四周输入火把
        }
        return false;
    }

    //自动输出
    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return false; //禁止自动输出
    }
}
