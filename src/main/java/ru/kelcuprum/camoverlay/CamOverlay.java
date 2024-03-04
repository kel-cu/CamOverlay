package ru.kelcuprum.camoverlay;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;
import ru.kelcuprum.alinlib.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import ru.kelcuprum.alinlib.config.Localization;
import ru.kelcuprum.alinlib.gui.toast.ToastBuilder;
import ru.kelcuprum.camoverlay.localization.StarScript;
import ru.kelcuprum.camoverlay.screens.config.ConfigScreen;

public class CamOverlay implements ClientModInitializer {
    public static Config config = new Config("config/CamOverlay/config.json");
    public static Minecraft MINECRAFT = Minecraft.getInstance();
    public static Localization localization = new Localization("camoverlay", "config/CamOverlay/lang");
    public static final Logger LOG = LogManager.getLogger("CamOverlay");
    public static void log(String message) { log(message, Level.INFO);}
    public static void log(String message, Level level) { LOG.log(level, "[" + LOG.getName() + "] " + message); }
    public static ResourceLocation TOAST_ICON = new ResourceLocation("camoverlay", "textures/gui/widget/toast/icon.png");
    public static int lastFOV = 0;
    @Override
    public void onInitializeClient() {
        log("Hi!");
        config.load();
        StarScript.init();
        lastFOV = config.getNumber("FOV.LAST", 70).intValue();
        registerBinds();
    }
    public void registerBinds(){
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
                "camoverlay.binds.enable.overlay",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                "camoverlay.name"
        ));
        KeyMapping recordBind;
        recordBind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "camoverlay.binds.record",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_ALT, // The keycode of the key
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
                new ToastBuilder()
                        .setIcon(TOAST_ICON)
                        .setTitle(Component.translatable("camoverlay.name"))
                        .setMessage(Component.translatable("camoverlay.toast."+(state ? "enable" : "disable")))
                        .setType(state ? ToastBuilder.Type.INFO : ToastBuilder.Type.ERROR)
                        .show(MINECRAFT.getToasts());
                if(state){
                    // Замена FOV
                    lastFOV = MINECRAFT.options.fov().get();
                    config.setNumber("FOV.LAST", lastFOV);
                    if(config.getBoolean("ENABLE.SET_FOV", true)) MINECRAFT.options.fov().set(config.getNumber("FOV", 30).intValue());
                } else {
                    // Замена FOV
                    MINECRAFT.options.fov().set(lastFOV);
                }
            }
            while (enableOverlayBind.consumeClick()) {
                if(config.getBoolean("ENABLE", false)){
                    boolean state = !config.getBoolean("ENABLE.OVERLAY", false);
                    config.setBoolean("ENABLE.OVERLAY", state);
                }
            }
            while (recordBind.consumeClick()) {
                if(config.getBoolean("ENABLE", false)){
                    boolean state = !config.getBoolean("RECORD_MODE", true);
                    config.setBoolean("RECORD_MODE", state);
                }
            }
            while (upFOVBind.consumeClick()){
                if(config.getBoolean("ENABLE", false)){
                    int fov = config.getNumber("FOV", 30).intValue() + 1;
                    if(fov > 110) fov = 110;
                    config.setNumber("FOV", fov);
                    MINECRAFT.options.fov().set(fov);
                }
            }
            while (downFOVBind.consumeClick()){
                if(config.getBoolean("ENABLE", false)){
                    int fov = config.getNumber("FOV", 30).intValue() - 1;
                    if(fov < 30) fov = 30;
                    config.setNumber("FOV", fov);
                    MINECRAFT.options.fov().set(fov);
                }
            }
            //
            while (rightRotateBind.consumeClick()){
                if(config.getBoolean("ENABLE", false)){
                    float rotate = config.getNumber("ROTATE", 0F).floatValue() + 1.0F;
                    if(rotate > 180.0f) rotate = -180.0f;
                    config.setNumber("ROTATE", rotate);
                }
            }
            while (leftRotateBind.consumeClick()){
                if(config.getBoolean("ENABLE", false)){
                    float rotate = config.getNumber("ROTATE", 0F).floatValue() - 1.0F;
                    if(rotate < -180.0f) rotate = 180.0f;
                    config.setNumber("ROTATE", rotate);
                }
            }
            while (resetRotateBind.consumeClick()){
                if(config.getBoolean("ENABLE", false)){
                    config.setNumber("ROTATE", 0.0F);
                }
            }
            while (menuBind.consumeClick()) {
                MINECRAFT.setScreen(ConfigScreen.build(MINECRAFT.screen));
            }
        });
    }
}
