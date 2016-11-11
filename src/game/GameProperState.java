/**
 * Albores, Allyssa
 * Bedio, Aiden Justin
 * Malaki, Earl Timothy
 * Paler, Timothy River
 * <p>
 * BSCS - II | UP - Cebu
 * CMSC22 - OOP
 * Final Project
 *

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
 * game.Note:
 * - Prioritize MVP first.
 *
 */

// TODO Finish monster implementation

package game;

import game.monsters.Monster;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.*;
import java.util.ArrayList;

public class GameProperState extends BasicGameState implements KeyListener {

    private static Monster monsterP1;
    private static Monster monsterP2;

    private ArrayList<Note> notesP1;
    private ArrayList<Note> notesP2;
    private Image[] imagesNotes;

    // Player 1 note x positions
    private float p1x1 = (float) (displayWidth * (0.01));
    private float p1x2 = (float) (p1x1 + (displayWidth * (0.03)));
    private float p1x3 = (float) (p1x2 + (displayWidth * (0.03)));
    private float p1x4 = (float) (p1x3 + (displayWidth * (0.03)));

    // Player 2 note x positions
    private float p2x4 = (float) (displayWidth - (displayWidth * (0.06)));
    private float p2x3 = (float) (p2x4 - (displayWidth * (0.03)));
    private float p2x2 = (float) (p2x3 - (displayWidth * (0.03)));
    private float p2x1 = (float) (p2x2 - (displayWidth * (0.03)));


    private float startingYPos = 0f;
    private float endingYPos = 600f;


    private static Music gameMusic;
    private Coordinate coordPlayer1;
    private Coordinate coordPlayer2;
    private static Animation animationPlayer1;
    private static Animation animationPlayer2;


    private static final int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private static final int displayHeight = BeatBitBeatMain.getDisplayHeight();


    // Music position
    private float musicPosition = 00.00f;

    private float speedNoteDrop = 4f;
    private int timePassed = 0;     // in milliseconds
    private boolean skillCast = false;
    private int timeSlowEffect;  // 5000ms == 5s

    private static BufferedReader br;
    private boolean pressedEscape;




    public int getID() {
        return BeatBitBeatMain.getGameProper();
    }

    public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {

        pressedEscape = false;

        coordPlayer1 = new Coordinate((displayWidth / 2) - 400, 100);
        coordPlayer2 = new Coordinate((displayWidth / 2) + 100, 100);

        imagesNotes = new Image[] {
                new Image("Assets/Graphics/Game Proper/Note Red.png"),
                new Image("Assets/Graphics/Game Proper/Note Yellow.png"),
                new Image("Assets/Graphics/Game Proper/Note Green.png"),
                new Image("Assets/Graphics/Game Proper/Note Blue.png")
        };

        notesP1 = new ArrayList<>();
        notesP2 = new ArrayList<>();

        timeSlowEffect = 3000;  // 5000ms == 5s
    }


    int delta;  // for printing. temporary
    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
        this.delta = delta;


        // TODO adjust pitch. match pitch loss and map read speed loss
        // slow music and read map
        if (skillCast) {
            timePassed += delta;
            if (timePassed == timeSlowEffect) {
                skillCast = false;
                timePassed = 0;
                speedNoteDrop = 4f;
                gameMusic.play();
                gameMusic.setPosition(musicPosition);
            }
            else {
                speedNoteDrop = 0.4f;
                // reduce file read speed to 6 lines per second
                // original speed is 60 lines per second
                // speed dropped from 4 to 0.4, which is 0.1 or 1%
                // beat map reading speed should also decrease by the same percentage
                // thus, reading should be at 6 lines per second
                if (timePassed % 90 == 0) {
                    readBeatMap();
                }
            }

        } else {
            readBeatMap();
        }

        // Control the falling of noteBars bars
        for (Note note : notesP1) {
            note.setY(note.getY() + speedNoteDrop);
        }

        // Control the falling of noteBars bars
        for (Note note : notesP2) {
            note.setY(note.getY() + speedNoteDrop);
        }


        // detect if note bars go past the hitbox
        for (int i = 0; i < notesP1.size() - 1; i++ ) {
            if (notesP1.get(i).getY() > endingYPos) {

                if (!gameMusic.playing()) {
                    gameMusic.play();
                }

                notesP1.remove(i);
            }
        }

        // detect if note bars go past the hitbox
        for (int i = 0; i < notesP2.size() - 1; i++ ) {
            if (notesP2.get(i).getY() > endingYPos) {
                notesP2.remove(i);
            }
        }


