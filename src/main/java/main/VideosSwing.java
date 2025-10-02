package main;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;

public class VideosSwing {

    private JFXPanel fxPanel;                // Panel que incrusta JavaFX
    private MediaPlayer currentPlayer;
    private MediaView mediaView;
    private HashMap<String, Media> mediaMap; // Guarda los videos cargados

    public VideosSwing(int width, int height) {
        // Inicializar JFXPanel y MediaView
        fxPanel = new JFXPanel();
        fxPanel.setSize(width, height);

        mediaView = new MediaView();
        mediaMap = new HashMap<>();
    }

    /**
     * Devuelve el JFXPanel para agregarlo a tu JPanel
     */
    public JFXPanel getFXPanel() {
        return fxPanel;
    }

    /**
     * Carga un video desde res/Videos
     * @param key clave para identificar el video
     * @param videoFilePath ruta relativa al proyecto (ej: "res/Videos/1002.mp4")
     */
    public void loadVideo(String key, String videoFilePath) {
        File file = new File(videoFilePath);
        if (!file.exists()) {
            System.out.println("Video no encontrado: " + videoFilePath);
            return;
        }

        Media media = new Media(file.toURI().toString());
        mediaMap.put(key, media);
    }

    /**
     * Reproduce un video previamente cargado
     * @param key clave del video
     */
    public void play(String key) {
        Media media = mediaMap.get(key);
        if (media == null) {
            System.out.println("Video no encontrado: " + key);
            return;
        }

        // Detener video actual si existe
        if (currentPlayer != null) {
            currentPlayer.stop();
        }

        currentPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(currentPlayer);

        // Crear escena JavaFX dentro de JFXPanel
        StackPane root = new StackPane(mediaView);
        Scene scene = new Scene(root, fxPanel.getWidth(), fxPanel.getHeight());
        fxPanel.setScene(scene);

        currentPlayer.play();
    }

    public void pause() {
        if (currentPlayer != null) currentPlayer.pause();
    }

    public void resume() {
        if (currentPlayer != null) currentPlayer.play();
    }

    public void stop() {
        if (currentPlayer != null) currentPlayer.stop();
    }
}
