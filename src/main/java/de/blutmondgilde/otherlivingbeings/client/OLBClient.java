package de.blutmondgilde.otherlivingbeings.client;

import de.blutmondgilde.otherlivingbeings.api.capability.Capabilities;
import de.blutmondgilde.otherlivingbeings.client.gui.ChooseRaceGui;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.IEventBus;

public class OLBClient {
    public static void init(final IEventBus modBus, final IEventBus forgeBus) {
        RenderHandler.init(forgeBus);
    }

    public static void syncLocalCapability(final CompoundTag tag) {
        final Player clientPlayer = Minecraft.getInstance().player;
        final Entity entity = clientPlayer.level.getEntity(tag.getInt("entityId"));
        entity.getCapability(Capabilities.BEING).ifPresent(beingCapability -> {
            //Update Capability
            beingCapability.deserializeNBT(tag);
            //Update player size
            entity.refreshDimensions();
        });
    }

    public static void openChoseRaceGui(){
        Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(new ChooseRaceGui()));
    }
}
