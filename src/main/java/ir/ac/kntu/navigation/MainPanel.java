package ir.ac.kntu.navigation;

import ir.ac.kntu.Constants;
import ir.ac.kntu.InputEventHandler;
import ir.ac.kntu.components.GameBoard;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class MainPanel extends Pane {

    private Scene scene;
    private MainMenu mainMenu;
    private GameBoard board;

    public MainPanel() {

        ImageView imageView = new ImageView(Constants.BOMBERMAN_BG_IMAGE);
        imageView.setFitWidth(Constants.MAIN_PANEL_WIDTH);
        imageView.setFitHeight(Constants.MAIN_PANEL_HEIGHT);
        getChildren().add(imageView);
        setPrefSize(Constants.MAIN_PANEL_WIDTH, Constants.MAIN_PANEL_HEIGHT);

//        setWidth(Constants.MAIN_PANEL_WIDTH);
//        setHeight(Constants.MAIN_PANEL_HEIGHT);

        MenuItem startGame = new MenuItem("Start", event -> {
            board = Constants.getDefaultBoard();
            board.setMainPanel(this);
            board.load();
            board.setScene(scene);
            scene.setRoot(board);
            scene.setOnKeyPressed(new InputEventHandler(board));
            board.startGame();
        });
        MenuItem exit = new MenuItem("Exit", event -> {
            System.exit(0);
        });

        mainMenu = new MainMenu(startGame, exit);

        mainMenu.setTranslateX(50);
        mainMenu.setTranslateY(400);
        getChildren().add(mainMenu);

    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

}
