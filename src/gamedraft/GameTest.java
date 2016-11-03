package gamedraft;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Timothy Paler
 */


public class GameTest extends StateBasedGame {

    // Display Resolution
    public static int displayWidth = 0;
    public static int displayHeight = 0;


    public static int gameScore = 0;
    public static int lives = 5;

    // Constructor
    public GameTest (String title) {
        super(title);
    }


    public static void main(String args[]) throws SlickException{
        AppGameContainer app = new AppGameContainer(new GameTest("Setup Test"));
        displayWidth = app.getScreenWidth();
        displayHeight = app.getScreenHeight();

        app.setTitle("BeatBitBeat");
        app.setDisplayMode(displayWidth, displayHeight, false);
        app.setTargetFrameRate(60);
        app.setAlwaysRender(true);

        
        app.start();
    }

    public void initStatesList(GameContainer container) throws SlickException {
        this.addState(new GameState());
        this.addState(new GameOverState());
    }

   
}

