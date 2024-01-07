package ru.kelcuprum.camoverlay.screens;

import net.minecraft.client.GuiMessage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import ru.kelcuprum.alinlib.gui.components.buttons.Button;
import ru.kelcuprum.alinlib.gui.components.buttons.ButtonBoolean;
import ru.kelcuprum.alinlib.gui.components.buttons.ButtonSprite;
import ru.kelcuprum.alinlib.gui.components.sliders.SliderInteger;
import ru.kelcuprum.alinlib.gui.components.text.TextBox;
import ru.kelcuprum.alinlib.gui.components.selector.SelectorStringWithIntButton;
import ru.kelcuprum.camoverlay.CamOverlay;
import ru.kelcuprum.camoverlay.InterfaceUtils;

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

        addRenderableWidget(new ButtonBoolean(x-90, 40, size, 20, CamOverlay.config, "ENABLE", false, Component.translatable("camoverlay.options.enable")));
        addRenderableWidget(new ButtonBoolean(x-90, 65, size, 20, CamOverlay.config, "ENABLE.OVERLAY", false, Component.translatable("camoverlay.options.enable.overlay")));
        addRenderableWidget(new ButtonBoolean(x-90, 90, size, 20, CamOverlay.config, "RECORD_MODE", false, Component.translatable("camoverlay.options.record_mode")));
        String[] type = {
                InterfaceUtils.Type.CAMIKONSHOT.name.getString(),
                InterfaceUtils.Type.KLASHRAICK.name.getString(),
                InterfaceUtils.Type.PHONE.name.getString(),
                InterfaceUtils.Type.DATE.name.getString(),
                InterfaceUtils.Type.NONE.name.getString(),
        };
        addRenderableWidget(new SelectorStringWithIntButton(x-90, 115, size, 20, type, CamOverlay.config, "TYPE", 0, Component.translatable("camoverlay.options.type")));
        String[] overlay = {
                InterfaceUtils.Overlay.GRID_3x3.name.getString(),
                InterfaceUtils.Overlay.GRID_4x4.name.getString(),
                InterfaceUtils.Overlay.GRID_CUSTOM.name.getString(),
                InterfaceUtils.Overlay.NONE.name.getString(),
        };
        addRenderableWidget(new SelectorStringWithIntButton(x-90, 140, size, 20, overlay, CamOverlay.config, "TYPE.OVERLAY", 0, Component.translatable("camoverlay.options.type.overlay")));
        addRenderableWidget(new SliderInteger(x-90, 165, size, 20, CamOverlay.config, "GRID_NUMBER", 2, 2, 20, Component.translatable("camoverlay.options.grid_number")));


        addRenderableWidget(new Button(x-90, height-30, size-25, 20, CommonComponents.GUI_BACK, (s) -> {
            assert this.minecraft != null;
            this.minecraft.setScreen(this.parent);
        }));

        addRenderableWidget(new ButtonSprite(x+70, height-30, 20, 20, new ResourceLocation("camoverlay", "textures/gui/widget/buttons/advanced.png"), Component.translatable("camoverlay.options.advanced"), (s) ->{
            assert this.minecraft != null;
            this.minecraft.setScreen(new AdvancedConfigScreen(this));
        }));
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = width/2;
        guiGraphics.fill(x-100, 0, x+100, height, 0x7F000000);
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
    }
}
