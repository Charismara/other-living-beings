package de.blutmondgilde.otherlivingbeings.registry;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import lombok.experimental.UtilityClass;
import net.minecraftforge.registries.ObjectHolder;

@UtilityClass
@ObjectHolder(OtherLivingBeings.MOD_ID)
public class LivingBeings {
    @ObjectHolder("slime_small")
    public static final LivingBeing small_slime = null;
}
