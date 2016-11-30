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
 * - skeleton code for abstract Monster class
 * <p>
 * TO DO:
 * - Add necessary instance variables
 * - Add necessary methods
 * - Make concrete specific monster classes (6 monsters)
 * <p>
 * game.Note:
 * -
 * <p>
 * Done:
 * - skeleton code for abstract Monster class
 * <p>
 * TO DO:
 * - Add necessary instance variables
 * - Add necessary methods
 * - Make concrete specific monster classes (6 monsters)
 * <p>
 * game.Note:
 * -
 * <p>
 * Done:
 * - skeleton code for abstract Monster class
 * <p>
 * TO DO:
 * - Add necessary instance variables
 * - Add necessary methods
 * - Make concrete specific monster classes (6 monsters)
 * <p>
 * game.Note:
 * -
 * <p>
 * Done:
 * - skeleton code for abstract Monster class
 * <p>
 * TO DO:
 * - Add necessary instance variables
 * - Add necessary methods
 * - Make concrete specific monster classes (6 monsters)
 * <p>
 * game.Note:
 * -
 * <p>
 * Done:
 * - skeleton code for abstract Monster class
 * <p>
 * TO DO:
 * - Add necessary instance variables
 * - Add necessary methods
 * - Make concrete specific monster classes (6 monsters)
 * <p>
 * game.Note:
 * -
 */

/**
 * Done:
 * - skeleton code for abstract Monster class
 *
 * TO DO:
 * - Add necessary instance variables
 * - Add necessary methods
 * - Make concrete specific monster classes (6 monsters)
 *
 * game.Note:
 * -
 */

package game.monsters;

import game.SkillCost;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

public abstract class Monster {

    private int hp;
    private int damage;

    private Image[] skillIcons;
    private int resourceBlue;
    private int resourceRed;
    private int resourceGreen;
    private int resourceYellow;

    private int totalResourceBlue;
    private int totalResourceRed;
    private int totalResourceGreen;
    private int totalResourceYellow;

    private int combo;
    private int maxCombo;
    //TODO
    //add damage dealt, total resource rbgy for stats, max combo


    public Monster() {
        this.hp = 100;
        resourceBlue = 100;
        resourceRed = 100;
        resourceGreen = 100;
        resourceYellow = 100;
        combo = 0;
        maxCombo = 0;
    }

    public void doSkillCost(SkillCost skillCost) {
        resourceRed -= skillCost.getCostRed();
        resourceGreen -= skillCost.getCostGreen();
        resourceBlue -= skillCost.getCostBlue();
        resourceYellow -= skillCost.getCostYellow();
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public int getMaxCombo() {
        return maxCombo;
    }

    public void attack(Monster defender) {
        defender.takeDamage(damage);
    }

    public int getHp() {
        return hp;
    }

    public boolean isAlive(){
        return hp >= 0;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setResourceBlue(int resourceBlue) {
        this.resourceBlue = resourceBlue;
    }

    public void setResourceRed(int resourceRed) {
        this.resourceRed = resourceRed;
    }

    public void setResourceGreen(int resourceGreen) {
        this.resourceGreen = resourceGreen;
    }

    public void setResourceYellow(int resourceYellow) {
        this.resourceYellow = resourceYellow;
    }

    public int getResourceBlue() {
        return resourceBlue;
    }

    public int getResourceRed() {
        return resourceRed;
    }

    public int getResourceGreen() {
        return resourceGreen;
    }

    public int getResourceYellow() {
        return resourceYellow;
    }

    public int getTotalResourceBlue() {
        return totalResourceBlue;
    }

    public int getTotalResourceRed() {
        return totalResourceRed;
    }

    public int getTotalResourceGreen() {
        return totalResourceGreen;
    }

    public int getTotalResourceYellow() {
        return totalResourceYellow;
    }


    public void addResourceBlue(int res) {
        resourceBlue += res;
        totalResourceBlue += res;
    }

    public void addResourceRed(int res) {
        resourceRed += res;
        totalResourceRed += res;
    }

    public void addResourceGreen(int res) {
        resourceGreen += res;
        totalResourceGreen += res;
    }

    public void addResourceYellow(int res) {
        resourceYellow += res;
        totalResourceYellow += res;
    }

    public int getCombo() {

        return combo;
    }


    public void setCombo(int combo) {
        if (combo > maxCombo) {
            maxCombo = combo;
        }
        this.combo = combo;
    }

    public boolean checkResources(SkillCost skillCost) {
        return (resourceRed >= skillCost.getCostRed()) && (resourceGreen >= skillCost.getCostGreen()) && (resourceBlue >= skillCost.getCostBlue()) && (resourceYellow >= skillCost.getCostYellow());
    }

    public abstract Image[] getSkillIcons();

    public abstract void skill1();

    public abstract void skill2();

    public abstract void skillUlt();

    public abstract int getDurationSkill1();

    public abstract int getDurationSkill2();

    public abstract int getDurationSkillUlt();

    public abstract int getCooldownSkill1();

    public abstract int getCooldownSkill2();

    public abstract int getCooldownSkillUlt();

    public abstract Animation getAnimationIdle();

    public abstract Animation getAnimationHumanIdle();

    public abstract Animation getAnimationSkill1();

    public abstract Animation getAnimationSkill2();

    public abstract Animation getAnimationSkillUlt();

    public abstract SkillCost getCostSkill1();

    public abstract SkillCost getCostSkill2();

    public abstract SkillCost getCostSkillUlt();

}
