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
    @ObjectHolder("slime_medium")
    public static final LivingBeing slime_medium = null;
    @ObjectHolder("magma_slime_small")
    public static final LivingBeing magma_slime_small = null;
    @ObjectHolder("magma_slime_medium")
    public static final LivingBeing magma_slime_medium = null;
    @ObjectHolder("ender_slime_small")
    public static final LivingBeing ender_slime_small = null;
    @ObjectHolder("ender_slime_medium")
    public static final LivingBeing ender_slime_medium = null;
    @ObjectHolder("baby_zombie")
    public static final LivingBeing baby_zombie = null;
    @ObjectHolder("zombie")
    public static final LivingBeing zombie = null;
    @ObjectHolder("baby_zombie_villager")
    public static final LivingBeing baby_zombie_villager = null;
    @ObjectHolder("zombie_villager")
    public static final LivingBeing zombie_villager = null;
    @ObjectHolder("husk")
    public static final LivingBeing husk = null;
    @ObjectHolder("drowned")
    public static final LivingBeing drowned = null;
}
