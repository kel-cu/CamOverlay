package ru.kelcuprum.camoverlay.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.kelcuprum.camoverlay.CamOverlay;

@Mixin(MouseHandler.class)
public class MouseMixin {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "onScroll", at = @At(value = "HEAD"), cancellable = true)
    private void swapPoint(long windowPointer, double xOffset, double yOffset, CallbackInfo ci){
        if (windowPointer == Minecraft.getInstance().getWindow().handle() && CamOverlay.config.getBoolean("ENABLE", false) && CamOverlay.config.getBoolean("ENABLE.SCROLL_FOV", true)) {
            if(minecraft.gui.screen() != null || minecraft.gui.overlay() != null) return;
            CamOverlay.changeFov(yOffset, true);
            ci.cancel();
        }
    }
}
