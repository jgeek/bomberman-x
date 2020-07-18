package ir.ac.kntu.components.gifts;

import ir.ac.kntu.components.Bomberman;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Gift extends ImageView {

    private Image image;
    private int width, height;

    public Gift(Image image, int width, int height) {
        super(image);
        setFitWidth(width);
        setFitHeight(height);
    }

    public abstract void consumeMe(Bomberman bomberman);
}
