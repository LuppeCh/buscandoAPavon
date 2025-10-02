package entity;

import main.gamePanel;
import varios.Direccion;

import java.util.Random;

public class NPC_Pavon extends Entity {
    public boolean tieneCupon = false;
    public NPC_Pavon(gamePanel gp) {
        super(gp);
        direction = Direccion.Abajo;
        speed = 1; // lo cambie para frenar al npc
        getImage();
        setDialogue();
        setColisionArea();
    }

    public void getImage() {
        up1 = setup("/Pavon/pavondetras1");
        up2 = setup("/Pavon/pavondetras2");
        down1 = setup("/Pavon/pavonfrente1");
        down2 = setup("/Pavon/pavonfrente2");
        left1 = setup("/Pavon/pavonizquierda1");
        left2 = setup("/Pavon/pavonizquierda2");
        right1 = setup("/Pavon/pavonderecha1");
        right2 = setup("/Pavon/pavonderecha2");
    }

    public void setDialogue() {
        dialogues[0] = "Volpin... ";
        dialogues[1] = "Hola cuanto tiempo...";
        dialogues[2] = "Me tengo que ir pero ten, \nde seguro te viene bien\n algo de comer";
        dialogues[3] = "Buena suerte";
        tieneCupon = true;
    }

    public void setColisionArea() {
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
    }

    public void setAction() {

        actionLockCounter++;
        if (actionLockCounter == 120) { // hacemos que solo se cambie la direccion cada 120 updates
            Random random = new Random();
            int i = random.nextInt(100) + 1; // consigue un numero entre 1 y 100
            if (i <= 25) {
                direction = Direccion.Arriba;
            }
            if (i > 25 && i <= 50) {
                direction = Direccion.Abajo;
            }
            if (i > 50 && i <= 75) {
                direction = Direccion.Derecha;
            }
            if (i > 75 && i <= 100) {
                direction = Direccion.Izquierda;
            }
            actionLockCounter = 0;
        }

    }

    // sobreescritura de la variable speak de entidades por personajes
    public void speak() {
        //podemos personalizar las caracteristicas principales de cada personaje
        super.speak();
    }
}
