package game;

import org.newdawn.slick.*;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class BeatBitBeatMapCreator extends BasicGame implements KeyListener {

    private static final String gameName = "Beat Bit Beat Map Creator";

    private BufferedWriter bw;
    private FileWriter fw;
    private File file;

    private Music music;

    private boolean pressedBlue;
    private boolean pressedRed;
    private boolean pressedYellow;
    private boolean pressedGreen;

    private int displayWidth;
    private int displayHeight;

    private String fileName;
    private String fileDir;

    private List<String> beatMapLines;

    public BeatBitBeatMapCreator(String title) {
        super(title);
    }

    public static void main(String[] args) throws SlickException {

        AppGameContainer app = new AppGameContainer(new BeatBitBeatMapCreator(gameName));

        app.setTitle("BeatBitBeatMapCreator");                // Title for top bar
        app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), false);     // fullscreen false for now
        app.setVSync(true);                         // matches monitor refresh rate. or use app.setTargetFrameRate(60);
        app.setMinimumLogicUpdateInterval(15);      // minimum delta of 15 ms
        app.setMaximumLogicUpdateInterval(15);      // maximum delta of 20 ms
        app.setAlwaysRender(true);                  // ???

        app.start();
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

        beatMapLines = new LinkedList<>();

        displayWidth = gameContainer.getWidth();
        displayHeight = gameContainer.getHeight();

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal = fc.showOpenDialog(null);

//        int returnVal = fc.showDialog(new JFrame(), "Select Music File");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileName = fc.getSelectedFile().getName();
            fileDir = fc.getSelectedFile().getAbsolutePath();
            System.out.println(fileDir);
            music = new Music(fileDir);

            try {
                file = new File(fileDir.substring(0, fileDir.length() - fileName.length()) +fileName.substring(0, fileName.length() - 4) +" Beat Map.txt");
                bw = new BufferedWriter(new FileWriter(file));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        if (music.playing()) {
//            try {
                if (pressedBlue) {
                    pressedBlue = false;
                    beatMapLines.add("1000\n");
//                    bw.write("1000\n");
                }
                else if (pressedRed) {
                    pressedRed = false;
                    beatMapLines.add("0100\n");
//                    bw.write("0100\n");
                }
                else if (pressedYellow) {
                    pressedYellow = false;
                    beatMapLines.add("0010\n");
//                    bw.write("0010\n");
                }
                else if (pressedGreen) {
                    pressedGreen = false;
                    beatMapLines.add("0001\n");
//                    bw.write("0001\n");
                }
                else {
                    beatMapLines.add("0000\n");
//                    bw.write("0000\n");
                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }



    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.drawString(String.valueOf(music.getPosition()), ((displayWidth / 2 ) - String.valueOf(music.getPosition()).length()), 50);
        graphics.drawString(fileName.substring(0, fileName.length() - 4), (displayWidth / 2) - (fileName.length()), 100);
    }

    @Override
    public void keyPressed(int key, char pressedKey) {
        if (key == Input.KEY_U) {
            pressedBlue = true;
        }
        if (key == Input.KEY_I) {
            pressedRed = true;
        }
        if (key == Input.KEY_O) {
            pressedYellow = true;
        }
        if (key == Input.KEY_P) {
            pressedGreen = true;
        }

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
                if (music.getPosition() - 2 > 0 && beatMapLines.size() == 0) {
                    for (int i = 0; i <= 120; i ++) {
                        beatMapLines.remove(beatMapLines.size() - 1);
                    }
                    music.setPosition(music.getPosition() - 2f);
                } else {
                    music.setPosition(0);
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
                }
                music.pause();
            }

        }

//        if (key == Input.KEY_RIGHT) {
//            if (music.playing()) {
//                music.setPosition(music.getPosition() + 2f);
//            } else {
//                music.resume();
//                music.setPosition(music.getPosition() + 2f);
//                music.pause();
//            }
//        }

    }

    @Override
    public void keyReleased(int key, char pressedKey) {

    }
}
