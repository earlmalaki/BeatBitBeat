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
 * - Better receiving bar accuracy
 * - GAME PART. Monster objects, hp, skills, resources, etc.
 * - Combo
 * - End of game state
 * Low prio:
 * - Cooldown
 * - Particle effects
 * <p>
 * game.Note:
 */

// TODO Finish monster implementation

package game;

import game.monsters.Monster;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.io.*;
import java.util.ArrayList;

public class GameProperState extends BasicGameState implements KeyListener {

    private static Monster monsterP1;
    private static Monster monsterP2;

//    private ArrayList<Note> notesP1;
//    private ArrayList<Note> notesP2;
//    private Note[] notesForP1;
//    private Note[] notesForP2;

//    private Integer[] pressedNotesP1;
//    private Integer[] pressedNotesP2;

    private ArrayList<Rectangle> notesP1;
    private ArrayList<Rectangle> notesP2;

    // Player 1 note x positions
    private int p1x1 = (int) (displayWidth * (0.04));
    private int p1x2 = (int) (p1x1 + (displayWidth * (0.03)));
    private int p1x3 = (int) (p1x2 + (displayWidth * (0.03)));
    private int p1x4 = (int) (p1x3 + (displayWidth * (0.03)));

    // Player 2 note x positions
    private int p2x4 = (int) (displayWidth - (displayWidth * (0.04)));
    private int p2x3 = (int) (p2x4 - (displayWidth * (0.03)));
    private int p2x2 = (int) (p2x3 - (displayWidth * (0.03)));
    private int p2x1 = (int) (p2x2 - (displayWidth * (0.03)));

    private ArrayList<Integer> pressedNotesP1;
    private ArrayList<Integer> pressedNotesP2;

    private static Music gameMusic;
    private Coordinate coordPlayer1;
    private Coordinate coordPlayer2;
    private static Animation animationPlayer1;
    private static Animation animationPlayer2;


    private static final int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private static final int displayHeight = BeatBitBeatMain.getDisplayHeight();

    private int startingYPos = 100;
    private int endingYPos = 600;

    // Music position
    private double musicPosition = 00.00;


    private ArrayList<Rectangle> receiveBarsP1;
    private ArrayList<Rectangle> receiveBarsP2;
    private ArrayList<Rectangle> lineBars;

    // Horizontal note receiving bar final dimensions
    private static final int recBarWidth = 35;
    private static final int recBarHeight = 10;

    // Vertical note bar final dimensions
    private static final int lineBarWidth = 2;
    private static final int lineBarHeight = 600;

    // Falling note dimensions
    private static final int noteBarWidth = 35;
    private static int noteBarHeight = 10;

    private static BufferedReader br;

    private boolean pressedEscape;

    public int getID() {
        return BeatBitBeatMain.getGameProper();
    }

