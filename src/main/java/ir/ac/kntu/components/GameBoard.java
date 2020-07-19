package ir.ac.kntu.components;

import ir.ac.kntu.Constants;
import ir.ac.kntu.Utils;
import ir.ac.kntu.components.gifts.BombAdder;
import ir.ac.kntu.components.gifts.BombBooster;
import ir.ac.kntu.components.gifts.Gift;
import ir.ac.kntu.components.tiles.*;
import ir.ac.kntu.navigation.MainPanel;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.util.*;
import java.util.stream.Collectors;

public class GameBoard extends Pane {

    public enum GameStop {
        TIMEOUT, WINNER, NONE;
    }

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
    private boolean playing;
    private VBox statusBar;
    private Scene scene;


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
        getChildren().addAll(bombermans);

        statusBar = new VBox();
        statusBar.setTranslateX(maxX + Constants.TILE_SIZE);
        statusBar.setTranslateY(0);
        statusBar.setPrefSize(200, maxY);
        bombermans.forEach(b -> statusBar.getChildren().add(new PlayerBoardSection(b)));
        getChildren().add(statusBar);

        systemBomber = new Bomberman(this, "system", Bomberman.SYSTEM_NAMES.SYSTEM, 0, 0, 0, 0);
        systemBomber.setMaxBombs(1000);
        systemBomber.setRow(-1);
        systemBomber.setCol(-1);


    }

    public void startGame() {
        playing = true;
        startTimers();
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

    }

    public void stopGame(GameStop gameStop) {
        timers.forEach(Timer::cancel);
        playing = false;
        System.out.println("Game is over");

//        Platform.runLater(() -> {
        Stage stage = new Stage();
        GameOverPanel panel = new GameOverPanel(stage, scene, mainPanel, gameStop, bombermans);
        Scene newScene = new Scene(panel);
        stage.setScene(newScene);
        stage.initStyle(StageStyle.UTILITY);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                scene.setRoot(mainPanel);
                stage.close();
            }
        });

//        stage.setAlwaysOnTop(true);
//        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
//        stage.toFront();
//        });
//        scene.setRoot(panel);
//        scene.getWindow().setWidth(600);
//        scene.getWindow().setHeight(300);
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
                Bomberman bomberman = new Bomberman(this, "Bomberman" + c, Bomberman.SYSTEM_NAMES.from(c), col * Constants.TILE_SIZE, row * Constants.TILE_SIZE, row, col);
                bombermans.add(bomberman);
                return freeSpace;
            default:
                throw new IllegalStateException("Unexpected board code: " + c);
        }
    }


    private Tile findRandomFreeTile() {
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

    public void removeBomberMan(Bomberman bomberman) {
//        bombermans.remove(bomberman);
        getChildren().remove(bomberman);
        long liveMans = bombermans.stream().filter(Bomberman::isAlive).count();
        if (liveMans <= 1) {
            stopGame(GameStop.WINNER);
        }
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
