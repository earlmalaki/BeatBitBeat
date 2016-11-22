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
 * - Key Listener for monster selection indicator
 * - monster selection
 * - Image or Sprite handler for background
 * - Music
 * - Animation handler for monster preview. both P1 and P2
 * - music selection
 * - game button
 * <p>
 * To Do:
 * - Import mirrored spritesheet for mosnter and human, then modify code (.update() )
 * - Put wallpaper file
 * - Put monster selection icon files
 * - put spritesheet for monster preview
 * - finalize positioning of buttons after putting in final graphics
 * - put final choices of music (atleast 3)
 * - put final song art per music
 * - game button icon
 * - Monster Objects - backend logic for game proper    // update, nov 6. added monster implementation at monster selection
 * <p>
 * game.Note:
 * - Monster selection icons should be of same dimensions for correct positioning
 * - Monster preview sprites should be of same dimensions for correct positioning
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

import java.io.File;
import java.io.IOException;

// Pick player, monster, music
public class CharacterSelectionState extends BasicGameState implements KeyListener {

    private int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private int displayHeight = BeatBitBeatMain.getDisplayHeight();
    private int spacingXOfImageHuman;
    private int spacingYOfImageHuman;

    private Monster monsterP1;
    private Monster monsterP2;

    private Image imageBackground;
    private Animation animationHumanIndicator;        // frame to emphasize hovered human selection
    private Image[] imagesSongArt;
    private Image imageBtnGame;
    private Image imageBtnPickSong;

    private Image[] imagesHumans1x1;

    // coordinate set for of human 1x1 pics in selection screen
    private Coordinate[] coordsImagesHuman;

    private Animation[] animateMonstersP1;
    private Animation[] animateHumansP1;
    private Animation[] animateMonstersP2;
    private Animation[] animateHumansP2;

    private static Animation animateP1Monster;
    private static Animation animateP2Monster;
    private static Animation animateP1Human;
    private static Animation animateP2Human;

    // Coordinates for monster preview animations

    // coord for the monster preview animation
    private Coordinate coordP1Monster = new Coordinate( (float)(displayWidth * 0.03), (float)(displayHeight * 0.20));
    private Coordinate coordP2Monster = new Coordinate( (float)(displayWidth - 600), (float)(displayHeight * 0.20));

    // coord for the human preview animation
    private Coordinate coordP1Human = new Coordinate( (float)(displayWidth * 0.05), 360f);
    private Coordinate coordP2Human = new Coordinate( (float)(displayWidth - 250), 360f);

    private Coordinate coordHumanIndicator;
    private Coordinate coordImageSongArt;
    private Coordinate coordBtnGame;

    private Audio soundPressArrows;
    private Audio soundPressEnter;
    private Music[] musicSongChoices;
    private File[] fileSongBeatMaps;

    private int caseMonsterAnimation = 2;       // index holder for monster preview animation
    private int indexImageSongArt;          // index holder for song art

    // Flags
    private boolean pressedEnter = false;
    private boolean monsterPicking = true;
    private boolean songPicking = false;
    private boolean p1Picking = true;
    private boolean p2Picking = false;


    private boolean pressedEscape = false;


