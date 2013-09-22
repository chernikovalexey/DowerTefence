package com.twopeople.td;

import com.twopeople.td.entity.Entity;
import com.twopeople.td.world.World;

/**
 * Created by podko_000
 * At 3:12 on 22.09.13
 */

public class Area{

    private float x,y,width,height;
    private int wave,id;
    public Area(World world, float x, float y, float width, float height, int wave, int id) {
        this.x = x;
        this.y=y;
        this.width =width;
        this.height = height;
        this.wave = wave;
        this.id = id;
        this.wave = wave;
    }
}
