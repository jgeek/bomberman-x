package ir.ac.kntu.components.gifts;

import ir.ac.kntu.Statics;
import ir.ac.kntu.components.Bomberman;
import ir.ac.kntu.components.GameBoard;

public class BombAdder extends Gift {

    private final GameBoard board;

    public BombAdder(GameBoard board) {
        super(Statics.BOMB_ADDER_IMAGE, Statics.TILE_SIZE, Statics.TILE_SIZE);
        this.board = board;
    }

    @Override
    public void consumeMe(Bomberman bomberman) {

        bomberman.setMaxBombs(bomberman.getMaxBombs() + 1);
        board.removeGIft(this);
    }
}
