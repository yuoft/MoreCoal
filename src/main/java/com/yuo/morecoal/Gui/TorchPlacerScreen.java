package com.yuo.morecoal.Gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.yuo.morecoal.MoreCoal;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.FurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TorchPlacerScreen extends ContainerScreen<TorchPlacerContainer> {
    private final ResourceLocation TORCH_PLACER_RESOURCE = new ResourceLocation(MoreCoal.MODID, "textures/gui/torch_placer.png");
    private final int textureWidth = 176;
    private final int textureHeight = 166;

    public TorchPlacerScreen(TorchPlacerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.xSize = textureWidth;
        this.ySize = textureHeight;
        this.titleX = this.xSize / 2 -22;
    }

    //渲染背景
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F); //确保颜色正常
        this.minecraft.getTextureManager().bindTexture(TORCH_PLACER_RESOURCE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        blit(matrixStack, i, j, 0, 0, xSize, ySize);
        //燃烧时间
        if (this.container.isRunning()) {
            int k = this.container.getBurnTime() > 14 ? 14 : this.container.getBurnTime();
            this.blit(matrixStack, i + 49, j + 20 + 12 - k, 176, 14 - k, 14, k);
        }
        //能量存储
        int l = this.container.getEnergy() > 52 ? 52 : this.container.getEnergy();
        this.blit(matrixStack, i + 162, j + 17 + 52 - l, 176, 52 + 14 - l, 6, l);
    }

    //渲染组件
    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        super.drawGuiContainerForegroundLayer(matrixStack, x, y);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F); //确保颜色正常
//        drawCenteredString(matrixStack, this.font,new TranslationTextComponent("gui.morecoal.torch_placer"),90,5, 0x696969);
//        drawString(matrixStack, this.font, new TranslationTextComponent("gui.morecoal.inventory"), 8, 70, 0x696969);
        drawString(matrixStack, this.font, Integer.toString(this.container.getEnergy()), 160, 5, 0x696969);
    }
}
