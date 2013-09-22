package com.twopeople.td.entity.mob;

import com.twopeople.td.world.World;

/**
 * Created by Alexey
 * At 3:19 AM on 9/22/13
 */

public class Unit extends Mob {
    public Unit(World world, float x, float z, int id) {
        super(world, x, z, 0, 0, id);
    }
}