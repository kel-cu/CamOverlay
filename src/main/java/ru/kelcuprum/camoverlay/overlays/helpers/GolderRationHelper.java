package ru.kelcuprum.camoverlay.overlays.helpers;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import ru.kelcuprum.camoverlay.CamOverlay;

public class GolderRationHelper extends AbstractHelper {
    public GolderRationHelper() {
        super(Component.translatable("camoverlay.overlay.golden_ratio"), "golden_ratio");
    }

    @Override
    public void render(GuiGraphics guiGraphics, int width, int height) {
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath("camoverlay", "textures/overlays/golden_ratio/gr_"+ CamOverlay.config.getString("GOLDEN_RATIO.ROTATE", "0")+".png");
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        guiGraphics.blit(texture, 0, 0, width, height, 0f, 0f, 1280, 720, 1280, 720);
        RenderSystem.defaultBlendFunc();
    }
}
