package ir.ac.kntu.components;

import ir.ac.kntu.Constants;
import ir.ac.kntu.Utils;
import ir.ac.kntu.actions.InsertBomb;
import ir.ac.kntu.actions.KeyDirection;
import ir.ac.kntu.actions.UserAction;
import ir.ac.kntu.components.gifts.Gift;
import ir.ac.kntu.components.tiles.Tile;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.util.*;


public class Bomberman extends ImageView {

    private final GameBoard board;
    private TranslateTransition translateTransition;
    private boolean bombBoosted;
    private PlayerBoardSection scoreBoard;
    private int score;

    public void setScoreBoard(PlayerBoardSection scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

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

        WHITE("white", new KeyDirection(KeyCode.UP, Direction.UP), new KeyDirection(KeyCode.DOWN, Direction.DOWN),
                new KeyDirection(KeyCode.LEFT, Direction.LEFT), new KeyDirection(KeyCode.RIGHT, Direction.RIGHT), new InsertBomb(KeyCode.SPACE)),
        BLACK("black", new KeyDirection(KeyCode.W, Direction.UP), new KeyDirection(KeyCode.S, Direction.DOWN),
                new KeyDirection(KeyCode.A, Direction.LEFT), new KeyDirection(KeyCode.D, Direction.RIGHT), new InsertBomb(KeyCode.ALT)),
        RED("red", new KeyDirection(KeyCode.Y, Direction.UP), new KeyDirection(KeyCode.H, Direction.DOWN),
                new KeyDirection(KeyCode.G, Direction.LEFT), new KeyDirection(KeyCode.J, Direction.RIGHT), new InsertBomb(KeyCode.K)),
        YELLOW("yellow", new KeyDirection(KeyCode.DIGIT5, Direction.UP), new KeyDirection(KeyCode.DIGIT2, Direction.DOWN),
                new KeyDirection(KeyCode.DIGIT1, Direction.LEFT), new KeyDirection(KeyCode.DIGIT3, Direction.RIGHT), new InsertBomb(KeyCode.DIGIT9)),
        SYSTEM("white", new KeyDirection(KeyCode.DIGIT5, Direction.UP), new KeyDirection(KeyCode.DIGIT2, Direction.DOWN),
                new KeyDirection(KeyCode.DIGIT1, Direction.LEFT), new KeyDirection(KeyCode.DIGIT3, Direction.RIGHT), new InsertBomb(KeyCode.DIGIT9));

        SYSTEM_NAMES(String name, UserAction... actions) {
            this.name = name;
            this.userActions = (Arrays.asList(actions));

            downMoving = Utils.loadBomberManImage(this, Direction.DOWN, State.MOVING);
            downStanding = Utils.loadBomberManImage(this, Direction.DOWN, State.STANDING);
            leftMoving = Utils.loadBomberManImage(this, Direction.LEFT, State.MOVING);
            leftStanding = Utils.loadBomberManImage(this, Direction.LEFT, State.STANDING);
            rightMoving = Utils.loadBomberManImage(this, Direction.RIGHT, State.MOVING);
            rightStanding = Utils.loadBomberManImage(this, Direction.RIGHT, State.STANDING);
            upMoving = Utils.loadBomberManImage(this, Direction.UP, State.MOVING);
            upStanding = Utils.loadBomberManImage(this, Direction.UP, State.STANDING);
        }

        public String getName() {
            return name;
        }

        Image downMoving;
        Image downStanding;
        Image leftMoving;
        Image leftStanding;
        Image rightMoving;
        Image rightStanding;
        Image upMoving;
        Image upStanding;
        String name;
        List<UserAction> userActions;


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
    private Image currentImage;
    private int speed = 60;
    private int maxBombs = Constants.BOMBERMAN_MAX_CONCURRENT_BOMBS;
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
        setFitWidth(Constants.BOMBERMAN_WIDTH);
        setFitHeight(Constants.BOMBERMAN_HEIGHT);

        currentImage = systemName.downStanding;
        setImage(currentImage);
    }

