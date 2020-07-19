package ir.ac.kntu.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class PlayerBoardSection extends Pane {

    private final Bomberman bomberman;
    private final ImageView imageView;
    Label scoreField = new Label("0");

    public PlayerBoardSection(Bomberman bomberman) {

        this.bomberman = bomberman;

        imageView = new ImageView(bomberman.getCurrentImage());
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);

        scoreField.setFont(Font.font(20));
        scoreField.setStyle("-fx-background-color: rgb(111, 106, 212);");
        scoreField.setTextFill(Color.WHITE);
        scoreField.setPrefWidth(200);
        scoreField.setAlignment(Pos.CENTER);

        Label nameField = new Label(bomberman.getUsername());
        nameField.setFont(Font.font(15));
        nameField.setStyle("-fx-background-color: rgb(156, 16, 79);");
        nameField.setTextFill(Color.WHITE);
        nameField.setPrefWidth(200);
        nameField.setAlignment(Pos.CENTER);

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(imageView, nameField, scoreField);
        box.setBorder(new Border(new BorderStroke(Color.GREEN,
                BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        getChildren().add(box);
        bomberman.setScoreBoard(this);
        setScore(bomberman.getScore());
        setPrefWidth(200);
    }

    public void setScore(int score) {
        scoreField.setText(score + "");
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }
}
