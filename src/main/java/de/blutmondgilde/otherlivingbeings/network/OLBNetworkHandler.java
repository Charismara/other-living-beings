package de.blutmondgilde.otherlivingbeings.network;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.network.packets.SyncLivingCapabilityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class OLBNetworkHandler {
    private static final String PROTOCOL_VERSION = String.valueOf(1);
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(OtherLivingBeings.MOD_ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void init() {
        int id = 1;
        CHANNEL.registerMessage(id++, SyncLivingCapabilityPacket.class, SyncLivingCapabilityPacket::toBytes, SyncLivingCapabilityPacket::new, SyncLivingCapabilityPacket::handle);
    }
}
