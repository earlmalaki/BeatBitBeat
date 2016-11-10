package game;

import org.newdawn.slick.Image;

public class Note {

    private Image image;
    private Coordinate coord;

    public Note(Image image, Coordinate coord) {
        this.image = image;
        this.coord = coord;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Coordinate getCoord() {
        return coord;
    }

    public void setCoord(Coordinate coord) {
        this.coord = coord;
    }

    public int getX() {
        return coord.getX();
    }

    public int getY() {
        return coord.getY();
    }

    public void setX(int x) {
        coord.setX(x);
    }

    public void setY(int y) {
        coord.setY(y);
    }
}
