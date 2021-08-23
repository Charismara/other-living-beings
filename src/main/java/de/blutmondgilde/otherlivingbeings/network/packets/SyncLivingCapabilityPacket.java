package de.blutmondgilde.otherlivingbeings.network.packets;

import de.blutmondgilde.otherlivingbeings.capability.BeingCapability;
import de.blutmondgilde.otherlivingbeings.client.OLBClient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncLivingCapabilityPacket {
    private final CompoundTag tag;

    public SyncLivingCapabilityPacket(final BeingCapability cap, final int targetId) {
        this.tag = cap.serializeNBT();
        this.tag.putInt("entityId", targetId);
    }

    public SyncLivingCapabilityPacket(FriendlyByteBuf decoder) {
        this.tag = decoder.readNbt();
    }

    public void toBytes(FriendlyByteBuf encoder) {
        encoder.writeNbt(this.tag);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> OLBClient.syncLocalCapability(this.tag)));
        ctx.get().setPacketHandled(true);
    }
}