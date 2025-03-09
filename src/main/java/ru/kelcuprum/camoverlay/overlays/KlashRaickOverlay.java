package ru.kelcuprum.camoverlay.overlays;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.config.Localization;
import ru.kelcuprum.camoverlay.CamOverlay;

public class KlashRaickOverlay extends AbstractOverlay{
    public KlashRaickOverlay() {
        super(Component.translatable("camoverlay.klashraick"), "klashraick");
    }

    @Override
    public void renderRound(GuiGraphics guiGraphics, int width, int height){
        guiGraphics.fill(20, 20, 80, 22, 0xFFFFFFFF);
        guiGraphics.fill(20, 20, 22, 55, 0xFFFFFFFF);
        // RIGHT TOP
        guiGraphics.fill(width-80, 20, width-20, 22, 0xFFFFFFFF);
        guiGraphics.fill(width-22, 20, width-20, 55, 0xFFFFFFFF);

        // LEFT BOTTOM
        guiGraphics.fill(20, height-20, 80, height-22, 0xFFFFFFFF);
        guiGraphics.fill(20, height-20, 22, height-55, 0xFFFFFFFF);
        // RIGHT BOTTOM
        guiGraphics.fill(width-80, height-20, width-20, height-22, 0xFFFFFFFF);
        guiGraphics.fill(width-22, height-20, width-20, height-55, 0xFFFFFFFF);
    }
    @Override
    public void renderCursor(GuiGraphics guiGraphics, int width, int height){
        int x = width / 2;
        int y = height / 2;

        guiGraphics.fill(x - 4, y - 1, x + 5, y, 0xFFFFFFFF);
        guiGraphics.fill(x, y - 5, x + 1, y + 4, 0xFFFFFFFF);
    }
    @Override
    public void renderText(GuiGraphics guiGraphics, int width, int height){
        Component rec = Component.translatable("camoverlay.klashraick." + (CamOverlay.config.getBoolean("RECORD_MODE", true) ? "rec" : "photo"));
        Component heightS = Component.translatable("camoverlay.klashraick.height");
        Component cam = Component.translatable("camoverlay.klashraick.cam");
        Component rotate = Component.literal(String.format("%s°", CamOverlay.config.getNumber("ROTATE", 0F).floatValue()));
        // CENTER BOTTOM
        guiGraphics.drawCenteredString(minecraft.font, rec, width / 2, 22, 0xFFFFFFFF);
        // LEFT
        guiGraphics.drawString(minecraft.font, heightS, 30, height - 28 - minecraft.font.lineHeight, 0xFFFFFFFF); // BOTTOM
        guiGraphics.drawString(minecraft.font, cam, width-30-minecraft.font.width(cam), height - 28 - minecraft.font.lineHeight, 0xFFFFFFFF); // BOTTOM
        // RIGHT
        guiGraphics.drawString(minecraft.font, rotate, width - 30 - minecraft.font.width(rotate), 30, 0xFFFFFFFF); // TOP
    }
    @Override
    public void renderStatus(GuiGraphics guiGraphics, int width, int height){
        double state = ((minecraft.player.getFoodData().getFoodLevel() + minecraft.player.getHealth()) / 2) / 20;
        // round
        guiGraphics.fill(25+5, 25+5, 80+5, 27+5, 0xFFFFFFFF);
        guiGraphics.fill(25+5, 25+5, 27+5, 45+5, 0xFFFFFFFF);
        guiGraphics.fill(25+5, 43+5, 80+5, 45+5, 0xFFFFFFFF);
        guiGraphics.fill(78+5, 25+5, 80+5, 45+5, 0xFFFFFFFF);
        guiGraphics.fill(80+5, 30+5, 82+5, 40+5, 0xFFFFFFFF);
        // state
//                    guiGraphics.fill(80, 45, 25, 25, 0x7F000000);
        guiGraphics.fill(29+5, 41+5, (int) (29+5 + (47 * state)), 29+5, 0xFFFFFFFF);
    }
}
