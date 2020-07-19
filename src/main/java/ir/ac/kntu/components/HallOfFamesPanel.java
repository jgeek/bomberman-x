package ir.ac.kntu.components;

import ir.ac.kntu.data.User;
import ir.ac.kntu.navigation.MenuHolder;
import ir.ac.kntu.service.UserService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class HallOfFamesPanel extends StackPane {

    private final UserService userService;

    public HallOfFamesPanel(MenuHolder mainMenu, UserService userService) {

        this.userService = userService;

        Rectangle background = new Rectangle(800, 600);
        background.setOpacity(0.2);
        DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
        shadow.setSpread(0.8);
        background.setEffect(shadow);

        TableView<User> tableView = new TableView();

        TableColumn<User, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<User, String> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        TableColumn<User, String> gamesCol = new TableColumn<>("Games");
        gamesCol.setCellValueFactory(new PropertyValueFactory<>("games"));
        TableColumn<User, String> victoriesCol = new TableColumn<>("Victories");
        victoriesCol.setCellValueFactory(new PropertyValueFactory<>("victories"));
        TableColumn<User, String> defeatsCol = new TableColumn<>("Defeats");
        defeatsCol.setCellValueFactory(new PropertyValueFactory<>("defeats"));
        TableColumn<User, String> drawsCol = new TableColumn<>("Draws");
        drawsCol.setCellValueFactory(new PropertyValueFactory<>("draws"));

        tableView.getColumns().add(idCol);
        tableView.getColumns().add(nameCol);
        tableView.getColumns().add(scoreCol);
        tableView.getColumns().add(gamesCol);
        tableView.getColumns().add(victoriesCol);
        tableView.getColumns().add(defeatsCol);
        tableView.getColumns().add(drawsCol);

        List<User> users = userService.list();
        for (User user : users) {
            tableView.getItems().add(user);
        }

        Button back = new Button("Back");
        back.setPrefWidth(100);
        back.setOnMouseClicked(event -> {
            HallOfFamesPanel.this.setVisible(false);
            mainMenu.setVisible(true);
        });

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(0);
        vBox.getChildren().addAll(tableView, back);
        getChildren().addAll(background, vBox);
        setTranslateX(50);
        setTranslateY(50);
    }

    private Label of(String value) {
        Label label = new Label(value);
        label.setPrefWidth(100);
        return label;
    }

}
