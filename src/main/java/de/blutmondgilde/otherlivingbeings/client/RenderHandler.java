package de.blutmondgilde.otherlivingbeings.client;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.api.capability.Capabilities;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

@OnlyIn(Dist.CLIENT)
public class RenderHandler {
    public static void init(final IEventBus forgeBus) {
        forgeBus.addListener(RenderHandler::onPreRenderPlayer);
    }

    public static void onPreRenderPlayer(final RenderPlayerEvent.Pre e) {
        if (!(e.getPlayer() instanceof AbstractClientPlayer)) return;
        final AbstractClientPlayer player = (AbstractClientPlayer) e.getPlayer();
        player.getCapability(Capabilities.BEING).ifPresent(beingCapability -> {
            final LivingBeing being = beingCapability.getLivingBeing();
            being.getModelTexture().ifPresent(textureLocation -> {
                //Replace Texture with Slime Texture
                try {
                    player.getPlayerInfo().textureLocations.remove(MinecraftProfileTexture.Type.SKIN);
                    player.getPlayerInfo().textureLocations.put(MinecraftProfileTexture.Type.SKIN, textureLocation);
                } catch (NullPointerException npe) {
                    OtherLivingBeings.getLogger().fatal("Could not get Player Information.");
                    npe.printStackTrace();
                }
            });
            //Replace Model
            being.getModel().ifPresent(model -> e.getRenderer().model = model);
        });
    }
}
