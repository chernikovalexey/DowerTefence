package com.twopeople.td.world;

import com.twopeople.td.entity.Entity;

import java.util.ArrayList;

/**
 * Created by Alexey
 * At 12:45 AM on 9/20/13
 */

public class EntityVault {
    private float worldWidth, worldHeight;
    public int xCells, yCells;

    public EntityVaultCell[] cells;

    public class EntityVaultCell {
        public static final float WIDTH = 200;
        public static final float HEIGHT = 200;

        private int x, y;
        private ArrayList<EntityVaultItem> entities = new ArrayList<EntityVaultItem>();

        public EntityVaultCell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void add(Entity entity) {
            entities.add(new EntityVaultItem(entity));
        }

        public boolean remove(Entity entity) {
            return entities.remove(entity);
        }

        public ArrayList<EntityVaultItem> getEntities() {
            return entities;
        }

        public boolean isIntersecting(Entity entity) {
            return entity.getX() >= x * WIDTH
                    && entity.getZ() >= y * HEIGHT
                    && entity.getX() + entity.getWidth() < x * (WIDTH + 1)
                    && entity.getZ() + entity.getHeight() < y * (HEIGHT + 1);
        }
    }

    private class EntityVaultItem {
        private Entity entity;
        public boolean isDuplicate = false;
        private ArrayList<Integer> duplicateCells = new ArrayList<Integer>();

        public EntityVaultItem(Entity entity) {
            this.entity = entity;
        }

        public void addDuplicate(int cell) {
            duplicateCells.add(cell);
        }

        public Entity getEntity() {
            return entity;
        }
    }

    public EntityVault(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.xCells = (int) (worldWidth / EntityVaultCell.WIDTH);
        this.yCells = (int) (worldHeight / EntityVaultCell.HEIGHT);
        this.cells = new EntityVaultCell[xCells * yCells];

        for (int x = 0; x < xCells; ++x) {
            for (int y = 0; y < yCells; ++y) {
                cells[x + y * xCells] = new EntityVaultCell(x, y);
            }
        }
    }

    public void add(Entity entity) {
        getCellFor(entity).add(entity);
    }

    public boolean remove(Entity entity) {
        return getCellFor(entity).remove(entity);
    }

    public void move(Entity entity) {
        remove(entity);
        add(entity);
    }

    public ArrayList<Entity> getVisible() {
        ArrayList<Entity> entities = new ArrayList<Entity>();

        for (int x = 0; x < xCells; ++x) {
            for (int y = 0; y < yCells; ++y) {
                EntityVaultCell cell = cells[x + y * xCells];
                for (EntityVaultItem i : cell.getEntities()) {
                    entities.add(i.getEntity());
                }
            }
        }

        return entities;
    }

    public EntityVaultCell getCellFor(Entity entity) {
        int cx = getCellX(entity);
        int cy = getCellY(entity);
        return cells[cx + cy * xCells];
    }

    public int getCellX(Entity entity) {
        return (int) (entity.getX() / EntityVaultCell.WIDTH);
    }

    public int getCellY(Entity entity) {
        return (int) (entity.getZ() / EntityVaultCell.HEIGHT);
    }
}