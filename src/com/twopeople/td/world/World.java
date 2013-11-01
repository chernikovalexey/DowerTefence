package com.twopeople.td.world;

import com.twopeople.td.entity.Camp;
import com.twopeople.td.entity.Entity;
import com.twopeople.td.entity.WaveSpawner;
import com.twopeople.td.entity.interior.Wall;
import com.twopeople.td.entity.tile.Tile;
import com.twopeople.td.entity.tile.TileList;
import com.twopeople.td.entity.tower.MachineGunTower;
import com.twopeople.td.state.GameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Alexey
 * At 12:42 AM on 9/20/13
 */

public class World {
    private GameState gameState;
    private float worldWidth, worldHeight;

    private EntityVault tiles;
    private EntityVault entities;
    private ArrayList<Area> areas = new ArrayList<Area>();
    private ArrayList<WaveSpawner> spawners = new ArrayList<WaveSpawner>();

    //    private Pathfinder pathfinder = new Pathfinder(this);
    private ConstructionManager constructionManager;
    private AreaManager areaManager;

    public World(GameState game) {
        try {
            Vector2f worldSize = Loader.preLoad("res/maps/map.dttwopeople", this);
            this.setWidth(worldSize.getX());
            this.setHeight(worldSize.getY());
            areas.clear();


        } catch (Exception e) {
        }

        this.gameState = game;
        this.tiles = new EntityVault(worldWidth, worldHeight);
        this.entities = new EntityVault(worldWidth, worldHeight);

        try {
            Loader.fromFile("res/maps/map.dttwopeople", this);
        } catch (Exception e) {
        }

        this.constructionManager = new ConstructionManager(this);
        this.areaManager = new AreaManager(areas, spawners);

        //        Mob mob = new Mob(this, 48 * 5, 48 * 2, 48, 48);
        Wall wall = new Wall(this, 48 * 20, 48 * 3);
        MachineGunTower tower = new MachineGunTower(this, 48 * 18, 48 * 4);
        Camp camp = new Camp(this, 48 * 8, 48 * 8, 999);
        //        entities.add(mob);
        entities.add(wall);
        entities.add(tower);
        entities.add(camp);

        for (int x = 0; x < worldWidth / 48; ++x) {
            for (int y = 0; y < worldHeight / 48; ++y) {
                tiles.add(new Tile(this, x * 48, y * 48, TileList.GRASS));
            }
        }

        //        mob.setGoal(camp);

        getState().getCamera().setTargetX(299);
    }

    public void update(GameContainer gameContainer, int delta) {
        updateVault(gameContainer, delta, tiles);
        updateVault(gameContainer, delta, entities);
        constructionManager.update(gameContainer, delta);
        areaManager.update();
    }

    public void render(GameContainer gameContainer, Graphics g) {
        Camera camera = gameState.getCamera();

        renderVault(gameContainer, camera, g, tiles);
        renderVault(gameContainer, camera, g, entities);
        constructionManager.render(gameContainer, camera, g);

        //renderGrid(camera, g, entities);
        renderFog(camera, g, entities);

        g.setColor(Color.white);
        g.drawString("selected tower=" + getCM().getSelectedTower(), 10, 30);
        g.drawString("camera pos=" + camera.getTargetX() + ", " + camera.getTargetY(), 10, 50);
        g.drawString("entities=" + entities.size(), 10, 70);
    }

    private void renderFog(Camera camera, Graphics g, EntityVault entities) {
        g.setColor(Color.black);
        for (Area area : areas) {
            if (area.isOpened()) { continue; }
            Entity e = new Entity(this, area.getX(), area.getY(), area.getY(), area.getWidth(), area.getHeight());
            if (camera.isVisible(e)) {
                g.fillRect(camera.getX(area.getX()), camera.getZ(area.getY()), area.getWidth(), area.getHeight());
            }
        }
    }

    private void updateVault(GameContainer gameContainer, int delta, EntityVault vault) {
        Entity e;
        Iterator<Entity> i = vault.getOnEachTickUpdatable().iterator();
        while (i.hasNext()) {
            e = i.next();
            e.update(gameContainer, delta, vault);
            if (e.shouldRemove()) {
                i.remove();
            }
        }
    }

    private void renderVault(GameContainer gameContainer, Camera camera, Graphics g, EntityVault vault) {
        Entity e;
        Iterator<Entity> i = vault.getVisible(camera).iterator();
        while (i.hasNext()) {
            e = i.next();
            e.render(gameContainer, camera, g);
        }
    }

    private void renderGrid(Camera camera, Graphics g, EntityVault vault) {
        g.setColor(Color.white);

        for (float x = 0; x < vault.xCells * EntityVault.EntityVaultCell.WIDTH; x += EntityVault.EntityVaultCell.WIDTH) {
            for (float y = 0; y < vault.yCells * EntityVault.EntityVaultCell.HEIGHT; y += EntityVault.EntityVaultCell.HEIGHT) {
                g.drawLine(camera.getX(x), camera.getZ(y), camera.getX(x + EntityVault.EntityVaultCell.WIDTH), camera.getZ(y));
                g.drawLine(camera.getX(x), camera.getZ(y), camera.getX(x), camera.getZ(y + EntityVault.EntityVaultCell.HEIGHT));
                int cell = ((int) (x / EntityVault.EntityVaultCell.WIDTH + y / EntityVault.EntityVaultCell.HEIGHT * vault.xCells));
                g.drawString("items=" + vault.getCell(cell).getEntities().size(), camera.getX(x + 15), camera.getZ(y + 15));
            }
        }
    }

    public boolean isOutside(Entity entity) {
        return entity.getX() < 0 || entity.getZ() < 0 || entity.getX() > worldWidth || entity.getZ() > worldHeight;
    }

    public EntityVault getEntities() {
        return this.entities;
    }

    public void addEntity(Entity entity) {
        if (entities.nothingColliding(entity)) {
            entities.add(entity);
        }
    }

    public void addSpawner(WaveSpawner spawner) {
        spawners.add(spawner);
    }

    public void addArea(Area area) {
        areas.add(area);
    }

    public float getWidth() {
        return this.worldWidth;
    }

    public float getHeight() {
        return this.worldHeight;
    }

    public GameState getState() {
        return gameState;
    }

    //    public Pathfinder getPathfinder() {
    //        return this.pathfinder;
    //    }

    public ConstructionManager getCM() {
        return constructionManager;
    }

    public void setWidth(float width) {
        this.worldWidth = width;
    }

    public void setHeight(float height) {
        this.worldHeight = height;
    }

    public Camp getCamp(int id) {
        for (Entity e : this.getEntities().getAll()) {
            if (e.getId() == id) { return (Camp) e; }
        }

        return null;
    }
}