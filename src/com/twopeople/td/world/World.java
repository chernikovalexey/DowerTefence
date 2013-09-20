package com.twopeople.td.world;

import com.twopeople.td.entity.Entity;
import com.twopeople.td.state.GameState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * Created by Alexey
 * At 12:42 AM on 9/20/13
 */

public class World {
    private GameState gameState;
    private float worldWidth, worldHeight;
    private EntityVault entities;

    public World(GameState game) {
        this.gameState = game;
        this.worldWidth = 1024;
        this.worldHeight = 768;
        this.entities = new EntityVault(worldWidth, worldHeight);

        entities.add(new Entity(10, 0, 10, 350, 350));
        entities.add(new Entity(410, 0, 370, 10, 10));
    }

    public void update(GameContainer gameContainer, int delta) {
        updateVault(gameContainer, delta, entities);
    }

    public void render(GameContainer gameContainer, Graphics g) {
        renderVault(gameContainer, gameState.getCamera(), g, entities);
        renderGrid(g, entities);
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

    private void renderGrid(Graphics g, EntityVault vault) {
        g.setColor(Color.white);

        for (float x = 0; x < vault.xCells * EntityVault.EntityVaultCell.WIDTH; x += EntityVault.EntityVaultCell.WIDTH) {
            for (float y = 0; y < vault.yCells * EntityVault.EntityVaultCell.HEIGHT; y += EntityVault.EntityVaultCell.HEIGHT) {
                g.drawLine(x, y, x + EntityVault.EntityVaultCell.WIDTH, y);
                g.drawLine(x, y, x, y + EntityVault.EntityVaultCell.HEIGHT);
                int cell = ((int) (x / EntityVault.EntityVaultCell.WIDTH + y / EntityVault.EntityVaultCell.HEIGHT * vault.xCells));
                g.drawString("items=" + vault.cells[cell].getEntities().size(), x + 15, y + 15);
            }
        }
    }
}