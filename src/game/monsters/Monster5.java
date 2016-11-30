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

public class Monster5 extends Monster {

    private Animation animationIdle;
    private Animation animationSkill1;
    private Animation animationSkill2;
    private Animation animationSkillUltP1;
    private Animation animationSkillUltP2;
    private Animation animationSkillUltP3;

    private Image[] skillIcons;
    // TODO enter proper duration of skill animation when sprites are done
    private Animation animationHumanIdle;

    private final SkillCost costSkill1 = new SkillCost(3, 0, 0, 3);
    private final SkillCost costSkill2 = new SkillCost(0, 7, 7, 0);
    private final SkillCost costSkillUlt = new SkillCost(12, 12, 12, 12);
    private SkillCost currResources;

    // TODO enter proper duration of skill animation when sprites are done
    private static final int skill1Duration = 3000;
    private static final int skill2Duration = 3000;
    private static final int skillUltDuration = 3000;
    private static final int skill1Cooldown = 2000;
    private static final int skill2Cooldown = 3000;
    private static final int skillUltCooldown = 5000;


    private static final int frameDurationMonsterIdle = 260;
    private static final int frameDurationHumanIdle = 300;
    private static final int frameDurationSkill1 = 200;
    private static final int frameDurationSkill2 = 260;
    private static final int frameDurationSkillUltP1 = 100;
    private static final int frameDurationSkillUltP2 = 200;
    private static final int frameDurationSkillUltP3 = 100;

    private static final int animationDurationSkillUltP1 = 600;
    private static final int animationDurationSkillUltP2 = 4000;
    private static final int animationDurationSkillUltP3 = 600;


    public Monster5(int playerNumber) throws SlickException {
        super();

        skillIcons = new Image[]{
                new Image("Assets/Graphics/Monster and Human Sprites/Root/Root - 1 Leaf Icon.png"),
                new Image("Assets/Graphics/Monster and Human Sprites/Root/Root - 2 Whip Icon.png"),
                new Image("Assets/Graphics/Monster and Human Sprites/Root/Root - 3 Sakura Icon.png")
        };
        if (playerNumber == 1) {
            animationIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - Idle P1.png", 600, 300, 0), frameDurationMonsterIdle);
            animationHumanIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - Human P1.png", 150, 150, 0), frameDurationHumanIdle);

            animationSkill1 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - 1 Leaf P1.png", 600, 300, 0), frameDurationSkill1);
            animationSkill2 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - 2 Whip P1.png", 720, 300, 0), frameDurationSkill2);
            animationSkillUltP1 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - 3 Sakura1 P1.png", 720, 300, 0), frameDurationSkillUltP1);
            animationSkillUltP2 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - 3 Sakura2 P1.png", 720, 300, 0), frameDurationSkillUltP2);
            animationSkillUltP3 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - 3 Sakura3 P1.png", 720, 300, 0), frameDurationSkillUltP3);


            // TODO uncomment when sprites for player2 (flipper player1 sprites) are done
        } else if (playerNumber == 2) {
            animationIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - Idle P2.png", 600, 300, 0), frameDurationMonsterIdle);
            animationHumanIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - Human P2.png", 150, 150, 0), frameDurationHumanIdle);

            animationSkill1 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - 1 Leaf P2.png", 600, 300, 0), frameDurationSkill1);
            animationSkill2 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - 2 Whip P2.png", 720, 300, 0), frameDurationSkill2);
            animationSkillUltP1 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - 3 Sakura1 P2.png", 720, 300, 0), frameDurationSkillUltP1);
            animationSkillUltP2 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - 3 Sakura2 P2.png", 720, 300, 0), frameDurationSkillUltP2);
            animationSkillUltP3 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Root/Root - 3 Sakura3 P2.png", 720, 300, 0), frameDurationSkillUltP3);

        }
    }


    @Override
    public Image[] getSkillIcons() {
        return skillIcons;
    }

    public void skill1() {
        super.setDamage(5);
        super.setResourceRed(super.getResourceRed() - 3);
        super.setResourceYellow(super.getResourceYellow() - 3);
        getAnimationSkill1().restart();

    }

    public void skill2() {
        super.setDamage(12);
        super.setResourceGreen(super.getResourceGreen() - 7);
        super.setResourceBlue(super.getResourceBlue() - 7);
        getAnimationSkill2().restart();

    }

    public void skillUlt() {
        super.setDamage(55);
        super.setResourceRed(super.getResourceRed() - 12);
        super.setResourceYellow(super.getResourceYellow() - 12);
        super.setResourceGreen(super.getResourceGreen() - 12);
        super.setResourceBlue(super.getResourceBlue() - 12);
        getAnimationSkillUlt().restart();

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

    @Override
    public Animation getAnimationSkillUlt() {
//         if timespanned < duration of ult p1
        // frameduration 1      frameduration 2         frameDuration 3
        time += 15;
        if (time > (frameDurationSkillUltP2 + frameDurationSkillUltP1) && time < frameDurationSkillUltP1 + frameDurationSkillUltP2 + frameDurationSkillUltP3) {
            return animationSkillUltP3;
        } else if (time > frameDurationSkillUltP1 && time < frameDurationSkillUltP1 + frameDurationSkillUltP2) {
            return animationSkillUltP2;
        } else if (time > 0 && time < frameDurationSkillUltP1) {
            return animationSkillUltP1;
        }

        return animationIdle;
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


    public static int getFrameDurationMonsterIdle() {
        return frameDurationMonsterIdle;
    }

    public static int getFrameDurationHumanIdle() {
        return frameDurationHumanIdle;
    }
}