package ir.javageek;

import ir.javageek.components.Bomberman;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class Utils {

    public static Image loadImage(String name) {
        return new Image(Objects.requireNonNull(Utils.class.getClassLoader().getResourceAsStream(name)));
    }

    //https://en.it1352.com/article/9ffa0d8090e14708b73e927324582abf.html
    // https://gist.github.com/ABuarque/b440d178c020f5b1add28dad8c3db856
    public static Media loadMedia(String name) {
        var f = new File("/Users/moreka/workspace/codes/bomberman-x/src/main/resources/assets/media/bomb1.wav");
        return new Media(f.toURI().toString());
//        try {
//            return new Media(Objects.requireNonNull(Utils.class.getClassLoader().getResource(name).toURI().toString()));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    public static void playMedia(String name) {
        Media media = Utils.loadMedia(name);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        System.out.println();
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        });
    }

    public static ImageView loadBomberManView(Bomberman.SYSTEM_NAMES name, Bomberman.Direction direction, Bomberman.State state) {
        String fileName = String.format("assets/player_%s/player_%s_%s_%s.png",
                name.getName(),
                name.getName(),
                direction.toString().toLowerCase(), state.toString().toLowerCase());
        ImageView imageView = new ImageView(loadImage(fileName));
        imageView.setFitHeight(Constants.BOMBERMAN_HEIGHT);
        imageView.setFitWidth(Constants.BOMBERMAN_WIDTH);
        return imageView;
    }

    public static Image loadBomberManImage(Bomberman.SYSTEM_NAMES name, Bomberman.Direction direction, Bomberman.State state) {
        String fileName = String.format("assets/player_%s/player_%s_%s_%s.png",
                name.getName(),
                name.getName(),
                direction.toString().toLowerCase(), state.toString().toLowerCase());
        return loadImage(fileName);
    }

    public static ImageView loadOneWayView(Bomberman.Direction direction) {
        String fileName = String.format("assets/map/oneway/oneway_%s.png", direction.toString().toLowerCase());
        return new ImageView(loadImage(fileName));
    }

    public static Timer runLater(Runnable runnable, int delay) {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(() -> {
                    runnable.run();
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, delay);
        return timer;
    }

    public static File[] getResourceFolderFiles(String folder) {
//        ClassLoader loader = Thread.currentThread().getContextClassLoader();
//        URL url = loader.getResource(folder);
        URL url = Utils.class.getClassLoader().getResource(folder);
        String path = url.getPath();
        Arrays.stream(new File(path).listFiles()).forEach(System.out::println);
        return new File(path).listFiles();
    }

    public static List<String> readLine(File file) throws IOException {

        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } finally {
            if (br != null) {
                br.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
    }
}
