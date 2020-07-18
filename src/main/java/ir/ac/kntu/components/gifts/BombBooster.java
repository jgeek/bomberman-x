package ir.ac.kntu.components.gifts;

import ir.ac.kntu.Statics;
import ir.ac.kntu.Utils;
import ir.ac.kntu.components.Bomb;
import ir.ac.kntu.components.Bomberman;
import javafx.scene.image.Image;

public class BombBooster extends Gift {

    public BombBooster() {
        super(Statics.BOMB_BOOSTER_IMAGE, Statics.TILE_SIZE, Statics.TILE_SIZE);
    }

    @Override
    public void consumeMe(Bomberman bomberman) {
        bomberman.getBombs().stream().filter(b -> !b.isExploded())
                .forEach(b -> b.setExplosionRange(Statics.BOMB_BOOSTED_EXPLOSION_RANGE));
        Utils.runLater(() -> {
            bomberman.getBombs().stream().filter(b -> !b.isExploded())
                    .forEach(b -> b.setExplosionRange(Statics.BOMB_EXPLOSION_RANGE));
        }, Statics.BOMB_BOOST_TIME);
    }
}
