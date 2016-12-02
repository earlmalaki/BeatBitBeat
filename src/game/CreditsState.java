package game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class CreditsState extends BasicGameState implements KeyListener {

    private Animation animateBG;
    private boolean pressedEscape = false;


    @Override
    public int getID() {
        return BeatBitBeatMain.getCredits();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        Image imageBg[] = new Image[]{
                new Image("Assets/Graphics/Credits/Home Screen0000.png"),
                new Image("Assets/Graphics/Credits/Home Screen0001.png"),
                new Image("Assets/Graphics/Credits/Home Screen0002.png"),
                new Image("Assets/Graphics/Credits/Home Screen0003.png"),
                new Image("Assets/Graphics/Credits/Home Screen0004.png"),
                new Image("Assets/Graphics/Credits/Home Screen0005.png"),
                new Image("Assets/Graphics/Credits/Home Screen0006.png"),
                new Image("Assets/Graphics/Credits/Home Screen0007.png"),
                new Image("Assets/Graphics/Credits/Home Screen0008.png"),
                new Image("Assets/Graphics/Credits/Home Screen0009.png"),
        };

        animateBG = new Animation(imageBg,200);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        animateBG.draw();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (pressedEscape) {
            pressedEscape = false;
            stateBasedGame.enterState(BeatBitBeatMain.getMainMenu(), new FadeOutTransition(), new FadeInTransition());
        }
    }

    @Override
    public void keyPressed(int key, char keyChar) {
        if (key == Input.KEY_ESCAPE) {
            pressedEscape = true;
        }
    }
}
