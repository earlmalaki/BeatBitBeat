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
 * <p>
 * /**
 * Done:
 * - Key Listener for receiving bars (ASDF, HJKL)
 * - Image handler for background
 * - Music player
 * - Randomized dropping of notes
 * <p>
 * To Do:
 * - Put wallpaper file
 * - Put receiving bar, vertical bar, and note graphic files
 * - finalize positioning of elements after putting in final graphics
 * - Music beat map making and reading
 * - Dropping notes according to beat map
 * - Better receiving bar accuracy
 * - GAME PART. Monster objects, hp, skills, resources, etc.
 * - End of game state
 * <p>
 * game.Note:
 * - Prioritize MVP first.
 */

package game;

import game.monsters.Monster;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.io.*;
import java.util.ArrayList;

public class GameProperState extends BasicGameState implements KeyListener {

    private Image imageBG;

    public static Monster monsterP1;
    public static Monster monsterP2;

    private ArrayList<Note> notesP1 = new ArrayList<>();
    private ArrayList<Note> notesP2 = new ArrayList<>();

    private Image[] imagesNotes;
    private Image[] imagesPressedHitbox;

    // Player 1 note x positions
    private float p1x1 = 27f;
    private float p1x2 = 70f;
    private float p1x3 = 113f;
    private float p1x4 = 156f;

    // Player 2 note x positions
    private float p2x1 = 1083f;
    private float p2x2 = 1126f;
    private float p2x3 = 1169f;
    private float p2x4 = 1213f;

    // Significant Y axis positions
    private float startingYPos = 0f;
    private float badYPos = 585f;
    private float goodYPos = 610f;
    private float perfectYPos = 635f;
    private float endingYPos = 660;    // miss

    private boolean badHitP1;
    private boolean badHitP2;
    private boolean goodHitP1;
    private boolean goodHitP2;
    private boolean perfectHitP1;
    private boolean perfectHitP2;
    private boolean missHitP1;
    private boolean missHitP2;

    private boolean skill1P1 = false;
    private boolean skill2P1 = false;
    private boolean skillUltP1 = false;
    private boolean skill1P2 = false;
    private boolean skill2P2 = false;
    private boolean skillUltP2 = false;

    private static Music gameMusic;
    private Coordinate coordMonsterP1 = new Coordinate((displayWidth / 2) - 400, 100);
    private Coordinate coordMonsterP2 = new Coordinate((displayWidth / 2) - 200, 100);
    private Coordinate coordHumanP1 = new Coordinate((displayWidth / 2) - 350, 350);
    private Coordinate coordHumanP2 = new Coordinate((displayWidth - 450), 350);

    private static Animation animationPlayer1;
    private static Animation animationPlayer2;

    private static final int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private static final int displayHeight = BeatBitBeatMain.getDisplayHeight();

    // Music position
    private float musicPosition = 00.00f;
    private String musicPositionString = "";

    private float speedNoteDrop = 4f;
    private int timePassed = 0;     // in milliseconds
    private boolean skillCast = false;
    private int slowDuration = 0;  // 3000ms == 3s

    private static BufferedReader br;
    private boolean pressedEscape = false;
    private boolean pressed1 = false;
    private float pitchSlowMusic = 0.05f;

    private boolean pressedQ = false;
    private boolean pressedW = false;
    private boolean pressedE = false;
    private boolean pressedR = false;

    private boolean pressedU = false;
    private boolean pressedI = false;
    private boolean pressedO = false;
    private boolean pressedP = false;

    // Create a font with the size of 20, and not bold or italic.
    UnicodeFont fontCombo;
    UnicodeFont fontResources;
    UnicodeFont fontTime;


    public int getID() {
        return BeatBitBeatMain.getGameProper();
    }

