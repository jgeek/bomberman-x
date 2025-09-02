package ir.javageek.navigation;

import ir.javageek.Constants;
import ir.javageek.InputEventHandler;
import ir.javageek.components.GameBoard;
import ir.javageek.components.HallOfFamesPanel;
import ir.javageek.data.GameMap;
import ir.javageek.data.User;
import ir.javageek.service.UserService;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class MainPanel extends Pane {

    private HallOfFamesPanel hallOfFamesPanel;
    private List<GameMap> maps;
    private Scene scene;
    private MenuHolder mainMenu;
    private MenuHolder gameMenu;
    private MenuHolder mapMenu;
    private UserSelectorPanel playersPanel;
    private GameBoard board;
    private GameMap selectMap = Constants.maps.get(0);
    private List<User> users;
    private List<User> selectedUsers = new ArrayList<>();

    private UserService userService;

    public MainPanel(UserService userService) {
        this.userService = userService;
    }

    public void init() {
        ImageView imageView = new ImageView(Constants.BOMBERMAN_BG_IMAGE);
        imageView.setFitWidth(Constants.MAIN_PANEL_WIDTH);
        imageView.setFitHeight(Constants.MAIN_PANEL_HEIGHT);
        getChildren().add(imageView);
        setPrefSize(Constants.MAIN_PANEL_WIDTH, Constants.MAIN_PANEL_HEIGHT);

        MenuItem startItem = new MenuItem("Start");
        startItem.setOnMousePressed(event -> {
            mainMenu.setVisible(false);
            gameMenu.setVisible(true);
        });
        MenuItem hallOfFamesItem = new MenuItem("Hall of Fame");
        hallOfFamesItem.setOnMousePressed(event -> {
            mainMenu.setVisible(false);
            hallOfFamesPanel.setVisible(true);
        });

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnMousePressed(event -> {
            System.exit(0);
        });

        mainMenu = new MenuHolder(startItem, hallOfFamesItem, exitItem);

        mainMenu.setTranslateX(50);
        mainMenu.setTranslateY(400);

        hallOfFamesPanel = new HallOfFamesPanel(mainMenu, userService);
        hallOfFamesPanel.setVisible(false);
        userService.subscribe(hallOfFamesPanel);

        MenuItem playItem = new MenuItem("Play");
        playItem.setOnMousePressed(event -> {
            board = new GameBoard(selectMap, selectedUsers, userService);
            board.load();
            board.setMainPanel(this);
            board.setScene(scene);
            HBox box = new HBox();
            scene.setRoot(box);
            InputEventHandler inputHandler = new InputEventHandler(board);
            scene.setOnKeyPressed(inputHandler);
            scene.setOnKeyReleased(inputHandler);
            board.setInputEventHandler(inputHandler);
            box.getChildren().add(board);
            board.startGame();
        });
        MenuItem playersItem = new MenuItem("Players");
        playersItem.setOnMousePressed(event -> {
            playersPanel.setVisible(true);
            gameMenu.setVisible(false);
        });

        MenuItem mapItem = new MenuItem("Map");
        mapItem.setOnMousePressed(event -> {
            gameMenu.setVisible(false);
            mapMenu.setVisible(true);
        });
        MenuItem backItem = new MenuItem("Back");
        backItem.setOnMousePressed(event -> {
            gameMenu.setVisible(false);
            mainMenu.setVisible(true);
        });
        gameMenu = new MenuHolder(playItem, playersItem, mapItem, backItem);
        gameMenu.setTranslateX(50);
        gameMenu.setTranslateY(400);
        gameMenu.setVisible(false);

        playersPanel = createPlayerSelectionMenu();
        playersPanel.setVisible(false);

        mapMenu = createMapMenu();

//        getChildren().addAll(mainMenu);
        getChildren().addAll(mainMenu, gameMenu, mapMenu, playersPanel, hallOfFamesPanel);

    }

    private UserSelectorPanel createPlayerSelectionMenu() {

        if (users == null) {
            users = userService.list();
        }
        UserSelectorPanel userSelector = new UserSelectorPanel(userService, gameMenu, selectedUsers);
        for (User user : users) {
            UserMenuItem item = new UserMenuItem(user);
            userSelector.addItem(item);
        }
        return userSelector;
    }

    private MenuHolder createMapMenu() {

        MenuHolder mapMenu = new MenuHolder();
        List<GameMap> maps = Constants.maps;
        if (maps.isEmpty()) {
            return mapMenu;
        }
        for (GameMap map : maps) {
            MapMenuItem item = new MapMenuItem(map);
            item.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    MainPanel.this.selectMap = map;
                    MainPanel.this.mapMenu.setVisible(false);
                    MainPanel.this.gameMenu.setVisible(true);
                }
            });
            mapMenu.addItem(item);
        }
        MenuItem back = new MenuItem("Back");
        back.setOnMousePressed(event -> {
            mapMenu.setVisible(false);
            gameMenu.setVisible(true);
        });
        mapMenu.addItem(back);
        mapMenu.setVisible(false);
        return mapMenu;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
