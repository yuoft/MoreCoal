package com.yuo.morecoal.entity;

import com.yuo.morecoal.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

//箭实体
public class TorchArrowEntity extends AbstractArrow {
    public TorchArrowEntity(EntityType<? extends AbstractArrow> type, Level worldIn) {
        super(type, worldIn);
        this.setBaseDamage(2);
    }

    public TorchArrowEntity(EntityType<? extends AbstractArrow> type, double x, double y, double z, Level worldIn) {
        super(type, x, y, z, worldIn);
        this.setBaseDamage(2);
    }

    public TorchArrowEntity(EntityType<? extends AbstractArrow> type, LivingEntity shooter, Level worldIn) {
        super(type, shooter, worldIn);
        this.setBaseDamage(2);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.torchArrow.get());
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        setToTorch(result);
    }

    private void setToTorch(BlockHitResult hit) {
        Direction face = hit.getDirection();
        BlockPos pos = hit.getBlockPos().relative(face);
        if (!level().isEmptyBlock(pos)) return;

        if (face == Direction.DOWN) {
            level().setBlockAndUpdate(pos, Blocks.LANTERN.defaultBlockState()
                    .setValue(LanternBlock.HANGING, true));
            this.discard();
        } else if (face == Direction.UP) {
            level().setBlockAndUpdate(pos, Blocks.TORCH.defaultBlockState());
            this.discard();
        } else {
            BlockState state = Blocks.WALL_TORCH.defaultBlockState()
                    .setValue(WallTorchBlock.FACING, face);
            level().setBlockAndUpdate(pos, state);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity living) {
            living.setSecondsOnFire(2);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putDouble("damage", 2);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setBaseDamage(compound.getDouble("damage"));
    }
}
