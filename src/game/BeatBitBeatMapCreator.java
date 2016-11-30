package game;

import org.newdawn.slick.*;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * A separate program needed to create beatmap for the game BeatBitBeat
 *
 * Writes lines to a text file at a rate of 15ms
 * The fixed rate is achieved by setting a fixed delta time
 * Delta time is the time in between calls of update() method
 *
 * Each line represents the beat map information
 * Each line contains 4 columns, represented by Red, Green, Blue, Yellow, respectively
 * Possible lines are the ff:
 * 0000     - no beat
 * 1000     - red note beat
 * 0100     - green note beat
 * 0010     - green blue beat
 * 0001     - green yellow beat
 *
 *
 * The BeatBitBeat beat map reader will then read this file at the same rate -- 15ms
 * The reader drops notes according to the fetched line from the beat map text file
 *
 */

public class BeatBitBeatMapCreator extends BasicGame implements KeyListener {

    private static final String gameName = "Beat Map Creator - BeatBitBeat";

    private BufferedWriter bw;
    private FileWriter fw;

    private Music music;

    private boolean pressedBlue;
    private boolean pressedRed;
    private boolean pressedYellow;
    private boolean pressedGreen;

    private String fileName;
    private String fileDir;

    // Beat map information stored in a LinkedList
    // each node in the link represents a single line
    private List<String> beatMapLines = new LinkedList<>();

    private static final int displayWidth = 600;
    private static final int displayHeight = 400;

    public BeatBitBeatMapCreator(String title) {
        super(title);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new BeatBitBeatMapCreator(gameName));

        app.setTitle(gameName);                // Title for top bar
        app.setDisplayMode(displayWidth, displayHeight, false);     // fullscreen false for now
        app.setVSync(true);                         // matches monitor refresh rate. or use app.setTargetFrameRate(60);
        app.setMinimumLogicUpdateInterval(15);      // minimum delta of 15 ms
        app.setMaximumLogicUpdateInterval(15);      // maximum delta of 20 ms

        app.start();
    }


    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        // Use JFileChooser to open a file
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = fc.showOpenDialog(new JFrame());    // receive response from the dialog, represented by integer

        if (returnVal == JFileChooser.APPROVE_OPTION) {     // if user opened a file
            fileName = fc.getSelectedFile().getName();
            fileDir = fc.getSelectedFile().getAbsolutePath();
            music = new Music(fileDir);     // load selected music

            try {
                File file = new File("Beat Map.txt");
                bw = new BufferedWriter(new FileWriter(file));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        if (music.playing()) {      // must only write if music is playing
                if (pressedRed) {
                    pressedRed = false;
                    beatMapLines.add("1000\n");
                }
                else if (pressedGreen) {
                    pressedGreen = false;
                    beatMapLines.add("0100\n");
                }
                else if (pressedBlue) {
                    pressedBlue = false;
                    beatMapLines.add("0010\n");
                }
                else if (pressedYellow) {
                    pressedYellow = false;
                    beatMapLines.add("0001\n");
                }
                else {
                    beatMapLines.add("0000\n");
                }
            System.out.println(beatMapLines.size());
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.drawString("Time", (displayWidth / 2) - 20, 10);
        graphics.drawString(String.valueOf(music.getPosition()), ((displayWidth / 2 ) - String.valueOf(music.getPosition()).length() * 10), 20);
        graphics.drawString(fileName.substring(0, fileName.length() - 4), (displayWidth / 2) - (fileName.length() * 10), 30);

        graphics.drawString("U - Red | I - Green | O - Blue | P - Yellow", (displayWidth / 2) - 50, 70);
        graphics.drawString("S - Start | Space - Pause/Play | Left - Step back", (displayWidth / 2) - 45, 80);
        graphics.drawString("Esc - Save Beat Map and Exit", (displayWidth / 2) - 30, 90);
    }

    @Override
    public void keyPressed(int key, char pressedKey) {
        if (key == Input.KEY_U) {
            pressedRed = true;
        }
        if (key == Input.KEY_I) {
            pressedGreen = true;
        }
        if (key == Input.KEY_O) {
            pressedBlue = true;
        }
        if (key == Input.KEY_P) {
            pressedYellow = true;
        }

        // Write beat map from linkedlist to file
        if (key == Input.KEY_ESCAPE) {
            try {
                for (String line : beatMapLines) {
                    bw.write(line);
                }

                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }

        // Pause/Play music
        if (key == Input.KEY_SPACE) {
            if (music.playing()) {
                music.pause();
            } else {
                music.resume();
            }
        }

        if (key == Input.KEY_S) {
            music.play();
        }

        if (key == Input.KEY_LEFT) {
            if (music.playing()) {

                // check if can still backtrack by 2 seconds
                if (music.getPosition() - 2 > 0 && beatMapLines.size() == 0) {
                    for (int i = 0; i <= 120; i ++) {
                        beatMapLines.remove(beatMapLines.size() - 1);
                    }
                    music.setPosition(music.getPosition() - 2f);    // set music position to 2 seconds before
                } else {
                    music.setPosition(0);
                    beatMapLines.clear();
                }


            } else {
                music.resume();
                if (music.getPosition() - 2 > 0 && beatMapLines.size() == 0) {
                    for (int i = 0; i <= 120; i ++) {
                        beatMapLines.remove(beatMapLines.size() - 1);
                    }
                    music.setPosition(music.getPosition() - 2f);
                } else {
                    music.setPosition(0);
                    beatMapLines.clear();
                }
                music.pause();
            }
        }


    }

    @Override
    public void keyReleased(int key, char pressedKey) {

    }
}
