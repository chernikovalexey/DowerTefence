package com.twopeople.td.entity;

import com.twopeople.td.world.World;

/**
 * Created by Alexey
 * At 6:18 AM on 9/22/13
 */

public class BattleEntity extends Entity {
    public BattleEntity(World world, float x, float y, float z, float width, float height, int id) {
        super(world, x, y, z, width, height, id);
    }

    public void shoot(float tx, float tz) {

    }
}