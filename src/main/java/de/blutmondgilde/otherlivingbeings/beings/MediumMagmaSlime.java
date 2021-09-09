package de.blutmondgilde.otherlivingbeings.beings;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.registry.LivingBeings;
import de.blutmondgilde.otherlivingbeings.util.OLBConstants;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public class MediumMagmaSlime extends LivingBeing {
    public MediumMagmaSlime() {
        super(Optional.ofNullable(LivingBeings.ender_slime_small), new TranslatableComponent(OtherLivingBeings.MOD_ID + ".being.mediummagmaslime.name"), List.of());
    }

    @Override
    public ResourceLocation getIcon() {
        return OLBConstants.Icons.MAGMA_CUBE;
    }

    @Override
    public boolean isSelectable() {
        return false;
    }
}
