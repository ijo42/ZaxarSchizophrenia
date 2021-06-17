package ru.ijo42.grinder;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GrinderContainerGui extends GuiContainer {
    public static final int WIDTH = 256;
    public static final int HEIGHT = 256;

    private static final ResourceLocation background = new ResourceLocation(Grinder.MOD_ID, "textures/gui/container/grinder.png");

    public GrinderContainerGui(GrinderContainer container) {
        super(container);
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}