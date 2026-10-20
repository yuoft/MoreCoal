package com.yuo.morecoal.entity;

import com.yuo.morecoal.MoreCoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 实体注册
 */
public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MoreCoal.MOD_ID);
    //投掷物
    public static RegistryObject<EntityType<TorchArrowEntity>> TORCH_ARROW = ENTITY_TYPES.register("torch_arrow",
            () -> EntityType.Builder.<TorchArrowEntity>of(TorchArrowEntity::new, MobCategory.MISC)
            .sized(0.5f, 0.5F).build("torch_arrow"));

}
