package ru.kelcuprum.camoverlay.overlays;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.camoverlay.CamOverlay;

public class CinematicOverlay extends AbstractOverlay {
    public CinematicOverlay() {
        super(Component.translatable("camoverlay.cinematic"), "cinematic");
    }

    @Override
    public void renderRound(GuiGraphics guiGraphics, int width, int height) {
        guiGraphics.fill(RenderType.guiOverlay(), 0, 0, guiGraphics.guiWidth(), CamOverlay.config.getNumber("CINEMATIC.SIZE", 30).intValue(), 0xFF000000);
        guiGraphics.fill(RenderType.guiOverlay(), 0, guiGraphics.guiHeight(), guiGraphics.guiWidth(), guiGraphics.guiHeight() - CamOverlay.config.getNumber("CINEMATIC.SIZE", 30).intValue(), 0xFF000000);
    }

    @Override
    public void renderText(GuiGraphics guiGraphics, int width, int height) {
        int i = CamOverlay.config.getNumber("CINEMATIC.SIZE", 30).intValue() / 2;
        int yBottom = height - i - (minecraft.font.lineHeight / 2);
        int yTop = i - (minecraft.font.lineHeight / 2);
        //
        guiGraphics.drawCenteredString(minecraft.font, AlinLib.localization.getParsedText(CamOverlay.config.getString("CINEMATIC.TOP_TEXT", "{world.name}")), width / 2, yTop, -1);
        //
        guiGraphics.drawCenteredString(minecraft.font, AlinLib.localization.getParsedText(CamOverlay.config.getString("CINEMATIC.BOTTOM_TEXT", "FPS: {minecraft.fps} Time: {time} Frame: {camoverlay.window.width}x{camoverlay.window.height}")), width / 2, yBottom, -1);
    }
}
