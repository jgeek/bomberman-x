package ir.ac.kntu.components;

import ir.ac.kntu.Statics;
import ir.ac.kntu.components.tiles.Block;
import ir.ac.kntu.components.tiles.FreeSpace;
import ir.ac.kntu.components.tiles.Tile;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Bomb extends ImageView {

    private int row;
    private int col;
    private Image image;
    private int delay;
    private int explosionRange;
    private GameBoard board;

    public Bomb(GameBoard board, double x, double y, int row, int col, int delay, int explosionRange) {
        this.delay = delay;
        this.explosionRange = explosionRange;
        this.row = row;
        this.col = col;
        this.board = board;
        setImage(Statics.BOMB_IMAGE);
        setFitWidth(Statics.BOMB_SIZE);
        setFitHeight(Statics.BOMB_SIZE);
//        setViewport(new Rectangle2D(0, 0, 60, 60 ));
        setTranslateX(x);
        setTranslateY(y);
    }

    public void explode() {
        List<Integer[]> indices = new ArrayList<>();
        List<Tile> toExplodeTiles = new ArrayList<>();
        toExplodeTiles.add(getToExplodeTile(row, col));
        toExplodeTiles.addAll(getToExplodeRowTiles(1));
        toExplodeTiles.addAll(getToExplodeRowTiles(-1));
        toExplodeTiles.addAll(getToExplodeColTiles(1));
        toExplodeTiles.addAll(getToExplodeColTiles(-1));


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                    for (Tile tile : toExplodeTiles) {
                        if (tile instanceof Block) {
                            tile.getChildren().get(0).setVisible(false);
                            tile.getChildren().get(1).setVisible(true);
                            tile.setCanPassThrow(true);
                        }
//                        board.getChildren().remove(tile);
//                        tile.setVisible(false);
//                        board.getTiles().remove(tile);
//                        Tile freeSpace = FreeSpace.fromTile(tile);
//                        board.getChildren().add(freeSpace);
//                        board.getTiles().add(freeSpace);

                        List<Bomberman> killed = new ArrayList<>();
                        for (Bomberman bomberman : board.getBombermans()) {
                            if (bomberman.getRow() == tile.getRow() && bomberman.getCol() == tile.getCol()) {
                                killed.add(bomberman);
                            }
                        }
                        killed.forEach(Bomberman::killMe);
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 100);


    }

    private Tile getToExplodeTile(int i, int j) {
        for (Tile tile : board.getTiles()) {
            if (tile.getRow() == i && tile.getCol() == j) {
                return tile.canExplode() ? tile : null;
            }
        }
        return null;
    }

    private List<Tile> getToExplodeRowTiles(int step) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 1 * step; i <= explosionRange; i += step) {
            Tile tile = getToExplodeTile(row, col + i);
            if (tile != null) {
                if (tile instanceof FreeSpace) {
//                    continue;
                }
                tiles.add(tile);
            } else {
                break;
            }
        }
        return tiles;
    }

    private List<Tile> getToExplodeColTiles(int step) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 1 * step; i <= explosionRange; i += step) {
            Tile tile = getToExplodeTile(row + i, col);
            if (tile != null) {
                if (tile instanceof FreeSpace) {
//                    continue;
                }
                tiles.add(tile);
            } else {
                break;
            }
        }
        return tiles;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getExplosionRange() {
        return explosionRange;
    }

    public void setExplosionRange(int explosionRange) {
        this.explosionRange = explosionRange;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
