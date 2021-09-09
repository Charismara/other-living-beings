package de.blutmondgilde.otherlivingbeings.beings;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.List;
import java.util.Optional;

public class MediumEnderSlime extends LivingBeing {
    public MediumEnderSlime() {
        super(Optional.empty(), new TranslatableComponent(OtherLivingBeings.MOD_ID + ".being.mediumenderslime.name"), List.of());
    }

    @Override
    public boolean isSelectable() {
        return false;
    }
}
