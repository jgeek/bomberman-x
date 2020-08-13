package ir.javageek.actions;

import ir.javageek.components.Bomberman;
import javafx.scene.input.KeyCode;

public class KeyDirection extends UserAction {
    public Bomberman.Direction direction;

    public KeyDirection(KeyCode keyCode, Bomberman.Direction direction) {
        super(keyCode);
        this.direction = direction;
    }

    @Override
    public boolean isMove() {
        return true;
    }
}
