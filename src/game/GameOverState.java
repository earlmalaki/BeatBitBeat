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

    private UnicodeFont fontStats;
    private UnicodeFont fontWinningPlayer;

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

    private static Coordinate[] coordsIndicator;
    private static int indexPosIndicator = 0;


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

        coordsIndicator = new Coordinate[] {
                new Coordinate( 537, 595),
                new Coordinate( 537, 680),
        };

        animateBG = new Animation(imagesBG, 200);

        fontStats = new UnicodeFont("Assets/Fonts/Disposable Droid/DisposableDroidBB.ttf", 24, false, false);
        fontStats.getEffects().add(new ColorEffect(java.awt.Color.white));
        fontStats.addAsciiGlyphs();
        fontStats.loadGlyphs();

        fontWinningPlayer = new UnicodeFont("Assets/Fonts/Disposable Droid/DisposableDroidBB.ttf", 32, false, false);
        fontWinningPlayer.getEffects().add(new ColorEffect(java.awt.Color.white));
        fontWinningPlayer.addAsciiGlyphs();
        fontWinningPlayer.loadGlyphs();
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

            if (indexPosIndicator == 0) {
                sbg.enterState(BeatBitBeatMain.getCharacterSelection(), new FadeOutTransition(), new FadeInTransition());
            } else if (indexPosIndicator == 1) {
                sbg.enterState(BeatBitBeatMain.getMainMenu(), new FadeOutTransition(), new FadeInTransition());
            }

        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        animateBG.draw();

        g.drawString("X = " + xMouse + " Y = " + yMouse, 10, 50);

        g.setColor(Color.gray);
        g.fillRect(coordsIndicator[indexPosIndicator].getX(), coordsIndicator[indexPosIndicator].getY(), 207f, 3f);

        if (GameProperState.monsterP1.getHp() > GameProperState.monsterP2.getHp()) {
            GameProperState.monsterP1.getAnimationIdle().draw(470,50);
            GameProperState.monsterP1.getAnimationHumanIdle().draw(520,220);
            fontWinningPlayer.drawString(580, 472, "PLAYER 1");
        } else if (GameProperState.monsterP1.getHp() < GameProperState.monsterP2.getHp()) {
            GameProperState.monsterP2.getAnimationIdle().draw(210,50);
            GameProperState.monsterP2.getAnimationHumanIdle().draw(550,220);
            fontWinningPlayer.drawString(580, 472, "PLAYER 2");
        } else {
            GameProperState.monsterP1.getAnimationIdle().draw(190,150);
            GameProperState.monsterP2.getAnimationIdle().draw(480,150);

            GameProperState.monsterP1.getAnimationHumanIdle().draw(510,220);
            GameProperState.monsterP2.getAnimationHumanIdle().draw(610,220);

            fontWinningPlayer.drawString(600, 472, "DRAW");
        }

        fontStats.drawString(285, 538, "" + p1MaxCombo);
        fontStats.drawString(150, 605, "" + p1MaxRed);
        fontStats.drawString(240, 605, "" + p1MaxGreen);
        fontStats.drawString(325, 605, "" + p1MaxBlue);
        fontStats.drawString(410, 605, "" + p1MaxYellow);
        fontStats.drawString(285, 658, "" + p1HPLeft);

        fontStats.drawString(980, 538, "" + p2MaxCombo);
        fontStats.drawString(850, 605, "" + p2MaxRed);
        fontStats.drawString(940, 605, "" + p2MaxGreen);
        fontStats.drawString(1025, 605, "" + p2MaxBlue);
        fontStats.drawString(1110, 605, "" + p2MaxYellow);
        fontStats.drawString(980, 658, "" + p2HPLeft);

    }


    @Override
    public void keyPressed(int key, char keyChar) {

        if (key == Input.KEY_UP) {
            if (indexPosIndicator != 0) {
                indexPosIndicator--;
            }
        }

        if (key == Input.KEY_DOWN) {
            if (indexPosIndicator != 1) {
                indexPosIndicator++;
            }
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
