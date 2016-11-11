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
 * game.Note:
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

import java.io.File;
import java.io.IOException;

// Pick player, monster, music
public class CharacterSelectionState extends BasicGameState implements KeyListener {

    // TODO Gaming part
    // TODO Monster object to represent the monster/person/player/hero
    private Monster monsterP1;
    private Monster monsterP2;

    private Image imageBackground;
    private Image imageMonsterIndicator;        // frame to emphasize hovered monster selection
    private Image[] imagesSongArt;
    private Image imageBtnGame;

    private Image[] imagesHumans1x1;
    private Coordinate[] coordsImagesHuman;

    private Animation[] animateMonsters;
    private Animation[] animateHumans;

    private static Animation animateP1Monster;
    private static Animation animateP2Monster;
    private static Animation animateP1Human;
    private static Animation animateP2Human;

    private Coordinate coordP1Monster;      // coord for the monster preview animation
    private Coordinate coordP2Monster;
    private Coordinate coordP1Human;      // coord for the human preview animation
    private Coordinate coordP2Human;

    private Coordinate coordMonsterIndicator;
    private Coordinate coordImageSongArt;
    private Coordinate coordBtnGame;

    private Audio soundPressArrows;
    private Audio soundPressEnter;
    private Music[] musicSongChoices;
    private File[] fileSongBeatMaps;

    private int caseMonsterAnimation;       // index holder for monster preview animation
    private int spacingXOfImageMonster;     // fixed spacing
    private int spacingYOfImageMonster;
    private int indexImageSongArt;          // index holder for song art

    private boolean pressedEnter;
    private boolean monsterPicking;
    private boolean songPicking;
    private boolean p1Picking;
    private boolean p2Picking;

    private int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private int displayHeight = BeatBitBeatMain.getDisplayHeight();

    private boolean pressedEscape;

    public int getID() {
        return BeatBitBeatMain.getCharacterSelection();
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        pressedEscape = false;

        // TODO Replace with correct files and names
        imageBackground = new Image("Assets/Graphics/Character Selection/Character Selection BG.jpeg");

        SpriteSheet spriteMonster1 = new SpriteSheet("Assets/Graphics/Character Selection/Monster Display Sprite/Big Blue Sprite.png", 300, 300, 1); //ref, tw, th, spacing
        SpriteSheet spriteMonster2 = new SpriteSheet("Assets/Graphics/Character Selection/Monster Display Sprite/Ghost Sprite.png", 420, 300, 1);
        SpriteSheet spriteMonster3 = new SpriteSheet("Assets/Graphics/Character Selection/Monster Display Sprite/Flame Sprite.png", 300, 300, 1);
        SpriteSheet spriteMonster4 = new SpriteSheet("Assets/Graphics/Character Selection/Monster Display Sprite/Ghost Sprite.png", 400, 300, 1);
        SpriteSheet spriteMonster5 = new SpriteSheet("Assets/Graphics/Character Selection/Monster Display Sprite/Ghost Sprite.png", 400, 300, 1);
        SpriteSheet spriteMonster6 = new SpriteSheet("Assets/Graphics/Character Selection/Monster Display Sprite/Ghost Sprite.png", 400, 300, 1);

        // TODO Replace correct names and adjust duration according to spritesheet
        animateMonsters = new Animation[]{
                new Animation(spriteMonster1, 250),
                new Animation(spriteMonster2, 250),
                new Animation(spriteMonster3, 250),
                new Animation(spriteMonster4, 250),
                new Animation(spriteMonster5, 250),
                new Animation(spriteMonster6, 250)
        };

        SpriteSheet spriteHuman1 = new SpriteSheet("Assets/Graphics/Character Selection/Human Display Sprite/Human 1 Sprite.jpg", 100, 100, 1); //ref, tw, th, spacing
        SpriteSheet spriteHuman2 = new SpriteSheet("Assets/Graphics/Character Selection/Human Display Sprite/Human 1 Sprite.jpg", 100, 100, 1);
        SpriteSheet spriteHuman3 = new SpriteSheet("Assets/Graphics/Character Selection/Human Display Sprite/Human 1 Sprite.jpg", 100, 100, 1);
        SpriteSheet spriteHuman4 = new SpriteSheet("Assets/Graphics/Character Selection/Human Display Sprite/Human 1 Sprite.jpg", 100, 100, 1);
        SpriteSheet spriteHuman5 = new SpriteSheet("Assets/Graphics/Character Selection/Human Display Sprite/Human 1 Sprite.jpg", 100, 100, 1);
        SpriteSheet spriteHuman6 = new SpriteSheet("Assets/Graphics/Character Selection/Human Display Sprite/Human 1 Sprite.jpg", 100, 100, 1);

        // TODO Replace correct names and adjust duration according to spritesheet
        animateHumans = new Animation[]{
                new Animation(spriteHuman1, 250),
                new Animation(spriteHuman2, 250),
                new Animation(spriteHuman3, 250),
                new Animation(spriteHuman4, 250),
                new Animation(spriteHuman5, 250),
                new Animation(spriteHuman6, 250)
        };

        // Coordinates for monster preview animations
        coordP1Monster = new Coordinate( (float)(displayWidth * 0.03), (float)(displayHeight * 0.20));
        coordP2Monster = new Coordinate( (float)(displayWidth - 300 - (displayWidth * 0.03)), (float)(displayHeight * 0.20));
        coordP1Human = new Coordinate( (float)(displayWidth * 0.05), (float)(displayHeight * 0.60));
        coordP2Human = new Coordinate( (float)(displayWidth - 200 - (displayWidth * 0.02)), (float)(displayHeight * 0.60));

        caseMonsterAnimation = 2;

        // TODO Replace with correct files and names
        imagesHumans1x1 = new Image[]{
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 1.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 2.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 3.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 4.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 5.png"),
                new Image("Assets/Graphics/Character Selection/Human Pic 1x1/Human Pic 5.png")
        };

        spacingXOfImageMonster = (imagesHumans1x1[0].getWidth() / 2) * 3;
        spacingYOfImageMonster = (imagesHumans1x1[0].getHeight() / 2) * 3;
        imageMonsterIndicator = new Image("Assets/player1.jpg"); // TODO Replace with correct files and names

        coordsImagesHuman = new Coordinate[]{
                new Coordinate((displayWidth / 2) - (imagesHumans1x1[0].getWidth() / 2) - spacingXOfImageMonster, 200),
                new Coordinate((displayWidth / 2) - (imagesHumans1x1[0].getWidth() / 2), 200),
                new Coordinate((displayWidth / 2) - (imagesHumans1x1[0].getWidth() / 2) + spacingXOfImageMonster, 200),
                new Coordinate((displayWidth / 2) - (imagesHumans1x1[0].getWidth() / 2) - spacingXOfImageMonster, 200 + spacingYOfImageMonster),
                new Coordinate((displayWidth / 2) - (imagesHumans1x1[0].getWidth() / 2), 200 + spacingYOfImageMonster),
                new Coordinate((displayWidth / 2) - (imagesHumans1x1[0].getWidth() / 2) + spacingXOfImageMonster, 200 + spacingYOfImageMonster),
        };

        coordMonsterIndicator = new Coordinate(coordsImagesHuman[1].getX(), coordsImagesHuman[1].getY());

        // TODO Replace with correct files and names
        imageBtnGame = new Image("Assets/player1.jpg");
        coordBtnGame = new Coordinate((displayWidth / 2) - (imageBtnGame.getWidth() / 2), displayHeight - 200);

        // Flags
        pressedEnter = false;
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

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

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

//            MainMenuState.resumeMusic();

            sbg.enterState(BeatBitBeatMain.getMainMenu(), new FadeOutTransition(), new FadeInTransition());
        }


