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

    private Coordinate[] coordsOptions = new Coordinate[] {
            new Coordinate(460, 570),
            new Coordinate(697, 570),
    };

    private Coordinate[] coordsSFXVolume = new Coordinate[] {
            new Coordinate(484,130),
            new Coordinate(420,130),
            new Coordinate(440,130),
            new Coordinate(460,130),
            new Coordinate(480,130),
            new Coordinate(500,130),
            new Coordinate(510,130),
            new Coordinate(520,130),
            new Coordinate(530,130),
            new Coordinate(540,130),

    };

    private Coordinate[] coordsMusicVolume = new Coordinate[] {
            new Coordinate(660, 100),
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

        if (pressedEnter) {
            pressedEnter = false;
            switch (indexIndicator) {
                case 0:
                    // menu music toggle
                    if (BeatBitBeatMain.isMenuMusicOn()) {
                        BeatBitBeatMain.setMenuMusicOn(false);
                        MainMenuState.pauseMusic();
                    } else {
                        BeatBitBeatMain.setMenuMusicOn(true);
                        MainMenuState.resumeMusic();
                    }

                    break;
                case 1:
                    // Sfx toggle
                    if (BeatBitBeatMain.isSFXOn()) {
                        BeatBitBeatMain.setSFXOn(false);
                    } else {
                        BeatBitBeatMain.setSFXOn(true);
                    }

                    break;
                case 2:
                    if (!gameContainer.isFullscreen()) {
                        gameContainer.setFullscreen(true);
                    } else {
                        gameContainer.setFullscreen(false);
                    }
                    break;
                default:
                    break;
            }
        }

        if (pressedEsc) {
            pressedEsc = false;
            stateBasedGame.enterState(BeatBitBeatMain.getMainMenu(), new FadeOutTransition(), new FadeInTransition());
        }
    }


    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        imageBG.draw();

        graphics.setColor(Color.lightGray);

        graphics.drawString("X = " + xMouse + " Y = " + yMouse, 10, 50);


        graphics.fillRect(coordsSFXVolume[0].getX(), coordsSFXVolume[0].getY(), 75, coordsSFXVolume[(int) (BeatBitBeatMain.getVolumeSFX() * 1)].getY());

//        graphics.fillRect(coordsMusicVolume[0].getX(), coordsMusicVolume[0].getY(), 75, coordsMusicVolume[(int) (BeatBitBeatMain.getVolumeMusic() * 1)].getY());

        graphics.fillRect(coordsOptions[indexIndicator].getX(), coordsOptions[indexIndicator].getY(), 125, 2);

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

        if (indexIndicator == 0) {
            if (key == Input.KEY_DOWN) {
                // decrease sfx volume
                if (BeatBitBeatMain.getVolumeSFX() > 0) {
                    BeatBitBeatMain.setVolumeSFX((float) (BeatBitBeatMain.getVolumeSFX() - 0.10));
                }
            }
            if (key == Input.KEY_UP) {
                // increase sfx volume
                if (BeatBitBeatMain.getVolumeSFX() < 9) {
                    BeatBitBeatMain.setVolumeSFX((float) (BeatBitBeatMain.getVolumeSFX() + 0.10));
                }
            }
        } else if (indexIndicator == 1) {
            if (key == Input.KEY_DOWN) {
                // decrease sfx volume
                if (BeatBitBeatMain.getVolumeMusic() > 0) {
                    BeatBitBeatMain.setVolumeMusic((float) (BeatBitBeatMain.getVolumeMusic() - 0.10));
                }
            }
            if (key == Input.KEY_UP) {
                // increase sfx volume
                if (BeatBitBeatMain.getVolumeMusic() < 9) {
                    BeatBitBeatMain.setVolumeMusic((float) (BeatBitBeatMain.getVolumeMusic() + 0.10));
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
