package com.twopeople.td;

import com.twopeople.td.entity.Entity;
import com.twopeople.td.world.World;

/**
 * Created by podko_000
 * At 3:12 on 22.09.13
 */

public class Area extends Entity {

    private int wave;
    public Area(World world, float x, float y, float width, float height, int wave, int id) {
        super(world, x, 0, y, width, height, id);
        this.wave = wave;
    }
}
