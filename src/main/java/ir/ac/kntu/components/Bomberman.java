package ir.ac.kntu.components;

import ir.ac.kntu.Statics;
import ir.ac.kntu.Utils;
import ir.ac.kntu.actions.UserAction;
import ir.ac.kntu.components.gifts.Gift;
import ir.ac.kntu.components.tiles.Tile;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.*;
import java.util.function.Consumer;


public class Bomberman extends ImageView {

    private final GameBoard board;
    private TranslateTransition translateTransition;

    public enum Direction {
        UP(-1, 'u'), DOWN(1, 'd'), LEFT(-1, 'l'), RIGHT(1, 'r');

        private final int value;
        private final int abbr;

        Direction(int value, char abbr) {
            this.value = value;
            this.abbr = abbr;
        }

        public static Direction fromAbbr(char c) {
            switch (c) {
                case 'u':
                    return UP;
                case 'd':
                    return DOWN;
                case 'l':
                    return LEFT;
                case 'r':
                    return RIGHT;
                default:
                    throw new IllegalStateException("Unexpected value: " + c);
            }
        }
    }

    public enum State {
        MOVING, STANDING;
    }


    public enum SYSTEM_NAMES {
        WHITE(new KeyDirection(KeyCode.UP, Direction.UP), new KeyDirection(KeyCode.DOWN, Direction.DOWN),
                new KeyDirection(KeyCode.LEFT, Direction.LEFT), new KeyDirection(KeyCode.RIGHT, Direction.RIGHT), new InsertBomb(KeyCode.SPACE)),
        BLACK(new KeyDirection(KeyCode.W, Direction.UP), new KeyDirection(KeyCode.S, Direction.DOWN),
                new KeyDirection(KeyCode.A, Direction.LEFT), new KeyDirection(KeyCode.D, Direction.RIGHT), new InsertBomb(KeyCode.ALT)),
        RED(new KeyDirection(KeyCode.Y, Direction.UP), new KeyDirection(KeyCode.H, Direction.DOWN),
                new KeyDirection(KeyCode.G, Direction.LEFT), new KeyDirection(KeyCode.J, Direction.RIGHT), new InsertBomb(KeyCode.K)),
        YELLOW(new KeyDirection(KeyCode.DIGIT5, Direction.UP), new KeyDirection(KeyCode.DIGIT2, Direction.DOWN),
                new KeyDirection(KeyCode.DIGIT1, Direction.LEFT), new KeyDirection(KeyCode.DIGIT3, Direction.RIGHT), new InsertBomb(KeyCode.DIGIT9));

        SYSTEM_NAMES(UserAction... actions) {
            this.userActions = (Arrays.asList(actions));
        }

        List<UserAction> userActions;

        ImageView downMoving = Utils.loadBomberManView(this, Direction.DOWN, State.MOVING);
        ImageView downStanding = Utils.loadBomberManView(this, Direction.DOWN, State.STANDING);
        ImageView leftMoving = Utils.loadBomberManView(this, Direction.LEFT, State.MOVING);
        ImageView leftStanding = Utils.loadBomberManView(this, Direction.LEFT, State.STANDING);
        ImageView rightMoving = Utils.loadBomberManView(this, Direction.RIGHT, State.MOVING);
        ImageView rightStanding = Utils.loadBomberManView(this, Direction.RIGHT, State.STANDING);
        ImageView upMoving = Utils.loadBomberManView(this, Direction.UP, State.MOVING);
        ImageView upStanding = Utils.loadBomberManView(this, Direction.UP, State.STANDING);

        public static SYSTEM_NAMES from(char c) {
            switch (c) {
                case '1':
                    return WHITE;
                case '2':
                    return RED;
                case '3':
                    return BLACK;
                case '4':
                    return YELLOW;
                default:
                    throw new IllegalArgumentException("invalid user type " + c);
            }
        }

        public List<UserAction> getUserActions() {
            return userActions;
        }
    }


    private String username;
    private SYSTEM_NAMES systemName;
    private ImageView currentView;
    private Image currentImage;
    private int speed = 60;
    private int maxBombs = Statics.BOMBERMAN_MAX_CONCURRENT_BOMBS;
    private List<Bomb> bombs = new ArrayList<>();
    private int row, col;
    private boolean alive = true;

