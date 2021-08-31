package de.blutmondgilde.otherlivingbeings.api.client.model;

import de.blutmondgilde.otherlivingbeings.OtherLivingBeings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerModelReplacement {
    private final ModelPart head, hat, body, right_arm, left_arm, right_leg, left_leg, ear, cloak, left_sleeve, right_sleeve, left_pants, right_pants, jacket;

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

    public static class Builder {
        private final List<ModelPart> parts = new ArrayList<>();
        private int incInt = 0;

        public static Builder create() {
            return new Builder();
        }

        public Builder addModelPart(ModelPart part) {
            if (parts.size() < 14) {
                this.parts.add(part);
            } else {
                OtherLivingBeings.getLogger().warn("Tried to add too many children to a Model Replacement. Max Children are 14. Think about creating parent parts!");
            }
            return this;
        }

        public PlayerModelReplacement build() {
            final ModelPart emptyPart = new ModelPart(new ArrayList<>(), new HashMap<>());

            return switch (this.parts.size()) {
                default -> new PlayerModelReplacement(emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart);
                case 1 -> new PlayerModelReplacement(this.parts.get(inc()), emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart);
                case 2 -> new PlayerModelReplacement(this.parts.get(inc()), this.parts.get(inc()), emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart);
                case 3 -> new PlayerModelReplacement(this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart);
                case 4 -> new PlayerModelReplacement(this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart);
                case 5 -> new PlayerModelReplacement(this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart);
                case 6 -> new PlayerModelReplacement(this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart);
                case 7 -> new PlayerModelReplacement(this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart);
                case 8 -> new PlayerModelReplacement(this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), emptyPart, emptyPart, emptyPart, emptyPart, emptyPart, emptyPart);
                case 9 -> new PlayerModelReplacement(this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), emptyPart, emptyPart, emptyPart, emptyPart, emptyPart);
                case 10 -> new PlayerModelReplacement(this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), emptyPart, emptyPart, emptyPart, emptyPart);
                case 11 -> new PlayerModelReplacement(this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), emptyPart, emptyPart, emptyPart);
                case 12 -> new PlayerModelReplacement(this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), emptyPart, emptyPart);
                case 13 -> new PlayerModelReplacement(this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), emptyPart);
                case 14 -> new PlayerModelReplacement(this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()), this.parts.get(inc()));
            };
        }

        private int inc() {
            return incInt++;
        }
    }
}
