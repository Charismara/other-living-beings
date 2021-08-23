package de.blutmondgilde.otherlivingbeings.registry;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.ability.BouncyAbility;
import de.blutmondgilde.otherlivingbeings.ability.JumperTier1Ability;
import de.blutmondgilde.otherlivingbeings.ability.JumperTier2Ability;
import de.blutmondgilde.otherlivingbeings.ability.NoLegsAbility;
import de.blutmondgilde.otherlivingbeings.ability.ShortBody;
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
    private static final RegistryObject<Ability> jumper_tier1 = registry.register("jumper_tier1", JumperTier1Ability::new);
    private static final RegistryObject<Ability> jumper_tier2 = registry.register("jumper_tier2", JumperTier2Ability::new);
    private static final RegistryObject<Ability> bouncy = registry.register("bouncy", BouncyAbility::new);
    private static final RegistryObject<Ability> short_body = registry.register("short_body", ShortBody::new);

    public static void init(final IEventBus modEventBus) {
        registry.makeRegistry("abilities", RegistryBuilder::new);
        registry.register(modEventBus);
    }
}
