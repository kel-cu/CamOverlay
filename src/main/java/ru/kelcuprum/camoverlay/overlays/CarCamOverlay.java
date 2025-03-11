package ru.kelcuprum.camoverlay.overlays;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.camoverlay.CamOverlay;

import java.io.File;

public class CarCamOverlay extends AbstractOverlay {
    public CarCamOverlay() {
        super(Component.empty().withStyle(Style.EMPTY.withColor(0xFFff8000).withBold(true).withItalic(true)).append(Component.translatable("camoverlay.carcam")), "carcam");
    }

    @Override
    public void renderRound(GuiGraphics guiGraphics, int width, int height) {
        if(CamOverlay.config.getBoolean("RECORD_MODE", true)) {
            guiGraphics.fill(RenderType.guiOverlay(), 0, 0, guiGraphics.guiWidth(), 20, 0xFF000000);
            guiGraphics.fill(RenderType.guiOverlay(), 0, guiGraphics.guiHeight(), guiGraphics.guiWidth(), guiGraphics.guiHeight() - 20, 0xFF000000);
        }
    }
    public static File file = AlinLib.MINECRAFT.gameDirectory.toPath().resolve("screenshots").toFile();
    @Override
    public void renderText(GuiGraphics guiGraphics, int width, int height) {
        int i = 10;
        int yBottom = height - i - (minecraft.font.lineHeight / 2);
        int camK = 19 / 10;
        int camH = 19 / camK;
        int camW = 21 / camK;
        guiGraphics.blit(RenderType::guiTexturedOverlay, ResourceLocation.fromNamespaceAndPath("camoverlay", String.format("textures/overlays/carcam/%s.png", CamOverlay.config.getBoolean("RECORD_MODE", true) ? "rec" : "photo")), 5, 5, 0, 0, camW, camH, camW, camH);
        String text = "00:00:00";
        if(!CamOverlay.config.getBoolean("RECORD_MODE", true) && file.exists() && file.isDirectory()){
            text = String.format("%05d", file.listFiles().length);
        }
        int yTop = i - (minecraft.font.lineHeight / 2);
        guiGraphics.drawString(minecraft.font, text, width - 10 - minecraft.font.width(text), yTop, -1);
        if(CamOverlay.config.getBoolean("RECORD_MODE", true)) guiGraphics.drawCenteredString(minecraft.font, CamOverlay.localization.getParsedText("{camoverlay.overlay.time}"), width / 2, yBottom, -1);
    }

    @Override
    public void renderStatus(GuiGraphics guiGraphics, int width, int height) {
        double state = ((minecraft.player.getFoodData().getFoodLevel() + minecraft.player.getHealth()) / 2) / 20;
        int y = height - 20 + 5;
        int batteryH = 10;
        int batteryW = 20;
        int x = width - 10 - batteryW;
        int color = state < 0.25 ? 0xFFff8000 : 0xFFFFFFFF;
        guiGraphics.fill(x-2, y+(batteryH / 3), x, y + batteryH - (batteryH / 3), color);
        guiGraphics.fill(x, y, x+batteryW, y+batteryH, color);
        guiGraphics.fill(x+1, y+1, x+batteryW-1, y+batteryH-1, 0xFF7f7f7f);
        guiGraphics.fill((int) (x+2+((batteryW-2) - (batteryW-2) * state)), y+2, x+(batteryW-2), y+batteryH-2, 0xFFff8000);
    }
}