        if (monsterPicking) {

            for (int i = 0; i < coordsImagesHuman.length; i++) {
                if (coordMonsterIndicator.equalTo(coordsImagesHuman[i])) {
                    caseMonsterAnimation = i + 1;       // index for casemonster starts at 1
                }
            }


            if (p1Picking) {    // if player 1's turn to pick
                if (pressedEnter) {
                    animateP1Monster = animateMonsters[caseMonsterAnimation - 1];
                    animateP1Human = animateHumans[caseMonsterAnimation - 1];

                    // TODO Gaming part
                    // TODO Monster object representing monster's unique capabilities
                    if (caseMonsterAnimation == 1) {
                        monsterP1 = new Monster1();
                    } else if (caseMonsterAnimation == 2) {
                        monsterP1 = new Monster2();
                    } else if (caseMonsterAnimation == 3) {
                        monsterP1 = new Monster3();
                    } else if (caseMonsterAnimation == 4) {
                        monsterP1 = new Monster4();
                    } else if (caseMonsterAnimation == 5) {
                        monsterP1 = new Monster5();
                    } else if (caseMonsterAnimation == 6) {
                        monsterP1 = new Monster6();
                    }
                    pressedEnter = false;
                    p1Picking = false;
                    p2Picking = true;       // p2's turn to pick
                }
            } else if (p2Picking) {    // if player 2's turn to pick

                animateMonsters[caseMonsterAnimation - 1].update(delta);
                animateHumans[caseMonsterAnimation - 1].update(delta);

                if (pressedEnter) {
                    animateP2Monster = animateMonsters[caseMonsterAnimation - 1];
                    animateP2Human = animateHumans[caseMonsterAnimation - 1];

                    // TODO Gaming part
                    // TODO Monster object representing monster's unique capabilities
                    if (caseMonsterAnimation == 1) {
                        monsterP2 = new Monster1();
                    } else if (caseMonsterAnimation == 2) {
                        monsterP2 = new Monster2();
                    } else if (caseMonsterAnimation == 3) {
                        monsterP2 = new Monster3();
                    } else if (caseMonsterAnimation == 4) {
                        monsterP2 = new Monster4();
                    } else if (caseMonsterAnimation == 5) {
                        monsterP2 = new Monster5();
                    } else if (caseMonsterAnimation == 6) {
                        monsterP2 = new Monster6();
                    }
                    pressedEnter = false;
                    monsterPicking = false;
                    p1Picking = false;
                    p2Picking = false;
                    songPicking = true;     // pick a song
                }

            }   // EO (p2Picking)

        } else if (songPicking) {    // song picking
//            MainMenuState.pauseMusic();
            animateP2Human.update(delta);
            animateP2Monster.update(delta);

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
                animateP2Human.update(delta);
                animateP2Monster.update(delta);

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

        // render monster selection icons
        for (int i = 0; i < imagesHumans1x1.length; i++) {
            imagesHumans1x1[i].draw(coordsImagesHuman[i].getX(), coordsImagesHuman[i].getY());
        }

        // draw game button when done picking
        if (!monsterPicking && !songPicking) {
            imageBtnGame.draw(coordBtnGame.getX(), coordBtnGame.getY());
        }

        if (monsterPicking) {
            imageMonsterIndicator.draw(coordMonsterIndicator.getX(), coordMonsterIndicator.getY());
            g.drawRect(coordMonsterIndicator.getX(), coordMonsterIndicator.getY(), imagesHumans1x1[0].getWidth(), imagesHumans1x1[0].getHeight());

            if (p1Picking) {
                g.drawString("Player 1 pick", (displayWidth / 2) - 10, 10);     // temp instruction display. to be improved

                animateMonsters[caseMonsterAnimation - 1].draw(coordP1Monster.getX(), coordP1Monster.getY());
                animateHumans[caseMonsterAnimation - 1].draw(coordP1Human.getX(), coordP1Human.getY());
            } else if (p2Picking) {
                g.drawString("Player 2 pick", (displayWidth / 2) - 10, 10);

                animateMonsters[caseMonsterAnimation - 1].getCurrentFrame().getFlippedCopy(true, false).draw(coordP2Monster.getX(), coordP2Monster.getY());
                animateHumans[caseMonsterAnimation - 1].getCurrentFrame().getFlippedCopy(true, false).draw(coordP2Human.getX(), coordP2Human.getY());

                animateP1Monster.draw(coordP1Monster.getX(), coordP1Monster.getY());        // draw preview P1's selected monster
                animateP1Human.draw(coordP1Human.getX(), coordP1Human.getY());

            }

        } else if (songPicking) {
            g.drawString("Pick a song", (displayWidth / 2) - 10, 10);

            animateP1Monster.draw(coordP1Monster.getX(), coordP1Monster.getY());
            animateP1Human.draw(coordP1Human.getX(), coordP1Human.getY());

            animateP2Monster.getCurrentFrame().getFlippedCopy(true, false).draw(coordP2Monster.getX(), coordP2Monster.getY());
            animateP2Human.getCurrentFrame().getFlippedCopy(true, false).draw(coordP2Human.getX(), coordP2Human.getY());

            // draw song art of the song being hovered at
            imagesSongArt[indexImageSongArt].draw(coordImageSongArt.getX(), coordImageSongArt.getY());

        } else {   // EO (songPicking)
            animateP1Monster.draw(coordP1Monster.getX(), coordP1Monster.getY());
            animateP1Human.draw(coordP1Human.getX(), coordP1Human.getY());
            animateP2Monster.getCurrentFrame().getFlippedCopy(true, false).draw(coordP2Monster.getX(), coordP2Monster.getY());
            animateP2Human.getCurrentFrame().getFlippedCopy(true, false).draw(coordP2Human.getX(), coordP2Human.getY());
        }


    }


    @Override
    public void keyPressed(int key, char pressedKey) {

        if (monsterPicking) {
            if (key == Input.KEY_UP) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordMonsterIndicator.getY() != coordsImagesHuman[0].getY()) {
                    coordMonsterIndicator.setY(coordMonsterIndicator.getY() - spacingYOfImageMonster);
                }
            } else if (key == Input.KEY_DOWN) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordMonsterIndicator.getY() != coordsImagesHuman[5].getY()) {
                    coordMonsterIndicator.setY(coordMonsterIndicator.getY() + spacingYOfImageMonster);
                }
            } else if (key == Input.KEY_LEFT) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordMonsterIndicator.getX() != coordsImagesHuman[0].getX()) {
                    coordMonsterIndicator.setX(coordMonsterIndicator.getX() - spacingXOfImageMonster);
                }
            } else if (key == Input.KEY_RIGHT) {
                soundPressArrows.playAsSoundEffect(1.0f, 1.0f, false);
                if (coordMonsterIndicator.getX() != coordsImagesHuman[5].getX()) {
                    coordMonsterIndicator.setX(coordMonsterIndicator.getX() + spacingXOfImageMonster);
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
