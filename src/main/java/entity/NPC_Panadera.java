package entity;

import main.gamePanel;
import varios.Direccion;

import java.util.Random;

public class NPC_Panadera extends Entity {
    public NPC_Panadera(gamePanel gp) {
        super(gp);
        direction = Direccion.Abajo;
        getImage();
        setDialogue();
        setColisionArea();
    }

    public void getImage() {
        down1 = setup("/NPC_Panadera/Panadera");
        down2 = setup("/NPC_Panadera/Panadera");
    }

    public void setDialogue() {
        dialogues[0] = "Volpin... ";
        dialogues[1] = "Hola cuanto tiempo...";
        dialogues[2] = "Me tengo que ir pero ten, \nde seguro te viene bien\n algo de comer";
        dialogues[3] = "Buena suerte";
    }

    public void setColisionArea() {
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
        solidArea.width = gp.tileSize;
        solidArea.height = 2 * gp.tileSize;
    }

    public void setAction() {
        //agrgar movimiento
        direction = Direccion.Abajo;
    }

    // sobreescritura de la variable speak de entidades por personajes
    public void speak() {
        //podemos personalizar las caracteristicas principales de cada personaje
        super.speak();
    }
}
