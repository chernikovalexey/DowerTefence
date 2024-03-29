package com.twopeople.td.world;

import com.twopeople.td.entity.Entity;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Alexey
 * At 12:45 AM on 9/20/13
 */

public class EntityVault {
    private int size = 0;
    public int xCells, yCells;

    private EntityVaultCell[] cells;

    public class EntityVaultCell {
        public static final float WIDTH = 200;
        public static final float HEIGHT = 200;

        private int x, y;
        private ArrayList<EntityVaultItem> entities = new ArrayList<EntityVaultItem>();

        public EntityVaultCell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getCellNum() {
            return computeCellNum(getX(), getY());
        }

        public void add(Entity entity) {
            EntityVaultItem newItem = new EntityVaultItem(entity);
            EntityVaultItem duplicateItem = new EntityVaultItem(entity);
            entities.add(newItem);

            if (isStickingOut(entity)) {
                for (EntityVaultCell cell : getAllCellsFor(entity)) {
                    if (!cell.equals(this)) {
                        newItem.addDuplicate(cell.getCellNum());
                        duplicateItem.isDuplicate = true;
                        duplicateItem.setMasterCell(getCellNum());
                        cell.add(duplicateItem);
                    }
                }
            }
        }

        public void add(EntityVaultItem item) {
            entities.add(item);
        }

        public boolean remove(Entity entity) {
            EntityVaultItem item;
            Iterator<EntityVaultItem> it = entities.iterator();

            while (it.hasNext()) {
                item = it.next();
                if (item.getEntity().equals(entity)) {
                    if (item.hasDuplicates()) {
                        for (Integer duplicate : item.getDuplicates()) {
                            getCell(duplicate).remove(entity);
                        }
                    }
                    it.remove();
                    return true;
                }
            }

            return false;
        }

        public ArrayList<EntityVaultItem> getEntities() {
            return entities;
        }

        public boolean isIntersecting(Entity entity) {
            return entity.isCollidingWith(new Rectangle(getX() * WIDTH, getY() * HEIGHT, WIDTH, HEIGHT));
        }

        public boolean isStickingOut(Entity entity) {
            return entity.getX() + entity.getWidth() > this.x + WIDTH
                    || entity.getZ() + entity.getHeight() > this.y + HEIGHT;
        }
    }

    private class EntityVaultItem {
        private Entity entity;
        public boolean isDuplicate = false;
        private int masterCell = 0;
        private ArrayList<Integer> duplicateCells = new ArrayList<Integer>();

        public EntityVaultItem(Entity entity) {
            this.entity = entity;
        }

        public void addDuplicate(int cell) {
            duplicateCells.add(cell);
        }

        public boolean hasDuplicates() {
            return duplicateCells.size() > 0 && !this.isDuplicate;
        }

        public ArrayList<Integer> getDuplicates() {
            return this.duplicateCells;
        }

        public void setMasterCell(int masterCell) {
            this.masterCell = masterCell;
        }

        public int getMasterCell() {
            return this.masterCell;
        }

        public Entity getEntity() {
            return entity;
        }
    }

    public EntityVault(float worldWidth, float worldHeight) {
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
        int cx = getCellX(entity);
        int cy = getCellY(entity);

        entity.setCellX(cx);
        entity.setCellY(cy);

        getCell(cx, cy).add(entity);
        ++size;
    }

    public boolean remove(Entity entity) {
        if (getCell(entity.getCellX(), entity.getCellY()).remove(entity)) {
            return --size == size;
        }
        return false;
    }

    public void move(Entity entity) {
        remove(entity);
        add(entity);
    }

    public ArrayList<Entity> getAll() {
        ArrayList<Entity> entities = new ArrayList<Entity>();

        for (int x = 0; x < xCells; ++x) {
            for (int y = 0; y < yCells; ++y) {
                EntityVaultCell cell = getCell(x, y);
                for (EntityVaultItem i : cell.getEntities()) {
                    if (!i.isDuplicate) {
                        entities.add(i.getEntity());
                    }
                }
            }
        }

        return entities;
    }

