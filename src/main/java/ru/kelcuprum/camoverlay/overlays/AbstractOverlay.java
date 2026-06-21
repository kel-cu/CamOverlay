package ru.kelcuprum.camoverlay.overlays;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.camoverlay.CamOverlay;

public abstract class AbstractOverlay {
    public Minecraft minecraft = AlinLib.MINECRAFT;
    public Component name;
    public String id;

    public AbstractOverlay(Component name, String id){
        this.name = name;
        this.id = id;
    }

    public void render(GuiGraphicsExtractor guiGraphics, int width, int height){
        renderRound(guiGraphics, width, height);
        if(!CamOverlay.config.getBoolean("ENABLE.HELPER", false) && CamOverlay.config.getBoolean("ENABLE.CURSOR", true)) renderCursor(guiGraphics, width, height);
        renderText(guiGraphics, width, height);
        renderStatus(guiGraphics, width, height);
    }

    public void renderRound(GuiGraphicsExtractor guiGraphics, int width, int height){

    }
    public void renderCursor(GuiGraphicsExtractor guiGraphics, int width, int height){

    }
    public void renderText(GuiGraphicsExtractor guiGraphics, int width, int height){

    }
    public void renderStatus(GuiGraphicsExtractor guiGraphics, int width, int height){

    }
}
