package com.twopeople.td.entity;

import com.twopeople.td.world.Camera;
import com.twopeople.td.world.EntityVault;
import com.twopeople.td.world.World;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created by Alexey
 * At 2:41 PM on 9/20/13
 */

public class Entity {
    protected World world;

    private float x, y, z;
    private float width, height;
    private int cellX, cellY;

    private float friction = 1f;
    private Vector2f direction = new Vector2f(0f, 0f);
    private Vector3f velocity = new Vector3f(0f, 0f, 0f);

    public Entity(World world, float x, float y, float z, float width, float height) {
        this.world = world;
        this.x = x;
        this.z = z;
        this.width = width;
        this.height = height;
    }

    public void update(GameContainer gameContainer, int delta, EntityVault vault) {
        direction.set(0, 0);
    }

    public void render(GameContainer gameContainer, Camera camera, Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(camera.getX(x), camera.getZ(z), width, height);
    }

    public void moveInertly(int delta, EntityVault vault) {
        float speed = 1f;
        float accelerationX = -velocity.x * friction + direction.x * speed;
        float accelerationZ = -velocity.z * friction + direction.y * speed;

        velocity.x = accelerationX * delta * 0.025f;
        velocity.z = accelerationZ * delta * 0.025f;

        ArrayList<Entity> entities;
        x += velocity.x;
        if (vault.getCollidingEntities(this).size() > 0) {
            x -= velocity.x;
        }
        z += velocity.z;
        if (vault.getCollidingEntities(this).size() > 0) {
            z -= velocity.z;
        }

        vault.move(this);
    }

    public void move(float dx, float dz) {
        direction = (new Vector2f(dx, dz)).normalise();
    }

    public Shape getBounds() {
        return new Rectangle(getX(), getZ(), getWidth(), getHeight());
    }

    public Shape[] getBBSkeleton() {
        return new Shape[]{getBounds()};
    }

    public boolean isCollidingWith(Entity entity) {
        for (Shape shape : entity.getBBSkeleton()) {
            if (isCollidingWith(shape)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCollidingWith(Shape shape) {
        for (Shape shape1 : getBBSkeleton()) {
            if (shape1.intersects(shape)) {
                return true;
            }
        }
        return false;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float z) {
        this.z = z;
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

    public void setDirectionX(float dx) {
        direction.x = dx;
    }

    public void setDirectionY(float dy) {
        direction.y = dy;
    }
}