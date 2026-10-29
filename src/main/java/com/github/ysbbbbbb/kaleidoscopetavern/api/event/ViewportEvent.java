package com.github.ysbbbbbb.kaleidoscopetavern.api.event;

import com.github.ysbbbbbb.kaleidoscopetavern.util.event.IKTEvent;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;

public abstract class ViewportEvent implements IKTEvent {
    private final GameRenderer renderer;
    private final Camera camera;
    private final double partialTick;

    public ViewportEvent(GameRenderer renderer, Camera camera, double partialTick) {
        this.renderer = renderer;
        this.camera = camera;
        this.partialTick = partialTick;
    }
    public GameRenderer getRenderer() {
        return renderer;
    }

    public Camera getCamera() {
        return camera;
    }

    public double getPartialTick() {
        return partialTick;
    }

    public static class ComputeCameraAngles extends ViewportEvent {
        private float yaw;
        private float pitch;
        private float roll;

        public ComputeCameraAngles(Camera camera, double renderPartialTicks, float yaw, float pitch, float roll) {
            super(Minecraft.getInstance().gameRenderer, camera, renderPartialTicks);
            this.setYaw(yaw);
            this.setPitch(pitch);
            this.setRoll(roll);
        }

        public float getPitch() {
            return pitch;
        }

        public float getYaw() {
            return yaw;
        }

        public float getRoll() {
            return roll;
        }

        private void setYaw(float yaw) {
            this.yaw = yaw;
        }

        private void setPitch(float pitch) {
            this.pitch = pitch;
        }

        public void setRoll(float roll) {
            this.roll = roll;
        }
    }
}
