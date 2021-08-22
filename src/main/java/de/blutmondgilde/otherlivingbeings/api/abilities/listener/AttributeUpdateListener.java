package de.blutmondgilde.otherlivingbeings.api.abilities.listener;

import net.minecraft.world.entity.player.Player;

public interface AttributeUpdateListener {
    void applyAttribute(Player player);

    void removeAttribute(Player player);
}
