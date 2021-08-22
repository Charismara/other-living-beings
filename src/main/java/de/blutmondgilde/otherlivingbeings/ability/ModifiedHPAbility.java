package de.blutmondgilde.otherlivingbeings.ability;

import de.blutmondgilde.otherlivingbeings.api.abilities.Ability;
import de.blutmondgilde.otherlivingbeings.api.abilities.listener.AttributeUpdateListener;
import lombok.Getter;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ModifiedHPAbility extends Ability implements AttributeUpdateListener {
    @Getter
    private final float maxHp;
    private static final UUID attributeUUID = UUID.fromString("5a82c178-7c6a-44be-be52-18d53d16b381");
    private final AttributeModifier attributeModifier;

    public ModifiedHPAbility(final TranslatableComponent name, final TranslatableComponent[] description, final float maxHp) {
        super(name, description);
        this.maxHp = maxHp;
        this.attributeModifier = new AttributeModifier(attributeUUID, "OtherLivingBeings.HP", maxHp - 20, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyAttribute(final Player player) {
        final AttributeInstance attributeInstance = player.getAttribute(Attributes.MAX_HEALTH);
        if (attributeInstance.hasModifier(attributeModifier)) {
            removeAttribute(player);
        }
        attributeInstance.addPermanentModifier(attributeModifier);
        player.setHealth(Math.min(player.getHealth(), maxHp));
    }

    @Override
    public void removeAttribute(final Player player) {
        player.getAttribute(Attributes.MAX_HEALTH).removeModifier(attributeModifier);
    }
}
