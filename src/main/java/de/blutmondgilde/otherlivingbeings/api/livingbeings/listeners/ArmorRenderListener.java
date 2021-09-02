package de.blutmondgilde.otherlivingbeings.api.livingbeings.listeners;

import de.blutmondgilde.otherlivingbeings.api.client.event.RenderArmorEvent;

public interface ArmorRenderListener {
    void beforeRenderArmor(RenderArmorEvent.Pre e);

    void applyArmorRenderModifier(RenderArmorEvent.ApplyModifier e);

    void resetArmorRenderModifier(RenderArmorEvent.ResetModifier e);
}
