package com.twopeople.td.entity;

import com.twopeople.td.world.World;

/**
 * Created by podko_000
 * At 3:37 on 22.09.13
 */

public class WaveSpawner extends Entity {
    public WaveSpawner(World world, float x, float y, int id) {
        super(world, x, 0, y, 48, 48, id);
    }
}
