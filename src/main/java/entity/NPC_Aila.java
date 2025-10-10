package entity;

import main.gamePanel;
import varios.Direccion;
import varios.Pelea;

public class NPC_Aila extends Entity {
    public boolean dialogoTerminado = false;
    public int dialogueIndexNPC = 0;
    private int contador = 0;
    public boolean activarFinal =false;

    public NPC_Aila(gamePanel gp) {
        super(gp);
        direction = Direccion.Abajo;
        getImage();
        setColisionArea();
    }

    public void getImage() {
        down1 = setup("/NPC_Aila/Aila");
        down2 = setup("/NPC_Aila/Aila");
    }


    public void setColisionArea() {
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
        solidArea.width = gp.tileSize;
        solidArea.height = 2 * gp.tileSize;
    }

    public void setAction() {
        direction = Direccion.Abajo;
    }

    @Override
    public void speak() {
        contador++;

        dialogues[0] = "Uh? Que si conozco a quien?";
        dialogues[1] = "Peivon?";
        dialogues[2] = ".";
        dialogues[3] = ". .";
        dialogues[4] = ". . .";
        dialogues[5] = "Que nombre curioso...";
        dialogues[6] = "la verdad es que no lo he visto";
        dialogues[7] = "*El telefono de Aila suena \n y ella lo guarda rapido*";
        dialogues[8] = "Acaso me estas acusando?";
        dialogues[9] = "Vete de mi tienda!";
        dialogues[10] = "*Te parece sospechoso pero no \n encontras nada raro*";
        dialogues[11] = "*Hasta que antes de irte notaste\n en uno de los espejos de la tienda\n que la chica no tiene reflejo*";
        dialogues[12] = "*es una vampira!*";
        dialogues[13] = "Ah, con que ya lo descubriste";

        // Mostramos el diálogo actual
        if (dialogueIndexNPC < dialogues.length && dialogues[dialogueIndexNPC] != null) {
            gp.ui.currentDialogue = dialogues[dialogueIndexNPC];
            dialogueIndexNPC++;
        } else {
            dialogoTerminado = true;
            gp.ui.currentDialogue = "Solo vete... no te quiero ver mas!!!";
        }

        // Activar pelea al final del diálogo
        if (contador == 14 && !dialogoTerminado) {
            activarPelea();
            activarFinal = true;
        }
    }

    private void activarPelea() {
        // Usamos el Reloj que ya existe en el gamePanel
        Pelea pelea = new Pelea(gp);

        // Obtenemos la panadera correcta del mapa (asegurarse de que sea instancia de NPC_Panadera)
        NPC_Panadera panadera = null;
        for (int i = 0; i < gp.maxWorldCol; i++) {
            for (int j = 0; j < gp.maxWorldRow; j++) {
                if (gp.npc[0][i] != null && gp.npc[0][i] instanceof NPC_Panadera) {
                    panadera = (NPC_Panadera) gp.npc[0][i];
                    break;
                }
            }
            if (panadera != null) break;
        }

        if (panadera != null) {
            pelea.iniciarCombate(panadera); // ⚡ Usamos el reloj interno de Pelea que apunta a gp.reloj
        } else {
            System.out.println("No se encontró la Panadera en el mapa");
        }
    }
}
