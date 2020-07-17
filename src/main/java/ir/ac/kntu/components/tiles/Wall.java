package ir.ac.kntu.components.tiles;

import ir.ac.kntu.Statics;
import ir.ac.kntu.components.Bomberman;
import javafx.scene.image.ImageView;

public class Wall extends Tile {

    public Wall(int row, int col, int width, int height, int x, int y) {
        super(row, col, width, height, x, y);
    }

    @Override
    protected ImageView createBaseImageView(int x, int y) {
        ImageView imageView = new ImageView(Statics.WALL_IMAGE);
//        imageView.setTranslateX(x);
//        imageView.setTranslateY(y);
        return imageView;
    }

    @Override
    public boolean canPassThrow(Bomberman.Direction direction) {
        return false;
    }
}
