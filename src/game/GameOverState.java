package game;

import game.monsters.Monster;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class GameOverState extends BasicGameState {

    @Override
    public int getID() {
        return BeatBitBeatMain.getGameOver();
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
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
}
