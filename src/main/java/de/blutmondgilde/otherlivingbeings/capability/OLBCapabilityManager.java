package de.blutmondgilde.otherlivingbeings.capability;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.registry.LivingBeings;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class OLBCapabilityManager {
    public static void init(final IEventBus modEventBus, final IEventBus forgeBus) {
        modEventBus.addListener(OLBCapabilityManager::registerCapabilities);
        forgeBus.addGenericListener(Entity.class, OLBCapabilityManager::attachCapabilities);
    }

    public static void registerCapabilities(final RegisterCapabilitiesEvent e) {
        e.register(BeingCapability.class);
    }

    public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> e) {
        if (BeingCapabilityImpl.canAttachTo(e.getObject())) {
            e.addCapability(new ResourceLocation(OtherLivingBeings.MOD_ID, "being_cap"), new BeingCapabilityImpl(LivingBeings.human, false));
        }
    }
}
