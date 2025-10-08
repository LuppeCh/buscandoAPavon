package main;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sonido {

    // Array para almacenar TODOS los clips (música y SE). Capacidad de 30.
    Clip clip[] = new Clip[30];
    URL sonidoURL[] = new URL[30];
    int musicIndex = -1; // Índice de la música que se está reproduciendo

    public Sonido() {

        // EFECTOS DE SONIDO (Asignación de índices 3 en adelante)
        sonidoURL[0] = getClass().getResource("/sonido/Pan de ajo.wav");
        sonidoURL[1] = getClass().getResource("/sonido/Pierde.wav");
        sonidoURL[2] = getClass().getResource("/sonido/Victoria.wav");
        sonidoURL[3] = getClass().getResource("/sonido/Derrota.wav");
        sonidoURL[4] = getClass().getResource("/sonido/cursor.wav");
        sonidoURL[5] = getClass().getResource("/sonido/aiaiai.wav");
        sonidoURL[6] = getClass().getResource("/sonido/MG.wav");


        // ⭐ MÚSICA DE FONDO (Asignación de índices 0, 1, 2)
        sonidoURL[7] = getClass().getResource("/sonido/fondo1.wav"); // Fondo para Mapa 1
        sonidoURL[8] = getClass().getResource("/sonido/fondo2.wav"); // Fondo para Mapa 2
        sonidoURL[9] = getClass().getResource("/sonido/fondo3.wav"); // Fondo para Mapa 3

        // ⭐ CLAVE: Cargar y Abrir TODOS los clips UNA SOLA VEZ
        for (int i = 0; i < sonidoURL.length; i++) {
            if (sonidoURL[i] != null) {
                setFile(i);
            }
        }
    }

    /** Carga y abre el archivo en el Clip[i]. Solo se llama en el constructor. */
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sonidoURL[i]);
            clip[i] = AudioSystem.getClip();
            clip[i].open(ais);
            // System.out.println("✅ Éxito: Clip " + i + " cargado."); // Descomentar para debug
        } catch (Exception e) {
            System.err.println("❌ ERROR: El archivo " + i + " (" + sonidoURL[i] + ") falló la carga. Revisa la ruta o el formato (debe ser WAV/PCM).");
            // e.printStackTrace(); // Descomentar para ver la traza del error
        }
    }

    // ----------------------------------------------------------------------
    // CONTROL DE MÚSICA DE FONDO (BUCLE)
    // ----------------------------------------------------------------------

    /** Inicia o cambia la música de fondo en bucle. */
    public void playMusic(int i) {
        // 1. Si ya está sonando la música correcta, salimos.
        if (musicIndex == i && clip[i] != null && clip[i].isRunning()) {
            return;
        }

        // 2. Si hay otra música sonando, la detenemos.
        if (musicIndex != -1 && clip[musicIndex] != null && clip[musicIndex].isRunning()) {
            clip[musicIndex].stop();
        }

        // 3. Iniciamos la nueva música
        if (clip[i] != null) {
            musicIndex = i;
            clip[i].setFramePosition(0); // Reiniciamos al inicio
            clip[i].loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /** Detiene la música de fondo. */
    public void stopMusic() {
        // Si hay una música rastreada, la detenemos.
        if (musicIndex != -1 && clip[musicIndex] != null && clip[musicIndex].isRunning()) {
            clip[musicIndex].stop();
        }
        musicIndex = -1; // Ninguna música está activa
    }

    // ----------------------------------------------------------------------
    // CONTROL DE EFECTOS DE SONIDO (Corte y Reproducción Única)
    // ----------------------------------------------------------------------

    /** Reproduce un efecto de sonido, deteniendo la música de fondo. */
    public void playSE(int i) {

        // ⭐ CAMBIO CRÍTICO: Detener explícitamente los clips de fondo (índices 7, 8, 9)
        for (int j = 7; j <= 9; j++) {
            if (clip[j] != null && clip[j].isRunning()) {
                clip[j].stop();
            }
        }
        // Opcional, pero bueno para la gestión del índice:
        musicIndex = -1;

        if (clip[i] != null) {
            clip[i].stop();
            clip[i].setFramePosition(0);
            clip[i].start();
        }
    }
}