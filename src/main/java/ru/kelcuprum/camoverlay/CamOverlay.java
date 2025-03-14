package ru.kelcuprum.camoverlay;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;
import org.meteordev.starscript.value.Value;
import org.meteordev.starscript.value.ValueMap;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.alinlib.api.events.alinlib.LocalizationEvents;
import ru.kelcuprum.alinlib.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import ru.kelcuprum.alinlib.config.Localization;
import ru.kelcuprum.alinlib.gui.GuiUtils;
import ru.kelcuprum.alinlib.gui.toast.ToastBuilder;
import ru.kelcuprum.camoverlay.overlays.*;
import ru.kelcuprum.camoverlay.overlays.helpers.*;
import ru.kelcuprum.camoverlay.screens.config.ConfigScreen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CamOverlay implements ClientModInitializer {
    public static Config config = new Config("config/CamOverlay/config.json");
    public static Minecraft MINECRAFT = Minecraft.getInstance();
    public static final Logger LOG = LogManager.getLogger("CamOverlay");

    public static void log(String message) {
        log(message, Level.INFO);
    }

    public static void log(String message, Level level) {
        LOG.log(level, "[" + LOG.getName() + "] " + message);
    }

    public static ResourceLocation TOAST_ICON = ResourceLocation.fromNamespaceAndPath("camoverlay", "textures/gui/widget/toast/icon.png");
    public static Localization localization = new Localization("camoverlay", "config/CamOverlay/lang");

    @Override
    public void onInitializeClient() {
        registerBinds();
        registerOverlays();
        registerHelpers();
        FabricLoader.getInstance().getModContainer("camoverlay").ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(GuiUtils.getResourceLocation("camoverlay","clover"), container, Component.translatable("resourcePack.camoverlay.clover"), ResourcePackActivationType.NORMAL);
        });
        LocalizationEvents.DEFAULT_PARSER_INIT.register((parser) -> {
            parser.ss.set("camoverlay", new ValueMap()
                    .set("overlay", new ValueMap()
                            .set("time", () -> Value.string(getTime(false)))
                            .set("world_time", () -> Value.string(getTime(true)))
                    )
                    .set("window", new ValueMap()
                            .set("width", () -> Value.number(AlinLib.MINECRAFT.getWindow().getWidth()))
                            .set("height", () -> Value.number(AlinLib.MINECRAFT.getWindow().getHeight()))
                            .set("scaled_width", () -> Value.number(AlinLib.MINECRAFT.getWindow().getGuiScaledWidth()))
                            .set("scaled_height", () -> Value.number(AlinLib.MINECRAFT.getWindow().getGuiScaledHeight()))
                    )
            );
        });
    }

    public static String getTime(boolean isWorld) {
        Component dFormat = Component.translatable("camoverlay.date.format");
        String clock;
        String strDateFormat = "MM/dd/yyyy hh:mm:ss";
        if (!dFormat.getString().equalsIgnoreCase("camoverlay.date.format")) strDateFormat = dFormat.getString();
        try {
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            if (isWorld && MINECRAFT.level != null) {
                long daytime = MINECRAFT.level.getDayTime() + 6000;
                int hours = (int) (daytime / 1000) % 24;
                int minutes = (int) ((daytime % 1000) * 60 / 1000);
                int day = (int) daytime / 1000 / 24;
                Calendar calendar = new GregorianCalendar();
                calendar.set(2000, Calendar.JANUARY, day + 1, hours, minutes, 0);
                clock = dateFormat.format(calendar.getTimeInMillis());
            } else clock = dateFormat.format(System.currentTimeMillis());
        } catch (IllegalArgumentException ex) {
            clock = strDateFormat;
        }
        return clock;
    }

    public void registerOverlays() {
        OverlayUtils.registerOverlay(new CamikonShotOverlay());
        OverlayUtils.registerOverlay(new KlashRaickOverlay());
        OverlayUtils.registerOverlay(new CarCamOverlay());
        OverlayUtils.registerOverlay(new CinematicOverlay());
        OverlayUtils.registerOverlay(new PhoneOverlay());
        OverlayUtils.registerOverlay(new DateOverlay());
        OverlayUtils.registerOverlay(new NoneOverlay());
    }

    public void registerHelpers() {
        OverlayUtils.registerHelper(new Grid3x3Overlay());
        OverlayUtils.registerHelper(new Grid4x4Overlay());
        OverlayUtils.registerHelper(new GridCustomOverlay());
        OverlayUtils.registerHelper(new GolderRationHelper());
        OverlayUtils.registerHelper(new VerticalGolderRationHelper());
        OverlayUtils.registerHelper(new NoneHelper());
    }

    public static Float getFov(float fov) {
        return config.getBoolean("ENABLE.SET_FOV", true) && config.getBoolean("ENABLE", false) ? config.getNumber("FOV", 30).floatValue() : fov;
    }

    public static void changeFov(double mouseScroll, boolean isMouse) {
        if (isMouse && !config.getBoolean("ENABLE.SCROLL_FOV", true)) return;
        if (!config.getBoolean("ENABLE.SET_FOV", true) && !config.getBoolean("ENABLE", true)) return;
        double fov = config.getNumber("FOV", 30).doubleValue();
        if (mouseScroll >= 0) fov += isMouse ? -1 : 1;
        else if (mouseScroll <= 0) fov -= isMouse ? -1 : 1;
        config.setNumber("FOV", Mth.clamp(fov, 1, 110));
    }

    public void registerBinds() {
        // enable
        KeyMapping enableModBind;
        enableModBind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "camoverlay.binds.enable",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_CONTROL, // The keycode of the key
                "camoverlay.name"
        ));
        KeyMapping enableOverlayBind;
        enableOverlayBind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "camoverlay.binds.enable.helper",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                "camoverlay.name"
        ));
        // Setting
        KeyMapping upFOVBind;
        upFOVBind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "camoverlay.binds.fov.up",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UP, // The keycode of the key
                "camoverlay.name"
        ));
        KeyMapping downFOVBind;
        downFOVBind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "camoverlay.binds.fov.down",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_DOWN, // The keycode of the key
                "camoverlay.name"
        ));
        KeyMapping rightRotateBind;
        rightRotateBind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "camoverlay.binds.rotate.right",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT, // The keycode of the key
                "camoverlay.name"
        ));
        KeyMapping leftRotateBind;
        leftRotateBind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "camoverlay.binds.rotate.left",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT, // The keycode of the key
                "camoverlay.name"
        ));
        KeyMapping resetRotateBind;
        resetRotateBind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "camoverlay.binds.rotate.reset",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT, // The keycode of the key
                "camoverlay.name"
        ));
        // Menu
        KeyMapping menuBind;
        menuBind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "camoverlay.binds.open",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_DELETE, // The keycode of the key
                "camoverlay.name"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            assert client.player != null;
            while (enableModBind.consumeClick()) {
                boolean state = !config.getBoolean("ENABLE", false);
                config.setBoolean("ENABLE", state);
                if (CamOverlay.config.getBoolean("ENABLE.TOAST", false)) new ToastBuilder()
                        .setIcon(TOAST_ICON)
                        .setTitle(Component.translatable("camoverlay.name"))
                        .setMessage(Component.translatable("camoverlay.toast." + (state ? "enable" : "disable")))
                        .setType(state ? ToastBuilder.Type.INFO : ToastBuilder.Type.ERROR)
                        .buildAndShow();
            }
            while (enableOverlayBind.consumeClick()) {
                if (config.getBoolean("ENABLE", false)) {
                    boolean state = !config.getBoolean("ENABLE.HELPER", false);
                    config.setBoolean("ENABLE.HELPER", state);
                }
            }
            while (upFOVBind.consumeClick()) {
                changeFov(1.0, false);
            }
            while (downFOVBind.consumeClick()) {
                changeFov(-1.0, false);
            }
            //
            while (rightRotateBind.consumeClick()) {
                if (config.getBoolean("ENABLE", false)) {
                    float rotate = config.getNumber("ROTATE", 0F).floatValue() + 1.0F;
                    if (rotate > 180.0f) rotate = -180.0f;
                    config.setNumber("ROTATE", rotate);
                }
            }
            while (leftRotateBind.consumeClick()) {
                if (config.getBoolean("ENABLE", false)) {
                    float rotate = config.getNumber("ROTATE", 0F).floatValue() - 1.0F;
                    if (rotate < -180.0f) rotate = 180.0f;
                    config.setNumber("ROTATE", rotate);
                }
            }
            while (resetRotateBind.consumeClick()) {
                if (config.getBoolean("ENABLE", false)) {
                    config.setNumber("ROTATE", 0.0F);
                }
            }
            while (menuBind.consumeClick()) {
                MINECRAFT.setScreen(ConfigScreen.build(MINECRAFT.screen));
            }
        });
    }
}
