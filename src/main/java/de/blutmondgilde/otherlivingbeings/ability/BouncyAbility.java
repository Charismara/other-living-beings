package de.blutmondgilde.otherlivingbeings.ability;

import de.blutmondgilde.otherlivingbeings.api.abilities.Ability;
import de.blutmondgilde.otherlivingbeings.api.abilities.listener.PlayerFallListener;
import de.blutmondgilde.otherlivingbeings.util.BounceHelper;
import de.blutmondgilde.otherlivingbeings.util.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

public class BouncyAbility extends Ability implements PlayerFallListener {
    public BouncyAbility() {
        super(ComponentUtils.translatableAbility("bouncy.name"), new TranslatableComponent[]{
                ComponentUtils.translatableAbility("bouncy.0"),
                ComponentUtils.translatableAbility("bouncy.1")
        });
    }

    @Override
    public float onFall(final Player player, final float fallDistance) {
        if (fallDistance > 2) {
            player.fallDistance = 0.0F;

            // players only bounce on the client, due to movement rules
            if (player.level.isClientSide) {
                double f = 0.95d;
                // only slow down half as much when bouncing
                player.setDeltaMovement(player.getDeltaMovement().x / f, player.getDeltaMovement().y * -0.9, player.getDeltaMovement().z / f);
                player.hasImpulse = true;
                player.setOnGround(false);
            }
            player.playSound(SoundEvents.SLIME_SQUISH, 1f, 1f);
            BounceHelper.addBounceHandler(player, player.getDeltaMovement().y);
        }

        return 0;
    }
}
