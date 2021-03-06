package com.yuo.morecoal.Event;

import com.yuo.morecoal.Blocks.BigTorch;
import com.yuo.morecoal.Blocks.BlockRegistry;
import com.yuo.morecoal.Blocks.FallBlock;
import com.yuo.morecoal.Items.*;
import com.yuo.morecoal.Items.Bow.CoalBow;
import com.yuo.morecoal.MoreCoal;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.FallingBlockEntity;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.spawner.AbstractSpawner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.*;

/**
 * ???????????????
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MoreCoal.MODID)
public class EventHandler {
    public static List<String> coalFeet = new ArrayList<>();
    private static final Random RANDOM = new Random();

    //??????????????????
    @SubscribeEvent
    public static void smeltingItem(FurnaceFuelBurnTimeEvent event){
        Item item = event.getItemStack().getItem();
        if (item.equals(ItemRegistry.ironCoal.get())) event.setBurnTime(16000 * 2);
        if (item.equals(ItemRegistry.ironCoalBlock.get())) event.setBurnTime(16000 * 2 * 10);
        if (item.equals(ItemRegistry.goldCoal.get())) event.setBurnTime(16000 * 4);
        if (item.equals(ItemRegistry.goldCoalBlock.get())) event.setBurnTime(16000 * 4 * 10);
        if (item.equals(ItemRegistry.diamondCoal.get())) event.setBurnTime(16000 * 8);
        if (item.equals(ItemRegistry.diamondCoalBlock.get())) event.setBurnTime(16000 * 8 * 10);
        if (item.equals(ItemRegistry.netheriteCoal.get())) event.setBurnTime(16000 * 16);
        if (item.equals(ItemRegistry.netheriteCoalBlock.get())) event.setBurnTime(16000 * 16 * 10);
        if (item.equals(ItemRegistry.lapisCoal.get())) event.setBurnTime(16000 * 4);
        if (item.equals(ItemRegistry.lapisCoalBlock.get())) event.setBurnTime(16000 * 4 * 10);
        if (item.equals(ItemRegistry.redstoneCoal.get())) event.setBurnTime(16000 * 4);
        if (item.equals(ItemRegistry.redstoneCoalBlock.get())) event.setBurnTime(16000 * 4 * 10);
        if (item.equals(ItemRegistry.emeraldCoal.get())) event.setBurnTime(16000 * 12);
        if (item.equals(ItemRegistry.emeraldCoalBlock.get())) event.setBurnTime(16000 * 12 * 10);
        if (item.equals(ItemRegistry.lavaCoal.get())) event.setBurnTime(20000 * 5);
        if (item.equals(ItemRegistry.lavaCoalBlock.get())) event.setBurnTime(20000 * 5 * 10);
    }

    //????????????????????????
    @SubscribeEvent
    public static void updatePlayer(LivingEvent.LivingUpdateEvent event) {
        LivingEntity living = event.getEntityLiving();
        if (living instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) living;
            Boolean hasFoot = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == ItemRegistry.coalFeet.get();
            //??????????????????????????????????????????
            String key = player.getGameProfile().getName()+":"+player.world.isRemote;
            //feet
            if (coalFeet.contains(key)) {
                if (hasFoot) {
                    BlockPos pos = player.getPosition();
                    World world = player.world;
                    if (world.isRemote) return;
                    if (world.isAirBlock(pos.down(1))) return;
                    int light = world.getLight(pos); //?????????????????????
                    if (light < 5){
                        ItemStack torch = findTorch(player);
                        if (torch.isEmpty()) { //?????????????????????
                            world.setBlockState(pos, Blocks.TORCH.getDefaultState()); //????????????
                            ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.FEET);
                            stack.damageItem(1, player, e -> e.sendBreakAnimation(Hand.MAIN_HAND)); //??????????????????
                        }else {
                            world.setBlockState(pos, Blocks.TORCH.getDefaultState());
                            torch.setCount(torch.getCount() - 1); //??????????????????
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

    //???????????????????????????????????????
    @SubscribeEvent
    public static void coalToolKill(LivingDropsEvent event){
        Entity source = event.getSource().getTrueSource();
        if (source instanceof PlayerEntity){ //???????????????
            PlayerEntity player = (PlayerEntity) source;
            World world = player.world;
            ItemStack stack = player.getHeldItemMainhand();
            LivingEntity living = event.getEntityLiving();
            if (!(stack.getItem() instanceof CoalSword) && !(stack.getItem() instanceof CoalAxe) && !(stack.getItem() instanceof CoalBow)) return;
            ITag<Item> tag = ItemTags.getCollection().get(TagsRegistry.RAW_FOOD); //????????????
            Collection<ItemEntity> drops = event.getDrops();
            List<ItemEntity> list = new ArrayList<>();
            Iterator<ItemEntity> iterator = drops.iterator();
            while (iterator.hasNext()){
                ItemEntity next = iterator.next();
                if (next.getItem().getItem().isIn(tag)){
                    //???????????????????????????
                    ItemStack dropStack = world.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(next.getItem()), world)
                            .map(FurnaceRecipe::getRecipeOutput).filter(e -> !e.isEmpty())
                            .map(e -> ItemHandlerHelper.copyStackWithSize(e, stack.getCount() * e.getCount()))
                            .orElse(next.getItem());
                    if (!dropStack.isEmpty()){
                        dropStack.setCount(next.getItem().getCount()); //????????????
                        next.remove(); //??????????????????
                        list.add(new ItemEntity(world, living.getPosX(), living.getPosY(), living.getPosZ(), dropStack));
                    }
                }
            }
            event.getDrops().addAll(list); //??????????????????
        }
    }

    //?????????????????????????????????
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
            if (block.equals(Blocks.COAL_ORE)){ //?????????????????????????????????
                int i = RANDOM.nextInt(100);
                int looting = EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, stack);
                if (i < 90 - looting * 3) return;
                dropsSuperCoal(world, pos, true);
            }
            if (block instanceof OreBlock){ //??????????????????????????????
                int i = RANDOM.nextInt(100);
                int looting = EnchantmentHelper.getEnchantmentLevel(Enchantments.LOOTING, stack);
                if (i < 90 - looting * 3) return;
                dropsSuperCoal(world, pos, false);
            }
        }
        if (item instanceof CoalAxe){ //??????????????????
            if (block.isIn(BlockTags.LOGS)){
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.CHARCOAL)));
            }
        }
    }

    //?????????????????????????????????
    @SubscribeEvent
    public static void coalToolRightBlock(PlayerInteractEvent.RightClickBlock event){
        Hand hand = event.getHand();
        if (hand != Hand.MAIN_HAND) return;
        World world = event.getWorld();
        PlayerEntity player = event.getPlayer();
        if (player == null) return;
        Item item = event.getItemStack().getItem();
        Direction face = event.getFace();
        BlockPos pos = event.getPos().offset(face);
        if (!world.isAirBlock(pos)) return;
        Block block = world.getBlockState(event.getPos()).getBlock();
        if (block instanceof FallBlock && event.getItemStack().isEmpty()){
            ItemStack itemStack = new ItemStack(ItemRegistry.fallBlock.get());
            player.setHeldItem(Hand.MAIN_HAND, itemStack);
            world.setBlockState(event.getPos(), Blocks.AIR.getDefaultState());
        }
        if (item instanceof CoalAxe || item instanceof CoalPickaxe || item instanceof CoalSword){ //????????????
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

    //????????????????????????????????????
    @SubscribeEvent
    public static void stopLivingSpawn(LivingSpawnEvent.CheckSpawn event){
        LivingEntity living = event.getEntityLiving();
        if (event.getSpawnReason() == SpawnReason.NATURAL){ //????????????
            BlockPos pos = new BlockPos(event.getX(), event.getY(), event.getZ());
            World world = event.getEntityLiving().world;
            for (BlockPos blockPos : BlockPos.getAllInBoxMutable(pos.add(-8, -8, -8), pos.add(8, 8, 8))) {
                BlockState state = world.getBlockState(blockPos);
                if (living instanceof MobEntity && state.getBlock().equals(BlockRegistry.bigTorch.get())){
                    event.setResult(Event.Result.DENY); //????????????
                }
                if (living instanceof AnimalEntity && state.getBlock().equals(BlockRegistry.bigTorch0.get())){
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }
    //?????????????????????????????????
    private static ItemStack findTorch(PlayerEntity player){
        for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (stack.equals(Items.TORCH) || stack.equals(Items.SOUL_TORCH)) {
                return stack;
            }
        }
        //??????????????????????????????
        return player.abilities.isCreativeMode ? new ItemStack(Items.TORCH) : ItemStack.EMPTY;
    }

    //???????????????????????????????????????
    public static void dropsSuperCoal(World world, BlockPos pos, boolean flag){
        int i = RANDOM.nextInt(200);
        if (i < 50) return;
        else if (i < 65) dropItem(world, pos, 0,  flag);
        else if (i < 95) dropItem(world, pos, 1, flag);
        else if (i < 120) dropItem(world, pos, 2, flag);
        else if (i < 140) dropItem(world, pos, 3, flag);
        else if (i < 160) dropItem(world, pos, 4, flag);
        else if (i < 175) dropItem(world, pos, 5, flag);
        else if (i < 185) dropItem(world, pos, 6, flag);
        else if (i < 195) dropItem(world, pos, 7, flag);
        else if (i < 199) dropItem(world, pos, 8, flag);
    }

    //??????
    private static void dropItem(World world, BlockPos pos, int num, boolean flag){
        //??????????????????
        Item[] DROPS= new Item[]{ Items.DIAMOND, ItemRegistry.ironCoal.get(),
                ItemRegistry.goldCoal.get(), ItemRegistry.lapisCoal.get(), ItemRegistry.redstoneCoal.get(),
                ItemRegistry.diamondCoal.get(),ItemRegistry.emeraldCoal.get(),
                ItemRegistry.netheriteCoal.get(), ItemRegistry.lavaCoal.get()};
        if (!flag && num == 0) return;
        world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(DROPS[num])));
    }
}

