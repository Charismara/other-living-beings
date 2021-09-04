package de.blutmondgilde.otherlivingbeings.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@AllArgsConstructor
public class RenderArmorEvent extends Event {
    @Getter
    private final PoseStack poseStack;
    @Getter
    private final MultiBufferSource buffer;
    @Setter
    @Getter
    private HumanoidModel<AbstractClientPlayer> armorModel;
    @Getter
    private final EquipmentSlot equipmentSlot;
    @Getter
    private final AbstractClientPlayer player;

    @Cancelable
    public static class Pre extends RenderArmorEvent {

        public Pre(PoseStack poseStack, MultiBufferSource buffer, HumanoidModel<AbstractClientPlayer> armorModel, AbstractClientPlayer player, EquipmentSlot equipmentSlot) {
            super(poseStack, buffer, armorModel, equipmentSlot, player);
        }
    }

    public static class ApplyModifier extends RenderArmorEvent {
        public ApplyModifier(PoseStack poseStack, MultiBufferSource buffer, HumanoidModel<AbstractClientPlayer> armorModel, AbstractClientPlayer player, EquipmentSlot equipmentSlot) {
            super(poseStack, buffer, armorModel, equipmentSlot, player);
        }
    }

    public static class ResetModifier extends RenderArmorEvent {
        public ResetModifier(PoseStack poseStack, MultiBufferSource buffer, HumanoidModel<AbstractClientPlayer> armorModel, AbstractClientPlayer player, EquipmentSlot equipmentSlot) {
            super(poseStack, buffer, armorModel, equipmentSlot, player);
        }
    }
}
