package ir.ac.kntu.components;

import ir.ac.kntu.Constants;
import ir.ac.kntu.Utils;
import ir.ac.kntu.components.gifts.BombAdder;
import ir.ac.kntu.components.gifts.BombBooster;
import ir.ac.kntu.components.gifts.Gift;
import ir.ac.kntu.components.tiles.*;
import ir.ac.kntu.data.GameMap;
import ir.ac.kntu.data.User;
import ir.ac.kntu.navigation.MainPanel;
import ir.ac.kntu.service.UserService;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.*;
import java.util.stream.Collectors;

public class GameBoard extends Pane {

    public enum GameStop {
        TIMEOUT, WINNER, NONE;

    }

    private Label timerLabel;
    private MainPanel mainPanel;
    private Bomberman systemBomber;
    private String name;
    private int rows;
    private int cols;
    private char[][] data;
    private List<Tile> tiles = new ArrayList<>();
    private List<Bomberman> bombermans = new ArrayList<>();
    private int minX, minY;
    private int maxX, maxY;
    private List<Timer> timers = new ArrayList<>();
    private List<Gift> gifts = new ArrayList<>();
    private Timer gameTimer;
    // maybe many thread read it
    private volatile boolean playing;
    private VBox statusBar;
    private Scene scene;
    private UserService userService;
    private List<User> users;


    public GameBoard(GameMap map, List<User> users, UserService userService) {
        this(map.getName(), map.getData());
        this.userService = userService;
        this.users = users;
        System.out.println(String.format("starting the game with %s users", users.size()));
    }

    public GameBoard(String name, char[][] data) {
        this.name = name;
        this.data = data;
        maxX = (data[0].length - 1) * Constants.TILE_SIZE;
        maxY = (data.length - 1) * Constants.TILE_SIZE;
    }

    public void load() {

        ImageView bgView = new ImageView(Constants.BOMBERMAN_BG_IMAGE);
//        bgView.setFitWidth(2000);
//        bgView.setFitHeight(2000);
        getChildren().add(bgView);


        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                Tile tile = createTile(row, col, data[row][col]);
                tile.init();
                tiles.add(tile);
                getChildren().add(tile);
            }
        }
        // add predefined bombermans
        getChildren().addAll(bombermans);
        // add selected user as bomberman
        createBomberMans(users);

        statusBar = new VBox();
        statusBar.setTranslateX(maxX + Constants.TILE_SIZE);
        statusBar.setTranslateY(0);
        statusBar.setPrefSize(200, maxY);
        bombermans.forEach(b -> {
            PlayerBoardSection section = new PlayerBoardSection(b, b.getSystemName().downStanding);
            statusBar.getChildren().add(section);
            b.setScoreBoard(section);
        });

        timerLabel = new Label(Constants.GAME_TIME / 1000 + "");
        timerLabel.setFont(Font.font(20));
        timerLabel.setStyle("-fx-background-color: yellow;");
        timerLabel.setTextFill(Color.RED);
        timerLabel.setPrefWidth(200);
        timerLabel.setAlignment(Pos.CENTER);
        timerLabel.paddingProperty().set(new Insets(5, 0, 5, 0));

        Label back = new Label("Main Menu");
        back.setStyle("-fx-border-color:black; -fx-background-color: white;-fx-label-padding: 5");
        back.setPrefWidth(200);
        back.setAlignment(Pos.CENTER);

        // button get focus and consumes the key actions
