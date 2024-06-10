package ru.kelcuprum.camoverlay.overlays;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.config.Localization;
import ru.kelcuprum.camoverlay.CamOverlay;

public class CamikonShotOverlay extends AbstractOverlay{
    public CamikonShotOverlay() {
        super(Component.translatable("camoverlay.camikonshot"), "camikon");
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
        Component rec = Component.translatable("camoverlay.camikonshot." + (CamOverlay.config.getBoolean("RECORD_MODE", true) ? "rec" : "photo"));
        Component cam = Component.translatable("camoverlay.camikonshot.cam");
        Component info = Component.literal(String.format("Frame: %sx%s  FPS: %s  FOV: %s", minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight(), minecraft.getFps(), Localization.getRounding(CamOverlay.getFov(minecraft.options.fov().get()), true)));
        Component rotate = Component.literal(String.format("%s°", CamOverlay.config.getNumber("ROTATE", 0F).floatValue()));
        // CENTER BOTTOM
        guiGraphics.drawCenteredString(minecraft.font, info, width / 2, height - 20 - minecraft.font.lineHeight, 0xFFFFFFFF);
        // LEFT
        guiGraphics.drawString(minecraft.font, rec, 30, 30, 0xFFFFFFFF); // TOP
        guiGraphics.drawString(minecraft.font, cam, 30, height - 28 - minecraft.font.lineHeight, 0xFFFFFFFF); // BOTTOM
        // RIGHT
        guiGraphics.drawString(minecraft.font, rotate, width - 30 - minecraft.font.width(rotate), 30, 0xFFFFFFFF); // TOP
    }
    @Override
    public void renderStatus(GuiGraphics guiGraphics, int width, int height){
        guiGraphics.fill(width - 80, height - 30, width - 25, height - 25, 0x7F000000);
        double stateHealth = (minecraft.player.getHealth() / minecraft.player.getMaxHealth());
        guiGraphics.fill(width - 25, height - 30, (int) (width - 25 - (55 * stateHealth)), height - 25, 0xFFFFFFFF);

        guiGraphics.fill(width - 80, height - 38, width - 25, height - 33, 0x7F000000);
        double stateFood = ((double) minecraft.player.getFoodData().getFoodLevel() / 20);
        guiGraphics.fill(width - 25, height - 38, (int) (width - 25 - (55 * stateFood)), height - 33, 0xFFFFFFFF);
    }
}
