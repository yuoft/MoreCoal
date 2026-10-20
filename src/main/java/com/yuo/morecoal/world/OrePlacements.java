package com.yuo.morecoal.world;

import com.yuo.morecoal.MoreCoal;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class OrePlacements {
    // 创建对应的placement的资源键
    public static final ResourceKey<PlacedFeature> ORE_MORECOAL = createKey("ore_morecoal");
    public static final ResourceKey<PlacedFeature> ORE_MORECOAL_DEEP = createKey("ore_morecoal_deep");
    public static final ResourceKey<PlacedFeature> ORE_MORECOAL_NETHER = createKey("ore_morecoal_nether");
    public static final ResourceKey<PlacedFeature> ORE_MORECOAL_END = createKey("ore_morecoal_end");

    public static void bootstrap(BootstapContext<PlacedFeature> ctx) {
        HolderGetter<ConfiguredFeature<?, ?>> features = ctx.lookup(Registries.CONFIGURED_FEATURE);

        // ★ 变量名与配置严格对应
        Holder<ConfiguredFeature<?, ?>> energyHolder = features.getOrThrow(OreFeatures.ORE_MORECOAL);
        Holder<ConfiguredFeature<?, ?>> energyDeepHolder = features.getOrThrow(OreFeatures.ORE_MORECOAL_DEEP);
        Holder<ConfiguredFeature<?, ?>> energyNetherHolder = features.getOrThrow(OreFeatures.ORE_MORECOAL_NETHER);
        Holder<ConfiguredFeature<?, ?>> energyEndHolder = features.getOrThrow(OreFeatures.ORE_MORECOAL_END);

        // 主世界能量矿：-64 ~ 128，每区块 8 处
        PlacementUtils.register(ctx, ORE_MORECOAL, energyHolder,
                commonOrePlacement(8,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(128))));
        PlacementUtils.register(ctx, ORE_MORECOAL_DEEP, energyDeepHolder,
                commonOrePlacement(8,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0))));

        // 下界能量矿：0 ~ 96，每区块 10 处
        PlacementUtils.register(ctx, ORE_MORECOAL_NETHER, energyNetherHolder,
                commonOrePlacement(10,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(128))));

        // ★ 末地能量矿：0 ~ 80（末地地表在 Y=60~80 左右）
        PlacementUtils.register(ctx, ORE_MORECOAL_END, energyEndHolder,
                commonOrePlacement(8,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(64))));
    }



    private static List<PlacementModifier> orePlacement(PlacementModifier pCountPlacement, PlacementModifier pHeightRange) {
        return List.of(pCountPlacement, InSquarePlacement.spread(), pHeightRange, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }
    // 我们没有使用这个，对应了原版的稀有矿物生成
    private static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }

    public static ResourceKey<PlacedFeature> createKey(String pKey) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MoreCoal.MOD_ID,pKey));
    }
}