    public int getID() {
        return BeatBitBeatMain.getCharacterSelection();
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        imageBackground = new Image("Assets/Graphics/Character Selection/Character Selection BG.png");

        // TODO Replace correct file names, Width & Height, adjust duration
        animateMonstersP1 = new Animation[]{
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Idle P1.png", 600, 300, 0), 250),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Idle P1.png", 600, 300, 0), 250),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Flame/Flame - Idle P1.png", 600, 300, 0), 250),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Idle P1.png", 600, 300, 0), 250),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Idle P1.png", 600, 300, 0), 250),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Idle P1.png", 600, 300, 0), 250)
        };

        animateMonstersP2 = new Animation[]{
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Idle P2.png", 600, 300, 0), 250),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Idle P2.png", 600, 300, 0), 250),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Flame/Flame - Idle P2.png", 600, 300, 0), 250),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Idle P2.png", 600, 300, 0), 250),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Idle P2.png", 600, 300, 0), 250),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Idle P2.png", 600, 300, 0), 250)
        };

        // TODO Replace correct file names, Width & Height, adjust duration
        animateHumansP1 = new Animation[]{
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Human P1.png", 150, 150, 0), 300),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Human P1.png", 150, 150, 0), 300),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Flame/Flame - Human P1.png", 150, 150, 0), 300),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Human P1.png", 150, 150, 0), 300),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Human P1.png", 150, 150, 0), 300),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Human P1.png", 150, 150, 0), 300)
        };

        animateHumansP2 = new Animation[]{
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Human P2.png", 150, 150, 0), 300),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Human P2.png", 150, 150, 0), 300),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Flame/Flame - Human P2.png", 150, 150, 0), 300),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Human P2.png", 150, 150, 0), 300),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Human P2.png", 150, 150, 0), 300),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Human P2.png", 150, 150, 0), 300)
        };

        // TODO Replace with correct files and names
        imagesHumans1x1 = new Image[]{
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 1.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 2.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 3.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 4.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 5.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 5.png")
        };

        spacingXOfImageHuman = (imagesHumans1x1[0].getWidth() / 2);
        spacingYOfImageHuman = (imagesHumans1x1[0].getHeight() / 2);
        coordsImagesHuman = new Coordinate[]{
                new Coordinate((displayWidth / 2) - (imagesHumans1x1[0].getWidth() / 2) - spacingXOfImageHuman, 200),
                new Coordinate((displayWidth / 2) - (imagesHumans1x1[0].getWidth() / 2), 200),
                new Coordinate((displayWidth / 2) - (imagesHumans1x1[0].getWidth() / 2) + spacingXOfImageHuman, 200),
                new Coordinate((displayWidth / 2) - (imagesHumans1x1[0].getWidth() / 2) - spacingXOfImageHuman, 200 + spacingYOfImageHuman),
                new Coordinate((displayWidth / 2) - (imagesHumans1x1[0].getWidth() / 2), 200 + spacingYOfImageHuman),
                new Coordinate((displayWidth / 2) - (imagesHumans1x1[0].getWidth() / 2) + spacingXOfImageHuman, 200 + spacingYOfImageHuman),
        };
        coordHumanIndicator = new Coordinate(coordsImagesHuman[1].getX(), coordsImagesHuman[1].getY());

        // TODO Replace with correct files and names
