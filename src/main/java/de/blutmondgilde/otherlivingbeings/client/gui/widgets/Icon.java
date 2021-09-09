package de.blutmondgilde.otherlivingbeings.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class Icon extends GuiComponent implements Widget {
    private final ResourceLocation resourceLocation;
    @Getter
    @Setter
    private int x, y, width, height;
    private float alpha;

    public Icon(ResourceLocation resourceLocation, int x, int y, int width, int height) {
        this(resourceLocation, x, y, width, height, 1F);
    }

    public Icon(ResourceLocation resourceLocation, int x, int y, int width, int height, float initialAlpha) {
        this.resourceLocation = resourceLocation;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.alpha = initialAlpha;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.enableTexture();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        RenderSystem.setShaderTexture(0, this.resourceLocation);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);

        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(519);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        blit(stack, this.x, this.y, 1, 0, 0, this.width, this.height, 16, 16);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1F);
        RenderSystem.disableBlend();
        RenderSystem.depthFunc(515);
        RenderSystem.disableDepthTest();
        RenderSystem.disableTexture();
    }

    public static void blit(PoseStack poseStack, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        innerBlit(poseStack, x, x + width, y, y + height, z, width, height, u, v, textureWidth, textureHeight);
    }

    private static void innerBlit(PoseStack poseStack, int x0, int x1, int y0, int y1, int z, int width, int height, float u0, float v0, int textureHeight, int textureWidth) {
        innerBlit(poseStack.last()
                .pose(), x0, x1, y0, y1, z, (u0 + 0.0F) / (float) textureHeight, (u0 + (float) width) / (float) textureHeight, (v0 + 0.0F) / (float) textureWidth, (v0 + (float) height) / (float) textureWidth);
    }

    private static void innerBlit(Matrix4f matrix, int x0, int x1, int y0, int y1, int z, float u0, float u1, float v0, float v1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix, (float) x0, (float) y1, (float) z).uv(u0, v1).endVertex();
        bufferbuilder.vertex(matrix, (float) x1, (float) y1, (float) z).uv(u1, v1).endVertex();
        bufferbuilder.vertex(matrix, (float) x1, (float) y0, (float) z).uv(u1, v0).endVertex();
        bufferbuilder.vertex(matrix, (float) x0, (float) y0, (float) z).uv(u0, v0).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
