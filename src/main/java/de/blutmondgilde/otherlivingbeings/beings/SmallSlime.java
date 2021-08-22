package de.blutmondgilde.otherlivingbeings.beings;

import de.blutmondgilde.otherlivingbeings.ability.NoLegsAbility;
import de.blutmondgilde.otherlivingbeings.ability.SmallBeingHPAbility;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;

import java.util.Optional;

public class SmallSlime extends LivingBeing {
    public SmallSlime() {
        super(Optional.empty());
        addAbility(new SmallBeingHPAbility());
        addAbility(new NoLegsAbility());
    }
}
