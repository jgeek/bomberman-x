package ir.ac.kntu.components.tiles;

import ir.ac.kntu.Utils;
import ir.ac.kntu.components.Bomberman;
import javafx.scene.image.ImageView;

public class OneWay extends Tile {

    private Bomberman.Direction direction;
    private char code;

    public OneWay(int row, int col, double width, double height, double x, double y, char code) {
        super(row, col, width, height, x, y);
        this.code = code;
        this.direction = Bomberman.Direction.fromAbbr(code);
    }

    @Override
    protected ImageView createBaseImageView() {
        return Utils.loadOneWayView(direction);
    }


    @Override
    public boolean canPassThrow(Bomberman.Direction direction) {
        return this.direction == direction;
    }
}
