package de.blutmondgilde.otherlivingbeings.handler;

import de.blutmondgilde.otherlivingbeings.api.abilities.listener.PlayerFallListener;
import de.blutmondgilde.otherlivingbeings.api.abilities.listener.PlayerLogInListener;
import de.blutmondgilde.otherlivingbeings.api.abilities.listener.PlayerLogOutListener;
import de.blutmondgilde.otherlivingbeings.api.capability.Capabilities;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.concurrent.atomic.AtomicReference;

public class PlayerEventHandler {
    public static void init(final IEventBus forgeBus) {
        forgeBus.addListener(PlayerEventHandler::onLogIn);
        forgeBus.addListener(PlayerEventHandler::onLogOut);
        forgeBus.addListener(PlayerEventHandler::onFallDamage);
    }

    private static void onLogIn(final PlayerEvent.PlayerLoggedInEvent e) {
        final Player player = e.getPlayer();
        player.getCapability(Capabilities.BEING).ifPresent(beingCapability -> beingCapability.getLivingBeing().getAbilities().stream()
                .filter(ability -> ability instanceof PlayerLogInListener)
                .map(ability -> (PlayerLogInListener) ability)
                .forEach(playerLogInListener -> playerLogInListener.onLogin(player)));
    }

    private static void onLogOut(final PlayerEvent.PlayerLoggedOutEvent e) {
        final Player player = e.getPlayer();
        player.getCapability(Capabilities.BEING).ifPresent(beingCapability -> beingCapability.getLivingBeing().getAbilities().stream()
                .filter(ability -> ability instanceof PlayerLogOutListener)
                .map(ability -> (PlayerLogOutListener) ability)
                .forEach(playerLogInListener -> playerLogInListener.onLogOut(player)));
    }

    private static void onFallDamage(final LivingFallEvent e) {
        if (!(e.getEntityLiving() instanceof Player)) return;
        final Player player = (Player) e.getEntityLiving();
        final AtomicReference<Float> fallDamage = new AtomicReference<>(e.getDamageMultiplier());

        player.getCapability(Capabilities.BEING).ifPresent(beingCapability -> beingCapability.getLivingBeing().getAbilities().stream()
                .filter(ability -> ability instanceof PlayerFallListener)
                .map(ability -> (PlayerFallListener) ability)
                .forEach(fallDamageListener -> fallDamage.set(Math.min(fallDamageListener.onFall(player, e.getDistance()), fallDamage.get()))));

        e.setDamageMultiplier(fallDamage.get());
        if (e.getDistance() <= 0) e.setCanceled(true);
    }
}
