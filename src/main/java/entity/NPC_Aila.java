package entity;

import main.gamePanel;
import varios.Direccion;

public class NPC_Aila extends Entity {
    public boolean pelea = false;
    public NPC_Aila(gamePanel gp) {
        super(gp);
        direction = Direccion.Abajo;
        getImage();
        setDialogue();
        setColisionArea();
    }

    public void getImage() {
        down1 = setup("/NPC_Aila/Aila");
        down2 = setup("/NPC_Aila/Aila");
    }

    public void setDialogue() {
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
    int contador = 0;
    // sobreescritura de la variable speak de entidades por personajes
    public void speak() {
        contador ++;
        dialogues[0] = "Uh? Que si conozco a quien?";
        dialogues[1] = "Peivon?";
        dialogues[2] = "Peivon? .";
        dialogues[3] = "Peivon? . .";
        dialogues[4] = "Peivon? . . .";
        dialogues[5] = "Que nombre curioso...";
        dialogues[6] = "la verdad es que no lo he visto";
        dialogues[7] = "*El telefono de Aila suena \n y ella lo guarda rapido*";
        dialogues[8] = "Acaso me estas acusando?";
        dialogues[9] = "Vete de mi tienda!";
        dialogues[10] = "*Te parece sospechoso pero no \n encontras nada raro*";
        dialogues[11] = "*Hasta que antes de irte notaste\n en uno de los espejos de la tienda\n que la chica no tiene reflejo*";
        dialogues[12] = "*es una vampira...*";
        dialogues[13] = "Ah, con que ya lo descubriste";
        if (contador == 14) {
            pelea = true;
        }

        //podemos personalizar las caracteristicas principales de cada personaje
        super.speak();
    }
}



