package ir.ac.kntu.components;

import ir.ac.kntu.Statics;
import ir.ac.kntu.Utils;
import ir.ac.kntu.components.gifts.BombBooster;
import ir.ac.kntu.components.gifts.Gift;
import ir.ac.kntu.components.tiles.*;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.*;
import java.util.stream.Collectors;

public class GameBoard extends StackPane {

    private Bomberman systemBomber;


    private String name;
    private int rows;
    private int cols;
    private char[][] data;
    private List<Tile> tiles = new ArrayList<>();
    private List<Bomberman> bombermans = new ArrayList<>();
    private int minX, minY = 0;
    private int maxX, maxY;
    List<Timer> timers = new ArrayList<>();
    List<Gift> gifts = new ArrayList<>();

    public GameBoard(String name, char[][] data) {
        this.name = name;
        this.data = data;
        maxX = (data[0].length - 1) * Statics.TILE_SIZE;
        maxY = (data.length - 1) * Statics.TILE_SIZE;
    }

    public void load() {
        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                Tile tile = createTile(row, col, data[row][col]);
                tile.init();
                tiles.add(tile);
                getChildren().add(tile);
            }
        }
        getChildren().addAll(bombermans);
        systemBomber = new Bomberman(this, "system", Bomberman.SYSTEM_NAMES.SYSTEM, 0, 0, 0, 0);
        systemBomber.setMaxBombs(1000);
        systemBomber.setRow(-1);
        systemBomber.setCol(-1);
        startTimers();
    }

    private void startTimers() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Tile tile = findRandomFreeTile();
                    int index = new Random().nextInt(2);
                    index = 2;
                    switch (index) {
                        case 0:
                            Gift gift = new BombBooster(GameBoard.this);
                            gifts.add(gift);
                            positionInTile(tile, gift);
                            break;
                        case 1:
                            Bomb bomb = new Bomb(GameBoard.this, systemBomber, tile.getTranslateX(), tile.getTranslateY(), tile.getRow(),
                                    tile.getCol(), Statics.BOMB_DELAY, Statics.BOMB_EXPLOSION_RANGE);
                            getChildren().add(bomb);
                            systemBomber.getBombs().add(bomb);
                            systemBomber.startBomb(bomb);
                            break;
                        case 2:
                            char[] signs = {'u', 'd', 'l', 'r'};
                            char c = signs[new Random().nextInt(signs.length)];
                            Tile oneWay = new OneWay(tile.getRow(), tile.getCol(), Statics.TILE_SIZE, Statics.TILE_SIZE,
                                    tile.getCol() * Statics.TILE_SIZE, tile.getRow() * Statics.TILE_SIZE, c);
                            oneWay.init();
                            tile.setAddedOn(oneWay);
                            getChildren().add(oneWay);
//                            tile.setVisible(false);
//                            tiles.add(oneWay);


                    }

                });
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, Statics.BOMB_BOOST_INTERVAL);

    }

    private void positionInTile(Tile tile, Positionable node) {
        node.setRow(tile.getRow());
        node.setCol(tile.getCol());
        ((Node) node).setTranslateX(tile.getTranslateX());
        ((Node) node).setTranslateY(tile.getTranslateY());
        getChildren().add((Node) node);
    }

    private Tile createTile(int row, int col, char c) {
        Tile freeSpace = new FreeSpace(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
        switch (c) {
            case 'w':
                return new Wall(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
            case 'b':
                Tile block = new Block(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
                return block;
            case 'f':
                return new FreeSpace(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
            case 'u':
            case 'd':
            case 'l':
            case 'r':
                return new OneWay(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE, c);
            case '1':
            case '2':
            case '3':
            case '4':
                Bomberman bomberman = new Bomberman(this, "Bomberman" + c, Bomberman.SYSTEM_NAMES.from(c), col * Statics.TILE_SIZE, row * Statics.TILE_SIZE, row, col);
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

    public void removeItem(Node node) {
        getChildren().remove(node);
    }

    public void removeBomberMan(Bomberman bomberman) {
        bombermans.remove(bomberman);
        getChildren().remove(bomberman);
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
}
