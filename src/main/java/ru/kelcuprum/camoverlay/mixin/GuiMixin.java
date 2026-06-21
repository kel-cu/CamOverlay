package ru.kelcuprum.camoverlay.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.kelcuprum.camoverlay.CamOverlay;
import ru.kelcuprum.camoverlay.OverlayUtils;

@Mixin(Hud.class)
public abstract class GuiMixin {
    @Inject(method = "extractRenderState", at = @At("HEAD"), cancellable = true)
    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if(!CamOverlay.config.getBoolean("ENABLE", false)) return;
        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();
        if(CamOverlay.config.getBoolean("ENABLE.HELPER", false)) OverlayUtils.getSelectedHelper().render(guiGraphics, screenWidth, screenHeight);
        OverlayUtils.getSelectedOverlay().render(guiGraphics, screenWidth, screenHeight);
        ci.cancel();
    }
}
