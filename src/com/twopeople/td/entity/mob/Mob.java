package com.twopeople.td.entity.mob;

import com.twopeople.td.entity.Entity;
import com.twopeople.td.world.EntityVault;
import com.twopeople.td.world.Path;
import com.twopeople.td.world.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Alexey
 * At 11:37 AM on 9/21/13
 */

public class Mob extends Entity {
    private float speed, damage;
    private int health, reward, unitId;
    private boolean isShooting, isFlying;

    private Entity goal;
    private Path path;

    public Mob(World world, float x, float z, float width, float height, int id) {
        super(world, x, 0, z, width, height, id);
    }

    @Override
    public void update(GameContainer gameContainer, int delta, EntityVault vault) {
        super.update(gameContainer, delta, vault);

        if (goal != null) {
            Vector2f goalPosition = path.getCurrentGoalPosition(this);
            move(goalPosition.x - getWidth() / 2, goalPosition.y - getHeight() / 2);
        }

        super.moveInertly(delta, vault);
    }

    @Override
    public Shape getBounds() {
        return new Rectangle(getX() + 1, getZ() + 1, getWidth() - 2, getHeight() - 2);
    }

    public void setGoal(Entity goal) {
        this.goal = goal;
        this.path = world.getPathfinder().trace(this, goal);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isShooting() {
        return isShooting;
    }

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
}