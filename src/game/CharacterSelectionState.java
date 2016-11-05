package game;

import game.monsters.Monster;
import org.newdawn.slick.*;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

// Pick player, monster, music
public class CharacterSelectionState extends BasicGameState implements KeyListener {

    private Monster monsterP1;
    private Monster monsterP2;

    private Image imageBackground;

    private SpriteSheet spriteMonster1;
    private SpriteSheet spriteMonster2;
    private SpriteSheet spriteMonster3;
    private SpriteSheet spriteMonster4;
    private SpriteSheet spriteMonster5;
    private SpriteSheet spriteMonster6;

    private Animation animateMonster1;
    private Animation animateMonster2;
    private Animation animateMonster3;
    private Animation animateMonster4;
    private Animation animateMonster5;
    private Animation animateMonster6;

    private Coordinate coordP1Monster;
    private Coordinate coordP2Monster;

    private int caseMonsterAnimation;

    private Image imageMonster1;
    private Image imageMonster2;
    private Image imageMonster3;
    private Image imageMonster4;
    private Image imageMonster5;
    private Image imageMonster6;

    private Image imageMonsterIndicator;

    private Coordinate coordImageMonster1;
    private Coordinate coordImageMonster2;
    private Coordinate coordImageMonster3;
    private Coordinate coordImageMonster4;
    private Coordinate coordImageMonster5;
    private Coordinate coordImageMonster6;

    private Coordinate coordMonsterIndicator;

    private boolean enterPressed;

    private Audio soundPressArrows;
    private Audio soundPressEnter;

    private boolean monsterPicking;
    private boolean songPicking;
    private boolean p1Picking;
    private boolean p2Picking;

    private Image[] imagesSongArt;
    private int indexImageSongArt;
    private Coordinate coordImageSongArt;
    private Audio[] audioSongChoices;

    private Coordinate coordBtnGame;
    private Image imageBtnGame;

    private int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private int displayHeight = BeatBitBeatMain.getDisplayHeight();

    private Audio audioMusicCharSelection;
    private Audio selectedMusic;

