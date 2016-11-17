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

import org.newdawn.slick.Animation;

public abstract class Monster {

    private int hp;
    private int damage;

    private int resourceBlue;
    private int resourceRed;
    private int resourceGreen;
    private int resourceYellow;

    public Monster(int hp) {
        this.hp = hp;

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

    public boolean checkResources(int red, int green, int blue, int yellow){
        return (resourceRed >= red && resourceYellow >= yellow & resourceGreen >= green && resourceBlue >= blue);
    }

    public abstract void skill1();
    public abstract void skill2();
    public abstract void skillUlt();

    public abstract int getSkill1Duration();
    public abstract int getSkill2Duration();
    public abstract int getSkillUltDuration();

    public abstract Animation getSkill1Animation();
    public abstract Animation getSkill2Animation();
    public abstract Animation getSkillUltAnimation();

}
