package com.twopeople.td.world;

/**
 * Created by podko_000
 * At 3:12 on 22.09.13
 */

public class Area {
    private float x, y, width, height;
    private int wave, id;
    private boolean isOpened = false;

    public Area(World world, float x, float y, float width, float height, int wave, int id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.wave = wave;
        this.id = id;
        this.wave = wave;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public int getWave() {
        return wave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}