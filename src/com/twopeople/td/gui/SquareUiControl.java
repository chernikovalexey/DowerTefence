package com.twopeople.td.gui;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * Created by Alexey
 * At 3:19 PM on 9/21/13
 */

public class SquareUiControl {
    private float x, y;
    private int width, height;

    public SquareUiControl(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Shape getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isOver(float x, float y) {
        return getBounds().intersects(new Rectangle(x, y, 1, 1));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}