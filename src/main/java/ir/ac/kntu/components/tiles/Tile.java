package ir.ac.kntu.components.tiles;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public abstract class Tile extends StackPane {

    private ImageView baseView;
//    private int width, height;
    private int row, col;

    public Tile(int row, int col, int width, int height, int x, int y) {
        this.row = row;
        this.col = col;
//        this.width = width;
//        this.height = height;
        baseView = createBaseImageView(x, y);
        baseView.setFitWidth(width);
        baseView.setFitHeight(height);
        getChildren().add(baseView);
        setTranslateX(x);
        setTranslateY(y);
    }

    protected abstract ImageView createBaseImageView(int x, int y);

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
