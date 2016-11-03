package master;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameProperState extends BasicGameState implements KeyListener {

    private ArrayList<Rectangle> noteBars;
    private ArrayList<Rectangle> receiveBars;
    private ArrayList<Rectangle> lineBars;

    public Audio gameMusic;

    private int timePassed;
    private Random random;

    private static final int displayWidth = BeatBitBeatMain.displayWidth;
    private static final int displayHeight = BeatBitBeatMain.displayHeight;

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

    Image p1Sprite;

    public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
        p1Sprite = new Image("/Assets/player1.jpg");
        try {
            gameMusic = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/beep beep short2.ogg"));
            gameMusic.playAsMusic(1.0f, 1.0f, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        noteBars = new ArrayList<>();
        receiveBars = new ArrayList<>();
        lineBars = new ArrayList<>();
        random = new Random();

        timePassed = 0;


        // Add horizontal receive bars
        receiveBars.add(new Rectangle(p1x1 - recBarWidth/2, endingYPos, recBarWidth, recBarHeight));
        receiveBars.add(new Rectangle(p1x2 - recBarWidth/2, endingYPos, recBarWidth, recBarHeight));
        receiveBars.add(new Rectangle(p1x3 - recBarWidth/2, endingYPos, recBarWidth, recBarHeight));
        receiveBars.add(new Rectangle(p1x4 - recBarWidth/2, endingYPos, recBarWidth, recBarHeight));
        receiveBars.add(new Rectangle(p2x1 - recBarWidth/2, endingYPos, recBarWidth, recBarHeight));
        receiveBars.add(new Rectangle(p2x2 - recBarWidth/2, endingYPos, recBarWidth, recBarHeight));
        receiveBars.add(new Rectangle(p2x3 - recBarWidth/2, endingYPos, recBarWidth, recBarHeight));
        receiveBars.add(new Rectangle(p2x4 - recBarWidth/2, endingYPos, recBarWidth, recBarHeight));

        // Add vertical line bars
        lineBars.add(new Rectangle(p1x1 - lineBarWidth/2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p1x2 - lineBarWidth/2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p1x3 - lineBarWidth/2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p1x4 - lineBarWidth/2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p2x1 - lineBarWidth/2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p2x2 - lineBarWidth/2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p2x3 - lineBarWidth/2, startingYPos, lineBarWidth, lineBarHeight));
        lineBars.add(new Rectangle(p2x4 - lineBarWidth/2, startingYPos, lineBarWidth, lineBarHeight));

    }




    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {

        // generate noteBars at random x
        timePassed += delta;
        //System.out.println(delta);
        if(timePassed > 400){
            timePassed = 0;

            if (genRand() == 1){
                noteBars.add(new Rectangle(p1x1 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
                noteBars.add(new Rectangle(p2x1 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
            }
            else if (genRand() == 2) {
                noteBars.add(new Rectangle(p1x2 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
                noteBars.add(new Rectangle(p2x2 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
            }
            else if (genRand() == 3) {
                noteBars.add(new Rectangle(p1x3 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
                noteBars.add(new Rectangle(p2x3 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
            }
            else if (genRand() == 4) {
                noteBars.add(new Rectangle(p1x4 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
                noteBars.add(new Rectangle(p2x4 - (noteBarWidth / 2), startingYPos, noteBarWidth, noteBarHeight));
            }
        }




        // Control the falling of noteBars bars
        for(Rectangle c : noteBars){
            c.setCenterY(c.getCenterY() + delta/5);
        }


        for(int i = 0; i < noteBars.size() - 1; i++) {
//        for(int i = noteBars.size() - 1; i >= 0; i--) {
            Rectangle c = noteBars.get(i);
            if(c.getCenterY() >= endingYPos){
                noteBars.remove(i);
                BeatBitBeatMain.lives--;
            }
            i++;

        }

        musicPosition = gameMusic.getPosition();

    }

    public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {

        // Game Screen borders
        g.drawLine( (displayWidth / 3) - 80, 0, (displayWidth / 3) - 80, displayHeight);  // left vertical division line
        g.drawLine( displayWidth - (displayWidth / 3) + 80, 0, displayWidth - (displayWidth / 3) + 80, displayHeight);  // right vertical division line
        g.drawLine( (displayWidth / 2), displayHeight / 2, (displayWidth / 2), displayHeight);  // center vertical division line
        g.drawLine((displayWidth / 3) - 80, displayHeight / 2, displayWidth - (displayWidth / 3) + 80, displayHeight / 2);  // center horizontal division line


        // Draw player character sprites
        g.drawImage(p1Sprite, 500, 200);

        // Draw monster sprites

        // Draw vertical line bars
        g.setColor(Color.lightGray);
        for(Rectangle r : lineBars){
            g.fill(r);
        }

        // Draw horizontal receive bars
        g.setColor(Color.red);
        for(Rectangle r : receiveBars){
            g.fill(r);
        }

        // Draw falling note bars
        g.setColor(Color.red);
        for(Rectangle c : noteBars){
            g.fill(c);
        }


        g.setColor(Color.white);
        g.drawString("Current noteBars : " + noteBars.size(), 20, 50);
        g.drawString("Current score : " + BeatBitBeatMain.gameScore, 20, 100);
        g.drawString("Current lives : " + BeatBitBeatMain.lives, 20, 150);
        g.drawString("Time", (displayWidth / 2) - 5, 5);

        String strMusicPosition = String.valueOf(musicPosition);
        g.drawString(strMusicPosition, (displayWidth / 2) - (strMusicPosition.length() / 2), 20);

    }

    // State ID. 0 for GameState
    public int getID() {
        return 2;
    }

    public int genRand() {
        return random.nextInt(4) + 1;
    }

    @Override
    public void keyPressed(int key, char pressedKey) {

        // Keylistener
        if (pressedKey == 'a') {
            System.out.println("A");
//            if (receiveBars.get(0).getCenter() == ) {
//
//            }
        } else if (pressedKey == 's') {
            System.out.println("S");
        } else if (pressedKey == 'd') {

        } else if (pressedKey == 'f') {

        } else if (pressedKey == 'h') {

        } else if (pressedKey == 'j') {

        } else if (pressedKey == 'k') {

        } else if (pressedKey == 'l') {

        }
    }

    @Override
    public void keyReleased(int key, char c) {

    }



} // END OF CLASS
