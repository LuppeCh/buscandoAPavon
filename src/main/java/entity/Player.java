package entity;

import main.gamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    gamePanel gp;
    KeyHandler keyH;

    public Player (gamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues() ;
        getPlayersImage();
    }
    public void setDefaultValues() {

        x = 100;
        y = 100;
        speed = 4;
        direction = "right";
    }
    //Metodo para usar las imagenes
    public void getPlayersImage(){
        try{

            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    //Metodo para mandar los controles del jugador
    public void update() {
        if(keyH.upPressed == true ||
                keyH.downPressed == true ||
                keyH.leftPressed == true ||
                keyH.rightPressed == true ){
            if(keyH.upPressed == true) {
                direction = "Arriba";
                y -= speed;
            }
            else if(keyH.downPressed == true) {
                direction = "Abajo";
                y += speed;
            }
            else if(keyH.leftPressed == true) {
                direction = "Izquierda";
                x -= speed;
            }
            else if(keyH.rightPressed == true) {
                direction = "Derecha";
                x += speed;
            }
            spriteCounter++;
            if(spriteCounter>10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        switch(direction) {
            case "Arriba":
                if(spriteNum ==1) {
                    image = up1;
                }
                if (spriteNum == 2){
                    image =up2;
                }
                break;
            case "Abajo":
                if(spriteNum ==1) {
                    image = down1;
                }
                if (spriteNum == 2){
                    image =down2;
                }
                break;
            case "Izquierda":
                if(spriteNum ==1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "Derecha":
                if(spriteNum ==1) {
                    image = right1;
                }
                if (spriteNum == 2){
                    image =right2;
                }
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
