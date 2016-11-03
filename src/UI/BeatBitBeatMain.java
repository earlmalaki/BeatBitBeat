package UI;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Earl on 20/10/2016.
 */
public class BeatBitBeatMain extends StateBasedGame{

    private static String gameName = "Beat Bit Beat";
    private static int mainMenu = 0;
    private static int characterSelection = 1;
    private static int gameProper = 2;

    public BeatBitBeatMain(String gameName){
        super(gameName);
        this.addState(new MainMenuWindow(mainMenu));
        this.addState(new CharacterSelectionWindow(characterSelection));
        this.addState(new GameProperWindow(gameProper));
    }

    public static void main(String[] args) {
        AppGameContainer appgc;
        try{
            appgc = new AppGameContainer(new BeatBitBeatMain(gameName));
            appgc.setDisplayMode(1280, 800, false);
            appgc.setTargetFrameRate(60);
            appgc.start();
        } catch (SlickException e){
            e.printStackTrace();
        }


    }

    public void initStatesList(GameContainer gc) throws SlickException{
        this.getState(mainMenu).init(gc, this);
        this.getState(characterSelection).init(gc, this);
        this.getState(gameProper).init(gc, this);
        this.enterState(mainMenu);
    }
}

















