package ir.javageek.actions;

import ir.javageek.components.Bomberman;
import javafx.scene.input.KeyCode;

public abstract class UserAction {
    private final KeyCode keyCode;

    public UserAction(KeyCode keyCode) {
        this.keyCode = keyCode;
    }

    public KeyCode getKeyCode() {
        return keyCode;
    }

    public abstract boolean isMove();
}
