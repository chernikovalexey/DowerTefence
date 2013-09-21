package com.twopeople.td.entity.tower;

import com.twopeople.td.entity.Entity;
import com.twopeople.td.gui.TowerIcons;
import com.twopeople.td.world.Camera;
import com.twopeople.td.world.World;
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
    private static int globalId = -1;
    private int id;
    private int price;

    private boolean selected = false;

    public Tower(World world, float x, float z, int price) {
        super(world, x, 0, z, 48, 48);
        this.price = price;
        this.id = ++globalId;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public Tower clone() throws CloneNotSupportedException {
        return (Tower) super.clone();
    }

    @Override
    public Shape getBounds() {
        return new Rectangle(getX() + 1, getZ() + 1, getWidth() - 2, getHeight() - 2);
    }

    @Override
    public void render(GameContainer gameContainer, Camera camera, Graphics g) {
        g.setColor(Color.green);
        g.fillRect(camera.getX(getX()), camera.getZ(getZ()), getWidth(), getHeight());
    }

    public void renderIcon(GameContainer gameContainer, Graphics g, float sx, float sy) {
        g.setColor(Color.lightGray);
        g.fillRect(sx, sy, TowerIcons.TowerIcon.WIDTH, TowerIcons.TowerIcon.HEIGHT);
        if (selected) {
            g.setColor(Color.red);
            g.fillRect(sx, sy + TowerIcons.TowerIcon.HEIGHT, TowerIcons.TowerIcon.WIDTH, 5);
        }
    }

    public boolean canAfford(int money) {
        return money >= price;
    }

    public void select() {
        this.selected = true;
    }

    public void deselect() {
        this.selected = false;
    }

    public void toggleSelection() {
        this.selected = !selected;
    }

    public boolean isSelected() {
        return this.selected;
    }
}