    public ArrayList<Entity> getVisible(Camera camera) {
        ArrayList<Entity> entities = new ArrayList<Entity>();

        for (Entity e : getAll()) {
            if (camera.isVisible(e)) {
                entities.add(e);
            }
        }

        return entities;
    }

    public ArrayList<Entity> getOnEachTickUpdatable() {
        ArrayList<Entity> entities = new ArrayList<Entity>();

        for (Entity e : getAll()) {
            if (e.updatesOnEachTick()) {
                entities.add(e);
            }
        }

        return entities;
    }

    private boolean isIntersectionPredicate(Entity e1, Entity e2, Entity.IType it) {
        return (it == Entity.IType.All)
                || (it == Entity.IType.NotOwner && !e1.isOwnerOf(e2));
    }

    public ArrayList<Entity> getCollidingEntities(Entity entity, Entity.IType it) {
        Entity e;
        ArrayList<Entity> entities = new ArrayList<Entity>();

        for (EntityVaultItem item : getCellFor(entity).getEntities()) {
            if (item.hasDuplicates()) {
                for (Integer duplicate : item.getDuplicates()) {
                    for (EntityVaultItem item2 : getCell(duplicate).getEntities()) {
                        e = item2.getEntity();
                        if (e.isCollidingWith(entity) && !e.equals(entity) && isIntersectionPredicate(e, entity, it)) {
                            entities.add(e);
                        }
                    }
                }
            }
            e = item.getEntity();
            if (e.isCollidingWith(entity) && !e.equals(entity) && isIntersectionPredicate(e, entity, it)) {
                entities.add(e);
                e.onHit(entity);
            }
        }
        return entities;
    }

    public ArrayList<Entity> getCollidingEntities(Entity entity) {
        return this.getCollidingEntities(entity, Entity.IType.All);
    }

    public boolean isCollidingShape(Shape shape) {
        Entity entity = new Entity(null, shape.getX(), 0, shape.getY(), shape.getWidth(), shape.getHeight(), -1);
        return !nothingColliding(entity);
    }

    public boolean nothingColliding(Entity entity) {
        return getCollidingEntities(entity).size() == 0;
    }

    public int computeCellNum(int x, int y) {
        return x + y * xCells;
    }

    public EntityVaultCell getCell(int num) {
        return cells[num];
    }

    public EntityVaultCell getCell(int x, int y) {
        return getCell(computeCellNum(x, y));
    }

    public EntityVaultCell getCellFor(Entity entity) {
        int cx = getCellX(entity);
        int cy = getCellY(entity);
        return cells[computeCellNum(cx, cy)];
    }

    public ArrayList<EntityVaultCell> getAllCellsFor(Entity entity) {
        EntityVaultCell cell;
        ArrayList<EntityVaultCell> intersectingCells = new ArrayList<EntityVaultCell>();

        for (int x = 0; x < xCells; ++x) {
            for (int y = 0; y < yCells; ++y) {
                cell = getCell(x, y);
                if (cell.isIntersecting(entity)) {
                    intersectingCells.add(cell);
                }
            }
        }

        return intersectingCells;
    }

    public int getCellX(Entity entity) {
        int cx = (int) (entity.getX() / EntityVaultCell.WIDTH);
        if (cx < 0) { cx = 0; }
        if (cx >= xCells) { cx = xCells - 1; }
        return cx;
    }

    public int getCellY(Entity entity) {
        int cy = (int) (entity.getZ() / EntityVaultCell.HEIGHT);
        if (cy < 0) { cy = 0; }
        if (cy >= yCells) { cy = yCells - 1; }
        return cy;
    }

    public int size() {
        return size;
    }

    public static ArrayList<Entity> filterByType(ArrayList<Entity> entities, Entity.EntityType type) {
        Iterator<Entity> i = entities.iterator();
        while (i.hasNext()) {
            if (i.next().getType() != type) {
                i.remove();
            }
        }
        return entities;
    }
}