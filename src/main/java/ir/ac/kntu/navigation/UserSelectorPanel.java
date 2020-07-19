package ir.ac.kntu.navigation;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UserSelectorPanel extends StackPane {

    private final GridPane grid;

    public UserSelectorPanel(Parent parent, UserMenuItem... items) {

        Rectangle background = new Rectangle(800, 600);
        background.setOpacity(0.2);
        DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
        shadow.setSpread(0.8);
        background.setEffect(shadow);

        grid = new GridPane();
        grid.setAlignment(Pos.TOP_RIGHT);
        grid.setPadding(new Insets(85, 0, 0, 0));
        grid.setHgap(10);
        grid.setVgap(10);

        int row = 0;
        int col = 0;
        for (MenuItem item : items) {
            grid.add(item, col, row, 1, 1);
            col++;
            if (col % 5 == 0) {
                col = 0;
                row++;
            }
        }

        Button back = new Button("Back");
        back.setPrefWidth(100);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                UserSelectorPanel.this.setVisible(false);
                parent.setVisible(true);
            }
        });
        getChildren().addAll(background, grid, back);
        setTranslateX(50);
        setTranslateY(50);
    }
}
