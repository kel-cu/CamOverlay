package ru.kelcuprum.camoverlay.mixin.world;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.camoverlay.CamOverlay;

@Mixin(TridentItem.class)
public class TridentMixin {

    @Inject(method = "releaseUsing", at= @At(value = "HEAD"), cancellable = true)
    public void use(ItemStack stack, Level level, LivingEntity entity, int timeLeft, CallbackInfoReturnable<Boolean> cir){
        assert AlinLib.MINECRAFT.player != null;
        if(entity.getId() != AlinLib.MINECRAFT.player.getId()) return;
        if(CamOverlay.config.getBoolean("ENABLE", false) && CamOverlay.config.getBoolean("ENABLE_WEATHER", false) && CamOverlay.config.getBoolean("ENABLE_RAIN", false))
            cir.setReturnValue(false);
    }
}
