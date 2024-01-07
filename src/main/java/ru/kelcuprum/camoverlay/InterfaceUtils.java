package ru.kelcuprum.camoverlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.camoverlay.localization.StarScript;

import static ru.kelcuprum.camoverlay.CamOverlay.MINECRAFT;

public class InterfaceUtils {
    public static Type getType(){
        return switch (CamOverlay.config.getNumber("TYPE", 0).intValue()){
            case 0 -> Type.CAMIKONSHOT;
            case 1 -> Type.KLASHRAICK;
            case 2 -> Type.PHONE;
            case 3 -> Type.DATE;
            default -> Type.NONE;
        };
    }
    public static Overlay getOverlay(){
        return switch (CamOverlay.config.getNumber("TYPE.OVERLAY", 0).intValue()){
            case 0 -> Overlay.GRID_3x3;
            case 1 -> Overlay.GRID_4x4;
            case 2 -> Overlay.GRID_CUSTOM;
            default -> Overlay.NONE;
        };
    }
    public enum Type{
        //CamikonShot
        CAMIKONSHOT(Component.translatable("camoverlay.camikonshot")),
        KLASHRAICK(Component.translatable("camoverlay.klashraick")),
        PHONE(Component.translatable("camoverlay.phone")),
        DATE(Component.translatable("camoverlay.date")),
        NONE(Component.translatable("camoverlay.none"));
        public final Component name;
        Type(Component name){
            this.name = name;
        }
        public void renderRound(GuiGraphics guiGraphics, int width, int height){
            switch (this){
                case CAMIKONSHOT, KLASHRAICK -> {
                    // LEFT TOP
                    guiGraphics.fill(20, 20, 80, 22, 0xFFFFFFFF);
                    guiGraphics.fill(20, 20, 22, 55, 0xFFFFFFFF);
                    // RIGHT TOP
                    guiGraphics.fill(width-80, 20, width-20, 22, 0xFFFFFFFF);
                    guiGraphics.fill(width-22, 20, width-20, 55, 0xFFFFFFFF);

                    // LEFT BOTTOM
                    guiGraphics.fill(20, height-20, 80, height-22, 0xFFFFFFFF);
                    guiGraphics.fill(20, height-20, 22, height-55, 0xFFFFFFFF);
                    // RIGHT BOTTOM
                    guiGraphics.fill(width-80, height-20, width-20, height-22, 0xFFFFFFFF);
                    guiGraphics.fill(width-22, height-20, width-20, height-55, 0xFFFFFFFF);
                }
                default -> {}
            }
        }
        public void renderCroshair(GuiGraphics guiGraphics, int width, int height){
            if(CamOverlay.config.getBoolean("ENABLE.OVERLAY", false)) return;
            switch (this){
                case CAMIKONSHOT, KLASHRAICK -> {
                    int x = width / 2;
                    int y = height / 2;

                    guiGraphics.fill(x - 4, y - 1, x + 5, y, 0xFFFFFFFF);
                    guiGraphics.fill(x, y - 5, x + 1, y + 4, 0xFFFFFFFF);
                }
                default -> {}
            }
        }
        public void renderStatus(GuiGraphics guiGraphics, int width, int height){
            switch (this){
                case CAMIKONSHOT -> {
                    guiGraphics.fill(width - 80, height - 30, width - 25, height - 25, 0x7F000000);
                    double stateHealth = (MINECRAFT.player.getHealth() / MINECRAFT.player.getMaxHealth());
                    guiGraphics.fill(width - 25, height - 30, (int) (width - 25 - (55 * stateHealth)), height - 25, 0xFFFFFFFF);

                    guiGraphics.fill(width - 80, height - 38, width - 25, height - 33, 0x7F000000);
                    double stateFood = ((double) MINECRAFT.player.getFoodData().getFoodLevel() / 20);
                    guiGraphics.fill(width - 25, height - 38, (int) (width - 25 - (55 * stateFood)), height - 33, 0xFFFFFFFF);
                }
                case KLASHRAICK -> {
                    double state = ((MINECRAFT.player.getFoodData().getFoodLevel() + MINECRAFT.player.getHealth()) / 2) / 20;
                    // round
                    guiGraphics.fill(25, 25, 80, 27, 0xFFFFFFFF);
                    guiGraphics.fill(25, 25, 27, 45, 0xFFFFFFFF);
                    guiGraphics.fill(25, 43, 80, 45, 0xFFFFFFFF);
                    guiGraphics.fill(78, 25, 80, 45, 0xFFFFFFFF);
                    guiGraphics.fill(80, 30, 82, 40, 0xFFFFFFFF);
                    // state
//                    guiGraphics.fill(80, 45, 25, 25, 0x7F000000);
                    guiGraphics.fill(29, 41, (int) (29 + (47 * state)), 29, 0xFFFFFFFF);
                }
                default -> {}
            }
        }

