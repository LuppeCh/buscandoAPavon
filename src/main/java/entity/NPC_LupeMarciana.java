package entity;

import main.gamePanel;
import object.OBJ_gpsNave;
import object.OBJ_valePorComida;
import varios.Direccion;

import java.util.Random;

public class NPC_LupeMarciana extends Entity{
    public boolean tieneGPS = false;

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
        dialogues[0] = "Zip Zip Zip";
        dialogues[1] = "* UN MARCIANO LLORANDO??? *";
        dialogues[2] = "Tu quien eres?";
        dialogues[3] = "Volpin? QUe nombre curioso";
        dialogues[4] = ".";
        dialogues[5] = ". .";
        dialogues[6] = ". . .";
        dialogues[7] = "Por qué te quedas ahi viendo?";
        dialogues[8] = "Ah, que si conozco a tu amigo? \n la verdad no tengo idea";
        dialogues[9] = "Mi nombre es Lupe y vengo \n de la lejana galaxia \n de Venado Tuerto";
        dialogues[10] = "He perdido mi nave, puedes \n ayudarme a encontrarla?";
        dialogues[11] = "Aqui tienes mi gps y mis llaves";
        dialogues[12] = "Suerte viajero";
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
        if(tieneGPS == false) {
            gp.player.inventory.add(new OBJ_gpsNave(gp));
            tieneGPS = true;}
        //podemos personalizar las caracteristicas principales de cada personaje
        super.speak();
    }
}
