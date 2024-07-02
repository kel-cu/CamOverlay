package ru.kelcuprum.camoverlay.screens.config;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBooleanBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.selector.SelectorBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.slider.SliderBuilder;
import ru.kelcuprum.alinlib.gui.components.text.CategoryBox;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;
import ru.kelcuprum.camoverlay.CamOverlay;
import ru.kelcuprum.camoverlay.OverlayUtils;

import static ru.kelcuprum.camoverlay.CamOverlay.MINECRAFT;

public class ConfigScreen {

    public static Screen build(Screen parent) {
        ConfigScreenBuilder builder = new ConfigScreenBuilder(parent, Component.translatable("camoverlay.name"))
                .addPanelWidget(new ButtonBuilder(Component.translatable("camoverlay.options"), (s) -> MINECRAFT.setScreen(ConfigScreen.build(parent))).build())
                .addPanelWidget(new ButtonBuilder(Component.translatable("camoverlay.options.advanced"), (s) -> MINECRAFT.setScreen(AdvancedConfigScreen.build(parent))).build())
                .addPanelWidget(new ButtonBuilder(Component.translatable("camoverlay.options.overlays"), (s) -> MINECRAFT.setScreen(OverlaysScreen.build(parent))).build());
        builder.addWidget(new TextBox(140, 5, Component.translatable("camoverlay.options"), true));

        builder.addWidget(new CategoryBox(Component.translatable("camoverlay.category.overlays"))
                .addValue(new ButtonBooleanBuilder(Component.translatable("camoverlay.options.enable"), false).setConfig(CamOverlay.config, "ENABLE").build())
                .addValue(new SelectorBuilder(Component.translatable("camoverlay.options.type"), selectorButton -> CamOverlay.config.setString("TYPE", OverlayUtils.getOverlayByName(selectorButton.getList()[selectorButton.getPosition()]).id))
                        .setList(OverlayUtils.getOverlayNames())
                        .setValue(OverlayUtils.getPositionOnOverlayNames(OverlayUtils.getSelectedOverlay().name.getString()))
                        .build())
                .addValue(new ButtonBooleanBuilder(Component.translatable("camoverlay.options.enable.cursor"), true).setConfig(CamOverlay.config, "ENABLE.CURSOR").build())
                .addValue(new SliderBuilder(Component.translatable("camoverlay.options.advanced.fov")).setDefaultValue(30).setMin(1).setMax(110).setConfig(CamOverlay.config, "FOV").build())
                .addValue(new ButtonBooleanBuilder(Component.translatable("camoverlay.options.record_mode"), false).setConfig(CamOverlay.config, "RECORD_MODE").build())
        );

        builder.addWidget(new CategoryBox(Component.translatable("camoverlay.category.helpers"))
                .addValue(new ButtonBooleanBuilder(Component.translatable("camoverlay.options.enable.helper"), false).setConfig(CamOverlay.config, "ENABLE.HELPER").build())
                .addValue(new SelectorBuilder(Component.translatable("camoverlay.options.helper"), selectorButton -> CamOverlay.config.setString("HELPER", OverlayUtils.getHelperByName(selectorButton.getList()[selectorButton.getPosition()]).id))
                        .setList(OverlayUtils.getHelperNames())
                        .setValue(OverlayUtils.getPositionOnHelperNames(OverlayUtils.getSelectedHelper().name.getString()))
                        .build())
                .addValue(new SliderBuilder(Component.translatable("camoverlay.options.grid_number")).setDefaultValue(2).setMin(2).setMax(20).setConfig(CamOverlay.config, "GRID_NUMBER").build())
                .addValue(new SelectorBuilder(Component.translatable("camoverlay.options.golden_ratio.rotate")).setList(new String[]{"0", "90", "180", "270"}).setValue("0").setConfig(CamOverlay.config, "GOLDEN_RATIO.ROTATE").build())
        );

        return builder.build();
    }
}
