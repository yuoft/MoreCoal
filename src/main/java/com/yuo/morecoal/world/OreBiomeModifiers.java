package com.yuo.morecoal.world;

import com.yuo.morecoal.MoreCoal;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddFeaturesBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class OreBiomeModifiers {
    // 注册的key
    public static final ResourceKey<BiomeModifier> ADD_MORECOAL_ORE = registerKey("add_morecoal_ore");
    public static final ResourceKey<BiomeModifier> ADD_MORECOAL_DEEP_ORE = registerKey("add_morecoal_deep_ore");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_MORECOAL_ORE = registerKey("add_nether_morecoal_ore");
    public static final ResourceKey<BiomeModifier> ADD_END_MORECOAL_ORE = registerKey("add_end_morecoal_ore");

    // BootstapContext 数据生成的上下文
    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        // ★ 主世界矿
        context.register(ADD_MORECOAL_ORE, new AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),                      // 主世界
                HolderSet.direct(placedFeatures.getOrThrow(OrePlacements.ORE_MORECOAL)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_MORECOAL_DEEP_ORE, new AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(OrePlacements.ORE_MORECOAL_DEEP)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        // ★ 下界矿
        context.register(ADD_NETHER_MORECOAL_ORE, new AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),                         // 下界
                HolderSet.direct(placedFeatures.getOrThrow(OrePlacements.ORE_MORECOAL_NETHER)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        // ★ 末地矿
        context.register(ADD_END_MORECOAL_ORE, new AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),                            // 末地
                HolderSet.direct(placedFeatures.getOrThrow(OrePlacements.ORE_MORECOAL_END)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(MoreCoal.MOD_ID, name));
    }
}
