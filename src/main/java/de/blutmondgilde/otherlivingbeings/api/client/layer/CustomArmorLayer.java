package de.blutmondgilde.otherlivingbeings.api.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.blutmondgilde.otherlivingbeings.api.client.event.RenderArmorEvent;
import de.blutmondgilde.otherlivingbeings.util.PlayerRendererUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class CustomArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends HumanoidArmorLayer<T, M, A> {
    public CustomArmorLayer(PlayerRenderer renderer) {
        super((RenderLayerParent<T, M>) renderer,
                (A) new HumanoidModel(PlayerRendererUtils.getEntityRendererContext(renderer).bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                (A) new HumanoidModel(PlayerRendererUtils.getEntityRendererContext(renderer).bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)));
    }

    @Override
    public void renderArmorPiece(PoseStack stack, MultiBufferSource bufferSource, T player, EquipmentSlot equipmentSlot, int p_117123_, A armorModel) {
        //Exit method if event has been canceled
        final RenderArmorEvent renderArmorEvent = new RenderArmorEvent.Pre(stack, bufferSource, (HumanoidModel<AbstractClientPlayer>) armorModel, (AbstractClientPlayer) player, equipmentSlot);
        if (MinecraftForge.EVENT_BUS.post(renderArmorEvent)) return;
        armorModel = (A) renderArmorEvent.getArmorModel();
        ItemStack itemstack = player.getItemBySlot(equipmentSlot);
        if (itemstack.getItem() instanceof ArmorItem) {
            ArmorItem armoritem = (ArmorItem) itemstack.getItem();
            if (armoritem.getSlot() == equipmentSlot) {
                armorModel = getArmorModelHook(player, itemstack, equipmentSlot, armorModel);
                this.getParentModel().copyPropertiesTo(armorModel);
                this.setPartVisibility(armorModel, equipmentSlot);
                boolean flag1 = itemstack.hasFoil();
                if (armoritem instanceof DyeableLeatherItem) {
                    int colorInt = ((DyeableLeatherItem) armoritem).getColor(itemstack);
                    float red = (float) (colorInt >> 16 & 255) / 255.0F;
                    float green = (float) (colorInt >> 8 & 255) / 255.0F;
                    float blue = (float) (colorInt & 255) / 255.0F;

                    this.renderModel(stack, bufferSource, p_117123_, flag1, armorModel, red, green, blue, this.getArmorResource(player, itemstack, equipmentSlot, null), equipmentSlot, (AbstractClientPlayer) player);
                    this.renderModel(stack, bufferSource, p_117123_, flag1, armorModel, 1.0F, 1.0F, 1.0F, this.getArmorResource(player, itemstack, equipmentSlot, "overlay"), equipmentSlot, (AbstractClientPlayer) player);
                } else {
                    this.renderModel(stack, bufferSource, p_117123_, flag1, armorModel, 1.0F, 1.0F, 1.0F, this.getArmorResource(player, itemstack, equipmentSlot, null), equipmentSlot, (AbstractClientPlayer) player);
                }
            }
        }
    }

    private void renderModel(PoseStack stack, MultiBufferSource bufferSource, int p_117109_, boolean p_117111_, A armorModel, float red, float green, float blue, ResourceLocation armorResource, EquipmentSlot slot, AbstractClientPlayer player) {
        //Apply Armor Modifiers
        MinecraftForge.EVENT_BUS.post(new RenderArmorEvent.ApplyModifier(stack, bufferSource, (HumanoidModel<AbstractClientPlayer>) armorModel, player, slot));

        VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(bufferSource, RenderType.armorCutoutNoCull(armorResource), false, p_117111_);
        armorModel.renderToBuffer(stack, vertexconsumer, p_117109_, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);

        //Reset to default
        MinecraftForge.EVENT_BUS.post(new RenderArmorEvent.ResetModifier(stack, bufferSource, (HumanoidModel<AbstractClientPlayer>) armorModel, player, slot));
    }
}
