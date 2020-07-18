package ir.ac.kntu.components.gifts;

import ir.ac.kntu.Statics;
import ir.ac.kntu.Utils;
import ir.ac.kntu.components.Bomberman;
import ir.ac.kntu.components.GameBoard;


public class BombBooster extends Gift {

    private final GameBoard board;

    public BombBooster(GameBoard board) {
        super(Statics.BOMB_BOOSTER_IMAGE, Statics.TILE_SIZE, Statics.TILE_SIZE);
        this.board = board;
    }

    @Override
    public void consumeMe(Bomberman bomberman) {
        bomberman.getBombs().stream().filter(b -> !b.isExploded())
                .forEach(b -> b.setExplosionRange(Statics.BOMB_BOOSTED_EXPLOSION_RANGE));
        bomberman.setBombBoosted(true);
        board.removeGIft(this);

        Utils.runLater(() -> {
            bomberman.getBombs().stream().filter(b -> !b.isExploded())
                    .forEach(b -> b.setExplosionRange(Statics.BOMB_EXPLOSION_RANGE));
            bomberman.setBombBoosted(false);
        }, Statics.BOMB_BOOST_TIME);
    }
}
