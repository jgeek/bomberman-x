package ir.ac.kntu.components;

import ir.ac.kntu.Statics;
import ir.ac.kntu.Utils;
import ir.ac.kntu.components.gifts.BombBooster;
import ir.ac.kntu.components.gifts.Gift;
import ir.ac.kntu.components.tiles.*;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.util.*;
import java.util.stream.Collectors;

public class GameBoard extends StackPane {
    private String name;
    private int rows;
    private int cols;
    private char[][] data;
    private List<Tile> tiles = new ArrayList<>();
    private List<Bomberman> bombermans = new ArrayList<>();
    private int minX, minY = 0;
    private int maxX, maxY;
    List<Timer> timers = new ArrayList<>();

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

        startTimers();
    }

    private void startTimers() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Tile tile = findRandomFreeTile();
                    Gift gift = new BombBooster();
                    positionInTile(tile, gift);
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
}