    public void moveX(Direction direction) {
        if (!checkAlivenessAndGame()) {
            return;
        }

        if (direction == Direction.RIGHT) {
            currentImage = systemName.rightMoving;
        } else {
            currentImage = systemName.leftMoving;
        }
        setImage(currentImage);

        double newX = checkMovement(direction);
        if (newX < 0) {
            return;
        }
        setTranslateX(newX);
        addCol(direction);
        checkStatus();
        correctImage(direction);
        System.out.println("x " + getTranslateX() + " y " + getTranslateY());
    }

    private void correctImage(Direction direction) {
        switch (direction) {
            case UP:
                currentImage = systemName.upStanding;
                break;
            case DOWN:
                currentImage = systemName.downStanding;
                break;
            case LEFT:
                currentImage = systemName.leftStanding;
                break;
            case RIGHT:
                currentImage = systemName.rightStanding;
                break;
        }
        setImage(currentImage);
    }

    public void moveY(Direction direction) {

        if (!checkAlivenessAndGame()) {
            return;
        }
        if (direction == Direction.UP) {
            currentImage = systemName.upMoving;
        } else {
            currentImage = systemName.downMoving;
        }
        setImage(currentImage);

        double newY = checkMovement(direction);
        if (newY < 0) {
            return;
        }
        setTranslateY(newY);
        addRow(direction);
        checkStatus();
        correctImage(direction);
        System.out.println("x " + getTranslateX() + " y " + getTranslateY());
    }

    private boolean checkAlivenessAndGame() {
        if (!alive) {
            System.out.println("I'm dead " + systemName);
            return false;
        }
        if (!board.isPlaying()) {
            System.out.println("Game is over");
            return false;
        }
        return true;
    }

    private void checkStatus() {
        // check if bomberman comes in a in fire tile
        Optional<Tile> tile = board.getTiles().stream().filter(t -> t.getRow() == row && t.getCol() == col)
                .filter(Tile::isInFire)
                .findFirst();
        tile.ifPresent(tile1 -> {
            System.out.println(String.format("bomberman %s went to fire and died", systemName));
            killMe();
        });

        // check if he get a gift
        Optional<Gift> gift = board.getGifts().stream().filter(g -> g.getRow() == row && g.getCol() == col).findFirst();
        gift.ifPresent(g -> g.consumeMe(this));
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
                Tile guestTile = tile.getGuestTile();
                if (guestTile != null) {
                    return guestTile.canPassThrow(direction) ? newValue : -1;
                } else {
                    return tile.canPassThrow(direction) ? newValue : -1;
                }
            }
        }
        throw new IllegalStateException(String.format("No tile found in row %s, col %s", nextRow, nextCol));
    }


    public void insertBomb() {
        if (bombs.size() >= maxBombs) {
            System.out.println(String.format("max bombs %s, current bombs %s", maxBombs, bombs.size()));
            return;
        }
        Bomb bomb = new Bomb(board, this, getTranslateX(), getTranslateY(), row, col, Constants.BOMB_DELAY,
                bombBoosted ? Constants.BOMB_BOOSTED_EXPLOSION_RANGE : Constants.BOMB_EXPLOSION_RANGE);
        bombs.add(bomb);
        board.getChildren().add(bomb);
        startBomb(bomb);
    }

    public void startBomb(Bomb bomb) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    board.getChildren().remove(bomb);
                    Bomberman.this.getBombs().remove(bomb);
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
        board.removeBomberMan(this);
    }

    public void updateScore(int score) {
        this.score += score;
        scoreBoard.setScore(this.score);
    }

    public String getUsername() {
        return username;
    }

    public SYSTEM_NAMES getSystemName() {
        return systemName;
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

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setBombBoosted(boolean bombBoosted) {
        this.bombBoosted = bombBoosted;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public PlayerBoardSection getScoreBoard() {
        return scoreBoard;
    }

    public int getScore() {
        return score;
    }
}