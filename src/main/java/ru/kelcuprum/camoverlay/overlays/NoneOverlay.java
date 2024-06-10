package ru.kelcuprum.camoverlay.overlays;

import net.minecraft.network.chat.Component;

public class NoneOverlay extends AbstractOverlay{
    public NoneOverlay() {
        super(Component.translatable("camoverlay.none"), "none");
    }
}
