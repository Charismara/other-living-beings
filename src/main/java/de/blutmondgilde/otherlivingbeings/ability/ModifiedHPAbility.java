package de.blutmondgilde.otherlivingbeings.ability;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class ModifiedHPAbility extends AttributeAbility {
    public ModifiedHPAbility(final TranslatableComponent name, final TranslatableComponent[] description, final float maxHp) {
        super(name, description, Attributes.MAX_HEALTH, UUID.fromString("5a82c178-7c6a-44be-be52-18d53d16b381"), "OtherLivingBeings.HP", maxHp - 20);
    }
}
