package ir.ac.kntu;

import ir.ac.kntu.components.GameBoard;
import javafx.scene.image.Image;

public class Statics {

    public static final int BOMBERMAN_WIDTH = 60;
    public static final int BOMBERMAN_HEIGHT = 60;
    public static final int BOMBERMAN_MAX_CONCURRENT_BOMBS = 2;
    public static final int BOMB_SIZE = 40;
    public static final int BOMB_DELAY = 3000;
    public static final int BOMB_EXPLOSION_RANGE = 2;
    public static int TILE_SIZE = 60;
    public static int MAP_ROWS = 10;
    public static int MAP_COLS = 10;

    public static Image FREE_SPACE_IMAGE = Utils.loadImage("assets/map/normal.png");
    public static Image BLOCK_IMAGE = Utils.loadImage("assets/map/block.png");
    public static Image WALL_IMAGE = Utils.loadImage("assets/map/wall.png");
    public static Image BOMB_IMAGE = Utils.loadImage("assets/map/bomb.png");

    public static GameBoard getDefaultBoard() {
        return new GameBoard("Default Map", DEFAULT_BOARD_DATA);
    }

    public static char[][] DEFAULT_BOARD_DATA = new char[][]{
            {'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'},
            {'w', '1', 'f', 'd', 'b', 'b', 'f', 'f', 'b', 'f', 'b', 'b', 'f', '2', 'w'},
            {'w', 'f', 'w', 'b', 'w', 'b', 'w', 'b', 'w', 'b', 'w', 'b', 'w', 'f', 'w'},
            {'w', 'b', 'f', 'b', 'f', 'b', 'f', 'b', 'b', 'f', 'f', 'b', 'f', 'b', 'w'},
            {'w', 'b', 'w', 'f', 'w', 'f', 'w', 'b', 'w', 'f', 'w', 'b', 'w', 'f', 'w'},
            {'w', 'f', '4', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'f', 'f', 'b', 'b', 'w'},
            {'w', 'f', 'w', 'b', 'w', 'b', 'w', 'b', 'w', 'b', 'w', 'b', 'w', 'b', 'w'},
            {'w', 'b', 'b', 'b', 'b', 'b', 'f', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'w'},
            {'w', 'f', 'w', 'b', 'w', 'b', 'w', 'b', 'w', 'b', 'w', 'b', 'w', 'f', 'w'},
            {'w', 'f', 'f', 'b', 'b', 'b', 'b', 'f', 'f', 'f', 'f', 'l', 'f', '3', 'w'},
            {'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'},
    };
}
