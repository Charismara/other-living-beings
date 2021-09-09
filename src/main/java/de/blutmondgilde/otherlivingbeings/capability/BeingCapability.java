package de.blutmondgilde.otherlivingbeings.capability;

import de.blutmondgilde.otherlivingbeings.api.capability.Capabilities;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.client.OLBClient;
import de.blutmondgilde.otherlivingbeings.handler.AttributeHandler;
import de.blutmondgilde.otherlivingbeings.network.OLBNetworkHandler;
import de.blutmondgilde.otherlivingbeings.network.packets.SyncLivingCapabilityPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

public interface BeingCapability extends ICapabilitySerializable<CompoundTag> {
    boolean hasBeenChosen();

    LivingBeing getLivingBeing();

    void setLivingBeing(final LivingBeing livingBeing);

    static void sync(final Player player) {
        //Update on Server Side
        AttributeHandler.apply(player);
        //Update player size
        player.refreshDimensions();
        //Update on Client Side
        OLBNetworkHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new SyncLivingCapabilityPacket(player.getCapability(Capabilities.BEING)
                .orElseThrow(() -> new IllegalStateException("Tried to Sync non existent capability")), player.getId()));
        //Open Race selection
        player.getCapability(Capabilities.BEING).ifPresent(beingCapability -> {
            if (!beingCapability.hasBeenChosen()) {
                DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> OLBClient::openChoseRaceGui);
            }
        });
    }
}
