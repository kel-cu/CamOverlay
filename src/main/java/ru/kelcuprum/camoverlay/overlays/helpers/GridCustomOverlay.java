package ru.kelcuprum.camoverlay.overlays.helpers;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.camoverlay.CamOverlay;

public class GridCustomOverlay extends AbstractHelper {
    public GridCustomOverlay() {
        super(Component.translatable("camoverlay.overlay.grid_custom"), "grid_custom");
    }

    @Override
    public void render(GuiGraphics guiGraphics, int width, int height) {
        int x = width / CamOverlay.config.getNumber("GRID_NUMBER", 2).intValue();
        int y = height / CamOverlay.config.getNumber("GRID_NUMBER", 2).intValue();
        int steps = CamOverlay.config.getNumber("GRID_NUMBER", 2).intValue() - 1;
        // x
        for(int i = 0;i<steps;i++){
            guiGraphics.fill(x+(x*i), 0, x+(x*i)+1, height, 0x7FFFFFFF);
        }
        // y
        for(int i = 0;i<steps;i++){
            guiGraphics.fill(0, y+(y*i), width, y+(y*i)+1, 0x7FFFFFFF);
        }
    }
}
