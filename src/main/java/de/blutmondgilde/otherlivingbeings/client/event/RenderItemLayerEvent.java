package de.blutmondgilde.otherlivingbeings.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

@AllArgsConstructor
public class RenderItemLayerEvent extends Event {
    @Getter
    private final Player player;
    @Getter
    private ItemStack itemStack;
    @Getter
    private final ItemTransforms.TransformType transformType;
    @Getter
    private final boolean leftHandHackery;
    @Getter
    private final PoseStack stack;
    @Getter
    private final MultiBufferSource bufferSource;
    @Getter
    private final int combinedLight;
    @Getter
    private final int combinedOverlay;
    @Getter
    private final BakedModel bakedModel;
    @Setter
    @Getter
    private ItemTransform itemTransform;
}
