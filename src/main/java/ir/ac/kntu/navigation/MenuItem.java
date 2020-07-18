package ir.ac.kntu.navigation;

import ir.ac.kntu.Constants;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuItem extends StackPane {


    private String title;

    public MenuItem(String title, EventHandler<MouseEvent> mouseHandler) {
        this.title = title;
        setOnMousePressed(mouseHandler);

        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.BLACK), new Stop(0.2, Color.YELLOW));
        Rectangle rect = new Rectangle(Constants.MENU_ITEM_WIDTH, Constants.MENU_ITEM_HEIGHT);
        rect.setFill(gradient);
//        rect.setOpacity(.5);
        rect.setVisible(false);

        Text text = new Text(title);
        text.setFont(Font.font(20));
        text.setFill(Color.YELLOW);

        setOnMouseEntered(event -> {
            rect.setVisible(true);
            text.setFill(Color.RED);
        });

        setOnMouseExited(event -> {
            rect.setVisible(false);
            text.setFill(Color.YELLOW);
        });


        getChildren().addAll(rect, text);
    }
}
