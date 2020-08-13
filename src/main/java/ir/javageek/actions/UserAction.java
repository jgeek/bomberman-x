package ir.javageek.actions;

import javafx.scene.input.KeyCode;

public abstract class UserAction {
    public KeyCode keyCode;

    public UserAction(KeyCode keyCode) {
        this.keyCode = keyCode;
    }

    public abstract boolean isMove();
}
