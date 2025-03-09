package ru.kelcuprum.camoverlay.mixin.world;

import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.kelcuprum.camoverlay.CamOverlay;

@Mixin(ClientLevel.ClientLevelData.class)
public class ClientLevelDataMixin {
    @Inject(at = @At("TAIL"), method = "getDayTime", cancellable = true)
    private void getTimeOfDay(CallbackInfoReturnable<Long> ci) {
        if(!CamOverlay.config.getBoolean("ENABLE", false)) return;
        if (CamOverlay.config.getBoolean("ENABLE_TIME", false)) ci.setReturnValue(CamOverlay.config.getNumber("TIME", 18000L).longValue());
    }

    @Inject(at = @At("TAIL"), method = "isRaining", cancellable = true)
    private void isRaining(CallbackInfoReturnable<Boolean> ci) {
        if(!CamOverlay.config.getBoolean("ENABLE", false)) return;
        if (CamOverlay.config.getBoolean("ENABLE_WEATHER", false)) ci.setReturnValue(CamOverlay.config.getBoolean("ENABLE_RAIN", false));
    }

    @Inject(at = @At("TAIL"), method = "isThundering", cancellable = true)
    private void isThundering(CallbackInfoReturnable<Boolean> ci) {
        if(!CamOverlay.config.getBoolean("ENABLE", false)) return;
        if (CamOverlay.config.getBoolean("ENABLE_WEATHER", false)) ci.setReturnValue(CamOverlay.config.getBoolean("ENABLE_THUNDERING", false));
    }
}
