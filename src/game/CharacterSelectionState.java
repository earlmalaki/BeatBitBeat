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
import org.newdawn.slick.font.effects.ColorEffect;
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
    private Coordinate coordP1Monster = new Coordinate((float) (displayWidth * 0.03), (float) (displayHeight * 0.20));
    private Coordinate coordP2Monster = new Coordinate((float) (displayWidth - 600), (float) (displayHeight * 0.20));

    // coord for the human preview animation
    private Coordinate coordP1Human = new Coordinate((float) (displayWidth * 0.05), 360f);
    private Coordinate coordP2Human = new Coordinate((float) (displayWidth - 250), 360f);

    private Coordinate coordHumanIndicator;
    private Coordinate coordImageSongArt;
    private Coordinate coordBtnGame;

    private int indexXHumanIndicator = 1;
    private int indexYHumanIndicator = 0;
    private Audio soundPressArrows;
    private Audio soundPressEnter;
    private Music[] musicSongChoices;
    private File[] fileSongBeatMaps;

    private int caseMonsterAnimation = 2;       // index holder for monster preview animation
    private int indexImageSongArt;          // index holder for song art

    // Flags
    private static boolean pressedEnter = false;
    private static boolean monsterPicking = true;
    private static boolean songPicking = false;
    private static boolean p1Picking = true;
    private static boolean p2Picking = false;


    private boolean pressedEscape = false;

    private UnicodeFont fontHeader;
    private String textPlayer1 = "PLAYER 1";
    private String textPlayer2 = "PLAYER 2";
    private String textPickASong = "PICK A SONG";
    private String textGame = "GAME";

    public int getID() {
        return BeatBitBeatMain.getCharacterSelection();
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        initializeFonts();
        initializeImagesAndAnimations();
        initializeAudioAndMusic();

        coordsImagesHuman = new Coordinate[]{
                new Coordinate(406, 108),
                new Coordinate(566, 108),
                new Coordinate(726, 108),
                new Coordinate(406, 266),
                new Coordinate(566, 266),
                new Coordinate(726, 266)
        };
        coordHumanIndicator = coordsImagesHuman[1];
        coordBtnGame = new Coordinate((displayWidth / 2) - (imageBtnGame.getWidth() / 2), displayHeight - 200);

        coordImageSongArt = new Coordinate((391), 440);
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
        // go back to main menu
        if (pressedEscape) {
            backToMainMenu(sbg);
        }

        updateCoordHumanIndicator();

        if (monsterPicking) {
            pickMonster();
        } else if (songPicking) {
            pickSong();
        } else {    // if done picking monsters and song
            if (pressedEnter) {     // game button pressed
                proceedToGameProper(sbg);
            }

        }

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

        imageBackground.draw();

        if (monsterPicking) {
            renderMonsterPicking();
        } else if (songPicking) {
            renderSongPicking();
        } else {   // character and music picking is done
            renderDonePickingBoth();
        }

        g.drawString("DELTA = " + delta, 10, 30);
        g.drawString("X = " + xMouse + " Y = " + yMouse, 10, 50);
    }


    @Override
    public void keyPressed(int key, char pressedKey) {

        if (monsterPicking) {
            if (key == Input.KEY_UP) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (indexYHumanIndicator != 0) {
                    indexYHumanIndicator--;
                }
            } else if (key == Input.KEY_DOWN) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (indexYHumanIndicator != 1) {
                    indexYHumanIndicator++;
                }
            } else if (key == Input.KEY_LEFT) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (indexXHumanIndicator != 0) {
                    indexXHumanIndicator--;
                }
            } else if (key == Input.KEY_RIGHT) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (indexXHumanIndicator != 2) {
                    indexXHumanIndicator++;
                }
            } else if (key == Input.KEY_ENTER) {
//                soundPressEnter.playAsSoundEffect(1.0f, 1.0f, false);

                // disable bottom left(monster 4) and bottom right monsters(monster 6)
                // for coming soon characters
                if (!((indexXHumanIndicator == 0 && indexYHumanIndicator == 1) ||  // monster bottom left
                        (indexXHumanIndicator == 2 && indexYHumanIndicator == 1)))   // monster bottom right
//                        (indexXHumanIndicator == 2 && indexYHumanIndicator == 0) || //  monster upper right
//                        (indexXHumanIndicator == 1 && indexYHumanIndicator == 1)))  // monster lower center
                {
                    pressedEnter = true;
                }
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
//                soundPressEnter.playAsSoundEffect(1.0f, 1.0f, false);
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

    public static void resetCharSelStateFlags() {
        pressedEnter = false;
        monsterPicking = true;
        songPicking = false;
        p1Picking = true;
        p2Picking = false;
    }

    private void initializeFonts() throws SlickException {
        fontHeader = new UnicodeFont("Assets/Fonts/Disposable Droid/DisposableDroidBB.ttf", 50, false, false);
        fontHeader.getEffects().add(new ColorEffect(java.awt.Color.white));
        fontHeader.addAsciiGlyphs();
        fontHeader.loadGlyphs();
    }

    private void initializeImagesAndAnimations() throws SlickException {
        imageBackground = new Image("Assets/Graphics/Character Selection/Character Selection BG.png");

        // TODO Replace correct file names, Width & Height, adjust duration
        animateMonstersP1 = new Animation[]{
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Idle P1.png", 600, 300, 0), Monster1.getFrameDurationMonsterIdle()),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Idle P1.png", 600, 300, 0), Monster2.getFrameDurationMonsterIdle()),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Flame/Flame - Idle P1.png", 600, 300, 0), Monster3.getFrameDurationMonsterIdle()),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Monster 6/Monster 6 - Idle P1.png", 600, 300, 0), 250),        // coming soon
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - Idle P1.png", 600, 300, 0), 250),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Monster 6/Monster 6 - Idle P1.png", 600, 300, 0), 250)         // coming soon
        };

        animateMonstersP2 = new Animation[]{
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Idle P2.png", 600, 300, 0), Monster1.getFrameDurationMonsterIdle()),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Idle P2.png", 600, 300, 0), Monster2.getFrameDurationMonsterIdle()),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Flame/Flame - Idle P2.png", 600, 300, 0), Monster3.getFrameDurationMonsterIdle()),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Monster 6/Monster 6 - Idle P2.png", 600, 300, 0), 250),        // coming soon
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - Idle P2.png", 600, 300, 0), 250),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Monster 6/Monster 6 - Idle P2.png", 600, 300, 0), 250)         // coming soon
        };

        // TODO Randomize players 1, 2, 3, 5 for  player 6
        animateHumansP1 = new Animation[]{
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Human P1.png", 150, 150, 0), Monster1.getFrameDurationHumanIdle()),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Human P1.png", 150, 150, 0), Monster2.getFrameDurationHumanIdle()),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Flame/Flame - Human P1.png", 150, 150, 0), Monster3.getFrameDurationHumanIdle()),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Monster 4/Monster 4 - Human P1.png", 150, 150, 0), 300),       // coming soon
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - Human P1.png", 150, 150, 0), 300),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Monster 6/Monster 6 - Human P1.png", 150, 150, 0), 300)        // coming soon
        };

        animateHumansP2 = new Animation[]{
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Human P2.png", 150, 150, 0), Monster1.getFrameDurationHumanIdle()),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Human P2.png", 150, 150, 0), Monster2.getFrameDurationHumanIdle()),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Flame/Flame - Human P2.png", 150, 150, 0), Monster3.getFrameDurationHumanIdle()    ),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Monster 4/Monster 4 - Human P2.png", 150, 150, 0), 300),       // coming soon
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - Human P2.png", 150, 150, 0), 300),
                new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Monster 6/Monster 6 - Human P2.png", 150, 150, 0), 300)        // coming soon
        };

        // TODO Replace with correct files and names
        imagesHumans1x1 = new Image[]{
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 1.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 2.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 3.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 4.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 5.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 6.png")
        };

        // TODO Replace with correct files and names
        animationHumanIndicator = new Animation(new SpriteSheet("Assets/Graphics/Character Selection/Indicator.png", 150, 150, 0), 100);


        imageBtnGame = new Image("Assets/Graphics/Character Selection/Play Button.png");
        imageBtnPickSong = new Image("Assets/Graphics/Character Selection/Choose Song Button.png");

        // TODO Replace with correct music files and names
        // Image array to hold song arts. Order must be in sync with Audio array
        indexImageSongArt = 0;
        imagesSongArt = new Image[]{
                new Image("Songs/Rage Against The Machine/Killing In The Name/Song Art.png"),
                new Image("Songs/Metallica/Master Of Puppets/Song Art.png"),
        };
    }

    private void initializeAudioAndMusic() throws SlickException {
        try {
            // TODO Replace with correct files
            soundPressArrows = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/pressArrowMainMenu.ogg"));
            soundPressEnter = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/pressEnterMainMenu.ogg"));

            // TODO Replace with correct files and names
            // Audio array to songs. Order must be in sync with song art Image array
            musicSongChoices = new Music[]{
                    new Music("Songs/Rage Against The Machine/Killing In The Name/Song.ogg"),
                    new Music("Songs/Metallica/Master Of Puppets/Song.ogg"),
            };

            fileSongBeatMaps = new File[]{
                    new File("Songs/Rage Against The Machine/Killing In The Name/Beat Map.txt"),
                    new File("Songs/Metallica/Master Of Puppets/Beat Map.txt"),
            };

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void backToMainMenu(StateBasedGame sbg) {
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

    private void updateCoordHumanIndicator() {
        if (indexXHumanIndicator == 0 && indexYHumanIndicator == 0) {
            coordHumanIndicator = coordsImagesHuman[0];
        } else if (indexXHumanIndicator == 1 && indexYHumanIndicator == 0) {
            coordHumanIndicator = coordsImagesHuman[1];
        } else if (indexXHumanIndicator == 2 && indexYHumanIndicator == 0) {
            coordHumanIndicator = coordsImagesHuman[2];
        } else if (indexXHumanIndicator == 0 && indexYHumanIndicator == 1) {
            coordHumanIndicator = coordsImagesHuman[3];
        } else if (indexXHumanIndicator == 1 && indexYHumanIndicator == 1) {
            coordHumanIndicator = coordsImagesHuman[4];
        } else if (indexXHumanIndicator == 2 && indexYHumanIndicator == 1) {
            coordHumanIndicator = coordsImagesHuman[5];
        }
    }

    private void pickMonster() throws SlickException {
        for (int i = 0; i < coordsImagesHuman.length; i++) {
            if (coordHumanIndicator.equalTo(coordsImagesHuman[i])) {
                caseMonsterAnimation = i + 1;       // index for casemonster starts at 1
            }
        }

        if (p1Picking) {    // if player 1's turn to pick
            if (pressedEnter) {
                animateP1Monster = animateMonstersP1[caseMonsterAnimation - 1];
                animateP1Human = animateHumansP1[caseMonsterAnimation - 1];

                monsterP1 = instantiateMonster(1);

                pressedEnter = false;
                p1Picking = false;
                p2Picking = true;       // p2's turn to pick
            }
        } else if (p2Picking) {    // if player 2's turn to pick

            if (pressedEnter) {

                animateP2Monster = animateMonstersP2[caseMonsterAnimation - 1];
                animateP2Human = animateHumansP2[caseMonsterAnimation - 1];

                monsterP2 = instantiateMonster(2);

                pressedEnter = false;
                monsterPicking = false;
                p1Picking = false;
                p2Picking = false;
                songPicking = true;     // pick a song
            }

        }   // EO (p2Picking)
    }
    
    private Monster instantiateMonster(int playerNumber) throws SlickException {
        Monster monster = null;
        if (caseMonsterAnimation == 1) {
            try {
                monster = new Monster1(playerNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (caseMonsterAnimation == 2) {
            monster = new Monster2(playerNumber);
        } else if (caseMonsterAnimation == 3) {
            monster = new Monster3(playerNumber);
        } else if (caseMonsterAnimation == 4) {
            monster = new Monster4(playerNumber);
        } else if (caseMonsterAnimation == 5) {
            monster = new Monster5(playerNumber);
        } else if (caseMonsterAnimation == 6) {
            monster = new Monster6(playerNumber);
        }
        return monster;
    }

    private void pickSong() {
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
    }

    private void proceedToGameProper(StateBasedGame sbg) {
        // stop playing preview of songs
        for (int i = 0; i < musicSongChoices.length; i++) {
            musicSongChoices[i].stop();
        }

        GameProperState.setMonsterP1(monsterP1);
        GameProperState.setMonsterP2(monsterP2);
        // TODO low prio. 3..2..1.. Countdown at GameProperState before music starts
        sbg.enterState(BeatBitBeatMain.getGameProper(), new FadeOutTransition(), new FadeInTransition());
    }

    private void renderMonsterPicking() {
        // render human selection icons
        for (int i = 0; i < imagesHumans1x1.length; i++) {
            imagesHumans1x1[i].draw(coordsImagesHuman[i].getX(), coordsImagesHuman[i].getY());
        }

        animationHumanIndicator.draw(coordHumanIndicator.getX(), coordHumanIndicator.getY());
        imagesSongArt[indexImageSongArt].draw(coordImageSongArt.getX(), coordImageSongArt.getY(), Color.darkGray);
        imageBtnGame.draw(((displayWidth / 2) - (imageBtnGame.getWidth() / 2)), (displayHeight - imageBtnGame.getHeight() - 50), Color.darkGray);


        if (p1Picking) {
            fontHeader.drawString((displayWidth / 2) - (textPlayer1.length() * 20) / 2, 50, textPlayer1);

            animateMonstersP1[caseMonsterAnimation - 1].draw(coordP1Monster.getX(), coordP1Monster.getY());
            animateHumansP1[caseMonsterAnimation - 1].draw(coordP1Human.getX(), coordP1Human.getY());

        } else if (p2Picking) {
            fontHeader.drawString((displayWidth / 2) - (textPlayer2.length() * 20) / 2, 50, textPlayer2);

            animateMonstersP2[caseMonsterAnimation - 1].draw(coordP2Monster.getX(), coordP2Monster.getY());
            animateHumansP2[caseMonsterAnimation - 1].draw(coordP2Human.getX(), coordP2Human.getY());

            animateP1Monster.draw(coordP1Monster.getX(), coordP1Monster.getY());        // draw preview P1's selected monster
            animateP1Human.draw(coordP1Human.getX(), coordP1Human.getY());

        }
    }

    private void renderSongPicking() {
        fontHeader.drawString((displayWidth / 2) - (textPickASong.length() * 20) / 2, 50, textPickASong);

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
//            imageBtnPickSong.draw( ((displayWidth / 2) - (imageBtnPickSong.getWidth() / 2)), (displayHeight - imageBtnPickSong.getHeight() - 50));
        imageBtnGame.draw(((displayWidth / 2) - (imageBtnGame.getWidth() / 2)), (displayHeight - imageBtnGame.getHeight() - 50), Color.darkGray);
    }

    private void renderDonePickingBoth() {
        fontHeader.drawString((displayWidth / 2) - (textGame.length() * 20) / 2, 50, textGame);

        // render human selection icons
        for (int i = 0; i < imagesHumans1x1.length; i++) {
            imagesHumans1x1[i].draw(coordsImagesHuman[i].getX(), coordsImagesHuman[i].getY(), Color.darkGray);
        }

        animateP1Monster.draw(coordP1Monster.getX(), coordP1Monster.getY());
        animateP1Human.draw(coordP1Human.getX(), coordP1Human.getY());
        animateP2Monster.draw(coordP2Monster.getX(), coordP2Monster.getY());
        animateP2Human.draw(coordP2Human.getX(), coordP2Human.getY());

        imagesSongArt[indexImageSongArt].draw(coordImageSongArt.getX(), coordImageSongArt.getY());
        imageBtnGame.draw(((displayWidth / 2) - (imageBtnGame.getWidth() / 2)), (displayHeight - imageBtnGame.getHeight() - 50));
    }

}
