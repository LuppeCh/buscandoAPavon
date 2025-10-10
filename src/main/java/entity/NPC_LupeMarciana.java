package entity;

import main.gamePanel;
import object.OBJ_gpsNave;

import varios.Direccion;



public class NPC_LupeMarciana extends Entity{
    public boolean tieneGPS = false;
    public int dialogueIndexNPC = 0;
    public NPC_LupeMarciana(gamePanel gp) {
        super(gp);
        direction = Direccion.Abajo;
        getImage();

        setColisionArea();
    }
    public void getImage() {
        down1 = setup("/NPC_LupeMarciana/lupemarciana1");
        down2 = setup("/NPC_LupeMarciana/lupemarciana2");

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
        dialogues[0] = "Zip Zip Zip";
        dialogues[1] = "* UN MARCIANO LLORANDO??? *";
        dialogues[2] = "Tu quien eres?";
        dialogues[3] = "Volpin? QUe nombre curioso";
        dialogues[4] = ".";
        dialogues[5] = ". .";
        dialogues[6] = ". . .";
        dialogues[7] = "Por que te quedas ahi viendo?";
        dialogues[8] = "Ah, que si conozco a tu amigo? \nla verdad no tengo idea";
        dialogues[9] = "Mi nombre es Lupe y vengo \nde la lejana galaxia \nde Venado Tuerto";
        dialogues[10] = "He perdido mi nave, puedes \nayudarme a encontrarla?";
        dialogues[11] = "Aqui tienes mi gps y mis llaves";
        dialogues[12] = "Suerte viajero";

        if (dialogueIndexNPC < dialogues.length && dialogues[dialogueIndexNPC] != null) {
            gp.ui.currentDialogue = dialogues[dialogueIndexNPC];
            dialogueIndexNPC++;
        } else {
            gp.ui.currentDialogue = "Zip Zip Zip Zip Zip Zip \n*llorando *";
        }

        if(!tieneGPS) {
            gp.player.inventory.add(new OBJ_gpsNave(gp));
            tieneGPS = true;
        }
    }
}
