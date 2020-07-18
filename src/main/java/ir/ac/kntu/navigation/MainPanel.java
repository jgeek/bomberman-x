package ir.ac.kntu.navigation;

import ir.ac.kntu.Constants;
import ir.ac.kntu.InputEventHandler;
import ir.ac.kntu.Utils;
import ir.ac.kntu.components.GameBoard;
import javafx.beans.NamedArg;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainPanel extends Pane {

    private Scene scene;
    private MainMenu mainMenu;
    private GameBoard board;

    public MainPanel() {

//        this.scene = scene;

//        setPrefSize(Constants.MAIN_PANEL_WIDTH, Constants.MAIN_PANEL_HEIGHT);

//        BackgroundImage backgroundImage = new BackgroundImage(bgImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        ImageView imageView = new ImageView(Constants.BOMBERMAN_BG_IMAGE);
        imageView.setFitWidth(Constants.MAIN_PANEL_WIDTH);
        imageView.setFitHeight(Constants.MAIN_PANEL_HEIGHT);
//        imageView.setViewport(new Rectangle2D(0, 0, Constants.MAIN_PANEL_WIDTH, Constants.MAIN_PANEL_HEIGHT));
//        imageView.setPreserveRatio(true);
        getChildren().add(imageView);

        MenuItem startGame = new MenuItem("Start", event -> {
            board = Constants.getDefaultBoard();
//            board.setPrefSize(Constants.MAIN_PANEL_WIDTH, Constants.MAIN_PANEL_HEIGHT);
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

//        menu = new MenuBox(new MenuItem("RESUME GAME", MenuItem.RESUME_GAME),
//                new MenuItem("NEW GAME", MenuItem.NEW_GAME),
//                new MenuItem("SHOW SAVES", MenuItem.SHOW_SAVES),
//                new MenuItem("SCALA SORT", MenuItem.SCALA_SORT),
//                new MenuItem("JAVA SORT", MenuItem.JAVA_SORT),
//                new MenuItem("GENERATOR", MenuItem.GENERATOR),
//                new MenuItem("PLAY SAVES", MenuItem.PLAY_SAVES), new MenuItem("QUIT", MenuItem.QUIT));
//        menu.isActive = true;
//
//        menuLevels = new MenuBox(new MenuItem("LEVEL 1", MenuItem.LEVEL1),
//                new MenuItem("LEVEL 2", MenuItem.LEVEL2), new MenuItem("LEVEL 3", MenuItem.LEVEL3),
//                new MenuItem("BACK", MenuItem.BACK));
//        menuLevels.isActive = false;
//        menuLevels.setVisible(false);
//        mainRoot.getChildren().addAll(menuLevels, menu);
//
//        return mainRoot;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
