package ir.ac.kntu.components.data;

public class GameMap {

    private String name;
    private char[][] data;

    public GameMap(String name, char[][] data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public char[][] getData() {
        return data;
    }
}
