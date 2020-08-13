package ir.javageek.actions;

import javafx.scene.input.KeyCode;

public class InsertBomb extends UserAction {
    public InsertBomb(KeyCode keyCode) {
        super(keyCode);
    }

    @Override
    public boolean isMove() {
        return false;
    }
}
