package ru.kelcuprum.camoverlay.mixin;

import net.minecraft.client.MouseHandler;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ru.kelcuprum.camoverlay.CamOverlay;

@Mixin(MouseHandler.class)
public class MouseMixin {
    @Redirect(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;swapPaint(D)V"))
    private void swapPoint(Inventory instance, double direction){
        if(!(CamOverlay.config.getBoolean("ENABLE", false) && CamOverlay.config.getBoolean("ENABLE.SCROLL_FOV", true))) instance.swapPaint(direction);
        else CamOverlay.changeFov(direction, true);
    }
}
