package de.blutmondgilde.otherlivingbeings.ability;

import net.minecraft.network.chat.TranslatableComponent;

public class SmallBeingHP extends ModifiedHPAbility {
    public SmallBeingHP() {
        super(
                new TranslatableComponent("otherlivingbeings.ability.smallbeinghp.name"), new TranslatableComponent[]{
                        new TranslatableComponent("otherlivingbeings.ability.smallbeinghp.0"),
                        new TranslatableComponent("otherlivingbeings.ability.smallbeinghp.1"),
                },
                10F);
    }
}
