package ir.javageek.components.gifts;

import ir.javageek.Constants;
import ir.javageek.components.Bomberman;
import ir.javageek.components.GameBoard;

/**
 * https://www.iconsdb.com/guacamole-green-icons/bomb-3-icon.html
 */
public class BombAdder extends Gift {

    private final GameBoard board;

    public BombAdder(GameBoard board) {
        super(Constants.BOMB_ADDER_IMAGE, Constants.TILE_SIZE, Constants.TILE_SIZE);
        this.board = board;
    }

    @Override
    public void consumeMe(Bomberman bomberman) {

        bomberman.setMaxBombs(bomberman.getMaxBombs() + 1);
        board.removeGIft(this);
    }
}
