package de.blutmondgilde.otherlivingbeings.registry;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.beings.SmallSlime;
import lombok.experimental.UtilityClass;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;

@UtilityClass
public class LivingBeingRegistry {
    private static final DeferredRegister<LivingBeing> registry = DeferredRegister.create(LivingBeing.class, OtherLivingBeings.MOD_ID);
    private static final RegistryObject<LivingBeing> slime_small = registry.register("slime_small", SmallSlime::new);

    public static void init(final IEventBus modEventBus) {
        registry.makeRegistry("living_beings", RegistryBuilder::new);
        registry.register(modEventBus);
    }
}
