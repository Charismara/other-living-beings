package de.blutmondgilde.otherlivingbeings.api.livingbeings;

import de.blutmondgilde.otherlivingbeings.api.abilities.Ability;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LivingBeing extends ForgeRegistryEntry<LivingBeing> {
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
}
