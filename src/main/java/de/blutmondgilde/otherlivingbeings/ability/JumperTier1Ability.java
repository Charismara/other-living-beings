package de.blutmondgilde.otherlivingbeings.ability;

import de.blutmondgilde.otherlivingbeings.api.abilities.EffectAbility;
import de.blutmondgilde.otherlivingbeings.util.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffects;

public class JumperTier1Ability extends EffectAbility {
    public JumperTier1Ability() {
        super(ComponentUtils.translatableAbility("jumpertier1.name"), new TranslatableComponent[]{
                ComponentUtils.translatableAbility("jumpertier1.0"),
                ComponentUtils.translatableAbility("jumpertier1.1")
        }, MobEffects.JUMP, 86400, 0);
    }
}
