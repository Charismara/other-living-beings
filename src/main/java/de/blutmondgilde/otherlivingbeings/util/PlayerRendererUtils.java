package de.blutmondgilde.otherlivingbeings.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.LivingEntity;

public class PlayerRendererUtils {
    public static EntityRendererProvider.Context getEntityRendererContext(RenderLayerParent<? extends LivingEntity, ? extends HumanoidModel<? extends LivingEntity>> renderer) {
        return new EntityRendererProvider.Context(((PlayerRenderer) renderer).entityRenderDispatcher, Minecraft.getInstance().getItemRenderer(), Minecraft.getInstance()
                .getResourceManager(), Minecraft.getInstance()
                .getEntityModels(), Minecraft.getInstance().font);
    }
}
