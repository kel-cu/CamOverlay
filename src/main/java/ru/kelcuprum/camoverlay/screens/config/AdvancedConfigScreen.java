package ru.kelcuprum.camoverlay.screens.config;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.gui.InterfaceUtils;
import ru.kelcuprum.alinlib.gui.components.buttons.ButtonConfigBoolean;
import ru.kelcuprum.alinlib.gui.components.buttons.base.Button;
import ru.kelcuprum.alinlib.gui.components.sliders.SliderConfigInteger;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;
import ru.kelcuprum.camoverlay.CamOverlay;

import static ru.kelcuprum.camoverlay.CamOverlay.MINECRAFT;

public class AdvancedConfigScreen{
    private static final InterfaceUtils.DesignType dType = InterfaceUtils.DesignType.FLAT;
    public static Screen build(Screen parent) {
        ConfigScreenBuilder builder = new ConfigScreenBuilder(parent, Component.translatable("camoverlay.name"), dType)
                .addPanelWidget(new Button(10, 40, 100, 20, dType, Component.translatable("camoverlay.options"), (s) -> MINECRAFT.setScreen(ConfigScreen.build(parent))))
                .addPanelWidget(new Button(10, 65, 100, 20, dType, Component.translatable("camoverlay.options.advanced"), (s) -> MINECRAFT.setScreen(AdvancedConfigScreen.build(parent))));
        builder.addWidget(new TextBox(140, 5, Component.translatable("camoverlay.options.advanced"), true));

        builder.addWidget(new ButtonConfigBoolean(140, 30, dType, CamOverlay.config, "DISABLE.HANDS", true, Component.translatable("camoverlay.options.advanced.disable.hands")));
        builder.addWidget(new SliderConfigInteger(140, 55, dType, CamOverlay.config, "ROTATE", 0, -180, 180, Component.translatable("camoverlay.options.advanced.rotate")));
        builder.addWidget(new ButtonConfigBoolean(140, 105, dType, CamOverlay.config, "ENABLE.SET_FOV", true, Component.translatable("camoverlay.options.advanced.enable.set_fov")));
        builder.addWidget(new ButtonConfigBoolean(140, 130, dType, CamOverlay.config, "WORLD_TIME", false, Component.translatable("camoverlay.options.advanced.world_time")));
        builder.addWidget(new ButtonConfigBoolean(140, 130, dType, CamOverlay.config, "ENABLE.TOAST", false, Component.translatable("camoverlay.options.advanced.enable.toast")));

        return builder.build();
    }
}
