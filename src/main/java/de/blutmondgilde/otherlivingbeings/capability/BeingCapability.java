package de.blutmondgilde.otherlivingbeings.capability;

import de.blutmondgilde.otherlivingbeings.api.capability.Capabilities;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.handler.AttributeHandler;
import de.blutmondgilde.otherlivingbeings.network.OLBNetworkHandler;
import de.blutmondgilde.otherlivingbeings.network.packets.SyncLivingCapabilityPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

public interface BeingCapability extends ICapabilitySerializable<CompoundTag> {
    LivingBeing getLivingBeing();

    void setLivingBeing(final LivingBeing livingBeing);

    public static void sync(final Player player) {
        //Update on Server Side
        AttributeHandler.apply(player);
        //Update on Client Side
        OLBNetworkHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new SyncLivingCapabilityPacket(player.getCapability(Capabilities.BEING)
                .orElseThrow(() -> new IllegalStateException("Tried to Sync non existent capability")), player.getId()));
    }
}
