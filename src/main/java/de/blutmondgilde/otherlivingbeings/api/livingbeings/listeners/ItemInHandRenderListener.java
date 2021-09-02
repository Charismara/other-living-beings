package de.blutmondgilde.otherlivingbeings.api.livingbeings.listeners;

import de.blutmondgilde.otherlivingbeings.api.client.event.RenderItemInHandEvent;

public interface ItemInHandRenderListener {
    void beforeItemInHandRender(RenderItemInHandEvent.Pre e);

    void applyItemInHandRenderModifier(RenderItemInHandEvent.ApplyModifier e);

    void resetItemInHandRenderModifier(RenderItemInHandEvent.ResetModifier e);
}
