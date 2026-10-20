package com.yuo.morecoal.event;

import com.yuo.morecoal.MoreCoal;
import com.yuo.morecoal.blocks.FallBlock;
import com.yuo.morecoal.blocks.ModBlocks;
import com.yuo.morecoal.items.*;
import com.yuo.morecoal.items.bow.CoalBow;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 事件处理类
 */
@Mod.EventBusSubscriber(modid = MoreCoal.MOD_ID)
public class EventHandler {
    public static List<String> coalFeet = new ArrayList<>();
    private static final Random RANDOM = new Random();

    //燃烧时间设置
    @SubscribeEvent
    public static void smeltingItem(FurnaceFuelBurnTimeEvent event){
        Item item = event.getItemStack().getItem();
        if (item.equals(ModItems.ironCoal.get())) event.setBurnTime(16000 * 2);
        if (item.equals(ModItems.ironCoalBlock.get())) event.setBurnTime(16000 * 2 * 10);
        if (item.equals(ModItems.goldCoal.get())) event.setBurnTime(16000 * 4);
        if (item.equals(ModItems.goldCoalBlock.get())) event.setBurnTime(16000 * 4 * 10);
        if (item.equals(ModItems.diamondCoal.get())) event.setBurnTime(16000 * 8);
        if (item.equals(ModItems.diamondCoalBlock.get())) event.setBurnTime(16000 * 8 * 10);
        if (item.equals(ModItems.netheriteCoal.get())) event.setBurnTime(16000 * 16);
        if (item.equals(ModItems.netheriteCoalBlock.get())) event.setBurnTime(16000 * 16 * 10);
        if (item.equals(ModItems.lapisCoal.get())) event.setBurnTime(16000 * 4);
        if (item.equals(ModItems.lapisCoalBlock.get())) event.setBurnTime(16000 * 4 * 10);
        if (item.equals(ModItems.redstoneCoal.get())) event.setBurnTime(16000 * 4);
        if (item.equals(ModItems.redstoneCoalBlock.get())) event.setBurnTime(16000 * 4 * 10);
        if (item.equals(ModItems.emeraldCoal.get())) event.setBurnTime(16000 * 12);
        if (item.equals(ModItems.emeraldCoalBlock.get())) event.setBurnTime(16000 * 12 * 10);
        if (item.equals(ModItems.lavaCoal.get())) event.setBurnTime(20000 * 5);
        if (item.equals(ModItems.lavaCoalBlock.get())) event.setBurnTime(20000 * 5 * 10);
    }

