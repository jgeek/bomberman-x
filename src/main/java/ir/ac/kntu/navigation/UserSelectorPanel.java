package ir.ac.kntu.navigation;

import ir.ac.kntu.components.GameOverPanel;
import ir.ac.kntu.data.User;
import ir.ac.kntu.service.InformNewUser;
import ir.ac.kntu.service.UserService;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;

public class UserSelectorPanel extends StackPane {

    private final UserService userService;
    private final GridPane grid;
    private final List<User> selectedUsers;
    private int row;
    private int col;

    public UserSelectorPanel(UserService userService, Parent parent, List<User> selectedUsers) {

        this.userService = userService;
        this.selectedUsers = selectedUsers;
        Rectangle background = new Rectangle(800, 600);
        background.setOpacity(0.2);
        DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
        shadow.setSpread(0.8);
        background.setEffect(shadow);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(0);

        grid = new GridPane();
        grid.setAlignment(Pos.TOP_RIGHT);
        grid.setPadding(new Insets(20, 0, 0, 0));
        grid.setHgap(10);
        grid.setVgap(10);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(30, 0, 0, 0));
        Button back = new Button("Back");
        back.setPrefWidth(100);
        back.setOnMouseClicked(event -> {
            UserSelectorPanel.this.setVisible(false);
            parent.setVisible(true);
        });
        Button newPlayer = new Button("New Player");
        newPlayer.setPrefWidth(120);
        newPlayer.setOnMouseClicked(event -> {
            NewUserPanel userPanel = new NewUserPanel(UserSelectorPanel.this);
            UserSelectorPanel.this.getChildren().add(userPanel);
        });

        hBox.getChildren().addAll(back, newPlayer);
        vBox.getChildren().addAll(grid, hBox);
        getChildren().addAll(background, vBox);
        setTranslateX(50);
        setTranslateY(50);
    }

    public void addItem(UserMenuItem item) {
        item.setSelectedUsers(selectedUsers);
        grid.add(item, col, row, 1, 1);
        col++;
        if (col % 5 == 0) {
            col = 0;
            row++;
        }
    }

    public void addUser(User user) {
        addItem(new UserMenuItem(user));
        try {
            userService.add(user);
        } catch (IOException e) {
            System.out.println(String.format("saving %s failed", user));
            e.printStackTrace();
        }
    }
}
