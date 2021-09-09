package de.blutmondgilde.otherlivingbeings.registry;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.beings.BabyZombie;
import de.blutmondgilde.otherlivingbeings.beings.BabyZombieVillager;
import de.blutmondgilde.otherlivingbeings.beings.Drowned;
import de.blutmondgilde.otherlivingbeings.beings.Husk;
import de.blutmondgilde.otherlivingbeings.beings.MediumEnderSlime;
import de.blutmondgilde.otherlivingbeings.beings.MediumMagmaSlime;
import de.blutmondgilde.otherlivingbeings.beings.MediumSlime;
import de.blutmondgilde.otherlivingbeings.beings.SmallEnderSlime;
import de.blutmondgilde.otherlivingbeings.beings.SmallMagmaSlime;
import de.blutmondgilde.otherlivingbeings.beings.SmallSlime;
import de.blutmondgilde.otherlivingbeings.beings.Zombie;
import de.blutmondgilde.otherlivingbeings.beings.ZombieVillager;
import lombok.experimental.UtilityClass;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;

@UtilityClass
public class LivingBeingRegistry {
    private static final DeferredRegister<LivingBeing> registry = DeferredRegister.create(LivingBeing.class, OtherLivingBeings.MOD_ID);
    private static final RegistryObject<LivingBeing> slime_small = registry.register("slime_small", SmallSlime::new);
    private static final RegistryObject<LivingBeing> slime_medium = registry.register("slime_medium", MediumSlime::new);
    private static final RegistryObject<LivingBeing> magma_slime_small = registry.register("magma_slime_small", SmallMagmaSlime::new);
    private static final RegistryObject<LivingBeing> magma_slime_medium = registry.register("magma_slime_medium", MediumMagmaSlime::new);
    private static final RegistryObject<LivingBeing> ender_slime_small = registry.register("ender_slime_small", SmallEnderSlime::new);
    private static final RegistryObject<LivingBeing> ender_slime_medium = registry.register("ender_slime_medium", MediumEnderSlime::new);
    private static final RegistryObject<LivingBeing> baby_zombie = registry.register("baby_zombie", BabyZombie::new);
    private static final RegistryObject<LivingBeing> zombie = registry.register("zombie", Zombie::new);
    private static final RegistryObject<LivingBeing> baby_zombie_villager = registry.register("baby_zombie_villager", BabyZombieVillager::new);
    private static final RegistryObject<LivingBeing> zombie_villager = registry.register("zombie_villager", ZombieVillager::new);
    private static final RegistryObject<LivingBeing> husk = registry.register("husk", Husk::new);
    private static final RegistryObject<LivingBeing> drowned = registry.register("drowned", Drowned::new);

    public static void init(final IEventBus modEventBus) {
        registry.makeRegistry("living_beings", RegistryBuilder::new);
        registry.register(modEventBus);
    }
}