    //火炬鞋暗处插火把
    @SubscribeEvent
    public static void updatePlayer(PlayerTickEvent event) {
        Player player = event.player;
        boolean hasFoot = player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.coalFeet.get();
        //防止其它模组飞行装备无法使用
        String key = player.getGameProfile().getName()+":"+player.level().isClientSide;
        //feet
        if (coalFeet.contains(key)) {
            if (hasFoot) {
                BlockPos pos = player.blockPosition();
                Level level = player.level();
                if (level.isClientSide) return;
                if (level.getBlockState(pos.below(1)).isAir()) return;
                int light = level.getBrightness(LightLayer.BLOCK, pos); //获取方块处亮度
                if (light < 5) {
                    ItemStack torch = findTorch(player);
                    if (torch.isEmpty()) { //玩家未持有火把
                        level.setBlockAndUpdate(pos, Blocks.TORCH.defaultBlockState()); //设置火把
                        ItemStack stack = player.getItemBySlot(EquipmentSlot.FEET);
                        stack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(InteractionHand.MAIN_HAND)); //消耗装备耐久
                    } else {
                        level.setBlockAndUpdate(pos, Blocks.TORCH.defaultBlockState());
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

    //煤炭武器击杀生物时掉落熟肉
    @SubscribeEvent
    public static void coalToolKill(LivingDropsEvent event) {
        Entity source = event.getSource().getEntity();
        if (!(source instanceof Player player)) return;

        ItemStack weapon = player.getMainHandItem();
        if (!(weapon.getItem() instanceof CoalSword) && !(weapon.getItem() instanceof CoalAxe) && !(weapon.getItem() instanceof CoalBow)) {
            return;
        }

        Level level = player.level();
        LivingEntity dead = event.getEntity();

        // 收集需要替换的掉落物，避免在遍历时直接修改原集合
        List<ItemEntity> toRemove = new ArrayList<>();
        List<ItemEntity> toAdd = new ArrayList<>();

        for (ItemEntity dropEntity : event.getDrops()) {
            ItemStack original = dropEntity.getItem();
            if (!original.is(MoreCoalTags.RAW_FOOD)) continue;

            ItemStack smelted = level.getRecipeManager()
                    .getRecipeFor(RecipeType.SMELTING, new SimpleContainer(original), level)
                    .map(recipe -> recipe.getResultItem(level.registryAccess()))
                    .filter(result -> !result.isEmpty())
                    .orElse(ItemStack.EMPTY);

            if (smelted.isEmpty()) continue;

            // 直接基于烧炼产物复制，数量沿用原掉落物的数量
            ItemStack replacement = smelted.copy();
            replacement.setCount(original.getCount());

            toRemove.add(dropEntity);
            toAdd.add(new ItemEntity(level, dead.getX(), dead.getY(), dead.getZ(), replacement));
        }

        // 用事件提供的方法安全地增删掉落物
        toRemove.forEach(drop -> event.getDrops().remove(drop));
        toAdd.forEach(entity -> event.getDrops().add(entity));
    }

    //煤炭镐挖掘矿石额外掉落
    @SubscribeEvent
    public static void coalToolBreak(BlockEvent.BreakEvent event){
        Player player = event.getPlayer();
        if (player == null) return;
        ItemStack stack = player.getMainHandItem();
        if (stack.isEmpty()) return;
        Item item = stack.getItem();
        Block block = event.getState().getBlock();
        Level world = player.level();
        BlockPos pos = event.getPos();
        if (item instanceof CoalPickaxe) {
            if (block.equals(Blocks.COAL_ORE)){ //煤矿掉落钻石和其它煤炭
                int i = RANDOM.nextInt(100);
                int looting = stack.getEnchantmentLevel(Enchantments.MOB_LOOTING);
                if (i < 90 - looting * 3) return;
                dropsSuperCoal(world, pos, true);
            }
            if (block instanceof DropExperienceBlock){ //其它矿石掉落各种煤炭
                int i = RANDOM.nextInt(100);
                int looting = stack.getEnchantmentLevel(Enchantments.MOB_LOOTING);
                if (i < 90 - looting * 3) return;
                dropsSuperCoal(world, pos, false);
            }
        }
        if (item instanceof CoalAxe){ //砍树掉落木炭
            if (event.getState().is(BlockTags.LOGS)){
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.CHARCOAL)));
            }
        }
    }

    //煤炭工具右键方块插火把
    @SubscribeEvent
    public static void coalToolRightBlock(PlayerInteractEvent.RightClickBlock event){
        InteractionHand hand = event.getHand();
        if (hand != InteractionHand.MAIN_HAND) return;
        Level world = event.getLevel();
        Player player = event.getEntity();
        if (player == null) return;
        Item item = event.getItemStack().getItem();
        Direction face = event.getFace() == null ? Direction.UP : event.getFace();
        BlockPos pos = event.getPos().relative(face);
        if (!world.getBlockState(pos).isAir()) return;
        Block block = world.getBlockState(event.getPos()).getBlock();
        if (block instanceof FallBlock && event.getItemStack().isEmpty()){
            ItemStack itemStack = new ItemStack(ModItems.fallBlock.get());
            player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
            world.setBlockAndUpdate(event.getPos(), Blocks.AIR.defaultBlockState());
        }
        if (item instanceof CoalAxe || item instanceof CoalPickaxe || item instanceof CoalSword){ //煤炭工具
            if (face == Direction.DOWN){
                world.setBlockAndUpdate(pos, Blocks.LANTERN.defaultBlockState());
            }
            else if (face == Direction.UP){
                world.setBlockAndUpdate(pos, Blocks.TORCH.defaultBlockState());
            }
            else {
                BlockState state = Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, face);
                world.setBlockAndUpdate(pos, state);
            }
            event.getItemStack().hurtAndBreak(1, player, e -> e.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        }
    }

    //禁止巨型火把周围生成怪物
    @SubscribeEvent
    public static void stopLivingSpawn(MobSpawnEvent.FinalizeSpawn event) {
        // 自然生成和刷怪笼生物
        if (!(event.getSpawnType() == MobSpawnType.NATURAL || event.getSpawnType() == MobSpawnType.SPAWNER)) return;

        // 2. 获取生物实体，并提前判断类型，避免无意义的扫描
        var living = event.getEntity();
        boolean isMonster = living instanceof Monster;
        boolean isAnimal = living instanceof Animal;

        if (!isMonster && !isAnimal) return;

        BlockPos pos = living.blockPosition();
        var level = (ServerLevel) event.getLevel();

        // 4. 遍历周围 8 格范围，寻找巨型火把
        for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-8, -8, -8), pos.offset(8, 8, 8))) {
            BlockState state = level.getBlockState(blockPos);

            if (isMonster && state.is(ModBlocks.bigTorch.get())) {
                event.setSpawnCancelled(true);
                return;
            }
            if (isAnimal && state.is(ModBlocks.bigTorch0.get())) {
                event.setSpawnCancelled(true);
                return;
            }
        }
    }

    //寻找玩家身上是否有火把
    private static ItemStack findTorch(Player player){
        for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() == Items.TORCH || stack.getItem() == Items.SOUL_TORCH) {
                return stack;
            }
        }
        //创造模式直接返回火把
        return player.isCreative() ? new ItemStack(Items.TORCH) : ItemStack.EMPTY;
    }

    //额外概率掉落钻石或高级煤炭
    public static void dropsSuperCoal(Level world, BlockPos pos, boolean flag){
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
    private static void dropItem(Level world, BlockPos pos, int num, boolean flag){
        //预定义掉落表
        Item[] DROPS= new Item[]{ Items.DIAMOND, ModItems.ironCoal.get(),
                ModItems.goldCoal.get(), ModItems.lapisCoal.get(), ModItems.redstoneCoal.get(),
                ModItems.diamondCoal.get(), ModItems.emeraldCoal.get(),
                ModItems.netheriteCoal.get(), ModItems.lavaCoal.get()};
        if (!flag && num == 0) return;
        world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(DROPS[num])));
    }
}

