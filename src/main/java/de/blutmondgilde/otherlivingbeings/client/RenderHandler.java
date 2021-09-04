package de.blutmondgilde.otherlivingbeings.client;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.api.capability.Capabilities;
import de.blutmondgilde.otherlivingbeings.client.event.RenderArmorEvent;
import de.blutmondgilde.otherlivingbeings.client.event.RenderItemInHandEvent;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.listeners.ArmorRenderListener;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.listeners.ItemInHandRenderListener;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.listeners.ModelRendererListener;
import de.blutmondgilde.otherlivingbeings.client.event.RenderItemLayerEvent;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

@SuppressWarnings("ConstantConditions")
@OnlyIn(Dist.CLIENT)
public class RenderHandler {
    public static void init(final IEventBus forgeBus) {
        forgeBus.addListener(RenderHandler::onPreRenderPlayer);
        forgeBus.addListener(RenderHandler::onPreRenderArmor);
        forgeBus.addListener(RenderHandler::onApplyArmorRenderModifier);
        forgeBus.addListener(RenderHandler::onResetArmorRenderModifier);
        forgeBus.addListener(RenderHandler::onPreRenderItemInHand);
        forgeBus.addListener(RenderHandler::onApplyItemInHandRenderModifier);
        forgeBus.addListener(RenderHandler::onRemoveItemInHandRenderModifier);
        forgeBus.addListener(RenderHandler::onRenderItemLayer);
    }

    public static void onPreRenderPlayer(final RenderPlayerEvent.Pre e) {
        if (!(e.getPlayer() instanceof final AbstractClientPlayer player)) return;
        player.getCapability(Capabilities.BEING).ifPresent(beingCapability -> {
            final LivingBeing being = beingCapability.getLivingBeing();
            if (being instanceof final ModelRendererListener listener) {
                //Replace Texture with Model Texture
                try {
                    player.getPlayerInfo().textureLocations.remove(MinecraftProfileTexture.Type.SKIN);
                    player.getPlayerInfo().textureLocations.put(MinecraftProfileTexture.Type.SKIN, listener.getModelTexture());
                } catch (NullPointerException npe) {
                    OtherLivingBeings.getLogger().fatal("Could not get Player Information.");
                    npe.printStackTrace();
                }
                e.getRenderer().model = listener.getModel(e.getRenderer());
            }

            //TODO only Replace Pose if living being can do that pose
            player.setPose(Pose.STANDING);

        });
    }

    public static void onPreRenderArmor(final RenderArmorEvent.Pre e) {
        e.getPlayer().getCapability(Capabilities.BEING).ifPresent(beingCapability -> {
            if (beingCapability.getLivingBeing() instanceof final ArmorRenderListener listener) {
                listener.beforeRenderArmor(e);
            }
        });
    }

    public static void onApplyArmorRenderModifier(final RenderArmorEvent.ApplyModifier e) {
        e.getPlayer().getCapability(Capabilities.BEING).ifPresent(beingCapability -> {
            if (beingCapability.getLivingBeing() instanceof final ArmorRenderListener listener) {
                listener.applyArmorRenderModifier(e);
            }
        });
    }

    public static void onResetArmorRenderModifier(final RenderArmorEvent.ResetModifier e) {
        e.getPlayer().getCapability(Capabilities.BEING).ifPresent(beingCapability -> {
            if (beingCapability.getLivingBeing() instanceof final ArmorRenderListener listener) {
                listener.resetArmorRenderModifier(e);
            }
        });
    }

    public static void onPreRenderItemInHand(final RenderItemInHandEvent.Pre e) {
        e.getPlayer().getCapability(Capabilities.BEING).ifPresent(beingCapability -> {
            if (beingCapability.getLivingBeing() instanceof final ItemInHandRenderListener listener) {
                listener.beforeItemInHandRender(e);
            }
        });
    }

    public static void onApplyItemInHandRenderModifier(final RenderItemInHandEvent.ApplyModifier e) {
        e.getPlayer().getCapability(Capabilities.BEING).ifPresent(beingCapability -> {
            if (beingCapability.getLivingBeing() instanceof final ItemInHandRenderListener listener) {
                listener.applyItemInHandRenderModifier(e);
            }
        });
    }

    public static void onRemoveItemInHandRenderModifier(final RenderItemInHandEvent.ResetModifier e) {
        e.getPlayer().getCapability(Capabilities.BEING).ifPresent(beingCapability -> {
            if (beingCapability.getLivingBeing() instanceof final ItemInHandRenderListener listener) {
                listener.resetItemInHandRenderModifier(e);
            }
        });
    }

    public static void onRenderItemLayer(final RenderItemLayerEvent e) {
        e.getPlayer().getCapability(Capabilities.BEING).ifPresent(beingCapability -> {
            if (beingCapability instanceof ItemInHandRenderListener listener) {
                listener.onRenderItemInHand(e);
            }
        });
    }
}
