package ir.ac.kntu.components;

import ir.ac.kntu.Constants;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

public class GameOverPanel extends Pane {

    public GameOverPanel(List<Bomberman> bombermans) {

        HBox winners = new HBox();
        bombermans.forEach(b -> winners.getChildren().add(new PlayerBoardSection(b)));
        getChildren().add(winners);
//        setPrefSize(600, 300);
        setWidth(600);
        setHeight(300);
        setMaxSize(600, 300);
//        winners.setTranslateX(maxX + Constants.TILE_SIZE);
//        winners.setTranslateY(0);
    }
}
