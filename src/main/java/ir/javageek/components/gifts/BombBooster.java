package ir.javageek.components.gifts;

import ir.javageek.Constants;
import ir.javageek.Utils;
import ir.javageek.components.Bomberman;
import ir.javageek.components.GameBoard;


public class BombBooster extends Gift {

    private final GameBoard board;

    public BombBooster(GameBoard board) {
        super(Constants.BOMB_BOOSTER_IMAGE, Constants.TILE_SIZE, Constants.TILE_SIZE);
        this.board = board;
    }

    @Override
    public void consumeMe(Bomberman bomberman) {
        bomberman.getBombs().stream().filter(b -> !b.isExploded())
                .forEach(b -> b.setExplosionRange(Constants.BOMB_BOOSTED_EXPLOSION_RANGE));
        bomberman.setBombBoosted(true);
        board.removeGIft(this);

        Utils.runLater(() -> {
            bomberman.getBombs().stream().filter(b -> !b.isExploded())
                    .forEach(b -> b.setExplosionRange(Constants.BOMB_EXPLOSION_RANGE));
            bomberman.setBombBoosted(false);
        }, Constants.BOMB_BOOST_TIME);
    }
}
