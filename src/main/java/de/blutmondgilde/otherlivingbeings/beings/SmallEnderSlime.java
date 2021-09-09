package de.blutmondgilde.otherlivingbeings.beings;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.registry.LivingBeings;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.List;
import java.util.Optional;

public class SmallEnderSlime extends LivingBeing {
    public SmallEnderSlime() {
        super(Optional.ofNullable(LivingBeings.ender_slime_medium), new TranslatableComponent(OtherLivingBeings.MOD_ID + ".being.smallenderslime.name"), List.of());
    }

    @Override
    public boolean isSelectable() {
        return false;
    }
}
