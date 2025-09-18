package entity;


import main.UtilityTool;
import main.gamePanel;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    gamePanel gp;
    public int worldX, worldY;

    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    //colision del jugador
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public Entity(gamePanel gp){
        this.gp =gp;
    }

    // Carga de las imagenes de las entidades
    BufferedImage setup(String imagePatch) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage Image = null;
        try {
            Image = ImageIO.read(getClass().getResourceAsStream(imagePatch + ".png"));
            Image = uTool.scaleImage(Image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Image;
    }

}
