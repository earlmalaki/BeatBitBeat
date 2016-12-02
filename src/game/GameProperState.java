/**
 * Albores, Allyssa
 * Bedio, Aiden Justin
 * Malaki, Earl Timothy
 * Paler, Timothy River
 * <p>
 * BSCS - II | UP - Cebu
 * CMSC22 - OOP
 * Final Project
 */

package game;

import game.monsters.Monster;
import game.monsters.Monster2;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.io.*;
import java.util.ArrayList;

/* TODO
* - spritesheet fix:
* - flame x-burner
* - flame firefly
* - ghost 3
* - root whip
* - root sakura
*/

public class GameProperState extends BasicGameState implements KeyListener {

    private Image imageBG;

    public static Monster monsterP1;
    public static Monster monsterP2;

    private static ArrayList<Note> notesP1 = new ArrayList<>();
    private static ArrayList<Note> notesP2 = new ArrayList<>();

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
    private float badYPos = 557f;
    private float goodYPos = 582f;
    private float perfectYPos = 607f;
    private float endingYPos = 632;    // miss

    private static boolean skill1P1 = false;
    private static boolean skill2P1 = false;
    private static boolean skillUltP1 = false;
    private static boolean skill1P2 = false;
    private static boolean skill2P2 = false;
    private static boolean skillUltP2 = false;
    private boolean endOfGame = false;
    private static Music gameMusic;
    private Coordinate coordMonsterP1 = new Coordinate((displayWidth / 2) - 400, 100);
    private Coordinate coordMonsterP2 = new Coordinate((displayWidth / 2) - 200, 100);
    private Coordinate coordHumanP1 = new Coordinate((displayWidth / 2) - 350, 350);
    private Coordinate coordHumanP2 = new Coordinate((displayWidth - 450), 350);


    private static final int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private static final int displayHeight = BeatBitBeatMain.getDisplayHeight();

    // Music position
    private float musicPosition = 00.00f;
    private float musicTimeLeft = 00.00f;
    private String musicPositionString = "";

    private static float speedNoteDrop = 4f;
    private static int timePassed = 0;     // in milliseconds
    private static boolean skillCast = false;
    private static int slowDuration = 0;  // 3000ms == 3s

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

    private static int timePassedSinceSkill1P1 = 0;
    private static int timePassedSinceSkill2P1 = 0;
    private static int timePassedSinceSkillUltP1 = 0;
    private static int timePassedSinceSkill1P2 = 0;
    private static int timePassedSinceSkill2P2 = 0;
    private static int timePassedSinceSkillUltP2 = 0;

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

