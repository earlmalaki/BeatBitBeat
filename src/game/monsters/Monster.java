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

public abstract class Monster {

    private int hp;
    private int damage;

    private int resourceBlue;
    private int resourceRed;
    private int resourceGreen;
    private int resourceYellow;



    public Monster() {
        this.hp = 100;

        resourceBlue = 0;
        resourceRed = 0;
        resourceGreen = 0;
        resourceYellow = 0;
    }

    // subtract damage points inflicted on this character
    public void takeDamage(int damage) {
        // TODO
        this.hp -= damage;
    }

    // applies damage to given defender
    public void attack(Monster defender) {
        // TODO
        defender.takeDamage(damage);
    }

    // returns true if alive
    public boolean isAlive() {
        return hp > 0 ? true : false;
    }

    public int getDamage() {
        // TODO
        return damage;
    }

    public int getHp(){
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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

    public void addResourceBlue(int res) {
        resourceBlue += res;
    }

    public void addResourceRed(int res) {
        resourceRed += res;
    }

    public void addResourceGreen(int res) {
        resourceGreen += res;
    }

    public void addResourceYellow(int res) {
        resourceYellow += res;
    }

    public boolean checkResources(SkillCost skillCost){
        return (resourceRed == skillCost.getCostRed()) && (resourceGreen == skillCost.getCostGreen()) && (resourceBlue == skillCost.getCostBlue()) && (resourceYellow == skillCost.getCostYellow());
    }


    public abstract void skill1();
    public abstract void skill2();
    public abstract void skillUlt();

    public abstract int getDurationSkill1();
    public abstract int getDurationSkill2();
    public abstract int getDurationSkillUlt();


    public abstract Animation getAnimationIdle();
    public abstract Animation getAnimationHumanIdle();

    public abstract Animation getAnimationSkill1();
    public abstract Animation getAnimationSkill2();
    public abstract Animation getAnimationSkillUlt();

    public abstract SkillCost getCostSkill1();
    public abstract SkillCost getCostSkill2();
    public abstract SkillCost getCostSkillUlt();

}
