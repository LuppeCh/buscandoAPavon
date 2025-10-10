package entity;
import main.gamePanel;
import main.KeyHandler;
import object.OBJ_sube;
import varios.Direccion;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    // Contadores de objetos
    public int gpsCount = 0;
    public int panDeAjoCount = 0;
    public int valePorComidaCount = 0;

    //inventario
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;


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
        setItems();
    }

    public void setDefaultValues() {

        worldX = 47 * gp.tileSize; //posicion del Player
        worldY = 4 * gp.tileSize;
        speed = 2;
        direction = Direccion.Abajo;
    }

    public void setItems() {
        //cargar el listado de los items iniciales
        inventory.add(new OBJ_sube(gp));

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
        //inicializacion de la colision
        collisionOn = false;
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) direction = Direccion.Arriba;
            else if (keyH.downPressed) direction = Direccion.Abajo;
            else if (keyH.leftPressed) direction = Direccion.Izquierda;
            else if (keyH.rightPressed) direction = Direccion.Derecha;

            // corroboramos la colision con el entorno
            gp.cChecker.checkTile(this);


            // corroboramos la colision con los objetos
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

//            gp.keyH.enterPressed = false;

            //Verificacion de si existen NPCs
            if(gp.npc[gp.currentMap] != null) {
                // corroboramos la colision con NPCs
                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);
            }


            // corroboramos colision evento
            gp.eHandler.checkEvent();

            // corroboramos colision evento
            gp.eHandler.checkEvent();

            gp.keyH.enterPressed = false;

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
        if (i != 999 && gp.obj[gp.currentMap][i] != null) {
            Entity obj =gp.obj[gp.currentMap][i];
            String objectName = obj.name.toLowerCase().trim();
            System.out.println("Objeto detectado: " + objectName);

            if(inventory.size()>= maxInventorySize){
                gp.ui.showMessage("Inventario lleno");
                return;
            }

            inventory.add(obj);

            if (objectName.contains("gps")) {
                gpsCount++;
                gp.playSE(1);
                gp.ui.showMessage("Tienes el GPS de la nave!!");
                System.out.println("GPS: " + gpsCount);

            } else if (objectName.contains("pan de ajo")) {
                panDeAjoCount++;
                gp.playSE(1);
                gp.ui.showMessage("Tienes el pan de ajo!!");
                System.out.println("Pan de Ajo: " + panDeAjoCount);

            } else if (objectName.contains("vale por comida")) {
                valePorComidaCount++;
                gp.playSE(1);
                speed += 1;
                gp.ui.showMessage("Tienes el vale por comida!!");
                System.out.println("Vale por comida: " + valePorComidaCount);

            } else if (objectName.contains("chest")) {
                gp.ui.gameFinished = true;
                gp.stopMusic();
                gp.playSE(3);
            } else {
                gp.playSE(1);
                gp.ui.showMessage("Has recogido:" + obj.name);
            }
            gp.obj[gp.currentMap][i] = null;
        }
    }

    public void interactNPC(int i) {
        if (i != 999){
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }
        }
    }


    public void removeItem(String itemName) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equalsIgnoreCase(itemName)) {
                inventory.remove(i);
                break;
            }
        }
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