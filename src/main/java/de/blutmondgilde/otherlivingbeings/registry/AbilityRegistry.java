package de.blutmondgilde.otherlivingbeings.registry;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.ability.NoLegsAbility;
import de.blutmondgilde.otherlivingbeings.ability.SmallBeingHPAbility;
import de.blutmondgilde.otherlivingbeings.api.abilities.Ability;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;

public class AbilityRegistry {
    private static final DeferredRegister<Ability> registry = DeferredRegister.create(Ability.class, OtherLivingBeings.MOD_ID);
    private static final RegistryObject<Ability> small_being = registry.register("small_being", SmallBeingHPAbility::new);
    private static final RegistryObject<Ability> no_legs = registry.register("no_legs", NoLegsAbility::new);

    public static void init(final IEventBus modEventBus) {
        registry.makeRegistry("abilities", RegistryBuilder::new);
        registry.register(modEventBus);
    }
}