    public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {

        pressedEscape = false;

        coordPlayer1 = new Coordinate((displayWidth / 2) - 300, 100);
        coordPlayer2 = new Coordinate((displayWidth / 2) + 100, 100);

//        notesForP1 = new Note[] {
//                new Note( new Image("Assets/Note Red.png"), new Coordinate(p1x1, startingYPos)),
//                new Note( new Image("Assets/Note Yellow.png"), new Coordinate(p1x2, startingYPos)),
//                new Note( new Image("Assets/Note Green.png"), new Coordinate(p1x3, startingYPos)),
//                new Note( new Image("Assets/Note Blue.png"), new Coordinate(p1x4, startingYPos)),
//        };
//        notesForP2 = new Note[] {
//                new Note( new Image("Assets/Note Red.png"), new Coordinate(p2x1, startingYPos)),
//                new Note( new Image("Assets/Note Yellow.png"), new Coordinate(p2x2, startingYPos)),
//                new Note( new Image("Assets/Note Green.png"), new Coordinate(p2x3, startingYPos)),
//                new Note( new Image("Assets/Note Blue.png"), new Coordinate(p2x4, startingYPos)),
//        };

        notesP1 = new ArrayList<>();
        notesP2 = new ArrayList<>();

//        coordsNotesP1 = new Coordinate[] {
//                new Coordinate( (int)(displayWidth * (0.06)), startingYPos),
//                new Coordinate( (int)((displayWidth * (0.06)) + (displayWidth * (0.05))), startingYPos),
//                new Coordinate( (int)((displayWidth * (0.06)) + (displayWidth * (0.05)) + (displayWidth * (0.05))), startingYPos),
//                new Coordinate( (int)((displayWidth * (0.06)) + (displayWidth * (0.05)) + (displayWidth * (0.05)) + (displayWidth * (0.05))), startingYPos),
//        };
//
//        coordsNotesP2 = new Coordinate[] {
//                new Coordinate( (int)(displayWidth - (displayWidth * (0.06)) - (displayWidth * (0.05)) - (displayWidth * (0.05)) - (displayWidth * (0.05))), startingYPos),
//                new Coordinate( (int)(displayWidth - (displayWidth * (0.06)) - (displayWidth * (0.05)) - (displayWidth * (0.05))), startingYPos),
//                new Coordinate( (int)(displayWidth - (displayWidth * (0.06)) - (displayWidth * (0.05))), startingYPos),
//                new Coordinate( (int)(displayWidth - (displayWidth * (0.06))), startingYPos),
//        };
//
//
//        pressedNotesP1 = new int[] {0,0,0,0};
//        pressedNotesP2 = new int[] {0,0,0,0};

        pressedNotesP1 = new ArrayList<>();
        pressedNotesP2 = new ArrayList<>();


        receiveBarsP1 = new ArrayList<>();
        receiveBarsP2 = new ArrayList<>();
        lineBars = new ArrayList<>();

        // Add horizontal receive bars
        receiveBarsP1.add(new Rectangle(p1x1 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));
        receiveBarsP1.add(new Rectangle(p1x2 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));
        receiveBarsP1.add(new Rectangle(p1x3 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));
        receiveBarsP1.add(new Rectangle(p1x4 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));

        receiveBarsP2.add(new Rectangle(p2x1 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));
        receiveBarsP2.add(new Rectangle(p2x2 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));
        receiveBarsP2.add(new Rectangle(p2x3 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));
        receiveBarsP2.add(new Rectangle(p2x4 - recBarWidth / 2, endingYPos, recBarWidth, recBarHeight));

        // Add integers to list of pressed note for p1
        // 0 for red, 1 for blue, 2 for yellow, 4 for green
        pressedNotesP1.add(0);
        pressedNotesP1.add(0);
        pressedNotesP1.add(0);
        pressedNotesP1.add(0);

        // Add integers to list of pressed note for p2
        // 0 for red, 1 for blue, 2 for yellow, 4 for green
        pressedNotesP2.add(0);
        pressedNotesP2.add(0);
        pressedNotesP2.add(0);
        pressedNotesP2.add(0);

        // Add vertical line bars
        lineBars.add(new Rectangle(p1x1 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p1x2 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p1x3 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p1x4 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p2x1 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p2x2 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p2x3 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p2x4 - lineBarWidth / 2, startingYPos, lineBarWidth, lineBarHeight));


    }


    int delta;  // for printing. temporary

    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
        this.delta = delta;

