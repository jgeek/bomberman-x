package ir.ac.kntu.components;

import ir.ac.kntu.Statics;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends ImageView {

    private Image image;
    private int delay;
    private int explosionRange;

    public Bomb(GameBoard board, double x, double y, int delay, int explosionRange) {
        this.delay = delay;
        this.explosionRange = explosionRange;
        setImage(Statics.BOMB_IMAGE);
        setFitWidth(Statics.BOMB_SIZE);
        setFitHeight(Statics.BOMB_SIZE);
//        setViewport(new Rectangle2D(0, 0, 60, 60 ));
        setTranslateX(x);
        setTranslateY(y);
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getExplosionRange() {
        return explosionRange;
    }

    public void setExplosionRange(int explosionRange) {
        this.explosionRange = explosionRange;
    }
}
