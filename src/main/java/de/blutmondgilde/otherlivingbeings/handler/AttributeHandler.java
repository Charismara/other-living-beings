package de.blutmondgilde.otherlivingbeings.handler;

import de.blutmondgilde.otherlivingbeings.api.abilities.listener.AttributeUpdateListener;
import de.blutmondgilde.otherlivingbeings.api.capability.Capabilities;
import net.minecraft.world.entity.player.Player;

public class AttributeHandler {
    public static void apply(final Player player) {
        //Apply attributes
        player.getCapability(Capabilities.BEING).ifPresent(beingCapability -> beingCapability.getLivingBeing().getAbilities()
                .stream()
                .filter(ability -> ability instanceof AttributeUpdateListener)
                .map(ability -> (AttributeUpdateListener) ability)
                .forEach(attributeUpdateListener -> attributeUpdateListener.applyAttribute(player)));
    }
}
