package ir.ac.kntu.components;

import ir.ac.kntu.Constants;
import ir.ac.kntu.navigation.MainPanel;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class GameOverPanel extends StackPane {

    public GameOverPanel(Scene scene, MainPanel mainPanel, GameBoard.GameStop gameStop, List<Bomberman> bombermans) {

//        setPrefSize(600, 250);
        List<Bomberman> lives = bombermans.stream().filter(Bomberman::isAlive).collect(Collectors.toList());
        if (lives.size() > 0) {
            switch (gameStop) {
                case TIMEOUT:
                    int score = Constants.GAME_SCORE / lives.size();
                    // if all men are alive, so they get nothing
                    if (lives.size() != bombermans.size()) {
                        lives.forEach(bm -> bm.updateScore(score));
                    }
                    break;
                case WINNER:
                    lives.get(0).updateScore(Constants.GAME_SCORE);
                    break;
                case NONE:
                    break;
            }
        } else {
            // set zero
            bombermans.forEach(bm -> bm.updateScore(-bm.getScore()));
        }

        Rectangle background = new Rectangle(800, 200);
        background.setOpacity(.9);
        background.setFill(Color.rgb(233, 237, 232));

        HBox hBox = new HBox();
        bombermans.forEach(b -> hBox.getChildren().add(new PlayerBoardSection(b)));

        Button button = new Button("Main Menu");
        button.setOnMouseClicked(event -> {
            scene.setRoot(mainPanel);
        });

        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(0);
        box.getChildren().addAll(hBox, button);

        getChildren().addAll(background, box);
        setAlignment(Pos.CENTER);
        setTranslateX(100);
        setTranslateY(200);
    }
}
