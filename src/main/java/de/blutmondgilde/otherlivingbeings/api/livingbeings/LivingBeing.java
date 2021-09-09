package de.blutmondgilde.otherlivingbeings.api.livingbeings;

import de.blutmondgilde.otherlivingbeings.api.abilities.Ability;
import de.blutmondgilde.otherlivingbeings.util.BeingResourceLocation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LivingBeing extends ForgeRegistryEntry<LivingBeing> {
    private static final ResourceLocation NoIcon = new BeingResourceLocation("textures/entity_icon/none.png");
    private final Optional<LivingBeing> evolveInto;
    @Getter
    private final ArrayList<Ability> abilities = new ArrayList<>();
    @Getter
    private final TranslatableComponent name;
    @Getter
    private final List<TranslatableComponent> descriptionLines;

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

    public ResourceLocation getIcon() {
        return NoIcon;
    }
}
