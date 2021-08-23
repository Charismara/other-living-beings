package de.blutmondgilde.otherlivingbeings.network.packets;

import de.blutmondgilde.otherlivingbeings.api.capability.Capabilities;
import de.blutmondgilde.otherlivingbeings.capability.BeingCapability;
import de.blutmondgilde.otherlivingbeings.handler.AttributeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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
        ctx.get().enqueueWork(() -> {
            final Player clientPlayer = Minecraft.getInstance().player;
            final Entity entity = clientPlayer.level.getEntity(this.tag.getInt("entityId"));
            entity.getCapability(Capabilities.BEING).ifPresent(beingCapability -> {
                //Update Capability
                beingCapability.deserializeNBT(this.tag);
                //Update Attributes
                if (entity instanceof Player) {
                    AttributeHandler.apply((Player) entity);
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }
}