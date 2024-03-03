package ru.kelcuprum.camoverlay.screens.config;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.alinlib.gui.InterfaceUtils;
import ru.kelcuprum.alinlib.gui.components.buttons.ButtonConfigBoolean;
import ru.kelcuprum.alinlib.gui.components.buttons.ButtonSprite;
import ru.kelcuprum.alinlib.gui.components.buttons.base.Button;
import ru.kelcuprum.alinlib.gui.components.selector.SelectorIntegerButton;
import ru.kelcuprum.alinlib.gui.components.selector.SelectorStringButton;
import ru.kelcuprum.alinlib.gui.components.sliders.SliderConfigInteger;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;
import ru.kelcuprum.camoverlay.CamOverlay;
import ru.kelcuprum.camoverlay.OverlayUtils;

import static ru.kelcuprum.camoverlay.CamOverlay.MINECRAFT;

public class ConfigScreen extends Screen {
    private final Screen parent;
    public ConfigScreen(Screen parent) {
        super(Component.translatable("camoverlay.options"));
        this.parent = parent;
    }
    private Boolean lastEnable = CamOverlay.config.getBoolean("ENABLE", false);

    @Override
    public void tick() {
        if(lastEnable != CamOverlay.config.getBoolean("ENABLE", false)){
            lastEnable = CamOverlay.config.getBoolean("ENABLE", false);
            if(lastEnable){
                // Замена FOV
                CamOverlay.lastFOV = MINECRAFT.options.fov().get();
                CamOverlay.config.setNumber("FOV.LAST", CamOverlay.lastFOV);
                if(CamOverlay.config.getBoolean("ENABLE.SET_FOV", true)) MINECRAFT.options.fov().set(CamOverlay.config.getNumber("FOV", 30).intValue());
            } else {
                // Замена FOV
                MINECRAFT.options.fov().set(CamOverlay.lastFOV);
            }
        }
        super.tick();
    }

    @Override
    protected void init() {
        int x = width/2;
        int size = 180;
        addRenderableWidget(new TextBox(x-100, 0, 200, 35, title, true));

        addRenderableWidget(new ButtonConfigBoolean(x-90, 40, size, 20, CamOverlay.config, "ENABLE", false, Component.translatable("camoverlay.options.enable")));
        addRenderableWidget(new ButtonConfigBoolean(x-90, 65, size, 20, CamOverlay.config, "ENABLE.OVERLAY", false, Component.translatable("camoverlay.options.enable.overlay")));
        addRenderableWidget(new ButtonConfigBoolean(x-90, 90, size, 20, CamOverlay.config, "RECORD_MODE", false, Component.translatable("camoverlay.options.record_mode")));
        String[] type = {
                OverlayUtils.Type.CAMIKONSHOT.name.getString(),
                OverlayUtils.Type.KLASHRAICK.name.getString(),
                OverlayUtils.Type.PHONE.name.getString(),
                OverlayUtils.Type.DATE.name.getString(),
                OverlayUtils.Type.NONE.name.getString(),
        };
        addRenderableWidget(new SelectorIntegerButton(x-90, 115, size, 20, type, CamOverlay.config, "TYPE", 0, Component.translatable("camoverlay.options.type")));
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
        addRenderableWidget(new SelectorIntegerButton(x-90, 140, size, 20, overlay, CamOverlay.config, "TYPE.OVERLAY", 0, Component.translatable("camoverlay.options.type.overlay")));
        addRenderableWidget(new SliderConfigInteger(x-90, 165, size, 20, CamOverlay.config, "GRID_NUMBER", 2, 2, 20, Component.translatable("camoverlay.options.grid_number")));
        addRenderableWidget(new SelectorStringButton(x-90, 190, size, 20, golden_ratio_rotate, CamOverlay.config, "GOLDEN_RATIO.ROTATE", "0", Component.translatable("camoverlay.options.type.overlay")));


        addRenderableWidget(new Button(x-90, height-30, size-25, 20, CommonComponents.GUI_BACK, (s) -> {
            assert this.minecraft != null;
            this.minecraft.setScreen(this.parent);
        }));

        addRenderableWidget(new ButtonSprite(x+70, height-30, 20, 20, InterfaceUtils.Icons.OPTIONS, Component.translatable("camoverlay.options.advanced"), (s) ->{
            assert this.minecraft != null;
            this.minecraft.setScreen(new AdvancedConfigScreen(this));
        }));
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = width/2;
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.fill(x-100, 0, x+100, height, 0x7F000000);
    }
}
