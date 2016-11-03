package master;

import org.newdawn.slick.*;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

// Pick player, monster, music
public class CharacterSelectionState extends BasicGameState{

    Image backgroundImage;
    private int mouseX;
    private int mouseY;

    private Audio oggCharSelection;

    public CharacterSelectionState(){
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        backgroundImage = new Image("Assets/bgimage.jpg");

        try {
            oggCharSelection = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/beep beep short.ogg"));
            oggCharSelection.playAsMusic(1.0f, 1.0f, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
        Input input = gc.getInput();

        mouseX = input.getMouseX();
        mouseY = input.getMouseY();

        if (input.isMouseButtonDown(0)) {
            if ((165 <= mouseX && mouseX <= 345) && (185 <= mouseY && mouseY <= 215)) {
                sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
            }
        }
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        g.drawImage(backgroundImage, 400f, 400f);
        g.drawString("Mouse pos: " +mouseX +", " +mouseY, 50, 50);

        g.drawString("CLICK HERE: ", 165, 185 );
    }

    public int getID(){
        return 1;       // 0 represents UI.MainMenuState
    }

}