    public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {

        fontCombo = new UnicodeFont("Assets/Fonts/Disposable Droid/DisposableDroidBB.ttf", 48, false, false);
        fontCombo.getEffects().add(new ColorEffect(java.awt.Color.white));
        fontCombo.addAsciiGlyphs();
        fontCombo.loadGlyphs();

        fontTime = new UnicodeFont("Assets/Fonts/Disposable Droid/DisposableDroidBB.ttf", 34, false, false);
        fontTime.getEffects().add(new ColorEffect(java.awt.Color.white));
        fontTime.addAsciiGlyphs();
        fontTime.loadGlyphs();

        fontResources = new UnicodeFont("Assets/Fonts/Disposable Droid/DisposableDroidBB.ttf", 20, false, false);
        fontResources.getEffects().add(new ColorEffect(java.awt.Color.white));
        fontResources.addAsciiGlyphs();
        fontResources.loadGlyphs();

        imageBG = new Image("Assets/Graphics/Game Proper/Game Proper BG.png");

        imagesNotes = new Image[]{
                new Image("Assets/Graphics/Game Proper/Note Red.png"),
                new Image("Assets/Graphics/Game Proper/Note Green.png"),
                new Image("Assets/Graphics/Game Proper/Note Blue.png"),
                new Image("Assets/Graphics/Game Proper/Note Yellow.png")
        };

        imagesPressedHitbox = new Image[]{
                new Image("Assets/Graphics/Game Proper/Pressed Hitbox Red.png"),
                new Image("Assets/Graphics/Game Proper/Pressed Hitbox Green.png"),
                new Image("Assets/Graphics/Game Proper/Pressed Hitbox Blue.png"),
                new Image("Assets/Graphics/Game Proper/Pressed Hitbox Yellow.png")
        };

    }


    int delta;  // for printing. temporary
    float xMouse;
    float yMouse;

    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
        this.delta = delta;
        Input input = container.getInput();
        xMouse = input.getMouseX();
        yMouse = input.getMouseY();


        // TODO adjust pitch. match pitch loss and map read speed loss
        // slow music and read map
        if (skillCast) {
            timePassed += delta;
            if (timePassed >= slowDuration) {
                skillCast = false;
                timePassed = 0;
                speedNoteDrop = 4f;
                skill1P1 = false;
                skill2P1 = false;
                skillUltP1 = false;
                skill1P2 = false;
                skill2P2 = false;
                skillUltP2 = false;
                gameMusic.play();
                gameMusic.setPosition(musicPosition);
            } else {
                // Apply effects of slow skill
                // Music pitch decreased from 1 to 0.05, or %5 percent
                // so note drop and file reading should also decrease by the same percentage

                // reduce speed of note vertical drop
                // original speed is 4 px per second
                // 4 * 0.05 = 0.2
                speedNoteDrop = 0.2f;

                // reduce speed of file reading
                // original speed is 60 lines per second
                // 60 * 0.05 = 3
                // thus, reading should be at 3 lines per second
                if (timePassed % 300 == 0) {
                    readBeatMap();
                }
            }

        } else {
            readBeatMap();
        }

        // Control the falling of noteBars bars
        for (int i = 0; i < notesP1.size(); i++) {      // or i < notesP2.size(), notes p1 and p2 are of same size
            notesP1.get(i).setY(notesP1.get(i).getY() + speedNoteDrop);
            notesP2.get(i).setY(notesP2.get(i).getY() + speedNoteDrop);
        }

        // Detect if notes reach the ending y pos
        if (notesP1.size() != 0) {  // notesP1 size == notesP2 size
            if (notesP1.get(0).getY() > endingYPos) {
                // play music
                if (!gameMusic.playing()) {
                    gameMusic.play();
                }

                notesP1.remove(0);
                notesP2.remove(0);
            }
        }


        musicPosition = gameMusic.getPosition();

        if (monsterP1.getHp() <= 0) {
            sbg.enterState(BeatBitBeatMain.getGameOver(), new FadeOutTransition(), new FadeInTransition());
        } else if (monsterP2.getHp() <= 0) {
            sbg.enterState(BeatBitBeatMain.getGameOver(), new FadeOutTransition(), new FadeInTransition());
        }

