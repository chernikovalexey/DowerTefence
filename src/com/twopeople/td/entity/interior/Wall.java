package com.twopeople.td.entity.interior;

import com.twopeople.td.Art;
import com.twopeople.td.entity.Entity;
import com.twopeople.td.world.Camera;
import com.twopeople.td.world.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Alexey
 * At 3:08 AM on 9/22/13
 */

public class Wall extends Entity {
    public Wall(World world, float x, float z) {
        super(world, x, 0, z, 32, 56);
    }

    @Override
    public void render(GameContainer gameContainer, Camera camera, Graphics g) {
        g.drawImage(Art.entities.getSprite(0, 0), camera.getX(getX()), camera.getZ(getZ()));
    }
}