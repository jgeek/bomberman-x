package ir.ac.kntu.components;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PlayerBoardSection extends VBox {

    private final Bomberman bomberman;
    //    private final Rectangle rect;
    private Image image;
    TextField text = new TextField("0");

    public PlayerBoardSection(Bomberman bomberman) {
        this.bomberman = bomberman;
        ImageView imageView = new ImageView(bomberman.getSystemName().downStanding);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        text.setFont(Font.font(20));
        text.setDisable(true);
        getChildren().addAll(imageView, text);
        bomberman.setScoreBoard(this);
        setScore(bomberman.getScore());

    }

    public void setScore(int score) {
        text.setText(score + "");
    }
}
