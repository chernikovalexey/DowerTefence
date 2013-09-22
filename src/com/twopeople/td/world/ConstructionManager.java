package com.twopeople.td.world;

import com.twopeople.td.entity.tower.MachineGunTower;
import com.twopeople.td.entity.tower.Tower;
import com.twopeople.td.entity.tower.TowerFactory;
import com.twopeople.td.gui.SquareUiControl;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.ArrayList;

/**
 * Created by Alexey
 * At 2:25 AM on 9/21/13
 */

public class ConstructionManager {
    private World world;
    private int xCells, yCells;

    private int money = 0;
    private ArrayList<Tower> towers = new ArrayList<Tower>();
    private int selectedTower = -1;

    private boolean renderGrid = false;
    private int overCellIndex = 0;
    protected static int cellAmount = -1;

    private class ConstructionCell extends SquareUiControl {
        public static final int WIDTH = 48;
        public static final int HEIGHT = 48;

        private int id;

        public ConstructionCell(float x, float y) {
            super(x, y, WIDTH, HEIGHT);
            this.id = ++cellAmount;
        }

        public void render(Camera camera, Graphics g) {
            g.setColor(new Color(255, 255, 255, 35));
            g.fillRect(camera.getX(getX()), camera.getZ(getY()), getWidth(), getHeight());
        }

        public int getId() {
            return this.id;
        }
    }

    private ArrayList<ConstructionCell> cells = new ArrayList<ConstructionCell>();

    public ConstructionManager(World world) {
        this.world = world;

        xCells = (int) (world.getWidth() / ConstructionCell.WIDTH);
        yCells = (int) (world.getHeight() / ConstructionCell.HEIGHT);

        for (int x = 0; x < xCells; ++x) {
            for (int y = 0; y < yCells; ++y) {
                cells.add(new ConstructionCell(getCellAbsX(x), getCellAbsY(y)));
            }
        }

        towers.add(new MachineGunTower(world, 0, 0));
    }

    public void update(GameContainer gameContainer, int delta) {
        Camera camera = world.getState().getCamera();
        Input input = gameContainer.getInput();

        for (ConstructionCell cell : cells) {
            if (cell.isOver(input.getMouseX() + camera.getTargetX(), input.getMouseY() + camera.getTargetY())) {
                overCellIndex = cell.getId();
                break;
            }
        }

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && renderGrid) {
            Tower newTower = towers.get(selectedTower);
            world.addEntity(TowerFactory.create(newTower.getClass().getName(), world, getOverCell().getX(), getOverCell().getY()));
        }
    }

    public void render(GameContainer gameContainer, Camera camera, Graphics g) {
        if (renderGrid) {
            g.setColor(new Color(255, 255, 255, 75));

            for (int x = 0; x < xCells; ++x) {
                for (int y = 0; y < yCells; ++y) {
                    g.drawLine(camera.getX(getCellAbsX(x) + 1), camera.getZ(getCellAbsY(y)), camera.getX(getCellAbsX(x + 1)), camera.getZ(getCellAbsY(y)));
                    g.drawLine(camera.getX(getCellAbsX(x)), camera.getZ(getCellAbsY(y) + 1), camera.getX(getCellAbsX(x)), camera.getZ(getCellAbsY(y + 1)));
                }
            }

            getOverCell().render(camera, g);
        }
    }

    public int getSelectedTower() {
        return this.selectedTower;
    }

    public void selectTower(int id) {
        if (id >= 0 && id < towers.size()) {
            selectedTower = id;
            renderGrid = true;
        }
    }

    public void deselectTower() {
        selectedTower = -1;
        renderGrid = false;
    }

    public ConstructionCell getOverCell() {
        return cells.get(overCellIndex);
    }

    public float getCellAbsX(int cx) {
        return cx * ConstructionCell.WIDTH;
    }

    public float getCellAbsY(int cy) {
        return cy * ConstructionCell.HEIGHT;
    }

    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public int getMoney() {
        return this.money;
    }

    public void addMoney(int money) {
        this.money += money;
    }
}