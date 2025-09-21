package entity;


import main.UtilityTool;
import main.gamePanel;
import varios.Direccion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    gamePanel gp;
    public int worldX, worldY;

    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public Direccion direction = Direccion.Abajo; // probar cambiar el primer Direction por String

    public int spriteCounter = 0;
    public int spriteNum = 1;

    //colision del jugador
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    //Inicializamos la variable para que no se cambie la direccion constantemente
    public int actionLockCounter = 0;

    //Dialogos
    String dialogues[] = new String[20];
    int dialogueIndex = 0;

    public BufferedImage image;
    public String name;
    public boolean collision = false;



    public Entity(gamePanel gp){
        this.gp = gp;
    }

    //Accion del NPC
    public void setAction() { }
    public void speak() {
        //Dialogos generales
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex ++;

        switch (gp.player.direction) {
            case Arriba:
                direction = Direccion.Arriba;
                break;
            case Abajo:
                direction = Direccion.Abajo;
                break;
            case Derecha:
                direction = Direccion.Derecha;
                break;
            case Izquierda:
                direction = Direccion.Izquierda;
                break;
        }
    }
    //Update
    public void update() {
        //Actualizamos el NPC
        setAction();
        // activacion/desactivacion de la colision
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,true);
        gp.cChecker.checkPlayer(this);

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

    // metodo para cargar el dibujo
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

            switch (direction) {
                case Arriba -> image = (spriteNum == 1) ? up1 : up2;
                case Abajo -> image = (spriteNum == 1) ? down1 : down2;
                case Izquierda -> image = (spriteNum == 1) ? left1 : left2;
                case Derecha -> image = (spriteNum == 1) ? right1 : right2;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    // Carga de las imagenes de las entidades
    protected BufferedImage setup(String imagePatch) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePatch + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
