package ru.kelcuprum.camoverlay.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.gui.components.buttons.Button;
import ru.kelcuprum.alinlib.gui.components.buttons.ButtonBoolean;
import ru.kelcuprum.alinlib.gui.components.sliders.SliderInteger;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;
import ru.kelcuprum.alinlib.gui.toast.AlinaToast;
import ru.kelcuprum.camoverlay.CamOverlay;

import static ru.kelcuprum.camoverlay.CamOverlay.MINECRAFT;
import static ru.kelcuprum.camoverlay.CamOverlay.TOAST_ICON;

public class AdvancedConfigScreen extends Screen {
    private final Screen parent;
    public AdvancedConfigScreen(Screen parent) {
        super(Component.translatable("camoverlay.options.advanced"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        if(CamOverlay.config.getBoolean("ENABLE", false)){
            CamOverlay.config.setBoolean("ENABLE", false);
            this.minecraft.getToasts().addToast(new AlinaToast(Component.translatable("camoverlay.name"), Component.translatable("camoverlay.toast.disable.screen"), TOAST_ICON));
            MINECRAFT.options.fov().set(CamOverlay.lastFOV);
        }
        int x = width/2;
        int size = 180;
        addRenderableWidget(new TextBox(x-100, 0, 200, 35, title, true));

        addRenderableWidget(new ButtonBoolean(x-90, 40, size, 20, CamOverlay.config, "DISABLE.HANDS", true, Component.translatable("camoverlay.options.advanced.disable.hands")));
        addRenderableWidget(new SliderInteger(x-90, 65, size, 20, CamOverlay.config, "ROTATE", 0, -180, 180, Component.translatable("camoverlay.options.advanced.rotate")));
        addRenderableWidget(new SliderInteger(x-90, 90, size, 20, CamOverlay.config, "FOV", 0, 30, 110, Component.translatable("camoverlay.options.advanced.fov")));
        addRenderableWidget(new ButtonBoolean(x-90, 115, size, 20, CamOverlay.config, "ENABLE.SET_FOV", true, Component.translatable("camoverlay.options.advanced.enable.set_fov")));
        addRenderableWidget(new ButtonBoolean(x-90, 140, size, 20, CamOverlay.config, "WORLD_TIME", false, Component.translatable("camoverlay.options.advanced.world_time")));


        addRenderableWidget(new Button(x-90, height-30, size, 20, CommonComponents.GUI_BACK, (s) -> {
            assert this.minecraft != null;
            this.minecraft.setScreen(this.parent);
        }));
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = width/2;
        guiGraphics.fill(x-100, 0, x+100, height, 0x7F000000);
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
    }
}
