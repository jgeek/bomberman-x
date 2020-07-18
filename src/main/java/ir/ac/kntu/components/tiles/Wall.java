package ir.ac.kntu.components.tiles;

import ir.ac.kntu.Statics;
import ir.ac.kntu.components.Bomberman;
import javafx.scene.image.ImageView;

public class Wall extends Tile {

    public Wall(int row, int col, double width, double height, double x, double y) {
        super(row, col, width, height, x, y);
    }

    @Override
    protected ImageView createBaseImageView() {
        return new ImageView(Statics.WALL_IMAGE);
    }

    @Override
    public boolean canPassThrow(Bomberman.Direction direction) {
        return false;
    }
}
