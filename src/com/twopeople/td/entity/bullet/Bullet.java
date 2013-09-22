package com.twopeople.td.entity.bullet;

import com.twopeople.td.entity.Entity;
import com.twopeople.td.world.Camera;
import com.twopeople.td.world.EntityVault;
import com.twopeople.td.world.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Alexey
 * At 6:11 AM on 9/22/13
 */

public class Bullet extends Entity {
    public Bullet(World world, float x, float z) {
        super(world, x, 0, z, 8, 8, -1);
        setSpeed(2.5f);
    }

    @Override
    public boolean updatesOnEachTick() {
        return true;
    }

    @Override
    public void update(GameContainer gameContainer, int delta, EntityVault vault) {
        System.out.println("Updating a bullet: " + getX() + ", " + getZ());
        super.update(gameContainer, delta, vault);
        super.moveInertly(delta, vault);
    }

    @Override
    public void render(GameContainer gameContainer, Camera camera, Graphics g) {
        g.setColor(Color.black);
        g.fillOval(camera.getX(getX()), camera.getZ(getZ()), getWidth(), getHeight());
    }
}