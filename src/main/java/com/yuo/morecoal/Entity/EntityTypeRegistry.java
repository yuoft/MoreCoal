package com.yuo.morecoal.Entity;

import com.yuo.morecoal.MoreCoal;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 实体注册
 */
public class EntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MoreCoal.MODID);
    //投掷物
    public static RegistryObject<EntityType<TorchArrowEntity>> TORCH_ARROW = ENTITY_TYPES.register("torch_arrow", () -> {
        return EntityType.Builder.<TorchArrowEntity>create(TorchArrowEntity::new, EntityClassification.MISC)
                .size(0.5f, 0.5F).build("torch_arrow");
    });

}
