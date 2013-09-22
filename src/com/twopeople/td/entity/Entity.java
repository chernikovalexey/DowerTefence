package com.twopeople.td.entity;

import com.twopeople.td.world.Camera;
import com.twopeople.td.world.EntityVault;
import com.twopeople.td.world.World;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;

/**
 * Created by Alexey
 * At 2:41 PM on 9/20/13
 */

public class Entity {
    private static int globalId = -1;

    protected World world;

    private boolean isToRemove = false;
    private boolean isImmortal = true;

    private int id;
    private int owner;
    private int health, maxHealth;
    private float speed = 0f;
    private float x, y, z;
    private float width, height;
    private int cellX, cellY;

    private IType intersectionType = IType.All;

    private Vector2f direction = new Vector2f(0f, 0f);
    private Vector3f velocity = new Vector3f(0f, 0f, 0f);

    private boolean isAnimated = false;
    private int currentAnimationState = 0;
    private Animation[] animations = new Animation[8];

    private Vector2f[] directions = new Vector2f[]{
            new Vector2f(-90), new Vector2f(0),
            new Vector2f(90), new Vector2f(180),
            new Vector2f(-135), new Vector2f(-45),
            new Vector2f(135), new Vector2f(45)
    };

    public enum EntityType {
        Tile, Interior, Mob, Tower, Bullet
    }

    public enum IType {
        All, None, NotOwner
    }

    public Entity(World world, float x, float y, float z, float width, float height, int id) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        setId(id);
    }

    public Entity(World world, float x, float y, float z, float width, float height) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.id = ++globalId;
    }

    public void update(GameContainer gameContainer, int delta, EntityVault vault) {
        updateDirection(direction.x, direction.y);
        direction.set(0, 0);
    }

    public void render(GameContainer gameContainer, Camera camera, Graphics g) {
        if (isAnimated) {
            Animation animation = animations[currentAnimationState];
            animation.draw(camera.getX(x), camera.getZ(z));
        } else {
            g.setColor(Color.cyan);
            g.fillRect(camera.getX(x), camera.getZ(z), width, height);
        }
    }

    private void updateDirection(float dx, float dy) {
        setDirectionX(dx);
        setDirectionY(dy);

        float theta;
        float minTheta = 360f;
        for (int i = 0, len = directions.length; i < len; ++i) {
            theta = Vector3f.angle(new Vector3f(direction.x, 0, direction.y), new Vector3f(directions[i].x, 0, directions[i].y));
            if (theta < minTheta) {
                minTheta = theta;
                currentAnimationState = i;
            }
        }
    }

    public void updateDirectionToPoint(float dx, float dz) {
        Vector2f newDirection = (new Vector2f(dx - getX(), dz - getZ())).normalise();
        updateDirection(newDirection.x, newDirection.y);
    }

    public void updateDirectionToEntity(Entity entity) {
        updateDirectionToPoint(entity.getX(), entity.getZ());
    }

    public void moveInertly(int delta, EntityVault vault) {
        float friction = 0.00001f;
        float _speed = getSpeed() / 10f;
        float accelerationX = -velocity.x * friction + direction.x * _speed;
        float accelerationZ = -velocity.z * friction + direction.y * _speed;

        velocity.x = accelerationX * delta;
        velocity.z = accelerationZ * delta;

        ArrayList<Entity> entities;

        x += velocity.x;
        if (intersectionType != IType.None) {
            entities = vault.getCollidingEntities(this, intersectionType);
            if (entities.size() > 0) {
                x -= velocity.x;
                onCollide(entities);
            }
        }

        z += velocity.z;
        if (intersectionType != IType.None) {
            entities = vault.getCollidingEntities(this, intersectionType);
            if (entities.size() > 0) {
                z -= velocity.z;
                onCollide(entities);
            }
        }

        vault.move(this);
    }

    public void move(float dx, float dz) {
        direction = (new Vector2f(dx - x, dz - z)).normalise();
    }

    protected void setAnimation(SpriteSheet sprite) {
        this.isAnimated = true;

        Image[] imagesUp = new Image[4];
        Image[] imagesDown = new Image[4];
        Image[] imagesLeft = new Image[4];
        Image[] imagesRight = new Image[4];
        Image[] imagesUpLeft = new Image[4];
        Image[] imagesUpRight = new Image[4];
        Image[] imagesDownLeft = new Image[4];
        Image[] imagesDownRight = new Image[4];

        for (int i = 0; i < 4; ++i) {
            imagesUp[i] = sprite.getSprite(i, 0);
            imagesDown[i] = sprite.getSprite(i, 4);
            imagesLeft[i] = sprite.getSprite(i, 2);
            imagesRight[i] = imagesLeft[i].getFlippedCopy(true, false);
            imagesUpLeft[i] = sprite.getSprite(i, 1);
            imagesUpRight[i] = imagesUpLeft[i].getFlippedCopy(true, false);
            imagesDownLeft[i] = sprite.getSprite(i, 3);
            imagesDownRight[i] = imagesDownLeft[i].getFlippedCopy(true, false);
        }

        animations[0] = new Animation(imagesUp, 200, true);
        animations[1] = new Animation(imagesRight, 200, true);
        animations[2] = new Animation(imagesDown, 200, true);
        animations[3] = new Animation(imagesLeft, 200, true);
        animations[4] = new Animation(imagesUpLeft, 200, true);
        animations[5] = new Animation(imagesUpRight, 200, true);
        animations[6] = new Animation(imagesDownLeft, 200, true);
        animations[7] = new Animation(imagesDownRight, 200, true);
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
            if (shape.intersects(shape1)) {
                return true;
            }
        }
        return false;
    }

    public void onHit(Entity entity) {
    }

    public void onCollide(ArrayList<Entity> entities) {
    }

    public void hurt(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    public void heal(int health) {
        health += health;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    protected void remove() {
        this.isToRemove = true;
    }

    public boolean shouldRemove() {
        return this.isToRemove;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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

    public float getDirectionX() {
        return direction.x;
    }

    public void setDirectionX(float dx) {
        direction.x = dx;
    }

    public float getDirectionY() {
        return direction.y;
    }

    public void setDirectionY(float dy) {
        direction.y = dy;
    }

    public int getId() {
        return id;
    }

    // Very low-level
    public void setId(int id) {
        this.id = id;
        globalId = id;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        this.maxHealth = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setImmortalness(boolean i) {
        this.isImmortal = i;
    }

    public boolean IsImmortal() {
        return this.isImmortal;
    }

    public void setIntersectionType(IType type) {
        this.intersectionType = type;
    }

    public int getOwner() {
        return this.owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public boolean isOwnerOf(Entity entity) {
        return entity.getOwner() == id;
    }

    public EntityType getType() {
        return EntityType.Interior;
    }

    public boolean updatesOnEachTick() {
        return false;
    }

    public float getDistanceTo(Entity entity) {
        return (float) Math.sqrt(Math.pow(entity.getX() - getX(), 2) + Math.pow(entity.getY() - getY(), 2) + Math.pow(entity.getZ() - getZ(), 2));
    }
}