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

import org.newdawn.slick.Animation;

public class Monster6 extends Monster {

    private static final int hp = 100;

    private Animation animationSkill1;
    private Animation animationSkill2;
    private Animation animationSkillUlt;

    // TODO enter proper duration of skill animation when sprites are done
    private static final int skill1Duration = 0;
    private static final int skill2Duration = 0;
    private static final int skillUltDuration = 0;

    public Monster6() {
        super(hp);
    }

    public void skill1() {
        super.setDamage(5);
        super.setResourceRed(super.getResourceRed() - 3);
        super.setResourceYellow(super.getResourceYellow() - 3);
    }

    public void skill2() {
        super.setDamage(12);
        super.setResourceRed(super.getResourceRed() - 7);
        super.setResourceYellow(super.getResourceYellow() - 7);

    }

    public void skillUlt() {
        super.setDamage(55);
        super.setResourceRed(super.getResourceRed() - 12);
        super.setResourceYellow(super.getResourceYellow() - 12);
        super.setResourceGreen(super.getResourceGreen() - 12);
        super.setResourceBlue(super.getResourceBlue() - 12);
    }

    public int getSkill1Duration() {
        return skill1Duration;
    }

    public int getSkill2Duration() {
        return skill2Duration;
    }

    public int getSkillUltDuration() {
        return skillUltDuration;
    }

    public Animation getSkill1Animation() {
        return animationSkill1;
    }

    public Animation getSkill2Animation() {
        return animationSkill2;
    }

    public Animation getSkillUltAnimation() {
        return animationSkillUlt;
    }

}
