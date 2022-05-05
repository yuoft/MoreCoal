package com.yuo.morecoal.Gui;

import com.yuo.morecoal.MoreCoal;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypeRegistry {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MoreCoal.MODID);
    public static final RegistryObject<ContainerType<TorchPlacerContainer>> torchPlacerContainer = CONTAINERS.register("torch_placer_container", () ->
            IForgeContainerType.create((int windowId, PlayerInventory inv, PacketBuffer data) ->
                    new TorchPlacerContainer(windowId, inv)));

}
