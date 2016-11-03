package gamedraft;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class GameOverState extends BasicGameState {

    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setColor(Color.white);
        g.drawString("Game over!", 300, 300);

    }
    
    @Override
    // State ID. 1 for GameOverState
    public int getID() {
        return 1;
    }

    
}
