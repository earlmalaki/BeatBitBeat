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
 * Note:
 * -
 */

package game.monsters;

public abstract class Monster {

    private int hp;
    private int mp;
    private int damage;

    private int resourceBlue;
    private int resourceRed;
    private int resourceGreen;
    private int resourceYellow;

    public Monster(int hp, int mp, int damage) {
        this.hp = hp;
        this.mp = mp;
        this.damage = damage;

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

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
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

    public void addResourceBlue() {
        resourceBlue++;
    }

    public void addResourceRed() {
        resourceRed++;
    }

    public void addResourceGreen() {
        resourceGreen++;
    }

    public void addResourceYellow() {
        resourceYellow++;
    }
}
