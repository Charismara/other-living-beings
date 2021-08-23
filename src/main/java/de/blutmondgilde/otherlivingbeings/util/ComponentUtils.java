package de.blutmondgilde.otherlivingbeings.util;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import net.minecraft.network.chat.TranslatableComponent;

public class ComponentUtils {
    public static TranslatableComponent translatable(final String path) {
        return new TranslatableComponent(OtherLivingBeings.MOD_ID + "." + path);
    }

    public static TranslatableComponent translatableAbility(final String path) {
        return translatable("ability." + path);
    }
}
