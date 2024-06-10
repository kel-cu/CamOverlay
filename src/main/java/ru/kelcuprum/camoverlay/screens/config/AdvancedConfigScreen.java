package ru.kelcuprum.camoverlay.screens.config;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBooleanBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.slider.SliderBuilder;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;
import ru.kelcuprum.camoverlay.CamOverlay;

import static ru.kelcuprum.camoverlay.CamOverlay.MINECRAFT;

public class AdvancedConfigScreen{
    public static Screen build(Screen parent) {
        ConfigScreenBuilder builder = new ConfigScreenBuilder(parent, Component.translatable("camoverlay.name"))
                .addPanelWidget(new ButtonBuilder(Component.translatable("camoverlay.options"), (s) -> MINECRAFT.setScreen(ConfigScreen.build(parent))).build())
                .addPanelWidget(new ButtonBuilder(Component.translatable("camoverlay.options.advanced"), (s) -> MINECRAFT.setScreen(AdvancedConfigScreen.build(parent))).build())
                .addPanelWidget(new ButtonBuilder(Component.translatable("camoverlay.options.overlays"), (s) -> MINECRAFT.setScreen(OverlaysScreen.build(parent))).build());;

        builder.addWidget(new TextBox(Component.translatable("camoverlay.options.advanced"), true))
                .addWidget(new ButtonBooleanBuilder(Component.translatable("camoverlay.options.advanced.disable.hands"), true).setConfig(CamOverlay.config, "DISABLE.HANDS").build())
                .addWidget(new SliderBuilder(Component.translatable("camoverlay.options.advanced.rotate")).setDefaultValue(0).setMin(-180).setMax(180).setConfig(CamOverlay.config, "ROTATE").build())
                .addWidget(new ButtonBooleanBuilder(Component.translatable("camoverlay.options.advanced.enable.set_fov"), true).setConfig(CamOverlay.config, "ENABLE.SET_FOV").build())
                .addWidget(new ButtonBooleanBuilder(Component.translatable("camoverlay.options.advanced.world_time"), false).setConfig(CamOverlay.config, "WORLD_TIME").build())
                .addWidget(new ButtonBooleanBuilder(Component.translatable("camoverlay.options.advanced.enable.toast"), false).setConfig(CamOverlay.config, "ENABLE.TOAST").build());

        return builder.build();
    }
}
