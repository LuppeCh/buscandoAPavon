package main;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class VideosSwing {

    private final JFXPanel fxPanel;
    public MediaPlayer currentPlayer;
    private final MediaView mediaView;
    private final HashMap<String, Media> mediaMap;
    private boolean fxInitialized = false;

    public VideosSwing(int width, int height) {
        // Inicializar JavaFX (solo una vez)
        if (!fxInitialized) {
            try {
                Platform.startup(() -> {}); // <- aquí se inicializa JavaFX
            } catch (IllegalStateException e) {}
            fxInitialized = true;
        }

        fxPanel = new JFXPanel();
        fxPanel.setPreferredSize(new Dimension(width, height));
        mediaView = new MediaView();
        mediaMap = new HashMap<>();
    }

    public JFXPanel getFXPanel() {
        return fxPanel;
    }

    public void loadVideo(String key, String videoFilePath) {
        File file = new File(videoFilePath);
        if (!file.exists()) {
            System.out.println("❌ Video no encontrado: " + videoFilePath);
            return;
        }
        Media media = new Media(file.toURI().toString());
        mediaMap.put(key, media);
    }

    public void play(String key) {
        Media media = mediaMap.get(key);
        if (media == null) {
            System.out.println("⚠️ Video no cargado: " + key);
            return;
        }

        // Detener video actual si existe
        if (currentPlayer != null) {
            stop(); // <- llamamos a stop() que ahora usa Platform.runLater
        }

        currentPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(currentPlayer);

        // Crear escena JavaFX dentro de JFXPanel y reproducir
        Platform.runLater(() -> {  // <- clave, todo esto corre en hilo de JavaFX
            StackPane root = new StackPane(mediaView);
            Scene scene = new Scene(root, fxPanel.getWidth(), fxPanel.getHeight());
            fxPanel.setScene(scene);
            currentPlayer.play();
        });
    }

    // ----------------------------
    // Modificaciones clave para evitar que se trabe el juego
    // ----------------------------

    public void pause() {
        if (currentPlayer != null) {
            Platform.runLater(() -> currentPlayer.pause()); // <- aquí
        }
    }

    public void resume() {
        if (currentPlayer != null) {
            Platform.runLater(() -> currentPlayer.play()); // <- aquí
        }
    }

    public void stop() {
        if (currentPlayer != null) {
            Platform.runLater(() -> currentPlayer.stop()); // <- aquí
        }
    }
}
