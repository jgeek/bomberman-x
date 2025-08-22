package ir.javageek.components;

import ir.javageek.Constants;
import ir.javageek.Utils;
import ir.javageek.components.tiles.Block;
import ir.javageek.components.tiles.FreeSpace;
import ir.javageek.components.tiles.OneWay;
import ir.javageek.components.tiles.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class Bomb extends ImageView implements Positionable {

    private Bomberman bomberman;
    private int row;
    private int col;
    private Image image;
    private int delay;
    private int explosionRange;
    private boolean exploded;
    private GameBoard board;

    public Bomb(GameBoard board, Bomberman bomberman, double x, double y, int row, int col, int delay, int explosionRange) {
        this.delay = delay;
        this.explosionRange = explosionRange;
        this.row = row;
        this.col = col;
        this.board = board;
        this.bomberman = bomberman;
        setImage(Constants.BOMB_IMAGE);
        setFitWidth(Constants.BOMB_SIZE);
        setFitHeight(Constants.BOMB_SIZE);
//        setViewport(new Rectangle2D(0, 0, 60, 60 ));
        setTranslateX(x);
        setTranslateY(y);
    }

    public void explode() {
        List<Tile> toExplodeTiles = new ArrayList<>();
        toExplodeTiles.add(getToExplodeTile(row, col));
        toExplodeTiles.addAll(getToExplodeRowTiles(1));
        toExplodeTiles.addAll(getToExplodeRowTiles(-1));
        toExplodeTiles.addAll(getToExplodeColTiles(1));
        toExplodeTiles.addAll(getToExplodeColTiles(-1));


        Utils.runLater(() -> {
            List<Bomberman> killed = new ArrayList<>();
            for (Tile tile : toExplodeTiles) {
                if (tile instanceof Block) {
                    if (!tile.isCanPassThrow()) {
                        // hide block main layer
                        tile.getChildren().get(0).setVisible(false);

                        // show breaking block
                        ImageView breakingBlock = new ImageView(Constants.BLOCK_BREAKING_IMAGE);
                        breakingBlock.setFitWidth(tile.getWidth());
                        breakingBlock.setFitHeight(tile.getHeight());
                        tile.getChildren().add(breakingBlock);
                    } else {
                        ImageView fire = new ImageView(Constants.EXPLOSION_IMAGE);
                        fire.setFitWidth(tile.getWidth());
                        fire.setFitHeight(tile.getHeight());
                        tile.getChildren().add(fire);
                    }
                } else if (tile instanceof FreeSpace) {
                    if (tile.getChildren().size() == 1) {
                        ImageView fire = new ImageView(Constants.EXPLOSION_IMAGE);
                        fire.setFitWidth(tile.getWidth());
                        fire.setFitHeight(tile.getHeight());
                        tile.getChildren().add(fire);
                    } else {
                        tile.getChildren().get(1).setVisible(true);
                    }
                    tile.setInFire(true);
                }
                for (Bomberman bomberman : board.getBombermans()) {
                    if (bomberman.isAlive() && bomberman.getRow() == tile.getRow() && bomberman.getCol() == tile.getCol()) {
                        killed.add(bomberman);
                        bomberman.setAlive(false);
                    }
                }
            }

            Utils.runLater(() -> {
                Utils.playMedia(Constants.EXPLOSION_SOUND);
            }, 100);

            killed.forEach(bomberman -> {
                bomberman.killMe();
                if (!this.bomberman.getSystemName().equals(Bomberman.SYSTEM_NAMES.SYSTEM) &&
                        !this.bomberman.getSystemName().equals(bomberman.getSystemName()) && this.bomberman.isAlive()) {
                    this.bomberman.updateScore(1);
                    this.bomberman.updateScore(bomberman.getScore());
                }
                System.out.println(String.format("%s is killed by %s", bomberman.getUsername(), this.bomberman.getUsername()));
            });
            Utils.runLater(() -> {
                for (Tile t : toExplodeTiles) {
                    if (t instanceof Block) {
                        // hide breaking block
                        t.getChildren().remove(2);
//                            t.getChildren().get(2).setVisible(false);
                        // show free space
                        t.getChildren().get(1).setVisible(true);
                        t.setCanPassThrow(true);
                    } else if (t instanceof FreeSpace) {
                        t.getChildren().get(1).setVisible(false);
                        t.setInFire(false);
                    }
                }
            }, Constants.EXPLOSION_REMAIN_TIME);

        }, 100);
        setExploded(true);
    }

    private Tile getToExplodeTile(int i, int j) {
        for (Tile tile : board.getTiles()) {
            if (tile.getRow() == i && tile.getCol() == j) {
                if (tile instanceof OneWay) {
                    return tile;
                }
                return tile.isExplodable() ? tile : null;
            }
        }
        return null;
    }

    private List<Tile> getToExplodeRowTiles(int step) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 1; i <= explosionRange; i += Math.abs(step)) {
            Tile tile = getToExplodeTile(row, col + (i * step));
            if (tile != null) {
                if (tile instanceof OneWay) {
                    continue;
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
        for (int i = 1; i <= explosionRange; i += Math.abs(step)) {
            Tile tile = getToExplodeTile(row + (i * step), col);
            if (tile != null) {
                if (tile instanceof OneWay) {
                    continue;
                }
                tiles.add(tile);
            } else {
                break;
            }
        }
        return tiles;
    }

    @Override
    public String toString() {
        return "Bomb{" +
                "bomberman=" + bomberman +
                '}';
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

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }
}
