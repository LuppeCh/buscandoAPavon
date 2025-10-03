package varios;

import entity.NPC_Panadera;
import main.gamePanel;

public class Pelea {

    gamePanel gp;
    int numDados = 1; //numero de dados en el array
    byte numCaras = 6; // numero de caras del dado

    public Pelea(gamePanel gp){
        this.gp = gp;
    }

    public void iniciarCombate(NPC_Panadera npcPan){

        System.out.println("Debug -> tienePan? " + npcPan.tienePan);
        // Primero, revisamos si el jugador tiene pan
        if (!npcPan.tienePan) {
            // No tiene pan -> muere
            gp.gameState = gp.gameOverState;
            System.out.println("No tenías pan de ajo, perdiste.");
            return;
        }

        // Tirada de dado en el momento
        int tirada = new Dados(numCaras, numDados).tirarDados()[0];

        if (tirada <= 4){ // 1 a 4 -> sumas tiempo
            gp.reloj.agregarTiempo(180);
            System.out.println("salio 1/2/3/4 Se agregaron 3 minutos");
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "\"Ah! Está bien! \n Te diré todo lo que sé \n Hay una nave secuestrando gente\n Te sugiero ir al pllanetario\"";
        } else { // 5 o 6 -> acepta por las buenas
            gp.gameState = gp.dialogueState;
            System.out.println("salio 5/6 no se agrega nada");
            gp.ui.currentDialogue = "\"Bueno, solo por que me das pena\n escuche que hay una nave secuestrando\ngenteTe sugiero ir al pllanetario\"";
        }
    }
}
