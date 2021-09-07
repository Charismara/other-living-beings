package de.blutmondgilde.otherlivingbeings.capability;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.api.capability.Capabilities;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fmllegacy.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@NoArgsConstructor
@AllArgsConstructor
public class BeingCapabilityImpl implements BeingCapability {
    @Setter
    @Getter
    private LivingBeing livingBeing;
    private final LazyOptional<BeingCapability> holder = LazyOptional.of(() -> this);
    @Setter
    private boolean hasBeenChosen;

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (livingBeing == null) return tag;
        tag.putString("being", livingBeing.getRegistryName().toString());
        tag.putBoolean("chosen", hasBeenChosen);
        return tag;
    }

    @Override
    public void deserializeNBT(final CompoundTag nbt) {
        if (nbt.isEmpty()) return;
        this.livingBeing = GameRegistry.findRegistry(LivingBeing.class).getValue(new ResourceLocation(nbt.getString("being")));
        this.hasBeenChosen = nbt.getBoolean("chosen");
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return Capabilities.BEING.orEmpty(cap, holder);
    }

    public static boolean canAttachTo(ICapabilityProvider provider) {
        if (!(provider instanceof Player)) return false;
        try {
            if (provider.getCapability(Capabilities.BEING).isPresent()) return false;
        } catch (NullPointerException e) {
            OtherLivingBeings.getLogger().error("Failed to get capability from {}", provider);
            return false;
        }
        return true;
    }

    @Override
    public boolean hasBeenChosen() {
        return this.hasBeenChosen;
    }
}
