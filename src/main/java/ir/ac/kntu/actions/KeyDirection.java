package ir.ac.kntu.actions;

import ir.ac.kntu.actions.UserAction;
import ir.ac.kntu.components.Bomberman;
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
