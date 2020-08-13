package ir.javageek;

import ir.javageek.navigation.MainPanel;
import ir.javageek.service.UserService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {

        try {
            MainPanel mainPanel = new MainPanel(new UserService());
            mainPanel.init();
            Scene scene = new Scene(mainPanel);
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
