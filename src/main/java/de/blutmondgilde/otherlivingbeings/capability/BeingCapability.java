package de.blutmondgilde.otherlivingbeings.capability;

import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface BeingCapability extends ICapabilitySerializable<CompoundTag> {
    LivingBeing getLivingBeing();

    void setLivingBeing(final LivingBeing livingBeing);
}
