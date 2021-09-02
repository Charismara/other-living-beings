package de.blutmondgilde.otherlivingbeings.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmallSlimeModel extends PlayerModel<AbstractClientPlayer> {
    private final ModelPart root;

    public SmallSlimeModel(ModelPart parent) {
        super(parent, false);
        this.root = parent;
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer vertexConsumer, int p_102036_, int p_102037_, float p_102038_, float p_102039_, float p_102040_, float p_102041_) {
        //Default Slime Offset
        stack.translate(0, 1, 0);
        headParts().forEach(part -> {
            part.xRot = 0;
            part.zRot = 0;
        });
        body.yRot = head.yRot;
        super.renderToBuffer(stack, vertexConsumer, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);
        stack.translate(0, 0, 0);
    }

    public static LayerDefinition createLeftArm() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(48, 16)
                .addBox(-3, 0, -3, 3, 0, 3), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public static LayerDefinition createRightArm() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(48, 16)
                .addBox(3, 0, -3, 3, 0, 3), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public static LayerDefinition createHat() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-4.0F, 0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public static LayerDefinition createHead() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("inner_cube", CubeListBuilder.create()
                .texOffs(0, 16)
                .addBox(-3.0F, 1, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_eye", CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-3.25F, 2.0F, -3.5F, 2.0F, 2.0F, 2.0F), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_eye", CubeListBuilder.create()
                .texOffs(32, 4)
                .addBox(1.25F, 2.0F, -3.5F, 2.0F, 2.0F, 2.0F), PartPose.ZERO);
        partdefinition.addOrReplaceChild("mouth", CubeListBuilder.create()
                .texOffs(32, 8)
                .addBox(0.0F, 5.0F, -3.5F, 1.0F, 1.0F, 1.0F), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public static LayerDefinition createBody() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("outer_cube", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-4.0F, 0, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public ModelPart root() {
        return this.root;
    }
}
