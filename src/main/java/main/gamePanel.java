package main;

import entity.Player;
import org.w3c.dom.ls.LSOutput;

import javax.swing.JPanel;
import java.awt.*;

public class gamePanel extends JPanel implements Runnable {
    // configuracion de pantalla
    final int originalTileSize = 16; // Tamaño de pixeles para los personajes y objetos(?
    final int scale = 3;

    public int tileSize = originalTileSize * scale; // escala a 48x48
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixeles
    final int screenHeight = tileSize * maxScreenRow; //576 pixeles

    // FPS
    int FPS = 60;



    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player =new Player(this, keyH);

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public gamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; //0.016 segundos
        double nextDrawTime = System.nanoTime() + drawInterval;


        while(gameThread != null) {

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void update() {

        //Llamamos el metodo update del objeto player
        player.update();
    }
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 =(Graphics2D)g;

        //Llamamos el metodo draw del objeto player
        player.draw(g2);

        g2.dispose();

    }
}
