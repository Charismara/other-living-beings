package de.blutmondgilde.otherlivingbeings.beings;

import de.blutmondgilde.otherlivingbeings.api.client.event.RenderArmorEvent;
import de.blutmondgilde.otherlivingbeings.api.client.model.PlayerModelReplacement;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.listeners.ArmorRenderListener;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.listeners.ModelRendererListener;
import de.blutmondgilde.otherlivingbeings.client.model.SmallSlimeModel;
import de.blutmondgilde.otherlivingbeings.api.client.layer.CustomArmorLayer;
import de.blutmondgilde.otherlivingbeings.registry.Abilities;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class SmallSlime extends LivingBeing implements ArmorRenderListener, ModelRendererListener {
    @OnlyIn(Dist.CLIENT)
    public static final ResourceLocation SLIME_LOCATION = new ResourceLocation("textures/entity/slime/slime.png");
    private static final ModelPart emptyPart = new ModelPart(new ArrayList<>(), new HashMap<>());

    public SmallSlime() {
        super(Optional.empty());
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
                emptyPart,
                emptyPart,
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

        return model;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void beforeRenderArmor(final RenderArmorEvent.Pre e) {
        //Only Render the Helmet
        if (!e.getEquipmentSlot().equals(EquipmentSlot.HEAD)) e.setCanceled(true);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void applyArmorRenderModifier(final RenderArmorEvent.ApplyModifier e) {
        switch (e.getEquipmentSlot()) {
            case HEAD -> {
                e.getPoseStack().translate(0, 0.4, 0);
                e.getPoseStack().scale(0.9F, 0.9F, 0.9F);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void resetArmorRenderModifier(final RenderArmorEvent.ResetModifier e) {
        switch (e.getEquipmentSlot()) {
            case HEAD -> {
                e.getPoseStack().translate(0, 0, 0);
                e.getPoseStack().scale(1F, 1F, 1F);
            }
        }
    }
}
