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
    List<Tile> tiles = new ArrayList<>();
    List<Bomberman> bombermans = new ArrayList<>();

    public GameBoard(String name, char[][] data) {
        this.name = name;
        this.data = data;
    }

    public GameBoard(String name, int rows, int cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
    }

    public void load1() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Tile block = new Block(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
                Tile grass = new FreeSpace(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
                tiles.add(row % 2 == 0 ? block : grass);
                getChildren().add(row % 2 == 0 ? block : grass);
            }
        }
    }

    public void load() {
        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {
                Tile tile = createTile(row, col, data[row][col]);
                if(tile != null){
                    getChildren().add(tile);
                }
            }
        }
        getChildren().addAll(bombermans);

        new Thread(() -> {
            while (true) {
                for (Bomberman b : bombermans) {
//                    b.moveX();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Tile createTile(int row, int col, char c) {
        Bomberman bomberman = null;
        switch (c) {
            case 'w':
                bomberman = new Bomberman(this, "asghar", Bomberman.SYSTEM_NAMES.from('1'), col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
//                bombermans.add(bomberman);
//                return null;
                return new Wall(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
            case 'b':
                return new Block(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
            case 'f':
                return new FreeSpace(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
            default:
                Tile tile = new FreeSpace(row, col, Statics.TILE_SIZE, Statics.TILE_SIZE, col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
                bomberman = new Bomberman(this, "asghar", Bomberman.SYSTEM_NAMES.from(c), col * Statics.TILE_SIZE, row * Statics.TILE_SIZE);
//                tile.getChildren().add(bomberman);
                bombermans.add(bomberman);
                return tile;
        }
    }

    public List<Bomberman> getBombermans() {
        return bombermans;
    }
}
