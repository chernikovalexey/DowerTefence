package com.twopeople.td.entity;

import com.twopeople.td.entity.bullet.Bullet;
import com.twopeople.td.world.World;
import org.newdawn.slick.geom.Circle;

/**
 * Created by Alexey
 * At 6:18 AM on 9/22/13
 */

public class BattleEntity extends Entity {
    private float range = 0f;

    public BattleEntity(World world, float x, float y, float z, float width, float height, int id) {
        super(world, x, y, z, width, height, id);
    }

    public void setRange(float range) {
        this.range = range;
    }

    public boolean isInRange(Entity entity) {
        return entity.isCollidingWith(new Circle(getX(), getZ(), range));
    }

    public void shoot(float tx, float tz) {
        double angle = Math.atan2(getDirectionY(), getDirectionX());
        Bullet bullet = new Bullet(world, getX(), getZ());
        bullet.setDirectionX((float) Math.cos(angle));
        bullet.setDirectionY((float) Math.sin(angle));
        world.addEntity(bullet);
    }

    public void shootAt(Entity entity) {
        shoot(entity.getX(), entity.getZ());
    }
}