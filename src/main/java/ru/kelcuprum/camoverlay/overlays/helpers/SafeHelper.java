package ru.kelcuprum.camoverlay.overlays.helpers;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import ru.kelcuprum.camoverlay.OverlayUtils;
import ru.kelcuprum.camoverlay.overlays.AbstractOverlay;
import ru.kelcuprum.camoverlay.overlays.SafeOverlay;

import java.util.List;

public class SafeHelper extends AbstractHelper {
    public SafeHelper() {
        super(Component.literal("Safe Mode"), "safe_mode");
    }

    @Override
    public void render(GuiGraphics guiGraphics, int width, int height){
        boolean andOverlay = OverlayUtils.getSelectedOverlay() instanceof SafeOverlay;
        List<FormattedCharSequence> text = minecraft.font.split(Component.translatable(andOverlay ? "camoverlay.safe_mode.helper.only_text" : "camoverlay.safe_mode.helper"), width-40);
        int size = minecraft.font.lineHeight+3;
        int y = height/2 - ((size * text.size())/2);
        if(andOverlay) y += ((size * (text.size()+1)));
        for(FormattedCharSequence txt : text){
            guiGraphics.drawCenteredString(minecraft.font, txt, width/2, y, -1);
            y+=size;
        }
    }
}
