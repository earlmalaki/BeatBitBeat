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

// TODO Finish monster implementation

package game;

import game.monsters.Monster;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.io.*;
import java.util.ArrayList;

public class GameProperState extends BasicGameState implements KeyListener {

    private Image imageBG;

    private static Monster monsterP1;
    private static Monster monsterP2;

    private ArrayList<Note> notesP1 = new ArrayList<>();
    private ArrayList<Note> notesP2 = new ArrayList<>();

    private Image[] imagesNotes;
    private Image[] imagesPressedHitbox;

    // Player 1 note x positions
    private float p1x1 = 36f - 13f;
    private float p1x2 = 74f - 13f;
    private float p1x3 = 113f - 13f;
    private float p1x4 = 150f - 13f;

    // Player 2 note x positions
    private float p2x1 = 1090f - 13f;
    private float p2x2 = 1128f - 13f;
    private float p2x3 = 1166f - 13f;
    private float p2x4 = 1203f - 13f;

    // Significant Y axis positions
    private float startingYPos = 0f;
    private float badYPos = 600f;
    private float goodYPos = 615f;
    private float perfectYPos = 630f;
    private float endingYPos = 645;    // miss

    private boolean badHitP1;
    private boolean badHitP2;
    private boolean goodHitP1;
    private boolean goodHitP2;
    private boolean perfectHitP1;
    private boolean perfectHitP2;
    private boolean missHitP1;
    private boolean missHitP2;

    private static Music gameMusic;
    private Coordinate coordPlayer1 = new Coordinate((displayWidth / 2) - 400, 100);
    private Coordinate coordPlayer2 = new Coordinate((displayWidth / 2) + 100, 100);

    private static Animation animationPlayer1;
    private static Animation animationPlayer2;

    private static final int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private static final int displayHeight = BeatBitBeatMain.getDisplayHeight();

    // Music position
    private float musicPosition = 00.00f;

    private float speedNoteDrop = 4f;
    private int timePassed = 0;     // in milliseconds
    private boolean slowCast = false;
    private int timeSlowEffect = 3000;  // 3000ms == 3s

    private static BufferedReader br;
    private boolean pressedEscape = false;

    private float pitchSlowMusic = 0.05f;

    private boolean pressedQ = false;
    private boolean pressedW = false;
    private boolean pressedE = false;
    private boolean pressedR = false;

    private boolean pressedU = false;
    private boolean pressedI = false;
    private boolean pressedO = false;
    private boolean pressedP = false;


    public int getID() {
        return BeatBitBeatMain.getGameProper();
    }

