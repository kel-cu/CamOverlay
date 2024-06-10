package ru.kelcuprum.camoverlay.overlays.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.AlinLib;

public abstract class AbstractHelper {
    public Minecraft minecraft = AlinLib.MINECRAFT;
    public Component name;
    public String id;

    public AbstractHelper(Component name, String id){
        this.name = name;
        this.id = id;
    }

    public void render(GuiGraphics guiGraphics, int width, int height){
    }
}
