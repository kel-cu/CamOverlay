package ru.kelcuprum.camoverlay.overlays;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class SafeOverlay extends AbstractOverlay{
    public SafeOverlay() {
        super(Component.literal("Safe Mode"), "safe_mode");
    }

    @Override
    public void renderText(GuiGraphics guiGraphics, int width, int height){
        List<FormattedCharSequence> text = minecraft.font.split(Component.translatable("camoverlay.safe_mode"), width-40);
        int size = minecraft.font.lineHeight+3;
        int y = height/2 - ((size * text.size())/2);
        for(FormattedCharSequence txt : text){
            guiGraphics.drawCenteredString(minecraft.font, txt, width/2, y, -1);
            y+=size;
        }
    }
}
