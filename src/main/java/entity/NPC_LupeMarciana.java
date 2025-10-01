package entity;

import main.gamePanel;
import varios.Direccion;

import java.util.Random;

public class NPC_LupeMarciana extends Entity{

    public NPC_LupeMarciana(gamePanel gp) {
        super(gp);
        direction = Direccion.Abajo;
        getImage();
        setDialogue();
        setColisionArea();
    }
    public void getImage() {
        down1 = setup("/NPC_LupeMarciana/lupemarciana1");
        down2 = setup("/NPC_LupeMarciana/lupemarciana2");

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
        solidArea.height = gp.tileSize;
    }

    public void setAction() {
        //agregar posible accion
        direction = Direccion.Abajo;
    }

    // sobreescritura de la variable speak de entidades por personajes
    public void speak() {
        //podemos personalizar las caracteristicas principales de cada personaje
        super.speak();
    }
}
