package ir.javageek.actions;

import javafx.scene.input.KeyCode;

public class ThrowBomb extends UserAction {
    public ThrowBomb(KeyCode keyCode) {
        super(keyCode);
    }

    @Override
    public boolean isMove() {
        return false;
    }
}
