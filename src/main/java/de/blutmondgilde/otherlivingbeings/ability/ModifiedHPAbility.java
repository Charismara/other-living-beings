package de.blutmondgilde.otherlivingbeings.ability;

import de.blutmondgilde.otherlivingbeings.api.abilities.AttributeAbility;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class ModifiedHPAbility extends AttributeAbility {
    private static final UUID uuid = UUID.fromString("5a82c178-7c6a-44be-be52-18d53d16b381");

    public ModifiedHPAbility(final TranslatableComponent name, final TranslatableComponent[] description, final float maxHp) {
        super(name, description, Attributes.MAX_HEALTH, uuid, "OtherLivingBeings.HP", maxHp - 20);
    }
}
