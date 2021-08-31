package de.blutmondgilde.otherlivingbeings.beings;

import com.mojang.blaze3d.vertex.PoseStack;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.registry.Abilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class SmallSlime extends LivingBeing {
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
    public void renderPlayer(final Player player, final PoseStack poseStack, final MultiBufferSource buffer, final int light, final float partialTicks) {
        final Slime entity = EntityType.SLIME.create(player.level);
        entity.setSize(0, false);
        entity.setPos(player.getX(), player.getY(), player.getZ());
        entity.setXRot(player.getXRot());
        entity.setYRot(player.getYRot());
        entity.setYHeadRot(player.getYHeadRot());
        entity.setOldPosAndRot();

        Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity, 0F, partialTicks, poseStack, buffer, light);
    }
}
