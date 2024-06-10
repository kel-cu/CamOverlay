package ru.kelcuprum.camoverlay.overlays;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.camoverlay.localization.StarScript;

import java.util.List;

public class PhoneOverlay extends AbstractOverlay{
    public PhoneOverlay() {
        super(Component.translatable("camoverlay.phone"), "phone");
    }

    @Override
    public void renderText(GuiGraphics guiGraphics, int width, int height){
        Component date = Component.literal(StarScript.parseText("{overlay.time}"));
        Component cam = Component.translatable("camoverlay.phone.cam");
        guiGraphics.drawString(minecraft.font, date, width - 30 - minecraft.font.width(date), height-28-minecraft.font.lineHeight, 0xFFFFFFFF); // BOTTOM RIGHT
        guiGraphics.drawString(minecraft.font, cam, 30, height-28-minecraft.font.lineHeight, 0xFFFFFFFF); // BOTTOM RIGHT
    }
}