        public void renderText(GuiGraphics guiGraphics, Minecraft minecraft, int width, int height){
            switch (this){
                case CAMIKONSHOT -> {
                    Component rec = Component.translatable("camoverlay.camikonshot." + (CamOverlay.config.getBoolean("RECORD_MODE", true) ? "rec" : "photo"));
                    Component cam = Component.translatable("camoverlay.camikonshot.cam");
                    Component info = Component.literal(String.format("Frame: %sx%s  FPS: %s  FOV: %s", minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight(), minecraft.getFps(), minecraft.options.fov().get()));
                    Component rotate = Component.literal(String.format("%s°", CamOverlay.config.getNumber("ROTATE", 0F).floatValue()));
                    // CENTER BOTTOM
                    guiGraphics.drawCenteredString(minecraft.font, info, width / 2, height - 20 - minecraft.font.lineHeight, 0xFFFFFFFF);
                    // LEFT
                    guiGraphics.drawString(minecraft.font, rec, 30, 30, 0xFFFFFFFF); // TOP
                    guiGraphics.drawString(minecraft.font, cam, 30, height - 28 - minecraft.font.lineHeight, 0xFFFFFFFF); // BOTTOM
                    // RIGHT
                    guiGraphics.drawString(minecraft.font, rotate, width - 30 - minecraft.font.width(rotate), 30, 0xFFFFFFFF); // TOP
                }
                case KLASHRAICK -> {
                    Component rec = Component.translatable("camoverlay.klashraick." + (CamOverlay.config.getBoolean("RECORD_MODE", true) ? "rec" : "photo"));
                    Component heightS = Component.translatable("camoverlay.klashraick.height");
                    Component cam = Component.translatable("camoverlay.klashraick.cam");
                    Component rotate = Component.literal(String.format("%s°", CamOverlay.config.getNumber("ROTATE", 0F).floatValue()));
                    // CENTER BOTTOM
                    guiGraphics.drawCenteredString(minecraft.font, rec, width / 2, 22, 0xFFFFFFFF);
                    // LEFT
                    guiGraphics.drawString(minecraft.font, heightS, 30, height - 28 - minecraft.font.lineHeight, 0xFFFFFFFF); // BOTTOM
                    guiGraphics.drawString(minecraft.font, cam, width-30-minecraft.font.width(cam), height - 28 - minecraft.font.lineHeight, 0xFFFFFFFF); // BOTTOM
                    // RIGHT
                    guiGraphics.drawString(minecraft.font, rotate, width - 30 - minecraft.font.width(rotate), 30, 0xFFFFFFFF); // TOP
                }
                case PHONE -> {
                    Component date = Component.literal(StarScript.parseText("{overlay.time}"));
                    Component cam = Component.translatable("camoverlay.phone.cam");
                    guiGraphics.drawString(minecraft.font, date, width - 30 - minecraft.font.width(date), height-28-minecraft.font.lineHeight, 0xFFFFFFFF); // BOTTOM RIGHT
                    guiGraphics.drawString(minecraft.font, cam, 30, height-28-minecraft.font.lineHeight, 0xFFFFFFFF); // BOTTOM RIGHT
                }
                case DATE -> {
                    Component date = Component.literal(StarScript.parseText("{overlay.time}"));
                    guiGraphics.drawString(minecraft.font, date, width - 30 - minecraft.font.width(date), height-28-minecraft.font.lineHeight, 0xFFFFFFFF); // BOTTOM RIGHT
                }
                default -> {}
            }
        }
    }
    public enum Overlay{
        GRID_3x3(Component.translatable("camoverlay.overlay.grid_3x3")),
        GRID_4x4(Component.translatable("camoverlay.overlay.grid_4x4")),
        GRID_CUSTOM(Component.translatable("camoverlay.overlay.grid_custom")),
        NONE(Component.translatable("camoverlay.none"));
        public final Component name;
        Overlay(Component name){
            this.name = name;
        }
        public void renderOverlay(GuiGraphics guiGraphics, int width, int height){
            switch (this){
                case GRID_3x3 -> {
                    int x = width / 3;
                    int y = height / 3;
                    // x
                    guiGraphics.fill(x, 0, x+1, height, 0x7FFFFFFF);
                    guiGraphics.fill(x*2, 0, (x*2)+1, height, 0x7FFFFFFF);
                    // y
                    guiGraphics.fill(0, y, width, y+1, 0x7FFFFFFF);
                    guiGraphics.fill(0, y*2, width, (y*2)+1, 0x7FFFFFFF);
                }
                case GRID_4x4 -> {
                    int x = width / 4;
                    int y = height / 4;
                    // x
                    guiGraphics.fill(x, 0, x+1, height, 0x7FFFFFFF);
                    guiGraphics.fill(x*2, 0, (x*2)+1, height, 0x7FFFFFFF);
                    guiGraphics.fill(x*3, 0, (x*3)+1, height, 0x7FFFFFFF);
                    // y
                    guiGraphics.fill(0, y, width, y+1, 0x7FFFFFFF);
                    guiGraphics.fill(0, y*2, width, (y*2)+1, 0x7FFFFFFF);
                    guiGraphics.fill(0, y*3, width, (y*3)+1, 0x7FFFFFFF);
                }
                case GRID_CUSTOM -> {
                    int x = width / CamOverlay.config.getNumber("GRID_NUMBER", 2).intValue();
                    int y = height / CamOverlay.config.getNumber("GRID_NUMBER", 2).intValue();
                    int steps = CamOverlay.config.getNumber("GRID_NUMBER", 2).intValue() - 1;
                    // x
                    for(int i = 0;i<steps;i++){
                        guiGraphics.fill(x+(x*i), 0, x+(x*i)+1, height, 0x7FFFFFFF);
                    }
                    // y
                    for(int i = 0;i<steps;i++){
                        guiGraphics.fill(0, y+(y*i), width, y+(y*i)+1, 0x7FFFFFFF);
                    }
                }
                default -> {}
            }
        }
    }
}
