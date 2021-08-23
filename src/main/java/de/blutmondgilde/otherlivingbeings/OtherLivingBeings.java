package de.blutmondgilde.otherlivingbeings;

import de.blutmondgilde.otherlivingbeings.capability.OLBCapabilityManager;
import de.blutmondgilde.otherlivingbeings.handler.CapabilityHandler;
import de.blutmondgilde.otherlivingbeings.network.OLBNetworkHandler;
import de.blutmondgilde.otherlivingbeings.registry.AbilityRegistry;
import de.blutmondgilde.otherlivingbeings.registry.LivingBeingRegistry;
import lombok.Getter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("otherlivingbeings")
public class OtherLivingBeings {
    @Getter
    private static final Logger Logger = LogManager.getLogger();
    public static final String MOD_ID = "otherlivingbeings";

    public OtherLivingBeings() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        registerRegistries(modEventBus);

        final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        CapabilityHandler.init(forgeEventBus);

        OLBCapabilityManager.init(modEventBus, forgeEventBus);
    }

    private void setup(final FMLCommonSetupEvent e) {
        OLBNetworkHandler.init();
    }

    private void registerRegistries(final IEventBus modEventBus) {
        AbilityRegistry.init(modEventBus); //Register all abilities
        LivingBeingRegistry.init(modEventBus); //Register all beings
    }
}
