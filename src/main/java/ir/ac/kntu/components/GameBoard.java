package ir.ac.kntu.components;

import ir.ac.kntu.Statics;
import ir.ac.kntu.components.tiles.Block;
import ir.ac.kntu.components.tiles.FreeSpace;
import ir.ac.kntu.components.tiles.Tile;
import ir.ac.kntu.components.tiles.Wall;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class GameBoard extends StackPane {
    private String name;
    private int rows;
    private int cols;
    private char[][] data;
    private List<Tile> tiles = new ArrayList<>();
    private List<Bomberman> bombermans = new ArrayList<>();
    private int minX, minY = 0;
    private int maxX, maxY;

    public GameBoard(String name, char[][] data) {
        this.name = name;
        this.data = data;
        maxX = (data[0].length - 1) * Statics.TILE_SIZE;
        maxY = (data.length - 1) * Statics.TILE_SIZE;
    }

    public GameBoard(String name, int rows, int cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
    }

    public void load() {
        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                Tile tile = createTile(row, col, data[row][col]);
                tiles.add(tile);
                getChildren().add(tile);
            }
        }
        getChildren().addAll(bombermans);
    }

    private Tile createTile(int row, int col, char c) {
        Bomberman bomberman = null;
        switch (c) {
            case 'w':
                return new Wall(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
            case 'b':
                return new Block(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
            case 'f':
                return new FreeSpace(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
            default:
                Tile tile = new FreeSpace(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
                bomberman = new Bomberman(this, "asghar", Bomberman.SYSTEM_NAMES.from(c), col * Statics.TILE_SIZE, row * Statics.TILE_SIZE, row, col);
//                tile.getChildren().add(bomberman);
                bombermans.add(bomberman);
                return tile;
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
}
