package main;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sonido {
    Clip clip;
    URL sonidoURL[] = new URL[30];

    public Sonido() {
        sonidoURL[0] = getClass().getResource("/sonido/Intro.wav");
        sonidoURL[1] = getClass().getResource("/sonido/Pan de ajo.wav");
        sonidoURL[2] = getClass().getResource("/sonido/Pierde.wav");
        sonidoURL[3] = getClass().getResource("/sonido/Victoria.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sonidoURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
