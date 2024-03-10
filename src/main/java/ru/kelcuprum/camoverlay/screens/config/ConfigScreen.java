package ru.kelcuprum.camoverlay.screens.config;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.gui.InterfaceUtils;
import ru.kelcuprum.alinlib.gui.components.buttons.ButtonConfigBoolean;
import ru.kelcuprum.alinlib.gui.components.buttons.base.Button;
import ru.kelcuprum.alinlib.gui.components.selector.SelectorIntegerButton;
import ru.kelcuprum.alinlib.gui.components.selector.SelectorStringButton;
import ru.kelcuprum.alinlib.gui.components.sliders.SliderConfigInteger;
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

        builder.addWidget(new ButtonConfigBoolean(140, 30, dType, CamOverlay.config, "ENABLE", false, Component.translatable("camoverlay.options.enable")));
        builder.addWidget(new ButtonConfigBoolean(140, 55, dType, CamOverlay.config, "ENABLE.OVERLAY", false, Component.translatable("camoverlay.options.enable.overlay")));
        builder.addWidget(new SliderConfigInteger(140, 80, dType, CamOverlay.config, "FOV", 0, 1, 110, Component.translatable("camoverlay.options.advanced.fov")));
        builder.addWidget(new ButtonConfigBoolean(140, 80, dType, CamOverlay.config, "RECORD_MODE", false, Component.translatable("camoverlay.options.record_mode")));
        String[] type = {
                OverlayUtils.Type.CAMIKONSHOT.name.getString(),
                OverlayUtils.Type.KLASHRAICK.name.getString(),
                OverlayUtils.Type.PHONE.name.getString(),
                OverlayUtils.Type.DATE.name.getString(),
                OverlayUtils.Type.NONE.name.getString(),
        };
        builder.addWidget(new SelectorIntegerButton(140, 105, dType, type, CamOverlay.config, "TYPE", 0, Component.translatable("camoverlay.options.type")));
        String[] overlay = {
                OverlayUtils.Overlay.GRID_3x3.name.getString(),
                OverlayUtils.Overlay.GRID_4x4.name.getString(),
                OverlayUtils.Overlay.GRID_CUSTOM.name.getString(),
                OverlayUtils.Overlay.GOLDEN_RATIO.name.getString(),
                OverlayUtils.Overlay.VERTICAL_GOLDEN_RATIO.name.getString(),
                OverlayUtils.Overlay.NONE.name.getString(),
        };
        String[] golden_ratio_rotate = {
                "0",
                "90",
                "180",
                "270"
        };
        builder.addWidget(new SelectorIntegerButton(140, 130, dType, overlay, CamOverlay.config, "TYPE.OVERLAY", 0, Component.translatable("camoverlay.options.type.overlay")));
        builder.addWidget(new SliderConfigInteger(140, 155, dType, CamOverlay.config, "GRID_NUMBER", 2, 2, 20, Component.translatable("camoverlay.options.grid_number")));
        builder.addWidget(new SelectorStringButton(140, 180, dType, golden_ratio_rotate, CamOverlay.config, "GOLDEN_RATIO.ROTATE", "0", Component.translatable("camoverlay.options.golden_ratio.rotate")));

        return builder.build();
    }
}