        if (monsterP1.getHp() <= 0) {
            sbg.enterState(BeatBitBeatMain.getGameOver(), new FadeOutTransition(), new FadeInTransition());
        } else if (monsterP2.getHp() <= 0) {
            sbg.enterState(BeatBitBeatMain.getGameOver(), new FadeOutTransition(), new FadeInTransition());

        }
        try {
            String line = br.readLine();

            if (line != null) {
                if (line.equals("1000")) {
//                    notesP1.add(notesForP1[0]);
//                    notesP2.add(notesForP2[0]);

                    notesP1.add(new Rectangle(p1x1 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
                    notesP2.add(new Rectangle(p2x1 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));

                } else if (line.equals("0100")) {
//                    notesP1.add(notesForP1[1]);
//                    notesP2.add(notesForP2[1]);

                    notesP1.add(new Rectangle(p1x2 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
                    notesP2.add(new Rectangle(p2x2 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
                } else if (line.equals("0010")) {
//                    notesP1.add(notesForP1[2]);
//                    notesP2.add(notesForP2[2]);

                    notesP1.add(new Rectangle(p1x3 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
                    notesP2.add(new Rectangle(p2x3 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
                } else if (line.equals("0001")) {
//                    notesP1.add(notesForP1[3]);
//                    notesP2.add(notesForP2[3]);

                    notesP1.add(new Rectangle(p1x4 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
                    notesP2.add(new Rectangle(p2x4 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }


//        for (Note note: notesP1) {
//            if (note.getCoord.getY() > endingYPos) {
//
//                if (!gameMusic.playing()) {
//                    gameMusic.play();
//                }
//
//                note.remove();
//            }
//        }


        // detect if notebars go past the receive bars
        // P1
        for (int i = 0; i < notesP1.size() - 1; i++) {
            Rectangle rect = notesP1.get(i);

            if (rect.getCenterY() > endingYPos) {

                // TODO Make better solution.
                if (!gameMusic.playing()) {
                    gameMusic.play();
                }

                notesP1.remove(i);
            }
        }

        // detect if notebars go past the receive bars
        // P1
        for (int i = 0; i < notesP2.size() - 1; i++) {
            Rectangle rect = notesP2.get(i);

            if (rect.getCenterY() > endingYPos) {
                notesP2.remove(i);

            }
        }


//        for (Note note : notesP1) {
//            note.setY(note.getY() + (delta / 4));
//        }
//
//        for (Note note : notesP2) {
//            note.setY(note.getY() + (delta / 4));
//        }

        // Control the falling of noteBars bars
        // P1
        for (Rectangle rect : notesP1) {
            rect.setCenterY(rect.getCenterY() + (delta / 4));
        }

        // Control the falling of noteBars bars
        // P2
        for (Rectangle rect : notesP2) {
            rect.setCenterY(rect.getCenterY() + (delta / 4));
        }

        // detect if notebars go past the receive bars
        // P1
        for (int i = 0; i < notesP1.size() - 1; i++) {
            Rectangle rect = notesP1.get(i);

            if (rect.getCenterY() > endingYPos) {


                // TODO Make better solution.
                if (!gameMusic.playing()) {
                    gameMusic.play();
                }

                notesP1.remove(i);
            }
        }

        // detect if notebars go past the receive bars
        // P1
        for (int i = 0; i < notesP2.size() - 1; i++) {
            Rectangle rect = notesP2.get(i);

            if (rect.getCenterY() > endingYPos) {
                notesP2.remove(i);

            }
        }

        musicPosition = gameMusic.getPosition();

    }

    public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {

        // Game Screen borders
        // TODO this is to be replaced by on-wallpaper border lines
        g.drawLine((displayWidth / 3) - (int) (displayWidth * 0.16), 0, (displayWidth / 3) - (int) (displayWidth * 0.16), displayHeight);  // left vertical division line
        g.drawLine(displayWidth - (displayWidth / 3) + (int) (displayWidth * 0.16), 0, displayWidth - (displayWidth / 3) + (int) (displayWidth * 0.16), displayHeight);  // right vertical division line
        g.drawLine((displayWidth / 2), (displayHeight / 2) + 50, (displayWidth / 2), displayHeight);  // center vertical division line
        g.drawLine((displayWidth / 3) - (int) (displayWidth * 0.16), (displayHeight / 2) + 50, displayWidth - (displayWidth / 3) + (int) (displayWidth * 0.16), (displayHeight / 2) + 50);  // center horizontal division line


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
        for (Rectangle rect : receiveBarsP1) {
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
        for (Rectangle rect : receiveBarsP2) {
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

//        for (Note note : notesP1) {
//            note.draw(note.getX(), note.getY());
//        }

//        for (Note note : notesP2) {
//            note.draw(note.getX(), note.getY());
//        }


        // Draw falling note bars for p1
        for (Rectangle rect : notesP1) {
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
        for (Rectangle rect : notesP2) {
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
        g.drawString("Curr NoteBars : " + notesP1.size() + notesP2.size(), displayWidth / 2, 50);

        // For testing purposes
        /*

        g.drawString("P1 Red: " + pressedNotesP1.get(0), 250, 450);
        g.drawString("P1 Yellow: " + pressedNotesP1.get(2), 250, 480);
        g.drawString("P1 Green: " + pressedNotesP1.get(3), 250, 510);
        g.drawString("P1 Blue: " + pressedNotesP1.get(1), 250, 540);
        g.drawString("P1 Health: " + monsterP1.getHp(), 250, 50);

        g.drawString("P2 Red: " + pressedNotesP2.get(0), 700, 450);
        g.drawString("P2 Yellow: " + pressedNotesP2.get(2), 700, 480);
        g.drawString("P2 Green: " + pressedNotesP2.get(3), 700, 510);
        g.drawString("P2 Blue: " + pressedNotesP2.get(1), 700, 540);
        g.drawString("P2 Health: " + monsterP2.getHp(), 900, 50);
        */
        g.drawString("P1 Red: " + monsterP1.getResourceRed(), 250, 450);
        g.drawString("P1 Yellow: " + monsterP1.getResourceYellow(), 250, 480);
        g.drawString("P1 Green: " + monsterP1.getResourceGreen(), 250, 510);
        g.drawString("P1 Blue: " + monsterP1.getResourceBlue(), 250, 540);
        g.drawString("P1 Health: " + monsterP1.getHp(), 250, 50);

        g.drawString("P2 Red: " + monsterP2.getResourceRed(), 700, 450);
        g.drawString("P2 Yellow: " + monsterP2.getResourceYellow(), 700, 480);
        g.drawString("P2 Green: " + monsterP2.getResourceGreen(), 700, 510);
        g.drawString("P2 Blue: " + monsterP2.getResourceBlue(), 700, 540);
        g.drawString("P2 Health: " + monsterP2.getHp(), 900, 50);

        g.drawString("DELTA = " + delta, 100, 50);
    }

    @Override
    public void keyPressed(int key, char pressedKey) {
        // Key listener
        if (key == Input.KEY_Q) {

//            if (endingYPos = notesP1.get(0).getY() ) {
//                pressedNotesP1.set(0, pressedNotesP1.get(0) + 1);
//
//                monsterP1.addResourceRed();
//            }

            // if note bar is near or within corresponding receive bar
            if (receiveBarsP1.get(0).getCenterY() - 50 <= notesP1.get(0).getCenterY() &&
                    receiveBarsP1.get(0).getCenterX() == notesP1.get(0).getCenterX()) {
                pressedNotesP1.set(0, pressedNotesP1.get(0) + 1);

                // increment resource
                monsterP1.addResourceRed();
            }

        }
        if (key == Input.KEY_W) {
//            if (endingYPos = notesP1.get(1).getY() ) {
//                pressedNotesP1.set(1, pressedNotesP1.get(1) + 1);
//
//                monsterP1.addResourceYellow();
//            }


            if (receiveBarsP1.get(1).getCenterY() - 50 <= notesP1.get(0).getCenterY() &&
                    receiveBarsP1.get(1).getCenterX() == notesP1.get(0).getCenterX()) {
                pressedNotesP1.set(1, pressedNotesP1.get(1) + 1);

                monsterP1.addResourceBlue();
            }

        }
        if (key == Input.KEY_E) {
//            if (endingYPos = notesP1.get(2).getY() ) {
//                pressedNotesP1.set(2, pressedNotesP1.get(2) + 1);
//
//                monsterP1.addResourceGreen();
//            }

            if (receiveBarsP1.get(2).getCenterY() - 50 <= notesP1.get(0).getCenterY() &&
                    receiveBarsP1.get(2).getCenterX() == notesP1.get(0).getCenterX()) {
                pressedNotesP1.set(2, pressedNotesP1.get(2) + 1);

                monsterP1.addResourceYellow();
            }

        }
        if (key == Input.KEY_R) {
//            if (endingYPos = notesP1.get(3).getY() ) {
//                pressedNotesP1.set(3, pressedNotesP1.get(3) + 1);
//
//                monsterP1.addResourceBlue();
//            }


            if (receiveBarsP1.get(3).getCenterY() - 50 <= notesP1.get(0).getCenterY() &&
                    receiveBarsP1.get(3).getCenterX() == notesP1.get(0).getCenterX()) {
                pressedNotesP1.set(3, pressedNotesP1.get(3) + 1);

                monsterP1.addResourceGreen();
            }


        }

        if (key == Input.KEY_U) {
//            if (endingYPos = notesP2.get(0).getY() ) {
//                pressedNotesP2.set(0, pressedNotesP2.get(0) + 1);
//
//                monsterP2.addResourceRed();
//            }

            if (receiveBarsP2.get(0).getCenterY() - 50 <= notesP2.get(0).getCenterY() &&
                    receiveBarsP2.get(0).getCenterX() == notesP2.get(0).getCenterX()) {
                pressedNotesP2.set(0, pressedNotesP2.get(0) + 1);

                monsterP2.addResourceBlue();
            }
        }
        if (key == Input.KEY_I) {
//            if (endingYPos = notesP2.get(1).getY() ) {
//                pressedNotesP2.set(1, pressedNotesP2.get(1) + 1);
//
//                monsterP2.addResourceYellow();
//            }

            if (receiveBarsP2.get(1).getCenterY() - 50 <= notesP2.get(0).getCenterY() &&
                    receiveBarsP2.get(1).getCenterX() == notesP2.get(0).getCenterX()) {
                pressedNotesP2.set(1, pressedNotesP2.get(1) + 1);

                monsterP2.addResourceRed();
            }
        }


        //FIXME
        // negative values
        // Cooldown
        // Slow-mo
        // Resource cost


        if (key == Input.KEY_O) {
//            if (endingYPos = notesP2.get(2).getY() ) {
//                pressedNotesP2.set(2, pressedNotesP2.get(2) + 1);
//
//                monsterP2.addResourceGreen();
//            }

            if (receiveBarsP2.get(2).getCenterY() - 50 <= notesP2.get(0).getCenterY() &&
                    receiveBarsP2.get(2).getCenterX() == notesP2.get(0).getCenterX()) {
                pressedNotesP2.set(2, pressedNotesP2.get(2) + 1);

                monsterP2.addResourceYellow();
            }

        }
        if (key == Input.KEY_P) {
//            if (endingYPos = notesP2.get(3).getY() ) {
//                pressedNotesP2.set(3, pressedNotesP2.get(3) + 1);
//
//                monsterP2.addResourceBlue();
//            }

            if (receiveBarsP2.get(3).getCenterY() - 50 <= notesP2.get(0).getCenterY() &&
                    receiveBarsP2.get(3).getCenterX() == notesP2.get(0).getCenterX()) {
                pressedNotesP2.set(3, pressedNotesP2.get(3) + 1);

                monsterP2.addResourceGreen();
            }
        }

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


        if (key == Input.KEY_ESCAPE) {
            pressedEscape = true;
        }
    }

    @Override
    public void keyReleased(int key, char pressedKey) {

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
