package game;

/**
 * Created by Earl on 21/11/2016.
 */
public class SkillCost {

    private int costRed;
    private int costGreen;
    private int costBlue;
    private int costYellow;

    public SkillCost (int costRed, int costGreen, int costBlue, int costYellow) {
        this.costRed = costRed;
        this.costGreen = costGreen;
        this.costBlue = costBlue;
        this.costYellow = costYellow;
    }

    public int getCostRed() {
        return costRed;
    }

    public void setCostRed(int costRed) {
        this.costRed = costRed;
    }

    public int getCostGreen() {
        return costGreen;
    }

    public void setCostGreen(int costGreen) {
        this.costGreen = costGreen;
    }

    public int getCostBlue() {
        return costBlue;
    }

    public void setCostBlue(int costBlue) {
        this.costBlue = costBlue;
    }

    public int getCostYellow() {
        return costYellow;
    }

    public void setCostYellow(int costYellow) {
        this.costYellow = costYellow;
    }

    public boolean canSkill (SkillCost skillCost) {
        return (this.costRed >= skillCost.getCostRed()) && (this.costGreen >= skillCost.getCostGreen()) && (this.costBlue >= skillCost.getCostBlue()) && (this.costYellow >= skillCost.getCostYellow());
    }

}
