package ir.ac.kntu;

import ir.ac.kntu.components.GameBoard;
import ir.ac.kntu.navigation.MainPanel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Starter extends Application {

    private GameBoard board;
    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        if (false) {

            board = Constants.getDefaultBoard();
            board.load();
            board.startGame();
            ;
            Group root = new Group();
            MainPanel mainPanel = new MainPanel();
            root.getChildren().add(board);
            scene = new Scene(root/*, Constants.MAIN_PANEL_WIDTH, Constants.MAIN_PANEL_HEIGHT, Color.rgb(240, 240, 240)*/);
//        scene = new Scene(mainPanel/*, Constants.MAIN_PANEL_WIDTH, Constants.MAIN_PANEL_HEIGHT, Color.rgb(240, 240, 240)*/);
            mainPanel.setScene(scene);
//        Group root = new Group(board);
            scene.setOnKeyPressed(new InputEventHandler(board));
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Bomberman");
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
//        stage.setMaxHeight(Constants.MAIN_PANEL_WIDTH);
//        stage.setMaxWidth(Constants.MAIN_PANEL_HEIGHT);
            stage.show();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent event) {
                    System.exit(0);
                }
            });
        } else {
            Group root = new Group();
            MainPanel mainPanel = new MainPanel();
            scene = new Scene(mainPanel/*, Constants.MAIN_PANEL_WIDTH, Constants.MAIN_PANEL_HEIGHT, Color.rgb(240, 240, 240)*/);
            mainPanel.setScene(scene);
//        Group root = new Group(board);
//            scene.setOnKeyPressed(new InputEventHandler(board));
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Bomberman");
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
//            stage.setWidth(Constants.MAIN_PANEL_WIDTH);
//            stage.setHeight(Constants.MAIN_PANEL_HEIGHT);
            stage.show();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent event) {
                    System.exit(0);
                }
            });
        }


    }
}
