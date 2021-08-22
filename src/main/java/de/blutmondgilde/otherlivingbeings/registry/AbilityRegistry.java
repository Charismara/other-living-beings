package de.blutmondgilde.otherlivingbeings.registry;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.ability.SmallBeingHP;
import de.blutmondgilde.otherlivingbeings.api.abilities.Ability;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;

public class AbilityRegistry {
    private static final DeferredRegister<Ability> registry = DeferredRegister.create(Ability.class, OtherLivingBeings.MOD_ID);
    private static final RegistryObject<Ability> small_being = registry.register("small_being", SmallBeingHP::new);

    public static void init(final IEventBus modEventBus) {
        registry.makeRegistry("abilities", RegistryBuilder::new);
        registry.register(modEventBus);
    }
}
