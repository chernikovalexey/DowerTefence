package com.twopeople.td.entity.mob;

import com.twopeople.td.entity.Entity;
import com.twopeople.td.world.EntityVault;
import com.twopeople.td.world.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

/**
 * Created by Alexey
 * At 11:37 AM on 9/21/13
 */

public class Mob extends Entity {
    private float speed, damage;
    private int health, reward, unitId;
    private boolean isShooting, isFlying;

    public Mob(World world, float x, float z, float width, float height, int id) {
        super(world, x, 0, z, width, height, id);
    }

    @Override
    public void update(GameContainer gameContainer, int delta, EntityVault vault) {
        super.update(gameContainer, delta, vault);

        Input input = gameContainer.getInput();

        if (input.isKeyDown(Input.KEY_A)) {
            setDirectionX(-1);
        }
        if (input.isKeyDown(Input.KEY_D)) {
            setDirectionX(1);
        }
        if (input.isKeyDown(Input.KEY_W)) {
            setDirectionY(-1);
        }
        if (input.isKeyDown(Input.KEY_S)) {
            setDirectionY(1);
        }

        super.moveInertly(delta, vault);
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