package de.blutmondgilde.otherlivingbeings.ability;

import de.blutmondgilde.otherlivingbeings.api.abilities.EffectAbility;
import de.blutmondgilde.otherlivingbeings.util.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffects;

public class JumperTier2Ability extends EffectAbility {
    public JumperTier2Ability() {
        super(ComponentUtils.translatableAbility("jumpertier2.name"), new TranslatableComponent[]{
                ComponentUtils.translatableAbility("jumpertier2.0"),
                ComponentUtils.translatableAbility("jumpertier2.1")
        }, MobEffects.JUMP, 86400, 2);
    }
}
