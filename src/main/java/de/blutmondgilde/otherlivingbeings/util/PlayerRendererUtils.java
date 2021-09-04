package de.blutmondgilde.otherlivingbeings.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.extensions.IForgeBakedModel;
import net.minecraftforge.common.model.TransformationHelper;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.InvocationTargetException;

public class PlayerRendererUtils {
    private static final Matrix4f flipX;
    private static final Matrix3f flipXNormal;

    static {
        flipX = Matrix4f.createScaleMatrix(-1, 1, 1);
        flipXNormal = new Matrix3f(flipX);
    }

    public static EntityRendererProvider.Context getEntityRendererContext(RenderLayerParent<? extends LivingEntity, ? extends HumanoidModel<? extends LivingEntity>> renderer) {
        return new EntityRendererProvider.Context(((PlayerRenderer) renderer).entityRenderDispatcher, Minecraft.getInstance().getItemRenderer(), Minecraft.getInstance()
                .getResourceManager(), Minecraft.getInstance()
                .getEntityModels(), Minecraft.getInstance().font);
    }

    public static BakedModel handleCameraTransforms(ItemTransforms.TransformType transformType, boolean leftHandHackery, PoseStack stack, BakedModel bakedModel) {
        PoseStack forgeStack = new PoseStack();
        //bakedModel = bakedModel.handlePerspective(transformType, forgeStack);
        try {
            bakedModel = (BakedModel) ObfuscationReflectionHelper.findMethod(IForgeBakedModel.class, "self").invoke(bakedModel);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
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
        return bakedModel;
    }
}
