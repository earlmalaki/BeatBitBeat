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

public class Monster2 extends Monster {


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
    private static final int skill1Duration = 3400;
    private static final int skill2Duration = 2700;
    private static final int skillUltDuration = 6000;

    private static final int skill1Cooldown = 5000;
    private static final int skill2Cooldown = 7000;
    private static final int skillUltCooldown = 15000;

    private static final int frameDurationMonsterIdle = 260;
    private static final int frameDurationHumanIdle = 300;
    private static final int frameDurationSkill1 = 200;
    private static final int frameDurationSkill2 = 100;
    private static final int frameDurationSkillUlt = 250;


    public Monster2(int playerNumber) throws SlickException {
        super();

        skillIcons = new Image[]{
                new Image("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - 1 Claw Icon.png"),
                new Image("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - 2 Release Icon.png"),
                new Image("Assets/Graphics/Monster and Human Sprites/Ghost/G3rd.png"),
        };
        if (playerNumber == 1) {
            animationIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Idle P1.png", 600, 300, 0), frameDurationMonsterIdle);
            animationHumanIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Human P1.png", 150, 150, 0), frameDurationHumanIdle);

            animationSkill1 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - 1 Claw P1.png", 600, 300, 0), frameDurationSkill1);
            animationSkill2 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - 2 Release P1.png", 600, 300, 0), frameDurationSkill2);
            animationSkillUlt = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - 3 Release P1.png", 600, 300, 0), frameDurationSkillUlt);

        } else if (playerNumber == 2) {
            animationIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Idle P2.png", 600, 300, 0), frameDurationMonsterIdle);
            animationHumanIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Human P2.png", 150, 150, 0), frameDurationHumanIdle);

            animationSkill1 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - 1 Claw P2.png", 600, 300, 0), frameDurationSkill1);
            animationSkill2 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - 2 Release P2.png", 600, 300, 0), frameDurationSkill2);
            animationSkillUlt = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - 3 Release P2.png", 600, 300, 0), frameDurationSkillUlt);
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

    }

    public void skill2() {

        super.setDamage(10);
        super.setResourceGreen(super.getResourceGreen() - 7);
        super.setResourceBlue(super.getResourceBlue() - 7);

    }

    public void skillUlt() {
        super.setDamage(25);
        super.setResourceRed(super.getResourceRed() - 12);
        super.setResourceYellow(super.getResourceYellow() - 12);
        super.setResourceGreen(super.getResourceGreen() - 12);
        super.setResourceBlue(super.getResourceBlue() - 12);

    }

    @Override
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

    @Override
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

    public static int getFrameDurationMonsterIdle() {
        return frameDurationMonsterIdle;
    }

    public static int getFrameDurationHumanIdle() {
        return frameDurationHumanIdle;
    }
}
