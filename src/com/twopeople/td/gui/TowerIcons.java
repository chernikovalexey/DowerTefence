package com.twopeople.td.gui;

import com.twopeople.td.entity.tower.Tower;
import com.twopeople.td.world.Camera;
import com.twopeople.td.world.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.ArrayList;

/**
 * Created by Alexey
 * At 1:14 PM on 9/21/13
 */

public class TowerIcons {
    private World world;

    public class TowerIcon extends SquareUiControl {
        public static final int WIDTH = 48;
        public static final int HEIGHT = 48;

        private Tower tower;

        public TowerIcon(Tower tower, float x, float y) {
            super(x, y, WIDTH, HEIGHT);
            this.tower = tower;
        }

        public Tower getTower() {
            return tower;
        }

        public void render(GameContainer gameContainer, Graphics g) {
            tower.renderIcon(gameContainer, g, getX(), getY());
        }
    }

    private ArrayList<TowerIcon> icons = new ArrayList<TowerIcon>();

    public TowerIcons(World world) {
        this.world = world;

        ArrayList<Tower> towers = world.getCM().getTowers();
        Camera camera = world.getState().getCamera();
        float padding = 5;
        float sx = camera.getScreenWidth() / 2 - (TowerIcon.WIDTH + padding) * towers.size() / 2;
        float sy = camera.getScreenHeight() - 10 - TowerIcon.HEIGHT;

        for (Tower tower : towers) {
            icons.add(new TowerIcon(tower, sx, sy));
            sx += TowerIcon.WIDTH + padding;
        }
    }

    public void update(GameContainer gameContainer, int delta) {
        Input input = gameContainer.getInput();

        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            for (TowerIcon icon : icons) {
                if (icon.isOver(input.getMouseX(), input.getMouseY())) {
                    icon.getTower().toggleSelection();

                    if (icon.getTower().isSelected()) {
                        world.getCM().selectTower(icon.getTower().getId());
                    } else {
                        world.getCM().deselectTower();
                    }

                    break;
                }
            }
        }
    }

    public void render(GameContainer gameContainer, Graphics g) {
        for (TowerIcon icon : icons) {
            icon.render(gameContainer, g);
        }
    }
}