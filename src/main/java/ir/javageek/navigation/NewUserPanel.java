package ir.javageek.navigation;

import ir.javageek.data.User;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class NewUserPanel extends StackPane {

    private final TextField text;
    private final UserSelectorPanel parent;

    public NewUserPanel(UserSelectorPanel parent) {

        this.parent = parent;
        Rectangle background = new Rectangle(300, 150);
        background.setOpacity(.4);
//        DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
//        shadow.setSpread(0.8);
//        background.setEffect(shadow);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 0, 0, 0));
        grid.setHgap(10);
        grid.setVgap(10);

        Label label = new Label("Name:");
        label.setTextFill(Color.WHITE);
        text = new TextField("");
        text.setPrefWidth(200);

        Button submit = new Button("Submit");
        submit.setDefaultButton(true);
        submit.setOnMousePressed(event -> {
            save();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnMousePressed(event -> {
            setVisible(false);
        });

        grid.add(label, 0, 0, 1, 1);
        grid.add(text, 1, 0, 1, 1);
        grid.add(cancel, 0, 1, 1, 1);
        grid.add(submit, 1, 1, 1, 1);

        getChildren().addAll(background, grid);
        text.requestFocus();

        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    save();
                }
            }
        });

    }

    private void save() {
        if (text.getText().length() == 0) {
            System.out.println("enter the name");
            return;
        }
        User user = new User(text.getText());
        parent.addUser(user);
        setVisible(false);
    }
}
