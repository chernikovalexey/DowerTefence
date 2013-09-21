package com.twopeople.td.world;

/**
 * Created by Alexey
 * At 12:42 AM on 9/20/13
 */

public class Camera {
    private float screenWidth, screenHeight;
    private float worldWidth, worldHeight;
    private float x, y;
    private float targetX, targetY;

    public Camera(float screenWidth, float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public float getTargetX() {
        return this.targetX;
    }

    public void setTargetX(float x) {
        this.targetX = x;
    }

    public float getTargetY() {
        return this.targetY;
    }

    public void setTargetY(float z) {
        this.targetY = z;
    }

    public void update(int delta) {
        x += (targetX - x) * delta * .01;
        y += (targetY - y) * delta * .01;
    }

    public float getX(float x) {
        return x - this.x;
    }

    public float getZ(float z) {
        return z - this.y;
    }

    public float getScreenWidth() {
        return this.screenWidth;
    }

    public float getScreenHeight() {
        return this.screenHeight;
    }
}