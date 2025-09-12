package entity;

import main.gamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    gamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    // Contadores de objetos
    public int gpsCount = 0;
    public int panDeAjoCount = 0;
    public int valePorComidaCount = 0;

    public Player(gamePanel gp, KeyHandler keyH) {
        this.gp = gp;
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
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "Abajo";
    }

    public void getPlayersImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/arriba.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/arriba2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/pixil-frame-0 (1).png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/pixil-frame-0.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/izquierda.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/izquierda2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/derecha.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/derecha2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(gp.ui.gameFinished || gp.ui.gameOver){
            return;
        }
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) direction = "Arriba";
            else if (keyH.downPressed) direction = "Abajo";
            else if (keyH.leftPressed) direction = "Izquierda";
            else if (keyH.rightPressed) direction = "Derecha";

            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            if (!collisionOn) {
                switch (direction) {
                    case "Arriba" -> worldY -= speed;
                    case "Abajo" -> worldY += speed;
                    case "Izquierda" -> worldX -= speed;
                    case "Derecha" -> worldX += speed;
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

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "Arriba" -> image = (spriteNum == 1) ? up1 : up2;
            case "Abajo" -> image = (spriteNum == 1) ? down1 : down2;
            case "Izquierda" -> image = (spriteNum == 1) ? left1 : left2;
            case "Derecha" -> image = (spriteNum == 1) ? right1 : right2;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}

