package de.blutmondgilde.otherlivingbeings.api.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@AllArgsConstructor
public class RenderItemInHandEvent extends Event {
    @Getter
    private final PoseStack poseStack;
    @Getter
    private final MultiBufferSource buffer;
    @Getter
    private final Player player;

    @Cancelable
    public static class Pre extends RenderItemInHandEvent {
        public Pre(PoseStack poseStack, MultiBufferSource buffer, Player player) {
            super(poseStack, buffer, player);
        }
    }

    public static class ApplyModifier extends RenderItemInHandEvent {
        @Getter
        @Setter
        private ItemStack itemStack;
        @Getter
        private final HumanoidArm arm;

        public ApplyModifier(PoseStack stack, MultiBufferSource buffer, Player player, ItemStack itemStack, HumanoidArm arm) {
            super(stack, buffer, player);
            this.itemStack = itemStack;
            this.arm = arm;
        }
    }

    public static class ResetModifier extends RenderItemInHandEvent {
        @Getter
        @Setter
        private ItemStack itemStack;
        @Getter
        private final HumanoidArm arm;

        public ResetModifier(PoseStack poseStack, MultiBufferSource buffer, Player player, ItemStack itemStack, HumanoidArm arm) {
            super(poseStack, buffer, player);
            this.itemStack = itemStack;
            this.arm = arm;
        }
    }
}
