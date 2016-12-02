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
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public class Monster2 extends Monster {


    private Animation animationIdle;
    private Animation animationSkill1;
    private Animation animationSkill2;
    private Animation animationSkillUlt;
    private Animation animationSpikes;
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

    // ULT skill sequence duration
//    private static final int skillUltADuration = 2400;
//    private static final int skillUltBDuration = 4760;
//    private static final int skillUltCDuration = 2400;
    private Audio monsterSfx1;
    private Audio monsterSfx2;
    private Audio monsterSfx3;
    private static final int skill1Cooldown = 5000;
    private static final int skill2Cooldown = 7000;
    private static final int skillUltCooldown = 15000;

    private static final int frameDurationMonsterIdle = 260;
    private static final int frameDurationHumanIdle = 300;
    private static final int frameDurationSkill1 = 200;
    private static final int frameDurationSkill2 = 100;
    private static final int frameDurationSkillUlt = 250;

    private static final int damageSkill1 = 15;
    private static final int damageSkill2 = 25;
    private static final int damageSkillUlt = 35;


    public Monster2(int playerNumber) throws SlickException {
        super();

        skillIcons = new Image[]{
                new Image("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - 1 Claw Icon.png"),
                new Image("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - 2 Release Icon.png"),
                new Image("Assets/Graphics/Monster and Human Sprites/Ghost/G3rd.png"),
        };
        //TODO replace with correct files
        try {
            monsterSfx1 = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/Ghost/Spectral_Dagger_2.ogg")); //ish
            monsterSfx2 = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/Ghost/Malefice.ogg")); //NOT SYNCED
            monsterSfx3 = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("Assets/Sound Effects/Ghost/Exorcism.ogg")); //    5/10

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (playerNumber == 1) {
            animationIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Idle P1.png", 600, 300, 0), frameDurationMonsterIdle);
            animationHumanIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Human P1.png", 150, 150, 0), frameDurationHumanIdle);

            animationSkill1 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - 1 Claw P1.png", 600, 300, 0), frameDurationSkill1);
            animationSkill2 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - 2 Release P1.png", 600, 300, 0), frameDurationSkill2);


            Image[] skillUlt = new Image[]{
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (1).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (2).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (3).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (4).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (5).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (6).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (7).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (8).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (9).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (10).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (11).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (12).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (13).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (14).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (15).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (16).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (17).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (18).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (19).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (20).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (21).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (22).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (23).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (24).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (25).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p1/ghost ult (26).png"),
            };

            int[] duration = new int[]{
                    200,
                    200,
                    200,
                    200,
                    200,
                    200, //6
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200


            };

            Image[] skillUltSpikes = new Image[]{
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (1).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (2).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (3).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (4).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (5).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (6).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (7).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (8).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (9).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (10).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (11).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (12).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (13).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (14).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (15).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (16).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (17).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (18).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (19).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (20).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (21).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (22).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (23).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (24).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (25).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (26).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (27).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (28).png"),
            };

            int[] durationSpikes = new int[]{
                    200,
                    200,
                    200,
                    200,
                    200,
                    200, //6
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200, //26
                    200,
                    200
            };

            animationSkillUlt = new Animation(skillUlt, duration);

            animationSpikes = new Animation(skillUltSpikes, durationSpikes);
        } else if (playerNumber == 2) {
            animationIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Idle P2.png", 600, 300, 0), frameDurationMonsterIdle);
            animationHumanIdle = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - Human P2.png", 150, 150, 0), frameDurationHumanIdle);

            animationSkill1 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - 1 Claw P2.png", 600, 300, 0), frameDurationSkill1);
            animationSkill2 = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost - 2 Release P2.png", 600, 300, 0), frameDurationSkill2);
            animationSkillUlt = new Animation(new SpriteSheet("Assets/Graphics/Monster and Human Sprites/Ghost/Ghost 3Portal Out P2.png", 600, 300, 0), frameDurationSkillUlt);
            Image[] skillUlt = new Image[]{
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (1).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (2).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (3).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (4).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (5).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (6).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (7).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (8).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (9).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (10).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (11).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (12).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (13).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (14).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (15).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (16).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (17).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (18).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (19).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (20).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (21).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (22).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (23).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/p2/ghost ult (24).png"),
            };

            int[] duration = new int[]{
                    200,
                    200,
                    200,
                    200,
                    200,
                    200, //6
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200


            };

            Image[] skillUltSpikes = new Image[]{
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (1).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (2).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (3).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (4).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (5).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (6).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (7).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (8).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (9).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (10).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (11).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (12).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (13).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (14).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (15).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (16).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (17).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (18).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (19).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (20).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (21).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (22).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (23).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (24).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (25).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (26).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (27).png"),
                    new Image("Assets/Graphics/Monster and Human Sprites/Ghost/ghost3skillult/ghost 3 spikes/ghost spikes (28).png"),
            };

            int[] durationSpikes = new int[]{
                    200,
                    200,
                    200,
                    200,
                    200,
                    200, //6
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200,
                    200, //26
                    200,
                    200
            };

            animationSkillUlt = new Animation(skillUlt, duration);

            animationSpikes = new Animation(skillUltSpikes, durationSpikes);
        }

    }

    @Override
    public Image[] getSkillIcons() {
        return skillIcons;
    }

    public void skill1() {
        super.setDamage(damageSkill1);
        super.doSkillCost(costSkill1);
        getAnimationSkill1().restart();
        monsterSfx1.playAsSoundEffect(1.0f, 1.0f, false);
    }

    public void skill2() {
        super.setDamage(damageSkill2);
        super.doSkillCost(costSkill2);
        getAnimationSkill2().restart();
        monsterSfx2.playAsSoundEffect(1.0f, 1.0f, false);

    }

    public void skillUlt() {
        super.setDamage(damageSkillUlt);
        super.doSkillCost(costSkillUlt);
        getAnimationSkillUlt().restart();
        getAnimationSpikes().restart();
        monsterSfx3.playAsSoundEffect(1.0f, 1.0f, false);
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

    public Animation getAnimationSpikes() {
        return animationSpikes;
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
