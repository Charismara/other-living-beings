package de.blutmondgilde.otherlivingbeings.client;

import de.blutmondgilde.otherlivingbeings.handler.RenderHandler;
import net.minecraftforge.eventbus.api.IEventBus;

public class OLBClient {
    public static void init(final IEventBus modBus, final IEventBus forgeBus) {
        RenderHandler.init(forgeBus);
    }
}
