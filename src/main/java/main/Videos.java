package main;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

//import java.util.HashMap;
//
//public class Videos {
//
//    private Stage stage;
//    private MediaPlayer currentPlayer;
//    private MediaView mediaView;
//    private HashMap<String, Media> mediaMap;
//
//    public Videos(Stage stage) {
//        this.stage = stage;
//        mediaMap = new HashMap<>();
//        mediaView = new MediaView();
//    }
//
//    /**
//     * Carga un video desde la ruta dentro de resources
//     * @param key clave para identificar el video
//     * @param videoURL ruta del video en classpath (ej: getClass().getResource("/videos/intro.mp4").toExternalForm())
//     */
//    public void loadVideo(String key, String videoURL) {
//        Media media = new Media(videoURL);
//        mediaMap.put(key, media);
//    }
//
//    /**
//     * Reproduce un video previamente cargado
//     * @param key clave del video
//     */
//    public void play(String key) {
//        Media media = mediaMap.get(key);
//        if (media == null) {
//            System.out.println("Video no encontrado: " + key);
//            return;
//        }
//
//        // Detener video actual si existe
//        if (currentPlayer != null) {
//            currentPlayer.stop();
//        }
//
//        currentPlayer = new MediaPlayer(media);
//        mediaView.setMediaPlayer(currentPlayer);
//
//        // Envolver MediaView en un StackPane para Scene
//        StackPane root = new StackPane(mediaView);
//        Scene scene = new Scene(root, 800, 600); // tamaño de la ventana, podés ajustarlo
//        stage.setScene(scene);
//        stage.show();
//
//        currentPlayer.play();
//    }
//
//    public void pause() {
//        if (currentPlayer != null) {
//            currentPlayer.pause();
//        }
//    }
//
//    public void resume() {
//        if (currentPlayer != null) {
//            currentPlayer.play();
//        }
//    }
//
//    public void stop() {
//        if (currentPlayer != null) {
//            currentPlayer.stop();
//        }
//    }
//
//}
