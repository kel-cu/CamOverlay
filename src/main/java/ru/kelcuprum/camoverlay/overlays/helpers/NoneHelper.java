package ru.kelcuprum.camoverlay.overlays.helpers;

import net.minecraft.network.chat.Component;
import ru.kelcuprum.camoverlay.overlays.AbstractOverlay;

public class NoneHelper extends AbstractHelper {
    public NoneHelper() {
        super(Component.translatable("camoverlay.none"), "none");
    }
}
