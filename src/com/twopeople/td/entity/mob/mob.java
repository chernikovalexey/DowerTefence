package com.twopeople.td.entity.mob;

import com.twopeople.td.entity.Entity;
import com.twopeople.td.world.EntityVault;
import com.twopeople.td.world.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

/**
 * Created by Alexey
 * At 11:37 AM on 9/21/13
 */

public class Mob extends Entity {
    public Mob(World world, float x, float z, float width, float height) {
        super(world, x, 0, z, width, height);
    }

    @Override
    public void update(GameContainer gameContainer, int delta, EntityVault vault) {
        super.update(gameContainer, delta, vault);

        Input input = gameContainer.getInput();

        if (input.isKeyDown(Input.KEY_A)) {
            setDirectionX(-1);
        }
        if (input.isKeyDown(Input.KEY_D)) {
            setDirectionX(1);
        }
        if (input.isKeyDown(Input.KEY_W)) {
            setDirectionY(-1);
        }
        if (input.isKeyDown(Input.KEY_S)) {
            setDirectionY(1);
        }

        super.moveInertly(delta, vault);
    }
}