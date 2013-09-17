package com.twopeople.td;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Created by Alexey
 * At 10:24 PM on 9/17/13
 */

public class GameStartup {
    public static void main(String[] args) {
        try {
            AppGameContainer game = new AppGameContainer(new GameController());
            game.setDisplayMode(800, 600, false);
            game.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}