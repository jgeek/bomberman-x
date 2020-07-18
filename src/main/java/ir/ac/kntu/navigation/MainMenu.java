package ir.ac.kntu.navigation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MainMenu extends StackPane {

    public MainMenu(MenuItem... items) {

        Rectangle background = new Rectangle(400, 300);
        background.setOpacity(0.2);
        DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
        shadow.setSpread(0.8);
        background.setEffect(shadow);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_RIGHT);
        vbox.setPadding(new Insets(85, 0, 0, 0));
        vbox.getChildren().addAll(items);

        getChildren().addAll(background, vbox);
    }
}
