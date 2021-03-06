package ir.javageek.components.tiles;

import ir.javageek.Constants;
import ir.javageek.components.Bomberman;
import javafx.scene.image.ImageView;

public class Block extends Tile {

    public Block(int row, int col, double width, double height, double x, double y) {
        super(row, col, width, height, x, y);
    }

    public void init() {
        ImageView baseView = createBaseImageView();
        baseView.setFitWidth(getWidth());
        baseView.setFitHeight(getHeight());
        ImageView fs = new ImageView(Constants.FREE_SPACE_IMAGE);
        fs.setFitWidth(getWidth());
        fs.setFitHeight(getHeight());
        fs.setVisible(false);
        getChildren().addAll(baseView, fs);
    }

    @Override
    protected ImageView createBaseImageView() {
        return new ImageView(Constants.BLOCK_IMAGE);
    }

    @Override
    public boolean canPassThrow(Bomberman.Direction direction) {
        return canPassThrow;
    }

    @Override
    public boolean isExplodable() {
        return true;
    }
}
