package com.twopeople.td.entity.tile;

import com.twopeople.td.entity.Entity;
import com.twopeople.td.world.Camera;
import com.twopeople.td.world.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Tile extends Entity {
    public enum TileType {
        Grass, Stone
    }

    private TileProperties properties;

    public Tile(World world, float x, float z, TileProperties properties) {
        super(world, x, 0, z, 48, 48, -1);
        this.properties = properties;
    }

    @Override
    public void render(GameContainer container, Camera camera, Graphics g) {
        g.drawImage(properties.image, camera.getX(getX()), camera.getZ(getZ()));
    }

    @Override
    public EntityType getType() {
        return EntityType.Tile;
    }

    @Override
    public boolean updatesOnEachTick() {
        return !properties.isStatic;
    }
}