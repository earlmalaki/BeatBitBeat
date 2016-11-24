package game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class OptionsState extends BasicGameState implements KeyListener {

    private Coordinate[] coordsOptions;
    private static final int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private static final int displayHeight = BeatBitBeatMain.getDisplayHeight();

    private boolean pressedEnter = false;
    private boolean pressedEsc = false;
    private int indexIndicator = 0;

    private Image imageBG;
    private Image imageCheck;
    private Image imageIndicator;
    private Image[] imageSFXVolume;

    @Override
    public int getID() {
        return BeatBitBeatMain.getOptions();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        imageBG = new Image("Assets/Graphics/Options/Options BG.png");
        imageCheck = new Image("Assets/Graphics/Options/Check.png");
        imageIndicator = new Image("Assets/Graphics/Options/Indicator.png");
        imageSFXVolume = new Image[]{
                new Image("Assets/Graphics/Options/Volume 0.png"),
                new Image("Assets/Graphics/Options/Volume 1.png"),
                new Image("Assets/Graphics/Options/Volume 2.png"),
                new Image("Assets/Graphics/Options/Volume 3.png"),
                new Image("Assets/Graphics/Options/Volume 4.png"),
                new Image("Assets/Graphics/Options/Volume 5.png"),
                new Image("Assets/Graphics/Options/Volume 6.png"),
                new Image("Assets/Graphics/Options/Volume 7.png"),
                new Image("Assets/Graphics/Options/Volume 8.png"),
                new Image("Assets/Graphics/Options/Volume 9.png"),
        };

        coordsOptions = new Coordinate[]{
                new Coordinate((displayWidth / 2) - 35, 150),
                new Coordinate((displayWidth / 2) - 35, 260),
                new Coordinate((displayWidth / 2) - 35, 370),
                new Coordinate((displayWidth / 2) - 35, 480)
        };

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
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

        if (BeatBitBeatMain.isMenuMusicOn()) {
            imageCheck.draw(coordsOptions[0].getX(), coordsOptions[0].getY());
        }

        if (BeatBitBeatMain.isSFXOn()) {
            imageCheck.draw(coordsOptions[1].getX(), coordsOptions[1].getY());
        }

        imageIndicator.draw(coordsOptions[indexIndicator].getX(), coordsOptions[indexIndicator].getY());

        switch (BeatBitBeatMain.getVolumeSFX()) {
            case 0:
                imageSFXVolume[0].draw(coordsOptions[2].getX() + 20, coordsOptions[2].getY());
                break;
            case 1:
                imageSFXVolume[1].draw(coordsOptions[2].getX() + 20 + 5, coordsOptions[2].getY());
                break;
            case 2:
                imageSFXVolume[2].draw(coordsOptions[2].getX() + 20 + 10, coordsOptions[2].getY());
                break;
            case 3:
                imageSFXVolume[3].draw(coordsOptions[2].getX() + 20 + 15, coordsOptions[2].getY());
                break;
            case 4:
                imageSFXVolume[4].draw(coordsOptions[2].getX() + 20 + 20, coordsOptions[2].getY());
                break;
            case 5:
                imageSFXVolume[5].draw(coordsOptions[2].getX() + 20 + 25, coordsOptions[2].getY());
                break;
            case 6:
                imageSFXVolume[6].draw(coordsOptions[2].getX() + 20 + 30, coordsOptions[2].getY());
                break;
            case 7:
                imageSFXVolume[7].draw(coordsOptions[2].getX() + 20 + 35, coordsOptions[2].getY());
                break;
            case 8:
                imageSFXVolume[8].draw(coordsOptions[2].getX() + 20 + 40, coordsOptions[2].getY());
                break;
            case 9:
                imageSFXVolume[9].draw(coordsOptions[2].getX() + 20 + 45, coordsOptions[2].getY());
                break;
            default:
                break;

        }
    }

    @Override
    public void keyPressed(int key, char pressedKey) {
        if (key == Input.KEY_UP) {
            if (indexIndicator != 0) {
                indexIndicator--;
            }
        }

        if (key == Input.KEY_DOWN) {
            if (indexIndicator != coordsOptions.length - 1) {
                indexIndicator++;
            }
        }

        if (key == Input.KEY_ENTER) {
            pressedEnter = true;
        }

        // if at sfx volume rocker
        if (indexIndicator == 3) {

            if (key == Input.KEY_LEFT) {
                // decrease sfx volume
                if (BeatBitBeatMain.getVolumeSFX() > 0) {
                    BeatBitBeatMain.setVolumeSFX(BeatBitBeatMain.getVolumeSFX() - 1);
                }
            }
            if (key == Input.KEY_RIGHT) {
                // increase sfx volume
                if (BeatBitBeatMain.getVolumeSFX() < 9) {
                    BeatBitBeatMain.setVolumeSFX(BeatBitBeatMain.getVolumeSFX() + 1);
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
