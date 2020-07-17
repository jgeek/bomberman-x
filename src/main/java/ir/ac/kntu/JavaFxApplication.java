package ir.ac.kntu;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class JavaFxApplication extends Application {

    public static void main(String[] args) {
        launch(args);
        Platform.setImplicitExit(false);
    }

    public void start(Stage stage) throws Exception {

        Circle circle = new Circle();

        //Setting the position of the circle
        circle.setCenterX(300.0f);
        circle.setCenterY(135.0f);

        //Setting the radius of the circle
        circle.setRadius(50.0f);

        //Setting the color of the circle
        circle.setFill(Color.BROWN);

        //Setting the stroke width of the circle
        circle.setStrokeWidth(20);
        Circle circle2 = new Circle(10);
        circle2.setFill(Color.YELLOW);

        //Creating Translate Transition
        TranslateTransition translateTransition = new TranslateTransition();

        //Setting the duration of the transition
        translateTransition.setDuration(Duration.millis(10000));

        //Setting the node for the transition
        translateTransition.setNode(circle);

        //Setting the value of the transition along the x axis.
        translateTransition.setByX(300);

        //Setting the cycle count for the transition
        translateTransition.setCycleCount(50);

        //Setting auto reverse value to false
        translateTransition.setAutoReverse(true);

        //Playing the animation
        translateTransition.play();

        //Creating a Group object
        Group root = new Group(circle);

//        Pane root = new Pane();
        root.setStyle("-fx-border-width: 0 0 5 0;");
        Scene scene = new Scene(root, 800, 600, Color.rgb(240, 240, 240));


        // Setting stage properties
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Faroborz Bobmerman");

        stage.setScene(scene);
        stage.show();
        System.out.println();
    }
}
