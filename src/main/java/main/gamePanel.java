package main;

import Tiles.TileManager;
import entity.Player;
import object.SuperObject;
import org.w3c.dom.ls.LSOutput;

import javax.swing.JPanel;
import java.awt.*;

public class gamePanel extends JPanel implements Runnable {
    // configuracion de pantalla
    final int originalTileSize = 16; // Tamaño de pixeles para los personajes y objetos(?
    final int scale = 3;

    public int tileSize = originalTileSize * scale; // escala a 48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixeles
    public final int screenHeight = tileSize * maxScreenRow; //576 pixeles


    // configuraciones del mundo
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight= tileSize * maxWorldRow;



    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    public CollisionChecker cChecker = new CollisionChecker(this);

    public AssetSetter aSetter = new AssetSetter(this);

    public Player player =new Player(this, keyH);

    // creamos un array que va a tener 10 espacios para objetos, esto se puede modificar a medida de ser necesario
    public SuperObject obj[] = new SuperObject[10];


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

    public void setupGame(){

        aSetter.setObject();
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
        //Llamamos primero a las tiles y después al player, para priorizar la "capa" Tiles

        tileM.draw(g2);

        for (int i =0; i < obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }

        //Llamamos el metodo draw del objeto player
        player.draw(g2);

        g2.dispose();

    }
}
