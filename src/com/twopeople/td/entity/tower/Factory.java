package com.twopeople.td.entity.tower;

import com.twopeople.td.world.World;

/**
 * Created by Alexey
 * At 4:10 PM on 9/21/13
 */

public class Factory {
    public MachineGunTower getMachineGunTower(World world) {
        System.out.println("Called~!");
        return new MachineGunTower(world, 0, 0);
    }
}