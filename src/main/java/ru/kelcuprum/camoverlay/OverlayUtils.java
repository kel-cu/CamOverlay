package ru.kelcuprum.camoverlay;


import ru.kelcuprum.camoverlay.overlays.AbstractOverlay;
import ru.kelcuprum.camoverlay.overlays.SafeOverlay;
import ru.kelcuprum.camoverlay.overlays.helpers.AbstractHelper;
import ru.kelcuprum.camoverlay.overlays.helpers.SafeHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OverlayUtils {
    public static AbstractOverlay safeModeOverlay = new SafeOverlay();
    public static AbstractHelper safeModeHelper = new SafeHelper();
    // Overlays
    public static Map<String, AbstractOverlay> overlays = new HashMap<>();
    public static List<String> overlaysID = new ArrayList<>();

    public static void registerOverlay(AbstractOverlay overlay){
        if(overlays.get(overlay.id) == null){
            overlays.put(overlay.id, overlay);
            overlaysID.add(overlay.id);
            CamOverlay.log(String.format("[Overlays] Registered %s by id %s", overlay.name.getString(), overlay.id));
        } else CamOverlay.log(String.format("[Overlays] Not registered %s by id %s, its overlay already registered", overlay.name.getString(), overlay.id));
    }

    public static String [] getOverlayNames(){
        String[] list = new String[overlays.size()];
        int i=0;
        for(String id : overlaysID){
            list[i] = overlays.get(id).name.getString();
            i++;
        }
        return list;
    }

    public static int getPositionOnOverlayNames(String name){
        int i = 0;
        for(String id : getOverlayNames()){
            if(id.equals(name)) break;
            else i++;
        }
        return i == getOverlayNames().length ? 0 : i;
    }

    public static AbstractOverlay getOverlay(String id){
        return overlays.get(id) == null ? safeModeOverlay : overlays.get(id);
    }

    public static AbstractOverlay getOverlayByName(String name){
        AbstractOverlay overlay = null;
        for(String id : overlaysID){
            if(getOverlay(id) != null){
                if(getOverlay(id).name.getString().equals(name)) overlay = getOverlay(id);
            }
        }
        return overlay;
    }

    public static AbstractOverlay getSelectedOverlay(){
        return getOverlay(CamOverlay.config.getString("TYPE", overlaysID.get(0)));
    }

    // Helpers

    public static Map<String, AbstractHelper> helpers = new HashMap<>();
    public static List<String> helpersID = new ArrayList<>();

    public static void registerHelper(AbstractHelper helper){
        if(helpers.get(helper.id) == null){
            helpers.put(helper.id, helper);
            helpersID.add(helper.id);
            CamOverlay.log(String.format("[Helpers] Registered %s by id %s", helper.name.getString(), helper.id));
        } else CamOverlay.log(String.format("[Helpers] Not registered %s by id %s, its helper already registered", helper.name.getString(), helper.id));
    }

    public static String[] getHelperNames(){
        String[] list = new String[helpers.size()];
        int i=0;
        for(String id : helpersID){
            list[i] = helpers.get(id).name.getString();
            i++;
        }
        return list;
    }

    public static int getPositionOnHelperNames(String name){
        int i = 0;
        for(String id : getHelperNames()){
            if(id.equals(name)) break;
            else i++;
        }
        return i == getHelperNames().length ? 0 : i;
    }

    public static AbstractHelper getHelper(String id){
        return helpers.get(id) == null ? safeModeHelper : helpers.get(id);
    }

    public static AbstractHelper getHelperByName(String name){
        AbstractHelper helper = null;
        for(String id : helpersID){
            if(getHelper(id) != null){
                if(getHelper(id).name.getString().equals(name)) helper = getHelper(id);
            }
        }
        return helper;
    }

    public static AbstractHelper getSelectedHelper(){
        return getHelper(CamOverlay.config.getString("HELPER", helpersID.get(0)));
    }
}
