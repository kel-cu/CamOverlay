package ru.kelcuprum.camoverlay.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.kelcuprum.camoverlay.CamOverlay;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "bobHurt", at= @At(value = "HEAD"))
    public void bobHurt(PoseStack poseStack, float f, CallbackInfo ci){
        if(!CamOverlay.config.getBoolean("ENABLE", false)) return;
        poseStack.mulPose(Axis.ZP.rotationDegrees(CamOverlay.config.getNumber("ROTATE", 0f).floatValue()));
    }
    @Inject(at = @At(value = "RETURN", ordinal = 1), method = "getFov", cancellable = true)
    private void getFov(Camera activeRenderInfo, float partialTicks, boolean useFOVSetting, CallbackInfoReturnable<Float> cir){
        if(CamOverlay.config.getBoolean("ENABLE", false) && CamOverlay.config.getBoolean("ENABLE.SET_FOV", true)) cir.setReturnValue(CamOverlay.getFov(cir.getReturnValueF()));
    }
}
