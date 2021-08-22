package de.blutmondgilde.otherlivingbeings.handler;

import de.blutmondgilde.otherlivingbeings.api.abilities.listener.AttributeUpdateListener;
import de.blutmondgilde.otherlivingbeings.api.capability.Capabilities;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class AttributeHandler {
    public static void init(final IEventBus forgeBus) {
        forgeBus.addListener(AttributeHandler::onPlayerJoin);
    }

    public static void onPlayerJoin(final PlayerEvent.PlayerLoggedInEvent e) {
        final Player player = e.getPlayer();
        //Apply attributes
        player.getCapability(Capabilities.BEING).ifPresent(beingCapability -> beingCapability.getLivingBeing().getAbilities()
                .stream()
                .filter(ability -> ability instanceof AttributeUpdateListener)
                .map(ability -> (AttributeUpdateListener) ability)
                .forEach(attributeUpdateListener -> attributeUpdateListener.applyAttribute(player)));
    }
}
