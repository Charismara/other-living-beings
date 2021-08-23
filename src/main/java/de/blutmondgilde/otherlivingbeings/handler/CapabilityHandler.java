package de.blutmondgilde.otherlivingbeings.handler;

import de.blutmondgilde.otherlivingbeings.api.capability.Capabilities;
import de.blutmondgilde.otherlivingbeings.capability.BeingCapability;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class CapabilityHandler {
    public static void init(final IEventBus forgeBus) {
        forgeBus.addListener(CapabilityHandler::onLoggIn);
        forgeBus.addListener(CapabilityHandler::onRespawn);
        forgeBus.addListener(CapabilityHandler::onDimChanged);
    }

    public static void onLoggIn(final PlayerEvent.PlayerLoggedInEvent e) {
        syncCapabilities(e);
    }

    public static void onRespawn(final PlayerEvent.PlayerRespawnEvent e) {
        syncCapabilities(e);
    }

    public static void onDimChanged(final PlayerEvent.PlayerChangedDimensionEvent e) {
        syncCapabilities(e);
    }

    private static void syncCapabilities(final PlayerEvent e) {
        final Player player = e.getPlayer();
        player.getCapability(Capabilities.BEING).ifPresent(beingCapability -> BeingCapability.sync(player));
    }
}
