package ir.ac.kntu.components;

import ir.ac.kntu.Statics;
import ir.ac.kntu.Utils;
import ir.ac.kntu.actions.UserAction;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.*;


public class Bomberman extends ImageView {

    private final GameBoard board;
    private TranslateTransition translateTransition;

    public enum Direction {
        UP(-1), DOWN(1), LEFT(-1), RIGHT(1);

        private final int value;

        Direction(int value) {
            this.value = value;
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

    public Bomberman(GameBoard board, String username, SYSTEM_NAMES systemName, int x, int y) {
        this.board = board;
        this.username = username;
        this.systemName = systemName;
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

//        getChildren().remove(currentView);
        if (direction == Direction.RIGHT) {
            systemName.rightMoving.setTranslateX(currentView.getTranslateX());
            systemName.rightMoving.setTranslateY(currentView.getTranslateY());
            currentView = systemName.rightMoving;
        } else {
            systemName.leftMoving.setTranslateX(currentView.getTranslateX());
            systemName.leftMoving.setTranslateY(currentView.getTranslateY());
            currentView = systemName.leftMoving;
        }
        double oldX = getTranslateX();
        double newX = getTranslateX() + (direction.value * speed);
        if (newX < board.getMinX() || newX > board.getMaxX()) {
            return;
        }
//        double newX = currentView.getTranslateX() + (direction.value * speed);
        currentView.setTranslateX(newX);
//        getChildren().add(currentView);
        setTranslateX(newX);

        System.out.println("x " + getTranslateX() + " y " + getTranslateY());


        //Setting the value of the transition along the x axis.
        translateTransition.setByX(newX - oldX);

        //Setting the cycle count for the transition
//        translateTransition.setCycleCount(50);

        //Setting auto reverse value to false
        translateTransition.setAutoReverse(false);

        //Playing the animation
//        translateTransition.play();
    }

    public void moveY(Direction direction) {
//        System.out.println("x " + currentView.getTranslateX()+ " y "+ currentView.getTranslateY());

//        getChildren().remove(currentView);
        if (direction == Direction.UP) {
            systemName.upMoving.setTranslateX(currentView.getTranslateX());
            systemName.upMoving.setTranslateY(currentView.getTranslateY());
            currentView = systemName.upMoving;
        } else {
            systemName.downMoving.setTranslateX(currentView.getTranslateX());
            systemName.downMoving.setTranslateY(currentView.getTranslateY());
            currentView = systemName.downMoving;
        }
        double newY = getTranslateY() + (direction.value * speed);
        if (newY < board.getMinY() || newY > board.getMaxY()) {
            return;
        }
//        double newY = currentView.getTranslateY() + (direction.value * speed);
        currentView.setTranslateY(newY);
//        getChildren().add(currentView);
        setTranslateY(newY);

        System.out.println("x " + getTranslateX() + " y " + getTranslateY());

    }

    public void insertBomb() {
        if (bombs.size() >= maxBombs) {
            System.out.println(String.format("max bombs %s, current bombs %s", maxBombs, bombs.size()));
            return;
        }
        Bomb bomb = new Bomb(board, getTranslateX(), getTranslateY(), Statics.BOMB_DELAY, Statics.BOMB_EXPLOSION_RANGE);
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
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, bomb.getDelay());
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
}