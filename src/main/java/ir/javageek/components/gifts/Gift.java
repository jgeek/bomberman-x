package ir.javageek.components.gifts;

import ir.javageek.components.Bomberman;
import ir.javageek.components.Positionable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Gift extends ImageView implements Positionable {

    public enum GiftType {
        BOMB_BOOSTER
    }

    private Image image;
    private int width, height;
    private int row, col;

    public Gift(Image image, int width, int height) {
        super(image);
        setFitWidth(width);
        setFitHeight(height);
    }

    public abstract void consumeMe(Bomberman bomberman);

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
