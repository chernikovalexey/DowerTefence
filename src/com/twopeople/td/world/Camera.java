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

    public void setX(float x) {
        this.targetX = x;
    }

    public void setY(float z) {
        this.targetY = z;
    }

    public void update(int delta) {
        x += (targetX - x) * delta * .01;
        y += (targetY - y) * delta * .01;
    }

    public float getX(float x) {
        return this.x + x;
    }

    public float getZ(float z) {
        return this.y + z;
    }
}