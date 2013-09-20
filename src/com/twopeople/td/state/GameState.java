package com.twopeople.td.state;

import com.twopeople.td.world.Camera;
import com.twopeople.td.world.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        camera = new Camera(gameContainer.getWidth(), gameContainer.getHeight());
        world = new World(this);
    }

    public Camera getCamera() {
        return camera;
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        camera.update(delta);
        world.update(gameContainer, delta);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        world.render(gameContainer, g);
    }
}