package com.twopeople.td;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by Alexey
 * At 6:27 AM on 9/22/13
 */

public class Art {
    public static SpriteSheet tiles = load("res/art/tiles.png", 48, 48);
    public static SpriteSheet entities = load("res/art/entities.png", 32, 56);
    public static SpriteSheet bullets = load("res/art/bullets.png", 8, 8);
    public static SpriteSheet mob = load("res/art/mob.png", 32, 32);

    public static SpriteSheet load(String file, int w, int h) {
        try {
            return new SpriteSheet(file, w, h);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        return null;
    }
}