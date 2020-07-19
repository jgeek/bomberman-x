package ir.ac.kntu.navigation;

import ir.ac.kntu.data.User;
import ir.ac.kntu.service.InformNewUser;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class NewUserPanel extends StackPane {

    public NewUserPanel(UserSelectorPanel parent) {

        Rectangle background = new Rectangle(300, 150);
        background.setOpacity(.9);
        DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
        shadow.setSpread(0.8);
        background.setEffect(shadow);

        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.TOP_RIGHT);
        grid.setPadding(new Insets(85, 0, 0, 0));
        grid.setHgap(10);
        grid.setVgap(10);
        Label label = new Label("Name:");

        TextField text = new TextField("");
        text.setPrefWidth(200);

        Button submit = new Button("Submit");
        submit.setOnMousePressed(event -> {
            if (text.getText().length() == 0) {
                System.out.println("enter the name");
                return;
            }
            User user = new User(text.getText());
            parent.addUser(user);
//            userLovers.forEach(ul -> ul.hello(user));
            setVisible(false);
//            parent.setVisible(true);
        });

        Button cancel = new Button("Cancel");
        cancel.setOnMousePressed(event -> {
            setVisible(false);
//            parent.setVisible(true);
        });

        grid.add(label, 0, 0, 1, 1);
        grid.add(text, 1, 0, 1, 1);
        grid.add(cancel, 0, 1, 1, 1);
        grid.add(submit, 1, 1, 1, 1);

        getChildren().addAll(background, grid);

    }
}
