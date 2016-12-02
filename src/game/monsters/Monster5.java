/**
 * Albores, Allyssa
 * Bedio, Aiden Justin
 * Malaki, Earl Timothy
 * Paler, Timothy River
 * <p>
 * BSCS - II | UP - Cebu
 * CMSC22 - OOP
 * Final Project
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 * <p>
 * Done:
 * - basic skeleton code for concrete monster
 * <p>
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 * <p>
 * game.Note:
 */

/**
 * Done:
 * - basic skeleton code for concrete monster
 *
 * To Do:
 * - add specific identity/behaviour (skills, capabilities, etc.)
 * - do this to the remaining monsters
 *
 * game.Note:
 *
 */

package game.monsters;

import game.SkillCost;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public class Monster5 extends Monster {

    private Animation animationIdle;
    private Animation animationSkill1;
    private Animation animationSkill2;
    private Animation animationSkillUlt;


    private Image[] skillIcons;
    // TODO enter proper duration of skill animation when sprites are done
    private Animation animationHumanIdle;

    private final SkillCost costSkill1 = new SkillCost(3, 0, 0, 3);
    private final SkillCost costSkill2 = new SkillCost(0, 7, 7, 0);
    private final SkillCost costSkillUlt = new SkillCost(12, 12, 12, 12);
    private SkillCost currResources;

    // TODO enter proper duration of skill animation when sprites are done
    private static final int skill1Duration = 2600;
    private static final int skill2Duration = 3000;
    private static final int skillUltDuration = 5200;
    private static final int skill1Cooldown = 2000;
    private static final int skill2Cooldown = 3000;
    private static final int skillUltCooldown = 5000;

    private Audio monsterSfx1;
    private Audio monsterSfx2;
    private Audio monsterSfx3;

    private static final int frameDurationMonsterIdle = 260;
    private static final int frameDurationHumanIdle = 300;
    private static final int frameDurationSkill1 = 150;
    private static final int frameDurationSkill2 = 130;
    private static final int frameDurationSkillUlt = 401;
    private static final int frameDurationSkillUltP1 = 100;
    private static final int frameDurationSkillUltP2 = 200;
    private static final int frameDurationSkillUltP3 = 100;
    // ULT skill sequence duration
//    private static final int skillUltADuration = 600;
//    private static final int skillUltBDuration = 4000;
//    private static final int skillUltDuration = 600;

    private static final int animationDurationSkillUltP1 = 600;
    private static final int animationDurationSkillUltP2 = 4000;
    private static final int animationDurationSkillUltP3 = 600;
    // ULT skill sequence frame duration
//    private static final int frameDurationSkillAUlt = 100;
//    private static final int frameDurationSkillBUlt = 200;
//    private static final int frameDurationSkillCUlt = 100;

    private static final int damageSkill1 = 15;
    private static final int damageSkill2 = 25;
    private static final int damageSkillUlt = 20;

    private Image imageFaceHealthBar;


    public Monster5(int playerNumber) throws SlickException {
        super();
        try {
            monsterSfx1 = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/Root/Moon_Glaive_attack.ogg")); //???
            monsterSfx2 = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/Root/Inverse_Bayonet_Tidebringer_attack.ogg")); //x3
            monsterSfx3 = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/Root/Tornado.ogg")); //NICE

        } catch (IOException e) {
            e.printStackTrace();
        }
        skillIcons = new Image[]{
                new Image("Assets/Graphics/Monster and Human Sprites/Root/Root - 1 Leaf Icon.png"),
                new Image("Assets/Graphics/Monster and Human Sprites/Root/Root - 2 Whip Icon.png"),
                new Image("Assets/Graphics/Monster and Human Sprites/Root/Root - 3 Sakura Icon.png")
        };
        if (playerNumber == 1) {
            animationIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - Idle P1.png", 600, 300, 0), frameDurationMonsterIdle);
            animationHumanIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - Human P1.png", 150, 150, 0), frameDurationHumanIdle);

            animationSkill1 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - 1 Leaf P1.png", 600, 300, 0), frameDurationSkill1);
            animationSkill2 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/whip p1.png", 720, 300, 0), frameDurationSkill2);
 Image[] skillUlt = new Image[]{
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura00.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura01.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura02.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura03.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura04.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura05.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura06.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura07.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura08.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura09.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura10.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura11.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura12.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura13.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura14.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura15.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura16.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura17.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura18.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura19.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura20.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura21.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura22.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura23.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura24.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura25.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura26.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura27.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura28.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura29.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura30.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p1/sakura31.png")
            };

            int[] duration = new int[]{
                    100,
                    100,
                    100,
                    100,
                    100,
                    100, //6
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200, //20
                    100,
                    100,
                    100,
                    100,
                    100,
                    100


            };
            animationSkillUlt = new Animation(skillUlt, duration);

            imageFaceHealthBar = new Image("Assets/Graphics/Monster and Human Sprites/Root/Root - Face Health Bar P1.png");

            // TODO uncomment when sprites for player2 (flipper player1 sprites) are done
        } else if (playerNumber == 2)

        {
            animationIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - Idle P2.png", 600, 300, 0), frameDurationMonsterIdle);
            animationHumanIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - Human P2.png", 150, 150, 0), frameDurationHumanIdle);

            animationSkill1 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - 1 Leaf P2.png", 600, 300, 0), frameDurationSkill1);
            animationSkill2 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/whip p2.png", 720, 300, 0), frameDurationSkill2);
//            animationSkillUlt = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - Idle P2.png", 600, 300, 0), frameDurationMonsterIdle);
            Image[] skillUlt = new Image[]{
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura00.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura01.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura02.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura03.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura04.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura05.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura06.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura07.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura08.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura09.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura10.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura11.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura12.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura13.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura14.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura15.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura16.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura17.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura18.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura19.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura20.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura21.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura22.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura23.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura24.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura25.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura26.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura27.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura28.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura29.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura30.png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Root/p2/sakura31.png")
            };
            animationSkillUlt = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - Idle P2.png", 600, 300, 0), frameDurationMonsterIdle);

            imageFaceHealthBar = new Image("Assets/Graphics/Monster and Human Sprites/Root/Root - Face Health Bar P2.png");

            int[] duration = new int[]{
                    100,
                    100,
                    100,
                    100,
                    100,
                    100, //6
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200, //20
                    100,
                    100,
                    100,
                    100,
                    100,
                    100


            };
            animationSkillUlt = new Animation(skillUlt, duration);
        }
    }


    @Override
    public Image[] getSkillIcons() {
        return skillIcons;
    }

    public void skill1() {
        super.setDamage(damageSkill1);
        super.doSkillCost(costSkill1);
        getAnimationSkill1().restart();
        monsterSfx1.playAsSoundEffect(1.0f, 1.0f, false);

    }

    public void skill2() {
        super.setDamage(damageSkill2);
        super.doSkillCost(costSkill2);
        getAnimationSkill2().restart();
        monsterSfx2.playAsSoundEffect(1.0f, 1.0f, false);
        monsterSfx2.playAsSoundEffect(1.0f, 1.0f, false);
        monsterSfx2.playAsSoundEffect(1.0f, 1.0f, false);

    }

    public void skillUlt() {
        super.setDamage(damageSkillUlt);
        super.doSkillCost(costSkillUlt);
        monsterSfx3.playAsSoundEffect(1.0f, 1.0f, false);

    }

    public int getDurationSkill1() {
        return skill1Duration;
    }

    @Override
    public int getDurationSkill2() {
        return skill2Duration;
    }

    @Override
    public int getDurationSkillUlt() {
        return skillUltDuration;
    }

    @Override
    public int getCooldownSkill1() {
        return skill1Cooldown;
    }

    @Override
    public int getCooldownSkill2() {
        return skill2Cooldown;
    }

    @Override
    public int getCooldownSkillUlt() {
        return skillUltCooldown;
    }

    @Override
    public Animation getAnimationIdle() {
        return animationIdle;
    }

    @Override
    public Animation getAnimationHumanIdle() {
        return animationHumanIdle;
    }

    @Override
    public Animation getAnimationSkill1() {
        return animationSkill1;
    }

    @Override
    public Animation getAnimationSkill2() {
        return animationSkill2;
    }


    private int time = 0;

    /*
        @Override
        public Animation getAnimationSkillUlt() {
    //         if timespanned < duration of ult p1
            // frameduration 1      frameduration 2         frameDuration 3
            time += 15;
            if (time > (animationDurationSkillUltP2 + animationDurationSkillUltP1) && time < animationDurationSkillUltP1 + animationDurationSkillUltP2 + animationDurationSkillUltP3) {
                return animationSkillUltP3;
            } else if (time > animationDurationSkillUltP1 && time < animationDurationSkillUltP1 + animationDurationSkillUltP2) {
                return animationSkillUltP2;
            } else if (time > 0 && time < animationDurationSkillUltP1) {
                return animationSkillUltP1;
            }

            return animationIdle;
        }*/
    public Animation getAnimationSkillUlt() {

        return animationSkillUlt;
    }

    @Override
    public SkillCost getCostSkill1() {
        return costSkill1;
    }

    @Override
    public SkillCost getCostSkill2() {
        return costSkill2;
    }

    @Override
    public SkillCost getCostSkillUlt() {
        return costSkillUlt;
    }

    @Override
    public Image getImageFaceHealthBar() {
        return imageFaceHealthBar;
    }


    public static int getFrameDurationMonsterIdle() {
        return frameDurationMonsterIdle;
    }

    public static int getFrameDurationHumanIdle() {
        return frameDurationHumanIdle;
    }
}