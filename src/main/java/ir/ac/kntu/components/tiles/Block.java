package ir.ac.kntu.components.tiles;

import ir.ac.kntu.Statics;
import javafx.scene.image.ImageView;

public class Block extends Tile {

    public Block(int row, int col, int width, int height, int x, int y) {
        super(row, col, width, height, x, y);
    }

    @Override
    protected ImageView createBaseImageView(int x, int y) {
        ImageView imageView = new ImageView(Statics.BLOCK_IMAGE);
//        imageView.setTranslateX(x);
//        imageView.setTranslateY(y);
        return imageView;
    }
}
