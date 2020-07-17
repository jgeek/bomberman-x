package ir.ac.kntu.components;

import ir.ac.kntu.actions.UserAction;
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
