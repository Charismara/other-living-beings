package de.blutmondgilde.otherlivingbeings.api.abilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

@AllArgsConstructor
public class Ability extends ForgeRegistryEntry<Ability> {
    @Getter
    private final TranslatableComponent name;
    @Getter
    private final TranslatableComponent[] description;
}
