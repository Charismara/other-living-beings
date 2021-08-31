package de.blutmondgilde.otherlivingbeings.beings;

import de.blutmondgilde.otherlivingbeings.api.client.model.PlayerModelReplacement;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.client.model.SmallSlimeModel;
import de.blutmondgilde.otherlivingbeings.registry.Abilities;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class SmallSlime extends LivingBeing {
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
    public Optional<ResourceLocation> getModelTexture() {
        return Optional.of(SLIME_LOCATION);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Optional<PlayerModel<AbstractClientPlayer>> getModel() {

        final PlayerModelReplacement model = new PlayerModelReplacement(
                SmallSlimeModel.createHead().bakeRoot(),
                emptyPart,
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
        return Optional.of(new SmallSlimeModel(model.toModelPart()));
    }
}
