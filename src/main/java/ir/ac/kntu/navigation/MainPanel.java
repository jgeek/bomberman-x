package ir.ac.kntu.navigation;

import ir.ac.kntu.Constants;
import ir.ac.kntu.InputEventHandler;
import ir.ac.kntu.components.GameBoard;
import ir.ac.kntu.data.GameMap;
import ir.ac.kntu.data.User;
import ir.ac.kntu.service.UserProvider;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class MainPanel extends Pane {

    private List<GameMap> maps;
    private Scene scene;
    private MenuHolder mainMenu;
    private MenuHolder gameMenu;
    private MenuHolder mapMenu;
    private UserSelectorPanel playerMenu;
    private GameBoard board;
    private GameMap selectMap = Constants.maps.get(0);
    private List<User> users;
    private List<User> selectedUsers = new ArrayList<>();

    public MainPanel() {

        ImageView imageView = new ImageView(Constants.BOMBERMAN_BG_IMAGE);
        imageView.setFitWidth(Constants.MAIN_PANEL_WIDTH);
        imageView.setFitHeight(Constants.MAIN_PANEL_HEIGHT);
        getChildren().add(imageView);
        setPrefSize(Constants.MAIN_PANEL_WIDTH, Constants.MAIN_PANEL_HEIGHT);

        MenuItem startItem = new MenuItem("Start");
        startItem.setOnMousePressed(event -> {
            gameMenu.setVisible(true);
            mainMenu.setVisible(false);
        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnMousePressed(event -> {
            System.exit(0);
        });

        mainMenu = new MenuHolder(startItem, exit);

        mainMenu.setTranslateX(50);
        mainMenu.setTranslateY(400);

        MenuItem playItem = new MenuItem("Play");
        playItem.setOnMousePressed(event -> {
            board = Constants.getDefaultBoard();
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
        MenuItem playersItem = new MenuItem("Players");
        playersItem.setOnMousePressed(event -> {
            playerMenu.setVisible(true);
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

        playerMenu = createPlayerSelectionMenu();
        playerMenu.setVisible(false);

        mapMenu = createMapMenu();

        getChildren().addAll(mainMenu, gameMenu, mapMenu, playerMenu);


    }

    private UserSelectorPanel createPlayerSelectionMenu() {

        if (users == null) {
            users = new UserProvider().list();
        }
        UserMenuItem[] items = new UserMenuItem[users.size()];
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            UserMenuItem item = new UserMenuItem(user);
            item.setOnMousePressed(event -> {
                if (selectedUsers.contains(user)) {
                    selectedUsers.remove(user);
                    item.setSelected(false);
                } else {
                    selectedUsers.add(user);
                    item.setSelected(true);
                }
            });
            items[i] = item;
        }
        return new UserSelectorPanel(gameMenu, items);
    }

    private MenuHolder createMapMenu() {

        MenuHolder mapMenu = new MenuHolder();
        List<GameMap> maps = Constants.maps;
        if (maps == null) {
            return mapMenu;
        }
        for (GameMap map : maps) {
            MapMenuItem item = new MapMenuItem(map);
            item.setOnMousePressed(event -> {
                MainPanel.this.selectMap = map;
                MainPanel.this.mapMenu.setVisible(false);
                MainPanel.this.gameMenu.setVisible(true);
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
