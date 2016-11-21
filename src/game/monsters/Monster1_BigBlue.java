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
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Monster1_BigBlue extends Monster {

    private Animation animationIdle;
    private Animation animationSkill1;
    private Animation animationSkill2;
    private Animation animationSkillUlt;

    private Animation animationHumanIdle;

    private final SkillCost costSkill1 = new SkillCost(3,0,0,3);
    private final SkillCost costSkill2 = new SkillCost(0,7,7,0);
    private final SkillCost costSkillUlt = new SkillCost(12,12,12,12);
    private SkillCost currResources;
    // TODO enter proper duration of skill animation when sprites are done
    private static final int skill1Duration = 3000;
    private static final int skill2Duration = 3000;
    private static final int skillUltDuration = 3000;


    public Monster1_BigBlue(int playerNumber) throws SlickException {
        super();

        if (playerNumber == 1) {
            animationIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Idle P1.png", 600, 300, 1), 250);
            animationHumanIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Human P1.jpg", 600, 300, 1), 250);

            animationSkill1 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - 1 Blistol P1.png", 600, 300, 1), 250);
            animationSkill2 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - 2 Gatling P1.png", 600, 300, 1), 250);
            animationSkillUlt = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - 3 Bluezooka P1.png", 600, 300, 1), 250);

            // TODO uncomment when sprites for player2 (flipper player1 sprites) are done
//        } else if (playerNumber == 2) {
//            animationIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Idle P2.png", 600, 300, 1), 250);
//            animationHumanIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Blueffy/Blueffy - Human P2.png", 600, 300, 1), 250);

//            animationSkill1 = new Animation(new SpriteSheet("Assets/Graphics/Monster Skills Sprites/Blueffy/Blueffy - 1 Blistol P2.png", 600, 300, 1), 250);
//            animationSkill2 = new Animation(new SpriteSheet("Assets/Graphics/Monster Skills Sprites/Blueffy/Blueffy - 2 Gatling P2.png", 600, 300, 1), 250);
//            animationSkillUlt = new Animation(new SpriteSheet("Assets/Graphics/Monster Skills Sprites/Blueffy/Blueffy - 3 Bluezooka P2.png", 600, 300, 1), 250);
        }

    }

    public void skill1() {
        currResources = new SkillCost(getResourceRed(), getResourceGreen(), getResourceBlue(), getResourceYellow());
        if(costSkill1.equalTo(currResources)) {
            super.setDamage(5);
            super.setResourceRed(super.getResourceRed() - 3);
            super.setResourceYellow(super.getResourceYellow() - 3);
        }
    }

    public void skill2() {
        currResources = new SkillCost(getResourceRed(), getResourceGreen(), getResourceBlue(), getResourceYellow());
        if(costSkill2.equalTo(currResources)) {
            super.setDamage(12);
            super.setResourceRed(super.getResourceGreen() - 7);
            super.setResourceYellow(super.getResourceBlue() - 7);
        }
    }

    public void skillUlt() {
        currResources = new SkillCost(getResourceRed(), getResourceGreen(), getResourceBlue(), getResourceYellow());
        if(costSkillUlt.equalTo(currResources)) {
            super.setDamage(55);
            super.setResourceRed(super.getResourceRed() - 12);
            super.setResourceYellow(super.getResourceYellow() - 12);
            super.setResourceGreen(super.getResourceGreen() - 12);
            super.setResourceBlue(super.getResourceBlue() - 12);
        }
    }

    public int getDurationSkill1() {
        return skill1Duration;
    }

    public int getDurationSkill2() {
        return skill2Duration;
    }

    public int getDurationSkillUlt() {
        return skillUltDuration;
    }

    public Animation getAnimationSkill1() {
        return animationSkill1;
    }

    public Animation getAnimationSkill2() {
        return animationSkill2;
    }

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
    public Animation getAnimationIdle() {
        return animationIdle;
    }

    @Override
    public Animation getAnimationHumanIdle() {
        return animationHumanIdle;
    }
}