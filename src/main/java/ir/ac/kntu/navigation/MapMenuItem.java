package ir.ac.kntu.navigation;

import ir.ac.kntu.components.data.GameMap;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MapMenuItem extends MenuItem {

    private final GameMap map;

    public MapMenuItem(GameMap map, EventHandler<MouseEvent> mouseHandler) {
        super(map.getName(), mouseHandler);
        this.map = map;
    }

    public GameMap getMap() {
        return map;
    }
}
