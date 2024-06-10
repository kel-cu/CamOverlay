package ru.kelcuprum.camoverlay.mixin;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.kelcuprum.camoverlay.CamOverlay;
import ru.kelcuprum.camoverlay.OverlayUtils;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow @Final private Minecraft minecraft;

    @Shadow @Final private ChatComponent chat;

    @Shadow private int tickCount;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if(!CamOverlay.config.getBoolean("ENABLE", false)) return;
        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();
//        if(CamOverlay.getSelectedOverlay() != null){
            OverlayUtils.getSelectedOverlay().render(guiGraphics, screenWidth, screenHeight);
//        }

        if(CamOverlay.config.getBoolean("ENABLE.HELPER", false)) OverlayUtils.getSelectedHelper().render(guiGraphics, screenWidth, screenHeight);
        if(minecraft.screen instanceof ChatScreen){
            int n = Mth.floor(this.minecraft.mouseHandler.xpos() * (double)screenWidth / (double)screenHeight);
            int p = Mth.floor(this.minecraft.mouseHandler.ypos() * (double)screenWidth / (double)screenHeight);
            this.minecraft.getProfiler().push("chat");
            this.chat.render(guiGraphics, tickCount, n, p, true);
        }
        ci.cancel();
    }
}