    public int getID() {
        return BeatBitBeatMain.getCharacterSelection();
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        imageBackground = new Image("Assets/bgimage.jpg");
        spriteMonster1 = new SpriteSheet("Assets/player1.jpg", 100, 100); //ref, tw, th, spacing
        spriteMonster2 = new SpriteSheet("Assets/player1.jpg", 100, 100);
        spriteMonster3 = new SpriteSheet("Assets/player1.jpg", 100, 100);
        spriteMonster4 = new SpriteSheet("Assets/player1.jpg", 100, 100);
        spriteMonster5 = new SpriteSheet("Assets/player1.jpg", 100, 100);
        spriteMonster6 = new SpriteSheet("Assets/player1.jpg", 100, 100);

        animateMonster1 = new Animation(spriteMonster1, 50);
        animateMonster2 = new Animation(spriteMonster2, 50);
        animateMonster3 = new Animation(spriteMonster3, 50);
        animateMonster4 = new Animation(spriteMonster4, 50);
        animateMonster5 = new Animation(spriteMonster5, 50);
        animateMonster6 = new Animation(spriteMonster6, 50);

        coordP1Monster = new Coordinate(100, 400);
        coordP2Monster = new Coordinate(displayWidth - spriteMonster1.getWidth() - 100, 400);

        caseMonsterAnimation = 0;

        imageMonster1 = new Image("Assets/player1.jpg");
        imageMonster2 = new Image("Assets/player1.jpg");
        imageMonster3 = new Image("Assets/player1.jpg");
        imageMonster4 = new Image("Assets/player1.jpg");
        imageMonster5 = new Image("Assets/player1.jpg");
        imageMonster6 = new Image("Assets/player1.jpg");

        imageMonsterIndicator = new Image("Assets/player1.jpg");

        coordImageMonster1 = new Coordinate((displayWidth / 2) - (imageMonster1.getWidth() * 2), (displayHeight / 2) - (imageMonster1.getHeight() * 3));
        coordImageMonster2 = new Coordinate((displayWidth / 2) - (imageMonster2.getWidth() / 2), (displayHeight / 2) - (imageMonster2.getHeight() * 3));
        coordImageMonster3 = new Coordinate((displayWidth / 2) + (imageMonster3.getWidth()), (displayHeight / 2) - (imageMonster3.getHeight() * 3));
        coordImageMonster4 = new Coordinate((displayWidth / 2) - (imageMonster4.getWidth() * 2), (displayHeight / 2) - (imageMonster4.getHeight() / 2) * 3);
        coordImageMonster5 = new Coordinate((displayWidth / 2) - (imageMonster5.getWidth() / 2), (displayHeight / 2) - (imageMonster5.getHeight() / 2) * 3);
        coordImageMonster6 = new Coordinate((displayWidth / 2) + (imageMonster6.getWidth()), (displayHeight / 2) - (imageMonster6.getHeight() / 2) * 3);

        coordMonsterIndicator = new Coordinate(coordImageMonster2.getX(), coordImageMonster2.getY());

        imageBtnGame = new Image("Assets/player1.jpg");
        coordBtnGame = new Coordinate((displayWidth / 2) - (imageBtnGame.getWidth() / 2), displayHeight - 200);

        enterPressed = false;
        monsterPicking = true;
        songPicking = false;
        p1Picking = true;
        p2Picking = false;

        indexImageSongArt = 0;
        imagesSongArt = new Image[]{
                new Image("Songs/Goin' Under/bn.png"),
                new Image("Songs/MechaTribe Assault/bn.png"),
                new Image("Songs/Springtime/bn.png"),
        };
        coordImageSongArt = new Coordinate((displayWidth / 2) - (imagesSongArt[0].getWidth() / 2), displayHeight - (imagesSongArt[0].getHeight() * 2));

        try {
            soundPressArrows = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/pressArrowMainMenu.ogg"));
            soundPressEnter = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/pressEnterMainMenu.ogg"));


            audioSongChoices = new Audio[]{
                    AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Songs/Goin' Under/Goin' Under.ogg")),
                    AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Songs/MechaTribe Assault/Mecha-Tribe Assault.ogg")),
                    AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Songs/Springtime/Kommisar - Springtime.ogg"))
            };

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {


        if (monsterPicking) {
            if (p1Picking) {    // if player 1's turn to pick
                if (enterPressed) {
                    if (coordMonsterIndicator.equalTo(coordImageMonster1)) {
                        System.out.println("1");
                        caseMonsterAnimation = 1;
                    } else if (coordMonsterIndicator.equalTo(coordImageMonster2)) {
                        System.out.println("2");
                        caseMonsterAnimation = 2;
                    } else if (coordMonsterIndicator.equalTo(coordImageMonster3)) {
                        System.out.println("3");
                        caseMonsterAnimation = 3;
                    } else if (coordMonsterIndicator.equalTo(coordImageMonster4)) {
                        System.out.println("4");
                        caseMonsterAnimation = 4;
                    } else if (coordMonsterIndicator.equalTo(coordImageMonster5)) {
                        System.out.println("5");
                        caseMonsterAnimation = 5;
                    } else if (coordMonsterIndicator.equalTo(coordImageMonster6)) {
                        System.out.println("6");
                        caseMonsterAnimation = 6;
                    }
                    enterPressed = false;
                    p1Picking = false;
                    p2Picking = true;
                } // EO if(enterPressed)


            } else if (p2Picking) {    // if player 2's turn to pick
                if (enterPressed) {
                    if (coordMonsterIndicator.equalTo(coordImageMonster1)) {
                        System.out.println("1");
                        caseMonsterAnimation = 1;
                    } else if (coordMonsterIndicator.equalTo(coordImageMonster2)) {
                        System.out.println("2");
                        caseMonsterAnimation = 2;
                    } else if (coordMonsterIndicator.equalTo(coordImageMonster3)) {
                        System.out.println("3");
                        caseMonsterAnimation = 3;
                    } else if (coordMonsterIndicator.equalTo(coordImageMonster4)) {
                        System.out.println("4");
                        caseMonsterAnimation = 4;
                    } else if (coordMonsterIndicator.equalTo(coordImageMonster5)) {
                        System.out.println("5");
                        caseMonsterAnimation = 5;
                    } else if (coordMonsterIndicator.equalTo(coordImageMonster6)) {
                        System.out.println("6");
                        caseMonsterAnimation = 6;
                    }
                    enterPressed = false;
                    p2Picking = false;
                    monsterPicking = false;
                    songPicking = true;
                }
            }   // EO (p2Picking)

        } else if (songPicking) {    // song picking
            MainMenuState.stopMusic();
            // Update Audio
            if (indexImageSongArt == 0) {
                if (!audioSongChoices[0].isPlaying()) {     // is not playing
                    for (int i = 0; i < audioSongChoices.length; i++) {
                        if (i != 0) {
                            audioSongChoices[i].stop();
                        }
                    }
                    audioSongChoices[0].playAsSoundEffect(1.0f, 1.0f, false);
                }

                if (enterPressed) {
                    enterPressed = false;
                    GameProperState.setGameMusic(audioSongChoices[0]);
                    songPicking = false;
                }

            } else if (indexImageSongArt == 1) {
                if (!audioSongChoices[1].isPlaying()) {     // is not playing
                    for (int i = 0; i < audioSongChoices.length; i++) {
                        if (i != 1) {
                            audioSongChoices[i].stop();
                        }
                    }
                    audioSongChoices[1].playAsSoundEffect(1.0f, 1.0f, false);
                }

                if (enterPressed) {
                    enterPressed = false;
                    GameProperState.setGameMusic(audioSongChoices[1]);
                    songPicking = false;
                }

            } else if (indexImageSongArt == 2) {
                if (!audioSongChoices[2].isPlaying()) {     // is not playing
                    for (int i = 0; i < audioSongChoices.length; i++) {
                        if (i != 2) {
                            audioSongChoices[i].stop();
                        }
                    }
                    audioSongChoices[2].playAsSoundEffect(1.0f, 1.0f, false);
                }

                if (enterPressed) {
                    enterPressed = false;
                    GameProperState.setGameMusic(audioSongChoices[2]);
                    songPicking = false;
                }
            }
        } else { // if monsterPicking and songPicking is false
            if (enterPressed) {
                MainMenuState.stopMusic();
                for (int i = 0; i < audioSongChoices.length; i++) {
                        audioSongChoices[i].stop();
                }
                sbg.enterState(BeatBitBeatMain.getGameProper(), new FadeOutTransition(), new FadeInTransition());
            }

        }


    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(imageBackground, 400f, 400f);

        imageMonster1.draw(coordImageMonster1.getX(), coordImageMonster1.getY());
        imageMonster2.draw(coordImageMonster2.getX(), coordImageMonster2.getY());
        imageMonster3.draw(coordImageMonster3.getX(), coordImageMonster3.getY());
        imageMonster4.draw(coordImageMonster4.getX(), coordImageMonster4.getY());
        imageMonster5.draw(coordImageMonster5.getX(), coordImageMonster5.getY());
        imageMonster6.draw(coordImageMonster6.getX(), coordImageMonster6.getY());

        if (!monsterPicking && !songPicking){
            imageBtnGame.draw(coordBtnGame.getX(), coordBtnGame.getY());
        }


        if (monsterPicking) {
            if (p1Picking) {
                switch (caseMonsterAnimation) {
                    case 0:     // no animation
                        break;
                    case 1:     // animate monster 1
                        animateMonster1.draw(coordP1Monster.getX(), coordP1Monster.getY());
                        break;
                }
            }
        } else if (songPicking) {
            // Update Displayed Song Art
            if (indexImageSongArt == 0) {
                imagesSongArt[0].draw(coordImageSongArt.getX(), coordImageSongArt.getY());
            } else if (indexImageSongArt == 1) {
                imagesSongArt[1].draw(coordImageSongArt.getX(), coordImageSongArt.getY());
            } else if (indexImageSongArt == 2) {
                imagesSongArt[2].draw(coordImageSongArt.getX(), coordImageSongArt.getY());
            }
        }   // EO (songPicking)


        imageMonsterIndicator.draw(coordMonsterIndicator.getX(), coordMonsterIndicator.getY());

        g.drawRect(coordMonsterIndicator.getX(), coordMonsterIndicator.getY(), imageMonster1.getWidth(), imageMonster1.getHeight());

        animateMonster1.draw(40, 400);
        animateMonster2.draw(displayWidth - (animateMonster1.getWidth() - 40), 400);
    }


    @Override
    public void keyPressed(int key, char pressedKey) {

        if (monsterPicking) {
            if (key == Input.KEY_UP) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordMonsterIndicator.getY() != coordImageMonster2.getY()) {
                    coordMonsterIndicator.setY(coordMonsterIndicator.getY() - (imageMonster2.getHeight() + (imageMonster2.getHeight() / 2)));
                }
            } else if (key == Input.KEY_DOWN) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordMonsterIndicator.getY() != coordImageMonster4.getY()) {
                    coordMonsterIndicator.setY(coordMonsterIndicator.getY() + (imageMonster4.getHeight() + (imageMonster4.getHeight() / 2)));
                }
            } else if (key == Input.KEY_LEFT) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordMonsterIndicator.getX() != coordImageMonster1.getX()) {
                    coordMonsterIndicator.setX(coordMonsterIndicator.getX() - (imageMonster1.getWidth() + (imageMonster1.getWidth() / 2)));
                }
            } else if (key == Input.KEY_RIGHT) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordMonsterIndicator.getX() != coordImageMonster3.getX()) {
                    coordMonsterIndicator.setX(coordMonsterIndicator.getX() + (imageMonster3.getWidth() + (imageMonster3.getWidth() / 2)));
                }
            } else if (key == Input.KEY_ENTER) {
                soundPressEnter.playAsSoundEffect(1.0f, 1.0f, false);
                enterPressed = true;
            }
        } else if (songPicking) {
            if (key == Input.KEY_LEFT) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (indexImageSongArt != 0) {
                    indexImageSongArt--;
                }
            } else if (key == Input.KEY_RIGHT) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (indexImageSongArt != imagesSongArt.length - 1) {
                    indexImageSongArt++;
                }
            } else if (key == Input.KEY_ENTER) {
                soundPressEnter.playAsSoundEffect(1.0f, 1.0f, false);
                enterPressed = true;
            }

        } else {
            if (key == Input.KEY_ENTER) {
                enterPressed = true;
            }
        }

    }

    @Override
    public void keyReleased(int key, char pressedKey) {

    }

}