        musicPosition = gameMusic.getPosition();
        animationPlayer2.update(delta);

    }

    public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {

        // Game Screen borders
        // TODO this is to be replaced by on-wallpaper border lines
        g.drawLine((displayWidth / 3) - (int) (displayWidth * 0.16), 0, (displayWidth / 3) - (int) (displayWidth * 0.16), displayHeight);  // left vertical division line
        g.drawLine(displayWidth - (displayWidth / 3) + (int) (displayWidth * 0.16), 0, displayWidth - (displayWidth / 3) + (int) (displayWidth * 0.16), displayHeight);  // right vertical division line
        g.drawLine((displayWidth / 2), (displayHeight / 2) + 50, (displayWidth / 2), displayHeight);  // center vertical division line
        g.drawLine((displayWidth / 3) - (int) (displayWidth * 0.16), (displayHeight / 2) + 50, displayWidth - (displayWidth / 3) + (int) (displayWidth * 0.16), (displayHeight / 2) + 50);  // center horizontal division line
        g.drawLine(0 ,endingYPos, displayWidth, endingYPos);

        // Draw player character animations
        animationPlayer1.draw(coordPlayer1.getX(), coordPlayer1.getY());
        animationPlayer2.getCurrentFrame().getFlippedCopy(true, false).draw(coordPlayer2.getX(), coordPlayer2.getY());


        // render falling notes
        for (Note note : notesP1) {
            note.getImage().draw(note.getX(), note.getY());
        }
        for (Note note : notesP2) {
            note.getImage().draw(note.getX(), note.getY());
        }


        g.setColor(Color.white);
        g.drawString("Time", (displayWidth / 2) - 5, 5);
        String strMusicPosition = null;
        if (gameMusic.playing()){
            strMusicPosition = String.valueOf(musicPosition);
            g.drawString(strMusicPosition, (displayWidth / 2) - (strMusicPosition.length()), 20);

        }
        g.drawString("Curr NoteBars : " + notesP1.size() +"  " +notesP2.size(), (displayWidth / 2) - 10, 50);

        // For testing purposes
        g.drawString("P1 Pressed Red: " + monsterP1.getResourceRed(), 20, 100);
        g.drawString("P1 Pressed Yellow: " + monsterP1.getResourceYellow(), 20, 160);
        g.drawString("P1 Pressed Green: " + monsterP1.getResourceGreen(), 20, 190);
        g.drawString("P1 Pressed Blue: " + monsterP1.getResourceBlue(), 20, 130);

        g.drawString("P2 Pressed Red: " + monsterP2.getResourceRed(), 900, 100);
        g.drawString("P2 Pressed Blue: " + monsterP2.getResourceYellow(), 900, 130);
        g.drawString("P2 Pressed Yellow: " + monsterP2.getResourceGreen(), 900, 160);
        g.drawString("P2 Pressed Green: " + monsterP2.getResourceBlue(), 900, 190);


        g.drawString("DELTA = " + delta, 100, 50);
    }

    @Override
    public void keyPressed(int key, char pressedKey) {
        // Key listener
        if (key == Input.KEY_Q) {

            // if note bar is near or within corresponding receive bar
            if (endingYPos - 40 <= notesP1.get(0).getY() ) {
                monsterP1.addResourceRed();
            }

        }
        if (key == Input.KEY_W) {
            if (endingYPos - 40 <= notesP1.get(0).getY() ) {
                monsterP1.addResourceYellow();
            }

        }
        if (key == Input.KEY_E) {
            if (endingYPos - 40 <= notesP1.get(0).getY() ) {
                monsterP1.addResourceGreen();
            }

        }
        if (key == Input.KEY_R) {
            if (endingYPos - 40 <= notesP1.get(0).getY() ) {
                monsterP1.addResourceBlue();
            }

        }

        if (key == Input.KEY_U) {
            if (endingYPos - 40 <= notesP2.get(0).getY() ) {
                monsterP2.addResourceRed();
            }

        }
        if (key == Input.KEY_I) {
            if (endingYPos - 40 <= notesP2.get(0).getY() ) {
                monsterP2.addResourceYellow();
            }

        }
        if (key == Input.KEY_O) {
            if (endingYPos - 40 <= notesP2.get(0).getY() ) {
                monsterP2.addResourceGreen();
            }

        }
        if (key == Input.KEY_P) {
            if (endingYPos - 40 <= notesP2.get(0).getY() ) {
                monsterP2.addResourceBlue();
            }

        }

        if (key == Input.KEY_H) {

        }

        if (key == Input.KEY_J) {

        }

        if (key == Input.KEY_M) {
            skillCast = true;
            gameMusic.play(0.10f, 1f);
            gameMusic.setPosition(musicPosition);
            // TODO adjust pitch. match pitch loss and map read speed loss
        }


        if (key == Input.KEY_ESCAPE) {
            pressedEscape = true;
        }
    }

    @Override
    public void keyReleased(int key, char pressedKey) {

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
