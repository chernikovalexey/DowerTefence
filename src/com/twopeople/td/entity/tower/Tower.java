package com.twopeople.td.entity.tower;

import com.twopeople.td.entity.BattleEntity;
import com.twopeople.td.entity.Entity;
import com.twopeople.td.gui.TowerIcons;
import com.twopeople.td.world.Camera;
import com.twopeople.td.world.EntityVault;
import com.twopeople.td.world.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Alexey
 * At 2:21 AM on 9/21/13
 */

public class Tower extends BattleEntity {
    private int price;

    private boolean selected = false;

    public Tower(World world, float x, float z, int price) {
        super(world, x, 0, z, 48, 48);
        this.price = price;
    }

    @Override
    public Shape getBounds() {
        return new Rectangle(getX() + 1, getZ() + 1, getWidth() - 2, getHeight() - 2);
    }

    @Override
    public EntityType getType() {
        return EntityType.Tower;
    }

    @Override
    public boolean updatesOnEachTick() {
        return true;
    }

    @Override
    public void update(GameContainer gameContainer, int delta, EntityVault vault) {
        Entity e;
        ArrayList<Entity> entities = EntityVault.filterByType(vault.getVisible(world.getState().getCamera()), EntityType.Mob);
        Iterator<Entity> i = entities.iterator();

        float distance;
        float minDistance = Float.MAX_VALUE;
        Entity target = null;

        while (i.hasNext()) {
            e = i.next();
            if (isInRange(e)) {
                distance = getDistanceTo(e);
                if (distance < minDistance) {
                    minDistance = distance;
                    target = e;
                }
            }
        }

        if (target != null) {
            updateDirectionToEntity(target);
            shootAt(target);
        }
    }

    @Override
    public void render(GameContainer gameContainer, Camera camera, Graphics g) {
        g.setColor(Color.green);
        g.fillRect(camera.getX(getX()), camera.getZ(getZ()), getWidth(), getHeight());
    }

    public void renderIcon(GameContainer gameContainer, Graphics g, float sx, float sy) {
        if (!world.getCM().canAfford(this)) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(Color.gray);
        }

        g.fillRect(sx, sy, TowerIcons.TowerIcon.WIDTH, TowerIcons.TowerIcon.HEIGHT);
        if (selected) {
            g.setColor(Color.red);
            g.fillRect(sx, sy + TowerIcons.TowerIcon.HEIGHT, TowerIcons.TowerIcon.WIDTH, 5);
        }
    }

    public int getPrice() {
        return this.price;
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

    public void setWorld(World world) {
        this.world = world;
    }
}