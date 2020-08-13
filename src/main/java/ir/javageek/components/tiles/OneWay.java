package ir.javageek.components.tiles;

import ir.javageek.Utils;
import ir.javageek.components.Bomberman;
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
