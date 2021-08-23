package de.blutmondgilde.otherlivingbeings.api.abilities.listener;

import net.minecraft.world.entity.player.Player;

public interface PlayerFallListener {
    /**
     * @param player Affected Player
     * @param distance Fall distance
     * @return fall damage amount
     */
    float onFall(final Player player, final float distance);
}
