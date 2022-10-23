package com.yuo.morecoal.Event;

import com.yuo.morecoal.Blocks.MoreCoalBlocks;
import com.yuo.morecoal.Blocks.FallBlock;
import com.yuo.morecoal.Items.Bow.CoalBow;
import com.yuo.morecoal.Items.*;
import com.yuo.morecoal.MoreCoal;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * 事件处理类
 */
@Mod.EventBusSubscriber(modid = MoreCoal.MODID)
public class EventHandler {
    public static List<String> coalFeet = new ArrayList<>();
    private static final Random RANDOM = new Random();

    //燃烧时间设置
    @SubscribeEvent
    public static void smeltingItem(FurnaceFuelBurnTimeEvent event){
        Item item = event.getItemStack().getItem();
        if (item.equals(MoreCoalItems.ironCoal.get())) event.setBurnTime(16000 * 2);
        if (item.equals(MoreCoalItems.ironCoalBlock.get())) event.setBurnTime(16000 * 2 * 10);
        if (item.equals(MoreCoalItems.goldCoal.get())) event.setBurnTime(16000 * 4);
        if (item.equals(MoreCoalItems.goldCoalBlock.get())) event.setBurnTime(16000 * 4 * 10);
        if (item.equals(MoreCoalItems.diamondCoal.get())) event.setBurnTime(16000 * 8);
        if (item.equals(MoreCoalItems.diamondCoalBlock.get())) event.setBurnTime(16000 * 8 * 10);
        if (item.equals(MoreCoalItems.netheriteCoal.get())) event.setBurnTime(16000 * 16);
        if (item.equals(MoreCoalItems.netheriteCoalBlock.get())) event.setBurnTime(16000 * 16 * 10);
        if (item.equals(MoreCoalItems.lapisCoal.get())) event.setBurnTime(16000 * 4);
        if (item.equals(MoreCoalItems.lapisCoalBlock.get())) event.setBurnTime(16000 * 4 * 10);
        if (item.equals(MoreCoalItems.redstoneCoal.get())) event.setBurnTime(16000 * 4);
        if (item.equals(MoreCoalItems.redstoneCoalBlock.get())) event.setBurnTime(16000 * 4 * 10);
        if (item.equals(MoreCoalItems.emeraldCoal.get())) event.setBurnTime(16000 * 12);
        if (item.equals(MoreCoalItems.emeraldCoalBlock.get())) event.setBurnTime(16000 * 12 * 10);
        if (item.equals(MoreCoalItems.lavaCoal.get())) event.setBurnTime(20000 * 5);
        if (item.equals(MoreCoalItems.lavaCoalBlock.get())) event.setBurnTime(20000 * 5 * 10);
    }

