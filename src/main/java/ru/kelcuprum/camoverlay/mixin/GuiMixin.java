package ru.kelcuprum.camoverlay.mixin;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.kelcuprum.camoverlay.CamOverlay;
import ru.kelcuprum.camoverlay.OverlayUtils;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    private int screenWidth;
    @Shadow
    private int screenHeight;

    @Shadow @Final private Minecraft minecraft;

    @Shadow @Final private ChatComponent chat;

    @Shadow private int tickCount;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) {
        if(!CamOverlay.config.getBoolean("ENABLE", false)) return;
        this.screenWidth = guiGraphics.guiWidth();
        this.screenHeight = guiGraphics.guiHeight();
        Window window = this.minecraft.getWindow();
        OverlayUtils.Type type = OverlayUtils.getType();
        type.renderRound(guiGraphics, screenWidth, screenHeight);
        type.renderCroshair(guiGraphics, screenWidth, screenHeight);
        type.renderText(guiGraphics, minecraft, screenWidth, screenHeight);
        type.renderStatus(guiGraphics, screenWidth, screenHeight);
        if(CamOverlay.config.getBoolean("ENABLE.OVERLAY", false)) OverlayUtils.getOverlay().renderOverlay(guiGraphics, screenWidth, screenHeight);
        if(minecraft.screen instanceof ChatScreen){
            int n = Mth.floor(this.minecraft.mouseHandler.xpos() * (double)window.getGuiScaledWidth() / (double)window.getScreenWidth());
            int p = Mth.floor(this.minecraft.mouseHandler.ypos() * (double)window.getGuiScaledHeight() / (double)window.getScreenHeight());
            this.minecraft.getProfiler().push("chat");
            this.chat.render(guiGraphics, tickCount, n, p);
        }
        ci.cancel();
    }
}
