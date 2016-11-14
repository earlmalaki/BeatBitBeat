/**
 * Albores, Allyssa
 * Bedio, Aiden Justin
 * Malaki, Earl Timothy
 * Paler, Timothy River
 *
 * BSCS - II | UP - Cebu
 * CMSC22 - OOP
 * Final Project
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

public class Monster3 extends Monster {

    private static final int hp = 100;
    private static final int damage = 0;

    // TODO enter proper duration of skill animation when sprites are done
    private static final int skill1Duration = 0;
    private static final int skill2Duration = 0;
    private static final int skillUltDuration = 0;

    public Monster3() {
        super(hp, damage, skill1Duration, skill2Duration, skillUltDuration);
    }

    public void skill1 () {
        super.setDamage(5);
        super.setResourceRed(super.getResourceRed() - 3);
        super.setResourceYellow(super.getResourceYellow() - 3);
    }

    public void skill2 () {
        super.setDamage(12);
        super.setResourceRed(super.getResourceRed() - 7);
        super.setResourceYellow(super.getResourceYellow() - 7);

    }

    public void skillUlt () {
        super.setDamage(55);
        super.setResourceRed(super.getResourceRed() - 12);
        super.setResourceYellow(super.getResourceYellow() - 12);
        super.setResourceGreen(super.getResourceGreen() - 12);
        super.setResourceBlue(super.getResourceBlue() - 12);
    }

}
