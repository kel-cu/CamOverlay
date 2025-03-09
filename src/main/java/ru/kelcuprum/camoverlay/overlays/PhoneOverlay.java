package ru.kelcuprum.camoverlay.overlays;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.camoverlay.CamOverlay;

public class PhoneOverlay extends AbstractOverlay{
    public PhoneOverlay() {
        super(Component.translatable("camoverlay.phone"), "phone");
    }

    @Override
    public void renderText(GuiGraphics guiGraphics, int width, int height){
        Component date = Component.literal(AlinLib.localization.getParsedText(CamOverlay.config.getString("PHONE.TEXT", "Kel Caffeine")));
        Component cam = Component.translatable("camoverlay.phone.cam");
        int w = (minecraft.font.lineHeight * 2) + 2;
        guiGraphics.blit(RenderType::guiTexturedOverlay, ResourceLocation.fromNamespaceAndPath("camoverlay", "textures/overlays/phone/icon.png"), 30, height-28-(minecraft.font.lineHeight * 2) - 2, w, w, w, w, w, w);
        guiGraphics.drawString(minecraft.font, cam, 35+w, height-28-(minecraft.font.lineHeight * 2) - 2, 0xFFFFFFFF);
        guiGraphics.drawString(minecraft.font, date, 35+w, height-28-minecraft.font.lineHeight, 0xFFFFFFFF);
    }
}
