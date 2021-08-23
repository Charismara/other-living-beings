package de.blutmondgilde.otherlivingbeings.ability;

import de.blutmondgilde.otherlivingbeings.api.abilities.Ability;
import de.blutmondgilde.otherlivingbeings.api.abilities.listener.AttributeUpdateListener;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class AttributeAbility extends Ability implements AttributeUpdateListener {
    private final AttributeModifier attributeModifier;
    private final Attribute attribute;

    public AttributeAbility(final TranslatableComponent name, final TranslatableComponent[] description, final Attribute attribute, final UUID uuid, final String modifierName, final float value) {
        super(name, description);
        this.attributeModifier = new AttributeModifier(uuid, modifierName, value, AttributeModifier.Operation.ADDITION);
        this.attribute = attribute;
    }

    @Override
    public void applyAttribute(final Player player) {
        final AttributeInstance attributeInstance = player.getAttribute(this.attribute);
        if (attributeInstance.hasModifier(attributeModifier)) {
            removeAttribute(player);
        }
        attributeInstance.addPermanentModifier(attributeModifier);
        //Trigger Attribute update
        player.setHealth(Math.min(player.getHealth(), player.getMaxHealth()));
    }

    @Override
    public void removeAttribute(final Player player) {
        player.getAttribute(this.attribute).removePermanentModifier(attributeModifier.getId());
    }
}
