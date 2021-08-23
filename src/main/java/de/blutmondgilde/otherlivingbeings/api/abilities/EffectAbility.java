package de.blutmondgilde.otherlivingbeings.api.abilities;

import de.blutmondgilde.otherlivingbeings.api.abilities.listener.PlayerLogOutListener;
import de.blutmondgilde.otherlivingbeings.api.abilities.listener.PlayerLogInListener;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public class EffectAbility extends Ability implements PlayerLogInListener, PlayerLogOutListener {
    private final MobEffectInstance effect;

    public EffectAbility(TranslatableComponent name, TranslatableComponent[] description, final MobEffect effect, final int duration, final int amplifier) {
        super(name, description);
        this.effect = new MobEffectInstance(effect, duration, amplifier, false, false, false);
    }

    @Override
    public void onLogin(final Player player) {
        player.addEffect(effect);
    }

    @Override
    public void onLogOut(final Player player) {
        player.removeEffect(effect.getEffect());
    }
}
