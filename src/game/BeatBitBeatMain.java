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
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.StateBasedGame;


public class BeatBitBeatMain extends StateBasedGame {

    private static final String gameName = "BeatBitBeat";

    // Game state IDs
    private static final int mainMenu = 0;
    private static final int characterSelection = 1;
    private static final int versusPreview = 2;
    private static final int gameProper = 3;
    private static final int gameOver = 4;
    private static final int options = 5;
    private static final int credits = 6;

    // Display Resolution
    private static final int displayWidth = 1280;
    private static final int displayHeight = 720;

    //Options
    private static float volumeSFX = 1;
    private static float volumeMusic = 1;

    private static boolean menuMusicOn = true;
    private static boolean SFXOn = true;

    private static Audio soundPressArrows;
    private static Audio soundPressEnter;


    // Constructor
    public BeatBitBeatMain(String gameName) {
        super(gameName);
    }


    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new BeatBitBeatMain(gameName));
        app.setTitle(gameName);                // Title for top bar

        if (((float) app.getScreenWidth() / (float) app.getScreenHeight()) == 1.77f) {
            app.setDisplayMode(displayWidth, displayHeight, true);  // fullscreen true for displays with the same ratio from game graphics
        } else {
            app.setDisplayMode(displayWidth, displayHeight, false);  // fullscreen false for displays with different ratio from game graphics
        }

        app.setVSync(true);                         // match monitor refresh rate, usually 60hz. thus 60fps. or use app.setTargetFrameRate(60);

        // fix delta, the time in between calls to update() method, to 15ms
        // critical for beatmap reading
        app.setMinimumLogicUpdateInterval(15);      // minimum delta of 15 ms
        app.setMaximumLogicUpdateInterval(15);      // maximum delta of 15 ms
        app.setAlwaysRender(true);

        app.start();


    }

    public void initStatesList(GameContainer gc) throws SlickException {

        // Instantiate each state and add to the list of states
        this.addState(new MainMenuState());
        this.addState(new CharacterSelectionState());
        this.addState(new VersusPreviewState());
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


    /*** SO Getters ***/
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

    public static float getVolumeSFX() {
        return volumeSFX;
    }

    public static void setVolumeSFX(float volumeSFX) {
        BeatBitBeatMain.volumeSFX = volumeSFX;
    }

    public static boolean isMenuMusicOn() {
        return menuMusicOn;
    }

    public static void setMenuMusicOn(boolean menuMusicOn) {
        BeatBitBeatMain.menuMusicOn = menuMusicOn;
    }

    public static boolean isSFXOn() {
        return SFXOn;
    }

    public static void setSFXOn(boolean SFXOn) {
        BeatBitBeatMain.SFXOn = SFXOn;
    }

    public static float getVolumeMusic() {
        return volumeMusic;
    }

    public static void setVolumeMusic(float volumeMusic) {
        BeatBitBeatMain.volumeMusic = volumeMusic;
    }

    public static Audio getSoundPressArrows() {
        return soundPressArrows;
    }

    public static void setSoundPressArrows(Audio sound) {
        soundPressArrows = sound;
    }

    public static Audio getSoundPressEnter() {
        return soundPressEnter;
    }

    public static void setSoundPressEnter(Audio sound) {
        soundPressEnter = sound;
    }

    public static void playKeySFX(int key) {
        if (key == Input.KEY_UP || key == Input.KEY_DOWN || key == Input.KEY_LEFT || key == Input.KEY_RIGHT) {
            soundPressArrows.playAsSoundEffect(1f, volumeSFX, false);
        }

        if (key == Input.KEY_ENTER) {
            soundPressEnter.playAsSoundEffect(1f, volumeMusic, false);
        }
    }

    /** EO Getters **/
}

