    //火炬鞋暗处插火把
    @SubscribeEvent
    public static void updatePlayer(LivingEvent.LivingUpdateEvent event) {
        LivingEntity living = event.getEntityLiving();
        if (living instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) living;
            boolean hasFoot = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == MoreCoalItems.coalFeet.get();
            //防止其它模组飞行装备无法使用
            String key = player.getGameProfile().getName()+":"+player.world.isRemote;
            //feet
            if (coalFeet.contains(key)) {
                if (hasFoot) {
                    BlockPos pos = player.getPosition();
                    World world = player.world;
                    if (world.isRemote) return;
                    if (world.isAirBlock(pos.down(1))) return;
                    int light = world.getLight(pos); //获取方块处亮度
                    if (light < 5){
                        ItemStack torch = findTorch(player);
                        if (torch.isEmpty()) { //玩家未持有火把
                            world.setBlockState(pos, Blocks.TORCH.getDefaultState()); //设置火把
                            ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.FEET);
                            stack.damageItem(1, player, e -> e.sendBreakAnimation(Hand.MAIN_HAND)); //消耗装备耐久
                        }else {
                            world.setBlockState(pos, Blocks.TORCH.getDefaultState());
                            torch.setCount(torch.getCount() - 1); //消耗火把数量
                        }
                    }
                } else {
                    coalFeet.remove(key);
                }
            } else if (hasFoot) {
                coalFeet.add(key);
            }
        }
    }

    //煤炭武器击杀生物时掉落熟肉
    @SubscribeEvent
    public static void coalToolKill(LivingDropsEvent event){
        Entity source = event.getSource().getTrueSource();
        if (source instanceof PlayerEntity){ //由玩家使用
            PlayerEntity player = (PlayerEntity) source;
            World world = player.world;
            ItemStack stack = player.getHeldItemMainhand();
            LivingEntity living = event.getEntityLiving();
            if (!(stack.getItem() instanceof CoalSword) && !(stack.getItem() instanceof CoalAxe) && !(stack.getItem() instanceof CoalBow)) return;
            ITag<Item> tag = ItemTags.getCollection().get(MoreCoalTags.RAW_FOOD); //生的食物
            Collection<ItemEntity> drops = event.getDrops();
            List<ItemEntity> list = new ArrayList<>();
            for (ItemEntity next : drops) {
                if (tag == null)  continue;
                if (next.getItem().getItem().isIn(tag)) {
                    //获取物品烧炼后产物
                    ItemStack dropStack = world.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(next.getItem()), world)
                            .map(FurnaceRecipe::getRecipeOutput).filter(e -> !e.isEmpty())
                            .map(e -> ItemHandlerHelper.copyStackWithSize(e, stack.getCount() * e.getCount()))
                            .orElse(next.getItem());
                    if (!dropStack.isEmpty()) {
                        dropStack.setCount(next.getItem().getCount()); //设置数量
                        next.remove(); //清除原版掉落
                        list.add(new ItemEntity(world, living.getPosX(), living.getPosY(), living.getPosZ(), dropStack));
                    }
                }
            }
            event.getDrops().addAll(list); //增加新掉落物
        }
    }

    //煤炭镐挖掘矿石额外掉落
    @SubscribeEvent
    public static void coalToolBreak(BlockEvent.BreakEvent event){
        PlayerEntity player = event.getPlayer();
        if (player == null) return;
        ItemStack stack = player.getHeldItemMainhand();
        if (stack.isEmpty()) return;
        Item item = stack.getItem();
        Block block = event.getState().getBlock();
        World world = player.world;
        BlockPos pos = event.getPos();
        if (item instanceof CoalPickaxe) {
            if (block.equals(Blocks.COAL_ORE)){ //煤矿掉落钻石和其它煤炭
                int i = RANDOM.nextInt(100);
                int looting = EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, stack);
                if (i < 90 - looting * 3) return;
                dropsSuperCoal(world, pos, true);
            }
            if (block instanceof OreBlock){ //其它矿石掉落各种煤炭
                int i = RANDOM.nextInt(100);
                int looting = EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, stack);
                if (i < 90 - looting * 3) return;
                dropsSuperCoal(world, pos, false);
            }
        }
        if (item instanceof CoalAxe){ //砍树掉落木炭
            if (block.isIn(BlockTags.LOGS)){
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.CHARCOAL)));
            }
        }
    }

    //煤炭工具右键方块插火把
    @SubscribeEvent
    public static void coalToolRightBlock(PlayerInteractEvent.RightClickBlock event){
        Hand hand = event.getHand();
        if (hand != Hand.MAIN_HAND) return;
        World world = event.getWorld();
        PlayerEntity player = event.getPlayer();
        if (player == null) return;
        Item item = event.getItemStack().getItem();
        Direction face = event.getFace() == null ? Direction.UP : event.getFace();
        BlockPos pos = event.getPos().offset(face);
        if (!world.isAirBlock(pos)) return;
        Block block = world.getBlockState(event.getPos()).getBlock();
        if (block instanceof FallBlock && event.getItemStack().isEmpty()){
            ItemStack itemStack = new ItemStack(MoreCoalItems.fallBlock.get());
            player.setHeldItem(Hand.MAIN_HAND, itemStack);
            world.setBlockState(event.getPos(), Blocks.AIR.getDefaultState());
        }
        if (item instanceof CoalAxe || item instanceof CoalPickaxe || item instanceof CoalSword){ //煤炭工具
            if (face == Direction.DOWN){
                world.setBlockState(pos, Blocks.LANTERN.getDefaultState());
            }
            else if (face == Direction.UP){
                world.setBlockState(pos, Blocks.TORCH.getDefaultState());
            }
            else {
                BlockState state = Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, face);
                world.setBlockState(pos, state);
            }
            event.getItemStack().damageItem(1, player, e -> e.sendBreakAnimation(Hand.MAIN_HAND));
        }
    }

    //禁止巨型火把周围生成怪物
    @SubscribeEvent
    public static void stopLivingSpawn(LivingSpawnEvent.CheckSpawn event){
        LivingEntity living = event.getEntityLiving();
        if (event.getSpawnReason() == SpawnReason.NATURAL){ //自然生成
            BlockPos pos = new BlockPos(event.getX(), event.getY(), event.getZ());
            World world = event.getEntityLiving().world;
            for (BlockPos blockPos : BlockPos.getAllInBoxMutable(pos.add(-8, -8, -8), pos.add(8, 8, 8))) {
                BlockState state = world.getBlockState(blockPos);
                if (living instanceof MobEntity && state.getBlock().equals(MoreCoalBlocks.bigTorch.get())){
                    event.setResult(Event.Result.DENY); //阻住生成
                }
                if (living instanceof AnimalEntity && state.getBlock().equals(MoreCoalBlocks.bigTorch0.get())){
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    //寻找玩家身上是否有火把
    private static ItemStack findTorch(PlayerEntity player){
        for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (stack.getItem() == Items.TORCH || stack.getItem() == Items.SOUL_TORCH) {
                return stack;
            }
        }
        //创造模式直接返回火把
        return player.abilities.isCreativeMode ? new ItemStack(Items.TORCH) : ItemStack.EMPTY;
    }

    //额外概率掉落钻石或高级煤炭
    public static void dropsSuperCoal(World world, BlockPos pos, boolean flag){
        int i = RANDOM.nextInt(200);
        if (i < 65 && i >= 50) dropItem(world, pos, 0,  flag);
        else if (i < 95) dropItem(world, pos, 1, flag);
        else if (i < 120) dropItem(world, pos, 2, flag);
        else if (i < 140) dropItem(world, pos, 3, flag);
        else if (i < 160) dropItem(world, pos, 4, flag);
        else if (i < 175) dropItem(world, pos, 5, flag);
        else if (i < 185) dropItem(world, pos, 6, flag);
        else if (i < 195) dropItem(world, pos, 7, flag);
        else if (i < 199) dropItem(world, pos, 8, flag);
    }

    //掉落
    private static void dropItem(World world, BlockPos pos, int num, boolean flag){
        //预定义掉落表
        Item[] DROPS= new Item[]{ Items.DIAMOND, MoreCoalItems.ironCoal.get(),
                MoreCoalItems.goldCoal.get(), MoreCoalItems.lapisCoal.get(), MoreCoalItems.redstoneCoal.get(),
                MoreCoalItems.diamondCoal.get(), MoreCoalItems.emeraldCoal.get(),
                MoreCoalItems.netheriteCoal.get(), MoreCoalItems.lavaCoal.get()};
        if (!flag && num == 0) return;
        world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(DROPS[num])));
    }
}

