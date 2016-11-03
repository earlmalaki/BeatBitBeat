package master;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Earl on 20/10/2016.
 */
public class BeatBitBeatMain extends StateBasedGame{

    private static String gameName = "Beat Bit Beat";
    private static int mainMenu = 0;
    private static int characterSelection = 1;
    private static int gameProper = 2;
    private static int gameOver = 3;


    // Display Resolution
    public static int displayWidth = 0;
    public static int displayHeight = 0;


    public static int gameScore = 0;
    public static int lives = 5;

    public BeatBitBeatMain(String gameName){
        super(gameName);
    }


    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new BeatBitBeatMain(gameName));
        displayWidth = app.getScreenWidth();
        displayHeight = app.getScreenHeight();

        app.setTitle("BeatBitBeat");
        app.setDisplayMode(displayWidth, displayHeight, false);
        app.setTargetFrameRate(60);
        app.setAlwaysRender(true);

        app.start();
    }

    public void initStatesList(GameContainer gc) throws SlickException{

        this.addState(new MainMenuState());
        this.addState(new CharacterSelectionState());
        this.addState(new GameProperState());
        this.addState(new GameOverState());
//
//        this.getState(mainMenu).init(gc, this);
//        this.getState(characterSelection).init(gc, this);
//        this.getState(gameProper).init(gc, this);
//        this.getState(gameOver).init(gc, this);
        this.enterState(mainMenu);
    }
}

















