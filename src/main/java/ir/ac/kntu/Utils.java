package ir.ac.kntu;

import ir.ac.kntu.components.Bomberman;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Utils {

    public static Image loadImage(String name) {
        return new Image(Objects.requireNonNull(Utils.class.getClassLoader().getResourceAsStream(name)));
    }

    public static ImageView loadBomberManView(Bomberman.SYSTEM_NAMES name, Bomberman.Direction direction, Bomberman.State state) {
        String fileName = String.format("assets/player_%s/player_%s_%s_%s.png",
                name.toString().toLowerCase(),
                name.toString().toLowerCase(),
                direction.toString().toLowerCase(), state.toString().toLowerCase());
        ImageView imageView = new ImageView(loadImage(fileName));
        imageView.setFitHeight(Statics.BOMBERMAN_HEIGHT);
        imageView.setFitWidth(Statics.BOMBERMAN_WIDTH);
        return imageView;
    }

    public static ImageView loadOneWayView(Bomberman.Direction direction) {
        String fileName = String.format("assets/map/oneway/oneway_%s.png", direction.toString().toLowerCase());
        return new ImageView(loadImage(fileName));
    }

    public static void runLater(Runnable runnable, int delay) {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(() -> {
                    runnable.run();
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, delay);
    }
}