        // adjust pitch loss and map read speed loss accordingly
        // slow music and read map
        if (skillCast) {
            timePassed += delta;
            if (timePassed >= slowDuration) {   // Skill animation done
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

        if (endOfGame) {
            endOfGame = false;

            skillCast = false;
            timePassed = 0;
            speedNoteDrop = 4f;
            skill1P1 = false;
            skill2P1 = false;
            skillUltP1 = false;
            skill1P2 = false;
            skill2P2 = false;
            skillUltP2 = false;
            gameMusic.stop();
            MainMenuState.playMusic();
            sbg.enterState(BeatBitBeatMain.getGameOver(), new FadeOutTransition(), new FadeInTransition());
        }
        if ((monsterP1.getHp() <= 0 || monsterP2.getHp() <= 0)) {
            skillCast = false;
            timePassed = 0;
            speedNoteDrop = 4f;
            skill1P1 = false;
            skill2P1 = false;
            skillUltP1 = false;
            skill1P2 = false;
            skill2P2 = false;
            skillUltP2 = false;
            gameMusic.stop();
            MainMenuState.playMusic();
            sbg.enterState(BeatBitBeatMain.getGameOver(), new FadeOutTransition(), new FadeInTransition());
        }

        // temporary
        if (pressed1) {
            pressed1 = false;

            skillCast = false;
            timePassed = 0;
            speedNoteDrop = 4f;
            skill1P1 = false;
            skill2P1 = false;
            skillUltP1 = false;
            skill1P2 = false;
            skill2P2 = false;
            skillUltP2 = false;
            gameMusic.stop();
            MainMenuState.playMusic();
            sbg.enterState(BeatBitBeatMain.getGameOver(), new FadeOutTransition(), new FadeInTransition());
        }

        if (skill1P1 || timePassedSinceSkill1P1 > 0) {
            timePassedSinceSkill1P1 += delta;
            if (timePassedSinceSkill1P1 >= monsterP1.getCooldownSkill1()) {
                timePassedSinceSkill1P1 = 0;
                skill1P1 = false;
            }
        }
        if (skill2P1 || timePassedSinceSkill2P1 > 0) {
            timePassedSinceSkill2P1 += delta;
            if (timePassedSinceSkill2P1 >= monsterP1.getCooldownSkill2()) {
                timePassedSinceSkill2P1 = 0;
                skill2P1 = false;
            }
        }
        if (skillUltP1 || timePassedSinceSkillUltP1 > 0) {
            timePassedSinceSkillUltP1 += delta;
            if (timePassedSinceSkillUltP1 >= monsterP1.getCooldownSkillUlt()) {
                timePassedSinceSkillUltP1 = 0;
                skillUltP1 = false;
            }
        }

        if (skill1P2 || timePassedSinceSkill1P2 > 0) {
            timePassedSinceSkill1P2 += delta;
            if (timePassedSinceSkill1P2 >= monsterP2.getCooldownSkill1()) {
                timePassedSinceSkill1P2 = 0;
                skill1P2 = false;
            }
        }
        if (skill2P2 || timePassedSinceSkill2P2 > 0) {
            timePassedSinceSkill2P2 += delta;
            if (timePassedSinceSkill2P2 >= monsterP2.getCooldownSkill2()) {
                timePassedSinceSkill2P2 = 0;
                skill2P2 = false;
            }
        }
        if (skillUltP2 || timePassedSinceSkillUltP2 > 0) {
            timePassedSinceSkillUltP2 += delta;
            if (timePassedSinceSkillUltP2 >= monsterP2.getCooldownSkillUlt()) {
                timePassedSinceSkillUltP2 = 0;
                skillUltP2 = false;
            }
        }
    }


    public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {

        imageBG.draw();
        int xx = 0;

        //320, 615
        for (int i = 0; i < monsterP1.getSkillIcons().length; i++) {
            if (timePassedSinceSkill1P1 != 0 && timePassedSinceSkill1P1 < monsterP1.getCooldownSkill1() || !monsterP1.checkResources(monsterP1.getCostSkill1())){
                monsterP1.getSkillIcons()[0].draw(319, 614, Color.darkGray);
            }else{
                monsterP1.getSkillIcons()[0].draw(319, 614);
            }
            if (timePassedSinceSkill2P1 != 0 && timePassedSinceSkill2P1 < monsterP1.getCooldownSkill2() || !monsterP1.checkResources(monsterP1.getCostSkill2())){
                monsterP1.getSkillIcons()[1].draw(419, 614, Color.darkGray);
            }else{
                monsterP1.getSkillIcons()[1].draw(419, 614);
            }
            if (timePassedSinceSkillUltP1 != 0 && timePassedSinceSkillUltP1 < monsterP1.getCooldownSkillUlt() || !monsterP1.checkResources(monsterP1.getCostSkillUlt())){
                monsterP1.getSkillIcons()[2].draw(519, 614, Color.darkGray);
            }else{
                monsterP1.getSkillIcons()[2].draw(519, 614);
            }


            //test.draw(319 + xx, 614);
            //xx += 100;
        }

        int xy = 0;
        for (Image test : monsterP2.getSkillIcons()) {

            if (timePassedSinceSkill1P2 != 0 && timePassedSinceSkill1P2 < monsterP2.getCooldownSkill1() || !monsterP2.checkResources(monsterP2.getCostSkill1())){
                monsterP2.getSkillIcons()[0].draw(669, 614, Color.darkGray);
            }else{
                monsterP2.getSkillIcons()[0].draw(669, 614);
            }
            if (timePassedSinceSkill2P2 != 0 && timePassedSinceSkill2P2 < monsterP2.getCooldownSkill2() || !monsterP2.checkResources(monsterP2.getCostSkill2())){
                monsterP2.getSkillIcons()[1].draw(769, 614, Color.darkGray);
            }else{
                monsterP2.getSkillIcons()[1].draw(769, 614);
            }
            if (timePassedSinceSkillUltP2 != 0 && timePassedSinceSkillUltP2 < monsterP2.getCooldownSkillUlt() || !monsterP2.checkResources(monsterP2.getCostSkillUlt())){
                monsterP2.getSkillIcons()[2].draw(869, 614, Color.darkGray);
            }else{
                monsterP2.getSkillIcons()[2].draw(869, 614);
            }
//            test.draw(669 + xy, 614);
//            xy += 100;
        }

        // render falling notes
        for (int i = 0; i < notesP1.size(); i++) {
            if (notesP1.get(i).getY() < perfectYPos) {
                notesP1.get(i).getImage().draw(notesP1.get(i).getX(), notesP1.get(i).getY());
                notesP2.get(i).getImage().draw(notesP2.get(i).getX(), notesP2.get(i).getY());
            }
        }

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

        // Draw player character animations
        if (skill1P1) {
            monsterP2.getAnimationIdle().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
            monsterP1.getAnimationSkill1().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
        } else if (skill2P1) {
            if (monsterP1 instanceof game.monsters.Monster3) {
                monsterP1.getAnimationIdle().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
                monsterP2.getAnimationIdle().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
                monsterP1.getAnimationSkill2().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
            } else {
                monsterP2.getAnimationIdle().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
                monsterP1.getAnimationSkill2().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
            }
        } else if (skillUltP1) {
            if (monsterP1 instanceof game.monsters.Monster5) {
                monsterP1.getAnimationIdle().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
                monsterP2.getAnimationIdle().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
                monsterP1.getAnimationSkillUlt().draw(212, 40);
            } else if (monsterP1 instanceof game.monsters.Monster2) {
                monsterP1.getAnimationSkillUlt().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
                monsterP2.getAnimationIdle().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
                ((Monster2) monsterP1).getAnimationSpikes().draw(coordMonsterP2.getX() + 180, coordMonsterP2.getY() - 100);
            } else {
                monsterP2.getAnimationIdle().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
                monsterP1.getAnimationSkillUlt().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
            }
        } else if (skill1P2) {

            monsterP1.getAnimationIdle().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
            monsterP2.getAnimationSkill1().draw(coordMonsterP2.getX(), coordMonsterP2.getY());

        } else if (skill2P2) {
            if (monsterP2 instanceof game.monsters.Monster3) {
                monsterP1.getAnimationIdle().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
                monsterP2.getAnimationIdle().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
                monsterP2.getAnimationSkill2().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
            } else if(monsterP2 instanceof game.monsters.Monster5){
                monsterP1.getAnimationIdle().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
                monsterP2.getAnimationSkill2().draw(coordMonsterP2.getX() - 120, coordMonsterP2.getY());
            } else {
                monsterP1.getAnimationIdle().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
                monsterP2.getAnimationSkill2().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
            }
        } else if (skillUltP2) {
            if (monsterP2 instanceof game.monsters.Monster5) {

                monsterP1.getAnimationIdle().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
                monsterP2.getAnimationIdle().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
                monsterP2.getAnimationSkillUlt().draw(displayWidth / 2 - 400, 40);
            } else if (monsterP2 instanceof game.monsters.Monster3) {

                monsterP1.getAnimationIdle().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
                monsterP2.getAnimationSkillUlt().draw(coordMonsterP2.getX() - 200, coordMonsterP2.getY());

            } else if (monsterP2 instanceof game.monsters.Monster2) {
                monsterP1.getAnimationIdle().draw(coordMonsterP1.getX(), coordMonsterP2.getY());
                monsterP2.getAnimationSkillUlt().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
                ((Monster2) monsterP2).getAnimationSpikes().draw(coordMonsterP1.getX() - 50, coordMonsterP1.getY() - 70);

            } else {
                monsterP1.getAnimationIdle().draw(coordMonsterP1.getX(), coordMonsterP1.getY());
                monsterP2.getAnimationSkillUlt().draw(coordMonsterP2.getX(), coordMonsterP2.getY());
            }


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


        // Skill cooldowns
//        (float) (0.87 * (87 - timePassedSinceSkill1P1)
//        (float) -(2.55 * (100 - monsterP1.getHp())),8);
//        g.fillRect(322, 620,87f, (float) (0.87 * (87 - timePassedSinceSkill1P1)));

//        g.drawString("" + timePassedSinceSkill1P1, 350, 700);
//        g.drawString("" + timePassedSinceSkill2P1, 450, 700);
//        g.drawString("" + timePassedSinceSkillUltP1, 550, 700);
//        g.drawString("" + timePassedSinceSkill1P2, 700, 700);
//        g.drawString("" + timePassedSinceSkill2P2, 800, 700);
//        g.drawString("" + timePassedSinceSkillUltP2, 900, 700);


        // Print resources
        fontResources.drawString(262, 615, "" + monsterP1.getResourceRed());
        fontResources.drawString(262, 638, "" + monsterP1.getResourceGreen());
        fontResources.drawString(262, 661, "" + monsterP1.getResourceBlue());
        fontResources.drawString(262, 684, "" + monsterP1.getResourceYellow());

        fontResources.drawString(983, 615, "" + monsterP2.getResourceRed());
        fontResources.drawString(983, 638, "" + monsterP2.getResourceGreen());
        fontResources.drawString(983, 661, "" + monsterP2.getResourceBlue());
        fontResources.drawString(983, 684, "" + monsterP2.getResourceYellow());

        // print music position / time

        if (gameMusic.playing()) {
            musicPositionString = String.format("%.2f", musicPosition);
            fontTime.drawString((displayWidth / 2) - (musicPositionString.length() * 12) / 2, 30, musicPositionString);
        } else {
            fontTime.drawString((displayWidth / 2) - 35, 30, "00.00");
        }

        monsterP1.getImageFaceHealthBar().draw(243, 14);
        monsterP2.getImageFaceHealthBar().draw(717, 14);

        g.setColor(Color.darkGray);
        g.fillRect(554, 20, (float) -(2.55 * (100 - monsterP1.getHp())), 8);
        g.fillRect(554, 30, (float) -(2.55 * (100 - monsterP1.getHp())), 8);

        g.fillRect(726, 20, (float) (2.55 * (100 - monsterP2.getHp())), 8);
        g.fillRect(726, 30, (float) (2.55 * (100 - monsterP2.getHp())), 8);


//        g.setColor(Color.white);
//        g.drawString("DELTA = " + delta, 10, 30);
//        g.drawString("X = " + xMouse + " Y = " + yMouse, 10, 50);

    }

    @Override
    public void keyPressed(int key, char pressedKey) {
        // Key listener
        if (key == Input.KEY_1) {
//            pressed1 = true;
        }
        try {


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
        } catch (IndexOutOfBoundsException e) {
            endOfGame = true;
        }

        /*** Start of Skills ***/
        if (!(skill1P1 || skill2P1 || skillUltP1 || skill1P2 || skill2P2 || skillUltP2)) {     // disable casting skill while a skill is ongoing


            if (key == Input.KEY_X) {
                if (monsterP1.checkResources(monsterP1.getCostSkill1()) && timePassedSinceSkill1P1 == 0) {   //monsters has resources and skill cooldowned alrdy, go atk
                    skillCast(monsterP1.getDurationSkill1());       // call skillCast and pass duration of slow motion
                    skill1P1 = true;
                    monsterP1.skill1();
                    monsterP1.attack(monsterP2);

                }
            }

            if (key == Input.KEY_C) {
                if (monsterP1.checkResources(monsterP1.getCostSkill2()) && timePassedSinceSkill2P1 == 0) { //monsters has resources, go atk
                    skillCast(monsterP1.getDurationSkill2());       // call skillCast and pass duration of slow motion
                    skill2P1 = true;
                    monsterP1.skill2();

                    monsterP1.attack(monsterP2);
                }
            }

            if (key == Input.KEY_V) {
                if (monsterP1.checkResources(monsterP1.getCostSkillUlt()) && timePassedSinceSkillUltP1 == 0) { //monsters has resources, go atk
                    skillCast(monsterP1.getDurationSkillUlt());       // call skillCast and pass duration of slow motion
                    skillUltP1 = true;
                    monsterP1.skillUlt();
                    if (monsterP1 instanceof game.monsters.Monster5) {
                        monsterP1.attack(monsterP1);
                    } else {
                        monsterP1.attack(monsterP2);
                    }
                }
            }

            if (key == Input.KEY_B) {

                if (monsterP2.checkResources(monsterP2.getCostSkill1()) && timePassedSinceSkill1P2 == 0) {//monsters has resources, go atk
                    skillCast(monsterP2.getDurationSkill1());       // call skillCast and pass duration of slow motion
                    skill1P2 = true;
                    monsterP2.skill1();
                    monsterP2.attack(monsterP1);
                }
            }

            if (key == Input.KEY_N) {
                if (monsterP2.checkResources(monsterP2.getCostSkill2()) && timePassedSinceSkill2P2 == 0) { //monsters has resources, go atk
                    skillCast(monsterP2.getDurationSkill2());       // call skillCast and pass duration of slow motion
                    skill2P2 = true;
                    monsterP2.skill2();
                    monsterP2.attack(monsterP1);
                }
            }

            if (key == Input.KEY_M) {
                if (monsterP2.checkResources(monsterP2.getCostSkillUlt()) && timePassedSinceSkillUltP2 == 0) { //monsters has resources, go atk
                    skillCast(monsterP2.getDurationSkillUlt());       // call skillCast and pass duration of slow motion
                    skillUltP2 = true;
                    monsterP2.skillUlt();
                    if (monsterP2 instanceof game.monsters.Monster5) {
                        monsterP2.attack(monsterP2);
                    } else {
                        monsterP2.attack(monsterP1);
                    }
                }
            }
        }


        /*** End of Skills ***/


        // Pause
        // TODO pause game
        if (key == Input.KEY_ESCAPE)

        {
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


    public static void setMonsterP1(Monster monsterp1) {
        monsterP1 = monsterp1;
    }

    public static void setMonsterP2(Monster monsterp2) {
        monsterP2 = monsterp2;
    }

    public static void resetGameProperState() {
        notesP1 = new ArrayList<>();
        notesP2 = new ArrayList<>();

        skill1P1 = false;
        skill2P1 = false;
        skillUltP1 = false;
        skill1P2 = false;
        skill2P2 = false;
        skillUltP2 = false;

        speedNoteDrop = 4f;
        timePassed = 0;
        skillCast = false;
        slowDuration = 0;

        timePassedSinceSkill1P1 = 0;
        timePassedSinceSkill2P1 = 0;
        timePassedSinceSkillUltP1 = 0;

        timePassedSinceSkill1P2 = 0;
        timePassedSinceSkill2P2 = 0;
        timePassedSinceSkillUltP2 = 0;
    }
} // END OF CLASS
