package com.twopeople.td.world;

import com.twopeople.td.entity.tower.Tower;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Created by Alexey
 * At 2:25 AM on 9/21/13
 */

public class ConstructionManager {
    public static final int CELL_WIDTH = 24;
    public static final int CELL_HEIGHT = 24;

    private World world;

    private int overCellX = 0;
    private int overCellY = 0;

    public ConstructionManager(World world) {
        this.world = world;
    }

    public void update(GameContainer gameContainer, int delta) {
        Input input = gameContainer.getInput();
        overCellX = input.getMouseX() / CELL_WIDTH;
        overCellY = input.getMouseY() / CELL_HEIGHT;

        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            world.addEntity(new Tower(getOverCellX(), getOverCellY()));
        }
    }

    public void render(GameContainer gameContainer, Graphics g) {
        g.setColor(new Color(255, 255, 255, 100));
        g.fillRect(getOverCellX(), getOverCellY(), CELL_WIDTH, CELL_HEIGHT);
    }

    public float getOverCellX() {
        return overCellX * CELL_WIDTH;
    }

    public float getOverCellY() {
        return overCellY * CELL_HEIGHT;
    }
}