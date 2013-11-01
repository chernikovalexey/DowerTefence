package com.twopeople.td.state;

import com.twopeople.td.gui.TowerIcons;
import com.twopeople.td.world.Camera;
import com.twopeople.td.world.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Alexey
 * At 12:41 AM on 9/20/13
 */

public class GameState extends BasicGameState {
    private Camera camera;
    private World world;

    private TowerIcons towerIcons;

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        camera = new Camera(gameContainer.getWidth(), gameContainer.getHeight());
        world = new World(this);
        towerIcons = new TowerIcons(world);
    }

    public Camera getCamera() {
        return camera;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        camera.update(delta);
        world.update(gameContainer, delta);
        towerIcons.update(gameContainer, delta);

        Input input = gameContainer.getInput();
        float mx = 200;//input.getMouseX();
        float my = 200;//input.getMouseY();
        if (gameContainer.hasFocus()) {
            if (input.isKeyDown(Input.KEY_LEFT) || mx < 10) {
                camera.moveX(-1f);
            }
            if (input.isKeyDown(Input.KEY_RIGHT) || mx > camera.getScreenWidth() - 10) {
                camera.moveX(1f);
            }
            if (input.isKeyDown(Input.KEY_UP) || my < 10) {
                camera.moveY(-1f);
            }
            if (input.isKeyDown(Input.KEY_DOWN) || my > camera.getScreenHeight() - 10) {
                camera.moveY(1f);
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        world.render(gameContainer, g);
        towerIcons.render(gameContainer, g);
        g.setColor(Color.white);
        g.drawString("money=" + world.getCM().getMoney(), gameContainer.getWidth() - 100, 10);
    }
}