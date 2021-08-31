package de.blutmondgilde.otherlivingbeings.ability;

import de.blutmondgilde.otherlivingbeings.api.abilities.Ability;
import de.blutmondgilde.otherlivingbeings.api.abilities.listener.PlayerSizeListener;
import de.blutmondgilde.otherlivingbeings.util.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EntityDimensions;

public class ShortBody extends Ability implements PlayerSizeListener {
    public ShortBody() {
        super(ComponentUtils.translatableAbility(".shortbody.name"), new TranslatableComponent[]{
                ComponentUtils.translatableAbility(".shortbody.0")
        });
    }

    @Override
    public EntityDimensions getSize(EntityDimensions dimensions) {
        return EntityDimensions.fixed(0.52F, 0.52F);
    }

    @Override
    public float getEyeHeight(float oldEyeHeight) {
        return 0.5F;
    }
}