    public Bomberman(GameBoard board, String username, SYSTEM_NAMES systemName, int x, int y, int row, int col) {
        this.board = board;
        this.username = username;
        this.systemName = systemName;
        this.row = row;
        this.col = col;
        setTranslateX(x);
        setTranslateY(y);
        setFitWidth(Statics.BOMBERMAN_WIDTH);
        setFitHeight(Statics.BOMBERMAN_HEIGHT);

        currentImage = systemName.rightStanding.getImage();
        setImage(currentImage);
        currentView = systemName.rightStanding;
        currentView.setTranslateX(x);
        currentView.setTranslateY(y);
//        getChildren().addAll(currentView);


        //Creating Translate Transition
        translateTransition = new TranslateTransition();

        //Setting the duration of the transition
        translateTransition.setDuration(Duration.millis(100));

        //Setting the node for the transition
        translateTransition.setNode(this);
    }

    public void moveX(Direction direction) {
        if (!alive) {
            System.out.println("I'm dead " + systemName);
            return;
        }

        if (direction == Direction.RIGHT) {
            systemName.rightMoving.setTranslateX(currentView.getTranslateX());
            systemName.rightMoving.setTranslateY(currentView.getTranslateY());
            currentView = systemName.rightMoving;
        } else {
            systemName.leftMoving.setTranslateX(currentView.getTranslateX());
            systemName.leftMoving.setTranslateY(currentView.getTranslateY());
            currentView = systemName.leftMoving;
        }

        double newX = checkMovement(direction);
        if (newX < 0) {
            return;
        }
        setTranslateX(newX);
        addCol(direction);
        checkStatus();
        System.out.println("x " + getTranslateX() + " y " + getTranslateY());
    }

    public void moveY(Direction direction) {

        if (direction == Direction.UP) {
            systemName.upMoving.setTranslateX(currentView.getTranslateX());
            systemName.upMoving.setTranslateY(currentView.getTranslateY());
            currentView = systemName.upMoving;
        } else {
            systemName.downMoving.setTranslateX(currentView.getTranslateX());
            systemName.downMoving.setTranslateY(currentView.getTranslateY());
            currentView = systemName.downMoving;
        }

        double newY = checkMovement(direction);
        if (newY < 0) {
            return;
        }
        setTranslateY(newY);
        addRow(direction);
        checkStatus();
        System.out.println("x " + getTranslateX() + " y " + getTranslateY());

    }

    private void checkStatus() {
        Optional<Tile> tile = board.getTiles().stream().filter(t -> t.getRow() == row && t.getCol() == col)
                .filter(Tile::isInFire)
                .findFirst();
        tile.ifPresent(tile1 -> {
            System.out.println(String.format("bomberman %s went to fire and died", systemName));
            killMe();
        });
    }

    private double checkMovement(Direction direction) {

        double newValue = 0;
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            newValue = getTranslateX() + (direction.value * speed);
            if (newValue < board.getMinX() || newValue > board.getMaxX()) {
                return -1;
            }
        } else {
            newValue = getTranslateY() + (direction.value * speed);
            if (newValue < board.getMinY() || newValue > board.getMaxY()) {
                return -1;
            }
        }
        int nextRow = row;
        int nextCol = col;
        switch (direction) {
            case UP:
                nextRow--;
                break;
            case DOWN:
                nextRow++;
                break;
            case LEFT:
                nextCol--;
                break;
            case RIGHT:
                nextCol++;
                break;
        }

        for (Tile tile : board.getTiles()) {
            if (tile.getRow() == nextRow && tile.getCol() == nextCol) {
                return tile.canPassThrow(direction) ? newValue : -1;
            }
        }
        throw new IllegalStateException(String.format("No tile found in row %s, col %s", nextRow, nextCol));
    }


    public void insertBomb() {
        if (bombs.size() >= maxBombs) {
            System.out.println(String.format("max bombs %s, current bombs %s", maxBombs, bombs.size()));
            return;
        }
        Bomb bomb = new Bomb(board, this, getTranslateX(), getTranslateY(), row, col, Statics.BOMB_DELAY, Statics.BOMB_EXPLOSION_RANGE);
        bombs.add(bomb);
        board.getChildren().add(bomb);
        startBomb(this, bomb);
    }

    private void startBomb(Bomberman bomberman, Bomb bomb) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    board.getChildren().remove(bomb);
                    bomberman.getBombs().remove(bomb);
                    bomb.explode();
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, bomb.getDelay());
    }

    public void addRow(Direction direction) {
        row += direction.value;
    }

    public void addCol(Direction direction) {
        col += direction.value;
    }

    public void killMe() {
        alive = false;
        board.getChildren().remove(this);
    }

    public void consumeGift(Gift gift) {

    }

    public String getUsername() {
        return username;
    }

    public SYSTEM_NAMES getSystemName() {
        return systemName;
    }

    public ImageView getCurrentView() {
        return currentView;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getMaxBombs() {
        return maxBombs;
    }

    public void setMaxBombs(int maxBombs) {
        this.maxBombs = maxBombs;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isAlive() {
        return alive;
    }
}