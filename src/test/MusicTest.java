package test;

import org.newdawn.slick.*;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public class MusicTest extends BasicGame {

    private int mouseX;
    private int mouseY;

    private Audio oggIntroMusic;

    private float musicTime;

    public MusicTest(String title) {
        super(title);
    }

    public static void main(String args[]) throws SlickException{
        AppGameContainer app = new AppGameContainer(new MusicTest("Setup Test"));

        app.setDisplayMode(800, 600, false);
        app.setAlwaysRender(true);
        app.setTargetFrameRate(60);

        app.start();
    }


    public int getID(){
        return 0;       // 0 represents UI.MainMenuState
    }


    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        try {
            oggIntroMusic = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/beep beep short2.ogg"));
            oggIntroMusic.playAsMusic(1.0f, 1.0f, false);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        Input input = gameContainer.getInput();

        musicTime = oggIntroMusic.getPosition();
//        printMus(musicTime);
        System.out.printf("%.2f\n", musicTime);

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.drawString(String.valueOf(musicTime), 20, 20);
    }

    public void printMus(double musicTime) {
        System.out.printf("%.2f\n", musicTime);
    }
}

/*
0.01
0.02
0.03
0.06
0.07
0.09
0.10
0.13
0.14
0.15
0.17
0.19
0.21
0.22
0.24
0.27
0.30
0.31
0.34
0.35
0.36
0.38
0.38
0.39
 */