package de.blutmondgilde.otherlivingbeings.beings;

import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.util.ComponentUtils;

import java.util.List;
import java.util.Optional;

public class Human extends LivingBeing {
    public Human() {
        super(Optional.empty(), ComponentUtils.translatable("being.human.name"), List.of());
    }

    @Override
    public boolean isSelectable() {
        return false;
    }
}
