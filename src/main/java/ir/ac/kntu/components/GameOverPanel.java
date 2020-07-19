package ir.ac.kntu.components;

import ir.ac.kntu.Constants;
import ir.ac.kntu.navigation.MainPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class GameOverPanel extends VBox {

    public GameOverPanel(Stage newStage, Scene scene, MainPanel mainPanel, GameBoard.GameStop gameStop, List<Bomberman> bombermans) {

        setPrefSize(600, 250);
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

        HBox mans = new HBox();
        bombermans.forEach(b -> mans.getChildren().add(new PlayerBoardSection(b)));
//        mans.setPrefSize(600, 300);

        Button button = new Button("Back to main manu");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.setRoot(mainPanel);
                newStage.close();
            }
        });

        setSpacing(20);
        getChildren().addAll(mans, button);
    }
}
