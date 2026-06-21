package ru.kelcuprum.camoverlay.overlays;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.camoverlay.CamOverlay;

public class PhoneOverlay extends AbstractOverlay{
    public PhoneOverlay() {
        super(Component.translatable("camoverlay.phone"), "phone");
    }

    @Override
    public void renderText(GuiGraphicsExtractor guiGraphics, int width, int height){
        Component datA = Component.literal(AlinLib.localization.getParsedText(CamOverlay.config.getString("PHONE.TEXT", "CamOverlay")));
        Component cam = Component.translatable("camoverlay.phone.cam");
        int w = (minecraft.font.lineHeight * 2) + 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath("camoverlay", "textures/overlays/phone/icon.png"), 30, height-28-(minecraft.font.lineHeight * 2) - 2, w, w, w, w, w, w);
        guiGraphics.text(minecraft.font, cam, 35+w, height-28-(minecraft.font.lineHeight * 2) - 2, 0xFFFFFFFF);
        guiGraphics.text(minecraft.font, datA, 35+w, height-28-minecraft.font.lineHeight, 0xFFFFFFFF);
        if(CamOverlay.config.getBoolean("PHONE.DATE", true)){
            Component date = Component.literal(CamOverlay.localization.getParsedText("{camoverlay.overlay.time}"));
            guiGraphics.text(minecraft.font, date, width - 30 - minecraft.font.width(date), height-28-minecraft.font.lineHeight, 0xFFFFFFFF);
        }
    }
}
