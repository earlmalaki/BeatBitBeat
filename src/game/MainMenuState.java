/**
 * Albores, Allyssa
 * Bedio, Aiden Justin
 * Malaki, Earl Timothy
 * Paler, Timothy River
 *
 * BSCS - II | UP - Cebu
 * CMSC22 - OOP
 * Final Project
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
 * Note:
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

public class MainMenuState extends BasicGameState implements KeyListener{

    // Images declaration
    private Image imageBackground;
    private Image imageBtnStart;
    private Image imageBtnOptions;
    private Image imageBtnCredits;
    private Image imageBtnExit;
    private Image imageIndicator;

    private int spacingOfBtns;      // fixed spacing of each button
    private int indexOfSelectedState;
    private boolean enterPressed;

    private int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private int displayHeight = BeatBitBeatMain.getDisplayHeight();

    private Coordinate coordIndicator;      // coord for the selection indicator
    private Coordinate coordBtnStart;
    private Coordinate coordBtnOptions;
    private Coordinate coordBtnCredits;
    private Coordinate coordBtnExit;

    // Animation for background
    private SpriteSheet spriteBG;
    private Animation animateSpriteBG;

    // Audio declaration
    private static Music audioMusicMainMenu;
    private Audio soundPressArrows;
    private Audio soundPressEnter;

    // MainMenuState.java state ID = 0
    public int getID(){
        return BeatBitBeatMain.getMainMenu();
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{

        // load resources and initialize objects
        // TODO: Replace correct files and filename
//        imageBackground = new Image("Assets/mainMenuSprite.gif");
        imageBtnStart = new Image("Assets/MainMenuBtn.png");
        imageBtnOptions = new Image("Assets/MainMenuBtn.png");
        imageBtnCredits = new Image("Assets/MainMenuBtn.png");
        imageBtnExit = new Image("Assets/MainMenuBtn.png");
        imageIndicator = new Image("Assets/MainMenuIndicator.png");

        // other btns depend on BtnStart's Y position
        spacingOfBtns = ( (imageBtnStart.getHeight() / 2) * 3);
        coordBtnStart = new Coordinate(displayWidth - 500, (imageBtnStart.getHeight() * 2) );
        coordBtnOptions = new Coordinate(displayWidth - 500, coordBtnStart.getY() + spacingOfBtns );
        coordBtnCredits = new Coordinate(displayWidth - 500, coordBtnOptions.getY() + spacingOfBtns );
        coordBtnExit = new Coordinate(displayWidth - 500, coordBtnCredits.getY() + spacingOfBtns );

        coordIndicator = new Coordinate(coordBtnStart.getX() - 100, coordBtnStart.getY());

        indexOfSelectedState = -1;
        enterPressed = false;

        // TODO: Replace correct files and filename
        spriteBG = new SpriteSheet("Assets/sprite.png", 533, 300); //ref, tw, th, spacing
        animateSpriteBG = new Animation(spriteBG, 100);     // spritesheet, duration


        try {
            // TODO: Replace correct music and filename
            audioMusicMainMenu = new Music("Assets/State Music/Main Menu Music.ogg");
            audioMusicMainMenu.loop();

            // TODO: Replace correct sound effects and filename
            soundPressArrows = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/pressArrowMainMenu.ogg"));
            soundPressEnter = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/pressEnterMainMenu.ogg"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    int delta;  // for printing. temporary
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
        this.delta = delta;  // for printing. temporary


        if (enterPressed){
            if (coordIndicator.getY() == coordBtnStart.getY()) {    // if indicator is pointing to Start btn
                indexOfSelectedState = BeatBitBeatMain.getCharacterSelection();     // get fixed ID for state
            } else if (coordIndicator.getY() == coordBtnOptions.getY()) {     // if indicator is pointing to options btn
                indexOfSelectedState = BeatBitBeatMain.getOptions();
            } else if (coordIndicator.getY() == coordBtnCredits.getY()) {     // if indicator is pointing to credits btn
                indexOfSelectedState = BeatBitBeatMain.getCredits();
            } else if (coordIndicator.getY() == coordBtnExit.getY()) {        // if indicator is pointing to exit btn
                System.exit(0);
            }
            enterPressed = false;
            // enter state indicated by indexOfSelectedState
            sbg.enterState(indexOfSelectedState, new FadeOutTransition(), new FadeInTransition());
        }

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
//        imageBackground.draw(0f, 0f);
        imageBtnStart.draw(coordBtnStart.getX(), coordBtnStart.getY());
        imageBtnOptions.draw(coordBtnOptions.getX(), coordBtnOptions.getY());
        imageBtnCredits.draw(coordBtnCredits.getX(), coordBtnCredits.getY());
        imageBtnExit.draw(coordBtnExit.getX(), coordBtnExit.getY());
        imageIndicator.draw(coordIndicator.getX(), coordIndicator.getY());

        animateSpriteBG.draw(0, 0);

        g.drawString("DELTA = "+delta, 100, 100);

    }



    @Override
    public void keyPressed(int key, char pressedKey) {
        if (key == Input.KEY_UP) {
            soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);

            if (coordIndicator.getY() != coordBtnStart.getY()) {    // if indicator is inside bounds
                coordIndicator.setY(coordIndicator.getY() - spacingOfBtns);     // indicator moves by the fixed Spacing
            }


        } else if (key == Input.KEY_DOWN) {
            soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);

            if (coordIndicator.getY() != coordBtnExit.getY()) {
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

    public static void stopMusic() {
        audioMusicMainMenu.stop();
    }




}
