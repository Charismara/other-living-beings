package de.blutmondgilde.otherlivingbeings.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import de.blutmondgilde.otherlivingbeings.api.capability.Capabilities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.RenderProperties;
import net.minecraftforge.common.model.TransformationHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {
    private static final Matrix4f flipX;
    private static final Matrix3f flipXNormal;

    static {
        flipX = Matrix4f.createScaleMatrix(-1, 1, 1);
        flipXNormal = new Matrix3f(flipX);
    }

    @Shadow
    @Final
    private ItemModelShaper itemModelShaper;

    @Shadow
    public abstract void renderModelLists(BakedModel p_115190_, ItemStack p_115191_, int p_115192_, int p_115193_, PoseStack p_115194_, VertexConsumer p_115195_);

    @Shadow
    public abstract BakedModel getModel(ItemStack p_174265_, @Nullable Level p_174266_, @Nullable LivingEntity p_174267_, int p_174268_);

    @Inject(method = "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V"), cancellable = true)
    private void onRenderStatic(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, boolean leftHandHackery, PoseStack stack, MultiBufferSource bufferSource, Level world, int combinedLight, int combinedOverlay, int p_174252_, CallbackInfo ci) {
        if (!transformType.firstPerson()) {
            if (livingEntity instanceof final Player player) {
                player.getCapability(Capabilities.BEING).ifPresent(beingCapability -> {
                    renderCustomItemLayer(player, itemStack, transformType, leftHandHackery, stack, bufferSource, combinedLight, combinedOverlay, this.getModel(itemStack, world, player, p_174252_));
                    ci.cancel();
                });
            }
        }
    }

    public void renderCustomItemLayer(final Player player, ItemStack itemStack, final ItemTransforms.TransformType transformType, final boolean leftHandHackery, final PoseStack stack, final MultiBufferSource bufferSource, final int combinedLight, final int combinedOverlay, BakedModel bakedModel) {
        if (!itemStack.isEmpty()) {
            stack.pushPose();
            boolean flag = transformType == ItemTransforms.TransformType.GUI || transformType == ItemTransforms.TransformType.GROUND || transformType == ItemTransforms.TransformType.FIXED;
            if (flag) {
                if (itemStack.is(Items.TRIDENT)) {
                    bakedModel = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
                } else if (itemStack.is(Items.SPYGLASS)) {
                    bakedModel = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("minecraft:spyglass#inventory"));
                }
            }

            //---- ForgeHooksClient.handleCameraTransforms ---- START
            PoseStack forgeStack = new PoseStack();
            //bakedModel = handlePerspective(bakedModel, transformType, forgeStack);
            ItemTransform itemTransform = bakedModel.getTransforms().getTransform(transformType);

            //final RenderItemLayerEvent renderItemLayerEvent = new RenderItemLayerEvent(player, itemStack, transformType, leftHandHackery, stack, bufferSource, combinedLight, combinedOverlay, bakedModel, itemTransform);
            //MinecraftForge.EVENT_BUS.post(renderItemLayerEvent);
            //itemTransform = renderItemLayerEvent.getItemTransform();

            Transformation tr = TransformationHelper.toTransformation(itemTransform);
            if (!tr.isIdentity()) {
                tr.push(forgeStack);
            }

            // If the stack is not empty, the code has added a matrix for us to use.
            if (!forgeStack.clear()) {
                // Apply the transformation to the real matrix stack, flipping for left hand
                Matrix4f tMat = forgeStack.last().pose();
                Matrix3f nMat = forgeStack.last().normal();
                if (leftHandHackery) {
                    tMat.multiplyBackward(flipX);
                    tMat.multiply(flipX);
                    nMat.multiplyBackward(flipXNormal);
                    nMat.mul(flipXNormal);
                }
                stack.last().pose().multiply(tMat);
                stack.last().normal().mul(nMat);
            }
            //---- ForgeHooksClient.handleCameraTransforms ---- END

            stack.translate(-0.5D, -0.5D, -0.5D);
            if (!bakedModel.isCustomRenderer() && (!itemStack.is(Items.TRIDENT) || flag)) {
                boolean fabulous;
                if (transformType != ItemTransforms.TransformType.GUI && !transformType.firstPerson() && itemStack.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem) itemStack.getItem()).getBlock();
                    fabulous = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
                } else {
                    fabulous = true;
                }
                if (bakedModel.isLayered()) {
                    //---- ForgeHooksClient.drawItemLayered Replacement ---- START
                    {
                        for (Pair<BakedModel, RenderType> layerModel : bakedModel.getLayerModels(itemStack, fabulous)) {
                            BakedModel layer = layerModel.getFirst();
                            RenderType rendertype = layerModel.getSecond();
                            ForgeHooksClient.setRenderLayer(rendertype); // neded for compatibility with MultiLayerModels
                            VertexConsumer ivertexbuilder;
                            if (fabulous) {
                                ivertexbuilder = ItemRenderer.getFoilBufferDirect(bufferSource, rendertype, true, itemStack.hasFoil());
                            } else {
                                ivertexbuilder = ItemRenderer.getFoilBuffer(bufferSource, rendertype, true, itemStack.hasFoil());
                            }
                            renderModelLists(layer, itemStack, combinedLight, combinedOverlay, stack, ivertexbuilder);
                        }
                        ForgeHooksClient.setRenderLayer(null);
                    }
                    //---- ForgeHooksClient.drawItemLayered Replacement ---- END
                } else {
                    RenderType rendertype = ItemBlockRenderTypes.getRenderType(itemStack, fabulous);
                    VertexConsumer vertexconsumer;
                    if (itemStack.is(Items.COMPASS) && itemStack.hasFoil()) {
                        stack.pushPose();
                        PoseStack.Pose posestack$pose = stack.last();
                        if (transformType == ItemTransforms.TransformType.GUI) {
                            posestack$pose.pose().multiply(0.5F);
                        } else if (transformType.firstPerson()) {
                            posestack$pose.pose().multiply(0.75F);
                        }

                        if (fabulous) {
                            vertexconsumer = ItemRenderer.getCompassFoilBufferDirect(bufferSource, rendertype, posestack$pose);
                        } else {
                            vertexconsumer = ItemRenderer.getCompassFoilBuffer(bufferSource, rendertype, posestack$pose);
                        }

                        stack.popPose();
                    } else if (fabulous) {
                        vertexconsumer = ItemRenderer.getFoilBufferDirect(bufferSource, rendertype, true, itemStack.hasFoil());
                    } else {
                        vertexconsumer = ItemRenderer.getFoilBuffer(bufferSource, rendertype, true, itemStack.hasFoil());
                    }

                    this.renderModelLists(bakedModel, itemStack, combinedLight, combinedOverlay, stack, vertexconsumer);
                }
            } else {
                RenderProperties.get(itemStack).getItemStackRenderer().renderByItem(itemStack, transformType, stack, bufferSource, combinedLight, combinedOverlay);
            }

            stack.popPose();
        }
    }
}
