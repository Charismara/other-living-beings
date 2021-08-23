package de.blutmondgilde.otherlivingbeings.ability;

import de.blutmondgilde.otherlivingbeings.api.abilities.Ability;
import de.blutmondgilde.otherlivingbeings.api.abilities.listener.PlayerSizeListener;
import de.blutmondgilde.otherlivingbeings.util.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;

public class ShortBody extends Ability implements PlayerSizeListener {
    public ShortBody() {
        super(ComponentUtils.translatableAbility(".shortbody.name"), new TranslatableComponent[]{
                ComponentUtils.translatableAbility(".shortbody.0")
        });
    }

    @Override
    public float getSize() {
        return 0.45F;
    }
}
