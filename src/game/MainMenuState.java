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
 * Done:
 * - Key Listener for buttons(Start, Options, Credits, Exit)
 * - Image or Sprite handler for background
 * - Intro music
 * - Put wallpaper file
 * - Put arrow icon files
 * - finalize positioning of buttons after putting in final graphics
 * <p>
 * To Do:
 * - Put final music
 * <p>
 * game.Note:
 * - OptionsState and CreditsState is still empty. Work on this soon. Prioritize MVP first.
 * - Button icons should be of same dimensions for correct positioning
 */

package game;

import org.newdawn.slick.*;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public class MainMenuState extends BasicGameState implements KeyListener {

    private float yIndicator;

    // Image array to contain png file for each arrow
    // arrow color changes per selected button thus needing different files
    private Image[] imagesArrows;

    // coordinate set of arrows
    // store fixed coords of arrows
    private Coordinate[] coordsArrows;

    // current focused button
    private int indexSelection = 0;

    private boolean enterPressed = false;

    private int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private int displayHeight = BeatBitBeatMain.getDisplayHeight();

    // Animation for background
    private Animation animateBGFire;

    // Audio declaration
    private static Music audioMusicMainMenu;
    private Audio soundPressArrows;
    private Audio soundPressEnter;

    private int indexOfSelectedState = 1;   // will hold the ID of the selected state. initialized to GameProperState ID

    // MainMenuState.java state ID = 0
    public int getID() {
        return BeatBitBeatMain.getMainMenu();
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        initializeImagesAndAnimations();
        initializeAudioAndMusic();

        // coordinate array for indicator positions
        // indicator positions are same with the positions of the 4 main buttons
        coordsArrows = new Coordinate[]{
                new Coordinate((displayWidth / 2) - 180, 368),
                new Coordinate((displayWidth / 2) - 180, 430),
                new Coordinate((displayWidth / 2) - 180, 495),
                new Coordinate((displayWidth / 2) - 180, 563)
        };

        // initialize indicator y position to the first button (start button)
        yIndicator = coordsArrows[0].getY();
    }

    int delta;  // for printing. temporary
    float xMouse;
    float yMouse;

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        this.delta = delta;  // for printing. temporary
        Input input = gc.getInput();
        xMouse = input.getMouseX();
        yMouse = input.getMouseY();

        // Enter selected state
        if (enterPressed) {
            enterPressed = false;

            if (yIndicator == coordsArrows[0].getY()) {    // if indicator is pointing to Start btn
                indexOfSelectedState = BeatBitBeatMain.getCharacterSelection();     // get  ID for state
            } else if (yIndicator == coordsArrows[1].getY()) {     // if indicator is pointing to options btn
                indexOfSelectedState = BeatBitBeatMain.getOptions();
            } else if (yIndicator == coordsArrows[2].getY()) {     // if indicator is pointing to credits btn
                indexOfSelectedState = BeatBitBeatMain.getCredits();
            } else if (yIndicator == coordsArrows[3].getY()) {        // if indicator is pointing to exit btn
                System.exit(0);
            }

            // enter state indicated by indexOfSelectedState
            sbg.enterState(indexOfSelectedState, new FadeOutTransition(), new FadeInTransition());
        }   // end of if (enterPressed)


    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        animateBGFire.draw(0, 0);

        imagesArrows[indexSelection].draw(coordsArrows[indexSelection].getX(), coordsArrows[indexSelection].getY());

        g.drawString("DELTA = " + delta, 10, 30);
        g.drawString("X = " + xMouse + " Y = " + yMouse, 10, 50);
    }


    @Override
    public void keyPressed(int key, char pressedKey) {
        playSFXKeyPress(key);       // play SFX according to KEY indicated by key argument
        if (key == Input.KEY_UP) {
            if (yIndicator != coordsArrows[0].getY()) {    // if indicator is inside bounds
                indexSelection--;
                yIndicator = coordsArrows[indexSelection].getY();
            }
        }
        if (key == Input.KEY_DOWN) {
            if (yIndicator != coordsArrows[3].getY()) {
                indexSelection++;
                yIndicator = coordsArrows[indexSelection].getY();
            }
        }
        if (key == Input.KEY_ENTER) {
            enterPressed = true;
        }
    }

    public static void pauseMusic() {
        audioMusicMainMenu.pause();
    }

    public static void resumeMusic() {
        audioMusicMainMenu.resume();
    }

    public static void playMusic() {
        audioMusicMainMenu.play();
    }

    private void initializeImagesAndAnimations() throws SlickException {
        imagesArrows = new Image[]{
                new Image("Assets/Graphics/Main Menu/Start Arrow.png"),
                new Image("Assets/Graphics/Main Menu/Options Arrow.png"),
                new Image("Assets/Graphics/Main Menu/Credits Arrow.png"),
                new Image("Assets/Graphics/Main Menu/Exit Arrow.png"),
        };

        Image[] imagesBG = new Image[] {
                new Image("Assets/Graphics/Main Menu/Frames/Home Screen0000.png"),
                new Image("Assets/Graphics/Main Menu/Frames/Home Screen0001.png"),
                new Image("Assets/Graphics/Main Menu/Frames/Home Screen0002.png"),
                new Image("Assets/Graphics/Main Menu/Frames/Home Screen0003.png"),
                new Image("Assets/Graphics/Main Menu/Frames/Home Screen0004.png"),
                new Image("Assets/Graphics/Main Menu/Frames/Home Screen0005.png"),
                new Image("Assets/Graphics/Main Menu/Frames/Home Screen0006.png"),
                new Image("Assets/Graphics/Main Menu/Frames/Home Screen0007.png"),
                new Image("Assets/Graphics/Main Menu/Frames/Home Screen0008.png"),
                new Image("Assets/Graphics/Main Menu/Frames/Home Screen0009.png"),
        };

        // load spritesheet and feed to Animation constructor
        animateBGFire = new Animation(imagesBG, 200);     // spritesheet, duration
    }

    private void initializeAudioAndMusic() throws SlickException {
        try {
            // TODO: Replace correct music and filename
            audioMusicMainMenu = new Music("Assets/State Music/Down.ogg");
//            audioMusicMainMenu.loop();  // play in loop the bg music

            // TODO: Replace correct sound effects and filename
            soundPressArrows = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/pressArrowMainMenu.ogg"));
            soundPressEnter = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/pressEnterMainMenu.ogg"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playSFXKeyPress(int key) {
        if (BeatBitBeatMain.isSFXOn()) {
            if (key == Input.KEY_UP || key == Input.KEY_DOWN || key == Input.KEY_LEFT || key == Input.KEY_RIGHT) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
            }
            if (key == Input.KEY_ENTER) {
//                soundPressEnter.playAsSoundEffect(1.0f, 1.0f, false);
            }
        }
    }


}
