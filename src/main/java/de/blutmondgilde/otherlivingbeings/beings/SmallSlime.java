package de.blutmondgilde.otherlivingbeings.beings;

import de.blutmondgilde.otherlivingbeings.api.client.model.PlayerModelReplacement;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.client.model.SmallSlimeModel;
import de.blutmondgilde.otherlivingbeings.registry.Abilities;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class SmallSlime extends LivingBeing {
    @OnlyIn(Dist.CLIENT)
    public static final ResourceLocation SLIME_LOCATION = new ResourceLocation("textures/entity/slime/slime.png");

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
    public Optional<ResourceLocation> getModelTexture() {
        return Optional.of(SLIME_LOCATION);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Optional<PlayerModel<AbstractClientPlayer>> getModel() {
        return Optional.of(new SmallSlimeModel(PlayerModelReplacement.Builder.create()
                .addModelPart(SmallSlimeModel.createInnerBodyLayer().bakeRoot())
                .addModelPart(SmallSlimeModel.createOuterBodyLayer().bakeRoot())
                .build()
                .toModelPart()));
    }
}
