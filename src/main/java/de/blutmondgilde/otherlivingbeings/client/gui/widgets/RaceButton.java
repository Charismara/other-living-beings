package de.blutmondgilde.otherlivingbeings.client.gui.widgets;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class RaceButton extends Button {
    public RaceButton(int x, int y, int width, int height, Component displayedText, LivingBeing livingBeing) {
        super(x, y, width, height, displayedText, button -> {
            OtherLivingBeings.getLogger().info("Clicked on {}", livingBeing.getRegistryName().getPath());
        });
    }
}
