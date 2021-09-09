package de.blutmondgilde.otherlivingbeings.client.gui.animation;

import lombok.Getter;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class Animation {
    public static final Animation None = new Animation(AnimationType.None, 0);
    @Getter
    private final AnimationType animationType;
    @Getter
    private final int totalTicks;
    @Getter
    private int remainingTicks;
    @Getter
    private int currentTick = 0;
    private Optional<Animation> waitingFor;

    public Animation(final AnimationType animationType, final int ticks, final Optional<Animation> waitingFor) {
        this.animationType = animationType;
        this.totalTicks = ticks;
        this.remainingTicks = ticks;
        this.waitingFor = waitingFor;
    }

    public Animation(final AnimationType animationType, final int ticks) {
        this(animationType, ticks, Optional.empty());
    }

    public Animation(Animation entryAnimation, Animation parentAnimation) {
        this(entryAnimation.getAnimationType(), entryAnimation.getTotalTicks(), Optional.of(parentAnimation));
    }

    public void tick() {
        if (this.remainingTicks == 0) return;

        final AtomicBoolean waitingForAnimation = new AtomicBoolean(false);
        waitingFor.ifPresent(animation -> {
            if (animation.isDone()) {
                this.waitingFor = Optional.empty();
            } else {
                waitingForAnimation.set(true);
            }
        });

        if (waitingForAnimation.get()) return;

        this.currentTick++;
        this.remainingTicks--;
    }

    public float getProgression() {
        if (totalTicks == currentTick) return 1F;
        return Math.min(1F / this.totalTicks * currentTick, 1F);
    }

    public boolean isDone() {
        return this.remainingTicks == 0;
    }
}
