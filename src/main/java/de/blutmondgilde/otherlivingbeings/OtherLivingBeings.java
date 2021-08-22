package de.blutmondgilde.otherlivingbeings;

import lombok.Getter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("otherlivingbeings")
public class OtherLivingBeings {
    @Getter
    private static final Logger Logger = LogManager.getLogger();
    public static final String MOD_ID = "otherlivingbeings";

    public OtherLivingBeings() {
        final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}