//        animationHumanIndicator = new Animation(new SpriteSheet("Assets/Graphics/Character Selection/Indicator Box.png", 150, 150, 1), 250);


        imageBtnGame = new Image("Assets/Graphics/Character Selection/Play Button.png");
        imageBtnPickSong = new Image("Assets/Graphics/Character Selection/Choose Song Button.png");

        coordBtnGame = new Coordinate((displayWidth / 2) - (imageBtnGame.getWidth() / 2), displayHeight - 200);


        // TODO Replace with correct music files and names
        // Image array to hold song arts. Order must be in sync with Audio array
        indexImageSongArt = 0;
        imagesSongArt = new Image[]{
                new Image("Songs/Goin' Under/bn.png"),
                new Image("Songs/MechaTribe Assault/bn.png"),
                new Image("Songs/Springtime/bn.png"),
                new Image("Songs/Triton (Original Mix) - Dubvision/bn.png")
        };

        coordImageSongArt = new Coordinate((displayWidth / 2) - (imagesSongArt[0].getWidth() / 2), displayHeight - (imagesSongArt[0].getHeight() * 2));

        try {
            // TODO Replace with correct files
            soundPressArrows = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/pressArrowMainMenu.ogg"));
            soundPressEnter = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/pressEnterMainMenu.ogg"));

            // TODO Replace with correct files and names
            // Audio array to songs. Order must be in sync with song art Image array
            musicSongChoices = new Music[]{
                    new Music("Songs/Goin' Under/Goin' Under.ogg"),
                    new Music("Songs/MechaTribe Assault/Mecha-Tribe Assault.ogg"),
                    new Music("Songs/Springtime/Kommisar - Springtime.ogg"),
                    new Music("Songs/Triton (Original Mix) - Dubvision/Triton (Original Mix).ogg")
            };

            fileSongBeatMaps = new File[]{
                    new File("Songs/Goin' Under/Goin' Under Beat Map.txt"),
                    new File("Songs/MechaTribe Assault/Mecha-Tribe Assault Beat Map.txt"),
                    new File("Songs/Springtime/Kommisar - Springtime Beat Map.txt"),
                    new File("Songs/Triton (Original Mix) - Dubvision/Triton (Original Mix) Beat Map.txt")
            };

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    int delta;  // for printing. temporary
    float xMouse;
    float yMouse;
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        this.delta = delta;  // for printing. temporary
        Input input = gc.getInput();
        xMouse = input.getMouseX();
        yMouse = input.getMouseY();

        // cancel/redo selection
        // reset selections
        if (pressedEscape) {
            pressedEscape = false;

            caseMonsterAnimation = 2;
            pressedEnter = false;
            monsterPicking = true;
            songPicking = false;
            p1Picking = true;
            p2Picking = false;

            for (Music music : musicSongChoices) {
                if (music.playing()) {
                    music.stop();
                }
            }

            MainMenuState.playMusic();
            sbg.enterState(BeatBitBeatMain.getMainMenu(), new FadeOutTransition(), new FadeInTransition());
        }


        if (monsterPicking) {

            for (int i = 0; i < coordsImagesHuman.length; i++) {
                if (coordHumanIndicator.equalTo(coordsImagesHuman[i])) {
                    caseMonsterAnimation = i + 1;       // index for casemonster starts at 1
                }
            }


            if (p1Picking) {    // if player 1's turn to pick
                if (pressedEnter) {
                    animateP1Monster = animateMonstersP1[caseMonsterAnimation - 1];
                    animateP1Human = animateHumansP1[caseMonsterAnimation - 1];

                    // TODO Gaming part
                    // TODO Monster object representing monster's unique capabilities
                    if (caseMonsterAnimation == 1) {
                        monsterP1 = new Monster1(1);
                    }
                    else if (caseMonsterAnimation == 2) {
                        monsterP1 = new Monster2(1);
                    } else if (caseMonsterAnimation == 3) {
                        monsterP1 = new Monster3(1);
                    } else if (caseMonsterAnimation == 4) {
                        monsterP1 = new Monster4(1);
                    } else if (caseMonsterAnimation == 5) {
                        monsterP1 = new Monster5(1);
                    } else if (caseMonsterAnimation == 6) {
                        monsterP1 = new Monster6(1);
                    }
                    pressedEnter = false;
                    p1Picking = false;
                    p2Picking = true;       // p2's turn to pick
                }
            } else if (p2Picking) {    // if player 2's turn to pick

                if (pressedEnter) {

                    animateP2Monster = animateMonstersP2[caseMonsterAnimation - 1];
                    animateP2Human = animateHumansP2[caseMonsterAnimation - 1];

                    // TODO Gaming part
                    // TODO Monster object representing monster's unique capabilities
                    if (caseMonsterAnimation == 1) {
                        monsterP2 = new Monster1(2);
                    }
                    else if (caseMonsterAnimation == 2) {
                        monsterP2 = new Monster2(2);
                    } else if (caseMonsterAnimation == 3) {
                        monsterP2 = new Monster3(2);
                    } else if (caseMonsterAnimation == 4) {
                        monsterP2 = new Monster4(2);
                    } else if (caseMonsterAnimation == 5) {
                        monsterP2 = new Monster5(2);
                    } else if (caseMonsterAnimation == 6) {
                        monsterP2 = new Monster6(2);
                    }
                    pressedEnter = false;
                    monsterPicking = false;
                    p1Picking = false;
                    p2Picking = false;
                    songPicking = true;     // pick a song
                }

            }   // EO (p2Picking)

        } else if (songPicking) {    // song picking

            // Play Audio according to selection scanning
            for (int i = 0; i < musicSongChoices.length; i++) {
                if (!musicSongChoices[indexImageSongArt].playing()) {
                    musicSongChoices[indexImageSongArt].play();
                }
            }


            if (pressedEnter) {     // song selected
                pressedEnter = false;
                songPicking = false;
                for (int i = 0; i < musicSongChoices.length; i++) {
                    if (musicSongChoices[i].playing()) {
                        GameProperState.setGameMusic(musicSongChoices[i]);      // save selected song for GameProperState usage
                        GameProperState.setBeatMap(fileSongBeatMaps[i]);
                    }
                }
            }
        } else { // if monsterPicking and songPicking is false
            if (pressedEnter) {     // game button pressed

                // stop playing preview of songs
                for (int i = 0; i < musicSongChoices.length; i++) {
                    musicSongChoices[i].stop();
                }

                GameProperState.setMonsterP1(monsterP1);
                GameProperState.setMonsterP2(monsterP2);
                GameProperState.setAnimationPlayer1(animateP1Monster);
                GameProperState.setAnimationPlayer2(animateP2Monster);
                // TODO low prio. 3..2..1.. Countdown at GameProperState before music starts
                sbg.enterState(BeatBitBeatMain.getGameProper(), new FadeOutTransition(), new FadeInTransition());
            }

        }

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

        imageBackground.draw();

        g.drawString("DELTA = " + delta, 100, 100);
        g.drawString("X = " + xMouse + " Y = " + yMouse, 100, 130);


        if (monsterPicking) {
            // render human selection icons
            for (int i = 0; i < imagesHumans1x1.length; i++) {
                imagesHumans1x1[i].draw(coordsImagesHuman[i].getX(), coordsImagesHuman[i].getY());
            }
//            animationHumanIndicator.draw(coordHumanIndicator.getX(), coordHumanIndicator.getY());
            imagesSongArt[indexImageSongArt].draw(coordImageSongArt.getX(), coordImageSongArt.getY(), Color.darkGray);
            imageBtnGame.draw( ((displayWidth / 2) - (imageBtnGame.getWidth() / 2)), (displayHeight - imageBtnGame.getHeight() - 50), Color.darkGray);


            if (p1Picking) {
                g.drawString("Player 1 pick", (displayWidth / 2) - 10, 10);     // temp instruction display. to be improved

                animateMonstersP1[caseMonsterAnimation - 1].draw(coordP1Monster.getX(), coordP1Monster.getY());
                animateHumansP1[caseMonsterAnimation - 1].draw(coordP1Human.getX(), coordP1Human.getY());

            } else if (p2Picking) {
                g.drawString("Player 2 pick", (displayWidth / 2) - 10, 10);

                animateMonstersP2[caseMonsterAnimation - 1].draw(coordP2Monster.getX(), coordP2Monster.getY());
                animateHumansP2[caseMonsterAnimation - 1].draw(coordP2Human.getX(), coordP2Human.getY());

                animateP1Monster.draw(coordP1Monster.getX(), coordP1Monster.getY());        // draw preview P1's selected monster
                animateP1Human.draw(coordP1Human.getX(), coordP1Human.getY());

            }

        } else if (songPicking) {
            g.drawString("Pick a song", (displayWidth / 2) - 10, 10);

            // render human selection icons
            for (int i = 0; i < imagesHumans1x1.length; i++) {
                imagesHumans1x1[i].draw(coordsImagesHuman[i].getX(), coordsImagesHuman[i].getY(), Color.darkGray);
            }

            animateP1Monster.draw(coordP1Monster.getX(), coordP1Monster.getY());
            animateP1Human.draw(coordP1Human.getX(), coordP1Human.getY());

            animateP2Monster.draw(coordP2Monster.getX(), coordP2Monster.getY());
            animateP2Human.draw(coordP2Human.getX(), coordP2Human.getY());

            // draw song art of the song being hovered at
            imagesSongArt[indexImageSongArt].draw(coordImageSongArt.getX(), coordImageSongArt.getY());
            imageBtnPickSong.draw( ((displayWidth / 2) - (imageBtnPickSong.getWidth() / 2)), (displayHeight - imageBtnPickSong.getHeight() - 50));

        } else {   // character and music picking is done
            // render human selection icons
            for (int i = 0; i < imagesHumans1x1.length; i++) {
                imagesHumans1x1[i].draw(coordsImagesHuman[i].getX(), coordsImagesHuman[i].getY(), Color.darkGray);
            }

            animateP1Monster.draw(coordP1Monster.getX(), coordP1Monster.getY());
            animateP1Human.draw(coordP1Human.getX(), coordP1Human.getY());
            animateP2Monster.draw(coordP2Monster.getX(), coordP2Monster.getY());
            animateP2Human.draw(coordP2Human.getX(), coordP2Human.getY());

            imagesSongArt[indexImageSongArt].draw(coordImageSongArt.getX(), coordImageSongArt.getY());
            imageBtnGame.draw( ((displayWidth / 2) - (imageBtnGame.getWidth() / 2)), (displayHeight - imageBtnGame.getHeight() - 50));
        }


    }


    @Override
    public void keyPressed(int key, char pressedKey) {

        if (monsterPicking) {
            if (key == Input.KEY_UP) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordHumanIndicator.getY() != coordsImagesHuman[0].getY()) {
                    coordHumanIndicator.setY(coordHumanIndicator.getY() - spacingYOfImageHuman);
                }
            } else if (key == Input.KEY_DOWN) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordHumanIndicator.getY() != coordsImagesHuman[5].getY()) {
                    coordHumanIndicator.setY(coordHumanIndicator.getY() + spacingYOfImageHuman);
                }
            } else if (key == Input.KEY_LEFT) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordHumanIndicator.getX() != coordsImagesHuman[0].getX()) {
                    coordHumanIndicator.setX(coordHumanIndicator.getX() - spacingXOfImageHuman);
                }
            } else if (key == Input.KEY_RIGHT) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordHumanIndicator.getX() != coordsImagesHuman[5].getX()) {
                    coordHumanIndicator.setX(coordHumanIndicator.getX() + spacingXOfImageHuman);
                }
            } else if (key == Input.KEY_ENTER) {
                soundPressEnter.playAsSoundEffect(1.0f, 1.0f, false);
                pressedEnter = true;
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
                pressedEnter = true;
            }

        } else {
            if (key == Input.KEY_ENTER) {
                pressedEnter = true;
            }
        }

        if (key == Input.KEY_ESCAPE) {
            pressedEscape = true;
        }

    }

    @Override
    public void keyReleased(int key, char pressedKey) {

    }

}
