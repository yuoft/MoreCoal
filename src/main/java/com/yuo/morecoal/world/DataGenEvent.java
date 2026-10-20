package com.yuo.morecoal.world;

import com.yuo.morecoal.MoreCoal;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MoreCoal.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEvent {

    @SubscribeEvent
    public static void addLoot(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<Provider> lookup = event.getLookupProvider();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
        }

        if (event.includeServer()) {
            generator.addProvider(true, new OreWorldGen(output, lookup));
        }
    }

}
