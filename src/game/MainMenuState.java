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
 * <p>
 * To Do:
 * - Put wallpaper file
 * - Put button icon files
 * - Put final music file and loop
 * - finalize positioning of buttons after putting in final graphics
 * <p>
 * game.Note:
 * - OptionsState and CreditsState is still empty. Work on this soon. Prioritize MVP first.
 * - Button icons should be of same dimensions for correct positioning
 * <p>
 * Done:
 * - Key Listener for buttons(Start, Options, Credits, Exit)
 * - Image or Sprite handler for background
 * - Intro music
 * <p>
 * To Do:
 * - Put wallpaper file
 * - Put button icon files
 * - Put final music file and loop
 * - finalize positioning of buttons after putting in final graphics
 * <p>
 * game.Note:
 * - OptionsState and CreditsState is still empty. Work on this soon. Prioritize MVP first.
 * - Button icons should be of same dimensions for correct positioning
 */

/**
 * Done:
 * - Key Listener for buttons(Start, Options, Credits, Exit)
 * - Image or Sprite handler for background
 * - Intro music
 *
 * To Do:
 * - Put wallpaper file
 * - Put button icon files
 * - Put final music file and loop
 * - finalize positioning of buttons after putting in final graphics
 *
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

    // Images declaration
    private Image imageBackground;
    private Image imageIndicator;
    private Coordinate coordIndicator;      // coord for the selection indicator

    private Image[] imagesButtons;
    private Coordinate[] coordsButtons;

    private int spacingOfBtns;      // fixed spacing of each button
    private int indexOfSelectedState;
    private boolean enterPressed;

    private int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private int displayHeight = BeatBitBeatMain.getDisplayHeight();


    // Animation for background
    private Animation animateSpriteBG;

    // Audio declaration
    private static Music audioMusicMainMenu;
    private Audio soundPressArrows;
    private Audio soundPressEnter;

    // MainMenuState.java state ID = 0
    public int getID() {
        return BeatBitBeatMain.getMainMenu();
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        // load resources and initialize objects
        // TODO: Replace correct files and filename
        imageIndicator = new Image("Assets/Graphics/Main Menu/Indicator.png");

        imagesButtons = new Image[]{
                new Image("Assets/Graphics/Main Menu/Start Button.png"),
                new Image("Assets/Graphics/Main Menu/Options Button.png"),
                new Image("Assets/Graphics/Main Menu/Credits Button.png"),
                new Image("Assets/Graphics/Main Menu/Exit Button.png"),
        };

        // other btns depend on BtnStart's Y position
        spacingOfBtns = ((imagesButtons[0].getHeight() / 2) * 3);
        coordsButtons = new Coordinate[]{
                new Coordinate(displayWidth - 500, (imagesButtons[0].getHeight() * 2)),
                new Coordinate(displayWidth - 500, (imagesButtons[0].getHeight() * 2) + spacingOfBtns),
                new Coordinate(displayWidth - 500, (imagesButtons[0].getHeight() * 2) + spacingOfBtns * 2),
                new Coordinate(displayWidth - 500, (imagesButtons[0].getHeight() * 2) + spacingOfBtns * 3)
        };

        coordIndicator = new Coordinate(coordsButtons[0].getX() - 100, coordsButtons[0].getY());

        indexOfSelectedState = 0;
        enterPressed = false;

        // TODO: Replace correct files and filename
        SpriteSheet spriteBG = new SpriteSheet("Assets/Graphics/Main Menu/Main Menu BG.jpeg", 1366, 768, 1); //ref, tw, th, spacing
        animateSpriteBG = new Animation(spriteBG, 300);     // spritesheet, duration


        try {
            // TODO: Replace correct music and filename
            audioMusicMainMenu = new Music("Assets/State Music/Main Menu Music.ogg");
//            audioMusicMainMenu = new Music("Assets/State Music/Down with the Sickness.ogg");
//            audioMusicMainMenu = new Music("Assets/State Music/Down.ogg");
            audioMusicMainMenu.loop();

            // TODO: Replace correct sound effects and filename
            soundPressArrows = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/pressArrowMainMenu.ogg"));
            soundPressEnter = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/pressEnterMainMenu.ogg"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    int delta;  // for printing. temporary

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        this.delta = delta;  // for printing. temporary

        if (enterPressed) {
            enterPressed = false;

            if (coordIndicator.getY() == coordsButtons[0].getY()) {    // if indicator is pointing to Start btn
                indexOfSelectedState = BeatBitBeatMain.getCharacterSelection();     // get fixed ID for state
            } else if (coordIndicator.getY() == coordsButtons[1].getY()) {     // if indicator is pointing to options btn
                indexOfSelectedState = BeatBitBeatMain.getOptions();
            } else if (coordIndicator.getY() == coordsButtons[2].getY()) {     // if indicator is pointing to credits btn
                indexOfSelectedState = BeatBitBeatMain.getCredits();
            } else if (coordIndicator.getY() == coordsButtons[3].getY()) {        // if indicator is pointing to exit btn
                System.exit(0);
            }

            // enter state indicated by indexOfSelectedState
            sbg.enterState(indexOfSelectedState, new FadeOutTransition(), new FadeInTransition());
        }

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        animateSpriteBG.draw(0, 0);

        for (int i = 0; i < imagesButtons.length; i++) {
            imagesButtons[i].draw(coordsButtons[i].getX(), coordsButtons[i].getY());
        }
        imageIndicator.draw(coordIndicator.getX(), coordIndicator.getY());


        g.drawString("DELTA = " + delta, 100, 100);

    }


    @Override
    public void keyPressed(int key, char pressedKey) {
        if (key == Input.KEY_UP) {
            soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);

            if (coordIndicator.getY() != coordsButtons[0].getY()) {    // if indicator is inside bounds
                coordIndicator.setY(coordIndicator.getY() - spacingOfBtns);     // indicator moves by the fixed Spacing
            }


        } else if (key == Input.KEY_DOWN) {
            soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);

            if (coordIndicator.getY() != coordsButtons[3].getY()) {
                coordIndicator.setY(coordIndicator.getY() + spacingOfBtns);
            }
        } else if (key == Input.KEY_ENTER) {
            soundPressEnter.playAsSoundEffect(1.0f, 1.0f, false);
            enterPressed = true;

        }


    }

    @Override
    public void keyReleased(int key, char pressedKey) {

    }

    public static void pauseMusic() {
        audioMusicMainMenu.pause();
    }

    public static void resumeMusic() {
        audioMusicMainMenu.resume();
    }


}
