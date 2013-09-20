package com.twopeople.td.entity;

import com.twopeople.td.world.Camera;
import com.twopeople.td.world.EntityVault;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * Created by Alexey
 * At 2:41 PM on 9/20/13
 */

public class Entity {
    private float x, y, z;
    private float width, height;
    private int cellX, cellY;

    public Entity(float x, float y, float z, float width, float height) {
        this.x = x;
        this.z = z;
        this.width = width;
        this.height = height;
    }

    public void update(GameContainer gameContainer, int delta, EntityVault vault) {
        Input input = gameContainer.getInput();
        float speed = 1f;
        if (input.isKeyDown(Input.KEY_A)) { x -= speed; }
        if (input.isKeyDown(Input.KEY_D)) { x += speed; }
        if (input.isKeyDown(Input.KEY_W)) { z -= speed; }
        if (input.isKeyDown(Input.KEY_S)) { z += speed; }
        vault.move(this);
    }

    public void render(GameContainer gameContainer, Camera camera, Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(camera.getX(x), camera.getZ(z), width, height);
    }

    public Shape getBounds() {
        return new Rectangle(getX(), getZ(), getWidth(), getHeight());
    }

    public Shape[] getBBSkeleton() {
        return new Shape[]{getBounds()};
    }

    public boolean isCollidingWith(Entity entity) {
        for (Shape shape1 : entity.getBBSkeleton()) {
            for (Shape shape2 : this.getBBSkeleton()) {
                if (shape1.intersects(shape2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public float getX() {
        return this.x;
    }

    public float getZ() {
        return this.z;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getCellX() {
        return cellX;
    }

    public void setCellX(int cellX) {
        this.cellX = cellX;
    }

    public int getCellY() {
        return cellY;
    }

    public void setCellY(int cellY) {
        this.cellY = cellY;
    }
}