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
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Monster1_BigBlue extends Monster {

    private static final int hp = 100;

    // Possible improvement:
    // Instead of being loaded and instantiated in CharacterSelectionState,
    // idle animation could be loaded in each monster.
    // Ex:
//    private Animation animationIdle;
//    animationIdle = new Animation(new SpriteSheet("Assets/Graphics/Character Selection/Monster Display Sprite/Big Blue Sprite.png", 300, 300, 1), 250);
//    public Animation getAnimationIdle() {
//        return animationIdle;
//    }

    private Animation animationSkill1;
    private Animation animationSkill2;
    private Animation animationSkillUlt;

    // TODO enter proper duration of skill animation when sprites are done
    private static final int skill1Duration = 3000;
    private static final int skill2Duration = 3000;
    private static final int skillUltDuration = 3000;

    // TODO fix skill animation. Might need to import flipped spritesheet for P2
    // TODO may be more efficient to introduce final variables for the resource cost for each monster's skills. a getter for these values may also be helpful
    // TODO If skill costs are uniform for all the monsters, variables can be in the Monster abstract class instead

    public Monster1_BigBlue() throws SlickException {
        super(hp);

        animationSkill1 = new Animation(new SpriteSheet("Assets/Graphics/Monster Skills Sprites/Blueffy/Blueffy - 1 Blistol.png", 600, 300, 1), 250);
        animationSkill2 = new Animation(new SpriteSheet("Assets/Graphics/Monster Skills Sprites/Blueffy/Blueffy - 2 Gatling.png", 600, 300, 1), 250);
        animationSkillUlt = new Animation(new SpriteSheet("Assets/Graphics/Monster Skills Sprites/Blueffy/Blueffy - 3 Bluezooka.png", 600, 300, 1), 250);
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