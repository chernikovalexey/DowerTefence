package com.twopeople.td.entity;

import com.twopeople.td.world.Camera;
import com.twopeople.td.world.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Alexey
 * At 10:13 PM on 9/21/13
 */

public class Camp extends Entity {
    public Camp(World world, int x, int z, int id) {
        super(world, x, 0, z, 48, 48, id);
    }

    @Override
    public void render(GameContainer gameContainer, Camera camera, Graphics g) {
        g.setColor(Color.pink);
        g.fillRect(camera.getX(getX()), camera.getZ(getZ()), getWidth(), getHeight());
    }
}