package game;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class GameOverState extends BasicGameState {

    private Animation animateBG;

    private boolean pressedEnter = false;

    UnicodeFont fontStats;

    private int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private int displayHeight = BeatBitBeatMain.getDisplayHeight();

    private static int p1MaxCombo = 0;
    private static int p1MaxRed = 0;
    private static int p1MaxGreen = 0;
    private static int p1MaxBlue = 0;
    private static int p1MaxYellow = 0;
    private static int p1HPLeft = 0;

    private static int p2MaxCombo = 0;
    private static int p2MaxRed = 0;
    private static int p2MaxGreen = 0;
    private static int p2MaxBlue = 0;
    private static int p2MaxYellow = 0;
    private static int p2HPLeft = 0;

    @Override
    public int getID() {
        return BeatBitBeatMain.getGameOver();
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        Image imagesBG[] = new Image[] {
                new Image ("Assets/Graphics/Game Over/Frames/GO0000.png"),
                new Image ("Assets/Graphics/Game Over/Frames/GO0001.png"),
                new Image ("Assets/Graphics/Game Over/Frames/GO0002.png"),
                new Image ("Assets/Graphics/Game Over/Frames/GO0003.png"),
                new Image ("Assets/Graphics/Game Over/Frames/GO0004.png"),
                new Image ("Assets/Graphics/Game Over/Frames/GO0005.png"),
                new Image ("Assets/Graphics/Game Over/Frames/GO0006.png"),
                new Image ("Assets/Graphics/Game Over/Frames/GO0007.png"),
                new Image ("Assets/Graphics/Game Over/Frames/GO0008.png"),
                new Image ("Assets/Graphics/Game Over/Frames/GO0009.png"),
        };

        animateBG = new Animation(imagesBG, 200);

        fontStats = new UnicodeFont("Assets/Fonts/Disposable Droid/DisposableDroidBB.ttf", 36, false, false);
        fontStats.getEffects().add(new ColorEffect(java.awt.Color.white));
        fontStats.addAsciiGlyphs();
        fontStats.loadGlyphs();
    }

    float xMouse;
    float yMouse;

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        Input input = gc.getInput();
        xMouse = input.getMouseX();
        yMouse = input.getMouseY();


        /*  Stepper effect on stats tally */
        if (p1MaxCombo < GameProperState.monsterP1.getMaxCombo())
            p1MaxCombo++;

        if (p1MaxRed < GameProperState.monsterP1.getTotalResourceRed())
            p1MaxRed++;

        if (p1MaxGreen < GameProperState.monsterP1.getTotalResourceGreen())
            p1MaxGreen++;

        if (p1MaxBlue < GameProperState.monsterP1.getTotalResourceBlue())
            p1MaxBlue++;

        if (p1MaxYellow < GameProperState.monsterP1.getTotalResourceYellow())
            p1MaxYellow++;

        if (p1HPLeft < GameProperState.monsterP1.getHp())
            p1HPLeft++;

        if (p2MaxCombo < GameProperState.monsterP2.getMaxCombo())
            p2MaxCombo++;

        if (p2MaxRed < GameProperState.monsterP2.getTotalResourceRed())
            p2MaxRed++;

        if (p2MaxGreen < GameProperState.monsterP2.getTotalResourceGreen())
            p2MaxGreen++;

        if (p2MaxBlue < GameProperState.monsterP2.getTotalResourceBlue())
            p2MaxBlue++;

        if (p2MaxYellow < GameProperState.monsterP2.getTotalResourceYellow())
            p2MaxYellow++;

        if (p2HPLeft < GameProperState.monsterP2.getHp())
            p2HPLeft++;
        /*  Stepper effect on stats tally */


        if (pressedEnter) {
            pressedEnter = false;

            CharacterSelectionState.resetCharSelStateFlags();
            GameProperState.resetGameProperState();
            GameOverState.resetGameOverState();

            sbg.enterState(BeatBitBeatMain.getCharacterSelection(), new FadeOutTransition(), new FadeInTransition());
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        animateBG.draw();

        g.drawString("X = " + xMouse + " Y = " + yMouse, 10, 50);

        if (GameProperState.monsterP1.isAlive()) {
            GameProperState.monsterP1.getAnimationIdle().draw(470,50);
        } else if (GameProperState.monsterP2.isAlive()) {
            GameProperState.monsterP2.getAnimationIdle().draw(155,50);
        } else {

        }

        fontStats.drawString(285, 520, "" + p1MaxCombo);
        fontStats.drawString(150, 605, "" + p1MaxRed);
        fontStats.drawString(240, 605, "" + p1MaxGreen);
        fontStats.drawString(325, 605, "" + p1MaxBlue);
        fontStats.drawString(410, 605, "" + p1MaxYellow);
        fontStats.drawString(285, 650, "" + p1HPLeft);

        fontStats.drawString(980, 520, "" + p2MaxCombo);
        fontStats.drawString(850, 605, "" + p2MaxRed);
        fontStats.drawString(940, 605, "" + p2MaxGreen);
        fontStats.drawString(1025, 605, "" + p2MaxBlue);
        fontStats.drawString(1110, 605, "" + p2MaxYellow);
        fontStats.drawString(980, 650, "" + p2HPLeft);

    }


    @Override
    public void keyPressed(int key, char keyChar) {

        if (key == Input.KEY_UP) {

        }

        if (key == Input.KEY_DOWN) {

        }

        if (key == Input.KEY_ENTER) {
            pressedEnter = true;
        }
    }

    public static void resetGameOverState() {
        p1MaxCombo = 0;
        p1MaxRed = 0;
        p1MaxGreen = 0;
        p1MaxBlue = 0;
        p1MaxYellow = 0;
        p1HPLeft = 0;

        p2MaxCombo = 0;
        p2MaxRed = 0;
        p2MaxGreen = 0;
        p2MaxBlue = 0;
        p2MaxYellow = 0;
        p2HPLeft = 0;
    }
}
