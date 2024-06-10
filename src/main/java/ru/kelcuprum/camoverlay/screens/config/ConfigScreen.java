package ru.kelcuprum.camoverlay.screens.config;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.gui.InterfaceUtils;
import ru.kelcuprum.alinlib.gui.components.builder.selector.SelectorBuilder;
import ru.kelcuprum.alinlib.gui.components.buttons.ButtonConfigBoolean;
import ru.kelcuprum.alinlib.gui.components.buttons.base.Button;
import ru.kelcuprum.alinlib.gui.components.selector.SelectorIntegerButton;
import ru.kelcuprum.alinlib.gui.components.selector.SelectorStringButton;
import ru.kelcuprum.alinlib.gui.components.sliders.SliderConfigInteger;
import ru.kelcuprum.alinlib.gui.components.text.CategoryBox;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;
import ru.kelcuprum.camoverlay.CamOverlay;
import ru.kelcuprum.camoverlay.OverlayUtils;

import static ru.kelcuprum.camoverlay.CamOverlay.MINECRAFT;

public class ConfigScreen {
    public static final InterfaceUtils.DesignType dType = InterfaceUtils.DesignType.FLAT;
    private static Boolean lastEnable = CamOverlay.config.getBoolean("ENABLE", false);

    public static Screen build(Screen parent) {
        ConfigScreenBuilder builder = new ConfigScreenBuilder(parent, Component.translatable("camoverlay.name"), dType)
                .setOnTick((d) -> {
                    if (lastEnable != CamOverlay.config.getBoolean("ENABLE", false)) {
                        lastEnable = CamOverlay.config.getBoolean("ENABLE", false);
                    }
                })
                .addPanelWidget(new Button(10, 40, 100, 20, dType, Component.translatable("camoverlay.options"), (s) -> MINECRAFT.setScreen(ConfigScreen.build(parent))))
                .addPanelWidget(new Button(10, 65, 100, 20, dType, Component.translatable("camoverlay.options.advanced"), (s) -> MINECRAFT.setScreen(AdvancedConfigScreen.build(parent))));
        builder.addWidget(new TextBox(140, 5, Component.translatable("camoverlay.options"), true));

        builder.addWidget(new CategoryBox(Component.translatable("camoverlay.category.overlays"))
                .addValue(new ButtonConfigBoolean(140, 30, dType, CamOverlay.config, "ENABLE", false, Component.translatable("camoverlay.options.enable")))
                .addValue(new SelectorBuilder(Component.translatable("camoverlay.options.type"), selectorButton -> CamOverlay.config.setString("TYPE", OverlayUtils.getOverlayByName(selectorButton.getList()[selectorButton.getPosition()]).id))
                        .setList(OverlayUtils.getOverlayNames())
                        .setValue(OverlayUtils.getPositionOnOverlayNames(OverlayUtils.getSelectedOverlay().name.getString()))
                        .build())
                .addValue(new SliderConfigInteger(140, 80, dType, CamOverlay.config, "FOV", 30, 1, 110, Component.translatable("camoverlay.options.advanced.fov")))
                .addValue(new ButtonConfigBoolean(140, 80, dType, CamOverlay.config, "RECORD_MODE", false, Component.translatable("camoverlay.options.record_mode"))));

        builder.addWidget(new CategoryBox(Component.translatable("camoverlay.category.helpers"))
                .addValue(new ButtonConfigBoolean(140, 55, dType, CamOverlay.config, "ENABLE.HELPER", false, Component.translatable("camoverlay.options.enable.helper")))
                .addValue(new SelectorBuilder(Component.translatable("camoverlay.options.helper"), selectorButton -> CamOverlay.config.setString("HELPER", OverlayUtils.getHelperByName(selectorButton.getList()[selectorButton.getPosition()]).id))
                        .setList(OverlayUtils.getHelperNames())
                        .setValue(OverlayUtils.getPositionOnHelperNames(OverlayUtils.getSelectedHelper().name.getString()))
                        .build())
                .addValue(new SliderConfigInteger(140, 155, dType, CamOverlay.config, "GRID_NUMBER", 2, 2, 20, Component.translatable("camoverlay.options.grid_number")))
                .addValue(new SelectorStringButton(140, 180, dType, new String[]{"0", "90", "180", "270"}, CamOverlay.config, "GOLDEN_RATIO.ROTATE", "0", Component.translatable("camoverlay.options.golden_ratio.rotate")))
        );
        return builder.build();
    }
}
