package de.blutmondgilde.otherlivingbeings.api.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import de.blutmondgilde.otherlivingbeings.client.event.RenderItemInHandEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
public class CustomItemInHandLayer<T extends Player, M extends PlayerModel<T>> extends ItemInHandLayer<T, M> {
    @SuppressWarnings("unchecked")
    public CustomItemInHandLayer(PlayerRenderer renderer) {
        super((RenderLayerParent<T, M>) renderer);
    }

    @ParametersAreNonnullByDefault
    @Override
    public void render(PoseStack stack, MultiBufferSource bufferSource, int light, T player, float p_117208_, float p_117209_, float p_117210_, float p_117211_, float p_117212_, float p_117213_) {
        if (MinecraftForge.EVENT_BUS.post(new RenderItemInHandEvent.Pre(stack, bufferSource, player, (PlayerRenderer) this.renderer))) return;
        super.render(stack, bufferSource, light, player, p_117208_, p_117209_, p_117210_, p_117211_, p_117212_, p_117213_);
    }

    @ParametersAreNonnullByDefault
    @Override
    protected void renderArmWithItem(LivingEntity player, ItemStack itemStack, ItemTransforms.TransformType transformType, HumanoidArm arm, PoseStack stack, MultiBufferSource bufferSource, int light) {
        if (itemStack.is(Items.SPYGLASS) && player.getUseItem() == itemStack && player.swingTime == 0) {
            this.renderArmWithSpyglass(player, itemStack, arm, stack, bufferSource, light);
        } else {
            if (!itemStack.isEmpty()) {
                stack.pushPose();
                MinecraftForge.EVENT_BUS.post(new RenderItemInHandEvent.ApplyModifier(stack, bufferSource, (Player) player, itemStack, arm, (PlayerRenderer) this.renderer));
                this.getParentModel().translateToHand(arm, stack);
                stack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
                stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                boolean flag = arm == HumanoidArm.LEFT;
                stack.translate((flag ? -1d : 1d) / 16.0d, 0.125D, -0.625D);
                Minecraft.getInstance().getItemInHandRenderer().renderItem(player, itemStack, transformType, flag, stack, bufferSource, light);
                MinecraftForge.EVENT_BUS.post(new RenderItemInHandEvent.ResetModifier(stack, bufferSource, (Player) player, itemStack, arm, (PlayerRenderer) this.renderer));
                stack.popPose();
            }
        }
    }

    private void renderArmWithSpyglass(LivingEntity player, ItemStack itemStack, HumanoidArm arm, PoseStack stack, MultiBufferSource bufferSource, int light) {
        stack.pushPose();
        MinecraftForge.EVENT_BUS.post(new RenderItemInHandEvent.ApplyModifier(stack, bufferSource, (Player) player, itemStack, arm, (PlayerRenderer) this.renderer));
        ModelPart modelpart = this.getParentModel().getHead();
        float f = modelpart.xRot;
        modelpart.xRot = Mth.clamp(modelpart.xRot, (-(float) Math.PI / 6F), ((float) Math.PI / 2F));
        modelpart.translateAndRotate(stack);
        modelpart.xRot = f;
        CustomHeadLayer.translateToHead(stack, false);
        boolean flag = arm == HumanoidArm.LEFT;
        stack.translate((flag ? -2.5F : 2.5F) / 16.0F, -0.0625D, 0.0D);
        Minecraft.getInstance().getItemInHandRenderer().renderItem(player, itemStack, ItemTransforms.TransformType.HEAD, false, stack, bufferSource, light);
        MinecraftForge.EVENT_BUS.post(new RenderItemInHandEvent.ResetModifier(stack, bufferSource, (Player) player, itemStack, arm, (PlayerRenderer) this.renderer));
        stack.popPose();
    }
}
