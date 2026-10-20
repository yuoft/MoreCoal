package com.yuo.morecoal.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.yuo.morecoal.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class TorchPlacerScreen extends AbstractContainerScreen<TorchPlacerContainer> {

    private static final ResourceLocation TEXTURE = ModUtils.fa("textures/gui/torch_placer.png");

    public TorchPlacerScreen(TorchPlacerContainer menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.titleLabelX = this.imageWidth / 2 - 22;
        this.inventoryLabelY = 70; // 原代码中注释掉的物品栏标签位置
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F); // color4f → setShaderColor
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);

        // 燃烧时间
        if (this.menu.isRunning()) {
            int k = Math.min(this.menu.getBurnTime(), 14);
            graphics.blit(TEXTURE, x + 49, y + 20 + 12 - k, 176, 14 - k, 14, k);
        }
        // 能量存储
        int l = Math.min(this.menu.getEnergy(), 52);
        graphics.blit(TEXTURE, x + 162, y + 17 + 52 - l, 176, 52 + 14 - l, 6, l);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.drawString(this.font, Integer.toString(this.menu.getEnergy()), 160, 5, 0x696969, false);
    }
}