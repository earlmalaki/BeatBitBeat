


package game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class VersusPreviewState extends BasicGameState implements KeyListener{

    private static Animation animateBG;
    private static Animation pressEnter;

    private boolean pressable = false;
    private boolean enterPressed = false;

    private static Coordinate coordHumanP1 = new Coordinate(1000, 120);
    private static Coordinate coordHumanP2 = new Coordinate(100, 450);

    private static final int displayWidth = BeatBitBeatMain.getDisplayWidth();
    private static final int displayHeight = BeatBitBeatMain.getDisplayHeight();

    int frameCount;

    @Override
    public int getID() {
        return BeatBitBeatMain.getVersusPreview();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Image imagesBG[] = new Image[]{
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0000.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0001.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0002.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0003.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0004.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0005.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0006.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0007.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0008.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0009.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0010.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0011.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0012.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0013.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0014.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0015.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0016.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0017.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0018.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0019.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0020.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0021.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0022.png"),
                new Image ("Assets/Graphics/VersusPreview/versus/Versus!0023.png"),
        };

        Image[] pEnter = new Image[]{
                new Image ("Assets/Graphics/VersusPreview/press/press0000.png"),
                new Image ("Assets/Graphics/VersusPreview/press/press0001.png"),
                new Image ("Assets/Graphics/VersusPreview/press/press0002.png"),
                new Image ("Assets/Graphics/VersusPreview/press/press0003.png"),
                new Image ("Assets/Graphics/VersusPreview/press/press0004.png"),
                new Image ("Assets/Graphics/VersusPreview/press/press0005.png"),
                new Image ("Assets/Graphics/VersusPreview/press/press0006.png"),
                new Image ("Assets/Graphics/VersusPreview/press/press0007.png"),
                new Image ("Assets/Graphics/VersusPreview/press/press0008.png"),
                new Image ("Assets/Graphics/VersusPreview/press/press0009.png"),
                new Image ("Assets/Graphics/VersusPreview/press/press0010.png"),
                new Image ("Assets/Graphics/VersusPreview/press/press0011.png"),
                new Image ("Assets/Graphics/VersusPreview/press/press0012.png"),
                new Image ("Assets/Graphics/VersusPreview/press/press0013.png"),
        };

        pressEnter = new Animation(pEnter,100);
        animateBG = new Animation(imagesBG,100);

        frameCount = animateBG.getFrameCount();
    }

    float xMouse;
    float yMouse;

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

        Input input = gc.getInput();
        xMouse = input.getMouseX();
        yMouse = input.getMouseY();

        if (coordHumanP1.getX() != 100){
            coordHumanP1.setX(coordHumanP1.getX() - 15);
            coordHumanP2.setX(coordHumanP2.getX() + 15);
        }

        if (pressable){
            if (enterPressed){
                pressable = false;
                enterPressed = false;

                sbg.enterState(BeatBitBeatMain.getGameProper(), new FadeOutTransition(), new FadeInTransition());


            }
        }

          //
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

        animateBG.draw();
        g.drawString("X = " + xMouse + " Y = " + yMouse, 10, 50);

        if (animateBG.getFrame() == frameCount - 1){
            animateBG.stop();
        }

        if (animateBG.isStopped()){
            pressEnter.draw();
            pressable = true;
        }

        GameProperState.monsterP1.getAnimationHumanIdle().draw(coordHumanP1.getX(),coordHumanP1.getY());
        GameProperState.monsterP2.getAnimationHumanIdle().draw(coordHumanP2.getX(),coordHumanP2.getY());

    }

    @Override
    public void keyPressed(int key, char pressedKey){
        if (key == Input.KEY_ENTER){
            enterPressed = true;
        }
    }

    public static void resetVersusState() {
        coordHumanP1.setX(1000);
        coordHumanP1.setY(120);
        coordHumanP2.setX(100);
        coordHumanP2.setY(450);

        animateBG.restart();
        pressEnter.restart();
    }




}
