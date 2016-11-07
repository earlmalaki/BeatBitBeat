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
 * Note:
 * - Prioritize MVP first.
 * <p>
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
 * Note:
 * - Prioritize MVP first.
 */

/**
 * Done:
 * - Key Listener for receiving bars (ASDF, HJKL)
 * - Image handler for background
 * - Music player
 * - Randomized dropping of notes
 *
 * To Do:
 * - Put wallpaper file
 * - Put receiving bar, vertical bar, and note graphic files
 * - finalize positioning of elements after putting in final graphics
 * - Music beat map making and reading
 * - Dropping notes according to beat map
 * - Better receiving bar accuracy
 * - GAME PART. Monster objects, hp, skills, resources, etc.
 * - End of game state
 *
 * Note:
 * - Prioritize MVP first.
 *
 */

// TODO Finish monster implementation

package game;

import game.monsters.Monster;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameProperState extends BasicGameState implements KeyListener {

    private static Monster monsterP1;
    private static Monster monsterP2;

    private ArrayList<Rectangle> p1NoteBars;
    private ArrayList<Rectangle> p2NoteBars;

    private ArrayList<Integer> p1PressedNotes;
    private ArrayList<Integer> p2PressedNotes;

    private ArrayList<Rectangle> p1ReceiveBars;
    private ArrayList<Rectangle> p2ReceiveBars;

    private ArrayList<Rectangle> lineBars;

    private static Audio gameMusic;
    private Coordinate coordPlayer1;
    private Coordinate coordPlayer2;
    private static Animation animationPlayer1;
    private static Animation animationPlayer2;


    private Random random;

    private static final int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private static final int displayHeight = BeatBitBeatMain.getDisplayHeight();

    private int startingYPos = 50;
    private int endingYPos = 650;

    // Player 1 note x positions
    private int p1x1 = (int) (displayWidth * (0.06));
    private int p1x2 = (int) (p1x1 + (displayWidth * (0.05)));
    private int p1x3 = (int) (p1x2 + (displayWidth * (0.05)));
    private int p1x4 = (int) (p1x3 + (displayWidth * (0.05)));

    // Player 2 note x positions
    private int p2x4 = (int) (displayWidth - (displayWidth * (0.06)));
    private int p2x3 = (int) (p2x4 - (displayWidth * (0.05)));
    private int p2x2 = (int) (p2x3 - (displayWidth * (0.05)));
    private int p2x1 = (int) (p2x2 - (displayWidth * (0.05)));


    // Horizontal note receiving bar final dimensions
    private static final int recBarWidth = 20;
    private static final int recBarHeight = 10;

    // Vertical note bar final dimensions
    private static final int lineBarWidth = 5;
    private static final int lineBarHeight = 600;

    // Falling note dimensions
    private static final int noteBarWidth = 20;
    private static int noteBarHeight = 10;

    // Music position
    private double musicPosition = 00.00;

    private int timePassed;

    BufferedReader br;

    public int getID() {
        return BeatBitBeatMain.getGameProper();
    }

    public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {

        coordPlayer1 = new Coordinate((displayWidth / 2) - 300, 100);
        coordPlayer2 = new Coordinate((displayWidth / 2) + 100, 100);

        p1NoteBars = new ArrayList<>();
        p2NoteBars = new ArrayList<>();

        p1PressedNotes = new ArrayList<>();
        p2PressedNotes = new ArrayList<>();

        p1ReceiveBars = new ArrayList<>();
        p2ReceiveBars = new ArrayList<>();

        lineBars = new ArrayList<>();

        random = new Random();

        timePassed = 0;


        // Add horizontal receive bars
        p1ReceiveBars.add(new Rectangle(p1x1 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));
        p1ReceiveBars.add(new Rectangle(p1x2 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));
        p1ReceiveBars.add(new Rectangle(p1x3 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));
        p1ReceiveBars.add(new Rectangle(p1x4 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));

        p2ReceiveBars.add(new Rectangle(p2x1 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));
        p2ReceiveBars.add(new Rectangle(p2x2 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));
        p2ReceiveBars.add(new Rectangle(p2x3 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));
        p2ReceiveBars.add(new Rectangle(p2x4 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));

        // Add integers to list of pressed note for p1
        // 0 for red, 1 for blue, 2 for yellow, 4 for green
        p1PressedNotes.add(0);
        p1PressedNotes.add(0);
        p1PressedNotes.add(0);
        p1PressedNotes.add(0);

        // Add integers to list of pressed note for p2
        // 0 for red, 1 for blue, 2 for yellow, 4 for green
        p2PressedNotes.add(0);
        p2PressedNotes.add(0);
        p2PressedNotes.add(0);
        p2PressedNotes.add(0);

        // Add vertical line bars
        lineBars.add(new Rectangle(p1x1 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p1x2 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p1x3 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p1x4 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p2x1 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p2x2 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p2x3 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p2x4 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));

        try {
            br = new BufferedReader(new FileReader("beatmap.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {

//        // generate noteBars at random x
//        timePassed += delta;
//        if(timePassed > 400){
//            timePassed = 0;
//
//            if (genRand() == 1){
//                p1NoteBars.add(new Rectangle(p1x1 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
//                p2NoteBars.add(new Rectangle(p2x1 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
//            }
//            else if (genRand() == 2) {
//                p1NoteBars.add(new Rectangle(p1x2 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
//                p2NoteBars.add(new Rectangle(p2x2 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
//            }
//            else if (genRand() == 3) {
//                p1NoteBars.add(new Rectangle(p1x3 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
//                p2NoteBars.add(new Rectangle(p2x3 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
//            }
//            else if (genRand() == 4) {
//                p1NoteBars.add(new Rectangle(p1x4 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
//                p2NoteBars.add(new Rectangle(p2x4 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
//            }
//        }
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (line.equals("1000")) {
            p1NoteBars.add(new Rectangle(p1x1 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
            p2NoteBars.add(new Rectangle(p2x1 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
        } else if (line.equals("0100")) {
            p1NoteBars.add(new Rectangle(p1x2 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
            p2NoteBars.add(new Rectangle(p2x2 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
        } else if (line.equals("0010")) {
            p1NoteBars.add(new Rectangle(p1x3 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
            p2NoteBars.add(new Rectangle(p2x3 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
        } else if (line.equals("0001")) {
            p1NoteBars.add(new Rectangle(p1x4 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
            p2NoteBars.add(new Rectangle(p2x4 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
        }


        // Control the falling of noteBars bars
        // P1
        for (Rectangle rect : p1NoteBars) {
            rect.setCenterY(rect.getCenterY() + (delta / 5));
        }

        // Control the falling of noteBars bars
        // P2
        for (Rectangle rect : p2NoteBars) {
            rect.setCenterY(rect.getCenterY() + (delta / 5));
        }

        // detect if notebars go past the receive bars
        // P1
        for (int i = 0; i < p1NoteBars.size() - 1; i++) {
            Rectangle rect = p1NoteBars.get(i);

            if (rect.getCenterY() > endingYPos) {
                p1NoteBars.remove(i);

            }
        }

        // detect if notebars go past the receive bars
        // P1
        for (int i = 0; i < p2NoteBars.size() - 1; i++) {
            Rectangle rect = p2NoteBars.get(i);

            if (rect.getCenterY() > endingYPos) {
                p2NoteBars.remove(i);

            }
        }

        musicPosition = gameMusic.getPosition();

    }

    public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {

        // Game Screen borders
        // TODO this is to be replaced by on-wallpaper border lines
        g.drawLine((displayWidth / 3) - 80, 0, (displayWidth / 3) - 80, displayHeight);  // left vertical division line
        g.drawLine(displayWidth - (displayWidth / 3) + 80, 0, displayWidth - (displayWidth / 3) + 80, displayHeight);  // right vertical division line
        g.drawLine((displayWidth / 2), displayHeight / 2, (displayWidth / 2), displayHeight);  // center vertical division line
        g.drawLine((displayWidth / 3) - 80, displayHeight / 2, displayWidth - (displayWidth / 3) + 80, displayHeight / 2);  // center horizontal division line


        // Draw player character animations
        animationPlayer1.draw(coordPlayer1.getX(), coordPlayer1.getY());
        animationPlayer2.draw(coordPlayer2.getX(), coordPlayer2.getY());


        // Draw vertical line bars
        g.setColor(Color.lightGray);
        for (Rectangle r : lineBars) {
            g.fill(r);
        }

        // Draw horizontal receive bars for p1
        int index = 0;
        for (Rectangle rect : p1ReceiveBars) {
            if (index == 0) {
                g.setColor(Color.red);
            } else if (index == 1) {
                g.setColor(Color.blue);
            } else if (index == 2) {
                g.setColor(Color.yellow);
            } else if (index == 3) {
                g.setColor(Color.green);
            }
            g.fill(rect);
            index++;
        }

        index = 0;
        // Draw horizontal receive bars for p2
        for (Rectangle rect : p2ReceiveBars) {
            if (index == 0) {
                g.setColor(Color.red);
            } else if (index == 1) {
                g.setColor(Color.blue);
            } else if (index == 2) {
                g.setColor(Color.yellow);
            } else if (index == 3) {
                g.setColor(Color.green);
            }
            g.fill(rect);
            index++;
        }

        // Draw falling note bars for p1
        for (Rectangle rect : p1NoteBars) {
            if (rect.getCenterX() == p1x1) {
                g.setColor(Color.red);
            } else if (rect.getCenterX() == p1x2) {
                g.setColor(Color.blue);
            } else if (rect.getCenterX() == p1x3) {
                g.setColor(Color.yellow);
            } else if (rect.getCenterX() == p1x4) {
                g.setColor(Color.green);
            }
            g.fill(rect);
        }
        // Draw falling note bars for p1
        for (Rectangle rect : p2NoteBars) {
            if (rect.getCenterX() == p2x1) {
                g.setColor(Color.red);
            } else if (rect.getCenterX() == p2x2) {
                g.setColor(Color.blue);
            } else if (rect.getCenterX() == p2x3) {
                g.setColor(Color.yellow);
            } else if (rect.getCenterX() == p2x4) {
                g.setColor(Color.green);
            }
            g.fill(rect);
        }


        g.setColor(Color.white);
        String strMusicPosition = String.valueOf(musicPosition);
        // TODO display only until 2 decimal places of music pos
        g.drawString(strMusicPosition, (displayWidth / 2) - (strMusicPosition.length() / 2), 20);
        g.drawString("Time", (displayWidth / 2) - 5, 5);
        g.drawString("Curr NoteBars : " + p1NoteBars.size() + p2NoteBars.size(), displayWidth / 2, 50);

        // For testing purposes
        g.drawString("P1 Pressed Red: " + p1PressedNotes.get(0), 20, 100);
        g.drawString("P1 Pressed Blue: " + p1PressedNotes.get(1), 20, 130);
        g.drawString("P1 Pressed Yellow: " + p1PressedNotes.get(2), 20, 160);
        g.drawString("P1 Pressed Green: " + p1PressedNotes.get(3), 20, 190);

        g.drawString("P2 Pressed Red: " + p2PressedNotes.get(0), 900, 100);
        g.drawString("P2 Pressed Blue: " + p2PressedNotes.get(1), 900, 130);
        g.drawString("P2 Pressed Yellow: " + p2PressedNotes.get(2), 900, 160);
        g.drawString("P2 Pressed Green: " + p2PressedNotes.get(3), 900, 190);
    }

    @Override
    public void keyPressed(int key, char pressedKey) {
        // Key listener
        if (key == Input.KEY_A) {
            // if note bar is near or within corresponding receive bar
            if (p1ReceiveBars.get(0).getCenterY() - 30 <= p1NoteBars.get(0).getCenterY() &&
                    p1ReceiveBars.get(0).getCenterX() == p1NoteBars.get(0).getCenterX()) {
                p1PressedNotes.set(0, p1PressedNotes.get(0) + 1);

                // increment resource
                monsterP1.addResourceBlue();
            }

        } else if (key == Input.KEY_S) {
            if (p1ReceiveBars.get(1).getCenterY() - 30 <= p1NoteBars.get(0).getCenterY() &&
                    p1ReceiveBars.get(1).getCenterX() == p1NoteBars.get(0).getCenterX()) {
                p1PressedNotes.set(1, p1PressedNotes.get(1) + 1);

                monsterP1.addResourceRed();
            }

        } else if (key == Input.KEY_D) {
            if (p1ReceiveBars.get(2).getCenterY() - 30 <= p1NoteBars.get(0).getCenterY() &&
                    p1ReceiveBars.get(2).getCenterX() == p1NoteBars.get(0).getCenterX()) {
                p1PressedNotes.set(2, p1PressedNotes.get(2) + 1);

                monsterP1.addResourceYellow();
            }

        } else if (key == Input.KEY_F) {
            if (p1ReceiveBars.get(3).getCenterY() - 30 <= p1NoteBars.get(0).getCenterY() &&
                    p1ReceiveBars.get(3).getCenterX() == p1NoteBars.get(0).getCenterX()) {
                p1PressedNotes.set(3, p1PressedNotes.get(3) + 1);

                monsterP1.addResourceGreen();
            }


        }

        if (key == Input.KEY_H) {
            if (p2ReceiveBars.get(0).getCenterY() - 30 <= p2NoteBars.get(0).getCenterY() &&
                    p2ReceiveBars.get(0).getCenterX() == p2NoteBars.get(0).getCenterX()) {
                p2PressedNotes.set(0, p2PressedNotes.get(0) + 1);

                monsterP2.addResourceBlue();
            }
        } else if (key == Input.KEY_J) {
            if (p2ReceiveBars.get(1).getCenterY() - 30 <= p2NoteBars.get(0).getCenterY() &&
                    p2ReceiveBars.get(1).getCenterX() == p2NoteBars.get(0).getCenterX()) {
                p2PressedNotes.set(1, p2PressedNotes.get(1) + 1);

                monsterP2.addResourceRed();
            }
        } else if (key == Input.KEY_K) {
            if (p2ReceiveBars.get(2).getCenterY() - 30 <= p2NoteBars.get(0).getCenterY() &&
                    p2ReceiveBars.get(2).getCenterX() == p2NoteBars.get(0).getCenterX()) {
                p2PressedNotes.set(2, p2PressedNotes.get(2) + 1);

                monsterP2.addResourceYellow();
            }

        } else if (key == Input.KEY_L) {
            if (p2ReceiveBars.get(3).getCenterY() - 30 <= p2NoteBars.get(0).getCenterY() &&
                    p2ReceiveBars.get(3).getCenterX() == p2NoteBars.get(0).getCenterX()) {
                p2PressedNotes.set(3, p2PressedNotes.get(3) + 1);

                monsterP2.addResourceGreen();
            }

        }
    }

    @Override
    public void keyReleased(int key, char pressedKey) {

    }

    public int genRand() {
        return random.nextInt(4) + 1;
    }

    public static void setGameMusic(Audio music) {
        gameMusic = music;
    }

    public static void startMusic() {
        gameMusic.playAsMusic(1.0f, 1.0f, false);
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
