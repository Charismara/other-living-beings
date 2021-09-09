package de.blutmondgilde.otherlivingbeings.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

import java.awt.*;

public class AnimatableText extends GuiComponent implements Widget {
    @Getter
    @Setter
    private int x, y, maxWith;
    @Setter
    private float alpha;
    @Getter
    private final Component text;
    private String currenText = "";
    private final Screen parent;
    @Setter
    private Color color;

    public AnimatableText(Screen parent, int x, int y, Component text, int maxWidth) {
        this(parent, x, y, 1F, text, maxWidth);
    }

    public AnimatableText(Screen parent, int x, int y, float initialAlpha, Component text, int maxWidth) {
        this.x = x;
        this.y = y;
        this.alpha = initialAlpha;
        this.text = text;
        this.parent = parent;
        this.color = new Color(1F, 1F, 1F, 1F);
        this.maxWith = maxWidth;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShaderColor(1F, 1F, 1F, this.alpha);
        Font font = this.parent.getMinecraft().font;
        font.draw(stack,
                Language.getInstance().getVisualOrder(FormattedText.composite(font.substrByWidth(this.text, this.maxWith))),
                this.x,
                this.y,
                this.color.getRGB());
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
    }
}
