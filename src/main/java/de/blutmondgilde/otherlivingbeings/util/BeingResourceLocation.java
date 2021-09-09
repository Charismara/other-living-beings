package de.blutmondgilde.otherlivingbeings.util;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import net.minecraft.resources.ResourceLocation;

public class BeingResourceLocation extends ResourceLocation {
    public BeingResourceLocation(String path) {
        super(OtherLivingBeings.MOD_ID, path);
    }
}
