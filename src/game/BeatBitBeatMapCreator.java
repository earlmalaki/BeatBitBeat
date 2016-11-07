package game;

import org.newdawn.slick.*;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class BeatBitBeatMapCreator extends BasicGame implements KeyListener{

    private static final String gameName = "Beat Bit Beat Map Creator";

    private BufferedWriter bw;
    private FileWriter fw;
    private File file;

    private Audio music;

    private boolean pressedBlue;
    private boolean pressedRed;
    private boolean pressedYellow;
    private boolean pressedGreen;

    public BeatBitBeatMapCreator(String title) {
        super(title);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new BeatBitBeatMapCreator(gameName));

        app.setTitle("BeatBitBeatMapCreator");                // Title for top bar
        app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), false);     // fullscreen false for now
        app.setVSync(true);                         // matches monitor refresh rate. or use app.setTargetFrameRate(60);
        app.setMinimumLogicUpdateInterval(20);      // minimum delta of 15 ms
        app.setMaximumLogicUpdateInterval(20);      // maximum delta of 20 ms
        app.setAlwaysRender(true);                  // ???

        app.start();
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        file = new File("beatmap.txt");

        try {
            bw = new BufferedWriter(new FileWriter(file));

            music = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/State Music/Main Menu Music.ogg"));
            music.playAsMusic(1.0f, 1.0f, false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException{
        try {
            if (pressedBlue) {
                pressedBlue = false;
                bw.write("1000\n");
            } else if (pressedRed) {
                pressedRed = false;
                bw.write("0100\n");
            } else if (pressedYellow) {
                pressedYellow = false;
                bw.write("0010\n");
            } else if (pressedGreen) {
                pressedGreen = false;
                bw.write("0001\n");
            } else if (pressedBlue && pressedRed) {
                pressedBlue = false;
                pressedRed = false;
                bw.write("1100\n");
            } else {
                bw.write("0000\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {

    }

    @Override
    public void keyPressed(int key, char pressedKey) {
        if (key == Input.KEY_A) {
            pressedBlue = true;
        } else if (key == Input.KEY_S) {
            pressedRed = true;
        } else if (key == Input.KEY_D) {
            pressedYellow = true;
        } else if (key == Input.KEY_F) {
            pressedGreen = true;
        } else if (key == Input.KEY_A && key == Input.KEY_S) {
            pressedBlue = true;
            pressedRed = true;
        }

        if (key == Input.KEY_ESCAPE) {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }

    }

    @Override
    public void keyReleased(int key, char pressedKey) {

    }
}
