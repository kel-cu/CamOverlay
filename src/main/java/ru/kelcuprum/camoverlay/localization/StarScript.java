package ru.kelcuprum.camoverlay.localization;

import meteordevelopment.starscript.Script;
import meteordevelopment.starscript.Section;
import meteordevelopment.starscript.StandardLib;
import meteordevelopment.starscript.Starscript;
import meteordevelopment.starscript.compiler.Compiler;
import meteordevelopment.starscript.compiler.Parser;
import meteordevelopment.starscript.utils.Error;
import meteordevelopment.starscript.utils.StarscriptError;
import meteordevelopment.starscript.value.Value;
import meteordevelopment.starscript.value.ValueMap;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;

import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.Level;
import ru.kelcuprum.camoverlay.CamOverlay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static ru.kelcuprum.camoverlay.CamOverlay.MINECRAFT;

public class StarScript {
    public static Component dFormat = Component.translatable("camoverlay.date.format");
    public static Starscript ss = new Starscript();

    public static void init() {
        StandardLib.init(ss);

        // General
        ss.set("minecraft", new ValueMap()
                .set("version", SharedConstants.getCurrentVersion().getName())
                .set("loader", Minecraft.getInstance().getVersionType())
        );
        ss.set("overlay", new ValueMap()
                .set("time", () -> Value.string(getTime()))
        );
    }
    public static String parseText(String text){
        String parsedText = text;
        try {
            parsedText = StarScript.run(StarScript.compile(text));
        } catch (Exception exception){
            CamOverlay.log(exception.getLocalizedMessage(), Level.ERROR);
        }
        return parsedText;
    }
    // Helpers
    public static String getTime(){
        String clock;
        String strDateFormat = "MM/dd/yyyy hh:mm:ss";
        if(!dFormat.getString().equalsIgnoreCase("camoverlay.date.format")) strDateFormat = dFormat.getString();
        try {
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            if(CamOverlay.config.getBoolean("WORLD_TIME", false) && MINECRAFT.level != null){
                long daytime = MINECRAFT.level.getDayTime()+6000;
                int hours=(int) (daytime / 1000)%24;
                int minutes = (int) ((daytime % 1000)*60/1000);
                int day = (int) daytime / 1000 / 24;
                Calendar calendar = new GregorianCalendar();
                calendar.set(2000, Calendar.JANUARY, day+1, hours, minutes, 0);
                clock = dateFormat.format(calendar.getTimeInMillis());
            } else clock = dateFormat.format(System.currentTimeMillis());
        } catch (IllegalArgumentException ex) {
            clock = strDateFormat;
        }
        return clock;
    }
    public static Script compile(String source) {
        Parser.Result result = Parser.parse(source);

        if (result.hasErrors()) {
            for (Error error : result.errors) CamOverlay.log(error.message, Level.ERROR);
            return null;
        }

        return Compiler.compile(result);
    }

    public static Section runSection(Script script, StringBuilder sb) {
        try {
            return ss.run(script, sb);
        }
        catch (StarscriptError error) {
            error.printStackTrace();
            return null;
        }
    }
    public static String run(Script script, StringBuilder sb) {
        Section section = runSection(script, sb);
        return section != null ? section.toString() : null;
    }

    public static String run(Script script) {
        return run(script, new StringBuilder());
    }
}