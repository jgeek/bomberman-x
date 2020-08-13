package ir.javageek.navigation;

import ir.javageek.Constants;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuItem extends StackPane {

    protected final Rectangle rect;
    protected final String title;
    protected final Text text;
    protected boolean ignoreEffect = false;

    public MenuItem(String title) {
        this.title = title;

        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.BLACK), new Stop(0.2, Color.YELLOW));
        rect = getItemRectangle();
        rect.setFill(gradient);
//        rect.setOpacity(.5);
        rect.setVisible(false);

        text = new Text(title);
        text.setFont(Font.font(20));
        text.setFill(Color.YELLOW);

        setOnMouseEntered(event -> {
            if (ignoreEffect)
                return;
            rect.setVisible(true);
            text.setFill(Color.RED);
        });

        setOnMouseExited(event -> {
            if (ignoreEffect)
                return;
            rect.setVisible(false);
            text.setFill(Color.YELLOW);
        });
        getChildren().addAll(rect, text);
    }

    protected Rectangle getItemRectangle() {
        return new Rectangle(Constants.MENU_ITEM_WIDTH, Constants.MENU_ITEM_HEIGHT);
    }

    public void setIgnoreEffect(boolean ignoreEffect) {
        this.ignoreEffect = ignoreEffect;
    }
}
