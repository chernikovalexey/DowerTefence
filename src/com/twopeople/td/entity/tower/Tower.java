package com.twopeople.td.entity.tower;

import com.twopeople.td.entity.Entity;
import com.twopeople.td.world.Camera;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * Created by Alexey
 * At 2:21 AM on 9/21/13
 */

public class Tower extends Entity {
    public Tower(float x, float z) {
        super(x, 0, z, 24, 24, -1);
    }

    @Override
    public Shape getBounds() {
        return new Rectangle(getX() + 1, getZ() + 1, getWidth() - 2, getHeight() - 2);
    }

    @Override
    public void render(GameContainer gameContainer, Camera camera, Graphics g) {
        g.setColor(Color.green);
        g.fillRect(getX(), getZ(), getWidth(), getHeight());
    }
}