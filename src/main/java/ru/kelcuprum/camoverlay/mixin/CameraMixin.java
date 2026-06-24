package ru.kelcuprum.camoverlay.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import ru.kelcuprum.camoverlay.CamOverlay;

@Mixin(Camera.class)
public class CameraMixin {
    @ModifyReturnValue(at = @At("RETURN"), method = "calculateFov")
    private float calculateFov(float original) {
        if(CamOverlay.config.getBoolean("ENABLE", false) && CamOverlay.config.getBoolean("ENABLE.SET_FOV", true)) return CamOverlay.getFov(original);
        else return original;
    }

    @ModifyReturnValue(at = @At("RETURN"), method = "calculateHudFov")
    private float calculateHudFov(float original) {
        if(CamOverlay.config.getBoolean("ENABLE", false) && CamOverlay.config.getBoolean("ENABLE.SET_FOV", true)) return CamOverlay.getFov(original);
        else return original;
    }
}
