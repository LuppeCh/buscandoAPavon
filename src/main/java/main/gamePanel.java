package main;

import Tiles.TileManager;
import entity.Entity;
import entity.Player;
import object.SuperObject;
import org.w3c.dom.ls.LSOutput;
import varios.Reloj;

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


    // FPS
    int FPS = 60;

    // Reloj
    private Reloj reloj;
    TileManager tileM = new TileManager(this);

    public KeyHandler keyH = new KeyHandler(this);
    Sonido sonido = new Sonido ();
    Thread gameThread;

    public CollisionChecker cChecker = new CollisionChecker(this);

    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);

    // ENTITY AND OBJECT
    public Player player =new Player(this, keyH);

    // creamos un array que va a tener 10 espacios para objetos, esto se puede modificar a medida de ser necesario
    public SuperObject obj[] = new SuperObject[10];

    //NPC
    public Entity npc[] = new Entity[10];

    //GAME STATES
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public gamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.reloj = new Reloj(ui);
    }

    public void setupGame(){

        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        gameState = playState;
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
        //pausa o reanudacion del juego
        if(gameState == playState){
            //Llamamos el metodo update del objeto player
            player.update();
            //Llamamos el metodo update del objeto NPC
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }

        }
        if(gameState == pauseState) {
            //Nada
        }

    }


    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 =(Graphics2D)g;
        //Llamamos primero a las tiles y después al player, para priorizar la "capa" Tiles.

        // DEBUG.
        long drawStart = 0;
        if(keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }


        tileM.draw(g2);
        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw time: " + passed, 10, 400);
            System.out.println("Draw time: " + passed);
        }

        //Objeto
        for (int i =0; i < obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }

        // NPC
        for (int i =0; i < npc.length; i++){
            if(npc[i] != null){
                npc[i].draw(g2);
            }
        }

        //Llamamos el metodo draw del objeto player
        player.draw(g2);
        // UI
        ui.draw(g2);

        g2.dispose();

    }
    public void playMusic(int i) {

        sonido.setFile(i);
        sonido.play();
        sonido.loop();
    }
    public void stopMusic() {

        sonido.stop();
    }
    public void playSE(int i) {

        sonido.setFile(i);
        sonido.play();
    }
}
