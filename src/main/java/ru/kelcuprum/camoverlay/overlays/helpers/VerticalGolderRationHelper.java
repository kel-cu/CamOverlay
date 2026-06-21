package ru.kelcuprum.camoverlay.overlays.helpers;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import ru.kelcuprum.camoverlay.CamOverlay;

public class VerticalGolderRationHelper extends AbstractHelper {
    public VerticalGolderRationHelper() {
        super(Component.translatable("camoverlay.overlay.vertical_golden_ratio"), "vertical_golden_ratio");
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, int width, int height) {
        Identifier texture = Identifier.fromNamespaceAndPath("camoverlay", "textures/overlays/golden_ratio/grv_"+CamOverlay.config.getString("GOLDEN_RATIO.ROTATE", "0")+".png");
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,texture, 0, 0, width, height, width, height, width, height);
    }
}
