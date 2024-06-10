package ru.kelcuprum.camoverlay.overlays.helpers;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class Grid3x3Overlay extends AbstractHelper {
    public Grid3x3Overlay() {
        super(Component.translatable("camoverlay.overlay.grid_3x3"), "grid_3x3");
    }

    @Override
    public void render(GuiGraphics guiGraphics, int width, int height) {
        int x = width / 3;
        int y = height / 3;
        // x
        guiGraphics.fill(x, 0, x+1, height, 0x7FFFFFFF);
        guiGraphics.fill(x*2, 0, (x*2)+1, height, 0x7FFFFFFF);
        // y
        guiGraphics.fill(0, y, width, y+1, 0x7FFFFFFF);
        guiGraphics.fill(0, y*2, width, (y*2)+1, 0x7FFFFFFF);
    }
}
