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
 * - Key Listener for monster selection indicator
 * - monster selection
 * - Image or Sprite handler for background
 * - Music
 * - Animation handler for monster preview. both P1 and P2
 * - music selection
 * - game button
 *
 * To Do:
 * - Put wallpaper file
 * - Put monster selection icon files
 * - put spritesheet for monster preview
 * - finalize positioning of buttons after putting in final graphics
 * - put final choices of music (atleast 3)
 * - put final song art per music
 * - game button icon
 * - Monster Objects - backend logic for game proper    // update, nov 6. added monster implementation at monster selection
 *
 * Note:
 * - Monster selection icons should be of same dimensions for correct positioning
 * - Monster preview sprites should be of same dimensions for correct positioning
 *
 */


package game;

import game.monsters.*;
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

    // TODO Gaming part
    // TODO Monster object to represent the monster/person/player/hero
    private Monster monsterP1;
    private Monster monsterP2;

    private Image imageBackground;
    private Image imageMonsterIndicator;        // frame to emphasize hovered monster selection

    private Image imageMonster1;
    private Image imageMonster2;
    private Image imageMonster3;
    private Image imageMonster4;
    private Image imageMonster5;
    private Image imageMonster6;

    private Image[] imagesSongArt;
    private Image imageBtnGame;

    private Animation animateMonster1;
    private Animation animateMonster2;
    private Animation animateMonster3;
    private Animation animateMonster4;
    private Animation animateMonster5;
    private Animation animateMonster6;

    private static Animation animateP1Monster;
    private static Animation animateP2Monster;

    private Coordinate coordP1Monster;      // coord for the monster preview animation
    private Coordinate coordP2Monster;

    private Coordinate coordImageMonster1;  // coord for the monster selection
    private Coordinate coordImageMonster2;
    private Coordinate coordImageMonster3;
    private Coordinate coordImageMonster4;
    private Coordinate coordImageMonster5;
    private Coordinate coordImageMonster6;

    private Coordinate coordMonsterIndicator;

    private Coordinate coordImageSongArt;
    private Coordinate coordBtnGame;

    private Audio soundPressArrows;
    private Audio soundPressEnter;
    private Audio[] audioSongChoices;

    private int caseMonsterAnimation;       // index holder for monster preview animation
    private int spacingXOfImageMonster;     // fixed spacing
    private int spacingYOfImageMonster;
    private int indexImageSongArt;          // index holder for song art

    private boolean enterPressed;
    private boolean monsterPicking;
    private boolean songPicking;
    private boolean p1Picking;
    private boolean p2Picking;

    private int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private int displayHeight = BeatBitBeatMain.getDisplayHeight();

    public int getID() {
        return BeatBitBeatMain.getCharacterSelection();
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        // TODO Replace with correct files and names
        imageBackground = new Image("Assets/bgimage.jpg");
        SpriteSheet spriteMonster1 = new SpriteSheet("Assets/Graphics/Monster Display Sprite/monster1.png", 200, 300); //ref, tw, th, spacing
        SpriteSheet spriteMonster2 = new SpriteSheet("Assets/Graphics/Monster Display Sprite/monster2.png", 200, 300);
        SpriteSheet spriteMonster3 = new SpriteSheet("Assets/Graphics/Monster Display Sprite/monster3.png", 200, 300);
        SpriteSheet spriteMonster4 = new SpriteSheet("Assets/Graphics/Monster Display Sprite/monster4.png", 200, 300);
        SpriteSheet spriteMonster5 = new SpriteSheet("Assets/Graphics/Monster Display Sprite/monster5.png", 200, 300);
        SpriteSheet spriteMonster6 = new SpriteSheet("Assets/Graphics/Monster Display Sprite/monster5.png", 200, 300);

        // TODO Replace correct names and adjust duration according to spritesheet
        animateMonster1 = new Animation(spriteMonster1, 200);
        animateMonster2 = new Animation(spriteMonster2, 200);
        animateMonster3 = new Animation(spriteMonster3, 200);
        animateMonster4 = new Animation(spriteMonster4, 200);
        animateMonster5 = new Animation(spriteMonster5, 200);
        animateMonster6 = new Animation(spriteMonster6, 200);

        // Coordinates for monster preview animations
        coordP1Monster = new Coordinate(50, 250);
        coordP2Monster = new Coordinate(displayWidth - (spriteMonster1.getWidth() / 2) - 50, 250);

        caseMonsterAnimation = 0;

        // TODO Replace with correct files and names
        imageMonster1 = new Image("Assets/Graphics/Monster Avatar/AvatarMonster1.png");
        imageMonster2 = new Image("Assets/Graphics/Monster Avatar/AvatarMonster2.png");
        imageMonster3 = new Image("Assets/Graphics/Monster Avatar/AvatarMonster3.png");
        imageMonster4 = new Image("Assets/Graphics/Monster Avatar/AvatarMonster4.png");
        imageMonster5 = new Image("Assets/Graphics/Monster Avatar/AvatarMonster5.png");
        imageMonster6 = new Image("Assets/Graphics/Monster Avatar/AvatarMonster5.png");

        spacingXOfImageMonster = (imageMonster4.getWidth() / 2) * 3;
        spacingYOfImageMonster = (imageMonster4.getHeight() / 2) * 3;
        imageMonsterIndicator = new Image("Assets/player1.jpg"); // TODO Replace with correct files and names

        coordImageMonster2 = new Coordinate((displayWidth / 2) - (imageMonster2.getWidth() / 2), 200);
        coordImageMonster1 = new Coordinate(coordImageMonster2.getX() - spacingXOfImageMonster, coordImageMonster2.getY());
        coordImageMonster3 = new Coordinate(coordImageMonster2.getX() + spacingXOfImageMonster, coordImageMonster2.getY());

        coordImageMonster5 = new Coordinate(coordImageMonster2.getX(), coordImageMonster2.getY() + spacingYOfImageMonster);
        coordImageMonster4 = new Coordinate(coordImageMonster5.getX() - spacingXOfImageMonster, coordImageMonster5.getY());
        coordImageMonster6 = new Coordinate(coordImageMonster5.getX() + spacingXOfImageMonster, coordImageMonster5.getY());

        coordMonsterIndicator = new Coordinate(coordImageMonster2.getX(), coordImageMonster2.getY());

        // TODO Replace with correct files and names
        imageBtnGame = new Image("Assets/player1.jpg");
        coordBtnGame = new Coordinate((displayWidth / 2) - (imageBtnGame.getWidth() / 2), displayHeight - 200);

        // Flags
        enterPressed = false;
        monsterPicking = true;
        songPicking = false;
        p1Picking = true;
        p2Picking = false;

        // TODO Replace with correct files and names
        // Image array to hold song arts. Order must be in sync with Audio array
        indexImageSongArt = 0;
        imagesSongArt = new Image[]{
                new Image("Songs/Goin' Under/bn.png"),
                new Image("Songs/MechaTribe Assault/bn.png"),
                new Image("Songs/Springtime/bn.png"),
        };
        coordImageSongArt = new Coordinate((displayWidth / 2) - (imagesSongArt[0].getWidth() / 2), displayHeight - (imagesSongArt[0].getHeight() * 2));

        try {
            // TODO Replace with correct files
            soundPressArrows = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/pressArrowMainMenu.ogg"));
            soundPressEnter = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/pressEnterMainMenu.ogg"));

            // TODO Replace with correct files and names
            // Audio array to songs. Order must be in sync with song art Image array
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
            if (coordMonsterIndicator.equalTo(coordImageMonster1)) {        // indicator is at monster 1
                caseMonsterAnimation = 1;                                   // set monster preview animation to monster 1's
            } else if (coordMonsterIndicator.equalTo(coordImageMonster2)) {
                caseMonsterAnimation = 2;
            } else if (coordMonsterIndicator.equalTo(coordImageMonster3)) {
                caseMonsterAnimation = 3;
            } else if (coordMonsterIndicator.equalTo(coordImageMonster4)) {
                caseMonsterAnimation = 4;
            } else if (coordMonsterIndicator.equalTo(coordImageMonster5)) {
                caseMonsterAnimation = 5;
            } else if (coordMonsterIndicator.equalTo(coordImageMonster6)) {
                caseMonsterAnimation = 6;
            }

            if (p1Picking) {    // if player 1's turn to pick
                if (enterPressed) {
                    if (caseMonsterAnimation == 1) {
                        animateP1Monster = animateMonster1;     // set P1's animation to monster 1's
                        // TODO Gaming part
                        // TODO Monster object representing monster's unique capabilities

                        // Update. nov 6.
                        // added monster implementations

                        monsterP1 = new Monster1();
                    } else if (caseMonsterAnimation == 2) {
                        animateP1Monster = animateMonster2;
                        monsterP1 = new Monster2();
                    } else if (caseMonsterAnimation == 3) {
                        animateP1Monster = animateMonster3;
                        monsterP1 = new Monster3();
                    } else if (caseMonsterAnimation == 4) {
                        animateP1Monster = animateMonster4;
                        monsterP1 = new Monster4();
                    } else if (caseMonsterAnimation == 5) {
                        animateP1Monster = animateMonster5;
                        monsterP1 = new Monster5();
                    } else if (caseMonsterAnimation == 6) {
                        animateP1Monster = animateMonster6;
                        monsterP1 = new Monster6();
                    }
                    enterPressed = false;
                    p1Picking = false;
                    p2Picking = true;       // p2's turn to pick
                }
            } else if (p2Picking) {    // if player 2's turn to pick
                if (enterPressed) {
                    if (caseMonsterAnimation == 1) {
                        animateP2Monster = animateMonster1;     // set P2's animation to monster 1's
                        // TODO Gaming part
                        // TODO Monster object representing monster's unique capabilities

                        // update

                        monsterP2 = new Monster1();
                    } else if (caseMonsterAnimation == 2) {
                        animateP2Monster = animateMonster2;
                        monsterP2 = new Monster2();
                    } else if (caseMonsterAnimation == 3) {
                        animateP2Monster = animateMonster3;
                        monsterP2 = new Monster3();
                    } else if (caseMonsterAnimation == 4) {
                        animateP2Monster = animateMonster4;
                        monsterP2 = new Monster4();
                    } else if (caseMonsterAnimation == 5) {
                        animateP2Monster = animateMonster5;
                        monsterP2 = new Monster5();
                    } else if (caseMonsterAnimation == 6) {
                        animateP2Monster = animateMonster6;
                        monsterP2 = new Monster6();
                    }
                    enterPressed = false;
                    monsterPicking = false;
                    p1Picking = false;
                    p2Picking = false;
                    songPicking = true;     // pick a song
                }

            }   // EO (p2Picking)

        } else if (songPicking) {    // song picking
            MainMenuState.stopMusic();
            // Play Audio according to selection scanning
            if (indexImageSongArt == 0) {
                if (!audioSongChoices[0].isPlaying()) {     // is not playing
                    for (int i = 0; i < audioSongChoices.length; i++) {
                        if (i != 0) {
                            audioSongChoices[i].stop();     // stop playing others except the one being hovered
                        }
                    }
                    // play the one being hovered
                    audioSongChoices[0].playAsSoundEffect(1.0f, 1.0f, false);
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

            } else if (indexImageSongArt == 2) {
                if (!audioSongChoices[2].isPlaying()) {     // is not playing
                    for (int i = 0; i < audioSongChoices.length; i++) {
                        if (i != 2) {
                            audioSongChoices[i].stop();
                        }
                    }
                    audioSongChoices[2].playAsSoundEffect(1.0f, 1.0f, false);
                }
            }

            if (enterPressed) {     // song selected
                enterPressed = false;
                songPicking = false;
                for (int i = 0; i < audioSongChoices.length; i++) {
                    if (audioSongChoices[i].isPlaying()) {
                        GameProperState.setGameMusic(audioSongChoices[i]);      // save selected song for GameProperState usage
                    }
                }
            }
        } else { // if monsterPicking and songPicking is false
            if (enterPressed) {     // game button pressed
                // stop playing preview of songs
                for (int i = 0; i < audioSongChoices.length; i++) {
                    audioSongChoices[i].stop();
                }
                GameProperState.setAnimationPlayer1(animateP1Monster);
                GameProperState.setAnimationPlayer2(animateP2Monster);
                GameProperState.startMusic();       // start music for the game proper
                sbg.enterState(BeatBitBeatMain.getGameProper(), new FadeOutTransition(), new FadeInTransition());
            }

        }

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(imageBackground, 400f, 400f);

        // render monster selection icons
        imageMonster1.draw(coordImageMonster1.getX(), coordImageMonster1.getY());
        imageMonster2.draw(coordImageMonster2.getX(), coordImageMonster2.getY());
        imageMonster3.draw(coordImageMonster3.getX(), coordImageMonster3.getY());
        imageMonster4.draw(coordImageMonster4.getX(), coordImageMonster4.getY());
        imageMonster5.draw(coordImageMonster5.getX(), coordImageMonster5.getY());
        imageMonster6.draw(coordImageMonster6.getX(), coordImageMonster6.getY());

        // draw game button when done picking
        if (!monsterPicking && !songPicking) {
            imageBtnGame.draw(coordBtnGame.getX(), coordBtnGame.getY());
        }

        if (monsterPicking) {
            imageMonsterIndicator.draw(coordMonsterIndicator.getX(), coordMonsterIndicator.getY());
            g.drawRect(coordMonsterIndicator.getX(), coordMonsterIndicator.getY(), imageMonster1.getWidth(), imageMonster1.getHeight());

            if (p1Picking) {
                g.drawString("Player 1 pick", (displayWidth / 2) - 10, 10);     // temp instruction display. to be improved
                if (caseMonsterAnimation == 1) {
                    animateMonster1.draw(coordP1Monster.getX(), coordP1Monster.getY());     // draw monster preview animation of the monster being hovered at
                } else if (caseMonsterAnimation == 2) {
                    animateMonster2.draw(coordP1Monster.getX(), coordP1Monster.getY());
                } else if (caseMonsterAnimation == 3) {
                    animateMonster3.draw(coordP1Monster.getX(), coordP1Monster.getY());
                } else if (caseMonsterAnimation == 4) {
                    animateMonster4.draw(coordP1Monster.getX(), coordP1Monster.getY());
                } else if (caseMonsterAnimation == 5) {
                    animateMonster5.draw(coordP1Monster.getX(), coordP1Monster.getY());
                } else if (caseMonsterAnimation == 6) {
                    animateMonster6.draw(coordP1Monster.getX(), coordP1Monster.getY());
                }
            } else if (p2Picking) {
                g.drawString("Player 2 pick", (displayWidth / 2) - 10, 10);
                animateP1Monster.draw(coordP1Monster.getX(), coordP1Monster.getY());        // draw preview P1's selected monster
                if (caseMonsterAnimation == 1) {
                    animateMonster1.draw(coordP2Monster.getX(), coordP2Monster.getY());
                } else if (caseMonsterAnimation == 2) {
                    animateMonster2.draw(coordP2Monster.getX(), coordP2Monster.getY());
                } else if (caseMonsterAnimation == 3) {
                    animateMonster3.draw(coordP2Monster.getX(), coordP2Monster.getY());
                } else if (caseMonsterAnimation == 4) {
                    animateMonster4.draw(coordP2Monster.getX(), coordP2Monster.getY());
                } else if (caseMonsterAnimation == 5) {
                    animateMonster5.draw(coordP2Monster.getX(), coordP2Monster.getY());
                } else if (caseMonsterAnimation == 6) {
                    animateMonster6.draw(coordP2Monster.getX(), coordP2Monster.getY());
                }
            }

        } else if (songPicking) {
            g.drawString("Pick a song", (displayWidth / 2) - 10, 10);

            animateP1Monster.draw(coordP1Monster.getX(), coordP1Monster.getY());
            animateP2Monster.draw(coordP2Monster.getX(), coordP2Monster.getY());

            // draw song art of the song being hovered at
            if (indexImageSongArt == 0) {
                imagesSongArt[0].draw(coordImageSongArt.getX(), coordImageSongArt.getY());
            } else if (indexImageSongArt == 1) {
                imagesSongArt[1].draw(coordImageSongArt.getX(), coordImageSongArt.getY());
            } else if (indexImageSongArt == 2) {
                imagesSongArt[2].draw(coordImageSongArt.getX(), coordImageSongArt.getY());
            }
        } else {   // EO (songPicking)
            animateP1Monster.draw(coordP1Monster.getX(), coordP1Monster.getY());    // draw preview P1's selected monster
            animateP2Monster.draw(coordP2Monster.getX(), coordP2Monster.getY());
        }


    }


    @Override
    public void keyPressed(int key, char pressedKey) {

        if (monsterPicking) {
            if (key == Input.KEY_UP) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordMonsterIndicator.getY() != coordImageMonster2.getY()) {
                    coordMonsterIndicator.setY(coordMonsterIndicator.getY() - spacingYOfImageMonster);
                }
            } else if (key == Input.KEY_DOWN) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordMonsterIndicator.getY() != coordImageMonster4.getY()) {
                    coordMonsterIndicator.setY(coordMonsterIndicator.getY() + spacingYOfImageMonster);
                }
            } else if (key == Input.KEY_LEFT) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordMonsterIndicator.getX() != coordImageMonster1.getX()) {
                    coordMonsterIndicator.setX(coordMonsterIndicator.getX() - spacingXOfImageMonster);
                }
            } else if (key == Input.KEY_RIGHT) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordMonsterIndicator.getX() != coordImageMonster3.getX()) {
                    coordMonsterIndicator.setX(coordMonsterIndicator.getX() + spacingXOfImageMonster);
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

    public static Animation getAnimateP1Monster() {
        return animateP1Monster;
    }

    public static Animation getAnimateP2Monster() {
        return animateP2Monster;
    }
}
