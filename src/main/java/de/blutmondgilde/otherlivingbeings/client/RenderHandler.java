package de.blutmondgilde.otherlivingbeings.client;

import de.blutmondgilde.otherlivingbeings.api.capability.Capabilities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

@OnlyIn(Dist.CLIENT)
public class RenderHandler {
    public static void init(final IEventBus forgeBus) {
        forgeBus.addListener(RenderHandler::onPreRenderPlayer);
    }

    public static void onPreRenderPlayer(RenderPlayerEvent.Pre e) {
        e.getPlayer().getCapability(Capabilities.BEING).ifPresent(beingCapability -> {
            //TODO cancel event
            //e.setCanceled(true);
            //TODO Render Player based on his being
            beingCapability.getLivingBeing().renderModel();
        });
    }
}