package ru.kelcuprum.camoverlay.mixin;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.kelcuprum.camoverlay.CamOverlay;

@Mixin(Camera.class)
public class CameraMixin {
    @Inject(at = @At("HEAD"), method = "getFov", cancellable = true)
    private void getFov(CallbackInfoReturnable<Float> cir){
        if(CamOverlay.config.getBoolean("ENABLE", false) && CamOverlay.config.getBoolean("ENABLE.SET_FOV", true)) cir.setReturnValue(CamOverlay.getFov(cir.getReturnValueF()));
    }
}
