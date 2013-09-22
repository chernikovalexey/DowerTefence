package com.twopeople.td.entity.tile;

import com.twopeople.td.Art;

import java.lang.reflect.Field;

public class TileList {
    public static TileProperties GRASS = new TileProperties(true, Art.tiles.getSprite(0, 0), Tile.TileType.Grass);
    public static TileProperties STONE = new TileProperties(true, Art.tiles.getSprite(1, 0), Tile.TileType.Stone);

    public static TileProperties getByType(Tile.TileType type) {
        for (Field field : TileList.class.getFields()) {
            if (field.getName().equals(type.toString().toUpperCase())) {
                try {
                    return (TileProperties) field.get(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}