package ir.ac.kntu.components;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.*;

public class PlayerBoardSection extends VBox {

    private final Bomberman bomberman;
    //    private final Rectangle rect;
    private Image image;
    TextField text = new TextField("0");

    public PlayerBoardSection(Bomberman bomberman) {
        this.bomberman = bomberman;
//        rect = new Rectangle(Constants.MENU_ITEM_WIDTH, Constants.MENU_ITEM_HEIGHT);
//        rect.setFill(Color.G);
        ImageView imageView = new ImageView(bomberman.getSystemName().downStanding);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        text.setFont(Font.font(20));
        text.setDisable(true);
        getChildren().addAll(imageView, text);
        bomberman.setScoreBoard(this);
    }

    public void setScore(int score) {
        text.setText(score + "");
    }
}
