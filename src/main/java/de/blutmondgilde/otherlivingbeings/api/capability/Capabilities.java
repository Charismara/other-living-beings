package de.blutmondgilde.otherlivingbeings.api.capability;

import de.blutmondgilde.otherlivingbeings.capability.BeingCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class Capabilities {
    @CapabilityInject(BeingCapability.class)
    public static final Capability<BeingCapability> BEING = null;
}
