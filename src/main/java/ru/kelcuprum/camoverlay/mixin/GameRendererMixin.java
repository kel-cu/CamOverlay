package ru.kelcuprum.camoverlay.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.kelcuprum.camoverlay.CamOverlay;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/math/Axis;rotationDegrees(F)Lorg/joml/Quaternionf;",
                    ordinal = 2
            )
    )
    public void renderWorld(float partialTicks, long finishTimeNano, PoseStack poseStack, CallbackInfo ci){
//        matrix.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(((RollCamera) camera).doABarrelRoll$getRo
        if(!CamOverlay.config.getBoolean("ENABLE", false)) return;
        poseStack.mulPose(Axis.ZP.rotationDegrees(CamOverlay.config.getNumber("ROTATE", 0f).floatValue()));
    }
}
