package de.blutmondgilde.otherlivingbeings.beings;

import de.blutmondgilde.otherlivingbeings.ability.SmallBeingHP;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;

import java.util.Optional;

public class SmallSlime extends LivingBeing {
    public SmallSlime() {
        super(Optional.empty());
        addAbility(new SmallBeingHP());
    }
}
