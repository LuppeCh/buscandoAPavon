package entity;

import main.UtilityTool;
import main.gamePanel;
import main.KeyHandler;
import varios.Direccion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    // Contadores de objetos
    public int gpsCount = 0;
    public int panDeAjoCount = 0;
    public int valePorComidaCount = 0;

    public Player(gamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayersImage();
    }

    public void setDefaultValues() {
        worldX = 23 * gp.tileSize; // posicion del Player
        worldY = 21 * gp.tileSize; // posicion del Player
        speed = 4;
        direction = Direccion.Abajo;
    }

    public void getPlayersImage() {

        up1 = setup("/player/arriba");
        up2 = setup("/player/arriba2");
        down1 = setup("/player/abajo");
        down2 = setup("/player/abajo2");
        left1 = setup("/player/izquierda");
        left2 = setup("/player/izquierda2");
        right1 = setup("/player/derecha");
        right2 = setup("/player/derecha2");
    }

    public void update() {
        if (gp.ui.gameFinished || gp.ui.gameOver) {
            return;
        }
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) direction = Direccion.Arriba;
            else if (keyH.downPressed) direction = Direccion.Abajo;
            else if (keyH.leftPressed) direction = Direccion.Izquierda;
            else if (keyH.rightPressed) direction = Direccion.Derecha;

            // corroboramos la colision con el entorno
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // corroboramos la colision con los objetos
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // corroboramos la colision con NPCs
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //Condicion para colision
            if (!collisionOn) {
                switch (direction) {
                    case Arriba -> worldY -= speed;
                    case Abajo -> worldY += speed;
                    case Izquierda -> worldX -= speed;
                    case Derecha -> worldX += speed;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }

    // Método para recoger objetos, insensible a mayúsculas y espacios
    public void pickUpObject(int i) {
        if (i != 999 && gp.obj[i] != null) {

            String objectName = gp.obj[i].name.toLowerCase().trim();
            System.out.println("Objeto detectado: " + objectName);

            if (objectName.contains("gps")) {
                gpsCount++;
                gp.obj[i] = null;
                gp.playSE(1);
                gp.ui.showMessage("Tienes el GPS de la nave!!");
                System.out.println("GPS: " + gpsCount);

            } else if (objectName.contains("pan de ajo")) {
                panDeAjoCount++;
                gp.obj[i] = null;
                gp.playSE(1);
                gp.ui.showMessage("Tienes el pan de ajo!!");
                System.out.println("Pan de Ajo: " + panDeAjoCount);

            } else if (objectName.contains("vale por comida")) {
                valePorComidaCount++;
                gp.obj[i] = null;
                gp.playSE(1);
                speed += 1;
                gp.ui.showMessage("Tienes el vale por comida!!");
                System.out.println("Vale por comida: " + valePorComidaCount);

            } else if (objectName.contains("chest")) {
                gp.ui.gameFinished = true;
                gp.stopMusic();
                gp.playSE(3);
            }
        }
    }

    public void interactNPC(int i) {
        if (i != 999){
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
        gp.keyH.enterPressed = false;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case Arriba -> image = (spriteNum == 1) ? up1 : up2;
            case Abajo -> image = (spriteNum == 1) ? down1 : down2;
            case Izquierda -> image = (spriteNum == 1) ? left1 : left2;
            case Derecha -> image = (spriteNum == 1) ? right1 : right2;
        }
        g2.drawImage(image, screenX, screenY, null);
    }
}

