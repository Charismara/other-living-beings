package de.blutmondgilde.otherlivingbeings.api.client.model;

import lombok.RequiredArgsConstructor;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
@RequiredArgsConstructor
public class PlayerModelReplacement {
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart body;
    private final ModelPart right_arm;
    private final ModelPart left_arm;

    public PlayerModelReplacement(LayerDefinition head, LayerDefinition hat, LayerDefinition body, LayerDefinition right_arm, LayerDefinition left_arm, LayerDefinition right_leg, LayerDefinition left_leg, LayerDefinition ear, LayerDefinition cloak, LayerDefinition left_sleeve, LayerDefinition right_sleeve, LayerDefinition left_pants, LayerDefinition right_pants, LayerDefinition jacket) {
        this.head = head.bakeRoot();
        this.hat = hat.bakeRoot();
        this.body = body.bakeRoot();
        this.right_arm = right_arm.bakeRoot();
        this.left_arm = left_arm.bakeRoot();
        this.right_leg = right_leg.bakeRoot();
        this.left_leg = left_leg.bakeRoot();
        this.ear = ear.bakeRoot();
        this.cloak = cloak.bakeRoot();
        this.left_sleeve = left_sleeve.bakeRoot();
        this.right_sleeve = right_sleeve.bakeRoot();
        this.left_pants = left_pants.bakeRoot();
        this.right_pants = right_pants.bakeRoot();
        this.jacket = jacket.bakeRoot();
    }

    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart ear;
    private final ModelPart cloak;
    private final ModelPart left_sleeve;
    private final ModelPart right_sleeve;
    private final ModelPart left_pants;
    private final ModelPart right_pants;
    private final ModelPart jacket;

    public ModelPart toModelPart() {
        final ArrayList<ModelPart.Cube> cubes = new ArrayList<>();
        final HashMap<String, ModelPart> children = new HashMap<>();

        children.put("head", head);
        children.put("hat", hat);
        children.put("body", body);
        children.put("right_arm", right_arm);
        children.put("left_arm", left_arm);
        children.put("right_leg", right_leg);
        children.put("left_leg", left_leg);
        children.put("ear", ear);
        children.put("cloak", cloak);
        children.put("left_sleeve", left_sleeve);
        children.put("right_sleeve", right_sleeve);
        children.put("left_pants", left_pants);
        children.put("right_pants", right_pants);
        children.put("jacket", jacket);
        return new ModelPart(cubes, children);
    }
}
