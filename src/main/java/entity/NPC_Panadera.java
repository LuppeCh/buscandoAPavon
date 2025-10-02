    package entity;

    import main.gamePanel;
    import object.OBJ_panDeAjo;
    import varios.Direccion;

    import java.util.Random;

    public class NPC_Panadera extends Entity {
        public boolean tienePan = false;
        public boolean dialogoTerminado = false;
        public int dialogueIndexNPC = 0;
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
            if (gp.npc[0][1] instanceof NPC_Pavon) { // casteamos Pavon
                NPC_Pavon pavon = (NPC_Pavon) gp.npc[0][1];

                if (pavon.tieneCupon) {
                    dialogues[0] = "Uh... Que es eso? ";
                    dialogues[1] = "Un cupon de comida?";
                    dialogues[2] = "Felicidades.... supongo";
                    dialogues[3] = "Te ofrezco 3 opciones: \n Chipa, Medialunas o... \n Pan de Ajo!!";
                    dialogues[4] = ".";
                    dialogues[5] = ". .";
                    dialogues[6] = ". . .";
                    dialogues[7] = "Me equivoque, solo nos queda \n Pan de Ajo... \n Disfrutalo!!!";
                    gp.player.removeItem("vale por comida");
                    if(tienePan == false){
                        gp.player.inventory.add(new OBJ_panDeAjo(gp));
                        tienePan=true;
                    }

                } else {
                    dialogues[0] = "Hola... en que te puedo ayudar?";
                    dialogues[1] = "Lo siento... \npero la unica comida que\nnos queda esta reservada";
                    dialogues[2] = "Si no tienes el cupon... \nEntonces vete... \nEspero clientes";
                }
            }
            // Mostrar línea por línea
            if (dialogues[dialogueIndexNPC] != null) {
                gp.ui.currentDialogue = dialogues[dialogueIndexNPC];

                dialogueIndexNPC++;
            } else {
                dialogoTerminado = true; // terminó todo el diálogo
                gp.ui.currentDialogue = "Ya te di el pan de ajo. Siguiente!!!"; // opcional
            }
        }
        }

