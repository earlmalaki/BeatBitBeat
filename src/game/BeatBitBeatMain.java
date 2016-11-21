/**
 * Albores, Allyssa
 * Bedio, Aiden Justin
 * Malaki, Earl Timothy
 * Paler, Timothy River
 * <p>
 * BSCS - II | UP - Cebu
 * CMSC22 - OOP
 * Final Project
 * <p>
 * NOTE:
 * - For now, prioritize work game MVP
 * - Put code documentation for readability
 * - Let others know what you're working on
 */


package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class BeatBitBeatMain extends StateBasedGame {

    private static final String gameName = "Beat Bit Beat";

    // Game state IDs
    private static final int mainMenu = 0;
    private static final int characterSelection = 1;
    private static final int versusPreview = 2;
    private static final int gameProper = 3;
    private static final int gameOver = 4;
    private static final int options = 5;
    private static final int credits = 6;

    // Display Resolution
    private static int displayWidth = 1280;
    private static int displayHeight = 800;

    // Constructor
    public BeatBitBeatMain(String gameName) {
        super(gameName);
    }


    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new BeatBitBeatMain(gameName));
//        displayWidth = app.getScreenWidth();
//        displayHeight = app.getScreenHeight();

        app.setTitle("BeatBitBeat");                // Title for top bar
        app.setDisplayMode(displayWidth, displayHeight, true);     // fullscreen false for now
        app.setVSync(true);                         // matches monitor refresh rate. or use app.setTargetFrameRate(60);
        app.setMinimumLogicUpdateInterval(15);      // minimum delta of 15 ms
        app.setMaximumLogicUpdateInterval(15);      // maximum delta of 15 ms
//        app.setAlwaysRender(true);                  // ???

        app.start();
    }

    public void initStatesList(GameContainer gc) throws SlickException {

        // Instantiate each state and add to the list of states
        this.addState(new MainMenuState());
        this.addState(new CharacterSelectionState());
        this.addState(new GameProperState());
        this.addState(new GameOverState());
        this.addState(new OptionsState());
        this.addState(new CreditsState());

        // idk what these lines do so i commented em out
        // works the same with or without
        // someone explain
//        this.getState(mainMenu).init(gc, this);
//        this.getState(characterSelection).init(gc, this);
//        this.getState(gameProper).init(gc, this);
//        this.getState(gameOver).init(gc, this);
//        this.getState(options).init(gc, this);
//        this.getState(credits).init(gc, this);

        this.enterState(mainMenu);
    }


    /** SO Getters **/
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

    public static int getVersusPreview() {
        return versusPreview;
    }
    /** EO Getters **/
}

















