package game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class OptionsState extends BasicGameState implements KeyListener {

    private static final int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private static final int displayHeight = BeatBitBeatMain.getDisplayHeight();

    private boolean pressedEnter = false;
    private boolean pressedEsc = false;
    private int indexIndicator = 0;

    private Image imageBG;

    private int indexVolSFX = 9;
    private int indexVolMusic = 9;

    private Coordinate[] coordsOptions = new Coordinate[]{
            new Coordinate(460, 527),
            new Coordinate(697, 527),
    };

    private Coordinate[] coordsSFXVolume = new Coordinate[]{
            new Coordinate(484, 160),
            new Coordinate(484, 185),
            new Coordinate(484, 210),
            new Coordinate(484, 235),
            new Coordinate(484, 260),
            new Coordinate(484, 285),
            new Coordinate(484, 310),
            new Coordinate(484, 335),
            new Coordinate(484, 360),
            new Coordinate(484, 385),
    };

    private Coordinate[] coordsMusicVolume = new Coordinate[]{
            new Coordinate(720, 160),
            new Coordinate(720, 185),
            new Coordinate(720, 210),
            new Coordinate(720, 235),
            new Coordinate(720, 260),
            new Coordinate(720, 285),
            new Coordinate(720, 310),
            new Coordinate(720, 335),
            new Coordinate(720, 360),
            new Coordinate(720, 385),
    };

    @Override
    public int getID() {
        return BeatBitBeatMain.getOptions();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        imageBG = new Image("Assets/Graphics/Options/Options BG.png");
    }


    float xMouse;
    float yMouse;

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

        Input input = gameContainer.getInput();
        xMouse = input.getMouseX();
        yMouse = input.getMouseY();

        if (pressedEsc) {
            pressedEsc = false;
            stateBasedGame.enterState(BeatBitBeatMain.getMainMenu(), new FadeOutTransition(), new FadeInTransition());
        }
    }


    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        imageBG.draw();

        graphics.setColor(Color.black);
        graphics.fillRect(coordsSFXVolume[0].getX(), 130, 77, 410 - coordsSFXVolume[indexVolSFX].getY());
        graphics.fillRect(coordsMusicVolume[0].getX(), 130, 77, 410 - coordsMusicVolume[indexVolMusic].getY());

        graphics.setColor(Color.white);
        graphics.fillRect(coordsOptions[indexIndicator].getX(), coordsOptions[indexIndicator].getY(), 125, 2);
        graphics.drawString("X = " + xMouse + " Y = " + yMouse, 10, 50);
    }

    @Override
    public void keyPressed(int key, char pressedKey) {

        if (key == Input.KEY_LEFT) {
            if (indexIndicator != 0) {
                indexIndicator--;
            }
        }

        if (key == Input.KEY_RIGHT) {
            if (indexIndicator != 1) {
                indexIndicator++;
            }
        }

        if (indexIndicator == 0) {      // SFX vol rocker
            if (key == Input.KEY_DOWN) {
                // decrease sfx volume
                if (indexVolSFX > 0) {
                    BeatBitBeatMain.setVolumeSFX(BeatBitBeatMain.getVolumeSFX() - 0.10f);
                    indexVolSFX--;
                    System.out.println(indexVolSFX);
                    System.out.println(BeatBitBeatMain.getVolumeSFX());
                }
            }
            if (key == Input.KEY_UP) {
                // increase sfx volume
                if (indexVolSFX < 9) {
                    BeatBitBeatMain.setVolumeSFX(BeatBitBeatMain.getVolumeSFX() + 0.10f);
                    indexVolSFX++;
                    System.out.println(indexVolSFX);
                    System.out.println(BeatBitBeatMain.getVolumeSFX());
                }
            }
        } else if (indexIndicator == 1) {   // music vol rocker
            if (key == Input.KEY_DOWN) {
                // decrease music volume
                if (indexVolMusic > 0) {
                    BeatBitBeatMain.setVolumeMusic(BeatBitBeatMain.getVolumeMusic() - 0.10f);
                    indexVolMusic--;
                }
            }
            if (key == Input.KEY_UP) {
                // increase music volume
                if (indexVolMusic < 9) {
                    BeatBitBeatMain.setVolumeMusic(BeatBitBeatMain.getVolumeMusic() + 0.10f);
                    indexVolMusic++;
                }
            }
        }

        if (key == Input.KEY_ESCAPE) {
            pressedEsc = true;
        }

    }

    @Override
    public void keyReleased(int key, char pressedKey) {

    }


}