//        Button back = new Button("Main Menu");
        back.setOnMouseClicked(event -> {
            stopGame(GameStop.NONE);
            scene.setRoot(mainPanel);
        });
        statusBar.getChildren().addAll(timerLabel, back);
        getChildren().add(statusBar);

        systemBomber = new Bomberman(this, "system", Bomberman.SYSTEM_NAMES.SYSTEM, 0, 0, 0, 0);
        systemBomber.setMaxBombs(1000);
        systemBomber.setRow(-1);
        systemBomber.setCol(-1);


    }

    public void startGame() {
        Timer start = new Timer();
        start.schedule(new TimerTask() {
            @Override
            public void run() {
                playing = true;
                startTimers();
            }
        }, Constants.GAME_START_DELAY);
    }

    private void startTimers() {

        TimerTask giftGenerator = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Tile tile = findRandomFreeTile();
                    int index = new Random().nextInt(4);
//                    index = 3;
                    switch (index) {
                        case 0:
                            Gift gift = new BombBooster(GameBoard.this);
                            gifts.add(gift);
                            positionInTile(tile, gift);
                            break;
                        case 1:
                            Bomb bomb = new Bomb(GameBoard.this, systemBomber, tile.getTranslateX(), tile.getTranslateY(), tile.getRow(),
                                    tile.getCol(), Constants.BOMB_DELAY, Constants.BOMB_EXPLOSION_RANGE);
                            getChildren().add(bomb);
                            systemBomber.getBombs().add(bomb);
                            systemBomber.startBomb(bomb);
                            break;
                        case 2:
                            char[] signs = {'u', 'd', 'l', 'r'};
                            char c = signs[new Random().nextInt(signs.length)];
                            Tile oneWay = new OneWay(tile.getRow(), tile.getCol(), Constants.TILE_SIZE, Constants.TILE_SIZE,
                                    tile.getCol() * Constants.TILE_SIZE, tile.getRow() * Constants.TILE_SIZE, c);
                            oneWay.init();
                            tile.setGuestTile(oneWay);
                            getChildren().add(oneWay);
                            disappearAfterAwhile(oneWay);
                            break;
                        case 3:
                            Gift bombAdder = new BombAdder(GameBoard.this);
                            gifts.add(bombAdder);
                            positionInTile(tile, bombAdder);
                            break;
                    }

                });
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(giftGenerator, 0, Constants.GIFT_BOOST_INTERVAL);
        timers.add(timer);

        gameTimer = Utils.runLater(() -> {
            stopGame(GameStop.TIMEOUT);

        }, Constants.GAME_TIME);
        timers.add(gameTimer);

        TimerTask timerTask = new CountDownTimer(timerLabel, Constants.GAME_TIME / 1000);
        Timer timerTimer = new Timer();
        timerTimer.scheduleAtFixedRate(timerTask, 0, 1000);
        timers.add(timerTimer);

    }

    public void stopGame(GameStop gameStop) {
        timers.forEach(Timer::cancel);
        playing = false;
        System.out.println("Game is over");
        if (gameStop == GameStop.NONE) {
            return;
        }

        GameOverPanel panel = new GameOverPanel(userService, scene, mainPanel, gameStop, bombermans);
        getChildren().add(panel);
//        Stage stage = new Stage();
//        Scene newScene = new Scene(panel);

//        stage.setScene(newScene);
//        stage.initStyle(StageStyle.UTILITY);
//
//        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent event) {
//                scene.setRoot(mainPanel);
//                stage.close();
//            }
//        });
//        stage.show();
    }

    private void disappearAfterAwhile(Tile tile) {
        Timer timer = Utils.runLater(() -> {
            getChildren().remove(tile);
            tile.getHostTile().setGuestTile(null);
        }, Constants.GIFT_BOOST_INTERVAL);
        timers.add(timer);

    }

    private void positionInTile(Tile tile, Positionable node) {
        node.setRow(tile.getRow());
        node.setCol(tile.getCol());
        ((Node) node).setTranslateX(tile.getTranslateX());
        ((Node) node).setTranslateY(tile.getTranslateY());
        getChildren().add((Node) node);
    }

    private Tile createTile(int row, int col, char c) {
        Tile freeSpace = new FreeSpace(row, col, Constants.TILE_SIZE, Constants.TILE_SIZE, col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
        switch (c) {
            case 'w':
                return new Wall(row, col, Constants.TILE_SIZE, Constants.TILE_SIZE, col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
            case 'b':
                Tile block = new Block(row, col, Constants.TILE_SIZE, Constants.TILE_SIZE, col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
                return block;
            case 'f':
                return new FreeSpace(row, col, Constants.TILE_SIZE, Constants.TILE_SIZE, col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
            case 'u':
            case 'd':
            case 'l':
            case 'r':
                return new OneWay(row, col, Constants.TILE_SIZE, Constants.TILE_SIZE, col * Constants.TILE_SIZE, row * Constants.TILE_SIZE, c);
            case '1':
            case '2':
            case '3':
            case '4':
                Bomberman bomberman = new Bomberman(this, "Bomberman " + c, Bomberman.SYSTEM_NAMES.from(c), col * Constants.TILE_SIZE, row * Constants.TILE_SIZE, row, col);
                bombermans.add(bomberman);
                return freeSpace;
            default:
                throw new IllegalStateException("Unexpected board code: " + c);
        }
    }


    public Tile findRandomFreeTile() {
        List<Tile> freeTiles = tiles.stream().filter(t -> t.isCanPassThrow()).collect(Collectors.toList());
        int index = (new Random()).nextInt(freeTiles.size());
        return freeTiles.get(index);
    }

    public void removeGIft(Gift gift) {
        getChildren().remove(gift);
        gifts.remove(gift);
    }

    public void removeItem(Node node) {
        getChildren().remove(node);
    }

    public synchronized void removeBomberMan(Bomberman bomberman) {
//        bombermans.remove(bomberman);
        getChildren().remove(bomberman);
        long liveMans = bombermans.stream().filter(Bomberman::isAlive).count();
        if (liveMans <= 1) {
            stopGame(GameStop.WINNER);
        }
    }


    public void createBomberMans(List<User> selectedUsers) {
        if (selectedUsers.size() > 4) {
            System.out.println("at most 4 players supported");
            return;
        }
        Bomberman.SYSTEM_NAMES[] systemNames = Bomberman.SYSTEM_NAMES.values();
        for (int i = 0; i < selectedUsers.size(); i++) {
            User user = selectedUsers.get(i);
            Tile tile = findRandomFreeTile();
            Bomberman bomberman = new Bomberman(this, user.getName(), systemNames[i],
                    tile.getCol() * Constants.TILE_SIZE, tile.getRow() * Constants.TILE_SIZE, tile.getRow(), tile.getCol());
            bomberman.setUser(user);
            bombermans.add(bomberman);
        }
        getChildren().addAll(bombermans);
    }

    public List<Bomberman> getBombermans() {
        return bombermans;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setBombermans(List<Bomberman> bombermans) {
        this.bombermans = bombermans;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
}
