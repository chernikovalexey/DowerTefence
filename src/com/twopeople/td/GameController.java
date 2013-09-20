package com.twopeople.td;

import com.twopeople.td.state.GameState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Alexey
 * At 10:57 PM on 9/17/13
 */

public class GameController extends StateBasedGame {
    public GameController() {
        super("Dower Tefence");
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(new GameState());

        enterState(1);
    }
}