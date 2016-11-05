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

    private Image imageBackground;
    private Image imageBtnStart;
    private Image imageBtnOptions;
    private Image imageBtnCredits;
    private Image imageBtnExit;
    private Image imageIndicator;

    private Coordinate coordIndicator;
    private int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private int displayHeight = BeatBitBeatMain.getDisplayHeight();

    private int changeState;
    private boolean enterPressed;

    private Coordinate coordBtnStart;
    private Coordinate coordBtnOptions;
    private Coordinate coordBtnCredits;
    private Coordinate coordBtnExit;

    private SpriteSheet spriteBG;
    private Animation animateSpriteBG;


    private static Audio audioMusicMainMenu;
    private Audio soundPressArrows;
    private Audio soundPressEnter;


    public int getID(){
        return BeatBitBeatMain.getMainMenu();
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{

        // load resources and initialize objects
        imageBackground = new Image("Assets/mainMenuSprite.gif");
        // TODO: Replace correct files and filename
        imageBtnStart = new Image("Assets/player1.jpg");
        imageBtnOptions = new Image("Assets/player1.jpg");
        imageBtnCredits = new Image("Assets/player1.jpg");
        imageBtnExit = new Image("Assets/player1.jpg");
        imageIndicator = new Image("Assets/player1.jpg");

        coordBtnStart = new Coordinate(((displayWidth / 2) - (imageBtnStart.getWidth() / 2)), ((displayHeight / 2) + (imageBtnStart.getHeight() / 2) * 2));
        coordBtnOptions = new Coordinate(((displayWidth / 2) - (imageBtnOptions.getWidth() / 2)), ((displayHeight / 2) + (imageBtnOptions.getHeight() / 2) * 3));
        coordBtnCredits = new Coordinate(((displayWidth / 2) - (imageBtnCredits.getWidth() / 2)), ((displayHeight / 2) + (imageBtnCredits.getHeight() / 2) * 4));
        coordBtnExit = new Coordinate(((displayWidth / 2) - (imageBtnExit.getWidth() / 2)), ((displayHeight / 2) + (imageBtnExit.getHeight() / 2) * 5));

        coordIndicator = new Coordinate((displayWidth / 2) - (imageBtnStart.getWidth() / 2) - 20, coordBtnStart.getY());

        changeState = -1;
        enterPressed = false;

        spriteBG = new SpriteSheet("Assets/sprite.png", 533, 300); //ref, tw, th, spacing
        animateSpriteBG = new Animation(spriteBG, 100);


        try {
            audioMusicMainMenu = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/State Music/Romani.ogg"));
            audioMusicMainMenu.playAsMusic(1.0f, 1.0f, false);

            soundPressArrows = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/pressArrowMainMenu.ogg"));
            soundPressEnter = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/pressEnterMainMenu.ogg"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    int delta;  // for printing. temporary
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
        this.delta = delta;  // for printing. temporary


        if (enterPressed){
            if (coordIndicator.getY() == coordBtnStart.getY()) {    // if indicator is pointing to Start btn
                changeState = BeatBitBeatMain.getCharacterSelection();
            } else if (coordIndicator.getY() == coordBtnOptions.getY()) {     // if indicator is pointing to options btn
                changeState = BeatBitBeatMain.getOptions();
            } else if (coordIndicator.getY() == coordBtnCredits.getY()) {     // if indicator is pointing to credits btn
                changeState = BeatBitBeatMain.getCredits();
            } else if (coordIndicator.getY() == coordBtnExit.getY()) {        // if indicator is pointing to exit btn
                System.exit(0);
            }
            enterPressed = false;
            // enter state indicated by changeState
            sbg.enterState(changeState, new FadeOutTransition(), new FadeInTransition());
        }

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        imageBackground.draw(0f, 0f);
        imageBtnStart.draw(coordBtnStart.getX(), coordBtnStart.getY());
        imageBtnOptions.draw(coordBtnOptions.getX(), coordBtnOptions.getY());
        imageBtnCredits.draw(coordBtnCredits.getX(), coordBtnCredits.getY());
        imageBtnExit.draw(coordBtnExit.getX(), coordBtnExit.getY());
        imageIndicator.draw(coordIndicator.getX(), coordIndicator.getY());

        g.drawRect(coordIndicator.getX(), coordIndicator.getY(), 10, 10);

        animateSpriteBG.draw(0, 0);

        g.drawString("DELTA = "+delta, 100, 100);


    }



    @Override
    public void keyPressed(int key, char pressedKey) {
        if (key == Input.KEY_UP) {
            soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);

            if (coordIndicator.getY() != coordBtnStart.getY()) {
                coordIndicator.setY(coordIndicator.getY() - (imageBtnStart.getHeight() / 2));
            }


        } else if (key == Input.KEY_DOWN) {
            soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);

            if (coordIndicator.getY() != coordBtnExit.getY()) {
                coordIndicator.setY(coordIndicator.getY() + (imageBtnExit.getHeight() / 2));
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