        if(pressed1){
            sbg.enterState(BeatBitBeatMain.getGameOver(), new FadeOutTransition(), new FadeInTransition());
        }
    }

    public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {

        imageBG.draw();
/*
        // Game Screen borders
        g.drawLine(0, endingYPos, displayWidth, endingYPos);
testing
*/
        // render falling notes
        for (int i = 0; i < notesP1.size(); i++) {
            if (notesP1.get(i).getY() < perfectYPos) {
                notesP1.get(i).getImage().draw(notesP1.get(i).getX(), notesP1.get(i).getY());
                notesP2.get(i).getImage().draw(notesP2.get(i).getX(), notesP2.get(i).getY());
            }
        }
/*
        g.setColor(Color.white);
        g.drawString("Time", (displayWidth / 2) - 5, 5);
        String strMusicPosition = null;
        if (gameMusic.playing()) {
            strMusicPosition = String.valueOf(musicPosition);
            g.drawString(strMusicPosition, (displayWidth / 2) - (strMusicPosition.length()), 20);

        }
*/
        // Hitbox feedback
        // Draw glowing hitbox if corresponding key is pressed
        if (pressedQ) {
            imagesPressedHitbox[0].draw(p1x1 - 19, perfectYPos - 15);
        }
        if (pressedW) {
            imagesPressedHitbox[1].draw(p1x2 - 19, perfectYPos - 15);
        }
        if (pressedE) {
            imagesPressedHitbox[2].draw(p1x3 - 19, perfectYPos - 15);
        }
        if (pressedR) {
            imagesPressedHitbox[3].draw(p1x4 - 19, perfectYPos - 15);
        }

        if (pressedU) {
            imagesPressedHitbox[0].draw(p2x1 - 19, perfectYPos - 15);
        }
        if (pressedI) {
            imagesPressedHitbox[1].draw(p2x2 - 19, perfectYPos - 15);
        }
        if (pressedO) {
            imagesPressedHitbox[2].draw(p2x3 - 19, perfectYPos - 15);
        }
        if (pressedP) {
            imagesPressedHitbox[3].draw(p2x4 - 19, perfectYPos - 15);
        }
        /*

*/
        // TODO fix skill animation
        // Draw player character animations
        if (skill1P1) {
            monsterP2.getAnimationIdle().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
            monsterP1.getAnimationSkill1().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
        } else if (skill2P1) {
            monsterP2.getAnimationIdle().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
            monsterP1.getAnimationSkill2().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
        } else if (skillUltP1) {
            monsterP2.getAnimationIdle().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
            monsterP1.getAnimationSkillUlt().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
        } else if (skill1P2) {
            monsterP1.getAnimationIdle().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
            monsterP2.getAnimationSkill1().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
        } else if (skill2P2) {
            monsterP1.getAnimationIdle().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
            monsterP2.getAnimationSkill2().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
        } else if (skillUltP2) {
            monsterP1.getAnimationIdle().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
            monsterP2.getAnimationSkillUlt().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
        } else {    // Idle
            monsterP1.getAnimationIdle().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
            monsterP2.getAnimationIdle().draw(coordMonsterP2.getX(), coordMonsterP2.getY());

            monsterP1.getAnimationHumanIdle().draw(coordHumanP1.getX(), coordHumanP1.getY());
            monsterP2.getAnimationHumanIdle().draw(coordHumanP2.getX(), coordHumanP2.getY());
        }


        if (monsterP1.getCombo() > 5) {
            fontCombo.drawString(92, 110, "" + monsterP1.getCombo());
        }
        if (monsterP2.getCombo() > 5) {
            fontCombo.drawString(1147, 110, "" + monsterP2.getCombo());
        }

        // Print resources
        fontResources.drawString(255, 684, "" + monsterP1.getResourceRed());
        fontResources.drawString(255, 706, "" + monsterP1.getResourceGreen());
        fontResources.drawString(255, 729, "" + monsterP1.getResourceBlue());
        fontResources.drawString(255, 752, "" + monsterP1.getResourceYellow());

        fontResources.drawString(995, 684, "" + monsterP2.getResourceRed());
        fontResources.drawString(995, 706, "" + monsterP2.getResourceGreen());
        fontResources.drawString(995, 729, "" + monsterP2.getResourceBlue());
        fontResources.drawString(995, 752, "" + monsterP2.getResourceYellow());

        // print music position / time

        if (gameMusic.playing()) {
            musicPositionString = String.format("%.2f", musicPosition);
            fontTime.drawString((displayWidth / 2) - (musicPositionString.length() * 12) / 2, 30, musicPositionString);
        } else {
            fontTime.drawString((displayWidth / 2) - 35, 30, "00.00");
        }

        //
        g.setColor(Color.white);
        g.drawString("DELTA = " + delta, 10, 30);
        g.drawString("X = " + xMouse + " Y = " + yMouse, 10, 50);
    }

    @Override
    public void keyPressed(int key, char pressedKey) {
        // Key listener
        if(key == Input.KEY_1){
            pressed1 = true;
        }
        if (key == Input.KEY_Q) {
            pressedQ = true;

            // if note is for the corresponding note bar
            if (notesP1.get(0).getX() == p1x1 + 1) {

                // if note is near or within corresponding hit box
                if (badYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= goodYPos) {    // bad hit
                    // combo will end if BAD HIT
                    monsterP1.setCombo(0);
                } else if (goodYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= perfectYPos) {    // good hit
                    monsterP1.addResourceRed(1);
                    monsterP1.setCombo(monsterP1.getCombo() + 1);
                } else if (perfectYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= endingYPos) {    // perfect hit
                    monsterP1.addResourceRed(2);
                    monsterP1.setCombo(monsterP1.getCombo() + 1);
                }
            }

        }
        if (key == Input.KEY_W) {
            pressedW = true;
            if (notesP1.get(0).getX() == p1x2 + 1) {
                if (badYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= goodYPos) {    // bad hit
                    monsterP1.setCombo(0);
                } else if (goodYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= perfectYPos) {    // good hit
                    monsterP1.addResourceGreen(1);
                    monsterP1.setCombo(monsterP1.getCombo() + 1);
                } else if (perfectYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= endingYPos) {    // perfect hit
                    monsterP1.addResourceGreen(2);
                    monsterP1.setCombo(monsterP1.getCombo() + 1);
                }
            }
        }

        if (key == Input.KEY_E) {
            pressedE = true;
            if (notesP1.get(0).getX() == p1x3 + 1) {
                if (badYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= goodYPos) {    // bad hit

                    monsterP1.setCombo(0);
                } else if (goodYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= perfectYPos) {    // good hit
                    monsterP1.addResourceBlue(1);
                    monsterP1.setCombo(monsterP1.getCombo() + 1);

                } else if (perfectYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= endingYPos) {    // perfect hit
                    monsterP1.addResourceBlue(2);
                    monsterP1.setCombo(monsterP1.getCombo() + 1);
                }
            }
        }

        if (key == Input.KEY_R) {
            pressedR = true;
            if (notesP1.get(0).getX() == p1x4 + 1) {
                if (badYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= goodYPos) {    // bad hit
                    monsterP1.setCombo(0);
                } else if (goodYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= perfectYPos) {    // good hit
                    monsterP1.addResourceYellow(1);
                    monsterP1.setCombo(monsterP1.getCombo() + 1);
                } else if (perfectYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= endingYPos) {    // perfect hit
                    monsterP1.addResourceYellow(2);
                    monsterP1.setCombo(monsterP1.getCombo() + 1);
                }
            }
        }


        if (key == Input.KEY_U) {
            pressedU = true;
            if (notesP2.get(0).getX() == p2x1 + 1) {
                if (badYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= goodYPos) {    // bad hit
                    monsterP2.setCombo(0);
                } else if (goodYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= perfectYPos) {    // good hit
                    monsterP2.addResourceRed(1);
                    monsterP2.setCombo(monsterP2.getCombo() + 1);

                } else if (perfectYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= endingYPos) {    // perfect hit
                    monsterP2.addResourceRed(2);
                    monsterP2.setCombo(monsterP2.getCombo() + 1);
                }
            }
        }

        if (key == Input.KEY_I) {
            pressedI = true;
            if (notesP2.get(0).getX() == p2x2 + 1) {
                if (badYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= goodYPos) {    // bad hit
                    // no resource gain
                    // display bad hit!
                    monsterP2.setCombo(0);
                } else if (goodYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= perfectYPos) {    // good hit
                    monsterP2.addResourceGreen(1);
                    monsterP2.setCombo(monsterP2.getCombo() + 1);
                } else if (perfectYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= endingYPos) {    // perfect hit
                    monsterP2.addResourceGreen(2);
                    monsterP2.setCombo(monsterP2.getCombo() + 1);
                }
            }
        }

        if (key == Input.KEY_O) {
            pressedO = true;
            if (notesP2.get(0).getX() == p2x3 + 1) {
                if (badYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= goodYPos) {    // bad hit
                    // no resource gain
                    // display bad hit!
                    monsterP2.setCombo(0);
                } else if (goodYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= perfectYPos) {    // good hit
                    monsterP2.addResourceBlue(1);
                    monsterP2.setCombo(monsterP2.getCombo() + 1);
                } else if (perfectYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= endingYPos) {    // perfect hit
                    monsterP2.addResourceBlue(2);
                    monsterP2.setCombo(monsterP2.getCombo() + 1);
                }
            }
        }

        if (key == Input.KEY_P) {
            pressedP = true;
            if (notesP2.get(0).getX() == p2x4 + 1) {
                if (badYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= goodYPos) {    // bad hit
                    // no resource gain
                    // display bad hit!
                    monsterP2.setCombo(0);
                } else if (goodYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= perfectYPos) {    // good hit
                    monsterP2.addResourceYellow(1);
                    monsterP2.setCombo(monsterP2.getCombo() + 1);
                } else if (perfectYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= endingYPos) {    // perfect hit
                    monsterP2.addResourceYellow(2);
                    monsterP2.setCombo(monsterP2.getCombo() + 1);
                }
            }
        }


        /*** Start of Skills ***/
        if (skill1P1 || skill2P1 || skillUltP1 || skill1P2 || skill2P2 || skillUltP2) {     // disable casting skill while a skill is ongoing
            if (key == Input.KEY_X) {
                if (monsterP1.checkResources(monsterP1.getCostSkill1())) {   //monsters has resources, go atk
                    skillCast(monsterP1.getDurationSkill1());       // call skillCast and pass duration of slow motion
                    skill1P1 = true;
                    monsterP1.skill1();
                    monsterP1.attack(monsterP2);
                }
            }

            if (key == Input.KEY_C) {
                if (monsterP1.checkResources(monsterP1.getCostSkill2())) { //monsters has resources, go atk
                    skillCast(monsterP1.getDurationSkill2());       // call skillCast and pass duration of slow motion
                    skill2P1 = true;
                    monsterP1.skill2();
                    monsterP1.attack(monsterP2);
                }
            }

            if (key == Input.KEY_V) {
                if (monsterP1.checkResources(monsterP1.getCostSkillUlt())) { //monsters has resources, go atk
                    skillCast(monsterP1.getDurationSkillUlt());       // call skillCast and pass duration of slow motion
                    skillUltP1 = true;
                    monsterP1.skillUlt();
                    monsterP1.attack(monsterP2);
                }
            }

            if (key == Input.KEY_B) {

                if (monsterP2.checkResources(monsterP2.getCostSkill1())) {//monsters has resources, go atk
                    skillCast(monsterP2.getDurationSkill1());       // call skillCast and pass duration of slow motion
                    skill1P2 = true;
                    monsterP2.skill1();
                    monsterP2.attack(monsterP1);
                }
            }

            if (key == Input.KEY_N) {
                if (monsterP2.checkResources(monsterP2.getCostSkill2())) { //monsters has resources, go atk
                    skillCast(monsterP2.getDurationSkill2());       // call skillCast and pass duration of slow motion
                    skill2P2 = true;
                    monsterP2.skill2();
                    monsterP2.attack(monsterP1);
                }
            }

            if (key == Input.KEY_M) {
                if (monsterP2.checkResources(monsterP2.getCostSkillUlt())) { //monsters has resources, go atk
                    skillCast(monsterP2.getDurationSkillUlt());       // call skillCast and pass duration of slow motion
                    skillUltP2 = true;
                    monsterP2.skillUlt();
                    monsterP2.attack(monsterP1);
                }
            }
        }


        /*** End of Skills ***/


        // Pause
        // TODO pause game
        if (key == Input.KEY_ESCAPE) {
            pressedEscape = true;
        }
    }   // end of keypress method


    // method for slowing things down
    // accepts duration in MS
    public void skillCast(int duration) {
        // match pitch loss and map read speed loss
        slowDuration = duration;
        skillCast = true;   // flag for handling of slow in update() method
        gameMusic.play(pitchSlowMusic, 1f);
        gameMusic.setPosition(musicPosition);
    }

    @Override
    public void keyReleased(int key, char pressedKey) {

        if (key == Input.KEY_Q) {
            pressedQ = false;
        }
        if (key == Input.KEY_W) {
            pressedW = false;
        }
        if (key == Input.KEY_E) {
            pressedE = false;
        }
        if (key == Input.KEY_R) {
            pressedR = false;
        }

        if (key == Input.KEY_U) {
            pressedU = false;
        }
        if (key == Input.KEY_I) {
            pressedI = false;
        }
        if (key == Input.KEY_O) {
            pressedO = false;
        }
        if (key == Input.KEY_P) {
            pressedP = false;
        }

    }

    public void readBeatMap() {
        try {
            String line = br.readLine();

            if (line != null) {
                if (line.equals("1000")) {
                    notesP1.add(new Note(imagesNotes[0], p1x1 + 1, startingYPos));
                    notesP2.add(new Note(imagesNotes[0], p2x1 + 1, startingYPos));

                } else if (line.equals("0100")) {
                    notesP1.add(new Note(imagesNotes[1], p1x2 + 1, startingYPos));
                    notesP2.add(new Note(imagesNotes[1], p2x2 + 1, startingYPos));

                } else if (line.equals("0010")) {
                    notesP1.add(new Note(imagesNotes[2], p1x3 + 1, startingYPos));
                    notesP2.add(new Note(imagesNotes[2], p2x3 + 1, startingYPos));

                } else if (line.equals("0001")) {
                    notesP1.add(new Note(imagesNotes[3], p1x4 + 1, startingYPos));
                    notesP2.add(new Note(imagesNotes[3], p2x4 + 1, startingYPos));

                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void setGameMusic(Music music) {
        gameMusic = music;
    }

    public static void setBeatMap(File file) {
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void setAnimationPlayer1(Animation animation) {
        animationPlayer1 = animation;
    }

    public static void setAnimationPlayer2(Animation animation) {
        animationPlayer2 = animation;
    }

    public static void setMonsterP1(Monster monsterp1) {
        monsterP1 = monsterp1;
    }

    public static void setMonsterP2(Monster monsterp2) {
        monsterP2 = monsterp2;
    }
} // END OF CLASS
