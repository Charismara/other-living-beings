package de.blutmondgilde.otherlivingbeings.api.abilities.listener;

import net.minecraft.world.entity.EntityDimensions;

public interface PlayerSizeListener {
    EntityDimensions getSize(EntityDimensions dimensions);

    float getEyeHeight(float oldEyeHeight);
}
