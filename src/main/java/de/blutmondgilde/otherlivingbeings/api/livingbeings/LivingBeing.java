package de.blutmondgilde.otherlivingbeings.api.livingbeings;

import com.mojang.blaze3d.vertex.PoseStack;
import de.blutmondgilde.otherlivingbeings.api.abilities.Ability;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class LivingBeing extends ForgeRegistryEntry<LivingBeing> {
    private final Optional<LivingBeing> evolveInto;
    @Getter
    private final ArrayList<Ability> abilities = new ArrayList<>();

    public boolean canEvolve() {
        if (evolveInto.isEmpty()) return false;
        return true;
    }

    public void addAbility(final Ability ability) {
        abilities.add(ability);
    }

    public void addAbilities(final Ability... abilities) {
        this.abilities.addAll(List.of(abilities));
    }

    public void removeAbility(final Ability ability) {
        //Remove if they are the same Object
        if (!abilities.remove(ability)) {
            //Just to be sure the ability will be removed
            abilities.removeIf(ability1 -> ability.getRegistryName().equals(ability1.getRegistryName()));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void renderPlayer(final Player player, final PoseStack poseStack, final MultiBufferSource buffer, final int partialTicks);
}
