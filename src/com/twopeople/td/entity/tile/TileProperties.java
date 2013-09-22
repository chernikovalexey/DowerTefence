package com.twopeople.td.entity.tile;

import org.newdawn.slick.Image;

public class TileProperties {
    public boolean isStatic = false;
    public Image image;
    public Tile.TileType tileType;

    public TileProperties(boolean isStatic, Image image, Tile.TileType tileType) {
        this.isStatic = isStatic;
        this.image = image;
        this.tileType = tileType;
    }
}