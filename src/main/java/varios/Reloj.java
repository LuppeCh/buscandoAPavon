package varios;

import main.UI;
import main.gamePanel;

public class Reloj {

    private final gamePanel gp;
    private double playTime;
    private long startTime = System.nanoTime();
    private UI condicion;

    public int min;
    public int seg;
    public int ms;

    public Reloj(gamePanel gp, UI condicion){
        this.gp = gp;
        this.condicion = condicion;
        this.startTime = System.nanoTime();
    }

    // Metodo de actualizacion del tiempo interno

    public void actualizarTiempo(){
        if (!condicion.gameFinished && !condicion.gameOver){
            long now = System.nanoTime();
            long elapsedNanos = now - startTime;
            playTime += elapsedNanos / 1_000_000.0;
            startTime = now;

            min = (int) (playTime / 1000) / 60;
            seg = (int) (playTime / 1000) % 60;
            ms = (int) (playTime % 1000);
        }
    }

    public void agregarTiempo(int segundosExtra) {
        playTime += segundosExtra * 1000.0; // convierto segundos a milisegundos
    }

    //Calculamos la condicion de derrota
    public void derrota() {
        if(min >= 7){
            gp.gameState = gp.gameOverState;
            gp.playSE(4);
        }
    }

    //Reiniciar el tiempo
    public void reiniciarTiempo() {
        playTime = 0;
        min = 0;
        seg = 0;
        ms = 0;
        startTime = System.nanoTime();
    }
    //para usarlo: reloj.reiniciarTiempo();


    // Gets para las variables
    public int getMinutos() {
        return min;
    }

    public int getSegundos() {
        return seg;
    }

    public int getMilisegundos() {
        return ms;
    }


}
