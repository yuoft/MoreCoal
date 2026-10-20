package com.yuo.morecoal.world;

import com.yuo.morecoal.MoreCoal;
import com.yuo.morecoal.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class OreFeatures {
    // 创建OreFeature对应的ResourceKey
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MORECOAL = createKey("ore_morecoal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MORECOAL_DEEP = createKey("ore_morecoal_deep");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MORECOAL_NETHER = createKey("ore_morecoal_nether");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MORECOAL_END = createKey("ore_morecoal_end");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> ctx) {
        RuleTest stoneOre = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateOre = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrack = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endStone = new BlockMatchTest(Blocks.END_STONE);   // ★ 加末地

        List<TargetBlockState> energyList = List.of(OreConfiguration.target(stoneOre, ModBlocks.moreCoalDeepOre.get().defaultBlockState()));
        List<TargetBlockState> energyDeepList = List.of(OreConfiguration.target(deepslateOre, ModBlocks.moreCoalDeepOre.get().defaultBlockState()));
        List<TargetBlockState> netherList = List.of(OreConfiguration.target(netherrack, ModBlocks.netherMoreCoalOre.get().defaultBlockState()));
        List<TargetBlockState> endList = List.of(OreConfiguration.target(endStone, ModBlocks.endMoreCoalOre.get().defaultBlockState()));

        FeatureUtils.register(ctx, ORE_MORECOAL, Feature.ORE, new OreConfiguration(energyList, 5));  //每堆数量
        FeatureUtils.register(ctx, ORE_MORECOAL_DEEP, Feature.ORE, new OreConfiguration(energyDeepList, 5));
        FeatureUtils.register(ctx, ORE_MORECOAL_NETHER, Feature.ORE, new OreConfiguration(netherList, 5));
        FeatureUtils.register(ctx, ORE_MORECOAL_END, Feature.ORE, new OreConfiguration(endList, 5));
    }
    // 创建ResourceKey的方法
    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String pName) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MoreCoal.MOD_ID,pName));
    }
}
