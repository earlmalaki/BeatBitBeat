package game;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class GameOverState extends BasicGameState implements KeyListener{

    private Image imageBG;

    private boolean pressedEnter = false;

    UnicodeFont fontStats;

    private int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private int displayHeight = BeatBitBeatMain.getDisplayHeight();


    @Override
    public int getID() {
        return BeatBitBeatMain.getGameOver();
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        imageBG = new Image("Assets/Graphics/Game Over/Game Over BG.png");

        fontStats = new UnicodeFont("Assets/Fonts/Disposable Droid/DisposableDroidBB.ttf", 26, false, false);
        fontStats.getEffects().add(new ColorEffect(java.awt.Color.white));
        fontStats.addAsciiGlyphs();
        fontStats.loadGlyphs();

    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if (pressedEnter) {
            pressedEnter = false;

            sbg.enterState(BeatBitBeatMain.getCharacterSelection(), new FadeOutTransition(), new FadeInTransition());
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        imageBG.draw();


        fontStats.drawString( (displayWidth / 2) - 100, 200, "Game over!");
//        System.out.println(GameProperState.monsterP1.getResourceBlue());
        fontStats.drawString(300, 500, "" + GameProperState.monsterP1.getMaxCombo());
        g.setColor(Color.white);
        if(GameProperState.monsterP1.isAlive()) {
            g.drawString("Player 2 wins!", 900, 300);
            g.drawString("Player 1 lost!", 100, 300);
        } else if(GameProperState.monsterP2.isAlive()) {
            g.drawString("Player 1 wins!", 100, 300);
            g.drawString("Player 2 lost!", 900, 300);
        }
        else{
            g.drawString("Player 1 wins/lost!", 100, 300);
            g.drawString("Player 2 wins/lost!", 900, 300);
            g.drawString("WOW DRWAWAWAWAWAW", 600, 300);
        }
        g.drawString("Player 1: ", 100, 300);
        g.drawString("Player 2: ", 900, 300);
        g.drawString("Max combo: " + GameProperState.monsterP1.getMaxCombo(), 100, 580);
        g.drawString("Max combo: " + GameProperState.monsterP2.getMaxCombo(), 900, 580);
        g.drawString("Total blue resources: " + GameProperState.monsterP1.getTotalResourceBlue(), 100, 600);
        g.drawString("Total red resources: " + GameProperState.monsterP1.getTotalResourceRed(), 100, 620);
        g.drawString("Total yellow resources: " + GameProperState.monsterP1.getTotalResourceYellow(), 100, 640);
        g.drawString("Total green resources: " + GameProperState.monsterP1.getTotalResourceGreen(), 100, 660);
        g.drawString("Total blue resources: " + GameProperState.monsterP2.getTotalResourceBlue(), 900, 600);
        g.drawString("Total red resources: " + GameProperState.monsterP2.getTotalResourceRed(), 900, 620);
        g.drawString("Total yellow resources: " + GameProperState.monsterP2.getTotalResourceYellow(), 900, 640);
        g.drawString("Total green resources: " + GameProperState.monsterP2.getTotalResourceGreen(), 900, 660);

    }
/*
    public static void setMonsterP1(Monster monsterP1) {
        GameOverState.monsterP1 = monsterP1;
    }

    public static void setMonsterP2(Monster monsterP2) {
        GameOverState.monsterP2 = monsterP2;
    }

    */

    @Override
    public void keyPressed (int key, char keyChar) {
        if (key == Input.KEY_ENTER) {
            pressedEnter = true;
        }
    }
}
