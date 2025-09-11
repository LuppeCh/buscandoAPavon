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


    public Player(gamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;


        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 32;
        solidArea.height = 32;


        setDefaultValues();
        getPlayersImage();
    }

    public void setDefaultValues() {


        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "Abajo";

    }

    //Metodo para usar las imagenes
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

    //Metodo para mandar los controles del jugador
    public void update() {
        if (keyH.upPressed == true ||
                keyH.downPressed == true ||
                keyH.leftPressed == true ||
                keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "Arriba";

            } else if (keyH.downPressed == true) {
                direction = "Abajo";
            } else if (keyH.leftPressed == true) {
                direction = "Izquierda";
            } else if (keyH.rightPressed == true) {
                direction = "Derecha";
            }
//check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);


            // check colision de objeto
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

// si la collision false, el jugador se puede mover
            if (collisionOn == false) {
                switch (direction) {
                    case "Arriba":
                        worldY -= speed;
                        break;
                    case "Abajo":
                        worldY += speed;
                        break;
                    case "Izquierda":
                        worldX -= speed;
                        break;
                    case "Derecha":
                        worldX += speed;
                        break;
                }

            }


            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void  pickUpObject( int i){
        if(i != 999){
            gp.obj[i] = null;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        switch (direction) {
            case "Arriba":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "Abajo":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "Izquierda":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "Derecha":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}
