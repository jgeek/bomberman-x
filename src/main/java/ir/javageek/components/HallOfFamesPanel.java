package ir.javageek.components;

import ir.javageek.data.DatabaseStateAware;
import ir.javageek.data.User;
import ir.javageek.navigation.MenuHolder;
import ir.javageek.service.UserService;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class HallOfFamesPanel extends StackPane implements DatabaseStateAware {

    private final UserService userService;
    private TableView<User> tableView;
    private List<User> users;

    public HallOfFamesPanel(MenuHolder mainMenu, UserService userService) {

        this.userService = userService;

        Rectangle background = new Rectangle(800, 600);
        background.setOpacity(0.2);
        DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
        shadow.setSpread(0.8);
        background.setEffect(shadow);

        tableView = new TableView();

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

        loadUsers(userService.readAll());

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

    private void loadUsers(List<User> users) {
        this.users = users;
        for (User user : users) {
            tableView.getItems().add(user);
        }
    }

    @Override
    public void update(List<User> users) {
        tableView.getItems().removeAll(this.users);
        loadUsers(users);
    }
}
