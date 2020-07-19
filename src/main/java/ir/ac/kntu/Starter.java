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

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {

        MainPanel mainPanel = null;
        try {
            mainPanel = new MainPanel();
            Scene scene = new Scene(mainPanel/*, Constants.MAIN_PANEL_WIDTH, Constants.MAIN_PANEL_HEIGHT, Color.rgb(240, 240, 240)*/);
            mainPanel.setScene(scene);
            // stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Bomberman");
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> System.exit(0));
            stage.show();
        } catch (Exception e) {
            System.out.println("system error happened");
            e.printStackTrace();
        }
    }
}
