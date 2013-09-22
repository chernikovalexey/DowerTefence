package com.twopeople.td.entity.tower;

import com.twopeople.td.world.EntityVault;
import com.twopeople.td.world.World;
import org.newdawn.slick.GameContainer;

/**
 * Created by Alexey
 * At 3:01 AM on 9/21/13
 */

public class MachineGunTower extends Tower {
    public MachineGunTower(World world, float x, float z) {
        super(world, x, z, 100);
    }

    @Override
    public void update(GameContainer gameContainer, int delta, EntityVault vault) {

    }
}