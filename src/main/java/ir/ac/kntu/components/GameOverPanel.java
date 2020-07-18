package ir.ac.kntu.components;

import ir.ac.kntu.Constants;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.stream.Collectors;

public class GameOverPanel extends Pane {

    public GameOverPanel(GameBoard.GameStop gameStop, List<Bomberman> bombermans) {

        List<Bomberman> lives = bombermans.stream().filter(Bomberman::isAlive).collect(Collectors.toList());
        if (lives.size() > 0) {
            switch (gameStop) {
                case TIMEOUT:
                    int score = Constants.GAME_SCORE / lives.size();
                    lives.forEach(bm -> bm.updateScore(score));
                    break;
                case WINNER:
                    lives.get(0).updateScore(Constants.GAME_SCORE);
                    break;
                case NONE:
                    break;
            }
        }

        HBox mans = new HBox();
        bombermans.forEach(b -> mans.getChildren().add(new PlayerBoardSection(b)));
        mans.setPrefSize(600, 300);
        getChildren().add(mans);
//        setPrefSize(600, 300);
//        setWidth(600);
//        setHeight(300);
//        setMaxSize(600, 300);
//        winners.setTranslateX(maxX + Constants.TILE_SIZE);
//        winners.setTranslateY(0);
    }
}
