package ir.javageek.navigation;

import ir.javageek.data.GameMap;

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
