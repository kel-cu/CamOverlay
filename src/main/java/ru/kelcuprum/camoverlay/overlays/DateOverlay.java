package ru.kelcuprum.camoverlay.overlays;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.camoverlay.CamOverlay;

public class DateOverlay extends AbstractOverlay{
    public DateOverlay() {
        super(Component.translatable("camoverlay.date"), "date");
    }

    @Override
    public void renderText(GuiGraphics guiGraphics, int width, int height){
        Component date = Component.literal(CamOverlay.localization.getParsedText("{camoverlay.overlay.time}"));
        guiGraphics.drawString(minecraft.font, date, width - 30 - minecraft.font.width(date), height-28-minecraft.font.lineHeight, 0xFFFFFFFF); // BOTTOM RIGHT
    }
}
