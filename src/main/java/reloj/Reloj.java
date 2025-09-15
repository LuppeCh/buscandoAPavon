package reloj;

import main.UI;

public class Reloj {

    private double playTime;
    private long startTime = System.nanoTime();
    private UI condicion;

    public Reloj(UI condicion){
        this.condicion = condicion;
        this.startTime = System.nanoTime();
    }

    // Metodo de actualizacion del tiempo interno

    public void actualizarTiempo(){
        if (!condicion.gameFinished && !condicion.gameOver){
            long now = System.nanoTime();
            long elapsedNanos = now - startTime;
            playTime = elapsedNanos / 1_000_000.0;
        }
    }

    // Gets para las variables
    public int getMinutos() {
        return (int)(playTime / 1000) / 60;
    }

    public int getSegundos() {
        return (int)(playTime / 1000) % 60;
    }

    public int getMilisegundos() {
        return (int)(playTime % 1000);
    }
}
