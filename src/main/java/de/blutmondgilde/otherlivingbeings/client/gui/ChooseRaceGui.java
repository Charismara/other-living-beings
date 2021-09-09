package de.blutmondgilde.otherlivingbeings.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import de.blutmondgilde.otherlivingbeings.api.livingbeings.LivingBeing;
import de.blutmondgilde.otherlivingbeings.client.gui.widgets.RaceListWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.client.gui.ScrollPanel;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fmllegacy.common.registry.GameRegistry;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ChooseRaceGui extends Screen {
    private static final int PADDING = 6;
    private final List<LivingBeing> livingBeings;
    private RaceListWidget raceList;
    private RaceListWidget.Entry selected;
    public int listWidth;
    private RaceInfoPanel raceInfo;

    public ChooseRaceGui() {
        super(new TranslatableComponent(OtherLivingBeings.MOD_ID + ".gui.chooserace.title"));
        this.livingBeings = GameRegistry.findRegistry(LivingBeing.class).getValues().stream().toList();
    }

    @Override
    protected void init() {
        final int fullButtonHeight = PADDING + 20 + PADDING;
        listWidth = Math.max(Math.min(this.width / 3, 200), 100);
        final int raceInfoWidth = this.width - this.listWidth - (PADDING * 3);

        this.raceList = this.addRenderableWidget(new RaceListWidget(this, listWidth, PADDING, this.height - PADDING));
        this.raceInfo = this.addRenderableWidget(new RaceInfoPanel(this.minecraft, raceInfoWidth, this.height - PADDING, PADDING));
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.render(stack, mouseX, mouseY, partialTicks);

        this.raceList.render(stack, mouseX, mouseY, partialTicks);
        if (this.raceInfo != null) this.raceInfo.render(stack, mouseX, mouseY, partialTicks);
        super.render(stack, mouseX, mouseY, partialTicks);
    }

    public Font getFontRenderer() {
        return this.font;
    }

    public <T extends ObjectSelectionList.Entry<T>> void buildRaceList(Consumer<T> raceListViewConsumer, Function<LivingBeing, T> newEntry) {
        this.livingBeings.forEach(livingBeing -> raceListViewConsumer.accept(newEntry.apply(livingBeing)));
    }

    public void setSelected(RaceListWidget.Entry entry) {
        this.selected = entry;
        updateCache();
    }

    @Override
    public void tick() {
        this.raceList.setSelected(this.selected);
        this.raceList.tick();
        if (this.selected != null) {
            this.selected = this.raceList.children().stream().filter(entry -> entry.getLivingBeing() == selected.getLivingBeing()).findFirst().orElse(null);
            updateCache();
        }
    }

    class RaceInfoPanel extends ScrollPanel {
        private List<FormattedCharSequence> lines = Collections.emptyList();
        private final Minecraft client;
        private final int barLeft;
        private final int barWidth = 3;

        public RaceInfoPanel(Minecraft client, int width, int height, int top) {
            super(client, width, height, top, raceList.getRight() + PADDING);
            this.client = client;
            this.barLeft = this.left + this.width - barWidth;
        }

        void setInfo(List<String> lines) {
            this.lines = resizeContent(lines);
        }

        void clearInfo() {
            this.lines = Collections.emptyList();
        }

        private List<FormattedCharSequence> resizeContent(List<String> lines) {
            List<FormattedCharSequence> ret = new ArrayList<>();
            for (String line : lines) {
                if (line == null) {
                    ret.add(null);
                    continue;
                }

                Component chat = ForgeHooks.newChatWithLinks(line, false);
                int maxTextLength = this.width - 12;
                if (maxTextLength >= 0) {
                    ret.addAll(Language.getInstance().getVisualOrder(font.getSplitter().splitLines(chat, maxTextLength, Style.EMPTY)));
                }
            }
            return ret;
        }

        @Override
        protected int getContentHeight() {
            int height = 50;
            height += (lines.size() * font.lineHeight);
            if (height < this.bottom - this.top - 8)
                height = this.bottom - this.top - 8;
            return height;
        }

        @Override
        public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
            this.drawBackground();

            Tesselator tess = Tesselator.getInstance();
            BufferBuilder worldr = tess.getBuilder();

            double scale = client.getWindow().getGuiScale();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor((int) (left * scale), (int) (client.getWindow().getHeight() - (bottom * scale)),
                    (int) (width * scale), (int) (height * scale));

            int baseY = this.top + border - (int) this.scrollDistance;
            this.drawPanel(matrix, right, baseY, tess, mouseX, mouseY);

            RenderSystem.disableDepthTest();

            int extraHeight = (this.getContentHeight() + border) - height;
            if (extraHeight > 0) {
                int barHeight = getBarHeight();

                int barTop = (int) this.scrollDistance * (height - barHeight) / extraHeight + this.top;
                if (barTop < this.top) {
                    barTop = this.top;
                }

                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                RenderSystem.disableTexture();
                worldr.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                worldr.vertex(barLeft, this.bottom, 0.0D).color(0x00, 0x00, 0x00, 0xFF).endVertex();
                worldr.vertex(barLeft + barWidth, this.bottom, 0.0D).color(0x00, 0x00, 0x00, 0xFF).endVertex();
                worldr.vertex(barLeft + barWidth, this.top, 0.0D).color(0x00, 0x00, 0x00, 0xFF).endVertex();
                worldr.vertex(barLeft, this.top, 0.0D).color(0x00, 0x00, 0x00, 0xFF).endVertex();
                tess.end();
                worldr.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                worldr.vertex(barLeft, barTop + barHeight, 0.0D).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                worldr.vertex(barLeft + barWidth, barTop + barHeight, 0.0D).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                worldr.vertex(barLeft + barWidth, barTop, 0.0D).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                worldr.vertex(barLeft, barTop, 0.0D).color(0x80, 0x80, 0x80, 0xFF).endVertex();
                tess.end();
                worldr.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                worldr.vertex(barLeft, barTop + barHeight - 1, 0.0D).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
                worldr.vertex(barLeft + barWidth - 1, barTop + barHeight - 1, 0.0D).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
                worldr.vertex(barLeft + barWidth - 1, barTop, 0.0D).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
                worldr.vertex(barLeft, barTop, 0.0D).color(0xC0, 0xC0, 0xC0, 0xFF).endVertex();
                tess.end();
            }

            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }

        @Override
        protected void drawPanel(PoseStack mStack, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
            for (FormattedCharSequence line : lines) {
                if (line != null) {
                    RenderSystem.enableBlend();
                    ChooseRaceGui.this.font.drawShadow(mStack, line, left + PADDING, relativeY, 0xFFFFFF);
                    RenderSystem.disableBlend();
                }
                relativeY += font.lineHeight;
            }

            final Style component = findTextLine(mouseX, mouseY);

            if (component != null) {
                ChooseRaceGui.this.renderComponentHoverEffect(mStack, component, mouseX, mouseY);
            }
        }

        private int getBarHeight() {
            int barHeight = (height * height) / this.getContentHeight();

            if (barHeight < 32) barHeight = 32;

            if (barHeight > height - border * 2)
                barHeight = height - border * 2;

            return barHeight;
        }

        private Style findTextLine(final int mouseX, final int mouseY) {
            if (!isMouseOver(mouseX, mouseY)) return null;

            double offset = (mouseY - top) + border + scrollDistance + 1;
            if (offset <= 0) return null;

            int lineIdx = (int) (offset / font.lineHeight);
            if (lineIdx >= lines.size() || lineIdx < 1) return null;

            FormattedCharSequence line = lines.get(lineIdx - 1);
            if (line != null) {
                return font.getSplitter().componentStyleAtWidth(line, mouseX - left - border);
            }
            return null;
        }

        @Override
        public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
            final Style component = findTextLine((int) mouseX, (int) mouseY);
            if (component != null) {
                ChooseRaceGui.this.handleComponentClicked(component);
                return true;
            }
            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        protected void drawBackground() {}

        @Override
        public NarrationPriority narrationPriority() {
            return NarrationPriority.NONE;
        }

        @Override
        public void updateNarration(NarrationElementOutput p_169152_) {}
    }

    private void updateCache() {
        if (selected == null) {
            this.raceInfo.clearInfo();
            return;
        }

        LivingBeing selectedLivingBeing = selected.getLivingBeing();
        List<String> lines = new ArrayList<>();
        lines.add(selectedLivingBeing.getName().getString());

        raceInfo.setInfo(lines);
    }

    @Override
    public void resize(Minecraft mc, int width, int height) {
        RaceListWidget.Entry selected = this.selected;
        this.init(mc, width, height);
        this.selected = selected;
        updateCache();
    }
}
