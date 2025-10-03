package main;

import Tiles.TileManager;
import entity.Entity;
import entity.Player;
import varios.Reloj;
import javax.swing.JFrame;

// JavaFX
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

// Swing
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

// AWT
import java.awt.*;

// Otros
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class gamePanel extends JPanel implements Runnable {
        // configuración de pantalla
        final int originalTileSize = 16;
        final int scale = 4;

    public VideosSwing videos;
    public int tileSize = originalTileSize * scale;
        public final int maxScreenCol = 16;
        public final int maxScreenRow = 12;
        public final int screenWidth = tileSize * maxScreenCol;
        public final int screenHeight = tileSize * maxScreenRow;
        public boolean videoMostrado = false; // para el video de salida de la tienda
        public boolean videoMostrado2 = false;


    // configuraciones del mundo
        public final int maxWorldCol = 50;
        public final int maxWorldRow = 50;
        public final int maxMap =10;
        public int currentMap = 0;


        // FPS
        int FPS = 60;

        // Reloj
        public Reloj reloj;
        TileManager tileM = new TileManager(this);

        public KeyHandler keyH = new KeyHandler(this);
        Sonido sonido = new Sonido();
        Thread gameThread;

        public CollisionChecker cChecker = new CollisionChecker(this);
        public AssetSetter aSetter = new AssetSetter(this);
        public UI ui = new UI(this);

        // ENTITY AND OBJECT
        public Player player = new Player(this, keyH);
        public EventHandler eHandler = new EventHandler(this);
        public Entity obj[][] = new Entity[maxMap][10];   // objetos
        public Entity npc[][] = new Entity[maxMap][10];   // npcs
         public boolean spawnPan = false;

        ArrayList<Entity> entityList = new ArrayList<>();



    //GAME STATES
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int gameOverState = 4;
    public final int characterState = 5;
    public final int videoState = 6;


    public gamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.reloj = new Reloj(this, ui);

        videos = new VideosSwing(screenWidth, screenHeight);

        this.setLayout(null); // layout nulo para poder posicionar libremente
        videos.getFXPanel().setBounds(0, 0, screenWidth, screenHeight);
        videos.getFXPanel().setVisible(false);
        this.add(videos.getFXPanel());

        // Agregar el JFXPanel al JPanel del juego
        this.add(videos.getFXPanel());
        videos.getFXPanel().setVisible(false);
        videos.loadVideo("pavon", "res/Videos/MercadoPatio.mp4");
        videos.loadVideo("monu", "res/Videos/Monumento.mp4");


    }

    public void showVideo(String key) {
        System.out.println("=== Mostrando video: " + key + " ===");

        // Pausar el juego mientras corre el video
        gameState = videoState;

        // Hacer visible el panel de video
        videos.getFXPanel().setVisible(true);
        videos.getFXPanel().revalidate();
        videos.getFXPanel().repaint();

        Platform.runLater(() -> {
            videos.play(key);

            // Cuando termine, ocultar panel y volver a jugar
            if (videos.currentPlayer != null) {
                videos.currentPlayer.setOnEndOfMedia(() -> {
                    Platform.runLater(() -> {
                        videos.stop();
                        videos.getFXPanel().setVisible(false);
                        gameState = playState;
                    });
                });
            }
        });
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
       // playMusic(0);
        gameState = titleState;

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

            while (gameThread != null) {
                update();
                repaint();
                try {
                    double remainingTime = nextDrawTime - System.nanoTime();
                    remainingTime = remainingTime / 1000000;

                    if (remainingTime < 0) {
                        remainingTime = 0;
                    }
                    Thread.sleep((long) remainingTime);

                    nextDrawTime += drawInterval;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
    }

    public void volverAJugar() {
        player.setDefaultValues();
        reloj.reiniciarTiempo();
        aSetter.setObject();
        aSetter.setNPC();
    }

    public void update() {
            if(gameState == playState){
                // Actualizar player
                player.update();

                // Actualizar NPCs
                for (int i = 0; i < npc[1].length; i++) {
                    if (npc[currentMap][i] != null) {
                        npc[currentMap][i].update();
                    }
                }

                // Revisar eventos
                eHandler.checkEvent(); // <- esto es clave

                // Actualizar reloj
                reloj.actualizarTiempo();
                reloj.derrota();
            }

            if(gameState == pauseState) {
                // juego en pausa, no actualizar nada
            }


        //pausa o reanudacion del juego
        if(gameState == playState){

            //Llamamos el metodo update del objeto player
            player.update();

            //Llamamos el metodo update del objeto NPC
            
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            // actualizar reloj y chequear derrota
            reloj.actualizarTiempo();
            reloj.derrota();
        }
        
        if(gameState == pauseState) {
            // si está en pauseState no hace nada
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 =(Graphics2D)g;

        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        //Llamamos primero a las tiles y después al player, para priorizar la "capa" Tiles.

        tileM.draw(g2);



        // TITLE SCREEN
        if(gameState == titleState) {
            ui.draw(g2);

        }
        if (gameState == videoState) {
            // Solo dejamos que se vea el JFXPanel
            // No dibujamos nada del juego debajo
            return;
        }

        // OTHERS
        else {
            // TILE
            tileM.draw(g2);
        }

        //Llamamos el metodo draw del objeto player
        player.draw(g2);
        // UI
        ui.draw(g2);

        // add entities
        entityList.add(player);

        for (Entity[] row : npc) {
            for (Entity n : row) {
                if (n != null) entityList.add(n);
            }
        }

        for (Entity[] row : obj) {
            for (Entity o : row) {
                if (o != null) entityList.add(o);
            }
        }

        // SORT
        Collections.sort(entityList, new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                return Integer.compare(o1.worldY, o2.worldY);
            }
        });

        // DRAW ENTITIES
        for (Entity e : entityList) {
            e.draw(g2);
        }

        // EMPTY ENTITY LIST
        entityList.clear();

        // UI
        ui.draw(g2);

        // Debug
        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.setFont(new Font("Arial",Font.PLAIN,20));
//            System.out.println("Draw time: " + passed);
            int x = 10;
            int y = 400;

            g2.drawString("World X: " + player.worldX, x, y); y+= 22;
            g2.drawString("World Y: " + player.worldY, x, y); y+= 22;
            g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, y); y+= 22;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y); y+= 22;
            g2.drawString("Draw time: " + passed, x, y);
        }

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