package ru.kelcuprum.camoverlay.screens.config;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.editbox.EditBoxBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.slider.SliderBuilder;
import ru.kelcuprum.alinlib.gui.components.text.CategoryBox;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;
import ru.kelcuprum.camoverlay.CamOverlay;

import static ru.kelcuprum.camoverlay.CamOverlay.MINECRAFT;

public class OverlaysScreen {
    public static Screen build(Screen parent) {
        ConfigScreenBuilder builder = new ConfigScreenBuilder(parent, Component.translatable("camoverlay.name"))
                .addPanelWidget(new ButtonBuilder(Component.translatable("camoverlay.options"), (s) -> MINECRAFT.setScreen(ConfigScreen.build(parent))).build())
                .addPanelWidget(new ButtonBuilder(Component.translatable("camoverlay.options.advanced"), (s) -> MINECRAFT.setScreen(AdvancedConfigScreen.build(parent))).build())
                .addPanelWidget(new ButtonBuilder(Component.translatable("camoverlay.options.overlays"), (s) -> MINECRAFT.setScreen(OverlaysScreen.build(parent))).build());

        builder.addWidget(new TextBox(Component.translatable("camoverlay.options.overlays"), true));

//        builder.addWidget(new CategoryBox(Component.translatable("camoverlay.camikonshot")));
//        builder.addWidget(new CategoryBox(Component.translatable("camoverlay.klashraick")));
        builder.addWidget(new CategoryBox(Component.translatable("camoverlay.cinematic"))
                .addValue(new SliderBuilder(Component.translatable("camoverlay.cinematic.size")).setDefaultValue(30).setMin(30).setMax(300).setConfig(CamOverlay.config, "CINEMATIC.SIZE").build())
                .addValue(new EditBoxBuilder(Component.translatable("camoverlay.cinematic.top_text")).setValue(CamOverlay.config.getString("CINEMATIC.TOP_TEXT", "{world.name}")).setConfig(CamOverlay.config, "CINEMATIC.TOP_TEXT").build())
                .addValue(new EditBoxBuilder(Component.translatable("camoverlay.cinematic.bottom_text")).setValue(CamOverlay.config.getString("CINEMATIC.BOTTOM_TEXT", "FPS: {minecraft.fps} Time: {time} Frame: {camoverlay.window.width}x{camoverlay.window.height}")).setConfig(CamOverlay.config, "CINEMATIC.BOTTOM_TEXT").build())
        );
        return builder.build();
    }
}
