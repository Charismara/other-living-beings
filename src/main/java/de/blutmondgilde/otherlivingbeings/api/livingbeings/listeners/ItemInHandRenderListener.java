package de.blutmondgilde.otherlivingbeings.api.livingbeings.listeners;

import de.blutmondgilde.otherlivingbeings.client.event.RenderItemInHandEvent;
import de.blutmondgilde.otherlivingbeings.client.event.RenderItemLayerEvent;

public interface ItemInHandRenderListener {
    void beforeItemInHandRender(RenderItemInHandEvent.Pre e);

    void applyItemInHandRenderModifier(RenderItemInHandEvent.ApplyModifier e);

    void resetItemInHandRenderModifier(RenderItemInHandEvent.ResetModifier e);

    void onRenderItemInHand(RenderItemLayerEvent e);
}
