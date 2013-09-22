package com.twopeople.td.world;

import com.twopeople.td.Area;
import com.twopeople.td.entity.Camp;
import com.twopeople.td.entity.Entity;
import com.twopeople.td.entity.WaveSpawner;
import com.twopeople.td.entity.interior.Wall;
import com.twopeople.td.entity.mob.Mob;
import com.twopeople.td.state.GameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.ArrayList;

/**
 * Created by Alexey
 * At 12:42 AM on 9/20/13
 */

public class World {
    private GameState gameState;
    private float worldWidth, worldHeight;

    private EntityVault entities;
    private ArrayList<Area> areas = new ArrayList<Area>();
    private ArrayList<WaveSpawner> spawners = new ArrayList<WaveSpawner>();

    private ConstructionManager constructionManager;
    private Pathfinder pathfinder = new Pathfinder(this);
    private Path path;

    public World(GameState game) {
        this.gameState = game;
        this.worldWidth = 2048;
        this.worldHeight = 2048;
        this.entities = new EntityVault(worldWidth, worldHeight);
        this.constructionManager = new ConstructionManager(this);

        Mob mob = new Mob(this, 48 * 24, 48 * 2, 48, 48, 0);
        Wall wall = new Wall(this, 48 * 20, 48 * 3, 0);
        Camp camp = new Camp(this, 48 * 8, 48 * 8, 0);
        entities.add(mob);
        entities.add(camp);
        entities.add(wall);

        path = pathfinder.trace(mob, camp);
        getState().getCamera().setTargetX(200);
    }

    public void update(GameContainer gameContainer, int delta) {
        updateVault(gameContainer, delta, entities);
        constructionManager.update(gameContainer, delta);

        Input input = gameContainer.getInput();

        Camera camera = gameState.getCamera();
        if (input.isKeyDown(Input.KEY_LEFT)) {
            camera.setTargetX(camera.getTargetX() - 1f);
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            camera.setTargetX(camera.getTargetX() + 1f);
        }
        if (input.isKeyDown(Input.KEY_UP)) {
            camera.setTargetY(camera.getTargetY() - 1f);
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            camera.setTargetY(camera.getTargetY() + 1f);
        }
    }

    public void render(GameContainer gameContainer, Graphics g) {
        Camera camera = gameState.getCamera();

        g.setColor(new Color(204, 204, 204, 25));
        g.fillRect(camera.getX(0), camera.getZ(0), worldWidth, worldHeight);
        path.render(camera, g);
        renderVault(gameContainer, camera, g, entities);
        constructionManager.render(gameContainer, camera, g);
        //pathfinder.render(camera, g);

        //renderGrid(camera, g, entities);
        g.setColor(Color.white);
        g.drawString("selected tower=" + getCM().getSelectedTower(), 10, 30);
        g.drawString("camera pos=" + camera.getTargetX() + ", " + camera.getTargetY(), 10, 50);
        g.drawString("entities=" + entities.size(), 10, 70);
    }

    private void updateVault(GameContainer gameContainer, int delta, EntityVault vault) {
        for (Entity e : vault.getVisible()) {
            e.update(gameContainer, delta, vault);
        }
    }

    private void renderVault(GameContainer gameContainer, Camera camera, Graphics g, EntityVault vault) {
        for (Entity e : vault.getVisible()) {
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
                g.drawString("items=" + vault.cells[cell].getEntities().size(), camera.getX(x + 15), camera.getZ(y + 15));
            }
        }
    }

    public EntityVault getEntities() {
        return this.entities;
    }

    public void addEntity(Entity entity) {
        if (entities.nothingColliding(entity)) {
            entities.add(entity);
        }
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

    public ConstructionManager getCM() {
        return constructionManager;
    }

    public void addSpawner(WaveSpawner spawner)
    {
        spawners.add(spawner);
    }

    public void addArea(Area area) {
        areas.add(area);
    }
}