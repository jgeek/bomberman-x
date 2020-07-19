package ir.ac.kntu.navigation;

import ir.ac.kntu.Constants;
import ir.ac.kntu.InputEventHandler;
import ir.ac.kntu.components.GameBoard;
import ir.ac.kntu.components.GameMap;
import ir.ac.kntu.service.MapProvider;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.List;

public class MainPanel extends Pane {

    private Scene scene;
    private MenuHolder mainMenu;
    private MenuHolder gameMenu;
    private GameBoard board;

    public MainPanel() {

        ImageView imageView = new ImageView(Constants.BOMBERMAN_BG_IMAGE);
        imageView.setFitWidth(Constants.MAIN_PANEL_WIDTH);
        imageView.setFitHeight(Constants.MAIN_PANEL_HEIGHT);
        getChildren().add(imageView);
        setPrefSize(Constants.MAIN_PANEL_WIDTH, Constants.MAIN_PANEL_HEIGHT);

        MenuItem startItem = new MenuItem("Start", event -> {
            gameMenu.setVisible(true);
            mainMenu.setVisible(false);
        });
        MenuItem exit = new MenuItem("Exit", event -> {
            System.exit(0);
        });

        mainMenu = new MenuHolder(startItem, exit);

        mainMenu.setTranslateX(50);
        mainMenu.setTranslateY(400);

        MenuItem playItem = new MenuItem("Play", event -> {
            board = Constants.getDefaultBoard();
            board.setMainPanel(this);
            board.load();
            board.setScene(scene);
            scene.setRoot(board);
            scene.setOnKeyPressed(new InputEventHandler(board));
            board.startGame();
        });
        MenuItem playersItem = new MenuItem("Players", event -> {
            System.exit(0);
        });
        MenuItem mapItem = new MenuItem("Map", event -> {
            try {
                List<GameMap> maps = new MapProvider().list();
                System.out.println("Maps loaded");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
        MenuItem backItem = new MenuItem("Back", event -> {
            gameMenu.setVisible(false);
            mainMenu.setVisible(true);
        });
        gameMenu = new MenuHolder(playItem, playersItem, mapItem, backItem);
        gameMenu.setTranslateX(50);
        gameMenu.setTranslateY(400);
        gameMenu.setVisible(false);


        getChildren().addAll(mainMenu, gameMenu);


    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

}
