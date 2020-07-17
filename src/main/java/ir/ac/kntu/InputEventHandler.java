package ir.ac.kntu;

import ir.ac.kntu.components.Bomberman;
import ir.ac.kntu.components.GameBoard;
import ir.ac.kntu.components.KeyDirection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

public class InputEventHandler implements EventHandler<KeyEvent> {

    Map<KeyCode, Bomberman> dispatcher = new HashMap<>();
    private final Map<KeyCode, Bomberman.Direction> keyMapper = new HashMap<>();

    public InputEventHandler(GameBoard board) {
        for (Bomberman bomberman : board.getBombermans()) {
            bomberman.getSystemName().getUserActions().forEach(userAction -> {
                dispatcher.put(userAction.keyCode, bomberman);
                if (userAction.isMove()) {
                    keyMapper.put(userAction.keyCode, ((KeyDirection) userAction).direction);
                }
            });
        }
    }

    @Override
    public void handle(KeyEvent event) {
        Bomberman bomberman = dispatcher.get(event.getCode());
        if (bomberman == null) {
            return;
        }
        Bomberman.Direction direction = keyMapper.get(event.getCode());
        if (direction == null) {
            // it's a bomb action
            bomberman.insertBomb();

        } else {
            switch (direction) {
                case UP:
                case DOWN:
                    bomberman.moveY(direction);
                    break;
                case LEFT:
                case RIGHT:
                    bomberman.moveX(direction);
                    break;
            }
        }
    }
}