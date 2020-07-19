package ir.ac.kntu.components;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.TimerTask;

public class CountDownTimer extends TimerTask {

    private final Label label;
    private int count;

    public CountDownTimer(Label timerLabel, int count) {
        this.count = count;
        this.label = timerLabel;
    }

    @Override
    public void run() {
        if (count == 0)
            return;
        count--;
        Platform.runLater(() -> label.setText(count + ""));
    }
}
