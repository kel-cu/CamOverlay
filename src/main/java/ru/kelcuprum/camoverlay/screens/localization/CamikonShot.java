package ru.kelcuprum.camoverlay.screens.localization;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.config.Localization;
import ru.kelcuprum.alinlib.gui.InterfaceUtils;
import ru.kelcuprum.alinlib.gui.components.buttons.base.Button;
import ru.kelcuprum.alinlib.gui.components.editbox.EditBoxLocalization;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;
import ru.kelcuprum.camoverlay.CamOverlay;
import ru.kelcuprum.camoverlay.OverlayUtils;

public class CamikonShot {
    private InterfaceUtils.DesignType designType = InterfaceUtils.DesignType.ALINA;
    private Localization localization = CamOverlay.localization;
    public Screen build(Screen parent){
        ConfigScreenBuilder builder = new ConfigScreenBuilder(parent, Component.translatable("camoverlay.name"), designType)
                .addPanelWidget(new Button(10, 40, 100, 20, designType, OverlayUtils.Type.CAMIKONSHOT.name, (s) -> {

                }))
                .addPanelWidget(new Button(10, 65, 100, 20, designType, OverlayUtils.Type.KLASHRAICK.name, (s) -> {

                }))
                .addPanelWidget(new Button(10, 90, 100, 20, designType, OverlayUtils.Type.PHONE.name, (s) -> {

                }))
                .addPanelWidget(new Button(10, 115, 100, 20, designType, OverlayUtils.Type.DATE.name, (s) -> {

                }))

                .addWidget(new EditBoxLocalization(140, 30, designType, localization, "", Component.empty()));
        return builder.build();
    }
}
