package de.blutmondgilde.otherlivingbeings.ability;

import de.blutmondgilde.otherlivingbeings.api.abilities.AttributeAbility;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class NoLegsAbility extends AttributeAbility {
    public NoLegsAbility() {
        super(new TranslatableComponent("otherlivingbeings.ability.nolegs.name"), new TranslatableComponent[]{
                        new TranslatableComponent("otherlivingbeings.ability.nolegs.0"),
                        new TranslatableComponent("otherlivingbeings.ability.nolegs.1")
                },
                Attributes.MOVEMENT_SPEED,
                UUID.fromString("a9f7aead-c844-48f1-b581-5ff21c0b75ac"),
                "OtherLivingBeings.NoLegs",
                0F - 0.1F);
    }


}
