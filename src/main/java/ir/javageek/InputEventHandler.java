package ir.javageek;

import ir.javageek.components.Bomberman;
import ir.javageek.components.GameBoard;
import ir.javageek.actions.KeyDirection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InputEventHandler implements EventHandler<KeyEvent> {

    private final GameBoard board;
    Map<KeyCode, Bomberman> dispatcher = new HashMap<>();
    private final Map<KeyCode, Bomberman.Direction> keyMapper = new HashMap<>();
    // Track pressed directions for each Bomberman
    private final Map<Bomberman, Set<Bomberman.Direction>> pressedDirections = new HashMap<>();

    public InputEventHandler(GameBoard board) {
        this.board = board;
        for (Bomberman bomberman : board.getBombermans()) {
            bomberman.getSystemName().getUserActions().forEach(userAction -> {
                dispatcher.put(userAction.getKeyCode(), bomberman);
                if (userAction.isMove()) {
                    keyMapper.put(userAction.getKeyCode(), ((KeyDirection) userAction).direction);
                }
            });
            pressedDirections.put(bomberman, new HashSet<>());
        }
    }

    @Override
    public void handle(KeyEvent event) {
        Bomberman bomberman = dispatcher.get(event.getCode());
        if (bomberman == null) {
            System.out.printf("key %s is not defined%n", event.getCode());
            return;
        }
        if (!board.isPlaying()) {
            System.out.println("Game is over");
            return;
        }
        if (!bomberman.isAlive()) {
            System.out.printf("Hey %s! Sorry, your're dead%n", bomberman.getUsername());
            return;
        }
        Bomberman.Direction direction = keyMapper.get(event.getCode());
        if (direction == null) {
            // it's a bomb action
            if (isThrowKey(event) && !bomberman.getSystemName().equals(Bomberman.SYSTEM_NAMES.SYSTEM)) {
                bomberman.throwBomb();
            } else {
                bomberman.insertBomb();
            }
        } else {
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                pressedDirections.get(bomberman).add(direction);
            } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                pressedDirections.get(bomberman).remove(direction);
            }
        }
    }

    public Set<Bomberman.Direction> getPressedDirections(Bomberman bomberman) {
        return pressedDirections.getOrDefault(bomberman, new HashSet<>());
    }

    private static boolean isThrowKey(KeyEvent event) {
        return event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.COMMAND;
    }
}
