package main;

import entity.*;
import varios.Dados;
import varios.Reloj;
import varios.Direccion;

import java.awt.*;

public class EventHandler {
    gamePanel gp;

    EventRect eventRect [][][];
    private boolean llamo = false;
    private  boolean piensa =false;
    private  boolean vioGPS =false;
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;


    public EventHandler (gamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while(map < gp.maxMap && col < gp.maxWorldCol &&  row < gp.maxWorldRow){
            eventRect [map][col][row] = new EventRect();
            eventRect [map][col][row].x = 0;
            eventRect [map][col][row].y = 0;
            eventRect [map][col][row].width = gp.tileSize/2;
            eventRect [map][col][row].height = gp.tileSize/2;
            eventRect [map][col][row].eventRectDefaultX = eventRect [map][col][row].x;
            eventRect [map][col][row].eventRectDefaultY = eventRect [map][col][row].y;

            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;

                if(row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {
        // Comprobar distancia del jugador con el evento
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }
        if (canTouchEvent) {
            //video
            if(hit(0, 24, 2, Direccion.Izquierda)||hit(0, 24, 3, Direccion.Izquierda)||hit(0, 24, 4, Direccion.Izquierda)||hit(0, 24, 27, Direccion.Izquierda)||hit(0, 24, 28, Direccion.Izquierda)||hit(0, 24, 29, Direccion.Izquierda)){
                if(!gp.videoMostrado){
                    gp.videoMostrado = true;
                    gp.showVideo("pavon");
                    if(gp.videoMostrado && gp.npc[0][1] instanceof NPC_Pavon) {
                        gp.npc[0][1] = null;
                    }
                }
            }
            else if(hit(2, 8, 28, Direccion.Izquierda)) {
                if (gp.npc[1][2] instanceof NPC_Aila && gp.npc[2][3] instanceof NPC_LupeMarciana) {

                    NPC_Aila aila = (NPC_Aila) gp.npc[1][2];
                    NPC_LupeMarciana marciana = (NPC_LupeMarciana) gp.npc[2][3];

                    // 2. Verificar las condiciones para el final (banderas)
                    // y asegurar que el video no se haya mostrado
                    if (!gp.videoMostrado2 && aila.activarFinal && marciana.tieneGPS) {

                        // 3. Activar bandera y llamar al video
                        gp.videoMostrado2 = true;
                        gp.showVideo("monu");

                        // 4. Reproducir efecto de sonido
                        gp.playSE(6);

                        // IMPORTANTE: NO pongas gp.ui.gameOver = true; aquí.
                        // Esa línea debe ir en el callback del video (gamePanel.java)
                    }
                }
            }
            // Interactuar con entorno
            else if (hit(0, 5, 7, Direccion.Arriba)) {
                interactuarEntorno(0, 5, 7, gp.dialogueState);
            }
            // Teleport Aila
            else if (hit(0, 20, 26, Direccion.Any)) {
                teleport(1, 38, 32);
            }
            // Teleport Planetario
            else if (hit(1, 38, 32, Direccion.Any)) {
                    teleport(2, 4, 48);
            }
            // Teleport Planetario2
            else if (hit(2, 9, 28, Direccion.Any)) {
                teleport(2, 40, 9);
            }
            // Teleport exit Planetario2
            else if (hit(2, 40, 9, Direccion.Any)) {
                teleport(2, 9, 28);
            }
            else if (hit(0, 19, 14, Direccion.Derecha)|| hit(0, 19, 15, Direccion.Derecha) ) {
                if(llamo == false){
                    llamada(gp.dialogueState);
                    llamo = true;
                }
            }
            else if (hit(0,20,14,Direccion.Derecha)||hit(0,20,15, Direccion.Derecha)){
                if(!piensa){
                    mensajeLugar(gp.dialogueState);
                    mensajeLugar(gp.dialogueState);
                    piensa =true;
                }
            }
            else if (hit(2,41,9,Direccion.Any)){
                if(gp.npc[2][3]instanceof NPC_LupeMarciana){
                    NPC_LupeMarciana marciana = (NPC_LupeMarciana) gp.npc[2][3];
                    if(marciana.tieneGPS && !vioGPS){
                        mensajeGPS(gp.dialogueState);
                        vioGPS =true;
                    }
                }
            }
        }
    }

    public boolean hit(int map, int col, int row, Direccion reqDirection){
        boolean hit = false;
        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                if(gp.player.direction == reqDirection || reqDirection == Direccion.Any){
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }


            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }

    public void mensajeLugar(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "*PENSANDO*\nPavon dijo que se iba a la galeria...\nAlla voy Pavon";
        canTouchEvent = false;
    }
    public void interactuarEntorno(int map, int col, int row, int gameState) {
        if(gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "No dice nada";
            eventRect[map][col][row].eventDone = true;
        }
    }

    public void teleport(int map, int col, int row) {
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;
        gp.ui.currentDialogue = "Teleport";
    }

    public void llamada(int gameState) {
        gp.gameState = gameState;
        gp.playSE(5);
        gp.ui.currentDialogue = "\"Volpin! Soy Sebas, \n secuestraron a Pavon... \n Resolve\"";
    }
    public void mensajeGPS(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "*PENSANDO*\nEl GPS dice qque esta en el monumento";
        canTouchEvent = false;
    }
}

