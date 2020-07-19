package ir.ac.kntu.navigation;

import ir.ac.kntu.data.GameMap;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MapMenuItem extends MenuItem {

    private final GameMap map;

    public MapMenuItem(GameMap map) {
        super(map.getName());
        this.map = map;
    }

    public GameMap getMap() {
        return map;
    }
}
