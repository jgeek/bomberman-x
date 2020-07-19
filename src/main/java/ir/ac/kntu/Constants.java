package ir.ac.kntu;

import ir.ac.kntu.components.GameBoard;
import ir.ac.kntu.data.GameMap;
import ir.ac.kntu.service.MapProvider;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.List;

public class Constants {

    public static final int BOMBERMAN_WIDTH = 60;
    public static final int BOMBERMAN_HEIGHT = 60;
    public static final int BOMBERMAN_MAX_CONCURRENT_BOMBS = 1;
    public static final int BOMB_SIZE = 50;
    public static final int BOMB_DELAY = 3000;
    public static final int BOMB_EXPLOSION_RANGE = 2;
    public static final int BOMB_BOOSTED_EXPLOSION_RANGE = 5;
    //    public static final int GAME_TIME = 5000;
    public static final int GAME_TIME = 3 * 60 * 1000;
    public static final int GAME_SCORE = 4;
    public static final int GAME_START_DELAY = 5000;
    public static final double MAIN_PANEL_WIDTH = 1100;
    public static final double MAIN_PANEL_HEIGHT = 800;
    public static final double MENU_ITEM_WIDTH = 400;
    public static final double MENU_ITEM_HEIGHT = 40;
    public static int EXPLOSION_REMAIN_TIME = 600;
    public static final int BOMB_BOOST_TIME = 15000;
    public static final int GIFT_BOOST_INTERVAL = 15000;
    public static int TILE_SIZE = 60;
    public static String USER_DB_FILE = "/home/kargar/bomberman.db";
//    public static String USER_DB_FILE = "/tmp/bomberman.db";

    public static Image FREE_SPACE_IMAGE = Utils.loadImage("assets/map/normal.png");
    public static Image BLOCK_IMAGE = Utils.loadImage("assets/map/block.png");
    public static Image BLOCK_BREAKING_IMAGE = Utils.loadImage("assets/map/block_breaking.png");
    public static Image WALL_IMAGE = Utils.loadImage("assets/map/wall.png");
    public static Image BOMB_IMAGE = Utils.loadImage("assets/map/bomb_black_128.png");
    public static Image EXPLOSION_IMAGE = Utils.loadImage("assets/map/explosion/fire.png");
    public static final Image BOMB_BOOSTER_IMAGE = Utils.loadImage("assets/map/powerup.png");
    public static final Image BOMB_ADDER_IMAGE = Utils.loadImage("assets/map/bomb_adder.png");
    public static final Image BOMBERMAN_BG_IMAGE = Utils.loadImage("assets/images/bomberman-1.png");

    public static String MAP_DIR = "map";

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

    public static List<GameMap> maps = null;

    static {
        try {
            maps = new MapProvider().list();
            System.out.println("Maps loaded");
        } catch (IOException e) {
            System.out.println("error in loading maps");
            e.printStackTrace();
        }
    }
}
