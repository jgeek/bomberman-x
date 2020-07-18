package ir.ac.kntu;

import ir.ac.kntu.components.GameBoard;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Starter extends Application {

    private GameBoard board;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        board = Statics.getDefaultBoard();
        board.load();
        Group root = new Group(board);
        Scene scene = new Scene(root, 1000, 800, Color.rgb(240, 240, 240));
        scene.setOnKeyPressed(new InputEventHandler(board));
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Bomberman");
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();

    }
}
