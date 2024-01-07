package ru.kelcuprum.camoverlay.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.kelcuprum.camoverlay.CamOverlay;
import ru.kelcuprum.camoverlay.InterfaceUtils;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    private int screenWidth;
    @Shadow
    private int screenHeight;

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) {
        if(!CamOverlay.config.getBoolean("ENABLE", false)) return;
        this.screenWidth = guiGraphics.guiWidth();
        this.screenHeight = guiGraphics.guiHeight();
        InterfaceUtils.Type type = InterfaceUtils.getType();
        type.renderRound(guiGraphics, screenWidth, screenHeight);
        type.renderCroshair(guiGraphics, screenWidth, screenHeight);
        type.renderText(guiGraphics, minecraft, screenWidth, screenHeight);
        type.renderStatus(guiGraphics, screenWidth, screenHeight);
        if(CamOverlay.config.getBoolean("ENABLE.OVERLAY", false)) InterfaceUtils.getOverlay().renderOverlay(guiGraphics, screenWidth, screenHeight);
        ci.cancel();
    }
}
