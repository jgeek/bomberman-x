package ir.ac.kntu.components.tiles;

import ir.ac.kntu.Statics;
import javafx.scene.image.ImageView;

public class FreeSpace extends Tile {

    public FreeSpace(int row, int col, int width, int height, int x, int y) {
        super(row, col, width, height, x, y);
    }

    @Override
    protected ImageView createBaseImageView(int x, int y) {
        ImageView imageView = new ImageView(Statics.FREE_SPACE_IMAGE);
//        imageView.setTranslateX(x);
//        imageView.setTranslateY(y);
        return imageView;
    }
}
