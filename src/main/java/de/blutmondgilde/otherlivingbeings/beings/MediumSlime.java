package de.blutmondgilde.otherlivingbeings.beings;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.util.OLBConstants;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public class MediumSlime extends LivingBeing {
    public MediumSlime() {
        super(Optional.empty(), new TranslatableComponent(OtherLivingBeings.MOD_ID + ".being.mediumslime.name"), List.of());
    }

    @Override
    public ResourceLocation getIcon() {
        return OLBConstants.Icons.SLIME;
    }
}
