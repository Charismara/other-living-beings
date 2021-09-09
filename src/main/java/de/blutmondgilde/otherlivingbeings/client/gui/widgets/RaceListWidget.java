package de.blutmondgilde.otherlivingbeings.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.client.gui.ChooseRaceGui;
import de.blutmondgilde.otherlivingbeings.client.gui.animation.Animation;
import de.blutmondgilde.otherlivingbeings.client.gui.animation.AnimationType;
import de.blutmondgilde.otherlivingbeings.util.BeingResourceLocation;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.fmlclient.gui.GuiUtils;

import java.awt.*;
import java.util.Objects;

public class RaceListWidget extends ObjectSelectionList<RaceListWidget.Entry> {
    private static final ResourceLocation BackgroundTexture = new BeingResourceLocation("textures/gui/race_list_background.png");
    private final int listWidth;
    private final ChooseRaceGui parent;
    private final Animation openingAnimation = new Animation(AnimationType.FadeIn, 50);

    public RaceListWidget(ChooseRaceGui parent, int listWidth, int top, int bottom, int left) {
        super(parent.getMinecraft(), listWidth, parent.height, top, bottom, parent.getFontRenderer().lineHeight * 2 + 8);
        this.listWidth = listWidth;
        this.parent = parent;
        this.refreshList();
        setRenderBackground(false);
        setRenderTopAndBottom(false);
        setLeftPos(left);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        int i = this.getScrollbarPosition();
        int j = i + 3;
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

        int j1 = this.getRowLeft();
        int k = this.y0 + 4 - (int) this.getScrollAmount();

        this.renderList(poseStack, j1, k, mouseX, mouseY, partialTicks);

        int k1 = this.getMaxScroll();
        if (k1 > 0) {
            RenderSystem.disableTexture();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            int l1 = (int) ((float) ((this.y1 - this.y0) * (this.y1 - this.y0)) / (float) this.getMaxPosition());
            l1 = Mth.clamp(l1, 32, this.y1 - this.y0 - 8);
            int i2 = (int) this.getScrollAmount() * (this.y1 - this.y0 - l1) / k1 + this.y0;
            if (i2 < this.y0) {
                i2 = this.y0;
            }

            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            bufferbuilder.vertex(i, this.y1, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.vertex(j, this.y1, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.vertex(j, this.y0, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.vertex(i, this.y0, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferbuilder.vertex(i, i2 + l1, 0.0D).color(128, 128, 128, 255).endVertex();
            bufferbuilder.vertex(j, i2 + l1, 0.0D).color(128, 128, 128, 255).endVertex();
            bufferbuilder.vertex(j, i2, 0.0D).color(128, 128, 128, 255).endVertex();
            bufferbuilder.vertex(i, i2, 0.0D).color(128, 128, 128, 255).endVertex();
            bufferbuilder.vertex(i, i2 + l1 - 1, 0.0D).color(192, 192, 192, 255).endVertex();
            bufferbuilder.vertex(j - 1, i2 + l1 - 1, 0.0D).color(192, 192, 192, 255).endVertex();
            bufferbuilder.vertex(j - 1, i2, 0.0D).color(192, 192, 192, 255).endVertex();
            bufferbuilder.vertex(i, i2, 0.0D).color(192, 192, 192, 255).endVertex();
            tesselator.end();
        }
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public void tick() {
        this.children().forEach(Entry::tick);
    }

    @Override
    protected void renderList(PoseStack stack, int x, int y, int mouseX, int mouseY, float partialTicks) {
        int entryCount = this.getItemCount();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        for (int currentEntry = 0; currentEntry < entryCount; currentEntry++) {
            renderEntry(currentEntry, tesselator, bufferbuilder, stack, x, y, mouseX, mouseY, partialTicks);
            if (!this.children().get(currentEntry).animation.isDone()) return; //Exit if the animation isn't done
        }
    }

    private void renderEntry(final int index, final Tesselator tesselator, final BufferBuilder bufferBuilder, final PoseStack stack, int x, int y, int mouseX, int mouseY, float partialTicks) {
        int rowTop = this.getRowTop(index);
        int rowBottom = this.getRowBottom(index);
        if (rowBottom >= this.y0 && rowTop <= this.y1) {
            int entryYPos = y + index * this.itemHeight + this.headerHeight;
            int entryHeight = this.itemHeight - 4;
            Entry entry = this.getEntry(index);
            int rowWidth = this.getScrollbarPosition();

            if (this.isSelectedItem(index)) {
                int xLeft = this.x0 + (this.width - rowWidth) / 2;

                RenderSystem.disableTexture();
                RenderSystem.setShader(GameRenderer::getPositionShader);
                float focusedColor = this.isFocused() ? 1.0F : 0.5F;
                RenderSystem.setShaderColor(focusedColor, focusedColor, focusedColor, 0.8F);

                bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
                bufferBuilder.vertex(xLeft, (entryYPos + entryHeight + 2), 0.0D).endVertex();
                bufferBuilder.vertex(rowWidth, (entryYPos + entryHeight + 2), 0.0D).endVertex();
                bufferBuilder.vertex(rowWidth, (entryYPos - 2), 0.0D).endVertex();
                bufferBuilder.vertex(xLeft, (entryYPos - 2), 0.0D).endVertex();
                tesselator.end();

                RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
                bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
                bufferBuilder.vertex(xLeft + 1, entryYPos + entryHeight + 1, 0.0D).endVertex();
                bufferBuilder.vertex(rowWidth - 1, entryYPos + entryHeight + 1, 0.0D).endVertex();
                bufferBuilder.vertex(rowWidth - 1, entryYPos - 1, 0.0D).endVertex();
                bufferBuilder.vertex(xLeft + 1, entryYPos - 1, 0.0D).endVertex();
                tesselator.end();

                RenderSystem.enableTexture();
            }

            this.hovered = this.isMouseOver(mouseX, mouseY) ? this.getEntryAtPosition(mouseX, mouseY) : null;
            entry.render(stack, index, rowTop, this.getRowLeft(), rowWidth, entryHeight, mouseX, mouseY, Objects.equals(this.hovered, entry), partialTicks);
        }
    }

    @Override
    protected int getScrollbarPosition() {
        return this.listWidth;
    }

    @Override
    public int getRowWidth() {
        return this.listWidth;
    }

    public void refreshList() {
        this.clearEntries();
        parent.buildRaceList(this::addEntry, livingBeing -> new Entry(livingBeing, this.parent));
        for (int i = 1; i < this.children().size(); i++) {
            Animation entryAnimation = this.children().get(i).animation;
            Animation parentAnimation = this.children().get(i - 1).animation;
            this.children().get(i).animation = new Animation(entryAnimation, parentAnimation);
        }
    }

    public class Entry extends ObjectSelectionList.Entry<Entry> {
        private static final Color hoverColor = new Color(0, 0, 0, 77);

        @Getter
        private final LivingBeing livingBeing;
        private final ChooseRaceGui parent;
        @Setter
        @Getter
        private Animation animation;
        private final Icon icon;
        private final AnimatableText text;

        public Entry(LivingBeing livingBeing, ChooseRaceGui parent) {
            this(livingBeing, parent, new Animation(AnimationType.FadeIn, 15));
        }

        public Entry(LivingBeing livingBeing, ChooseRaceGui parent, Animation animation) {
            this.livingBeing = livingBeing;
            this.parent = parent;
            this.icon = new Icon(livingBeing.getIcon(), 0, 0, 16, 16, 0F);
            this.animation = animation;
            this.text = new AnimatableText(this.parent, 0, 0, 0F, this.livingBeing.getName(), listWidth);
        }

        @Override
        public Component getNarration() {
            return new TranslatableComponent("narrator.select", livingBeing.getName());
        }

        @Override
        public void render(PoseStack stack, int entryId, int top, int left, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isHovered, float partialTicks) {
            if (top < RaceListWidget.this.y0 || top + entryHeight > RaceListWidget.this.y1) return;

            if (isHovered) {
                GuiUtils.drawGradientRect(stack.last().pose(), 0, left, top, entryWidth, top + entryHeight, hoverColor.getRGB(), hoverColor.getRGB());
            }

            this.icon.setX(left + 3);
            this.icon.setY(top + (entryHeight - this.icon.getHeight()) / 2);
            this.icon.render(stack, mouseX, mouseY, partialTicks);
            this.icon.setAlpha(this.animation.getProgression());

            Font font = this.parent.getFontRenderer();
            this.text.setX(this.icon.getX() + icon.getWidth() + 3);
            this.text.setY(top + (entryHeight - font.lineHeight) / 2);
            this.text.setAlpha(this.animation.getProgression());
            this.text.setAnimation(this.animation);
            this.text.render(stack, mouseX, mouseY, partialTicks);
        }

        @Override
        public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
            parent.setSelected(this);
            RaceListWidget.this.setSelected(this);
            return false;
        }

        public void tick() {
            this.animation.tick();
            this.text.tick();
        }
    }
}
