package ir.ac.kntu.components;

import ir.ac.kntu.data.User;
import ir.ac.kntu.navigation.MenuHolder;
import ir.ac.kntu.navigation.UserSelectorPanel;
import ir.ac.kntu.service.UserService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class HallOfFames extends StackPane {

    private final UserService userService;
    private GridPane grid;

    public HallOfFames(MenuHolder mainMenu, UserService userService) {

        this.userService = userService;

        Rectangle background = new Rectangle(800, 600);
        background.setOpacity(0.2);
        DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
        shadow.setSpread(0.8);
        background.setEffect(shadow);

        grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setPadding(new Insets(20, 0, 0, 0));
        grid.setHgap(10);
        grid.setVgap(10);

        List<User> users = userService.list();
        int row = 0;
        for (User user : users) {
            grid.add(of(user.getId() + ""), 0, row, 1, 1);
            grid.add(of(user.getName()), 1, row, 1, 1);
            grid.add(of(user.getGames() + ""), 2, row, 1, 1);
            grid.add(of(user.getVictories() + ""), 3, row, 1, 1);
            grid.add(of(user.getDefeats() + ""), 4, row, 1, 1);
            grid.add(of(user.getDraws() + ""), 5, row, 1, 1);
            row++;
        }

        Button back = new Button("Back");
        back.setPrefWidth(100);
        back.setOnMouseClicked(event -> {
            HallOfFames.this.setVisible(false);
            mainMenu.setVisible(true);
        });

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(0);
        vBox.getChildren().addAll(grid, back);
//        getChildren().addAll(background, vBox);

        setTranslateX(50);
        setTranslateY(50);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(background, vBox);
//
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(stackPane);
        getChildren().add(scroll);
//        setContent(stackPane);

    }

    private Label of(String value) {
        Label label = new Label(value);
        label.setPrefWidth(100);
        return label;
    }

}
