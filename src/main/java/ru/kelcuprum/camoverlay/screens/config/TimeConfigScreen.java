package ru.kelcuprum.camoverlay.screens.config;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBooleanBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.slider.SliderBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.text.TextBuilder;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;
import ru.kelcuprum.camoverlay.CamOverlay;

import static ru.kelcuprum.camoverlay.CamOverlay.MINECRAFT;

public class TimeConfigScreen {
    public static Screen build(Screen parent) {
        ConfigScreenBuilder builder = new ConfigScreenBuilder(parent, Component.translatable("camoverlay.name"))
                .addPanelWidget(new ButtonBuilder(Component.translatable("camoverlay.options"), (s) -> MINECRAFT.setScreen(ConfigScreen.build(parent))).build())
                .addPanelWidget(new ButtonBuilder(Component.translatable("camoverlay.options.time"), (s) -> MINECRAFT.setScreen(TimeConfigScreen.build(parent))).build())
                .addPanelWidget(new ButtonBuilder(Component.translatable("camoverlay.options.advanced"), (s) -> MINECRAFT.setScreen(AdvancedConfigScreen.build(parent))).build())
                .addPanelWidget(new ButtonBuilder(Component.translatable("camoverlay.options.overlays"), (s) -> MINECRAFT.setScreen(OverlaysScreen.build(parent))).build());

        builder.addWidget(new TextBuilder(Component.translatable("camoverlay.options.time")))
                .addWidget(new ButtonBooleanBuilder(Component.translatable("camoverlay.options.time.enable"), false).setConfig(CamOverlay.config, "ENABLE_TIME").build())
                .addWidget(new SliderBuilder(Component.translatable("camoverlay.options.time.time")).setDefaultValue(18000).setMin(0).setMax(24000).setConfig(CamOverlay.config, "TIME").build())
                .addWidget(new ButtonBooleanBuilder(Component.translatable("camoverlay.options.weather.enable"), false).setConfig(CamOverlay.config, "ENABLE_WEATHER").build())
                .addWidget(new ButtonBooleanBuilder(Component.translatable("camoverlay.options.weather.enable.rain"), false).setConfig(CamOverlay.config, "ENABLE_RAIN").build())
                .addWidget(new ButtonBooleanBuilder(Component.translatable("camoverlay.options.weather.enable.thundering"), false).setConfig(CamOverlay.config, "ENABLE_THUNDERING").build());

        return builder.build();
    }
}
