package de.blutmondgilde.otherlivingbeings.beings;

import com.mojang.math.Vector3f;
import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.api.client.model.PlayerModelReplacement;
import de.blutmondgilde.otherlivingbeings.api.client.renderer.layer.CustomArmorLayer;
import de.blutmondgilde.otherlivingbeings.api.client.renderer.layer.CustomItemInHandLayer;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.listeners.ArmorRenderListener;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.listeners.ItemInHandRenderListener;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.listeners.ModelRendererListener;
import de.blutmondgilde.otherlivingbeings.client.event.RenderArmorEvent;
import de.blutmondgilde.otherlivingbeings.client.event.RenderItemInHandEvent;
import de.blutmondgilde.otherlivingbeings.client.event.RenderItemLayerEvent;
import de.blutmondgilde.otherlivingbeings.client.model.SmallSlimeModel;
import de.blutmondgilde.otherlivingbeings.registry.Abilities;
import de.blutmondgilde.otherlivingbeings.registry.LivingBeings;
import de.blutmondgilde.otherlivingbeings.util.OLBConstants;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SmallSlime extends LivingBeing implements ArmorRenderListener, ModelRendererListener, ItemInHandRenderListener {
    @OnlyIn(Dist.CLIENT)
    public static final ResourceLocation SLIME_LOCATION = new ResourceLocation("textures/entity/slime/slime.png");
    private static final ModelPart emptyPart = new ModelPart(new ArrayList<>(), new HashMap<>());

    public SmallSlime() {
        super(Optional.ofNullable(LivingBeings.slime_medium), new TranslatableComponent(OtherLivingBeings.MOD_ID + ".being.smallslime.name"), List.of());
        addAbility(Abilities.SmallBeing);
        addAbility(Abilities.NoLegs);
        addAbility(Abilities.JumperTier1);
        addAbility(Abilities.Bouncy);
        addAbility(Abilities.ShortBody);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ResourceLocation getModelTexture() {
        return SLIME_LOCATION;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public PlayerModel<AbstractClientPlayer> getModel(final PlayerRenderer renderer) {
        final PlayerModelReplacement replacement = new PlayerModelReplacement(
                SmallSlimeModel.createHead().bakeRoot(),
                SmallSlimeModel.createHat().bakeRoot(),
                SmallSlimeModel.createBody().bakeRoot(),
                SmallSlimeModel.createRightArm().bakeRoot(),
                SmallSlimeModel.createLeftArm().bakeRoot(),
                emptyPart,
                emptyPart,
                emptyPart,
                emptyPart,
                emptyPart,
                emptyPart,
                emptyPart,
                emptyPart,
                emptyPart);
        final SmallSlimeModel model = new SmallSlimeModel(replacement.toModelPart());
        //Remove Player Model Layers
        renderer.layers.clear();
        //Add Armor Layer
        renderer.addLayer(new CustomArmorLayer<>(renderer));
        //Add Item in hand Layer
        renderer.addLayer(new CustomItemInHandLayer<>(renderer));
        return model;
    }

    @Override
    public void beforeRenderArmor(final RenderArmorEvent.Pre e) {
        //Only Render the Helmet
        if (!e.getEquipmentSlot().equals(EquipmentSlot.HEAD)) e.setCanceled(true);
    }

    @Override
    public void applyArmorRenderModifier(final RenderArmorEvent.ApplyModifier e) {
        switch (e.getEquipmentSlot()) {
            case HEAD -> {
                e.getPoseStack().translate(0, 0.4, 0);
                e.getPoseStack().scale(0.9F, 0.9F, 0.9F);
            }
        }
    }

    @Override
    public void resetArmorRenderModifier(final RenderArmorEvent.ResetModifier e) {
        switch (e.getEquipmentSlot()) {
            case HEAD -> {
                e.getPoseStack().translate(0, 0, 0);
                e.getPoseStack().scale(1F, 1F, 1F);
                e.getPoseStack().mulPose(Vector3f.XP.rotationDegrees(-45));
            }
        }
    }

    @Override
    public void beforeItemInHandRender(RenderItemInHandEvent.Pre e) {
        //Nothing to do here
    }

    @Override
    public void applyItemInHandRenderModifier(RenderItemInHandEvent.ApplyModifier e) {
        e.getPoseStack().translate(0, -0.5, -0.1);
        if (e.getItemStack().is(Items.SPYGLASS) && e.getPlayer().getUseItem() == e.getItemStack() && e.getPlayer().swingTime == 0) {
            e.getPoseStack().translate(-0.05, 0.525, 0.05);
        }
        e.getPoseStack().scale(0.9F, 0.9F, 0.9F);
        e.getPoseStack().mulPose(Vector3f.XP.rotationDegrees(-15));
        if (e.getItemStack().is(Items.SPYGLASS) && e.getPlayer().getUseItem() == e.getItemStack() && e.getPlayer().swingTime == 0) {
            e.getPoseStack().mulPose(Vector3f.XP.rotationDegrees(55));
        }

        e.getRenderer().model.leftArm.zRot = 0;
        e.getRenderer().model.rightArm.zRot = 0;
    }

    @Override
    public void resetItemInHandRenderModifier(RenderItemInHandEvent.ResetModifier e) {
        e.getPoseStack().translate(0, 0, 0);
        e.getPoseStack().scale(1F, 1F, 1F);
        e.getPoseStack().mulPose(Vector3f.XP.rotationDegrees(15));
        if (e.getItemStack().is(Items.SPYGLASS) && e.getPlayer().getUseItem() == e.getItemStack() && e.getPlayer().swingTime == 0) {
            e.getPoseStack().mulPose(Vector3f.XP.rotationDegrees(-45));
        }
    }

    @Override
    public void onRenderItemInHand(RenderItemLayerEvent e) {
        e.getStack().mulPose(Vector3f.ZP.rotationDegrees(-e.getItemTransform().rotation.z()));
    }

    @Override
    public ResourceLocation getIcon() {
        return OLBConstants.Icons.SLIME;
    }
}