    public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {

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
        if (slowCast) {
            timePassed += delta;
            if (timePassed == timeSlowEffect) {
                slowCast = false;
                timePassed = 0;
                speedNoteDrop = 4f;
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

            // detect if note bars go past the hitbox
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
        animationPlayer2.update(delta);

        if (monsterP1.getHp() <= 0) {
            sbg.enterState(BeatBitBeatMain.getGameOver(), new FadeOutTransition(), new FadeInTransition());
        } else if (monsterP2.getHp() <= 0) {
            sbg.enterState(BeatBitBeatMain.getGameOver(), new FadeOutTransition(), new FadeInTransition());
        }

    }

    public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {

        imageBG.draw();

        // Game Screen borders
        g.drawLine(0, endingYPos, displayWidth, endingYPos);

        // Draw player character animations
        animationPlayer1.draw(coordPlayer1.getX(), coordPlayer1.getY());
        animationPlayer2.getCurrentFrame().getFlippedCopy(true, false).draw(coordPlayer2.getX(), coordPlayer2.getY());


        // render falling notes
        for (int i = 0; i < notesP1.size(); i++) {
            if (notesP1.get(i).getY() + 8 <= perfectYPos) {
                notesP1.get(i).getImage().draw(notesP1.get(i).getX(), notesP1.get(i).getY());
                notesP2.get(i).getImage().draw(notesP2.get(i).getX(), notesP2.get(i).getY());
            }
        }

        g.setColor(Color.white);
        g.drawString("Time", (displayWidth / 2) - 5, 5);
        String strMusicPosition = null;
        if (gameMusic.playing()) {
            strMusicPosition = String.valueOf(musicPosition);
            g.drawString(strMusicPosition, (displayWidth / 2) - (strMusicPosition.length()), 20);

        }

        g.drawString("X = " + xMouse + " Y = " + yMouse, 100, 130);
        g.drawString("Curr NoteBars : " + notesP1.size() + "  " + notesP2.size(), (displayWidth / 2) - 10, 50);

        // For testing purposes
        g.drawString("P1 Pressed Red: " + monsterP1.getResourceRed(), p1x4 + 150, displayHeight - 150);
        g.drawString("P1 Pressed Green: " + monsterP1.getResourceGreen(), p1x4 + 150, displayHeight - 140);
        g.drawString("P1 Pressed Blue: " + monsterP1.getResourceBlue(), p1x4 + 150, displayHeight - 130);
        g.drawString("P1 Pressed Yellow: " + monsterP1.getResourceYellow(), p1x4 + 150, displayHeight - 120);

        g.drawString("P2 Pressed Red: " + monsterP2.getResourceRed(), (displayWidth / 2) + 50, displayHeight - 150);
        g.drawString("P2 Pressed Green: " + monsterP2.getResourceGreen(), (displayWidth / 2) + 50, displayHeight - 140);
        g.drawString("P2 Pressed Blue: " + monsterP2.getResourceBlue(), (displayWidth / 2) + 50, displayHeight - 130);
        g.drawString("P2 Pressed Yellow: " + monsterP2.getResourceYellow(), (displayWidth / 2) + 50, displayHeight - 120);


        g.drawString("DELTA = " + delta, 100, 50);

        // Hitbox feedback
        // Draw glowing hitbox if corresponding key is pressed
        if (pressedQ) {
            imagesPressedHitbox[0].draw(p1x1 - 6, perfectYPos - 19);
        }
        if (pressedW) {
            imagesPressedHitbox[1].draw(p1x2 - 6, perfectYPos - 19);
        }
        if (pressedE) {
            imagesPressedHitbox[2].draw(p1x3 - 6, perfectYPos - 19);
        }
        if (pressedR) {
            imagesPressedHitbox[3].draw(p1x4 - 6, perfectYPos - 19);
        }

        if (pressedU) {
            imagesPressedHitbox[0].draw(p2x1 - 6, perfectYPos - 19);
        }
        if (pressedI) {
            imagesPressedHitbox[1].draw(p2x2 - 6, perfectYPos - 19);
        }
        if (pressedO) {
            imagesPressedHitbox[2].draw(p2x3 - 6, perfectYPos - 19);
        }
        if (pressedP) {
            imagesPressedHitbox[3].draw(p2x4 - 6, perfectYPos - 19);
        }
    }

    @Override
    public void keyPressed(int key, char pressedKey) {
        // Key listener

        if (key == Input.KEY_Q) {
            pressedQ = true;
            imagesPressedHitbox[0].draw(p1x1 - 19, perfectYPos - 22);
            // if note bar is near or within corresponding hitbox
            if (notesP1.get(0).getX() == p1x1 && badYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= goodYPos) {    // bad hit
                // no resource gain
                // display bad hit!
            } else if (notesP1.get(0).getX() == p1x1 && goodYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= perfectYPos) {    // good hit
                monsterP1.addResourceRed(1);
            } else if (notesP1.get(0).getX() == p1x1 && perfectYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= endingYPos) {    // perfect hit
                monsterP1.addResourceRed(2);
            }

        }
        if (key == Input.KEY_W) {
            pressedW = true;
            imagesPressedHitbox[1].draw(p1x2 - 19, perfectYPos - 22);
            if (notesP1.get(0).getX() == p1x2 && badYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= goodYPos) {    // bad hit
                // no resource gain
                // display bad hit!
            } else if (notesP1.get(0).getX() == p1x2 && goodYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= perfectYPos) {    // good hit
                monsterP1.addResourceGreen(1);
            } else if (notesP1.get(0).getX() == p1x2 && perfectYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= endingYPos) {    // perfect hit
                monsterP1.addResourceGreen(2);
            }

        }
        if (key == Input.KEY_E) {
            pressedE = true;
            imagesPressedHitbox[2].draw(p1x3 - 19, perfectYPos - 22);
            if (notesP1.get(0).getX() == p1x3 && badYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= goodYPos) {    // bad hit
                // no resource gain
                // display bad hit!
            } else if (notesP1.get(0).getX() == p1x3 && goodYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= perfectYPos) {    // good hit
                monsterP1.addResourceBlue(1);
            } else if (notesP1.get(0).getX() == p1x3 && perfectYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= endingYPos) {    // perfect hit
                monsterP1.addResourceBlue(2);
            }

        }
        if (key == Input.KEY_R) {
            pressedR = true;
            imagesPressedHitbox[3].draw(p1x4 - 19, perfectYPos - 22);
            if (notesP1.get(0).getX() == p1x4 && badYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= goodYPos) {    // bad hit
                // no resource gain
                // display bad hit!
            } else if (notesP1.get(0).getX() == p1x4 && goodYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= perfectYPos) {    // good hit
                monsterP1.addResourceYellow(1);
            } else if (notesP1.get(0).getX() == p1x4 && perfectYPos <= notesP1.get(0).getY() && notesP1.get(0).getY() <= endingYPos) {    // perfect hit
                monsterP1.addResourceYellow(2);
            }

        }

        if (key == Input.KEY_U) {
            pressedU = true;
            imagesPressedHitbox[0].draw(p2x1 - 19, perfectYPos - 22);
            if (notesP2.get(0).getX() == p2x1 && badYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= goodYPos) {    // bad hit
                // no resource gain
                // display bad hit!
            } else if (notesP2.get(0).getX() == p2x1 && goodYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= perfectYPos) {    // good hit
                monsterP2.addResourceRed(1);
            } else if (notesP2.get(0).getX() == p2x1 && perfectYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= endingYPos) {    // perfect hit
                monsterP2.addResourceRed(2);
            }

        }
        if (key == Input.KEY_I) {
            pressedI = true;
            imagesPressedHitbox[1].draw(p2x2 - 19, perfectYPos - 22);
            if (notesP2.get(0).getX() == p2x2 && badYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= goodYPos) {    // bad hit
                // no resource gain
                // display bad hit!
            } else if (notesP2.get(0).getX() == p2x2 && goodYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= perfectYPos) {    // good hit
                monsterP2.addResourceGreen(1);
            } else if (notesP2.get(0).getX() == p2x2 && perfectYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= endingYPos) {    // perfect hit
                monsterP2.addResourceGreen(2);
            }

        }
        if (key == Input.KEY_O) {
            pressedO = true;
            imagesPressedHitbox[2].draw(p2x3 - 19, perfectYPos - 22);
            if (notesP2.get(0).getX() == p2x3 && badYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= goodYPos) {    // bad hit
                // no resource gain
                // display bad hit!
            } else if (notesP2.get(0).getX() == p2x3 && goodYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= perfectYPos) {    // good hit
                monsterP2.addResourceBlue(1);
            } else if (notesP2.get(0).getX() == p2x3 && perfectYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= endingYPos) {    // perfect hit
                monsterP2.addResourceBlue(2);
            }

        }
        if (key == Input.KEY_P) {
            pressedP = true;
            imagesPressedHitbox[3].draw(p2x4 - 19, perfectYPos - 22);
            if (notesP2.get(0).getX() == p2x4 && badYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= goodYPos) {    // bad hit
                // no resource gain
                // display bad hit!
            } else if (notesP2.get(0).getX() == p2x4 && goodYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= perfectYPos) {    // good hit
                monsterP2.addResourceYellow(1);
            } else if (notesP2.get(0).getX() == p2x4 && perfectYPos <= notesP2.get(0).getY() && notesP2.get(0).getY() <= endingYPos) {    // perfect hit
                monsterP2.addResourceYellow(2);
            }

        }



        // FIXME
        // negative values
        // Cooldown
        // Slow-mo
        // Resource cost
        /*** Start of Skills ***/
        if (key == Input.KEY_Z) {

            if (monsterP1.getResourceRed() >= 3 && monsterP1.getResourceGreen() >= 3) { //monsters has resources, go atk
                monsterP1.setDamage(5);
                monsterP1.setResourceRed(monsterP1.getResourceRed() - 3);
                monsterP1.setResourceYellow(monsterP1.getResourceYellow() - 3);
                monsterP1.attack(monsterP2);
            }
        }

        if (key == Input.KEY_X) {
            if (monsterP1.getResourceBlue() >= 7 && monsterP1.getResourceYellow() >= 7) { //monsters has resources, go atk
                monsterP1.setDamage(12);
                monsterP1.setResourceRed(monsterP1.getResourceRed() - 1);
//                monsterP1.setResourceYellow(monsterP1.getResourceYellow() - 1);
                monsterP1.attack(monsterP2);
            }
        }

        if (key == Input.KEY_C) {
            if (monsterP1.getResourceRed() >= 12 && monsterP1.getResourceYellow() >= 12 && monsterP1.getResourceBlue() >= 12 && monsterP1.getResourceGreen() >= 12) { //monsters has resources, go atk
                monsterP1.setDamage(55);
                monsterP1.setResourceRed(monsterP1.getResourceRed() - 12);
                monsterP1.setResourceYellow(monsterP1.getResourceYellow() - 12);
                monsterP1.setResourceGreen(monsterP1.getResourceGreen() - 12);
                monsterP1.setResourceBlue(monsterP1.getResourceBlue() - 12);
                monsterP1.attack(monsterP2);
            }
        }

        if (key == Input.KEY_COMMA) {

            if (monsterP2.getResourceRed() >= 3 && monsterP2.getResourceGreen() >= 3) { //monsters has resources, go atk
                monsterP2.setDamage(5);
                monsterP2.setResourceRed(monsterP1.getResourceRed() - 3);
                monsterP2.setResourceGreen(monsterP2.getResourceGreen() - 3);
                monsterP2.attack(monsterP1);
            }
        }

        if (key == Input.KEY_PERIOD) {
            if (monsterP2.getResourceBlue() >= 7 && monsterP2.getResourceYellow() >= 7) { //monsters has resources, go atk
                monsterP2.setDamage(12);
                monsterP2.setResourceYellow(monsterP2.getResourceYellow() - 7);
                monsterP2.setResourceBlue(monsterP2.getResourceBlue() - 7);
                monsterP2.attack(monsterP1);
            }
        }

        if (key == Input.KEY_BACKSLASH) {
            if (monsterP2.getResourceRed() >= 12 && monsterP2.getResourceYellow() >= 12 && monsterP2.getResourceBlue() >= 12 && monsterP2.getResourceGreen() >= 12) { //monsters has resources, go atk
                monsterP2.setDamage(55);
                monsterP2.setResourceRed(monsterP2.getResourceRed() - 12);
                monsterP2.setResourceYellow(monsterP2.getResourceYellow() - 12);
                monsterP2.setResourceGreen(monsterP2.getResourceGreen() - 12);
                monsterP2.setResourceBlue(monsterP2.getResourceBlue() - 12);
                monsterP2.attack(monsterP1);
            }
        }

        if (key == Input.KEY_M) {
            slowCast = true;
            gameMusic.play(pitchSlowMusic, 1f);
            gameMusic.setPosition(musicPosition);
            // TODO adjust pitch. match pitch loss and map read speed loss
        }
        /*** End of Skills ***/


        // Pause
        // TODO pause game
        if (key == Input.KEY_ESCAPE) {
            pressedEscape = true;
        }
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
                    notesP1.add(new Note(imagesNotes[0], p1x1, startingYPos));
                    notesP2.add(new Note(imagesNotes[0], p2x1, startingYPos));

                } else if (line.equals("0100")) {
                    notesP1.add(new Note(imagesNotes[1], p1x2, startingYPos));
                    notesP2.add(new Note(imagesNotes[1], p2x2, startingYPos));

                } else if (line.equals("0010")) {
                    notesP1.add(new Note(imagesNotes[2], p1x3, startingYPos));
                    notesP2.add(new Note(imagesNotes[2], p2x3, startingYPos));

                } else if (line.equals("0001")) {
                    notesP1.add(new Note(imagesNotes[3], p1x4, startingYPos));
                    notesP2.add(new Note(imagesNotes[3], p2x4, startingYPos));

                }


            }
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (SlickException e) {
//            e.printStackTrace();
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
