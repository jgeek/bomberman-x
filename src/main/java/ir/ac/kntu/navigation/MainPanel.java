package ir.ac.kntu.navigation;

import ir.ac.kntu.Constants;
import ir.ac.kntu.InputEventHandler;
import ir.ac.kntu.components.GameBoard;
import ir.ac.kntu.components.data.GameMap;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.List;

public class MainPanel extends Pane {

    private List<GameMap> maps;
    private Scene scene;
    private MenuHolder mainMenu;
    private MenuHolder gameMenu;
    private MenuHolder mapMenu;
    private GameBoard board;
    private GameMap selectMap = Constants.maps.get(0);

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
//            board = Constants.getDefaultBoard();
            board = new GameBoard(selectMap.getName(), selectMap.getData());
            board.setMainPanel(this);
            board.load();
            board.setScene(scene);
            HBox box = new HBox();
            scene.setRoot(box);
            scene.setOnKeyPressed(new InputEventHandler(board));

//            scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<Event>() {
//                @Override
//                public void handle(Event event) {
//                    System.out.println(event);
//                }
//            });
            box.getChildren().add(board);
            board.startGame();
        });
        MenuItem playersItem = new MenuItem("Players", event -> {
            System.exit(0);
        });
        MenuItem mapItem = new MenuItem("Map", event -> {
            gameMenu.setVisible(false);
            mapMenu.setVisible(true);
        });
        MenuItem backItem = new MenuItem("Back", event -> {
            gameMenu.setVisible(false);
            mainMenu.setVisible(true);
        });
        gameMenu = new MenuHolder(playItem, playersItem, mapItem, backItem);
        gameMenu.setTranslateX(50);
        gameMenu.setTranslateY(400);
        gameMenu.setVisible(false);

        mapMenu = createMapMenu();

        getChildren().addAll(mainMenu, gameMenu, mapMenu);


    }

    private MenuHolder createMapMenu() {

        MenuHolder mapMenu = new MenuHolder();
        List<GameMap> maps = Constants.maps;
        if (maps == null) {
            return mapMenu;
        }
        for (GameMap map : maps) {
            mapMenu.addItem(new MapMenuItem(map, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    MainPanel.this.selectMap = map;
                    MainPanel.this.mapMenu.setVisible(false);
                    MainPanel.this.gameMenu.setVisible(true);
                }
            }));
        }
        mapMenu.setVisible(false);
        return mapMenu;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

}
