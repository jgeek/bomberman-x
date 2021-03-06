package ir.javageek.components.tiles;

import ir.javageek.Constants;
import ir.javageek.components.Bomberman;
import javafx.scene.image.ImageView;

public class FreeSpace extends Tile {

    public FreeSpace(int row, int col, double width, double height, double x, double y) {
        super(row, col, width, height, x, y);
        setCanPassThrow(true);
    }

    @Override
    protected ImageView createBaseImageView() {
        ImageView imageView = new ImageView(Constants.FREE_SPACE_IMAGE);
//        imageView.setTranslateX(x);
//        imageView.setTranslateY(y);
        return imageView;
    }

    @Override
    public boolean canPassThrow(Bomberman.Direction direction) {
        return true;
    }

    @Override
    public boolean isExplodable() {
        return true;
    }

//    public static FreeSpace fromTile(Tile tile) {
//        FreeSpace fs = new FreeSpace(tile.getRow(), tile.getCol(), tile.getWidth(), tile.getHeight(), tile.getTranslateX(), tile.getTranslateY());
//        fs.init();
//        return fs;
//    }
}
