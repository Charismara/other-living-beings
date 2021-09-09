package de.blutmondgilde.otherlivingbeings.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.blutmondgilde.otherlivingbeings.client.gui.animation.Animation;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.TextComponent;

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
    @Setter
    @Getter
    private Animation animation;
    private final Font font;

    public AnimatableText(Screen parent, int x, int y, Component text, int maxWidth) {
        this(parent, x, y, 1F, text, maxWidth);
    }

    public AnimatableText(Screen parent, int x, int y, float initialAlpha, Component text, int maxWidth) {
        this.x = x;
        this.y = y;
        this.alpha = initialAlpha;
        this.text = text;
        this.parent = parent;
        this.color = new Color(1F, 1F, 1F);
        this.maxWith = maxWidth - 6;
        this.animation = Animation.None;
        this.font = parent.getMinecraft().font;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        Component resultText = new TextComponent(getTextByProgression());

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);

        font.draw(stack, Language.getInstance().getVisualOrder(resultText), this.x, this.y, this.color.getRGB());

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1F);
        RenderSystem.disableBlend();
    }

    public void tick() {
        this.animation.tick();
    }

    private String getTextByProgression() {
        String text = FormattedText.composite(this.parent.getMinecraft().font.substrByWidth(this.text, this.maxWith)).getString();
        int size = (int) text.chars().count();

        text = text.substring(0, Math.min(Math.round(size * this.animation.getProgression()), size));

        return text;
    }
}
