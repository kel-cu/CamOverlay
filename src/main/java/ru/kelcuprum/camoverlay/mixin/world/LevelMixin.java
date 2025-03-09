package ru.kelcuprum.camoverlay.mixin.world;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.kelcuprum.camoverlay.CamOverlay;

@Mixin(Level.class)
public abstract class LevelMixin {


    @Shadow @Nullable public abstract MinecraftServer getServer();

    @Inject(at = @At("TAIL"), method = "getRainLevel", cancellable = true)
    private void getRainGradient(float delta, CallbackInfoReturnable<Float> ci) {
        if(!CamOverlay.config.getBoolean("ENABLE", false)) return;
        if(this.getServer() != null) return;
        if(CamOverlay.config.getBoolean("ENABLE_WEATHER", false))
            ci.setReturnValue(CamOverlay.config.getBoolean("ENABLE_RAIN", false) ? 1f : 0f);
    }

    @Inject(at = @At("TAIL"), method = "getThunderLevel", cancellable = true)
    private void getThunderGradient(float delta, CallbackInfoReturnable<Float> ci) {
        if(!CamOverlay.config.getBoolean("ENABLE", false)) return;
        if(this.getServer() != null) return;
        if(CamOverlay.config.getBoolean("ENABLE_WEATHER", false))
            ci.setReturnValue(CamOverlay.config.getBoolean("ENABLE_THUNDERING", false) ? 1f : 0f);
    }


    @Inject(at = @At("TAIL"), method = "isRaining", cancellable = true)
    private void isRaining(CallbackInfoReturnable<Boolean> ci) {
        if(!CamOverlay.config.getBoolean("ENABLE", false)) return;
        if(getServer() != null) return;
        if (CamOverlay.config.getBoolean("ENABLE_WEATHER", false)) ci.setReturnValue(CamOverlay.config.getBoolean("ENABLE_RAIN", false));
    }

    @Inject(at = @At("TAIL"), method = "isThundering", cancellable = true)
    private void isThundering(CallbackInfoReturnable<Boolean> ci) {
        if(!CamOverlay.config.getBoolean("ENABLE", false)) return;
        if(getServer() != null) return;
        if (CamOverlay.config.getBoolean("ENABLE_WEATHER", false)) ci.setReturnValue(CamOverlay.config.getBoolean("ENABLE_THUNDERING", false));
    }
}
