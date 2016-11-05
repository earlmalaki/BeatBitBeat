package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class BeatBitBeatMain extends StateBasedGame{

    private static final String gameName = "Beat Bit Beat";
    private static final int mainMenu = 0;
    private static final int characterSelection = 1;
    private static final int gameProper = 2;
    private static final int gameOver = 3;
    private static final int options = 4;
    private static final int credits = 5;


    // Display Resolution
    private static int displayWidth = 0;
    private static int displayHeight = 0;


    public BeatBitBeatMain(String gameName){
        super(gameName);
    }


    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new BeatBitBeatMain(gameName));
        displayWidth = app.getScreenWidth();
        displayHeight = app.getScreenHeight();

        app.setTitle("BeatBitBeat");
        app.setDisplayMode(displayWidth, displayHeight, false);
        app.setVSync(true);     // matches monitor refresh rate. or use app.setTargetFrameRate(60);
        app.setMinimumLogicUpdateInterval(15);      // minimum delta of 15 ms
        app.setMaximumLogicUpdateInterval(20);      // maximum delta of 20 ms
        app.setAlwaysRender(true);

        app.start();
    }

    public void initStatesList(GameContainer gc) throws SlickException{

        this.addState(new MainMenuState());
        this.addState(new CharacterSelectionState());
        this.addState(new GameProperState());
        this.addState(new GameOverState());
        this.addState(new OptionsState());
        this.addState(new CreditsState());


//        this.getState(mainMenu).init(gc, this);
//        this.getState(characterSelection).init(gc, this);
//        this.getState(gameProper).init(gc, this);
//        this.getState(gameOver).init(gc, this);
//        this.getState(options).init(gc, this);
//        this.getState(credits).init(gc, this);

        this.enterState(mainMenu);
    }


    public static int getMainMenu() {
        return mainMenu;
    }

    public static int getCharacterSelection() {
        return characterSelection;
    }

    public static int getGameProper() {
        return gameProper;
    }

    public static int getGameOver() {
        return gameOver;
    }

    public static int getOptions() {
        return options;
    }

    public static int getCredits() {
        return credits;
    }

    public static int getDisplayWidth() {
        return displayWidth;
    }

    public static int getDisplayHeight() {
        return displayHeight;
    }
}

















