package com.yuo.morecoal.Entity;

import com.yuo.morecoal.Items.MoreCoalItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

//箭实体
public class TorchArrowEntity extends AbstractArrowEntity {
    public TorchArrowEntity(EntityType<? extends AbstractArrowEntity> type, World worldIn) {
        super(type, worldIn);
        this.setDamage(2);
    }

    public TorchArrowEntity(EntityType<? extends AbstractArrowEntity> type, double x, double y, double z, World worldIn) {
        super(type, x, y, z, worldIn);
        this.setDamage(2);
    }

    public TorchArrowEntity(EntityType<? extends AbstractArrowEntity> type, LivingEntity shooter, World worldIn) {
        super(type, shooter, worldIn);
        this.setDamage(2);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(MoreCoalItems.torchArrow.get());
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult result) {
        super.func_230299_a_(result);
        setToTorch(result);
    }

    //设置生成火把
    private void setToTorch(BlockRayTraceResult hit) {
        Direction face = hit.getFace();
        BlockPos pos = hit.getPos().offset(face); //根据朝向获取坐标
        if (!world.isAirBlock(pos)) return; // 坐标不是空气则返回
        // 碰撞到方块底部设置灯笼
        if (face == Direction.DOWN){
            world.setBlockState(pos, Blocks.LANTERN.getDefaultState().with(LanternBlock.HANGING, true));
            this.remove();
        }
        // 碰撞到方块顶部设置火把
        else if (face == Direction.UP){
            world.setBlockState(pos, Blocks.TORCH.getDefaultState());
            this.remove();
        }
        // 方块四周
        else {
            BlockState state = Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, face);
            world.setBlockState(pos, state);
            this.remove();
        }
    }
    // 攻击到实体 点燃
    @Override
    protected void arrowHit(LivingEntity living) {
        living.setFire(2);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putDouble("damage", 2);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setDamage(compound.getDouble("damage"));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
