package de.blutmondgilde.otherlivingbeings.api.livingbeings.listeners;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;

public interface ModelRendererListener {
    PlayerModel<AbstractClientPlayer> getModel(final PlayerRenderer renderer);

    ResourceLocation getModelTexture();
}
