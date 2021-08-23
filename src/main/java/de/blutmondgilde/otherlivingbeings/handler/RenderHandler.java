package de.blutmondgilde.otherlivingbeings.handler;

import de.blutmondgilde.otherlivingbeings.api.abilities.listener.PlayerSizeListener;
import de.blutmondgilde.otherlivingbeings.api.capability.Capabilities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

@OnlyIn(Dist.CLIENT)
public class RenderHandler {
    public static void init(final IEventBus forgeBus) {
        forgeBus.addListener(RenderHandler::onPreRenderPlayer);
        forgeBus.addListener(RenderHandler::onPostRenderPlayer);
    }

    public static void onPreRenderPlayer(RenderPlayerEvent.Pre e) {
        e.getPlayer().getCapability(Capabilities.BEING).ifPresent(beingCapability -> beingCapability.getLivingBeing().getAbilities()
                .stream()
                .filter(ability -> ability instanceof PlayerSizeListener)
                .map(ability -> (PlayerSizeListener) ability)
                .findFirst().ifPresent(playerSizeListener -> {
                    e.getMatrixStack().pushPose();
                    e.getMatrixStack().scale(playerSizeListener.getSize(), playerSizeListener.getSize(), playerSizeListener.getSize());
                    if (e.getPlayer().isCrouching() && playerSizeListener.getSize() < 0.2) {
                        e.getMatrixStack().translate(0, 1d, 0);
                    }
                }));
    }

    public static void onPostRenderPlayer(RenderPlayerEvent.Post e) {
        e.getPlayer().getCapability(Capabilities.BEING).ifPresent(beingCapability -> e.getMatrixStack().popPose());
    }
}
